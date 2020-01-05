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

package com.pig4cloud.pigx.ccxxicu.mapper.task;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务表
 *
 * @author pigx code generator
 * @date 2019-08-13 16:30:28
 */
public interface TaskMapper extends BaseMapper<Task> {

	/**
	 * 分页查询任务
	 * @param page
	 * @param task
	 * @return
	 */
	IPage<List<TaskVo>> selectPaging (Page page, @Param("task")TaskVo task);

	/**
	 * 全查数据
	 * @param task
	 * @return
	 */
	List<TaskVo> selectAll(@Param("task")TaskVo task);

	/**
	 * id查询数据
	 * @param id
	 * @return
	 */
	TaskVo selectId(@Param("id")Integer id);


	/**
	 *雪花id查询
	 * @param taskId
	 * @return
	 */
	TaskVo getTaskId(@Param("taskId")String taskId);

	/**
	 * 超时任务
	 * @return
	 */
	List<Task>overtimeTask();


	/**
	 * 通过项目id查询对应的数据 （医嘱）
	 * @param sourceItemsId
	 * @return
	 */
	List<Task>getsSourceItemsId(@Param("sourceItemsId") String sourceItemsId);

	/**
	 * 根据病人ID获取对应的任务ID列表内容
	 * @param patientId
	 * @return
	 */
	List<Task> getAdviceTaskByPatientId(@Param("patientId") String patientId);

	Task getTaskByTaskId(@Param("taskId") String taskId);

	/**
	 * 通过患者id查询相关的任务
	 * @param patientId
	 * @return
	 */
	List<Task> stopTask(@Param("patientId") String patientId);

	/**
	 * 获取在线任务数
	 * @return
	 */
	List<Task> getOnlineTask();

	/**
	 * 获取今日创建任务数
	 * @return
	 */
	Integer getTodayCreateTask();

	/**
	 * 获取今日创建任务完成数
	 * @return
	 */
	Integer getTodayCreateOverTask();

}