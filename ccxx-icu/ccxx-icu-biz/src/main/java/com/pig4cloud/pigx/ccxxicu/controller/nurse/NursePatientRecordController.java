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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.PatientEnum;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 护士看护患者记录表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:12:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursepatientrecord" )
@Api(value = "nursepatientrecord", tags = "护士看护患者记录表管理")
public class NursePatientRecordController {

    private final NursePatientRecordService nursePatientRecordService;

	/**
	 * 护士表
	 */
	private final NurseService nurseService;

	/**
	 * 患者
	 */
	private final PatientService patientService;

	/**
	 * 护士患者关联表
	 */
	private final NursePatientCorrelationService nursePatientCorrelationService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param nursePatientRecord 护士看护患者记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNurNursePatientRecordPage(Page page, NursePatientRecord nursePatientRecord) {
        return R.ok(nursePatientRecordService.page(page, Wrappers.query(nursePatientRecord)));
    }


	/**
	 * 新增前跳转页面
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面 ")
    @PostMapping("/preJump")
    public R preJump(){

		/* 当前登录的护士科室，查询一下当前科室的护士 */
		return R.ok(nurseService.selectByDept(SecurityUtils.getUser().getDeptId()+""));
    }


    /**
     * 通过id查询护士看护患者记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(nursePatientRecordService.getById(id));
    }


    /**
     * 新增护士看护患者记录表
     * @param nursePatientRecord 护士看护患者记录表
     * @return R
     */
    @ApiOperation(value = "新增护士看护患者记录表", notes = "新增护士看护患者记录表")
    @SysLog("新增护士看护患者记录表" )
    @PostMapping("/add")
    public R save(@RequestBody NursePatientRecord nursePatientRecord) {

		return R.ok(nursePatientRecordService.nurseRelationPatient(nursePatientRecord));
    }

    /**
     * 修改护士看护患者记录表
     * @param nursePatientRecord 护士看护患者记录表
     * @return R
     */
    @ApiOperation(value = "修改护士看护患者记录表", notes = "修改护士看护患者记录表")
    @SysLog("修改护士看护患者记录表" )
    @PostMapping("/update")
    public R updateById(@RequestBody NursePatientRecord nursePatientRecord) {


        return R.ok(nursePatientRecordService.nursePatientupdate(nursePatientRecord));
    }

	/**
	 * 护士患者断开关联
	 * @return
	 */
	@ApiOperation(value = "护士患者断开关联",notes = "护士患者断开关联")
	@SysLog("护士患者断开关联")
	@PostMapping("/disconnection")
    public R disconnection(@RequestBody NursePatientRecord nursePatientRecord){

		return R.ok(nursePatientRecordService.disconnection(nursePatientRecord));
	}






    /**
     * 通过id删除护士看护患者记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士看护患者记录表", notes = "通过id删除护士看护患者记录表")
    @SysLog("通过id删除护士看护患者记录表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		NursePatientRecord byId = nursePatientRecordService.getById(id);
		byId.setDelFlag(1); // 删除
		byId.setDelTime(LocalDateTime.now()); // 删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");  // 删除用户

		return R.ok(nursePatientRecordService.updateById(byId));
    }


	/**
	 * 条件分页查询数据
	 * @param page
	 * @param nursePatientRecord
	 * @return
	 */
    @ApiOperation(value = "条件分页查询数据",notes = "条件分页查询数据")
	@SysLog("条件分页查询数据")
	@GetMapping("/selectPaging")
    public R selectPaging(Page page,NursePatientRecord nursePatientRecord){

		Map<String,Object> test = new HashMap<>();
		String depat = SecurityUtils.getUser().getDeptId() + "";

		/* 当前科室下的护士 */

		List<Nurse> nurses = nurseService.selectByDept(depat);

		if (CollectionUtils.isNotEmpty(nurses)){
			test.put("nurses",nurses);

		}

		/* 当前科室下的在科患者 */
		PatientBo patientBo = new PatientBo();
		patientBo.setAdmissionDepartment(depat);
		patientBo.setEntryState(PatientEnum.IN_SCIENCE.getCode());//在科标识
			List<PatientVo> patients = patientService.inSciencePatientAll(patientBo);
			if (CollectionUtils.isNotEmpty(patients)){
				test.put("patients",patients);
			}
		IPage pageList = nursePatientRecordService.selectPaging(page, nursePatientRecord);

		if (pageList.getSize()>0){
			test.put("pageList",pageList);
		}
		return  R.ok(test);
	}












}
