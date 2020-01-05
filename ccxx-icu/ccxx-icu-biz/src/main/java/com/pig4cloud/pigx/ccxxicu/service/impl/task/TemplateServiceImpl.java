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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.TemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.*;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TemplateEnum;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TemplateMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.scenes.WatchOpenService;
import com.pig4cloud.pigx.ccxxicu.service.task.*;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务模板
 *
 * @author pigx code generator
 * @date 2019-08-15 10:09:38
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

	@Autowired
	private TemplateMapper templateMapper;
	/**
	 * 任务子模板
	 */
	@Autowired
	private TemplateSubTaskService templateSubTaskService;

	/**
	 * 长期任务
	 */
	@Autowired
	private TimingExecutionService timingExecutionService;

	/**
	 * 主任务
	 */
	@Autowired
	private TasksService tasksService;
	/**
	 * 子任务模板
	 */
	@Autowired
	private TaskSubTaskService taskSubTaskService;

/**
 * 护士患者关联
 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 腕表
	 * @param template
	 * @return
	 */
	@Autowired
	private WatchOpenService watchOpenService;



	/**
	 * 任务模板全查
	 *
	 * @param template
	 * @return
	 */
	@Override
	public List<Template> selectAll(TemplateVo template) {
		return templateMapper.selectAll(template);
	}

	/**
	 * 雪花id查询
	 *
	 * @param templateId
	 * @return
	 */
	@Override
	public Template getTemplateId(String templateId) {
		return templateMapper.getTemplateId(templateId);
	}

	/**
	 * 任务模板分页查询
	 *
	 * @param page
	 * @param template
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, TemplateVo template) {
		return templateMapper.selectPaging(page, template);
	}


	/**
	 * 新增
	 *
	 * @param templateBo
	 * @return
	 */
	@Override
	public Boolean add(TemplateBo templateBo) {

		Template template = new Template();
		BeanUtil.copyProperties(templateBo, template);
		String dept = SecurityUtils.getUser().getDeptId() + "";
		template.setDeptId(dept); /// 科室
		String id = SnowFlake.getId() + "";
		template.setTemplateId(id);  //雪花id查询
		template.setCreateUserId(SecurityUtils.getUser().getId() + "");  //创建人
		template.setCreateTime(LocalDateTime.now());  // 创建时间
		List<TemplateSubTask> templateSubTasks = templateBo.getTemplateSubTasks();
		//任务子模板
		if (CollectionUtils.isEmpty(templateSubTasks)) {

			return false;

		}

		template.setSubTaskNum(templateSubTasks.size());

		//添加子模板
		templateSubTasks.forEach(ar -> {
			//添加主模板的id
			ar.setTaskTemplateId(template.getTemplateId());
			ar.setCreateTime(LocalDateTime.now());//创建时间
			ar.setCreateUserId(SecurityUtils.getUser().getId() + "");//删除护士
			ar.setTemplateSubTaskId(SnowFlake.getId() + ""); // 新增
			templateSubTaskService.save(ar);
		});

		boolean save = this.save(template);

		if (!save) {

			return false;

		} else {

			return true;

		}

	}



	/**
	 * 来源id查询
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Template sourceId(String id) {
		return templateMapper.sourceId(id);
	}


	/**
	 * 查询有没有快捷主任务模板
	 * @return
	 */
	@Override
	public Boolean source() {
		Template template = new Template();
		template.setSource(TemplateEnum.FAST.getCode());
		template.setDeptId(SecurityUtils.getUser().getDeptId()+"");//当前护士id的登录科室
		List<Template> source = templateMapper.source(template);
		if (CollectionUtils.isNotEmpty(source)&&source.size()==1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 任务模板新增任务【用药任务调用任务模板新增】
	 * @param template
	 * @return
	 */
	@Override
	public Boolean templateAddTask(com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateBo template) {

		//责任护士
		String nurse = "";
		if (StringUtils.isNotEmpty(template.getPatientId())){
			nurse =nursePatientCorrelationService.selectDutyNurseId(template.getPatientId());
		}else{
			throw new ApiException(ResponseCode.TASK_PATIENT.getCode(),ResponseCode.TASK_PATIENT.getMessage());
		}

		List<TaskSubTask> taskSubTaskList = new ArrayList<>();

		/* 通过主模板的id查询当前主模板下当前时间下有多少可以自动模板 */
		List<TemplateSubTask> templateSubTasks = templateSubTaskService.taskTemplateIdTime(template.getTemplateId());
		/* 判断查询出来的模板内容不为空时*/
		if (CollectionUtils.isEmpty(templateSubTasks)){
			throw new ApiException(ResponseCode.TEMPLATE__NOT_SUB.getCode(),ResponseCode.TEMPLATE__NOT_SUB.getMessage());
		}

			/* 通过护士id查询责任护士 */

			// 利用主任务模板创建主任务。\2
			Tasks task = new Tasks();
			String taskId = SnowFlake.getId()+""; //主任务id
			task.setTaskId(taskId);
			task.setTaskType(TaskEnum.TEMPLATE.getNumber());//来源为模板
			task.setTaskTypeId(template.getTemplateId());
			task.setTaskName(template.getTemplateContent());//任务名称
			task.setTaskDescribe(template.getTemplateDescribe());//任务描述
			task.setPatientId(template.getPatientId());
			task.setDutyNurseId(nurse);//责任护士
			task.setCreatorId(SecurityUtils.getUser().getId()+"");//护士id
			task.setCreateTime(LocalDateTime.now());
			task.setEmergency(TaskEnum.TASK_COMMONLY.getNumber());
			task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//任务状态待执行
			task.setSubTaskNum(0);
			task.setExecutingNum(0);
			task.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室

			// 判断子任务模板中是否有间隔时间，如果有创建长期任务，反之生成任务
			// 同时判断子任务的来源，判断当前子模板是自建 还是项目模板

			for (int i= 0;i<templateSubTasks.size();i++){
				/* 判断当前任务是否有间隔时间，如果 */
				System.out.println(templateSubTasks.get(i));
				if (templateSubTasks.get(i).getIntervalTime()!=0&&templateSubTasks.get(i).getIntervalTime()!=null){  //间隔时间不为空时，创建长期任务
			/**
			 * 长期任务操作
			 */
					TimingExecution timingExecution = new TimingExecution();
					timingExecution.setDelFlag(0);//删除0标识

					if (templateSubTasks.get(i).getSource()==TaskEnum.TEMPLATE_PROJECT.getNumber()){  //为模板项目时
						timingExecution.setSource(TaskEnum.TEMPLATE_PROJECT.getNumber());
						timingExecution.setSourceId(templateSubTasks.get(i).getTaskTemplateId()+templateSubTasks.get(i).getSourceId());
					}if (templateSubTasks.get(i).getSource()==TaskEnum.TEMPLATE_BUILD_BY_ONESELF.getNumber()){ //模板自建时
						timingExecution.setSource(TaskEnum.TEMPLATE_BUILD_BY_ONESELF.getNumber());
						timingExecution.setSourceId(templateSubTasks.get(i).getTaskTemplateId());
					}

					//判断当模板任务是否有预结束时间，如果没有的情况将判断主模板是否有开始结束时间，如果两者都没有的情况结束时间设定为当前时间
					if (templateSubTasks.get(i).getPreEndTime()==null){//当前模板任务的预结束时间为空时

						if (template.getStartTime()!=null&&template.getEndTime()!=null){//当任务模板中的开始和结束时间不为空时
							timingExecution.setPreStartTime(template.getStartTime());//预开始时间
							timingExecution.setNextExecutionTime(template.getStartTime());//下次执行执行为预开始时间
							timingExecution.setPreEndTime(template.getEndTime());//与结束时间设定为当前前时间后1小时
						}else{//任务模板的开始、结束时间为空时时
							  //判断模板模板中的开始时间中的开始时间是不为空
							if (templateSubTasks.get(i).getPreStartTime()!=null){  //开始时间
								LocalDateTime startTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreStartTime());
								timingExecution.setPreStartTime(startTime);//预开始时间
								timingExecution.setNextExecutionTime(startTime);//下次执行执行为预开始时间
							}else{//任务模板的开始时间为空时时
								timingExecution.setPreStartTime(LocalDateTime.now());//预开始时间
								timingExecution.setNextExecutionTime(LocalDateTime.now());//下次执行执行为预开始时间
								timingExecution.setPreEndTime(LocalDateTime.now().plusHours(1));//与结束时间设定为当前前时间后1小时

							}
						}

					}else{
						if (templateSubTasks.get(i).getPreStartTime()!=null){  //开始时间
							LocalDateTime startTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreStartTime());
							timingExecution.setPreStartTime(startTime);//预开始时间
							timingExecution.setNextExecutionTime(startTime);//下次执行执行为预开始时间
						}else{//当模板中的预开始时间为空时，设定当前时间为预开始时间
							timingExecution.setPreStartTime(LocalDateTime.now());//预开始时间
							timingExecution.setNextExecutionTime(LocalDateTime.now());//下次执行执行为预开始时间
						}

						if (templateSubTasks.get(i).getPreEndTime()!=null){//结束时间
							LocalDateTime entTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreEndTime());
							timingExecution.setPreStartTime(entTime);//预开始时间
							//  判断结束时间是否比当前时间之后
							LocalDateTime now = LocalDateTime.now();
							boolean before = entTime.isAfter(now);
							if (before){ //为真时，回填结束时间
								timingExecution.setPreEndTime(entTime);//预结束时间
							}else{//为加上证明结束时间比当前时间小，那么次长期任务不执行数据，将开始和结束时间修改到第二天
								LocalDateTime startTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreEndTime());
								timingExecution.setPreStartTime(startTime.plusDays(1));//预开始时间
								timingExecution.setNextExecutionTime(startTime.plusDays(1));//下次执行执行为预开始时间
								timingExecution.setPreEndTime(entTime.plusDays(1));//预结束时间
							}

						}else{//当模板的预结束时间为空时，设定预结束时间为一小时之后
							timingExecution.setPreEndTime(LocalDateTime.now().plusHours(1));//预结束时间
						}
					}
					timingExecution.setTaskName(templateSubTasks.get(i).getTaskContent());//任务名称
					timingExecution.setTaskDescribe(templateSubTasks.get(i).getTaskDescribe());//任务描述
					timingExecution.setIntervalTimes(templateSubTasks.get(i).getIntervalTime());//间隔时间
					timingExecution.setPatientId(template.getPatientId());//患者id
					timingExecution.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
					timingExecution.setCreateTime(LocalDateTime.now());//创建时间
					timingExecution.setExecutionType(templateSubTasks.get(i).getExecutionType());//任务执行类型

					boolean save = timingExecutionService.save(timingExecution);
					if (!save){
						throw new ApiException(ResponseCode.TIMING_ADD_FAIL.getCode(),ResponseCode.TASK_PATIENT.getMessage());
					}
					System.out.println("长期任务----------");


				}else{//间隔时间为空时，创建普通任务

					TaskSubTask subTask = new TaskSubTask();
					subTask.setTaskSubTaskId(SnowFlake.getId()+"");//雪花
					/*  判断一下当前医嘱的任务模板，是否有来源id，如果有情况下情*/
					if (templateSubTasks.get(i).getSource()==TaskEnum.TEMPLATE_PROJECT.getNumber()){  //为模板项目时
						subTask.setTaskType(TaskEnum.TEMPLATE_PROJECT.getNumber());
						subTask.setTaskTypeId(templateSubTasks.get(i).getTaskTemplateId()+templateSubTasks.get(i).getSourceId());
					}if (templateSubTasks.get(i).getSource()==TaskEnum.TEMPLATE_BUILD_BY_ONESELF.getNumber()){ //模板自建时
						subTask.setTaskType(TaskEnum.TEMPLATE_BUILD_BY_ONESELF.getNumber());
						subTask.setTaskTypeId(templateSubTasks.get(i).getTaskTemplateId());
					}
					subTask.setTaskName(templateSubTasks.get(i).getTaskContent());//任务名称
					subTask.setTaskDescribe(templateSubTasks.get(i).getTaskDescribe());//任务内容
					subTask.setPatientId(template.getPatientId());//患者id
					subTask.setDutyNurseId(nurse);//责任护士

					if (templateSubTasks.get(i).getPreEndTime()==null){//当前模板任务的预结束时间为空时

						if (template.getStartTime()!=null&&template.getEndTime()!=null){//当任务模板中的开始和结束时间不为空时
							subTask.setPreStartTime(template.getStartTime());//预开始时间
							subTask.setPreEndTime(template.getEndTime());//与结束时间设定为当前前时间后1小时
						}else{//任务模板的开始、结束时间为空时时
							//判断模板模板中的开始时间中的开始时间是不为空
							if (templateSubTasks.get(i).getPreStartTime()!=null){  //开始时间
								LocalDateTime startTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreStartTime());
								subTask.setPreStartTime(startTime);//预开始时间
							}else{//任务模板的开始时间为空时时
								subTask.setPreStartTime(LocalDateTime.now());//预开始时间
								subTask.setPreEndTime(LocalDateTime.now().plusHours(1));//与结束时间设定为当前前时间后1小时
							}
						}

					}else{
						if (templateSubTasks.get(i).getPreStartTime()!=null){//预开始时间不为空
							LocalDateTime startTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreStartTime());
							subTask.setPreStartTime(startTime);//预开始时间
						}else{//模板中的预开始时间为空时
							subTask.setPreStartTime(LocalDateTime.now());//预开始时间
						}

						if (templateSubTasks.get(i).getPreEndTime()!=null){
							LocalDateTime entTime = timingExecutionService.dateTime(templateSubTasks.get(i).getPreEndTime());
							subTask.setPreEndTime(entTime);//预结束时间
						}else{//没有预结束时间
							subTask.setPreEndTime(LocalDateTime.now().plusHours(1));//预结束时间
						}

					}


					subTask.setCreateTime(LocalDateTime.now());//创建时间
					subTask.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//待执行状态
					subTask.setExecutionType(templateSubTasks.get(i).getExecutionType());//执行类型
					subTask.setMainTaskId(taskId);//主任务id
					subTask.setTaskRelation(TaskEnum.TASK_SON.getNumber());//子任务标识
					subTask.setProgressBar(0);//进度条
					subTask.setReceptionNurseId(task.getReceptionNurseId());//与接收护士
					subTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
					subTask.setDelFlag(0);//删除

					// 相关主任务子任务+1
					task.setSubTaskNum(task.getSubTaskNum()+1);
					/* 当数据填充完成后，将数据回填到list集合 */
					taskSubTaskList.add(subTask);
					boolean b = watchOpenService.addTaskToWatch(subTask);
					if (!b){
						System.out.println("推送腕表失败！！！");
					}
				}

			}


			Boolean add = tasksService.save(task);
			if (!add){
				throw new ApiException(ResponseCode.TASK_ADD_FAIL.getCode(),ResponseCode.TASK_ADD_FAIL.getMessage());
			}
			if (CollectionUtils.isNotEmpty(taskSubTaskList)){
				boolean b = taskSubTaskService.saveOrUpdateBatch(taskSubTaskList);

				if (b){
					return true;
				}else{
					throw new ApiException(ResponseCode.TASK_ADD_SUB_FAIL.getCode(),ResponseCode.TASK_ADD_SUB_FAIL.getMessage());
				}

			}else{
				return true;
			}




	}


	/**
	 * 通过任务模板的类型查询数据
	 * @param source
	 * @return
	 */
	@Override
	public List<Template> sourceType(Integer source) {
		Template template = new Template();
		template.setSource(source);
		return templateMapper.source(template);
	}

	/**
	 * 查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】
	 * @param template
	 * @return
	 */
	@Override
	public List<Template> selectTaskPreJump(TemplateVo template) {
		return templateMapper.selectTaskPreJump(template);
	}
}
