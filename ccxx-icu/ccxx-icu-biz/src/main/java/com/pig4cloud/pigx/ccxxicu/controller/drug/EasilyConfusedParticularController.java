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
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.EasilyConfusedParticular;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.drug.EasilyConfusedParticularService;
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
 * 易混淆详情
 *
 * @author pigx code generator
 * @date 2019-09-19 10:41:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/easilyConfusedParticular" )
@Api(value = "easilyConfusedParticular", tags = "易混淆详情管理")
public class EasilyConfusedParticularController {

    private final EasilyConfusedParticularService easilyConfusedParticularService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param easilyConfusedParticular 易混淆详情
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEasilyConfusedParticularPage(Page page, EasilyConfusedParticular easilyConfusedParticular) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		easilyConfusedParticular.setDelFlag(0);

        return R.ok(easilyConfusedParticularService.page(page, Wrappers.query(easilyConfusedParticular)));
    }


    /**
     * 通过id查询易混淆详情
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
    	return R.ok(easilyConfusedParticularService.getById(id));
    }


    /**
     * 新增易混淆详情
     * @param easilyConfusedParticular 易混淆详情
     * @return R
     */
    @ApiOperation(value = "新增易混淆详情", notes = "新增易混淆详情")
    @SysLog("新增易混淆详情" )
    @PostMapping("/add")
    public R save(@RequestBody EasilyConfusedParticular easilyConfusedParticular) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		easilyConfusedParticular.setCreateTime(LocalDateTime.now());
		easilyConfusedParticular.setCreateUserId(user.getId()+"");
		easilyConfusedParticular.setDelFlag(0);
		easilyConfusedParticular.setEasilyConfusedParticularId(SnowFlake.getId()+"");

        return R.ok(easilyConfusedParticularService.save(easilyConfusedParticular));
    }

    /**
     * 修改易混淆详情
     * @param easilyConfusedParticular 易混淆详情
     * @return R
     */
    @ApiOperation(value = "修改易混淆详情", notes = "修改易混淆详情")
    @SysLog("修改易混淆详情" )
    @PutMapping
    public R updateById(@RequestBody EasilyConfusedParticular easilyConfusedParticular) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		easilyConfusedParticular.setUpdateUserId(user.getId()+"");

        return R.ok(easilyConfusedParticularService.updateById(easilyConfusedParticular));
    }

    /**
     * 通过id删除易混淆详情
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除易混淆详情", notes = "通过id删除易混淆详情")
    @SysLog("通过id删除易混淆详情" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		EasilyConfusedParticular byId = easilyConfusedParticularService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");

		return R.ok(easilyConfusedParticularService.updateById(byId));

    }

}
