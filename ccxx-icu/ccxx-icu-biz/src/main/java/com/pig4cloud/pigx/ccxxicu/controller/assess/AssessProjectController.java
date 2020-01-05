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

package com.pig4cloud.pigx.ccxxicu.controller.assess;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProject;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectService;
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
 * 评估项目条件表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:45:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assessProject")
@Api(value = "assessProject", tags = "评估项目条件表 管理")
public class AssessProjectController {

	private final AssessProjectService assessProjectService;

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param assessProject 评估项目条件表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getAssessProjectPage(Page page, AssessProject assessProject) {
		return R.ok(assessProjectService.page(page, Wrappers.query(assessProject)));
	}


	/**
	 * 通过id查询评估项目条件表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(assessProjectService.getById(id));
	}

	/**
	 * 新增评估项目条件表
	 *
	 * @param assessProject 评估项目条件表
	 * @return R
	 */
	@ApiOperation(value = "新增评估项目条件表 ", notes = "新增评估项目条件表 ")
	@SysLog("新增评估项目条件表 ")
	@PostMapping("/add")
	public R save(@RequestBody AssessProject assessProject) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			return R.failed(1, "权限不足！");
		}

		assessProject.setDelFlag(0);
		assessProject.setCreateUserId(user.getId() + "");
		assessProject.setCreateTime(LocalDateTime.now());
		assessProject.setAssessProjectId(SnowFlake.getId() + "");
		return R.ok(assessProjectService.add(assessProject));
	}

	/**
	 * 修改评估项目条件表
	 *
	 * @param assessProject 评估项目条件表
	 * @return R
	 */
	@ApiOperation(value = "修改评估项目条件表 ", notes = "修改评估项目条件表 ")
	@SysLog("修改评估项目条件表 ")
	@PostMapping("update")
	public R updateById(@RequestBody AssessProject assessProject) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(assessProject.getId()+"")) {
			return R.failed(1, "操作失败！");
		}


		assessProject.setUpdateUserId(user.getId() + "");

		return R.ok(assessProjectService.updateById(assessProject));
	}

	/**
	 * 通过id删除评估项目条件表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除评估项目条件表 ", notes = "通过id删除评估项目条件表 ")
	@SysLog("通过id删除评估项目条件表 ")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(id+"")) {
			return R.failed(1, "操作失败！");
		}

		AssessProject byId = assessProjectService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");
		return R.ok(assessProjectService.updateById(byId));
	}

}
