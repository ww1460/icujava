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
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateSubTaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateSubTaskVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 任务模板子模板
 *
 * @author pigx code generator
 * @date 2019-10-07 16:58:17
 */
public interface TemplateSubTaskService extends IService<TemplateSubTask> {

	/**
	 * 任务模板id查询
	 * @param id
	 * @return
	 */
	List<TemplateSubTask> taskTemplateId(String id);


	/**
	 * 通过任务模板id查询子模板任务（查询出来的任务，是有长期任务，模板结束时间大于当前时间的模板任务）
	 * @param id
	 * @return
	 */
	List<TemplateSubTask> taskTemplateIdTime(String id);


	/**
	 * 多数据删除
	 * @param list
	 * @return
	 */
	Boolean deletes(List<TemplateSubTask> list);
	/**
	 * 来源id查询
	 * @param id
	 * @return
	 */
	TemplateSubTask sourceId(String id);
	/**
	 * 展示自建的任务模板
	 * @param templateSubTask
	 * @return
	 */
	List<TemplateSubTask> buildByOneself(TemplateSubTaskVo templateSubTask);

	/**
	 * 任务模板新增任务
	 * @param templateSubTask
	 * @return
	 */
	Boolean addTask(TemplateSubTaskBo templateSubTask);


	/**
	 * 通过判断任务模板，查询当条任务模板是否有固定结果     任务模板的来源 是否来源于项目中
	 * @param templateSubTas
	 * @return
	 */
	R selectTemplateProjectValue(TemplateSubTask templateSubTas);

}
