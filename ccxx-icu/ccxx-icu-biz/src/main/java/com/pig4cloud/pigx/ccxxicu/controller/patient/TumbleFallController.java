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
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.TumbleFall;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.patient.TumbleFallService;
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
 * 跌倒、坠床记录表
 *
 * @author pigx code generator
 * @date 2019-09-29 10:10:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tumbleFall" )
@Api(value = "tumbleFall", tags = "跌倒、坠床记录表管理")
public class TumbleFallController {

    private final TumbleFallService tumbleFallService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param tumbleFall 跌倒、坠床记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTumbleFallPage(Page page, TumbleFall tumbleFall) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			//这里只是一个跳板  并非按创建人查询  而是查询当前护士关联的患者
			tumbleFall.setCreateUserId(user.getId()+"");

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			tumbleFall.setDeptId(user.getDeptId()+"");
		}

		return R.ok(tumbleFallService.selectByPage(page, tumbleFall));
    }


    /**
     * 通过id查询跌倒、坠床记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {

    	return R.ok(tumbleFallService.getById(id));
    }

    /**
     * 新增跌倒、坠床记录表
     * @param tumbleFall 跌倒、坠床记录表
     * @return R
     */
    @ApiOperation(value = "新增跌倒、坠床记录表", notes = "新增跌倒、坠床记录表")
    @SysLog("新增跌倒、坠床记录表" )
    @PostMapping("/add")
    public R save(@RequestBody TumbleFall tumbleFall) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(false);

		}
		if (StringUtils.isEmpty(tumbleFall.getPatientId())) {
			return R.failed(false);

		}

		tumbleFall.setDeptId(user.getDeptId()+"");
		tumbleFall.setCreateUserId(user.getId()+"");
		tumbleFall.setCreateTime(LocalDateTime.now());
		tumbleFall.setTumbleFallId(SnowFlake.getId()+"");

		return R.ok(tumbleFallService.save(tumbleFall));
    }

    /**
     * 修改跌倒、坠床记录表
     * @param tumbleFall 跌倒、坠床记录表
     * @return R
     */
    @ApiOperation(value = "修改跌倒、坠床记录表", notes = "修改跌倒、坠床记录表")
    @SysLog("修改跌倒、坠床记录表" )
    @PostMapping("/update")
    public R updateById(@RequestBody TumbleFall tumbleFall) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}

		tumbleFall.setUpdateUserId(user.getId()+"");
        return R.ok(tumbleFallService.updateById(tumbleFall));
    }

    /**
     * 通过id删除跌倒、坠床记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除跌倒、坠床记录表", notes = "通过id删除跌倒、坠床记录表")
    @SysLog("通过id删除跌倒、坠床记录表" )
    @GetMapping("/del/{id}" )
    public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}

		TumbleFall byId = tumbleFallService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");
		return R.ok(tumbleFallService.updateById(byId));
    }

}
