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
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 项目记录可选值表
 *
 * @author pigx code generator
 * @date 2019-09-09 11:20:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectRecordValue")
@Api(value = "projectRecordValue", tags = "项目记录可选值表管理")
public class ProjectRecordValueController {

	private final ProjectRecordValueService projectRecordValueService;

	@Autowired
	private ProjectService projectService;

	/**
	 * 分页查询
	 *
	 * @param page               分页对象
	 * @param projectRecordValue 项目记录可选值表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getProjectRecordValuePage(Page page, ProjectRecordValue projectRecordValue) {
		return R.ok(projectRecordValueService.page(page, Wrappers.query(projectRecordValue)));
	}


	/**
	 * 通过id查询项目记录可选值表
	 *
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByProject/{projectId}")
	public R getById(@PathVariable("projectId") String projectId) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(projectId)) {
			return R.failed(1, "操作失败！");
		}

		List<ProjectRecordValue> list = projectRecordValueService.list(Wrappers.<ProjectRecordValue>query().lambda()
				.eq(ProjectRecordValue::getDelFlag, 0)
				.eq(ProjectRecordValue::getProjectId, projectId)
		);

		return R.ok(list);
	}

	/**
	 * 新增项目记录可选值表
	 *
	 * @param projectRecordValue 项目记录可选值表
	 * @return R
	 */
	@ApiOperation(value = "新增项目记录可选值表", notes = "新增项目记录可选值表")
	@SysLog("新增项目记录可选值表")
	@PostMapping("/add")
	public R save(@RequestBody ProjectRecordValue projectRecordValue) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}
		long id = SnowFlake.getId();
		projectRecordValue.setProjectRecordValueId(id + "");
		projectRecordValue.setCreateTime(LocalDateTime.now());
		projectRecordValue.setDelFlag(0);
		projectRecordValue.setCreateUserId(user.getId() + "");
		projectRecordValue.setDeptId(user.getDeptId()+"");
		boolean save = projectRecordValueService.add(projectRecordValue);
		if (save) {

			Project byId = projectService.selectByProjectId(projectRecordValue.getProjectId());
			//如果该项目设定不存在预定值 则修改该项目设定
			if (!(byId.getProjectRecordValueFlag().equals(0))) {

				byId.setProjectRecordValueFlag(0);

				projectService.updateById(byId);
			}
		}
		return R.ok(save);
	}

	/**
	 * 修改项目记录可选值表
	 *
	 * @param projectRecordValue 项目记录可选值表
	 * @return R
	 */
	@ApiOperation(value = "修改项目记录可选值表", notes = "修改项目记录可选值表")
	@SysLog("修改项目记录可选值表")
	@PostMapping("/update")
	public R updateById(@RequestBody ProjectRecordValue projectRecordValue) {
		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		projectRecordValue.setUpdateUserId(user.getId() + "");

		return R.ok(projectRecordValueService.updateById(projectRecordValue));
	}

	/**
	 * 通过id删除项目记录可选值表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除项目记录可选值表", notes = "通过id删除项目记录可选值表")
	@SysLog("通过id删除项目记录可选值表")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		ProjectRecordValue byId = projectRecordValueService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		return R.ok(projectRecordValueService.updateById(byId));
	}


	/**
	 * 查询项目的固定值
	 *
	 * @param project 查询项目的固定值
	 * @return R
	 */
	@ApiOperation(value = "查询项目的固定值", notes = "查询项目的固定值")
	@SysLog("查询项目的固定值")
	@PostMapping("/getProjectValue")
	public R getProjectValue(@RequestBody Project project) {
		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		return R.ok(projectRecordValueService.selectProjectValue(project));
	}


}
