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
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.DispensingDrugService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 配药
 *
 * @author pigx code generator
 * @date 2019-09-05 15:53:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dispensingdrug" )
@Api(value = "dispensingdrug", tags = "配药管理")
public class DispensingDrugController {

    private final DispensingDrugService dispensingDrugService;

	/**
	 * 患者
	 */
	private final PatientService patientService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param dispensingDrug 配药
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNurDispensingDrugPage(Page page, DispensingDrug dispensingDrug) {
        return R.ok(dispensingDrugService.page(page, Wrappers.query(dispensingDrug)));
    }


    /**
     * 通过id查询配药
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(dispensingDrugService.getById(id));
    }

    /**
     * 新增配药
     * @param dispensingDrug 配药
     * @return R
     */
    @ApiOperation(value = "新增配药", notes = "新增配药")
    @SysLog("新增配药" )
    @PostMapping("/add")
    public R save(@RequestBody DispensingDrug dispensingDrug) {
    	dispensingDrug.setDispensingDrugId(SnowFlake.getId()+""); //雪花
		if (!dispensingDrug.getAllergichistory().equals("")||dispensingDrug.getAllergichistory()!=null){
			Patient byPatientId = patientService.getByPatientId(dispensingDrug.getPatientId());
			byPatientId.setAllergichistory(dispensingDrug.getAllergichistory());
			if (patientService.save(byPatientId)==false){
			}
		}
        return R.ok(dispensingDrugService.save(dispensingDrug));
    }

    /**
     * 修改配药
     * @param dispensingDrug 配药
     * @return R
     */
    @ApiOperation(value = "修改配药", notes = "修改配药")
    @SysLog("修改配药" )
    @PostMapping("/update")
    public R updateById(@RequestBody DispensingDrug dispensingDrug) {
        return R.ok(dispensingDrugService.updateById(dispensingDrug));
    }

    /**
     * 通过id删除配药
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除配药", notes = "通过id删除配药")
    @SysLog("通过id删除配药" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		DispensingDrug byId = dispensingDrugService.getById(id);
		byId.setDelFlag(1);
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		byId.setDelTime(LocalDateTime.now());
		return R.ok(dispensingDrugService.updateById(byId));
    }

	/**
	 * 医嘱配药页面的展示
	 * @return
	 */
	@ApiOperation(value = "医嘱配药页面的展示",notes = "医嘱配药页面的展示")
	@SysLog("医嘱配药页面的展示")
	@GetMapping("/dispensingDrugPage")
	public R dispensingDrugPage(String doctorsAdviceId){
    	return R.ok(dispensingDrugService.dispensingDrugPage(doctorsAdviceId));
	}



}
