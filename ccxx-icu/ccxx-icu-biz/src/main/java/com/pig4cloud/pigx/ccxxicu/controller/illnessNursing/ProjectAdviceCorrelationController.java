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

package com.pig4cloud.pigx.ccxxicu.controller.illnessNursing;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.ProjectAdviceCorrelation;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.ProjectAdviceCorrelationService;
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
 * 项目状态和建议的关联表
 *
 * @author pigx code generator
 * @date 2019-09-09 14:38:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectAdviceCorrelation")
@Api(value = "projectAdviceCorrelation", tags = "项目状态和建议的关联表管理")
public class ProjectAdviceCorrelationController {

	private final ProjectAdviceCorrelationService projectAdviceCorrelationService;

	/**
	 * 分页查询
	 *
	 * @param page                     分页对象
	 * @param projectAdviceCorrelation 项目状态和建议的关联表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getProjectAdviceCorrelationPage(Page page, ProjectAdviceCorrelation projectAdviceCorrelation) {
		return R.ok(projectAdviceCorrelationService.page(page, Wrappers.query(projectAdviceCorrelation)));
	}


	/**
	 * 通过id查询项目状态和建议的关联表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(projectAdviceCorrelationService.getById(id));
	}

	/**
	 * 新增项目状态和建议的关联表
	 *
	 * @param projectAdviceCorrelations 项目状态和建议的关联表
	 * @return R
	 */
	@ApiOperation(value = "新增项目状态和建议的关联表", notes = "新增项目状态和建议的关联表")
	@SysLog("新增项目状态和建议的关联表")
	@PostMapping("/addAndUpdate")
	public R save(@RequestBody List<ProjectAdviceCorrelation> projectAdviceCorrelations) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(projectAdviceCorrelationService.updateCorrelation(projectAdviceCorrelations));
	}


	/**
	 * 通过id删除项目状态和建议的关联表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除项目状态和建议的关联表", notes = "通过id删除项目状态和建议的关联表")
	@SysLog("通过id删除项目状态和建议的关联表")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		ProjectAdviceCorrelation byId = projectAdviceCorrelationService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		return R.ok(projectAdviceCorrelationService.updateById(byId));
	}

}
