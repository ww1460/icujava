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

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TasksVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务主表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:07:21
 */
public interface TasksService extends IService<Tasks> {


	/**
	 * 新增任务
	 * 新增主任务的同时要生成一条子任务，用于任务模板的快捷选择
	 */
	Boolean add(Tasks task);

	/**
	 * 子任务添加
	 * @param tasks
	 * @return
	 */
	Boolean addSubTask(Tasks tasks);

	/**
	 * 主任务和子任务同时新增【用于医嘱生成任务】
	 * @param taskVo
	 * @return
	 */
	Boolean addTaskVo(TasksVo taskVo);


	/**
	 * 通过雪花id查询当前表中的数据数据
	 * @param id
	 * @return
	 */
	Tasks taskId(String id);

	/**
	 * 通过医嘱id查询任务的数据【只用于查询当前医嘱是否生成任务】
	 * @param id
	 * @return
	 */
	Boolean DoctorsAdvice(String id);


	/**
	 * 通过医嘱执行记录id查询，生成的相关任务
	 * @return
	 */
	List<Tasks> doctorsAdviceExt(String id);

}
