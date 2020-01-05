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


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectWarm;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectWarmVo;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectWarmService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目预警表
 *
 * @author yyj
 * @date 2019-08-09 19:59:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectWarm")
@Api(value = "projectWarm", tags = "项目预警表管理")
public class ProjectWarmController {

	private final ProjectWarmService projectWarmService;

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param projectWarmVo 项目预警表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getProjectWarmPage(Page page, ProjectWarmVo projectWarmVo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}


		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员

			return R.ok(projectWarmService.selectByPage(page, projectWarmVo));


		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			projectWarmVo.setDeptId(user.getDeptId() + "");
			return R.ok(projectWarmService.selectByPage(page, projectWarmVo));

		}


		projectWarmVo.setNurseId(user.getId() + "");

		return R.ok(projectWarmService.selectByPage(page, projectWarmVo));
	}


	/**
	 * 通过id查询项目预警表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getById/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(projectWarmService.getById(id));
	}

	/**
	 * 新增项目预警表
	 *
	 * @param projectWarm 项目预警表
	 * @return R
	 */
	@ApiOperation(value = "新增项目预警表", notes = "新增项目预警表")
	@SysLog("新增项目预警表")
	@PostMapping("/add")
	public R save(@RequestBody ProjectWarm projectWarm) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {
			return R.failed(1, "操作有误！");
		}

		projectWarm.setCreateUserId(user.getId() + "");
		projectWarm.setDeptId(user.getDeptId() + "");

		return R.ok(projectWarmService.add(projectWarm));
	}

	/**
	 * 修改项目预警表
	 *
	 * @param projectWarm 项目预警表
	 * @return R
	 */
	@ApiOperation(value = "修改项目预警表", notes = "修改项目预警表")
	@SysLog("修改项目预警表")
	@PostMapping("/update")
	public R updateById(@RequestBody ProjectWarm projectWarm) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {
			return R.failed(1, "操作有误！");
		}

		projectWarm.setUpdateUserId(user.getId() + "");


		return R.ok(projectWarmService.updateById(projectWarm));
	}

	/**
	 * 通过id删除项目预警表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除项目预警表", notes = "通过id删除项目预警表")
	@SysLog("通过id删除项目预警表")
	@DeleteMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {
			return R.failed(1, "操作有误！");
		}

		ProjectWarm byId = projectWarmService.getById(id);

		byId.setDelFlag(1);

		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		return R.ok(projectWarmService.updateById(byId));
	}



	/**
	 * 分页查询
	 *
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/warmShow")
	public R getProjectWarmPage() {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		ProjectWarmVo projectWarmVo = new ProjectWarmVo();

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员

			return R.ok(projectWarmService.warmShow(projectWarmVo));


		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			projectWarmVo.setDeptId(user.getDeptId() + "");
			return R.ok(projectWarmService.warmShow(projectWarmVo));

		}

		projectWarmVo.setNurseId(user.getId()+"");

		return R.ok(projectWarmService.warmShow(projectWarmVo));
	}



}
