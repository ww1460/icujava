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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateSubTaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateSubTaskVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TemplateSubTaskMapper;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.task.TasksService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateSubTaskService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务模板子模板
 *
 * @author pigx code generator
 * @date 2019-10-07 16:58:17
 */
@Service
public class TemplateSubTaskServiceImpl extends ServiceImpl<TemplateSubTaskMapper, TemplateSubTask> implements TemplateSubTaskService {

	@Autowired
	private TemplateSubTaskMapper templateSubTaskMapper;

	/**
	 * 主任务
	 */
	@Autowired
	private TasksService tasksService;

	/**
	 * 项目结果
	 */
	@Autowired
	private ProjectRecordValueService projectRecordValueService;












	/**
	 * 任务模板查询
	 * @param id
	 * @return
	 */
	@Override
	public List<TemplateSubTask> taskTemplateId(String id) {
		return templateSubTaskMapper.taskTemplateId(id);
	}

	/**
	 * 通过任务模板id查询子模板任务（查询出来的任务，是有长期任务，模板结束时间大于当前时间的模板任务）
	 * @param id
	 * @return
	 */
	@Override
	public List<TemplateSubTask> taskTemplateIdTime(String id) {
		return templateSubTaskMapper.taskTemplateIdTime(id);
	}

	/**
	 * 多数据删除
	 * @param list
	 * @return
	 */
	@Override
	public Boolean deletes(List<TemplateSubTask> list) {
		if (CollectionUtils.isNotEmpty(list)){
			list.forEach(e->{
				e.setDelFlag(1);
				e.setDelUserId(SecurityUtils.getUser().getId()+"");//删除用户
				e.setDelTime(LocalDateTime.now());//删除时间
			});
			return this.updateBatchById(list);
		}else{
			return true;
		}


	}

	/**
	 * 来源id查询
	 * @param id
	 * @return
	 */
	@Override
	public TemplateSubTask sourceId(String id) {
		return templateSubTaskMapper.sourceId(id);
	}

	/**
	 *
	 * 	  展示快捷的任务模板
	 * 	  @param templateSubTask
	 * 	  @return
	 *          */
	@Override
	public List<TemplateSubTask> buildByOneself(TemplateSubTaskVo templateSubTask) {

		if("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){ //护士长
			templateSubTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
			return templateSubTaskMapper.buildByOneself(templateSubTask);
		}if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){//护士
			templateSubTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
			templateSubTask.setLoginNurse(SecurityUtils.getUser().getId()+""); //登录护士
			return templateSubTaskMapper.buildByOneself(templateSubTask);
		}
		return templateSubTaskMapper.buildByOneself(templateSubTask);
	}


	/**
	 * 任务模板新增任务
	 * @param templateSubTask
	 * @return
	 */
	@Override
	public Boolean addTask(TemplateSubTaskBo templateSubTask) {

		String nurse  = SecurityUtils.getUser().getId()+"";

		Tasks task = new Tasks();
		task.setTaskId(SnowFlake.getId()+"");//雪花id
		/*任务类型*/

		if (templateSubTask.getSource()== TaskEnum.TEMPLATE_BUILD_BY_ONESELF.getNumber()){//当前任务模板的来源为自建时
			// 模板为自建时，
			task.setTaskType(TaskEnum.TEMPLATE_PROJECT.getNumber());//模板自建
			task.setTaskTypeId(templateSubTask.getTaskTemplateId());

		}if(templateSubTask.getSource()==TaskEnum.TEMPLATE_PROJECT.getNumber()){//当前任务模板的来源为项目是】时
			task.setTaskType(TaskEnum.TEMPLATE_PROJECT.getNumber());
			task.setTaskTypeId(templateSubTask.getTaskTemplateId()+"-"+templateSubTask.getSourceId());
		}if (templateSubTask.getSource()==TaskEnum.FAST_PROJECT.getNumber()){//当前模板任务是快捷项目时
			task.setTaskType(TaskEnum.FAST_PROJECT.getNumber());
			task.setTaskTypeId(templateSubTask.getTaskTemplateId()+"-"+templateSubTask.getSourceId());
		}if (templateSubTask.getSource()==TaskEnum.FAST_BUILD_BY_ONESELF.getNumber()){//当前模板为快捷自建时
			task.setTaskType(TaskEnum.FAST_BUILD_BY_ONESELF.getNumber());
			task.setTaskTypeId(templateSubTask.getTaskTemplateId());

		}
		task.setTaskName(templateSubTask.getTaskContent());//内容
		task.setTaskDescribe(templateSubTask.getTaskDescribe());//描述
		task.setPatientId(templateSubTask.getPatientId());//患者
		task.setCreatorId(nurse);
		task.setExecutingNum(1);//总执行条数
		task.setPreEndTime(LocalDateTime.now());//预结束时间
		task.setPreStartTime(LocalDateTime.now());//预开始时间
		task.setStartTime(LocalDateTime.now());//开始时间
		task.setEndTime(LocalDateTime.now());//结束时间
		task.setReceptionNurseId(nurse); //接收护士
		task.setCompletedNurseId(nurse);//结束护士
		task.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
		task.setState(TaskEnum.COMPLETE.getNumber());//状态
		task.setSubTaskNum(1);//子任务条数
		task.setExecutionType(templateSubTask.getExecutionType());
		task.setCreateTime(LocalDateTime.now());//创建 时间
		task.setProgressBar(100); // 完成
		task.setResult(templateSubTask.getResult());//结果
		Boolean add = tasksService.addSubTask(task);
		if (add){
			return  true;
		}else{
			return false;
		}

	}


	/**
	 * 通过判断任务模板，查询当条任务模板是否有固定结果     任务模板的来源 是否来源于项目中
	 *
	 * @param templateSubTask
	 * @return
	 */
	@Override
	public R selectTemplateProjectValue(TemplateSubTask templateSubTask) {


		Project project = new Project();
		/* templateSubTask */
		if (templateSubTask.getSource()==TaskEnum.PROJECT.getNumber()||//为项目时
				templateSubTask.getSource()==TaskEnum.TEMPLATE_PROJECT.getNumber()||//为模板项目时
				templateSubTask.getSource()==TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()||//为医嘱项目时
				templateSubTask.getSource()==TaskEnum.FAST_PROJECT.getNumber()){//为快捷项目时

			/*  将任务模板中的来源id作为数据查询*/
			project.setProjectId(templateSubTask.getSourceId());
			/* 查询相应 的结果 */
			List<ProjectValueVo> projectValue = projectRecordValueService.selectProjectValue(project);
			/*有结果时  返回结果  没有时返回nill*/
			if (CollectionUtils.isNotEmpty(projectValue)) {
				return R.ok(projectValue, "1");
			} else {
				return R.ok(null, "0");
			}
		} else {
			return R.ok(null, "0");
		}
	}





}
