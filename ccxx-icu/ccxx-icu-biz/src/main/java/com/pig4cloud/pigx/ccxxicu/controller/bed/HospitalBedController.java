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

package com.pig4cloud.pigx.ccxxicu.controller.bed;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.bed.HospitalBedService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 床位表
 *
 * @author yyj
 * @date 2019-08-07 21:24:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/hospitalBed")
@Api(value = "hospitalBed", tags = "床位表管理")
public class HospitalBedController {

	private final HospitalBedService hospitalBedService;

	/**
	 * 分页查询
	 * 管理员查询所有科室的床位 护士长查询该科室的床位
	 *
	 * @param page        分页对象
	 * @param hospitalBed 床位表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getNurHospitalBedPage(Page page, HospitalBed hospitalBed) {

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		PigxUser user = SecurityUtils.getUser();
		//判断是否登录 和  登录人是否存在权限
		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		hospitalBed.setDelFlag(0);

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员返回其对应条件的数据
			return R.ok(hospitalBedService.selectByPage(page,hospitalBed));

		} else {
			//护士长返回其科室的数据
			hospitalBed.setDeptId(user.getDeptId() + "");

			return R.ok(hospitalBedService.selectByPage(page,hospitalBed));
		}


	}

	/**
	 * 验证床位名
	 *
	 * @param hospitalBed 床位名
	 * @return
	 */
	@GetMapping("/bedName")
	public R bedName(HospitalBed hospitalBed) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		HospitalBed condition = new HospitalBed();
		condition.setBedName(hospitalBed.getBedName());
		condition.setDelFlag(0);
		condition.setDeptId(user.getDeptId()+"");
		HospitalBed one = hospitalBedService.getOne(new QueryWrapper<>(condition));
		if (one!= null && StringUtils.isNotEmpty(hospitalBed.getBedId())) {

			if (one.getBedId().equals(hospitalBed.getBedId())) {

				return R.ok();

			}

		}
		return R.ok(one);
	}

	/**
	 * 验证床位编号
	 *
	 * @param hospitalBed 床位编号
	 * @return
	 */
	@GetMapping("/bedCode")
	public R bedCode(HospitalBed hospitalBed) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		HospitalBed condition = new HospitalBed();
		condition.setBedCode(hospitalBed.getBedCode());
		condition.setDelFlag(0);
		condition.setDeptId(user.getDeptId()+"");
		HospitalBed one = hospitalBedService.getOne(new QueryWrapper<>(condition));
		if (one!= null && StringUtils.isNotEmpty(hospitalBed.getBedId())) {

			if (one.getBedId().equals(hospitalBed.getBedId())) {

				return R.ok();

			}

		}
		return R.ok(one);
	}



	/**
	 * 通过id查询床位表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getById/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(hospitalBedService.getById(id));
	}

	/**
	 * 新增床位表
	 *
	 * @param hospitalBed 床位表
	 * @return R
	 */
	@ApiOperation(value = "新增床位表", notes = "新增床位表")
	@SysLog("新增床位表")
	@PostMapping("/add")
	public R save(@RequestBody HospitalBed hospitalBed) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		hospitalBed.setDelFlag(0);
		hospitalBed.setCreateTime(LocalDateTime.now());
		hospitalBed.setCreateUserId(user.getId() + "");
		long id = SnowFlake.getId();
		hospitalBed.setBedId(id + "");
		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(hospitalBedService.save(hospitalBed));

		}
		//护士长
		hospitalBed.setDeptId(user.getDeptId() + "");

		return R.ok(hospitalBedService.save(hospitalBed));


	}

	/**
	 * 修改床位表
	 *
	 * @param hospitalBed 床位表
	 * @return R
	 */
	@ApiOperation(value = "修改床位表", notes = "修改床位表")
	@SysLog("修改床位表")
	@PostMapping("/update")
	public R updateById(@RequestBody HospitalBed hospitalBed) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		hospitalBed.setUpdateTime(LocalDateTime.now());
		hospitalBed.setUpdateUserId(user.getId() + "");

		return R.ok(hospitalBedService.updateById(hospitalBed));
	}

	/**
	 * 通过id删除床位表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除床位表", notes = "通过id删除床位表")
	@SysLog("通过id删除床位表")
	@GetMapping("/deleteById/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		HospitalBed byId = hospitalBedService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		return R.ok(hospitalBedService.updateById(byId));
	}

	/**
	 * 查询登录护士科室下  某状态的床位
	 *
	 * @param hospitalBed
	 * @return
	 */
	@ApiOperation(value = "查询某科室下，某状态的床位", notes = "查询某科室下，某状态的床位")
	@SysLog("查询某科室下，某状态的床位")
	@PostMapping("/getDeptBed")
	public R getDeptBed(@RequestBody HospitalBed hospitalBed) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		hospitalBed.setDeptId(user.getDeptId() + "");

		return R.ok(hospitalBedService.selectByFlags(hospitalBed));

	}


}
