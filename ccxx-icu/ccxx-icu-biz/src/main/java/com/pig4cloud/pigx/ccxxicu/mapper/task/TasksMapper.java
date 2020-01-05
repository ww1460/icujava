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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务主表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:07:21
 */
public interface TasksMapper extends BaseMapper<Tasks> {

	/**
	 * 通过雪花id删除数据
	 * @param id
	 * @return
	 */
	@Select("delete from tak_tasks where task_id = #{id}")
	Boolean taskIdDelect(@Param("id")String id);

	/**
	 * 通过雪花id查询当前表中的数据数据
	 * @param id
	 * @return
	 */
	@Select("select * from tak_tasks where task_id = #{id}")
	Tasks taskId(@Param("id")String id);

	/**
	 * 用医嘱id当前的医嘱是不是有生成过任务
	 * @param id
	 * @return
	 */
	@Select("SELECT  * from tak_tasks  WHERE (task_type = 5 or task_type = 6 or task_type = 7) and  task_type_id = #{id}")
	List<Tasks> doctorsAdvice(@Param("id")String id);

	/**
	 * 通过医嘱执行记录id查询，生成的相关任务
	 * @param id
	 * @return
	 */
	@Select("SELECT id,task_id from  tak_tasks WHERE task_type = 5 and task_type_id = #{id}")
	List<Tasks> doctorsAdviceExt(@Param("id")String id);
}
