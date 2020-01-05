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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.WorkTimeBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.WorkTime;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.WorkTimeService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * 护士在线时间
 *
 * @author pigx code generator
 * @date 2019-11-06 17:02:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/workTime" )
@Api(value = "workTime", tags = "护士在线时间管理")
public class WorkTimeController {

    private final WorkTimeService workTimeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param workTime 护士在线时间
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getWorkTimePage(Page page, WorkTime workTime) {
        return R.ok(workTimeService.page(page, Wrappers.query(workTime)));
    }


	/**
	 * 查询护士的床边数据
	 * @param workTimeBo id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/workTimeShow" )
	public R workTimeShow(WorkTimeBo workTimeBo){
		PigxUser user = SecurityUtils.getUser();
		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//管理员  查看全部

			return R.ok(null);

		}

		if (StringUtils.isEmpty(workTimeBo.getNurseId())) {
			return R.ok(null);
		}

		return R.ok(workTimeService.groupByBed(workTimeBo));
	}



    /**
     * 通过id查询护士在线时间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(workTimeService.getById(id));
    }

    /**
     * 新增护士在线时间
     * @param workTime 护士在线时间
     * @return R
     */
    @ApiOperation(value = "新增护士在线时间", notes = "新增护士在线时间")
    @SysLog("新增护士在线时间" )
    @PostMapping
    public R save(@RequestBody WorkTime workTime) {
        return R.ok(workTimeService.save(workTime));
    }

    /**
     * 修改护士在线时间
     * @param workTime 护士在线时间
     * @return R
     */
    @ApiOperation(value = "修改护士在线时间", notes = "修改护士在线时间")
    @SysLog("修改护士在线时间" )
    @PostMapping("/test")
    public R updateById(@RequestBody List<WorkTime> workTime) {
		for (int i = 0; i < workTime.size(); i++) {
			WorkTime ar = workTime.get(i);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime dateTime = now.withHour(1).withMinute(i);

			ar.setStartTime(dateTime);
			ar.setEndTime(dateTime.minusMinutes(-(dateTime.getMinute()+i)));
			long between = ChronoUnit.MINUTES.between(ar.getStartTime(), ar.getEndTime());
			ar.setTimeCount(Integer.parseInt(between+""));

			ar.setWorkTimeId(SnowFlake.getId()+"");
			ar.setDelFlag(0);
			ar.setCreateTime(LocalDateTime.now());

		}

    	return R.ok(workTimeService.saveBatch(workTime));
    }

    /**
     * 通过id删除护士在线时间
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士在线时间", notes = "通过id删除护士在线时间")
    @SysLog("通过id删除护士在线时间" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(workTimeService.removeById(id));
    }

}
