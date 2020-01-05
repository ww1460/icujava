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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessShowBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessType;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessTypeService;
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
 * 评估类型表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:45:38
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assessType")
@Api(value = "assessType", tags = "评估类型表管理")
public class AssessTypeController {

	private final AssessTypeService assessTypeService;


	/**
	 * 分页查询
	 *
	 * @param page       分页对象
	 * @param assessType 评估类型表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getAssessTypePage(Page page, AssessType assessType) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(assessTypeService.page(page, Wrappers.query(assessType)));
	}


	/**
	 * 通过id查询评估类型表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(assessTypeService.getById(id));
	}


	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/getAssessTree/{projectId}")
	public R getTree(@PathVariable("projectId") String projectId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			return R.failed(1, "权限不足！");

		}
		if (StringUtils.isEmpty(projectId)) {
			return R.failed(1, "操作失败！");
		}


		return R.ok(assessTypeService.getAssessData(projectId,null));
	}


	/**
	 * 返回树形菜单集合及结果
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/getAssess")
	public R getAssess(AssessShowBo assessShowBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(assessShowBo.getProjectCode())) {
			return R.failed(1, "操作失败！");
		}



		return R.ok(assessTypeService.getAssess(assessShowBo));
	}

	/**
	 * 返回树形菜单集合及结果
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/assessRecord")
	public R getAssessRecord(AssessRecord assessRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}


		return R.ok(assessTypeService.selectAssess(assessRecord));
	}



	/**
	 * 新增评估类型表
	 *
	 * @param assessType 评估类型表
	 * @return R
	 */
	@ApiOperation(value = "新增评估类型表", notes = "新增评估类型表")
	@SysLog("新增评估类型表")
	@PostMapping("/add")
	public R save(@RequestBody AssessType assessType) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			return R.failed(1, "权限不足！");

		}

		assessType.setAssessTypeId(SnowFlake.getId() + "");
		assessType.setCreateUserId(user.getId() + "");
		assessType.setCreateTime(LocalDateTime.now());
		assessType.setDelFlag(0);
		assessType.setDeptId(user.getDeptId() + "");

		return R.ok(assessTypeService.save(assessType));
	}

	/**
	 * 修改评估类型表
	 *
	 * @param assessType 评估类型表
	 * @return R
	 */
	@ApiOperation(value = "修改评估类型表", notes = "修改评估类型表")
	@SysLog("修改评估类型表")
	@PostMapping("/update")
	public R updateById(@RequestBody AssessType assessType) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		assessType.setUpdateUserId(user.getId() + "");

		return R.ok(assessTypeService.updateById(assessType));
	}

	/**
	 * 通过id删除评估类型表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除评估类型表", notes = "通过id删除评估类型表")
	@SysLog("通过id删除评估类型表")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		AssessType byId = assessTypeService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");


		return R.ok(assessTypeService.updateAll(byId));
	}

}
