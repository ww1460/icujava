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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.TransferRecord;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.patient.TransferRecordService;
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
 * 患者转科交接记录
 *
 * @author pigx code generator
 * @date 2019-10-04 15:06:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/transferRecord" )
@Api(value = "transferRecord", tags = "患者转科交接记录管理")
public class TransferRecordController {

    private final TransferRecordService transferRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param TransferRecord 患者转科交接记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTransferRecordPage(Page page, TransferRecord TransferRecord) {
        return R.ok(transferRecordService.page(page, Wrappers.query(TransferRecord)));
    }

	/**
	 * 回填数据
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "回填数据", notes = "回填数据")
	@GetMapping("/oldData" )
	public R getOldData(@RequestParam String patientId) {
		return R.ok(transferRecordService.oldData(patientId));
	}


    /**
     * 通过患者id查询患者转科交接记录
     * @param patientId patientId
     * @return R
     */
    @ApiOperation(value = "通过患者id查询患者转科交接记录", notes = "通过患者id查询患者转科交接记录")
    @GetMapping("/transferRecord/{patientId}" )
    public R getById(@PathVariable("patientId" ) String patientId) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}
		if (StringUtils.isEmpty(patientId)) {
			return R.failed(1, "操作失败！");
		}

		//查询该患者的转科交接记录
		List<TransferRecord> list = transferRecordService.list(Wrappers.<TransferRecord>query().lambda()
				.eq(TransferRecord::getDelFlag, 0)
				.eq(TransferRecord::getDeptId, user.getDeptId() + "")
				.eq(TransferRecord::getPatientId, patientId)
				.orderByDesc(TransferRecord::getCreateTime)
		);

		if (CollectionUtils.isEmpty(list)) {

			return R.ok(new TransferRecord());

		}
		return R.ok(list.get(0));
    }

    /**
     * 新增患者转科交接记录
     * @param transferRecord 患者转科交接记录
     * @return R
     */
    @ApiOperation(value = "新增患者转科交接记录", notes = "新增患者转科交接记录")
    @SysLog("新增患者转科交接记录" )
    @PostMapping("/add")
    public R save(@RequestBody TransferRecord transferRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}
		if (StringUtils.isEmpty(transferRecord.getPatientId())) {
			return R.failed(1, "操作失败！");
		}


		transferRecord.setDeptId(user.getDeptId()+"");
		transferRecord.setCreateUserId(user.getId()+"");
		transferRecord.setCreateTime(LocalDateTime.now());
		transferRecord.setTransferRecordId(SnowFlake.getId()+"");
        return R.ok(transferRecordService.save(transferRecord));
    }

    /**
     * 修改患者转科交接记录
     * @param TransferRecord 患者转科交接记录
     * @return R
     */
    @ApiOperation(value = "修改患者转科交接记录", notes = "修改患者转科交接记录")
    @SysLog("修改患者转科交接记录" )
    @PostMapping("/update")
    public R updateById(@RequestBody TransferRecord TransferRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}

		TransferRecord.setUpdateUserId(user.getId()+"");

        return R.ok(transferRecordService.updateById(TransferRecord));
    }

    /**
     * 通过id删除患者转科交接记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除患者转科交接记录", notes = "通过id删除患者转科交接记录")
    @SysLog("通过id删除患者转科交接记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(transferRecordService.removeById(id));
    }

}
