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

package com.pig4cloud.pigx.ccxxicu.controller.drug;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.SpecialDrugsRegistration;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.drug.SpecialDrugsRegistrationService;
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
 * 特殊药品登记
 *
 * @author pigx code generator
 * @date 2019-09-19 10:41:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/specialDrugsRegistration" )
@Api(value = "specialDrugsRegistration", tags = "特殊药品登记管理")
public class SpecialDrugsRegistrationController {

    private final SpecialDrugsRegistrationService specialDrugsRegistrationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param specialDrugsRegistration 特殊药品登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSpecialDrugsRegistrationPage(Page page, SpecialDrugsRegistration specialDrugsRegistration) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		specialDrugsRegistration.setDelFlag(0);

        return R.ok(specialDrugsRegistrationService.page(page, Wrappers.query(specialDrugsRegistration).lambda().orderByDesc(SpecialDrugsRegistration::getCreateTime)));
    }


    /**
     * 通过id查询特殊药品登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

        return R.ok(specialDrugsRegistrationService.getById(id));
    }

    /**
     * 新增特殊药品登记
     * @param specialDrugsRegistration 特殊药品登记
     * @return R
     */
    @ApiOperation(value = "新增特殊药品登记", notes = "新增特殊药品登记")
    @SysLog("新增特殊药品登记" )
    @PostMapping("/add")
    public R save(@RequestBody SpecialDrugsRegistration specialDrugsRegistration) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		specialDrugsRegistration.setDelFlag(0);
		specialDrugsRegistration.setCreateTime(LocalDateTime.now());
		specialDrugsRegistration.setCreateUserId(user.getId()+"");
		specialDrugsRegistration.setSpecialDrugsRegistrationId(SnowFlake.getId()+"");

        return R.ok(specialDrugsRegistrationService.save(specialDrugsRegistration));
    }

    /**
     * 修改特殊药品登记
     * @param specialDrugsRegistration 特殊药品登记
     * @return R
     */
    @ApiOperation(value = "修改特殊药品登记", notes = "修改特殊药品登记")
    @SysLog("修改特殊药品登记" )
    @PostMapping("/update")
    public R updateById(@RequestBody SpecialDrugsRegistration specialDrugsRegistration) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		specialDrugsRegistration.setUpdateUserId(user.getId()+"");

        return R.ok(specialDrugsRegistrationService.updateById(specialDrugsRegistration));
    }

    /**
     * 通过id删除特殊药品登记
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除特殊药品登记", notes = "通过id删除特殊药品登记")
    @SysLog("通过id删除特殊药品登记" )
    @GetMapping("/del/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		SpecialDrugsRegistration byId = specialDrugsRegistrationService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");

		return R.ok(specialDrugsRegistrationService.updateById(byId));
    }

}
