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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.IcuRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.IcuRecordVo;
import com.pig4cloud.pigx.ccxxicu.service.patient.IcuRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
 *
 * @author pigx code generator
 * @date 2019-10-03 16:48:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/icurecord" )
@Api(value = "icurecord", tags = "icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数管理")
public class IcuRecordController {

    private final IcuRecordService icuRecordService;

    /**
     * 分页查询
     * @param page icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPatIcuRecordPage(Page page,IcuRecordVo icuRecord) {
		// 管理员登录
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(icuRecordService.page(page,icuRecord));
		}
    	icuRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
        return R.ok(icuRecordService.page(page,icuRecord));
    }


    /**
     * 通过id查询icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(icuRecordService.getById(id));
    }

    /**
     * 新增icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @param icuRecord icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @return R
     */
    @ApiOperation(value = "新增icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数", notes = "新增icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数")
    @SysLog("新增icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数" )
    @PostMapping("/add")
    public R save(@RequestBody IcuRecord icuRecord) {
        return R.ok(icuRecordService.add(icuRecord));
    }

    /**
     * 修改icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @param icuRecord icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @return R
     */
    @ApiOperation(value = "修改icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数", notes = "修改icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数")
    @SysLog("修改icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数" )
    @PostMapping("/update")
    public R updateById(@RequestBody IcuRecord icuRecord) {
        return R.ok(icuRecordService.updateById(icuRecord));
    }

    /**
     * 通过id删除icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数", notes = "通过id删除icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数")
    @SysLog("通过id删除icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		IcuRecord byId = icuRecordService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");//护士
		return R.ok(icuRecordService.updateById(byId));
    }


	/**
	 * 测试数据
	 * @param icuRecord
	 * @return
	 */

	@GetMapping("test")
	public R test(IcuRecord icuRecord) {
		return R.ok(icuRecordService.add(icuRecord));
	}



}
