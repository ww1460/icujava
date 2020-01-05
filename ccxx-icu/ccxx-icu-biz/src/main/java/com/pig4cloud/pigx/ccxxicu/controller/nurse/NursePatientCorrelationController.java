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
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientInfoBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
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
 * 患者和护士关联表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:08:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursepatientcorrelation" )
@Api(value = "nursepatientcorrelation", tags = "患者和护士关联表管理")
public class NursePatientCorrelationController {

    private final NursePatientCorrelationService nursePatientCorrelationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param nursePatientCorrelation 患者和护士关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNurNursePatientCorrelationPage(Page page, NursePatientCorrelation nursePatientCorrelation) {
        return R.ok(nursePatientCorrelationService.page(page, Wrappers.query(nursePatientCorrelation)));
    }


    /**
     * 通过id查询患者和护士关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(nursePatientCorrelationService.getById(id));
    }

    /**
     * 新增患者和护士关联表
     * @param nursePatientCorrelation 患者和护士关联表
     * @return R
     */
    @ApiOperation(value = "新增患者和护士关联表", notes = "新增患者和护士关联表")
    @SysLog("新增患者和护士关联表" )
    @PostMapping("/add")

    public R save(@RequestBody NursePatientCorrelation nursePatientCorrelation) {
        return R.ok(nursePatientCorrelationService.save(nursePatientCorrelation));
    }

    /**
     * 修改患者和护士关联表
     * @param nursePatientCorrelation 患者和护士关联表
     * @return R
     */
    @ApiOperation(value = "修改患者和护士关联表", notes = "修改患者和护士关联表")
    @SysLog("修改患者和护士关联表" )
    @PutMapping
    public R updateById(@RequestBody NursePatientCorrelation nursePatientCorrelation) {
        return R.ok(nursePatientCorrelationService.updateById(nursePatientCorrelation));
    }

    /**
     * 通过id删除患者和护士关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除患者和护士关联表", notes = "通过id删除患者和护士关联表")
    @SysLog("通过id删除患者和护士关联表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(nursePatientCorrelationService.removeById(id));
    }


	/**
	 * 条件查询数据，护士查询患者
	 * @param nursePatientCorrelation
	 * @return
	 */
	@ApiOperation(value = "条件查询数据，护士查询患者",notes = "条件查询数据，护士查询患者")
	@SysLog("条件查询数据，护士查询患者")
    @GetMapping("/selectPatient")
    public R selectPatient(NursePatientCorrelation nursePatientCorrelation){


		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 护士长查询当前科室 AdmissionDepartment */

			return R.ok(nursePatientCorrelationService.selectAll(nursePatientCorrelation));
		}else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {
			/* 护士查询护士关联的患者，先通过护士id查询向关联的患者id*/
			nursePatientCorrelation.setNurseId(SecurityUtils.getUser().getId()+"");// 护士id
			return R.ok(nursePatientCorrelationService.selectAll(nursePatientCorrelation));
		}

    	return R.ok(nursePatientCorrelationService.selectAll(nursePatientCorrelation));
	}

	@ApiOperation(value = "条件数据全查",notes = "条件数据全查")
	@SysLog("条件数据全查")
	@GetMapping("/selectAll")
	public R selectAll(NursePatientCorrelation nursePatientCorrelation){
	return R.ok(nursePatientCorrelationService.selectAll(nursePatientCorrelation));
	}



	@ApiOperation(value = "查询患者信息",notes = "查询患者信息")
	@SysLog("查询患者信息")
	@GetMapping("/patientInfo")
	public R patientInfo(){

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}

		PatientInfoBo patientInfoBo = new PatientInfoBo();

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			patientInfoBo.setNurseId(user.getId()+"");

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			patientInfoBo.setDeptId(user.getDeptId()+"");
		}

		return R.ok(nursePatientCorrelationService.getPatientId(patientInfoBo));

	}


}
