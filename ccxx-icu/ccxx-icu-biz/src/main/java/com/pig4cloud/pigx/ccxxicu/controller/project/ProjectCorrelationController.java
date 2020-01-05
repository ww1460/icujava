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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectCorrelation;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectCorrelationService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 项目标识表(主要是对护理单一二作关联)
 *
 * @author pigx code generator
 * @date 2019-09-13 16:11:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/projectCorrelation")
@Api(value = "projectCorrelation", tags = "项目标识表(主要是对护理单一二作关联)管理")
public class ProjectCorrelationController {

	private final ProjectCorrelationService projectCorrelationService;


	/**
	 * 获取某护理单的项目id
	 *
	 * @param nursingReportFlag 项目标识表(主要是对护理单一二作关联)
	 * @return
	 */
	@ApiOperation(value = "获取某护理单的项目id", notes = "获取某护理单的项目id")
	@GetMapping("/getProjectCorrelation/{nursingReportFlag}")
	public R getProjectCorrelation(@PathVariable("nursingReportFlag") Integer nursingReportFlag) {
		PigxUser user = SecurityUtils.getUser();
		List<String> roleCodes = SecurityUtils.getRoleCodes();

		ProjectCorrelation projectCorrelation = new ProjectCorrelation();

		if (!roleCodes.get(0).equals("ROLE_ADMIN")) {

			projectCorrelation.setDeptId(user.getDeptId() + "");

		}
		if (StringUtils.isEmpty(nursingReportFlag+"")) {
			return R.failed(1, "操作失败！");
		}
		projectCorrelation.setNursingReportFlag(nursingReportFlag);
		return R.ok(projectCorrelationService.selectReportProject(projectCorrelation));
	}

	/**
	 * 修改展示方式
	 *
	 * @param projectCorrelation 修改展示方式
	 * @return
	 */
	@ApiOperation(value = "修改展示方式", notes = "修改展示方式")
	@PostMapping("/update")
	public R update(@RequestBody ProjectCorrelation projectCorrelation) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty() || roleCodes.get(0).equals("ROLE_NURSE")) {

			R.failed(1, "登录有误或权限不足");

		}

		return R.ok(projectCorrelationService.updateById(projectCorrelation));
	}

	/**
	 * 修改展示顺序
	 *
	 * @param projectCorrelation 修改展示顺序
	 * @return
	 */
	@ApiOperation(value = "修改展示顺序", notes = "修改展示顺序")
	@PostMapping("/updateBatch")
	public R updateBatch(@RequestBody List<ProjectCorrelation> projectCorrelation) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty() || roleCodes.get(0).equals("ROLE_NURSE")) {

			R.failed(1, "登录有误或权限不足");

		}

		if (CollectionUtils.isEmpty(projectCorrelation)) {

			return R.failed("未进行修改排序！");

		}


		//将现在的关联进行添加
		projectCorrelation.forEach(ar -> {

			ar.setShowIndex(projectCorrelation.indexOf(ar)+1);

		});

		return R.ok(projectCorrelationService.updateBatchById(projectCorrelation));
	}


	/**
	 * 修改项目标识表(主要是对护理单一二作关联)
	 *
	 * @param projectCorrelation 项目标识表(主要是对护理单一二作关联)
	 * @return R
	 */
	@ApiOperation(value = "修改项目标识表(主要是对护理单一二作关联)", notes = "修改项目标识表(主要是对护理单一二作关联)")
	@SysLog("修改项目标识表(主要是对护理单一二作关联)")
	@PostMapping("/save")
	public R updateById(@RequestBody List<ProjectCorrelation> projectCorrelation) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty() || roleCodes.get(0).equals("ROLE_NURSE")) {

			R.failed(1, "登录有误或权限不足");

		}

		if (CollectionUtils.isEmpty(projectCorrelation)) {
			return R.failed(2);
		}
		//确定是哪个记录单
		Integer nursingReportFlag = projectCorrelation.get(0).getNursingReportFlag();

		List<ProjectCorrelation> list = projectCorrelationService.list(Wrappers.<ProjectCorrelation>query().lambda()
				.eq(ProjectCorrelation::getDeptId, user.getDeptId() + "")
				.eq(ProjectCorrelation::getNursingReportFlag, nursingReportFlag)
		);

		if (!CollectionUtils.isEmpty(list)) {
			//将之前的关联全部删除
			projectCorrelationService.remove(Wrappers.<ProjectCorrelation>query().lambda()
					.eq(ProjectCorrelation::getNursingReportFlag, nursingReportFlag)
					.eq(ProjectCorrelation::getDeptId, user.getDeptId()));
		}

		//将现在的关联进行添加
		projectCorrelation.forEach(ar -> {

			ar.setDeptId(user.getDeptId() + "");
			ar.setShowIndex(projectCorrelation.indexOf(ar)+1);

		});

		return R.ok(projectCorrelationService.saveBatch(projectCorrelation));
	}


	@GetMapping("/del/{id}")
	public R delete(@PathVariable("id") Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty() || roleCodes.get(0).equals("ROLE_NURSE")) {

			R.failed(1, "登录有误或权限不足");

		}

		return R.ok(projectCorrelationService.removeById(id));

	}


}
