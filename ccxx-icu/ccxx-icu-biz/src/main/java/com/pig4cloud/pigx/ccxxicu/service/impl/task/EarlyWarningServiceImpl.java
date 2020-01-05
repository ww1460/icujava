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
package com.pig4cloud.pigx.ccxxicu.service.impl.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.EarlyWarning;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.Message;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.MessageTypeEnum;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.TaskWarnInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.EarlyWarningVo;
import com.pig4cloud.pigx.ccxxicu.mapper.task.EarlyWarningMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.task.EarlyWarningService;
import com.pig4cloud.pigx.ccxxicu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务预警表
 *
 * @author pigx code generator
 * @date 2019-08-16 14:57:57
 */
@Service
public class EarlyWarningServiceImpl extends ServiceImpl<EarlyWarningMapper, EarlyWarning> implements EarlyWarningService {

	@Autowired
	private EarlyWarningMapper earlyWarningMapper;

	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	@Autowired
	private NurseService nurseService;


	/**
	 * 定时器查询重复的任务内容
	 * @param taskId
	 * @return
	 */
	@Override
	public EarlyWarning timingSelect(String taskId) {
		return earlyWarningMapper.timingSelect(taskId);
	}

	/**
	 * 定时查询全部数据
	 * @return
	 */
	@Override
	public List<EarlyWarning> timingAll() {
		return earlyWarningMapper.timingAll();
	}

	/**
	 * 预警任务分页查询
	 * @param page
	 * @param earlyWarning
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, EarlyWarning earlyWarning) {
		return earlyWarningMapper.selectPaging(page,earlyWarning);
	}


	@Override
	public List<EarlyWarning> getEarlyTask(String patientId) {
		EarlyWarning earlyWarning = new EarlyWarning();
		earlyWarning.setDelFlag(0);
		earlyWarning.setPatientId(patientId);
		earlyWarning.setState(0);
		return baseMapper.selectList(new QueryWrapper<>(earlyWarning));
	}


	@Override
	public void setToWatch(EarlyWarning earlyWarning) {
		if (earlyWarning==null) {
			return;
		}
		PatientInfo patientInfo = nursePatientCorrelationService.selectPatientId(earlyWarning.getPatientId());
		WebSocketServer ws = new WebSocketServer();
		Message message = new Message();
		message.setReceiver(patientInfo.getNurse().getNurseId());
		message.setType(MessageTypeEnum.ITEM_WARN_PROMPT);
		List<Object> infoList = new ArrayList<>();
		TaskWarnInfo itemWarnInfo = new TaskWarnInfo();
		itemWarnInfo.setBedNo(patientInfo.getHospitalBed().getBedCode());
		itemWarnInfo.setTaskName(earlyWarning.getTaskContent());
		itemWarnInfo.setPatientName(patientInfo.getPatient().getName());
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = earlyWarning.getTriggerTime().atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		itemWarnInfo.setExecuteTime(date);
		infoList.add(itemWarnInfo);
		message.setMessageBody(infoList);
		try {
			ws.sendMessageTo(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<EarlyWarningVo> getTask(String nurseId) {

		Nurse nurse = nurseService.selectByUserId(nurseId);

		return baseMapper.getTask(nurse);
	}
}
