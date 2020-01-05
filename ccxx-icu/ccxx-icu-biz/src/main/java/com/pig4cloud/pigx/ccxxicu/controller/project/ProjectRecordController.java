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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ProjectRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
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
 * 项目数据记录表
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectRecord")
@Api(value = "projectRecord", tags = "项目数据记录表管理")
public class ProjectRecordController {

	private final ProjectRecordService projectRecordService;

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param projectRecordBo 项目数据记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getProjectRecordPage(Page page, ProjectRecordBo projectRecordBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(projectRecordService.selectByPage(page, projectRecordBo));

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			projectRecordBo.setDeptId(user.getDeptId() + "");
			return R.ok(projectRecordService.selectByPage(page, projectRecordBo));
		}

		projectRecordBo.setNurseId(user.getId() + "");

		return R.ok(projectRecordService.selectByPage(page, projectRecordBo));
	}


	/**
	 * 通过id查询项目数据记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getById/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(projectRecordService.getById(id));
	}

	/**
	 * 修改项目数据记录表
	 *
	 * @param projectRecord 修改项目数据记录表
	 * @return R
	 */
	@ApiOperation(value = "修改项目数据记录表", notes = "修改项目数据记录表")
	@SysLog("修改项目数据记录表")
	@PostMapping("/update")
	public R update(@RequestBody ProjectRecord projectRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime createTime = projectRecord.getCreateTime();
			LocalDateTime dateTime = now.minusHours(1);
			if (!dateTime.isBefore(createTime)) {

				return R.failed(3, "已超过修改时间，不可修改");

			}

		}
		projectRecord.setUpdateUserId(user.getId()+"");
		ProjectRecord byId = projectRecordService.getById(projectRecord.getId());
		String replace = projectRecord.getProjectSpecificRecord().replace(byId.getProjectValue(), projectRecord.getProjectValue());
		projectRecord.setProjectSpecificRecord(replace);
		return R.ok(projectRecordService.updateRecord(projectRecord));
	}
	/**
	 * 新增项目数据记录表
	 *
	 * @param projectRecord 项目数据记录表
	 * @return R
	 */
	@ApiOperation(value = "新增项目数据记录表", notes = "新增项目数据记录表")
	@SysLog("新增项目数据记录表")
	@PostMapping("/add")
	public R save(@RequestBody ProjectRecord projectRecord) {
		return R.ok(projectRecordService.save(projectRecord));
	}


	/**
	 * 获取某患者的生命体征及出入量
	 *
	 * @param patientId id
	 * @return R
	 */
	@ApiOperation(value = "获取某患者的生命体征及出入量", notes = "获取某患者的生命体征及出入量")
	@SysLog("获取某患者的生命体征及出入量")
	@GetMapping("/getVital/{patientId}")
	public R getVital(@PathVariable String patientId) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(patientId)) {
			return R.ok(null);
		}

		return R.ok(projectRecordService.selectLineChart(patientId));
	}






}
