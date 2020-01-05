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

package com.pig4cloud.pigx.ccxxicu.service.illnessNursing;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.common.core.util.R;

/**
 * 病情护理项目状态表
 *
 * @author pigx code generator
 * @date 2019-09-04 11:30:06
 */
public interface IllnessNursingStateService extends IService<IllnessNursingState> {


	R selectTree(String patientId);

	/**
	 * 查询某项目的状态及其护理建议
	 * @param illnessProjectId
	 * @return
	 */
	R getProjectState(String illnessProjectId);


	/**
	 * 删除项目
	 * @param project
	 * @return
	 */
	boolean delProjectByUpdate(Project project);

	/**
	 * 删除项目正异常记录
	 * @param id
	 * @return
	 */
	boolean delByUpdate(Integer id);





}
