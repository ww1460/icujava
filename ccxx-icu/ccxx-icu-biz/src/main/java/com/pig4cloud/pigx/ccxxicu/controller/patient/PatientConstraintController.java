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

package com.pig4cloud.pigx.ccxxicu.controller.patient;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.PatientConstraint;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientConstraintService;
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
 * 患者约束记录
 *
 * @author pigx code generator
 * @date 2019-08-29 11:14:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/patientConstraint" )
@Api(value = "patientConstraint", tags = "患者约束记录管理")
public class PatientConstraintController {

    private final PatientConstraintService patientConstraintService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param patientConstraint 患者约束记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPatientConstraintPage(Page page, PatientConstraint patientConstraint) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}


		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(patientConstraintService.getByPage(page,patientConstraint));

		}

		patientConstraint.setDeptId(user.getDeptId()+"");

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			patientConstraint.setCreateUserId(user.getId()+"");
		}

		return R.ok(patientConstraintService.getByPage(page,patientConstraint));
    }


    /**
     * 通过id查询患者约束记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(patientConstraintService.getById(id));
    }

    /**
     * 新增患者约束记录
     * @param patientConstraint 患者约束记录
     * @return R
     */
    @ApiOperation(value = "新增患者约束记录", notes = "新增患者约束记录")
    @SysLog("新增患者约束记录" )
    @PostMapping("/add")
    public R save(@RequestBody PatientConstraint patientConstraint) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(patientConstraint.getPatientId())) {
			return R.failed(1, "操作失败！");
		}

		patientConstraint.setDelFlag(0);
		patientConstraint.setCreateTime(LocalDateTime.now());
		patientConstraint.setCreateUserId(user.getId()+"");
		patientConstraint.setPatientConstraintId(SnowFlake.getId()+"");
		patientConstraint.setDeptId(user.getDeptId()+"");

        return R.ok(patientConstraintService.save(patientConstraint));
    }

    /**
     * 修改患者约束记录
     * @param patientConstraint 患者约束记录
     * @return R
     */
    @ApiOperation(value = "修改患者约束记录", notes = "修改患者约束记录")
    @SysLog("修改患者约束记录" )
    @PostMapping("/update")
    public R updateById(@RequestBody PatientConstraint patientConstraint) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		patientConstraint.setUpdateUserId(user.getId()+"");

		return R.ok(patientConstraintService.updateById(patientConstraint));
    }

    /**
     * 通过id删除患者约束记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除患者约束记录", notes = "通过id删除患者约束记录")
    @SysLog("通过id删除患者约束记录" )
    @GetMapping("/del/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		PatientConstraint byId = patientConstraintService.getById(id);

		byId.setUpdateUserId(user.getId()+"");
		byId.setDelFlag(1);

		return R.ok(patientConstraintService.updateById(byId));
    }

}
