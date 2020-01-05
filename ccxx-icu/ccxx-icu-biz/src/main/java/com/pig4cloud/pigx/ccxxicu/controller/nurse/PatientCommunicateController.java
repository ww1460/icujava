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
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.PatientCommunicate;
import com.pig4cloud.pigx.ccxxicu.service.nurse.PatientCommunicateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 护士与患者沟通
 *
 * @author pigx code generator
 * @date 2019-09-03 20:31:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/patientcommunicate" )
@Api(value = "patientcommunicate", tags = "护士与患者沟通管理")
public class PatientCommunicateController {

    private final PatientCommunicateService patientCommunicateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param patientCommunicate 护士与患者沟通
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNurPatientCommunicatePage(Page page, PatientCommunicate patientCommunicate) {
    	patientCommunicate.setDelFlag(0);
        return R.ok(patientCommunicateService.page(page, Wrappers.query(patientCommunicate)));
    }


    /**
     * 通过id查询护士与患者沟通
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(patientCommunicateService.getById(id));
    }

    /**
     * 新增护士与患者沟通
     * @param patientCommunicate 护士与患者沟通
     * @return R
     */
    @ApiOperation(value = "新增护士与患者沟通", notes = "新增护士与患者沟通")
    @SysLog("新增护士与患者沟通" )
    @PostMapping("/add")
    public R save(@RequestBody PatientCommunicate patientCommunicate) {
    	patientCommunicate.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
		patientCommunicate.setCreateTime(LocalDateTime.now());
		patientCommunicate.setCreateUserId(SecurityUtils.getUser().getId()+"");//用户
        return R.ok(patientCommunicateService.save(patientCommunicate));
    }

    /**
     * 修改护士与患者沟通
     * @param patientCommunicate 护士与患者沟通
     * @return R
     */
    @ApiOperation(value = "修改护士与患者沟通", notes = "修改护士与患者沟通")
    @SysLog("修改护士与患者沟通" )
    @PostMapping("/update")
    public R updateById(@RequestBody PatientCommunicate patientCommunicate) {
        return R.ok(patientCommunicateService.updateById(patientCommunicate));
    }

    /**
     * 通过id删除护士与患者沟通
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士与患者沟通", notes = "通过id删除护士与患者沟通")
    @SysLog("通过id删除护士与患者沟通" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		PatientCommunicate byId = patientCommunicateService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		return R.ok(patientCommunicateService.updateById(byId));
    }

	/**
	 * 医患沟通全查数据
	 * @param patientCommunicate
	 * @return
	 */
	@ApiOperation(value = "医患沟通全查",notes = "医患沟通全查")
    @SysLog("医患沟通全查")
    @GetMapping("/selectAll")
    public R selectAll(PatientCommunicate patientCommunicate){
    	return R.ok(patientCommunicateService.selectAll(patientCommunicate));
	}






}
