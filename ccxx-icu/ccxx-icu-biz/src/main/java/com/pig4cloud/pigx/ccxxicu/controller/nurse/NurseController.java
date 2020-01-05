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

package com.pig4cloud.pigx.ccxxicu.controller.nurse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UserBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseInfo;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
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
 * 护士表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:02:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nurse")
@Api(value = "nurse", tags = "护士表管理")
public class NurseController {

	private final NurseService nurseService;

	/**
	 * 分页查询
	 *
	 * @param page  分页对象
	 * @param nurse 护士表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getNurNursePage(Page page, Nurse nurse) {

		nurse.setDelFlag(0);

		List<String> roleCodes = SecurityUtils.getRoleCodes();
		//判断是不是管理员
		if (roleCodes.size() > 0) {

			for (int i = 0; i < roleCodes.size(); i++) {

				if (roleCodes.get(i).equals("ROLE_ADMIN")) {

					return R.ok(nurseService.page(page, Wrappers.query(nurse).lambda().orderByDesc(Nurse::getCreateTime)));

				}
			}
		}

		//护士长查看
		PigxUser user = SecurityUtils.getUser();

		if (user != null) {

			Integer deptId = user.getDeptId();

			nurse.setDeptId(deptId + "");

		}


		return R.ok(nurseService.page(page, Wrappers.query(nurse).lambda().orderByDesc(Nurse::getCreateTime)));
	}

	/**
	 * 新增护士表
	 *
	 * @param userBo 护士表
	 * @return R
	 */
	@ApiOperation(value = "新增护士表", notes = "新增护士表")
	@SysLog("新增护士表")
	@PostMapping("/add")
	public R save(@RequestBody UserBo userBo) {
		return R.ok(nurseService.add(userBo));
	}

	/**
	 * 修改护士表
	 *
	 * @param nurseInfo 护士个人信息
	 * @return R
	 */
	@ApiOperation(value = "修改护士表", notes = "修改护士表")
	@SysLog("修改护士表")
	@PostMapping("/update")
	public R updateById(@RequestBody NurseInfo nurseInfo) {

		return R.ok(nurseService.update(nurseInfo));
	}


	/**
	 * 修改护士个人签名
	 *
	 * @param nurse 修改护士个人签名
	 * @return R
	 */
	@ApiOperation(value = "修改护士个人签名", notes = "修改护士个人签名")
	@SysLog("修改护士表")
	@PostMapping("/updateSignature")
	public R updateById(@RequestBody Nurse nurse) {

		return R.ok(nurseService.updateById(nurse));
	}



	/**
	 * 通过id删除护士表
	 *
	 * @param nurse
	 * @return R
	 */
	@ApiOperation(value = "通过id删除护士表", notes = "通过id删除护士表")
	@SysLog("通过id删除护士表,参数nurseId")
	@PostMapping("/del")
	public R removeById(@RequestBody Nurse nurse) {
		String nurseId = nurse.getNurseId();

		return R.ok(nurseService.deleteByNurseId(nurseId));
	}


	@ApiOperation(value = "获取当前登录的护士信息", notes = "获取当前登录的护士信息")
	@SysLog("获取当前登录的护士信息")
	@PostMapping("/nurseInfo")
	public R getNurseInfo() {
		/**
		 * 获取当前登录护士
		 */
		PigxUser user = SecurityUtils.getUser();

		Integer userId = user.getId();

		return R.ok(nurseService.getNurseInfo(userId));

	}


	@ApiOperation(value = "根据护士的nurseId获取护士信息", notes = "根据护士的nurseId获取护士信息")
	@SysLog("根据护士的nurseId获取护士信息，参数nurseId")
	@PostMapping("/getById")
	public R nurseInfo(@RequestBody Nurse nurse) {

		String nurseId = nurse.getNurseId();

		nurse.setDelFlag(0);

		return R.ok(nurseService.getNurseInfo(Integer.parseInt(nurseId)));

	}


	@ApiOperation(value = "获取该科室所有非护士长护士", notes = "获取该科室所有非护士长护士")
	@SysLog("获取该科室所有非护士长护士")
	@PostMapping("/getDeptNurse")
	public R getDeptNurse() {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//护士长获取该科室的所有非护士长护士
		if (roleCodes.get(0).equals("ROLE_MATRON")) {

			Integer deptId = user.getDeptId();

			return R.ok(nurseService.selectByDept(deptId + ""));

		}
		//如果是管理员 获取所有非护士长 护士
		if (roleCodes.get(0).equals("ROLE_ADMIN")) {

			return R.ok(nurseService.selectByDept(null));

		}

		return R.failed();
	}

	@ApiOperation(value = "获取该科室所有护士", notes = "获取该科室所有护士")
	@SysLog("获取该科室所有护士")
	@PostMapping("/getDeptAllNurse")
	public R getDeptAllNurse() {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		//如果是管理员 获取所有非护士长 护士
		if (roleCodes.get(0).equals("ROLE_ADMIN")) {

			return R.ok(nurseService.selectAllNurse(null));

		}

		return R.ok(nurseService.selectAllNurse(user.getDeptId() + ""));
	}

}
