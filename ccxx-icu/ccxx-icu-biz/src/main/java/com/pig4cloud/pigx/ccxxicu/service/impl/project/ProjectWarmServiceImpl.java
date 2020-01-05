/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pigx.ccxxicu.service.impl.project;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectWarm;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.WarmJudge;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.ItemWarnInfo;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.Message;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.MessageTypeEnum;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectWarmVo;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.utils.RegularMatchUtils;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.project.ProjectWarmMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectWarmService;
import com.pig4cloud.pigx.ccxxicu.service.project.WarmJudgeService;
import com.pig4cloud.pigx.ccxxicu.websocket.WebSocketServer;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 项目预警表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:59:14
 */
@Service
public class ProjectWarmServiceImpl extends ServiceImpl<ProjectWarmMapper, ProjectWarm> implements ProjectWarmService {

	@Autowired
	private WarmJudgeService warmJudgeService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	@Value("${report.startTime}")
	private Integer startHour;//开始的小时

	@Autowired
	private NurseService nurseService;

	/**
	 * 对该项目的录入数据进行校验
	 * 将异常数据进行处理
	 *
	 * @param projectWarm
	 * @return
	 */
	@Override
	public int add(ProjectWarm projectWarm) {

		if (projectWarm == null) {
			return 0;
		}


		Project project = projectService.selectByProjectId(projectWarm.getProjectId());
		WarmJudge warmJudge = warmJudgeService.selectByProjectId(project.getProjectId());
		if (warmJudge == null) {
			project.setProjectWarmFlag(1);
			projectService.updateById(project);
		}
		if (StringUtils.isEmpty(projectWarm.getWarmValue())||"null".equals(projectWarm.getWarmValue())) {
			return 0;
		}
		Integer topValue = warmJudge.getTopValue() == null ? 0 : warmJudge.getTopValue();
		Integer bottomValue = warmJudge.getBottomValue() == null ? 0 : warmJudge.getBottomValue();
		Integer maxWarm = warmJudge.getMaxWarm() == null ? 0 : warmJudge.getMaxWarm();
		Integer minWarm = warmJudge.getMinWarm() == null ? 0 : warmJudge.getMinWarm();

		Double aDouble = Double.parseDouble(RegularMatchUtils.getDouble(projectWarm.getWarmValue())==null?"":RegularMatchUtils.getDouble(projectWarm.getWarmValue()));

		if (aDouble==null) {
			return 0;
		}

		//数据不合法
		if (aDouble <= bottomValue || aDouble >= topValue) {
			throw new ApiException(10000,project.getProjectName()+"的数据不符合规则！");
		}
		if (minWarm > aDouble || aDouble > maxWarm) {

			projectWarm.setWarmContent("患者的" + project.getProjectName() + "为" + projectWarm.getWarmValue() + "，可能存在异常！");

		} else {
			return 0;
		}

		ProjectWarm result  = this.selectByPatient(projectWarm);

		if (result==null) {

			projectWarm.setCreateTime(LocalDateTime.now());
			projectWarm.setDelFlag(0);
			projectWarm.setProjectWarmId(SnowFlake.getId()+"");
			boolean save = this.save(projectWarm);
			this.setToWatch(projectWarm);
			if (save) {
				return 0;
			} else {
				throw new ApiException(10000,project.getProjectName()+"的预警添加失败！");
			}
		}

		result.setWarmContent(projectWarm.getWarmContent());
		result.setWarmValue(projectWarm.getWarmValue());
		result.setCreateTime(LocalDateTime.now());
		boolean b = this.updateById(result);
		this.setToWatch(result);
		if (b) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 项目预警推送腕表
	 *
	 * @param projectWarm
	 */
	private void setToWatch(ProjectWarm projectWarm) {

		if (projectWarm == null) {
			return;
		}
		PatientInfo patientInfo = nursePatientCorrelationService.selectPatientId(projectWarm.getPatientId());
		Project project = projectService.selectByProjectId(projectWarm.getProjectId());
		WebSocketServer ws = new WebSocketServer();
		Message message = new Message();
		message.setReceiver(patientInfo.getNurse().getNurseId());
		message.setType(MessageTypeEnum.ITEM_WARN_PROMPT);
		List<Object> infoList = new ArrayList<>();
		ItemWarnInfo itemWarnInfo = new ItemWarnInfo();
		itemWarnInfo.setBedNo(patientInfo.getHospitalBed().getBedCode());
		itemWarnInfo.setItemName(project.getProjectName());
		itemWarnInfo.setPatientName(patientInfo.getPatient().getName());
		itemWarnInfo.setItemValue(projectWarm.getWarmValue());
		infoList.add(itemWarnInfo);
		message.setMessageBody(infoList);
		try {
			ws.sendMessageTo(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 预警项目分页查询
	 *
	 * @param page
	 * @param projectWarmVo
	 * @return
	 */
	@Override
	public IPage<ProjectWarmVo> selectByPage(Page page, ProjectWarmVo projectWarmVo) {
		return baseMapper.selectByPage(page, projectWarmVo);
	}


	@Override
	public ProjectWarm selectByPatient(ProjectWarm projectWarm) {
		return baseMapper.selectByPatient(projectWarm);
	}

	/**
	 * 循环展示
	 * @param projectWarmVo
	 * @return
	 */
	@Override
	public List<ProjectWarmVo> warmShow(ProjectWarmVo projectWarmVo) {

		LocalDateTime now = LocalDateTime.now();

		int hour = now.getHour();

		if (hour < startHour) {

			LocalDateTime dateTime = now.minusDays(1).withHour(startHour);

			projectWarmVo.setStartTime(dateTime);

		} else {
			LocalDateTime dateTime = now.withHour(startHour);
			projectWarmVo.setStartTime(dateTime);
		}

		projectWarmVo.setEndTime(LocalDateTime.now());

		List<ProjectWarmVo> projectWarmVos = baseMapper.warmShow(projectWarmVo);

		if (CollectionUtils.isEmpty(projectWarmVos)) {

			ProjectWarmVo res = new ProjectWarmVo();

			Nurse nurse = nurseService.selectByUserId(SecurityUtils.getUser().getId() + "");
			String motto = null;
			if (nurse != null) {
				 motto = nurse.getMotto();
			}

			if (StringUtils.isEmpty(motto)) {
				Random num = new Random();
				int i = num.nextInt(5);
				if (i == 0) {
					res.setBedCode("追求完美的服务,做病人的知心朋友!");
				} else if (i == 1) {
					res.setBedCode("对每个患者多点细心,多点耐心,再多点责任心!");
				} else if (i == 2) {
					res.setBedCode("爱心相连,服务永远!");
				} else if (i == 3) {
					res.setBedCode("性命所系,健康所托。");
				} else {
					res.setBedCode("微笑暖人心,真情待病人。");
				}

			} else {
				res.setBedCode(motto);
			}
			projectWarmVos.add(res);
		}

		return projectWarmVos;
	}

	/**
	 * watch 展示的数据
	 * @param nurseId
	 * @return
	 */
	@Override
	public List<ProjectWarmVo> toWatch(String nurseId) {

		Nurse nurse = nurseService.selectByUserId(nurseId);
		return baseMapper.toWatch(nurse);
	}
}
