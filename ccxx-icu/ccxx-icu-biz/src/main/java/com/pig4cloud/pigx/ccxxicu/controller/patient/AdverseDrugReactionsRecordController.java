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
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.AdverseDrugReactionsRecord;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.patient.AdverseDrugReactionsRecordService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
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
 * 患者药物不良反应记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:46:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/adversedrugreactionsrecord" )
@Api(value = "adversedrugreactionsrecord", tags = "患者药物不良反应记录表管理")
public class AdverseDrugReactionsRecordController {

    private final AdverseDrugReactionsRecordService adverseDrugReactionsRecordService;

    private final PatientService patientService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param adverseDrugReactionsRecord 患者药物不良反应记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPatAdverseDrugReactionsRecordPage(Page page, AdverseDrugReactionsRecord adverseDrugReactionsRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(adverseDrugReactionsRecordService.selectPaging(page,adverseDrugReactionsRecord));

		}

    	adverseDrugReactionsRecord.setDeptId(user.getDeptId()+"");
        return R.ok(adverseDrugReactionsRecordService.selectPaging(page,adverseDrugReactionsRecord));
    }

	/***
	 * 新增前查询数据
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "新增前用患者id查询数据",notes = "新增前用患者id查询数据")
	@SysLog("新增前用患者id查询数据")
	@GetMapping("/preJump")
    public R preJump(String patientId){
		return R.ok(patientService.getByPatientId(patientId));
	}






    /**
     * 通过id查询患者药物不良反应记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(adverseDrugReactionsRecordService.getById(id));
    }



    /**
     * 新增患者药物不良反应记录表
     * @param adverseDrugReactionsRecord 患者药物不良反应记录表
     * @return R
     */
    @ApiOperation(value = "新增患者药物不良反应记录表", notes = "新增患者药物不良反应记录表")
    @SysLog("新增患者药物不良反应记录表" )
    @PostMapping("/add")
    public R save(@RequestBody AdverseDrugReactionsRecord adverseDrugReactionsRecord) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(adverseDrugReactionsRecord.getPatientId())) {
			return R.failed(1, "操作失败！");
		}

		adverseDrugReactionsRecord.setDeptId(user.getDeptId()+"");
		adverseDrugReactionsRecord.setDelFlag(0);
		adverseDrugReactionsRecord.setHisDoctorsAdviceId(SnowFlake.getId()+"");
		adverseDrugReactionsRecord.setCreateTime(LocalDateTime.now());//创建时间
		adverseDrugReactionsRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");//  患者关系
		return R.ok(adverseDrugReactionsRecordService.save(adverseDrugReactionsRecord));
    }

    /**
     * 修改患者药物不良反应记录表
     * @param adverseDrugReactionsRecord 患者药物不良反应记录表
     * @return R
     */
    @ApiOperation(value = "修改患者药物不良反应记录表", notes = "修改患者药物不良反应记录表")
    @SysLog("修改患者药物不良反应记录表" )
    @PostMapping("/update")
    public R updateById(@RequestBody AdverseDrugReactionsRecord adverseDrugReactionsRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

        return R.ok(adverseDrugReactionsRecordService.updateById(adverseDrugReactionsRecord));
    }

    /**
     * 通过id删除患者药物不良反应记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除患者药物不良反应记录表", notes = "通过id删除患者药物不良反应记录表")
    @SysLog("通过id删除患者药物不良反应记录表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		AdverseDrugReactionsRecord byId = adverseDrugReactionsRecordService.getById(id);

			byId.setDelFlag(1);
			byId.setDelTime(LocalDateTime.now());
			byId.setDelUserId(SecurityUtils.getUser().getId()+"");

		return R.ok(adverseDrugReactionsRecordService.updateById(byId));
    }

}
