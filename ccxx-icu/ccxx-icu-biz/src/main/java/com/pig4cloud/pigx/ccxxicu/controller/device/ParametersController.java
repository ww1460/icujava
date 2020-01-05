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

package com.pig4cloud.pigx.ccxxicu.controller.device;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Parameters;
import com.pig4cloud.pigx.ccxxicu.service.device.ParametersService;
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
 * 设备参数表
 *
 * @author pigx code generator
 * @date 2019-08-05 09:40:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/parameters")
@Api(value = "parameters", tags = "设备参数表管理")
public class ParametersController {

	private final ParametersService parametersService;

	/**
	 * 分页查询
	 *
	 * @param page       分页对象
	 * @param parameters 设备参数表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getDevParametersPage(Page page, Parameters parameters) {
		parameters.setDeptId(SecurityUtils.getUser().getDeptId() + ""); //科室
		return R.ok(parametersService.page(page, Wrappers.query(parameters)));
	}


	/**
	 * 通过id查询设备参数表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(parametersService.getById(id));
	}

	/**
	 * 新增设备参数表
	 *
	 * @param parameters 设备参数表
	 * @return R
	 */
	@ApiOperation(value = "新增设备参数表", notes = "新增设备参数表")
	@SysLog("新增设备参数表")
	@PostMapping("/add")
	public R save(@RequestBody Parameters parameters) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}

		parameters.setCreateTime(LocalDateTime.now());
		parameters.setCreateUserId(user.getId() + "");
		parameters.setDelFlag(0);
		parameters.setDeptId(user.getDeptId()+"");
		return R.ok(parametersService.save(parameters));
	}

	/**
	 * 修改设备参数表
	 *
	 * @param parameters 设备参数表
	 * @return R
	 */
	@ApiOperation(value = "修改设备参数表", notes = "修改设备参数表")
	@SysLog("修改设备参数表")
	@PostMapping("/update")
	public R updateById(@RequestBody Parameters parameters) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}

		return R.ok(parametersService.updateById(parameters));
	}

	/**
	 * 通过id删除设备参数表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除设备参数表", notes = "通过id删除设备参数表")
	@SysLog("通过id删除设备参数表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {


		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}
		
		Parameters parameters = parametersService.getById(id);
		parameters.setDelFlag(1); // 删除标识
		parameters.setDelTime(LocalDateTime.now()); // 删除时间
		parameters.setDelUserId(SecurityUtils.getUser().getId() + ""); //删除的护士信息
		return R.ok(parametersService.updateById(parameters));
	}

	/**
	 * 查询某设备型号的参数
	 *
	 * @param devModel
	 * @return
	 */
	@ApiOperation(value = "查询某设备型号的参数", notes = "查询某设备型号的参数")
	@SysLog("查询某设备型号的参数")
	@GetMapping("/parameters")
	public R selectPaging(@RequestParam String devModel) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}

		return R.ok(parametersService.list(Wrappers.<Parameters>query().lambda()
						.eq(Parameters::getDelFlag, 0)
						.eq(Parameters::getDevModel, devModel)
						.eq(Parameters::getDeptId, user.getDeptId() + "")
				)
		);
	}


}
