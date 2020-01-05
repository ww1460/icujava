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

package com.pig4cloud.pigx.ccxxicu.controller.arrange;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.arrange.ArrangePeriod;
import com.pig4cloud.pigx.ccxxicu.api.vo.arrange.WeekShiftVo;
import com.pig4cloud.pigx.ccxxicu.service.arrange.ArrangePeriodService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 排班周期结果表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/arrangePeriod" )
@Api(value = "arrangePeriod", tags = "排班周期结果表管理")
public class ArrangePeriodController {

    private final ArrangePeriodService arrangePeriodService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param arrangePeriod 排班周期结果表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getArrangePeriodPage(Page page, ArrangePeriod arrangePeriod) {
        return R.ok(arrangePeriodService.page(page, Wrappers.query(arrangePeriod)));
    }


    /**
     * 通过id查询排班周期结果表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(arrangePeriodService.getById(id));
    }

    /**
     * 新增排班周期结果表
     * @param arrangePeriod 排班周期结果表
     * @return R
     */
    @ApiOperation(value = "新增排班周期结果表", notes = "新增排班周期结果表")
    @SysLog("新增排班周期结果表" )
    @PostMapping
    public R save(@RequestBody ArrangePeriod arrangePeriod) {
        return R.ok(arrangePeriodService.save(arrangePeriod));
    }

    /**
     * 修改排班周期结果表
     * @param arrangePeriod 排班周期结果表
     * @return R
     */
    @ApiOperation(value = "修改排班周期结果表", notes = "修改排班周期结果表")
    @SysLog("修改排班周期结果表" )
    @PostMapping("/update")
    public R updateById(@RequestBody List<ArrangePeriod> arrangePeriod) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if(roleCodes.get(0).equals("ROLE_NURSE")){

			return R.failed("权限不足");

		}
		if (CollectionUtils.isEmpty(arrangePeriod)) {
			return R.failed("数据不可为空！");
		}

        return R.ok();
    }

    /**
     * 通过id删除排班周期结果表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除排班周期结果表", notes = "通过id删除排班周期结果表")
    @SysLog("通过id删除排班周期结果表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(arrangePeriodService.removeById(id));
    }

	/**
	 * 查询某科室下某周的班次
	 * @param dateTime id
	 * @return R
	 */
	@ApiOperation(value = "查询某科室下某周的班次", notes = "查询某科室下某周的班次")
	@GetMapping("/weekShift" )
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public R selectShift(@PathVariable LocalDateTime dateTime) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		 if(roleCodes.get(0).equals("ROLE_NURSE")){

			 return R.failed("权限不足");

		}
		if (dateTime==null) {
			dateTime = LocalDateTime.now();
		}
		ArrangePeriod arrangePeriod = new ArrangePeriod();

		arrangePeriod.setDeptId(user.getDeptId()+"");
		arrangePeriod.setStartTime(dateTime);

		return R.ok(arrangePeriodService.selectWeekShift(arrangePeriod));
	}


	/**
	 * 获取下个周开始和结束时间
	 * @return
	 */
	@GetMapping("/dataComputer")
	public R dataComputer() {

		LocalDateTime now = LocalDateTime.now();

		while (!((now.getDayOfWeek().getValue()) == 1)) {

			now = now.minusDays(-1);

		}

		ArrangePeriod result = new ArrangePeriod();
		result.setStartTime(now);
		result.setEndTime(now.minusDays(-6));
		return R.ok(result);
	}

	/**
	 * 班次数据复制
	 * @return R
	 */
	@ApiOperation(value = "班次数据复制", notes = "班次数据复制")
	@SysLog("班次数据复制" )
	@GetMapping("/copyShift")
	public R copyShift(@RequestBody List<WeekShiftVo> weekShiftVos) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if(roleCodes.get(0).equals("ROLE_NURSE")){

			return R.failed("权限不足");

		}
		if (CollectionUtils.isEmpty(weekShiftVos)) {
			return R.failed("数据不可为空！");
		}



		return R.ok(arrangePeriodService.copyShift(weekShiftVos));
	}


}
