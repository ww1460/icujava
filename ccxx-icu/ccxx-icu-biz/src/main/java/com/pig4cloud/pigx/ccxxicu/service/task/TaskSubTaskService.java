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

package com.pig4cloud.pigx.ccxxicu.service.task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TaskSubTaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.SubTaskDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskSubTaskVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;
import java.util.Map;

/**
 * 任务子表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:13:52
 */
public interface TaskSubTaskService extends IService<TaskSubTask> {


	/**
	 * 新增
	 * @param taskSubTask
	 * @return
	 */
	Boolean add(TaskSubTask taskSubTask);
	/**
	 * 通过子任务新增【同时新增主任务】
	 * @param taskSubTask
	 * @return
	 */
	Boolean addTask(TaskSubTask taskSubTask);
	/**
	 * 根据主任务id删除数据
	 * @param id
	 * @return
	 */
	Boolean delete (String id);
	/**
	 * 通过主任务id查询
	 * @param id
	 * @return
	 */
	List<TaskSubTask> mainTaskId(String id);
	/**
	 * 点击修改 任务状态
	 * @param
	 * @return
	 */
	Boolean updateState(TaskSubTaskBo taskBo);
	/**
	 * 任务结果需要做的处理
	 * @param taskBo
	 * @return
	 */
	Boolean taskResult(TaskSubTaskBo taskBo);
	/**
	 * 分页查询任务
	 * @param page
	 * @param taskSubTaskVo
	 * @return
	 */
	IPage selectPaging(Page page, TaskSubTaskVo taskSubTaskVo);
	/**
	 * 任务不分页
	 * @param taskSubTask
	 * @return
	 */
	List<TaskSubTaskVo> taskList(TaskSubTaskVo taskSubTask);
	/***
	 * 通过判断当条任务为项目任务，查询当条项目是否结果
	 * @param taskSubTask
	 * @return
	 */
	List<ProjectValueVo> selectProjectValue(TaskSubTask taskSubTask);
	/**
	 * id查询数据
	 * @param id
	 * @return
	 */
	Map<String,Object> getId(Integer id);
	/**
	 * 通过患者id查询当前患者未完成的任务【交接班】
	 * @param id
	 * @return
	 */
	List<TaskSubTask> stopTask(String id);
	/**
	 * 通过子任务的雪花id查询子任务的数据
	 * @param taskSubTaskId
	 * @return
	 */
	TaskSubTask taskSubTaskId(String taskSubTaskId);
	/**
	 * 护理模板新增
	 * @param taskSubTask
	 * @return
	 */
	Boolean nursingScheme(TaskSubTask taskSubTask);
	/**
	 * 【出科时使用，利用患者id查询还在在出科时未完成的任务将其完成】
	 * @param patientId
	 * @return
	 */
	Boolean shiftsEndTask(String patientId);
	/**
	 * 通过医嘱数据,查询当前医嘱所产生的任务
	 * @param hisDoctorsAdviceId
	 * @return
	 */
	List<SubTaskDoctorsAdviceVo> doctorsAdviceSubTask(String hisDoctorsAdviceId);
	/**
	 * 任务委托
	 * @return
	 */
	Boolean taskEntrust(TaskSubTask task);

	/**
	 * 任务拆分
	 * @param list
	 * @return
	 */
	R taskSubTaskSplit(List<TaskSubTask> list);

	/**
	 * 任务委托护士查询
	 * @return
	 */
	R taskEntrustNurse();


	/**
	 * 超时任务
	 * @return
	 */
	List<TaskSubTask>overtimeTask();



}
