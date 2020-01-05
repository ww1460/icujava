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
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.TemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateVo;

import java.util.List;

/**
 * 任务模板
 *
 * @author pigx code generator
 * @date 2019-08-15 10:09:38
 */
public interface TemplateService extends IService<Template> {

	/**
	 * 任务模板全查
	 * @param template
	 * @return
	 */
	List<Template> selectAll(TemplateVo template);


	/**
	 * 雪花id查询
	 * @param templateId
	 * @return
	 */
	Template getTemplateId(String templateId);

	/**
	 * 任务模板分页查询
	 * @param page
	 * @param template
	 * @return
	 */
	IPage selectPaging(Page page, TemplateVo template);

	/**
	 * 新增
	 * @param templateBo
	 * @return
	 */
	Boolean add(TemplateBo templateBo);

	/**
	 * 来源 id查询
	 * @param id
	 * @return
	 */
	Template sourceId(String id);


	/**
	 * 查询有没有快捷主任务模板
	 * @return
	 */
	Boolean source();

	/**
	 * 任务模板新增任务【用药任务调用任务模板新增】
	 * @param template
	 * @return
	 */
	Boolean templateAddTask(com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateBo template);

	/**
	 * 通过任务模板的类型查询数据
	 * @param source
	 * @return
	 */
	List<Template> sourceType(Integer source);

	/**
	 * 查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】
	 * @param template
	 * @return
	 */
	List<Template> selectTaskPreJump(TemplateVo template);
}
