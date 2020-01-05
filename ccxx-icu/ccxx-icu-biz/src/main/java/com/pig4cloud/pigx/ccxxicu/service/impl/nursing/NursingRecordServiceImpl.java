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
package com.pig4cloud.pigx.ccxxicu.service.impl.nursing;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.*;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectWarm;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingProjectVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingRecordShow;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingRecordVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingReportThreeVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.IntakeOutputEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.ProjectTypeEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.utils.RegularMatchUtils;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nursing.NursingRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.nursing.IntakeService;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectWarmService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 护理记录
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:13
 */
@Service
public class NursingRecordServiceImpl extends ServiceImpl<NursingRecordMapper, NursingRecord> implements NursingRecordService {

	@Value("${report.startTime}")
	private Integer startHour;//开始的小时

	@Value("${report.endTime}")
	private Integer endHour;//结束的小时

	@Autowired
	private ProjectRecordService projectRecordService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectWarmService projectWarmService;

	@Autowired
	private IntakeService intakeOutputRecordService;

	@Autowired
	private ProjectRecordValueService projectRecordValueService;

	@Autowired
	private HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	/**
	 * 任务添加护理记录
	 * @param taskSubTask
	 * @return
	 */
	@Override
	public boolean taskAdd(TaskSubTask taskSubTask) {

		NursingRecord nursingRecord = new NursingRecord();

		PigxUser user = SecurityUtils.getUser();
		//来源
		nursingRecord.setSource(DataSourceEnum.TASK.getCode());
		nursingRecord.setCreateTime(LocalDateTime.now());
		//来源id  这里是任务id
		String id = SnowFlake.getId() + "";
		nursingRecord.setSourceId(taskSubTask.getTaskSubTaskId());
		nursingRecord.setDelFlag(0);
		nursingRecord.setCreateUserId(user.getId()+"");
		nursingRecord.setDeptId(user.getDeptId()+"");
		nursingRecord.setPatientId(taskSubTask.getPatientId());
		//对护理记录内容进行处理  结果拼接
		StringBuffer content = new StringBuffer();
		String result = taskSubTask.getResult();
		String taskName = taskSubTask.getTaskName();
		Integer dosage = taskSubTask.getDosage();
		content.append(StringUtils.isEmpty(taskName) ? "" : taskName);
		content.append((dosage==null||dosage==0) ? "" : ":"+dosage+"。");
		content.append(StringUtils.isEmpty(result) ? "" : "结果为："+result);
		nursingRecord.setRecordContent(content.toString());
		nursingRecord.setNursingRecordId(id);
		//查询任务的类型
		Integer sourceItems = taskSubTask.getTaskType();

		//如果是属于项目类
		if (sourceItems!= null &&  sourceItems== TaskEnum.PROJECT.getNumber()) {
			this.taskVerifyProject(taskSubTask,id);
		}

		//来源于医嘱项目
		if (sourceItems!= null &&  sourceItems== TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()) {

			this.taskVerifyAdvice(taskSubTask,id);

		}

		//来源于自建模板项目
		if (sourceItems!= null &&  sourceItems== TaskEnum.TEMPLATE_PROJECT.getNumber()) {

			this.taskVerifyProject(taskSubTask,id);

		}

		//来源于模板快捷项目
		if (sourceItems!= null &&  sourceItems== TaskEnum.FAST_PROJECT.getNumber()) {

			this.taskVerifyFastProject(taskSubTask,id);

		}

		return this.save(nursingRecord);
	}

	/**
	 * 处理模板快捷项目
	 * @param taskSubTask
	 * @param nursingRecordId
	 */
	private boolean taskVerifyFastProject(TaskSubTask taskSubTask,String nursingRecordId) {

		//这里是模板  医嘱  项目
		String taskTypeId = taskSubTask.getTaskTypeId();

		String[] split = taskTypeId.split("-");

		if (split.length!=2) {
			return false;
		}

		//查询项目
		Project project = projectService.selectByProjectId(split[1]);

		if (project==null) {
			return false;
		}

		ProjectRecord projectRecord = new ProjectRecord();
		projectRecord.setProjectId(project.getProjectId());
		projectRecord.setPatientId(taskSubTask.getPatientId());
		projectRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");
		projectRecord.setCreateTime(LocalDateTime.now());
		projectRecord.setSource(DataSourceEnum.TASK.getCode());
		projectRecord.setSourceId(nursingRecordId);
		projectRecord.setProjectRecordId(SnowFlake.getId()+"");
		projectRecord.setProjectSpecificRecord(StringUtils.isEmpty(taskSubTask.getResult())?
				taskSubTask.getTaskName():taskSubTask.getResult());
		projectRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");

		//判断该项目是否存在固定值
		if (project.getProjectRecordValueFlag()==0) {

			ProjectRecordValue recordValue = new ProjectRecordValue();

			recordValue.setDeptId(project.getDeptId());
			recordValue.setDelFlag(0);
			recordValue.setProjectId(project.getProjectId());
			recordValue.setRecordValue(taskSubTask.getResult());

			ProjectRecordValue one = projectRecordValueService.getOne(new QueryWrapper<>(recordValue));

			if (one!= null) {

				projectRecord.setProjectValue(one.getDataFlag()+"");
				projectRecord.setProjectSpecificRecord(one.getRecordValue());

			}

		}

		//判断预警
		boolean b = this.verifyWarm(project, projectRecord);
		if (!b) {
			return false;
		}

		//判断该项目是否为出入量
		boolean b1 = this.verifyIntakeOutput(project, projectRecord,nursingRecordId);
		if (!b1) {
			return false;
		}

		return projectRecordService.add(projectRecord);

	}


	/**
	 * 任务处理项目类
	 */
	private Boolean taskVerifyProject(TaskSubTask taskSubTask,String nursingRecordId) {

		String sourceId = taskSubTask.getTaskTypeId();

		if (StringUtils.isEmpty(sourceId)) {

			return false;

		}
		//查询项目
		Project project = projectService.selectByProjectId(sourceId);

		if (project==null) {
			return false;
		}

		ProjectRecord projectRecord = new ProjectRecord();
		projectRecord.setProjectId(project.getProjectId());
		projectRecord.setPatientId(taskSubTask.getPatientId());
		projectRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");
		projectRecord.setCreateTime(LocalDateTime.now());
		projectRecord.setSource(DataSourceEnum.TASK.getCode());
		projectRecord.setSourceId(nursingRecordId);
		projectRecord.setProjectRecordId(SnowFlake.getId()+"");
		projectRecord.setProjectSpecificRecord(StringUtils.isEmpty(taskSubTask.getResult())?
				taskSubTask.getTaskName():taskSubTask.getResult());
		projectRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");


		//判断该项目是否存在固定值
		if (project.getProjectRecordValueFlag() == 0) {

			ProjectRecordValue recordValue = new ProjectRecordValue();

			recordValue.setDeptId(project.getDeptId());
			recordValue.setDelFlag(0);
			recordValue.setProjectId(project.getProjectId());
			recordValue.setRecordValue(taskSubTask.getResult());

			ProjectRecordValue one = projectRecordValueService.getOne(new QueryWrapper<>(recordValue));

			if (one != null) {

				projectRecord.setProjectValue(one.getDataFlag() + "");
				projectRecord.setProjectSpecificRecord(one.getRecordValue());

			}

		} else {
			if (projectRecord.getProjectSpecificRecord() != null) {

				projectRecord.setProjectValue(RegularMatchUtils.getDouble(projectRecord.getProjectSpecificRecord()) + "");

			}
		}

		//判断预警
		boolean b = this.verifyWarm(project, projectRecord);
		if (!b) {
			return false;
		}

		//判断该项目是否为出入量
		boolean b1 = this.verifyIntakeOutput(project, projectRecord,nursingRecordId);
		if (!b1) {
			return false;
		}
		/**
		 * ------------------------固定值搞定----------------------------------------
		 */
		return projectRecordService.add(projectRecord);
	}

	/**
	 * 任务处理医嘱项目
	 * @return
	 */
	private boolean taskVerifyAdvice(TaskSubTask taskSubTask,String nursingRecordId) {
		//这里是模板  医嘱  项目
		String taskTypeId = taskSubTask.getTaskTypeId();

		String[] split = taskTypeId.split("-");

		if (split.length!=3) {

			return false;

		}

		HisDoctorsAdviceProject condition = new HisDoctorsAdviceProject();

		condition.setDoctorsAdviceProjectId(split[1]);
		//医嘱
		HisDoctorsAdviceProject one = hisDoctorsAdviceProjectService.getOne(new QueryWrapper<>(condition));
		//项目
		Project project = projectService.selectByProjectId(split[2]);

		ProjectRecord projectRecord = new ProjectRecord();
		projectRecord.setProjectId(project.getProjectId());
		projectRecord.setPatientId(taskSubTask.getPatientId());
		projectRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");
		projectRecord.setCreateTime(LocalDateTime.now());
		projectRecord.setSource(DataSourceEnum.TASK.getCode());
		projectRecord.setSourceId(nursingRecordId);
		projectRecord.setProjectRecordId(SnowFlake.getId()+"");
		projectRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		//判断项目是否为出入量
		if (project.getProjectType().equals(ProjectTypeEnum.OUTPUT_PROJECT.getCode())||
				project.getProjectType().equals(ProjectTypeEnum.INTAKE_PROJECT.getCode())) {
			//如果是出入量的医嘱   将医嘱存入
			projectRecord.setProjectSpecificRecord(one.getContent());
			projectRecord.setProjectValue(taskSubTask.getDosage()+"");
			projectRecord.setRemarks(split[1]);
		}


		projectRecord.setProjectSpecificRecord(StringUtils.isEmpty(taskSubTask.getResult())?
				taskSubTask.getTaskName():taskSubTask.getResult());

		//判断该项目是否存在固定值
		if (project.getProjectRecordValueFlag()==0) {

			ProjectRecordValue recordValue = new ProjectRecordValue();

			recordValue.setDeptId(project.getDeptId());
			recordValue.setDelFlag(0);
			recordValue.setProjectId(project.getProjectId());
			recordValue.setRecordValue(taskSubTask.getResult());

			ProjectRecordValue ones = projectRecordValueService.getOne(new QueryWrapper<>(recordValue));

			if (ones!= null) {

				projectRecord.setProjectValue(ones.getDataFlag()+"");
				projectRecord.setProjectSpecificRecord(ones.getRecordValue());

			}

		}

		//判断预警
		boolean b = this.verifyWarm(project, projectRecord);
		if (!b) {
			return false;
		}
		//判断该项目是否为出入量
		boolean b1 = this.verifyIntakeOutput(project, projectRecord, nursingRecordId);
		if (!b1) {
			return false;
		}
		return projectRecordService.add(projectRecord);
	}

	/**
	 * 护理模板新增护理记录 并关联其他数据 新增
	 *
	 * @param templateRecordBo
	 * @return
	 */
	@Transactional
	@Override
	public boolean templateAdd(TemplateRecordBo templateRecordBo) {

		NursingRecord nursingRecord = new NursingRecord();

		PigxUser user = SecurityUtils.getUser();
		LocalDateTime now = LocalDateTime.now();

		String s = this.verifyNursingContent(templateRecordBo.getRecordContent());

		if (StringUtils.isEmpty(s)) {

			return false;

		}
		//护理记录的封装
		nursingRecord.setCreateTime(now);
		nursingRecord.setCreateUserId(user.getId() + "");
		nursingRecord.setDelFlag(0);
		nursingRecord.setNursingRecordId(SnowFlake.getId() + "");
		nursingRecord.setDeptId(user.getDeptId() + "");
		nursingRecord.setPatientId(templateRecordBo.getPatientId());
		nursingRecord.setRecordContent(s);
		nursingRecord.setSource(DataSourceEnum.NURSING_TEMPLATE.getCode());
		nursingRecord.setSourceId(templateRecordBo.getTemplateCode());//护理模板的code值
		nursingRecord.setShowFlag(templateRecordBo.getShowFlag());//展示位置  这里会出现展示在护理记录单三的位置
		boolean save = this.save(nursingRecord);
		if (!save) {
			return false;
		}

		if (templateRecordBo.getIsProjectFlag().equals(1)) {

			return true;

		}

		//List<Integer> num = new ArrayList<>();
		//分解扔项目记录 并检验数据
		List<ProjectRecord> projectRecords = templateRecordBo.getProjectRecords();
		//添加项目数据
		for (ProjectRecord ar : projectRecords) {

			ar.setPatientId(templateRecordBo.getPatientId());
			ar.setDeptId(user.getDeptId() + "");
			ar.setSource(DataSourceEnum.NURSING_TEMPLATE.getCode());
			ar.setSourceId(nursingRecord.getNursingRecordId());
			ar.setCreateUserId(user.getId() + "");
			ar.setCreateTime(now);

			String projectId = ar.getProjectId();

			Project project = projectService.selectByProjectId(projectId);
			//验证数据预警
			boolean b = this.verifyWarm(project, ar);
			if (!b) {
				return false;
			}

			//判断该项目是否为出入量
			boolean b1 = this.verifyIntakeOutput(project, ar, nursingRecord.getNursingRecordId());
			if (!b1) {
				return false;
			}
		}

		boolean b = projectRecordService.addList(projectRecords);

		return b;
	}

	/**
	 * 修改单条护理记录的前提查看
	 *
	 * @param nursingRecord
	 * @return
	 */
	@Transactional
	@Override
	public R getNursingRecord(NursingRecord nursingRecord) {


		NursingRecordShow nursingRecordShow = new NursingRecordShow();



		nursingRecordShow.setNursingRecord(nursingRecord);

		List<NursingProjectVo> projectRecords = projectRecordService.getValueCorrelation(nursingRecord.getNursingRecordId());

		nursingRecordShow.setNursingProject(projectRecords);

		return R.ok(nursingRecordShow);
	}

	/**
	 * 护理记录分页查询
	 *
	 * @param page
	 * @param nursingRecordBo
	 * @return
	 */
	@Override
	public IPage<NursingRecordVo> selectByPage(Page page, NursingRecordBo nursingRecordBo) {
		return baseMapper.selectByPage(page, nursingRecordBo);
	}

	/**
	 * 修改护理记录
	 *
	 * @param nursingRecordShow
	 * @return
	 */
	@Transactional
	@Override
	public boolean updateNursingRecord(NursingRecordShow nursingRecordShow) {

		PigxUser user = SecurityUtils.getUser();

		NursingRecord nursingRecord = nursingRecordShow.getNursingRecord();

		String s = this.verifyNursingContent(nursingRecord.getRecordContent());
		if (!StringUtils.isEmpty(s)) {
			nursingRecord.setRecordContent(s);
		}


		/**
		 * 护理记录修改失败
		 */
		if (!this.updateById(nursingRecord)) {
			return false;
		}

		/**
		 * 判断是否存在管联数据
		 */
		if (CollectionUtils.isEmpty(nursingRecordShow.getNursingProject())) {

			return true;

		}

		List<NursingProjectVo> nursingProject = nursingRecordShow.getNursingProject();

		//项目数据
		for (NursingProjectVo ar : nursingProject) {


			//存在项目预警
			if (ar.getProjectWarmFlag().equals(0)) {
				ProjectWarm projectWarm = new ProjectWarm();
				projectWarm.setWarmContent(ar.getProjectValue());
				projectWarm.setWarmValue(RegularMatchUtils.getDouble(ar.getProjectValue()) + "");
				projectWarm.setDeptId(ar.getDeptId());
				projectWarm.setProjectId(ar.getProjectId());
				projectWarm.setPatientId(ar.getPatientId());
				projectWarm.setCreateUserId(user.getId() + "");
				int add = projectWarmService.add(projectWarm);
				if (add == 1) {

					return false;

				}
			}

			ProjectRecord projectRecord = new ProjectRecord();
			BeanUtil.copyProperties(ar, projectRecord);



			if (ar.getProjectSpecificRecord() != null && RegularMatchUtils.getDouble(ar.getProjectSpecificRecord()) != null) {

				ar.setProjectValue(RegularMatchUtils.getDouble(ar.getProjectSpecificRecord()));

			}
			ar.setUpdateUserId(user.getId() + "");


			//该项目是出入量时
			if ((ProjectTypeEnum.OUTPUT_PROJECT.getCode()).equals(ar.getProjectType()) ||
					(ProjectTypeEnum.INTAKE_PROJECT.getCode()).equals(ar.getProjectType())) {

				IntakeOutputRecord intakeOutputRecord = new IntakeOutputRecord();
				intakeOutputRecord.setPatientId(ar.getPatientId());
				intakeOutputRecord.setProjectId(ar.getProjectId());
				intakeOutputRecord.setSourceId(nursingRecord.getNursingRecordId());

				IntakeOutputRecord record = intakeOutputRecordService.selectCorrelationValue(intakeOutputRecord);

				if ((ProjectTypeEnum.OUTPUT_PROJECT.getCode()).equals(ar.getProjectType())) {
					record.setIntakeOutputValue(-RegularMatchUtils.getInteger(ar.getProjectSpecificRecord()));
					ar.setProjectValue(record.getIntakeOutputValue()+"");
				}

				record.setUpdateUserId(user.getId() + "");

				if (record != null) {

					boolean b = intakeOutputRecordService.updateRecord(record);
					if (!b) {
						return false;
					}
				}
			}
			//修改数据
			if (!projectRecordService.updateRecord(projectRecord)) {

				return false;

			}


		}

		return true;
	}

	/**
	 * 删除护理记录
	 *
	 * @param nursingRecord
	 * @return
	 */
	@Transactional
	@Override
	public boolean deleteNursingRecord(NursingRecord nursingRecord) {

		//删除护理记录
		if (!this.updateById(nursingRecord)) {
			return false;
		}
		//获取护理记录生成id
		String nursingRecordId = nursingRecord.getNursingRecordId();
		//去查询是否有关联数据
		List<NursingProjectVo> valueCorrelation = projectRecordService.getValueCorrelation(nursingRecordId);
		if (valueCorrelation == null || valueCorrelation.size() == 0) {

			return true;

		}

		for (NursingProjectVo ar : valueCorrelation) {

			//该项目是出入量时
			if ((ProjectTypeEnum.OUTPUT_PROJECT.getCode()).equals(ar.getProjectType()) ||
					(ProjectTypeEnum.INTAKE_PROJECT.getCode()).equals(ar.getProjectType())) {
				IntakeOutputRecord intakeOutputRecord = new IntakeOutputRecord();
				intakeOutputRecord.setPatientId(ar.getPatientId());
				intakeOutputRecord.setProjectId(ar.getProjectId());
				intakeOutputRecord.setSourceId(nursingRecord.getNursingRecordId());
				IntakeOutputRecord record = intakeOutputRecordService.selectCorrelationValue(intakeOutputRecord);

				if (record != null) {
					record.setDelFlag(1);
					record.setDelUserId(nursingRecord.getDelUserId());
					record.setDelTime(nursingRecord.getDelTime());
					boolean b = intakeOutputRecordService.delete(record);
					if (!b) {
						return false;
					}
				}
			}
			//修改数据
			ProjectRecord projectRecord = new ProjectRecord();
			BeanUtil.copyProperties(ar, projectRecord);
			projectRecord.setDelFlag(1);
			projectRecord.setDelUserId(nursingRecord.getDelUserId());
			projectRecord.setDelTime(nursingRecord.getDelTime());
			if (!projectRecordService.delete(projectRecord)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 悬浮窗录入
	 *
	 * @param windowsEnterBo
	 * @return
	 */
	@Transactional
	@Override
	public boolean windowsAdd(WindowsEnterBo windowsEnterBo) {

		NursingRecord nursingRecord = new NursingRecord();

		PigxUser user = SecurityUtils.getUser();
		LocalDateTime now = LocalDateTime.now();
		nursingRecord.setCreateTime(now);
		nursingRecord.setCreateUserId(user.getId() + "");
		nursingRecord.setDelFlag(0);
		nursingRecord.setDeptId(user.getDeptId() + "");
		nursingRecord.setNursingRecordId(SnowFlake.getId() + "");
		nursingRecord.setDeptId(user.getDeptId() + "");
		nursingRecord.setPatientId(windowsEnterBo.getPatientId());
		nursingRecord.setRecordContent(windowsEnterBo.getEnterContent());
		nursingRecord.setSource(DataSourceEnum.WINDOWS_ENTER.getCode());
		//判断录入内容是否为空
		if (StringUtils.isBlank(windowsEnterBo.getEnterContent())) {
			return false;
		}

		boolean contains = windowsEnterBo.getEnterContent().contains("=");

		if (contains) {
			String[] split = windowsEnterBo.getEnterContent().split("=");

			String s = windowsEnterBo.getEnterContent().split("=")[0];

			Project project = projectService.selectByName(s);

			if (project != null && split.length!= 2) {
				return false;
			}

		}

		//判断是否存在项目  即是否存在冒号
		List<String> strings = RegularMatchUtils.splitEqual(windowsEnterBo.getEnterContent());
		while (!CollectionUtils.isEmpty(strings)) {
			//当集合不为空时
			Project project = projectService.selectByName(strings.get(0));
			if (project == null) {
				break;
			}
			//新增项目记录
			ProjectRecord projectRecord = new ProjectRecord();
			projectRecord.setProjectId(project.getProjectId());
			projectRecord.setProjectValue(RegularMatchUtils.getDouble(strings.get(1)) + "");
			projectRecord.setProjectSpecificRecord(strings.get(1));
			projectRecord.setCreateTime(LocalDateTime.now());
			projectRecord.setCreateUserId(user.getId() + "");
			projectRecord.setSourceId(nursingRecord.getNursingRecordId());
			projectRecord.setSource(DataSourceEnum.WINDOWS_ENTER.getCode());
			projectRecord.setPatientId(windowsEnterBo.getPatientId());
			projectRecord.setDeptId(user.getDeptId() + "");
			String replace = windowsEnterBo.getEnterContent().replace(strings.get(0)+"=", project.getProjectName()+":");
			nursingRecord.setRecordContent(replace);
			boolean add = projectRecordService.add(projectRecord);
			if (!add) {
				return false;
			}
			//判断项目预警
			boolean b = this.verifyWarm(project, projectRecord);
			if (!b) {
				return false;
			}
			//判断出入量
			boolean b1 = this.verifyIntakeOutput(project, projectRecord, nursingRecord.getNursingRecordId());
			if (!b1) {
				return false;
			}


			break;
		}

		return this.save(nursingRecord);
	}

	/**
	 * 病情护理录入
	 *
	 * @param illnessRecordBoList
	 * @return
	 */
	@Transactional
	@Override
	public boolean illness(List<IllnessRecordBo> illnessRecordBoList) {

		PigxUser user = SecurityUtils.getUser();

		LocalDateTime now = LocalDateTime.now();

		long id = SnowFlake.getId();//护理记录的id

		NursingRecord nursingRecord = new NursingRecord();

		nursingRecord.setCreateTime(now);
		nursingRecord.setCreateUserId(user.getId() + "");
		nursingRecord.setDelFlag(0);
		nursingRecord.setDeptId(user.getDeptId() + "");
		nursingRecord.setNursingRecordId(id + "");
		nursingRecord.setSource(DataSourceEnum.ILLNESS_NURSING.getCode());


		List<String> res = new ArrayList<>();
		//将病情护理项目遍历 逐个新增到项目记录等表中
		illnessRecordBoList.forEach(ar -> {

			if (StringUtils.isEmpty(ar.getProjectState()) || StringUtils.isEmpty(ar.getIllnessRecord())) {
				return;
			}
			nursingRecord.setPatientId(ar.getPatientId());

			Project project = projectService.selectByProjectId(ar.getProjectId());

			if (StringUtils.isEmpty(ar.getIllnessRecord())) {
				return;
			}

			ProjectRecord projectRecord = new ProjectRecord();

			projectRecord.setProjectId(ar.getProjectId());
			projectRecord.setCreateUserId(user.getId() + "");
			projectRecord.setDeptId(user.getDeptId() + "");
			projectRecord.setPatientId(ar.getPatientId());
			projectRecord.setProjectSpecificRecord(ar.getIllnessRecord());
			projectRecord.setProjectValue(RegularMatchUtils.getDouble(ar.getIllnessRecord()));
			projectRecord.setRemarks(ar.getNursingMeasure());
			projectRecord.setSource(DataSourceEnum.ILLNESS_NURSING.getCode());
			projectRecord.setSourceId(id + "");
			//预警验证
			boolean b = verifyWarm(project, projectRecord);

			if (!b) {
				return;
			}
			//项目记录添加
			boolean add = projectRecordService.add(projectRecord);

			if (!add) {
				throw new ApiException(10000,ar.getProjectName()+"的数据添加失败！");
			}
			//出入量判断
			boolean b1 = verifyIntakeOutput(project, projectRecord, id + "");

			if (!b1) {
				throw new ApiException(10000,ar.getProjectName()+"的数据添加失败！");
			}
			//拼接字符串  用于最后形成护理记录
			res.add(project.getProjectName() + "：" + ar.getIllnessRecord() +
					(StringUtils.isEmpty(ar.getNursingMeasure()) ? "" : ("，" + ar.getNursingMeasure())));


		});
		if (CollectionUtils.isEmpty(res)||nursingRecord.getPatientId()==null) {

			throw new ApiException(10000,"病情护理添加失败！数据不可为空");

		}

		String join = StringUtils.join(res, "");

		nursingRecord.setRecordContent(join);

		return this.save(nursingRecord);
	}

	/**
	 * 验证预警
	 *
	 * @param project
	 * @param projectRecord
	 * @return
	 */
	private boolean verifyWarm(Project project, ProjectRecord projectRecord) {

		//存在项目预警
		if (project.getProjectWarmFlag().equals(0)) {
			ProjectWarm projectWarm = new ProjectWarm();
			projectWarm.setWarmContent(projectRecord.getProjectSpecificRecord());
			if (StringUtils.isEmpty(projectRecord.getProjectValue())||"null".equals(projectRecord.getProjectValue())) {
				projectWarm.setWarmValue(RegularMatchUtils.getDouble(projectRecord.getProjectSpecificRecord()));
			} else {
				projectWarm.setWarmValue(projectRecord.getProjectValue());
			}
			projectWarm.setDeptId(projectRecord.getDeptId() + "");
			projectWarm.setProjectId(projectRecord.getProjectId());
			projectWarm.setPatientId(projectRecord.getPatientId());
			projectWarm.setCreateUserId(projectRecord.getCreateUserId());
			projectWarm.setCreateTime(projectRecord.getCreateTime());
			int add = projectWarmService.add(projectWarm);
			if (add == 1) {
				return false;
			}
		}

		return true;

	}

	/**
	 * 验证出入量
	 *
	 * @param project
	 * @param projectRecord
	 * @return
	 */
	private boolean verifyIntakeOutput(Project project, ProjectRecord projectRecord, String nursingRecordId) {

		try {
			//该项目是出入量时
			if ((ProjectTypeEnum.OUTPUT_PROJECT.getCode()).equals(project.getProjectType()) ||
					(ProjectTypeEnum.INTAKE_PROJECT.getCode()).equals(project.getProjectType())) {

				IntakeOutputRecord record = new IntakeOutputRecord();
				//封装数据
				record.setPatientId(projectRecord.getPatientId());
				record.setCreateUserId(projectRecord.getCreateUserId());
				record.setProjectId(project.getProjectId());
				record.setSource(projectRecord.getSource());
				record.setDeptId(projectRecord.getDeptId() + "");
				record.setCauseRemark(projectRecord.getRemarks());
				record.setValueDescription(projectRecord.getProjectSpecificRecord());
				//判断具体是出量还是入量
				if ((ProjectTypeEnum.OUTPUT_PROJECT.getCode()).equals(project.getProjectType())) {
					//为出量时
					record.setIntakeOutputType(IntakeOutputEnum.OUTPUT.getCode());
					if (StringUtils.isEmpty(projectRecord.getProjectValue())) {
						record.setIntakeOutputValue(-RegularMatchUtils.getInteger(projectRecord.getProjectSpecificRecord()));
					} else {
						record.setIntakeOutputValue(-RegularMatchUtils.getInteger(projectRecord.getProjectValue()));
					}

				} else if ((ProjectTypeEnum.INTAKE_PROJECT.getCode()).equals(project.getProjectType())) {
					//为入量时
					record.setIntakeOutputType(IntakeOutputEnum.INTAKE.getCode());
					if (StringUtils.isEmpty(projectRecord.getProjectValue())) {
						record.setIntakeOutputValue(RegularMatchUtils.getInteger(projectRecord.getProjectSpecificRecord()));
					} else {
						record.setIntakeOutputValue(RegularMatchUtils.getInteger(projectRecord.getProjectValue()));
					}				}
				//将数据来源的id 封装
				record.setSourceId(nursingRecordId);
				//执行新增
				boolean add = intakeOutputRecordService.add(record);
				if (!add) {
					throw new ApiException(10000,project.getProjectName()+"添加失败！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 护理记录单三
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public List<NursingReportThreeVo> getReport(NursingBo nursingBo) {

		LocalDateTime endTime = nursingBo.getEndTime();

		int hour = endTime.getHour();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatNow = df.format(now);
		String format = df.format(endTime);

		/**
		 * //首先判断传来的日期是否为当前时间 因为当前时间有着8点前后的区分
		 * 当时间为今天时 1、时间在早上8点前 时间区间为头一天的8点到今天的7点
		 *               2、时间在早上8点后 时间区间为今天天的8点到第二天的7点
		 *
		 *  当时间不是今天时  时间区间为传来时间那天的早上8点到第二天7点
		 */
		if (formatNow.equals(format)) {

			if (hour >= 8) {

				LocalDateTime dateTime = endTime.withHour(8);
				nursingBo.setStartTime(dateTime);

			} else {

				LocalDateTime dateTime = endTime.minusDays(1);
				LocalDateTime dateTime1 = dateTime.withHour(8);
				nursingBo.setStartTime(dateTime1);

			}

		} else {

			LocalDateTime dateTime = endTime.withHour(8);
			nursingBo.setStartTime(dateTime);
			LocalDateTime dateTime1 = endTime.minusDays(-1);
			LocalDateTime dateTime2 = dateTime1.withHour(7);
			nursingBo.setEndTime(dateTime2);

		}
		return baseMapper.getReport(nursingBo);
	}


	/**
	 * 完善护理记录内容
	 *
	 * @param content
	 * @return
	 */
	private String verifyNursingContent(String content) {
		//以分号拆分数据
		List<String> strings = RegularMatchUtils.splitSemicolon(content);

		if (strings.size() < 2) {

			return content;

		}
		StringBuffer msg = new StringBuffer();
		strings.forEach(ar -> {
			//以冒号拆分
			List<String> strings1 = RegularMatchUtils.splitColon(ar);
			if (strings1 == null || strings1.size() != 2) {
				return;
			}
			//项目名
			msg.append(strings1.get(0));
			//以逗号拆分数据
			List<String> strings2 = RegularMatchUtils.splitComma(strings1.get(1));
			//拆分数据失败时，将原数据直接拼接
			if (strings2 == null) {
				msg.append(":" + strings1.get(1) + ";");
			} else {
				//拆分成功 前一数据为空 则将后一数据直接拼接
				if (StringUtils.isEmpty(strings2.get(0))) {
					msg.append(":" + strings2.get(1) + ";");
				} else {
					//直接拼接原数据
					msg.append(":" + strings1.get(1) + ";");
				}
			}
		});

		String s = msg + "";
		if (s.contains(",;")) {
			s=s.replace(",;", ";");
		} else if (s.contains("，;")) {
			s=s.replace("，;", ";");
		}

		return s;
	}

}
