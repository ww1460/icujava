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
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目管理表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:09
 */
public interface ProjectService extends IService<Project> {

	/**
	 * 根据项目名查询
	 * @param projectName
	 * @return
	 */
	Project selectByName(@Param("projectName") String projectName);

	/**
	 * 根据项目简称查询
	 * @param projectCode
	 * @return
	 */
	Project selectByCode(@Param("projectCode") String projectCode, Integer projectType);

	/**
	 * 根据项目类别查询
	 * @param projectType
	 * @return
	 */
	List<Project> selectByType(@Param("projectType") Integer projectType);

	/**
	 * 获取项目所有类别
	 * @return
	 */
	List<Integer> getProjectType();

	/**
	 * 根据生成id 查询
	 * @param projectId
	 * @return
	 */
	Project selectByProjectId(String projectId);

	/**
	 * 新增项目
	 * @param project
	 * @return
	 */
	boolean add(Project project);

	/**
	 * 修改项目
	 * @param project
	 * @return
	 */
	boolean updateProject(Project project);

	/**
	 * 删除项目
	 * @param project
	 * @return
	 */
	boolean deleteProject(Project project);

	/**
	 * 管理病情护理项目
	 * @param projects
	 * @return
	 */
	boolean illnessProject(List<Project> projects);


}
