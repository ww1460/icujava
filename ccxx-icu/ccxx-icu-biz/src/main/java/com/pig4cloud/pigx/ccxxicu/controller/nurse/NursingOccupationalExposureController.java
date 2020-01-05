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

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursingOccupationalExposure;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursingOccupationalExposureService;
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
 * 护理职业暴露记录
 *
 * @author pigx code generator
 * @date 2019-08-30 16:38:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursingOccupationalExposure" )
@Api(value = "nursingOccupationalExposure", tags = "护理职业暴露记录管理")
public class NursingOccupationalExposureController {

    private final NursingOccupationalExposureService nursingOccupationalExposureService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param nursingOccupationalExposure 护理职业暴露记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNursingOccupationalExposurePage(Page page, NursingOccupationalExposure nursingOccupationalExposure) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}



		nursingOccupationalExposure.setDelFlag(0);

		if (!roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			nursingOccupationalExposure.setDeptId(user.getDeptId()+"");

		}

        return R.ok(nursingOccupationalExposureService.page(page, Wrappers.query(nursingOccupationalExposure)));
    }


    /**
     * 通过id查询护理职业暴露记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(nursingOccupationalExposureService.getById(id));
    }

    /**
     * 新增护理职业暴露记录
     * @param nursingOccupationalExposure 护理职业暴露记录
     * @return R
     */
    @ApiOperation(value = "新增护理职业暴露记录", notes = "新增护理职业暴露记录")
    @SysLog("新增护理职业暴露记录" )
    @PostMapping("/add")
    public R save(@RequestBody NursingOccupationalExposure nursingOccupationalExposure) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (StringUtils.isEmpty(nursingOccupationalExposure.getNurseId())) {
			return R.failed(1, "操作失败！");
		}


		nursingOccupationalExposure.setCreateTime(LocalDateTime.now());
		nursingOccupationalExposure.setOccupationalExposureId(SnowFlake.getId()+"");
		nursingOccupationalExposure.setCreateUserId(user.getId()+"");
		nursingOccupationalExposure.setDelFlag(0);
		nursingOccupationalExposure.setDeptId(user.getDeptId()+"");

        return R.ok(nursingOccupationalExposureService.save(nursingOccupationalExposure));
    }

    /**
     * 修改护理职业暴露记录
     * @param nursingOccupationalExposure 护理职业暴露记录
     * @return R
     */
    @ApiOperation(value = "修改护理职业暴露记录", notes = "修改护理职业暴露记录")
    @SysLog("修改护理职业暴露记录" )
    @PostMapping("/update")
    public R updateById(@RequestBody NursingOccupationalExposure nursingOccupationalExposure) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		nursingOccupationalExposure.setUpdateUserId(user.getId()+"");

        return R.ok(nursingOccupationalExposureService.updateById(nursingOccupationalExposure));
    }

    /**
     * 通过id删除护理职业暴露记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护理职业暴露记录", notes = "通过id删除护理职业暴露记录")
    @SysLog("通过id删除护理职业暴露记录" )
    @GetMapping("/del/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		NursingOccupationalExposure byId = nursingOccupationalExposureService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");
		return R.ok(nursingOccupationalExposureService.updateById(byId));
    }

}
