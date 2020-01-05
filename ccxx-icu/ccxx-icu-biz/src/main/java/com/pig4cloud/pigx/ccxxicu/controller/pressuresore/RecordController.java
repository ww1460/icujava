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
import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.Record;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.pressuresore.RecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 压疮记录表 
 *
 * @author pigx code generator
 * @date 2019-08-30 16:19:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pressureSore/recorder" )
@Api(value = "recorder", tags = "压疮记录表 管理")
public class RecordController {

    private final RecordService recordService;
	/**
	 * 患者表
	 */
	private final PatientService patientService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param record 压疮记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPreSoreRecordPage(Page page, Record record) {
        return R.ok(recordService.selectPaging(page, record));
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
		dictionaries.add("pre_position");
		dictionaries.add("by_stages");
		dictionaries.add("preventive_measures");
		dictionaries.add("local_processing");
		test.put("dictionaries",dictionaries);

		Patient patient = patientService.getByPatientId(patientId);
		test.put("patient",patient);
		return R.ok(test);
	}



	/**
     * 通过id查询压疮记录表 
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(recordService.getById(id));
    }

    /**
     * 新增压疮记录表 
     * @param record 压疮记录表
     * @return R
     */
    @ApiOperation(value = "新增压疮记录表 ", notes = "新增压疮记录表 ")
    @SysLog("新增压疮记录表 " )
    @PostMapping("/add")
    public R save(@RequestBody Record record) {
        return R.ok(recordService.save(record));
    }

    /**
     * 修改压疮记录表 
     * @param record 压疮记录表
     * @return R
     */
    @ApiOperation(value = "修改压疮记录表 ", notes = "修改压疮记录表 ")
    @SysLog("修改压疮记录表 " )
   @PostMapping("/update")
    @PreAuthorize("@pms.hasPermission('generator_presorerecord_edit')" )
    public R updateById(@RequestBody Record record) {
        return R.ok(recordService.updateById(record));
    }

    /**
     * 通过id删除压疮记录表 
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除压疮记录表 ", notes = "通过id删除压疮记录表 ")
    @SysLog("通过id删除压疮记录表 " )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		Record byId = recordService.getById(id);
		byId.setDelFlag(1);	// 删除标识
		byId.setDelTime(LocalDateTime.now());//删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");//护士id
    	return R.ok(recordService.updateById(byId));
    }

}
