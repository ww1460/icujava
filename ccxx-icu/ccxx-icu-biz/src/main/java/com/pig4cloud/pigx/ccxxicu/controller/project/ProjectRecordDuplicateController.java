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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordDuplicate;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordDuplicateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 项目数据记录表复本
 *
 * @author pigx code generator
 * @date 2019-09-11 14:35:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectRecordDuplicate")
@Api(value = "projectRecordDuplicate", tags = "项目数据记录表复本管理")
public class ProjectRecordDuplicateController {

	private final ProjectRecordDuplicateService projectRecordDuplicateService;


	/**
	 * 新增项目数据记录表复本
	 *
	 * @param projectRecordDuplicate 项目数据记录表复本
	 * @return R
	 */
	@ApiOperation(value = "新增项目数据记录表复本", notes = "新增项目数据记录表复本")
	@SysLog("新增项目数据记录表复本")
	@PostMapping("/add")
	public R save(@RequestBody ProjectRecordDuplicate projectRecordDuplicate) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}
		projectRecordDuplicate.setProjectRecordDuplicateId(SnowFlake.getId() + "");
		projectRecordDuplicate.setDelFlag(0);
		projectRecordDuplicate.setCreateUserId(user.getId() + "");
		projectRecordDuplicate.setDeptId(user.getDeptId() + "");
		projectRecordDuplicate.setSource(DataSourceEnum.WINDOWS_ENTER.getCode());

		return R.ok(projectRecordDuplicateService.add(projectRecordDuplicate));
	}

	/**
	 * 修改项目数据记录表复本
	 *
	 * @param projectRecordDuplicate 项目数据记录表复本
	 * @return R
	 */
	@ApiOperation(value = "修改项目数据记录表复本", notes = "修改项目数据记录表复本")
	@SysLog("修改项目数据记录表复本")
	@PostMapping("/update")
	public R update(@RequestBody ProjectRecordDuplicate projectRecordDuplicate) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		projectRecordDuplicate.setUpdateUserId(user.getId() + "");

		return R.ok(projectRecordDuplicateService.updateRecord(projectRecordDuplicate));
	}


	/**
	 * 查询护理记录单二
	 *
	 * @param nursingBo
	 * @return R
	 */
	@ApiOperation(value = "查询护理记录单二", notes = "查询护理记录单二")
	@SysLog("查询护理记录单二")
	@PostMapping("/getReportTwo")
	public R getReportTwo(@RequestBody NursingBo nursingBo) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(nursingBo.getPatientId())) {
			return R.ok(null);
		}

		nursingBo.setNursingReportFlag(2);
		nursingBo.setDeptId(user.getDeptId() + "");
		return projectRecordDuplicateService.getNursingReport(nursingBo);
	}

	/**
	 * 查询护理记录单一
	 *
	 * @param nursingBo
	 * @return R
	 */
	@ApiOperation(value = "查询护理记录单一", notes = "查询护理记录单一")
	@SysLog("查询护理记录单一")
	@PostMapping("/getReportOne")
	public R getReportOne(@RequestBody NursingBo nursingBo) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(nursingBo.getPatientId())) {
			return R.ok(null);
		}
		nursingBo.setNursingReportFlag(1);
		nursingBo.setDeptId(user.getDeptId() + "");
		return projectRecordDuplicateService.getNursingReport(nursingBo);
	}

	/**
	 * 查询生命体征
	 *
	 * @param nursingBo
	 * @return R
	 */
	@ApiOperation(value = "查询生命体征", notes = "查询生命体征")
	@SysLog("查询护理记录单一")
	@PostMapping("/getVital")
	public R getVital(@RequestBody NursingBo nursingBo) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(nursingBo.getPatientId())) {
			return R.ok(null);
		}
		nursingBo.setDeptId(user.getDeptId() + "");
		return projectRecordDuplicateService.getVital(nursingBo);
	}

	/**
	 * 通过id删除护士与患者沟通
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除护士与患者沟通", notes = "通过id删除护士与患者沟通")
	@SysLog("通过id删除护士与患者沟通")
	@DeleteMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		ProjectRecordDuplicate byId = projectRecordDuplicateService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId() + "");
		return R.ok(projectRecordDuplicateService.updateById(byId));
	}





}
