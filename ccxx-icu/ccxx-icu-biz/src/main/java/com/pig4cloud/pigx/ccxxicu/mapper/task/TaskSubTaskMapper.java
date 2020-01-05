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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.SubTaskDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskSubTaskVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务子表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:13:52
 */
public interface TaskSubTaskMapper extends BaseMapper<TaskSubTask> {


	/**
	 * 通过主任务id查询
	 * @param id
	 * @return
	 */
	@Select("SELECT * from tak_task_sub_task WHERE main_task_id = #{id}")
	List<TaskSubTask> mainTaskId(@Param("id") String id);

	/**
	 * 分页查询任务
	 * @param page
	 * @param taskSubTask
	 * @return
	 */
	IPage<List<TaskSubTaskVo>> selectPaging(Page page, @Param("task") TaskSubTaskVo taskSubTask);

	/**
	 * 任务不分页
	 * @param taskSubTask
	 * @return
	 */
	List<TaskSubTaskVo> taskList(@Param("task") TaskSubTaskVo taskSubTask);


	/**
	 * id查询数据
	 * @param id
	 * @return
	 */
	TaskSubTaskVo getId(@Param("id")Integer id);

	/**
	 * 通过患者id查询当前患者未完成的任务【交接班】
	 * @param id
	 * @return
	 */
	List<TaskSubTask> stopTask(@Param("id")String id);

	/**
	 * 通过子任务的雪花id查询子任务的数据
	 * @param taskSubTaskId
	 * @return
	 */
	@Select("SELECT * from tak_task_sub_task WHERE task_sub_task_id = #{taskSubTaskId}")
	TaskSubTask taskSubTaskId(@Param("taskSubTaskId") String taskSubTaskId);

	/**
	 * 通过医嘱数据,查询当前医嘱所产生的任务
	 * @param hisDoctorsAdviceId
	 * @return
	 */
	List<SubTaskDoctorsAdviceVo> doctorsAdviceSubTask(@Param("hisDoctorsAdviceId")String hisDoctorsAdviceId);


	/**
	 * 根据病人ID获取对应的任务ID列表内容
	 * @param patientId
	 * @return
	 */
	List<TaskSubTask> getAdviceTaskByPatientId(@Param("patientId") String patientId);

	/**
	 * 查询超时为未出的任务
	 * @return
	 */
	List<TaskSubTask> overtimeTask();

}
