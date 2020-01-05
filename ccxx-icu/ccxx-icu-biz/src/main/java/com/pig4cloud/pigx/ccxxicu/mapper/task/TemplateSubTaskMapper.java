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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateSubTaskVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务模板子模板
 *
 * @author pigx code generator
 * @date 2019-10-07 16:58:17
 */
public interface TemplateSubTaskMapper extends BaseMapper<TemplateSubTask> {

	/**
	 * 任务模板id查询
	 * @param id
	 * @return
	 */
	@Select("select * from tak_template_sub_task WHERE  del_flag =0 and task_template_id = #{id}")
	List<TemplateSubTask> taskTemplateId(@Param("id")String id);

	/**
	 * 通过任务模板id查询子模板任务（查询出来的任务，是有长期任务，模板结束时间大于当前时间的模板任务）
	 * @param id
	 * @return
	 */
	@Select("select * from tak_template_sub_task WHERE  del_flag =0 and \n" +
			"(DATE_FORMAT(pre_end_time,'%H:%i:%S') >= DATE_FORMAT(NOW(),'%H:%i:%S') or interval_time!=0 or pre_end_time is null) and task_template_id = #{id}")
	List<TemplateSubTask> taskTemplateIdTime(@Param("id")String id);


	/**
	 * 来源查询数据
	 * @param id
	 * @return
	 */
	@Select("select * from tak_template_sub_task WHERE  del_flag =0 and source_id = #{id} ")
	TemplateSubTask sourceId(@Param("id") String id);

	/**
	 * 展示自建的任务模板-
	 * @param templateSubTask
	 * @return
	 */
	List<TemplateSubTask> buildByOneself(@Param("templateSubTask") TemplateSubTaskVo templateSubTask);

}
