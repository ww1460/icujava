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

package com.pig4cloud.pigx.ccxxicu.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目管理表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:09
 */
public interface ProjectMapper extends BaseMapper<Project> {
	/**
	 * 根据项目名查询
	 * @param projectName
	 * @return
	 */
	Project selectByName(@Param("projectName") String projectName, @Param("deptId")String deptId);

	/**
	 * 根据项目简称查询
	 * @param projectCode
	 * @return
	 */
	Project selectByCode(@Param("projectCode") String projectCode, @Param("projectType") Integer projectType, @Param("deptId")String deptId);

	/**
	 * 根据项目类别查询
	 * @param projectType
	 * @return
	 */
	List<Project> selectByType(@Param("projectType") Integer projectType, @Param("deptId")String deptId);

	/**
	 * 获取项目所有类别
	 * @return
	 */
	List<Integer> getProjectType(@Param("deptId")String deptId);

	/**
	 * 根据生成id 查询
	 * @param projectId
	 * @return
	 */
	Project selectByProjectId(@Param("projectId")String projectId, @Param("deptId")String deptId);





}
