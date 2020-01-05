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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.EasilyConfusedCatalog;
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.EasilyConfusedParticular;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.drug.EasilyConfusedCatalogService;
import com.pig4cloud.pigx.ccxxicu.service.drug.EasilyConfusedParticularService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 药品易混淆目录
 *
 * @author pigx code generator
 * @date 2019-09-19 10:41:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/easilyConfusedCatalog" )
@Api(value = "easilyConfusedCatalog", tags = "药品易混淆目录管理")
public class EasilyConfusedCatalogController {

    private final EasilyConfusedCatalogService easilyConfusedCatalogService;

	@Autowired
	private EasilyConfusedParticularService easilyConfusedParticularService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param easilyConfusedCatalog 药品易混淆目录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEasilyConfusedCatalogPage(Page page, EasilyConfusedCatalog easilyConfusedCatalog) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
        return R.ok(easilyConfusedCatalogService.getList(page, easilyConfusedCatalog));
    }


    /**
     * 通过id查询药品易混淆目录
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


    	return R.ok(easilyConfusedCatalogService.getById(id));
    }

    /**
     * 新增药品易混淆目录
     * @param easilyConfusedCatalog 药品易混淆目录
     * @return R
     */
    @ApiOperation(value = "新增药品易混淆目录", notes = "新增药品易混淆目录")
    @SysLog("新增药品易混淆目录" )
    @PostMapping("/add")
    public R save(@RequestBody EasilyConfusedCatalog easilyConfusedCatalog) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		easilyConfusedCatalog.setCreateTime(LocalDateTime.now());
		easilyConfusedCatalog.setCreateUserId(user.getId()+"");
		easilyConfusedCatalog.setDelFlag(0);
		easilyConfusedCatalog.setEasilyConfusedCatalogId(SnowFlake.getId()+"");

        return R.ok(easilyConfusedCatalogService.save(easilyConfusedCatalog));
    }

    /**
     * 修改药品易混淆目录
     * @param easilyConfusedCatalog 药品易混淆目录
     * @return R
     */
    @ApiOperation(value = "修改药品易混淆目录", notes = "修改药品易混淆目录")
    @SysLog("修改药品易混淆目录" )
    @PostMapping("/update")
    public R updateById(@RequestBody EasilyConfusedCatalog easilyConfusedCatalog) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		easilyConfusedCatalog.setUpdateUserId(user.getId()+"");

        return R.ok(easilyConfusedCatalogService.updateById(easilyConfusedCatalog));
    }

    /**
     * 通过id删除药品易混淆目录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除药品易混淆目录", notes = "通过id删除药品易混淆目录")
    @SysLog("通过id删除药品易混淆目录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//查询出原数据
		EasilyConfusedCatalog byId = easilyConfusedCatalogService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");
		//将该类的药品数据查出
		List<EasilyConfusedParticular> list = easilyConfusedParticularService.list(Wrappers.<EasilyConfusedParticular>query().lambda()
				.eq(EasilyConfusedParticular::getDelFlag, 0)
				.eq(EasilyConfusedParticular::getEasilyConfusedCatalogId, byId.getEasilyConfusedCatalogId())
		);
		//为空 直接结束
		if (CollectionUtils.isEmpty(list)) {

			return R.ok(easilyConfusedCatalogService.updateById(byId));

		}
		//不为空  对数据作删除处理
		list.forEach(ar->{
			ar.setDelFlag(1);
			ar.setDelTime(LocalDateTime.now());
			ar.setDelUserId(user.getId()+"");

		});

		easilyConfusedParticularService.updateBatchById(list);

		return R.ok(easilyConfusedCatalogService.updateById(byId));
    }

}
