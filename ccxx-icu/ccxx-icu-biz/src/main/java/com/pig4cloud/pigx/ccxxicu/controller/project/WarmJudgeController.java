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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.WarmJudge;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.WarmJudgeVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.project.WarmJudgeService;
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
 * 预警项目判断表
 *
 * @author yyj
 * @date 2019-08-09 19:58:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/warmJudge")
@Api(value = "warmJudge", tags = "预警项目判断表管理")
public class WarmJudgeController {

	private final WarmJudgeService warmJudgeService;

	private final ProjectService projectService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param warmJudgeVo 预警项目判断表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getWarmJudgePage(Page page, WarmJudgeVo warmJudgeVo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(warmJudgeService.selectByPage(page, warmJudgeVo));
	}


	/**
	 * 通过id查询预警项目判断表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(warmJudgeService.getById(id));
	}

	/**
	 * 查询某项目预警判断
	 *
	 * @param projectId
	 * @return
	 */
	@ApiOperation(value = "查询某项目预警判断", notes = "查询某项目预警判断")
	@GetMapping("/getProjectWarm/{projectId}")
	public R getProjectWarm(@PathVariable("projectId") String projectId) {
		if (StringUtils.isEmpty(projectId)) {
			return R.failed(1, "操作失败！");
		}
		return R.ok(warmJudgeService.selectByProjectId(projectId));

	}


	/**
	 * 新增预警项目判断表
	 *
	 * @param warmJudge 预警项目判断表
	 * @return R
	 */
	@ApiOperation(value = "新增预警项目判断表", notes = "新增预警项目判断表")
	@SysLog("新增预警项目判断表")
	@PostMapping("/add")
	public R save(@RequestBody WarmJudge warmJudge) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}
		//如果存在记录  则进行修改
		if (warmJudge.getId() != null) {

			warmJudge.setUpdateUserId(user.getId() + "");

			return R.ok(warmJudgeService.updateById(warmJudge));

		}
		//不存在记录  进行新增
		long id = SnowFlake.getId();
		warmJudge.setWarmJudgeId(id + "");
		warmJudge.setCreateTime(LocalDateTime.now());
		warmJudge.setDelFlag(0);
		warmJudge.setCreateUserId(user.getId() + "");
		boolean save = warmJudgeService.save(warmJudge);

		if (save) {

			Project byId = projectService.selectByProjectId(warmJudge.getProjectId());
			//如果该项目设定不存在预定值 则修改该项目设定
			if (!(byId.getProjectWarmFlag().equals(0))) {

				byId.setProjectWarmFlag(0);

				projectService.updateById(byId);
			}

		}

		return R.ok(save);
	}

	/**
	 * 修改预警项目判断表
	 *
	 * @param warmJudge 预警项目判断表
	 * @return R
	 */
	@ApiOperation(value = "修改预警项目判断表", notes = "修改预警项目判断表")
	@SysLog("修改预警项目判断表")
	@PostMapping("/update")
	public R updateById(@RequestBody WarmJudge warmJudge) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		warmJudge.setUpdateUserId(user.getId() + "");

		return R.ok(warmJudgeService.updateById(warmJudge));
	}

	/**
	 * 通过id删除预警项目判断表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除预警项目判断表", notes = "通过id删除预警项目判断表")
	@SysLog("通过id删除预警项目判断表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		WarmJudge byId = warmJudgeService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		//修改该项目的预警设定
		Project project = projectService.selectByProjectId(byId.getProjectId());

		project.setProjectWarmFlag(1);

		projectService.updateById(project);

		return R.ok(warmJudgeService.updateById(byId));
	}

}
