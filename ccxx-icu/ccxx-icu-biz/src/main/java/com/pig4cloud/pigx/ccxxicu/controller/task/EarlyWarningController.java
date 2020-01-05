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

package com.pig4cloud.pigx.ccxxicu.controller.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.EarlyWarning;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.EarlyWarningVo;
import com.pig4cloud.pigx.ccxxicu.service.task.EarlyWarningService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 任务预警表
 *
 * @author pigx code generator
 * @date 2019-08-16 14:57:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/earlywarning" )
@Api(value = "earlywarning", tags = "任务预警表管理")
public class EarlyWarningController {

    private final EarlyWarningService earlyWarningService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param earlyWarning 任务预警表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakEarlyWarningPage(Page page, EarlyWarning earlyWarning) {

		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 如果当前登录的是护士长的时候需要将当前科室所有的护士，及患者展示给护士长查看 */
			/* 查询当前科室下所有的 */
			earlyWarning.setDeptId(SecurityUtils.getUser().getDeptId()+"");
			System.out.println("1-----------------------------------"+earlyWarning);
			return R.ok(earlyWarningService.selectPaging(page,earlyWarning));
		}else if("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
				earlyWarning.setNurseId(SecurityUtils.getUser().getId()+"");
				earlyWarning.setDeptId(SecurityUtils.getUser().getDeptId()+"");
			System.out.println("2-----------------------------------"+earlyWarning);
			return R.ok(earlyWarningService.selectPaging(page,earlyWarning));
		}

		return R.ok(earlyWarningService.selectPaging(page,earlyWarning));
    }


    /**
     * 通过id查询任务预警表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(earlyWarningService.getById(id));
    }

    /**
     * 新增任务预警表
     * @param earlyWarning 任务预警表
     * @return R
     */
    @ApiOperation(value = "新增任务预警表", notes = "新增任务预警表")
    @SysLog("新增任务预警表" )
    @PostMapping("/add")
    public R save(@RequestBody EarlyWarning earlyWarning) {
        return R.ok(earlyWarningService.save(earlyWarning));
    }

    /**
     * 修改任务预警表
     * @param earlyWarning 任务预警表
     * @return R
     */
    @ApiOperation(value = "修改任务预警表", notes = "修改任务预警表")
    @SysLog("修改任务预警表" )
    @PostMapping("/update")
    public R updateById(@RequestBody EarlyWarning earlyWarning) {
        return R.ok(earlyWarningService.updateById(earlyWarning));
    }




    /**
     * 通过id删除任务预警表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务预警表", notes = "通过id删除任务预警表")
    @SysLog("通过id删除任务预警表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		EarlyWarning byId = earlyWarningService.getById(id);
		byId.setDelFlag(1);
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		byId.setDelTime(LocalDateTime.now());
		return R.ok(earlyWarningService.updateById(byId));
    }












}
