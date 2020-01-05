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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsTaskRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 交接班任务记录表
 *
 * @author pigx code generator
 * @date 2019-08-23 16:16:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/changeshiftstaskrecord" )
@Api(value = "changeshiftstaskrecord", tags = "交接班任务记录表管理")
public class ChangeShiftsTaskRecordController {

    private final ChangeShiftsTaskRecordService changeShiftsTaskRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param changeShiftsTaskRecord 交接班任务记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakChangeShiftsTaskRecordPage(Page page, ChangeShiftsTaskRecord changeShiftsTaskRecord) {
        return R.ok(changeShiftsTaskRecordService.page(page, Wrappers.query(changeShiftsTaskRecord)));
    }


    /**
     * 通过id查询交接班任务记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(changeShiftsTaskRecordService.getById(id));
    }

    /**
     * 新增交接班任务记录表
     * @param changeShiftsTaskRecord 交接班任务记录表
     * @return R
     */
    @ApiOperation(value = "新增交接班任务记录表", notes = "新增交接班任务记录表")
    @SysLog("新增交接班任务记录表" )
    @PostMapping("/add")
    public R save(@RequestBody ChangeShiftsTaskRecord changeShiftsTaskRecord) {
        return R.ok(changeShiftsTaskRecordService.save(changeShiftsTaskRecord));
    }

    /**
     * 修改交接班任务记录表
     * @param changeShiftsTaskRecord 交接班任务记录表
     * @return R
     */
    @ApiOperation(value = "修改交接班任务记录表", notes = "修改交接班任务记录表")
    @SysLog("修改交接班任务记录表" )
    @PostMapping("update")
    public R updateById(@RequestBody ChangeShiftsTaskRecord changeShiftsTaskRecord) {
        return R.ok(changeShiftsTaskRecordService.updateById(changeShiftsTaskRecord));
    }

    /**
     * 通过id删除交接班任务记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除交接班任务记录表", notes = "通过id删除交接班任务记录表")
    @SysLog("通过id删除交接班任务记录表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		ChangeShiftsTaskRecord byId = changeShiftsTaskRecordService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");

		return R.ok(changeShiftsTaskRecordService.updateById(byId));
    }


	/**
	 * 条件数据全查
	 * @param changeShiftsTaskRecord
	 * @return
	 */
	@ApiOperation(value = "条件数据全查",notes = "条件数据全查")
	@SysLog("条件数据全查")
    @GetMapping("/selectAll")
    public R selectAll(ChangeShiftsTaskRecord changeShiftsTaskRecord){
    return R.ok(changeShiftsTaskRecordService.selectAll(changeShiftsTaskRecord));
    }







}
