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
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TasksVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TasksMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingRecordService;
import com.pig4cloud.pigx.ccxxicu.service.scenes.WatchOpenService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TasksService;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务主表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:07:21
 */
@Service
public class TasksServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements TasksService {



	@Autowired
	private TasksMapper tasksMapper;

	/**
	 * 子任务
	 */

	@Autowired
	private TaskSubTaskService taskSubTaskService;

	/**
	 * 护士患者关联
	 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	//腕表推送
	@Autowired
	private WatchOpenService watchOpenService;

	//
	@Autowired
	private NursingRecordService nursingRecordService;
	/**
	 * 主任务和子任务同时新增【用于医嘱生成任务】
	 * @param taskVo
	 * @return
	 */
	@Override
	public Boolean addTaskVo(TasksVo taskVo) {

		System.out.println("接到的数据有多少"+taskVo.getTask()+taskVo.getSubTask());

		/* 主任务新增*/
		boolean save = this.save(taskVo.getTask());
		if (!save){
			return false;
		}
		/**
		 * 子任务新增
		 */
		boolean b = taskSubTaskService.saveBatch(taskVo.getSubTask());
		if (!b){
			return false;
		}


		taskVo.getSubTask().forEach(e->{
			boolean b1 = watchOpenService.addTaskToWatch(e);
			if (!b1){
				System.out.println("推送腕表失败！！！");
			}
		});


		return true;
	}

	/**
	 * 新增任务
	 * 新增主任务的同时要生成一条子任务，用于任务模板的快捷选择
	 * @param task
	 * @return
	 */
	@Transactional
	@Override
	public Boolean add(Tasks task) {

		/* 主任务默认参数 */
		String dept =SecurityUtils.getUser().getDeptId()+""; //科室
		String creatotId = SecurityUtils.getUser().getId()+"";//创建人
		LocalDateTime time = LocalDateTime.now(); //时间
		String nurseId = nursePatientCorrelationService.selectDutyNurseId(task.getPatientId());//责任护士id
		String id = SnowFlake.getId()+"";
		task.setTaskId(id);//雪花 id
		task.setDeptId(dept);//科室id
		task.setDelFlag(0);//删除标识
		task.setCreatorId(creatotId);//创建人
		task.setCreateTime(time);//创建时间
		task.setTaskRelation(TaskEnum.TASK_MAIN.getNumber());//主任务
		task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 任务状态为待执行
		task.setDutyNurseId(nurseId);//责任护士
		task.setReceiveTime(time); //创建时间
		task.setProgressBar(0);//进度条
		 //判断一下创建任务的是护士吗？
		if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {//护士
		//是的情况将接受护士变为创建护士
			task.setReceptionNurseId(creatotId);
		}else{
		//反之为责任护士
			task.setReceptionNurseId(nurseId);
		}
		boolean save = this.save(task);
		if (!save){
			return false;
		}
		 //  添加子任务
		TaskSubTask taskSubTask = new TaskSubTask();
		taskSubTask.setTaskName(task.getTaskName());//任务名称
		taskSubTask.setTaskDescribe(task.getTaskDescribe());//任务内容
		taskSubTask.setPatientId(task.getPatientId());//患者
		taskSubTask.setPreEndTime(task.getPreEndTime());//预开始时间
		taskSubTask.setPreEndTime(task.getEndTime());//结束时间
		taskSubTask.setMainTaskId(id);//主任务id
		taskSubTask.setExecutionType(task.getExecutionType());//

		boolean b1 = watchOpenService.addTaskToWatch(taskSubTask);
		if (!b1){
			System.out.println("推送腕表失败！！！");
		}
		return taskSubTaskService.add(taskSubTask);
	}

	/**
	 * 通过雪花id查询当前表中的数据数据
	 * @param id
	 * @return
	 */
	@Override
	public Tasks taskId(String id) {
		return tasksMapper.taskId(id);
	}

	/*
	通过医嘱id查询任务的数据【只用于查询当前医嘱是否生成任务】
	 */
	@Override
	public Boolean DoctorsAdvice(String id) {
		/*医嘱*/
		List<Tasks> tasks = tasksMapper.doctorsAdvice(id);
		if (CollectionUtils.isEmpty(tasks)){
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 子任务添加
	 * @param task
	 * @return
	 */
	@Override
	public Boolean addSubTask(Tasks task) {
		String nurseId = nursePatientCorrelationService.selectDutyNurseId(task.getPatientId());//责任护士id
		task.setDutyNurseId(nurseId);
		TaskSubTask taskSubTask = new TaskSubTask();
		taskSubTask.setTaskSubTaskId(SnowFlake.getId()+"");//雪花
		BeanUtil.copyProperties(task,taskSubTask);
		taskSubTask.setMainTaskId(task.getTaskId());//主任务id

		boolean save = taskSubTaskService.save(taskSubTask);
		/*判断当前任务是否为为项目*/
		if (taskSubTask.getTaskType()==TaskEnum.FAST_PROJECT.getNumber()||taskSubTask.getTaskType()==TaskEnum.TEMPLATE_PROJECT.getNumber()||taskSubTask.getTaskType()==TaskEnum.PROJECT.getNumber()||taskSubTask.getTaskType()==TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()){//判断当前任务为快捷项目时
			boolean b = nursingRecordService.taskAdd(taskSubTask);
			if (b==false){
				return false;
			}
		}

		if (save){
			boolean b1 = watchOpenService.addTaskToWatch(taskSubTask);
			if (!b1){
				System.out.println("推送腕表失败！！！");
			}
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 通过医嘱执行记录id查询，生成的相关任务
	 * @param id
	 * @return
	 */
	@Override
	public List<Tasks> doctorsAdviceExt(String id) {
		return tasksMapper.doctorsAdviceExt(id);
	}
}
