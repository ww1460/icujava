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

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessNursingStateService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
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
 * 病情护理项目状态表
 *
 * @author pigx code generator
 * @date 2019-09-04 11:30:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/illnessNursingState")
@Api(value = "illnessNursingState", tags = "病情护理项目状态表管理")
public class IllnessNursingStateController {

	private final IllnessNursingStateService illnessNursingStateService;

	@Autowired
	private ProjectService projectService;


	/**
	 * 查询病情护理的项目
	 *
	 * @return
	 */
	@ApiOperation(value = "查询病情护理的项目", notes = "查询病情护理的项目")
	@GetMapping("/getProject")
	public R getProject(Page page) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//查询所有的病情护理的项目
		return R.ok(projectService.page(page, Wrappers.<Project>query().lambda()
				.eq(Project::getDelFlag, 0)
				.eq(Project::getIllnessNursingFlag, 0)
				.eq(Project::getDeptId, user.getDeptId() + "")));

	}


	/**
	 * 分页查询
	 *
	 * @param page                分页对象
	 * @param illnessNursingState 病情护理项目状态表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getIllnessNursingStatePage(Page page, IllnessNursingState illnessNursingState) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		illnessNursingState.setDelFlag(0);

		return R.ok(illnessNursingStateService.page(page, Wrappers.query(illnessNursingState)));
	}


	/**
	 * 通过id查询病情护理项目状态表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(illnessNursingStateService.getById(id));
	}


	/**
	 * 查询每个项目的具体正异常数据
	 *
	 * @param illnessProjectId
	 * @return R
	 */
	@ApiOperation(value = "查询每个项目的具体正异常数据", notes = "查询每个项目的具体正异常数据")
	@GetMapping("/getProjectState/{illnessProjectId}")
	public R getById(@PathVariable("illnessProjectId") String illnessProjectId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (StringUtils.isEmpty(illnessProjectId)) {
			return R.failed(1, "操作失败！");
		}

		//根据项目id  查询对应的正异常数据
		return illnessNursingStateService.getProjectState(illnessProjectId);
	}


	/**
	 * 通过患者id查询病情护理项目状态表
	 *
	 * @param patientId 患者id
	 * @return R
	 */
	@ApiOperation(value = "通过患者id查询病情护理项目状态表", notes = "通过患者id查询病情护理项目状态表")
	@GetMapping("/getIllnessTree/{patientId}")
	public R getIllnessTree(@PathVariable("patientId") String patientId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(patientId)) {
			return R.failed(1, "操作失败！");
		}


		return illnessNursingStateService.selectTree(patientId);
	}


	/**
	 * 新增病情护理项目状态表
	 *
	 * @param illnessNursingState 病情护理项目状态表
	 * @return R
	 */
	@ApiOperation(value = "新增病情护理项目状态表", notes = "新增病情护理项目状态表")
	@SysLog("新增病情护理项目状态表")
	@PostMapping("/add")
	public R save(@RequestBody IllnessNursingState illnessNursingState) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		illnessNursingState.setCreateTime(LocalDateTime.now());
		illnessNursingState.setCreateUserId(user.getId() + "");
		illnessNursingState.setDelFlag(0);
		illnessNursingState.setIllnessNursingStateId(SnowFlake.getId() + "");

		return R.ok(illnessNursingStateService.save(illnessNursingState));
	}

	/**
	 * 修改病情护理项目状态表
	 *
	 * @param illnessNursingState 病情护理项目状态表
	 * @return R
	 */
	@ApiOperation(value = "修改病情护理项目状态表", notes = "修改病情护理项目状态表")
	@SysLog("修改病情护理项目状态表")
	@PostMapping("/update")
	public R updateById(@RequestBody IllnessNursingState illnessNursingState) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		illnessNursingState.setUpdateUserId(user.getId() + "");

		return R.ok(illnessNursingStateService.updateById(illnessNursingState));
	}

	/**
	 * 通过id删除病情护理项目状态表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除病情护理项目状态表", notes = "通过id删除病情护理项目状态表")
	@SysLog("通过id删除病情护理项目状态表")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}


		return R.ok(illnessNursingStateService.delByUpdate(id));
	}


	/**
	 * 删除项目  直接修改项目的病情护理标识  并删除该项目的正常异常数据
	 *
	 * @param project project
	 * @return R
	 */
	@ApiOperation(value = "删除项目", notes = "删除项目")
	@SysLog("删除项目")
	@PostMapping("/delProject")
	public R delProject(@RequestBody Project project) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		project.setUpdateUserId(user.getId() + "");

		return R.ok(illnessNursingStateService.delProjectByUpdate(project));
	}


}
