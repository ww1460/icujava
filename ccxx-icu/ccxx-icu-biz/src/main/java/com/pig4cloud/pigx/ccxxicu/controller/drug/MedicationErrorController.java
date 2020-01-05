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

package com.pig4cloud.pigx.ccxxicu.controller.drug;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.MedicationError;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.drug.MedicationErrorService;
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
 * 用药错误登记
 *
 * @author pigx code generator
 * @date 2019-09-18 16:31:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/medicationError")
@Api(value = "medicationError", tags = "用药错误登记管理")
public class MedicationErrorController {

	private final MedicationErrorService medicationErrorService;

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param medicationError 用药错误登记
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getMedicationErrorPage(Page page, MedicationError medicationError) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		medicationError.setDelFlag(0);

		if (!roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			medicationError.setDeptId(user.getDeptId()+"");
		}


		return R.ok(medicationErrorService.page(page, Wrappers.query(medicationError).lambda().orderByDesc(MedicationError::getCreateTime)));
	}

	/**
	 * 通过id查询用药错误登记
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


		return R.ok(medicationErrorService.getById(id));
	}

	/**
	 * 新增用药错误登记
	 *
	 * @param medicationError 用药错误登记
	 * @return R
	 */
	@ApiOperation(value = "新增用药错误登记", notes = "新增用药错误登记")
	@SysLog("新增用药错误登记")
	@PostMapping("/add")
	public R save(@RequestBody MedicationError medicationError) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		medicationError.setDelFlag(0);
		medicationError.setCreateTime(LocalDateTime.now());
		medicationError.setMedicationErrorId(SnowFlake.getId() + "");
		medicationError.setDeptId(user.getDeptId() + "");

		return R.ok(medicationErrorService.save(medicationError));
	}

	/**
	 * 修改用药错误登记
	 *
	 * @param medicationError 用药错误登记
	 * @return R
	 */
	@ApiOperation(value = "修改用药错误登记", notes = "修改用药错误登记")
	@SysLog("修改用药错误登记")
	@PostMapping("/update")
	public R updateById(@RequestBody MedicationError medicationError) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		medicationError.setUpdateUserId(user.getId() + "");

		return R.ok(medicationErrorService.updateById(medicationError));
	}

	/**
	 * 通过id删除用药错误登记
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除用药错误登记", notes = "通过id删除用药错误登记")
	@SysLog("通过id删除用药错误登记")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		MedicationError byId = medicationErrorService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");


		return R.ok(medicationErrorService.updateById(byId));
	}

}
