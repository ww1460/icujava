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

package com.pig4cloud.pigx.ccxxicu.controller.pressuresore;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.InevitablePressureSore;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.pressuresore.InevitablePressureSoreService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 难免压疮申报表
 *
 * @author pigx code generator
 * @date 2019-08-26 11:09:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/inevitablePressureSore" )
@Api(value = "inevitablePressureSore", tags = "难免压疮申报表管理")
public class InevitablePressureSoreController {

    private final InevitablePressureSoreService inevitablePressureSoreService;


	/**
	 * 患者表
	 */
	private final PatientService patientService;



	/**
     * 分页查询
     * @param page 分页对象
     * @param inevitablePressureSore 难免压疮申报表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPreUnavoidablePressureUlcerDeclarationFormPage(Page page, InevitablePressureSore inevitablePressureSore) {
    	inevitablePressureSore.setDelFlag(0);
        return R.ok(inevitablePressureSoreService.selectPaging(page, inevitablePressureSore));
    }


	/**
	 * 新增前跳转页面
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@GetMapping("/preJump")
	public R preJump(String patientId){
		Map<String,Object> test = new HashMap<>();
		List<String> dictionaries  = new ArrayList<>();
		dictionaries.add("declare_reason");
		dictionaries.add("preventive_nursing_measures");
		dictionaries.add("pre_position");
		dictionaries.add("by_stages");
		test.put("dictionaries",dictionaries);
		Patient patient = patientService.getByPatientId(patientId);
		test.put("patient",patient);
		return R.ok(test);
	}


    /**
     * 通过id查询难免压疮申报表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(inevitablePressureSoreService.getById(id));
    }

    /**
     * 新增难免压疮申报表
     * @param inevitablePressureSore 难免压疮申报表
     * @return R
     */
    @ApiOperation(value = "新增难免压疮申报表", notes = "新增难免压疮申报表")
    @SysLog("新增难免压疮申报表" )
    @PostMapping("/add")
    public R save(@RequestBody InevitablePressureSore inevitablePressureSore) {
        return R.ok(inevitablePressureSoreService.save(inevitablePressureSore));
    }

    /**
     * 修改难免压疮申报表
     * @param inevitablePressureSore 难免压疮申报表
     * @return R
     */
    @ApiOperation(value = "修改难免压疮申报表", notes = "修改难免压疮申报表")
    @SysLog("修改难免压疮申报表" )
    @PostMapping("/update")
    public R updateById(@RequestBody InevitablePressureSore inevitablePressureSore) {
        return R.ok(inevitablePressureSoreService.updateById(inevitablePressureSore));
    }

    /**
     * 通过id删除难免压疮申报表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除难免压疮申报表", notes = "通过id删除难免压疮申报表")
    @SysLog("通过id删除难免压疮申报表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(inevitablePressureSoreService.removeById(id));
    }

}
