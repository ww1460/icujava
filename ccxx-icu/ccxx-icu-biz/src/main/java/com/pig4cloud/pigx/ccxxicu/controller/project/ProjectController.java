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

package com.pig4cloud.pigx.ccxxicu.controller.project;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * 项目管理表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project")
@Api(value = "project", tags = "项目管理表管理")
public class ProjectController {

	private final ProjectService projectService;


	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param project 项目管理表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getProjectListPage(Page page, Project project) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}


		project.setDelFlag(0);
		if (!roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员

			project.setDeptId(user.getDeptId() + "");
		}

		if (StringUtils.isNotEmpty(project.getProjectCode())) {

			project.setProjectCode(project.getProjectCode().toUpperCase());

		}

		return R.ok(projectService.page(page, Wrappers.query(project)));
	}

	/**
	 * 获取各项目的项目名
	 *
	 * @return
	 */
	@ApiOperation(value = "获取各类的项目", notes = "获取各类的项目")
	@PostMapping("/getTypeProject")
	public R getTypeProject() {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}
		//查询都有哪些类型
		List<Integer> projectType = projectService.getProjectType();
		HashMap<Integer, List<Project>> result = new HashMap<>();
		//将查询的类型 放入集合
		projectType.forEach(ar -> {

			result.put(ar, projectService.selectByType(ar));

		});


		return R.ok(result);
	}

	/**
	 * 通过id查询项目管理表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(projectService.getById(id));
	}

	/**
	 * 新增项目管理表
	 *
	 * @param project 项目管理表
	 * @return R
	 */
	@ApiOperation(value = "新增项目管理表", notes = "新增项目管理表")
	@SysLog("新增项目管理表")
	@PostMapping("/add")
	public R save(@RequestBody Project project) {

		PigxUser user = SecurityUtils.getUser();
		if (user == null) {

			R.failed(1, "操作有误！");

		}
		long id = SnowFlake.getId();
		project.setProjectId(id + "");
		project.setCreateTime(LocalDateTime.now());
		project.setDelFlag(0);
		project.setCreateUserId(user.getId() + "");
		project.setDeptId(user.getDeptId() + "");

		return R.ok(projectService.add(project));
	}

	/**
	 * 修改项目管理表
	 *
	 * @param project 项目管理表
	 * @return R
	 */
	@ApiOperation(value = "修改项目管理表", notes = "修改项目管理表")
	@SysLog("修改项目管理表")
	@PostMapping("/update")
	public R updateById(@RequestBody Project project) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		project.setUpdateUserId(user.getId() + "");

		return R.ok(projectService.updateProject(project));
	}

	/**
	 * 通过id删除项目管理表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除项目管理表", notes = "通过id删除项目管理表")
	@SysLog("通过id删除项目管理表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		Project byId = projectService.getById(id);


		byId.setDelUserId(user.getId() + "");
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());

		return R.ok(projectService.deleteProject(byId));
	}

	/**
	 * 获取某一类型的项目名
	 *
	 * @return
	 */
	@ApiOperation(value = "获取某一类型的项目名", notes = "获取某一类型的项目名")
	@GetMapping("/getOneTypeProject/{projectType}")
	public R getOneTypeProject(@PathVariable Integer projectType) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		if (StringUtils.isEmpty(projectType+"")) {
			return R.failed(1, "操作失败！");
		}

		return R.ok(projectService.selectByType(projectType));
	}


	/**
	 * 管理病情护理项目
	 *
	 * @return
	 */
	@ApiOperation(value = "管理病情护理项目", notes = "管理病情护理项目")
	@PostMapping("/illnessProject")
	public R illnessProject(@RequestBody List<Project> projects) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		return R.ok(projectService.illnessProject(projects));
	}


	/**
	 * 获取科室的病情护理项目
	 *
	 * @return
	 */
	@ApiOperation(value = "获取某一类型的项目名", notes = "获取某一类型的项目名")
	@GetMapping("/illness")
	public R illness() {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}
		//查询该科室下的病情护理项目
		return R.ok(projectService.list(Wrappers.<Project>query().lambda()
						.eq(Project::getDelFlag, 0)
						.eq(Project::getIllnessNursingFlag, 0)
						.eq(Project::getDeptId, user.getDeptId() + "")
				));
	}


}
