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
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 任务表
 *
 * @author pigx code generator
 * @date 2019-08-13 16:30:28
 */
public interface TaskService extends IService<Task> {

	/**
	 * 分页查询任务
	 * @param page
	 * @param task
	 * @return
	 */
	IPage selectPaging(Page page,TaskVo task);



	/**
	 * 全查数据
	 * @param task
	 * @return
	 */
	List<TaskVo> selectAll(TaskVo task);

	/**
	 * id查询数据
	 * @param id
	 * @return
	 */
	TaskVo selectId(Integer id);

	/**
	 * id查询数据 【任务详情】
	 * @param taskId
	 * @return
	 */
	R taskId(String taskId);

	/**
	 *雪花id查询
	 * @param taskId
	 * @return
	 */
	TaskVo getTaskId(String taskId);

	/**
	 * 超时任务
	 * @return
	 */
	List<Task>overtimeTask();

	/**
	 * 任务多个新增
	 * @param list
	 * @return
	 */
	R inserts(List<Task> list);

	/**
	 * 点击修改 任务状态
	 * @param task
	 * @return
	 */
	R updateState(TaskBo task);

	/**
	 * 点击任务模板接收时
	 * @param task
	 * @return
	 */
	R insertTemplate(TaskBo task);

	/**
	 * 添加任务
	 * @param task
	 * @return
	 */
	Boolean taskAdd(Task task);


	/**
	 * 通过项目id查询对应的数据 （医嘱）
	 * @param sourceItemsId
	 * @return
	 */
	List<Task>getsSourceItemsId(String sourceItemsId);

	/**
	 * 任务委托护士查询
	 * @return
	 */
	R taskEntrustNurse();


	/**
	 * 任务委托
	 * @return
	 */
	Boolean taskEntrust(Task task);

	/***
	 * 通过判断当条任务为项目任务，查询当条项目是否结果
	 * @param task
	 * @return
	 */
	List<ProjectValueVo> selectProjectValue(Task task);

	/**
	 * 通过患者id 查询相关任务，停止任务
	 * @param patientId
	 * @return
	 */
	Boolean stopTask(String patientId);

	/**
	 * 任务添加护理记录单中
	 * @param taskBo
	 * @return
	 */
	Boolean taskAddNursingRecord(TaskBo taskBo);

	/**
	 * 任务添加到出入量中
	 * @param taskBo
	 * @return
	 */
	Boolean taskAddIntakeOutput(TaskBo taskBo);

	/**
	 * 任务添加到项目中
	 * @param taskBo
	 * @return
	 */
	Boolean taskAddProject(TaskBo taskBo);

	/**
	 * 任务结果需要做的处理
	 * @param taskBo
	 * @return
	 */
	Boolean taskResult(TaskBo taskBo);

}
