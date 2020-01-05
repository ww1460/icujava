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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务模板
 *
 * @author pigx code generator
 * @date 2019-08-15 10:09:38
 */
public interface TemplateMapper extends BaseMapper<Template> {


	/**
	 * 任务模板全查
	 * @param template
	 * @return
	 */
	List<Template> selectAll(@Param("template") TemplateVo template);

	/**
	 * 雪花id查询
	 * @param templateId
	 * @return
	 */
	Template getTemplateId(@Param("templateId")String templateId);

	/**
	 * 任务分页查询
	 * @param page
	 * @param template
	 * @return
	 */
	IPage<List<Template>> selectPaging(Page page, @Param("template")TemplateVo template);

	/**
	 * 来源id查询
	 * @param id
	 * @return
	 */
	@Select("select id,template_id from tak_template WHERE del_flag = 0 and source_id =#{id}")
	Template sourceId(@Param("id")String id);

	/**
	 * 查询有没有快捷主任务模板
	 * @param template
	 * @return
	 */
	List<Template> source(@Param("template")Template template);

	/**
	 * 查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】
	 * @param template
	 * @return
	 */
	List<Template> selectTaskPreJump( @Param("template")TemplateVo template);
}
