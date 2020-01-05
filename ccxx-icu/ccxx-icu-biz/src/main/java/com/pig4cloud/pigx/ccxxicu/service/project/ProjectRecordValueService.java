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

package com.pig4cloud.pigx.ccxxicu.service.project;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;

import java.util.List;

/**
 * 项目记录可选值表
 *
 * @author pigx code generator
 * @date 2019-09-09 11:20:49
 */
public interface ProjectRecordValueService extends IService<ProjectRecordValue> {
	/**
	 * 新增数据
	 * @param projectRecordValue
	 * @return
	 */
	boolean add(ProjectRecordValue projectRecordValue);

	/**
	 * 查询项目的固定值
	 * @param project
	 * @return
	 */
	List<ProjectValueVo> selectProjectValue(Project project);

	/**
	 * 查询报表的固定值
	 *
	 * @param nursingBo
	 * @return
	 */
	List<ProjectValueVo> getProjectValue(NursingBo nursingBo);

	/**
	 * 通过雪花id查询数据
	 * @param projectRecordValueId
	 * @return
	 */
	ProjectRecordValue getProjectRecordValueId(String projectRecordValueId);


}
