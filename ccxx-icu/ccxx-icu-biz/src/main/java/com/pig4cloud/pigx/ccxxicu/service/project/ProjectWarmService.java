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


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectWarm;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectWarmVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目预警表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:59:14
 */
public interface ProjectWarmService extends IService<ProjectWarm> {

	/**
	 * 校验 是否新增预警
	 * @param projectWarm
	 * @return
	 */
	int add(ProjectWarm projectWarm);


	/**
	 * 预警项目分页查询
	 * @param page
	 * @param projectWarmVo
	 * @return
	 */
	IPage<ProjectWarmVo> selectByPage(Page page , @Param("query") ProjectWarmVo projectWarmVo);

	/**
	 * 获取最新的一条记录
	 * @param projectWarm
	 * @return
	 */
	ProjectWarm selectByPatient(ProjectWarm projectWarm);



	List<ProjectWarmVo> warmShow(ProjectWarmVo projectWarmVo);


	List<ProjectWarmVo> toWatch(String nurseId);


}
