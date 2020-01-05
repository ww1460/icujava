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

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 患者与床位关联表
 *
 * @author pigx code generator
 * @date 2019-08-08 14:09:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/patientBedCorrelation" )
@Api(value = "patientBedCorrelation", tags = "患者与床位关联表管理")
public class PatientBedCorrelationController {

	/**
	 *
	 *
	 * 此业务层暂时无用   后续考虑删除
	 *  此业务层暂时无用   后续考虑删除
	 *   此业务层暂时无用   后续考虑删除
	 *    此业务层暂时无用   后续考虑删除
	 *     此业务层暂时无用   后续考虑删除
	 *      此业务层暂时无用   后续考虑删除
	 *       此业务层暂时无用   后续考虑删除
	 *        此业务层暂时无用   后续考虑删除
	 *
	 *
	 */





   /* private final PatientBedCorrelationService patientBedCorrelationService;

    *//**
     * 分页查询
     * @param page 分页对象
     * @param patientBedCorrelation 患者与床位关联表
     * @return
     *//*
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPatientBedCorrelationPage(Page page, PatientBedCorrelation patientBedCorrelation) {
        return R.ok(patientBedCorrelationService.page(page, Wrappers.query(patientBedCorrelation)));
    }


    *//**
     * 通过id查询患者与床位关联表
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(patientBedCorrelationService.getById(id));
    }

    *//**
     * 新增患者与床位关联表
     * @param patientBedCorrelation 患者与床位关联表
     * @return R
     *//*
    @ApiOperation(value = "新增患者与床位关联表", notes = "新增患者与床位关联表")
    @SysLog("新增患者与床位关联表" )
    @PostMapping
    public R save(@RequestBody PatientBedCorrelation patientBedCorrelation) {
        return R.ok(patientBedCorrelationService.save(patientBedCorrelation));
    }

    *//**
     * 修改患者与床位关联表
     * @param patientBedCorrelation 患者与床位关联表
     * @return R
     *//*
    @ApiOperation(value = "修改患者与床位关联表", notes = "修改患者与床位关联表")
    @SysLog("修改患者与床位关联表" )
    @PutMapping
    public R updateById(@RequestBody PatientBedCorrelation patientBedCorrelation) {
        return R.ok(patientBedCorrelationService.updateById(patientBedCorrelation));
    }

    *//**
     * 通过id删除患者与床位关联表
     * @param id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除患者与床位关联表", notes = "通过id删除患者与床位关联表")
    @SysLog("通过id删除患者与床位关联表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable String id) {
        return R.ok(patientBedCorrelationService.removeById(id));
    }*/

}
