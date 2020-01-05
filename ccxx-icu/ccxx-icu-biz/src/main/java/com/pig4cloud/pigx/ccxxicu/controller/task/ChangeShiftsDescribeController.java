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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsDescribe;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsDescribeService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 交接班描述记录表   用户临时记录交接班的描述
 *
 * @author pigx code generator
 * @date 2019-08-23 13:42:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/changeshiftsdescribe" )
@Api(value = "changeshiftsdescribe", tags = "交接班描述记录表   用户临时记录交接班的描述管理")
public class ChangeShiftsDescribeController {

    private final ChangeShiftsDescribeService changeShiftsDescribeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param changeShiftsDescribe 交接班描述记录表   用户临时记录交接班的描述
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakChangeShiftsDescribePage(Page page, ChangeShiftsDescribe changeShiftsDescribe) {
        return R.ok(changeShiftsDescribeService.page(page, Wrappers.query(changeShiftsDescribe)));
    }


    /**
     * 通过id查询交接班描述记录表   用户临时记录交接班的描述
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(changeShiftsDescribeService.getById(id));
    }

    /**
     * 新增交接班描述记录表   用户临时记录交接班的描述
     * @param changeShiftsDescribe 交接班描述记录表   用户临时记录交接班的描述
     * @return R
     */
    @ApiOperation(value = "新增交接班描述记录表   用户临时记录交接班的描述", notes = "新增交接班描述记录表   用户临时记录交接班的描述")
    @SysLog("新增交接班描述记录表   用户临时记录交接班的描述" )
    @PostMapping("add")
    public R save(@RequestBody ChangeShiftsDescribe changeShiftsDescribe) {
        return R.ok(changeShiftsDescribeService.save(changeShiftsDescribe));
    }

    /**
     * 修改交接班描述记录表   用户临时记录交接班的描述
     * @param changeShiftsDescribe 交接班描述记录表   用户临时记录交接班的描述
     * @return R
     */
    @ApiOperation(value = "修改交接班描述记录表   用户临时记录交接班的描述", notes = "修改交接班描述记录表   用户临时记录交接班的描述")
    @SysLog("修改交接班描述记录表   用户临时记录交接班的描述" )
   	@PostMapping("/update")
    public R updateById(@RequestBody ChangeShiftsDescribe changeShiftsDescribe) {
        return R.ok(changeShiftsDescribeService.updateById(changeShiftsDescribe));
    }

    /**
     * 通过id删除交接班描述记录表   用户临时记录交接班的描述
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除交接班描述记录表   用户临时记录交接班的描述", notes = "通过id删除交接班描述记录表   用户临时记录交接班的描述")
    @SysLog("通过id删除交接班描述记录表   用户临时记录交接班的描述" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(changeShiftsDescribeService.removeById(id));
    }







}
