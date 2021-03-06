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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.NursingTaskRelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.NursingTaskRelationVo;

import java.util.List;

/**
 * 护理项目与任务模板关联表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:39:31
 */
public interface NursingTaskRelationService extends IService<NursingTaskRelation> {

	/**
	 * 全查数据
	 * @param nursingTaskRelation
	 * @return
	 */
	List<NursingTaskRelationVo>selectAll(NursingTaskRelation nursingTaskRelation);

	/**
	 * 分页查询数据
	 * @param page
	 * @param nursingTaskRelation
	 * @return
	 */
	IPage selectPaging(Page page,NursingTaskRelation nursingTaskRelation);

	/**
	 * 通过任务模板查询数据
	 * @param takTemplateId
	 * @return
	 */
	NursingTaskRelation takTemplateId(String takTemplateId);


}
