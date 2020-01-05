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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Entrust;
import com.pig4cloud.pigx.ccxxicu.service.task.EntrustService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 任务委托
 *
 * @author pigx code generator
 * @date 2019-09-02 16:00:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/entrust" )
@Api(value = "entrust", tags = "任务委托管理")
public class EntrustController {

    private final EntrustService entrustService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param entrust 任务委托
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakEntrustPage(Page page, Entrust entrust) {
        return R.ok(entrustService.selectPaging(page,entrust));
    }


    /**
     * 通过id查询任务委托
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(entrustService.getById(id));
    }

    /**
     * 新增任务委托
     * @param entrust 任务委托
     * @return R
     */
    @ApiOperation(value = "新增任务委托", notes = "新增任务委托")
    @SysLog("新增任务委托" )
    @PostMapping("add")
    public R save(@RequestBody Entrust entrust) {
        return R.ok(entrustService.save(entrust));
    }

    /**
     * 修改任务委托
     * @param entrust 任务委托
     * @return R
     */
    @ApiOperation(value = "修改任务委托", notes = "修改任务委托")
    @SysLog("修改任务委托" )
    @PostMapping("/update")
    public R updateById(@RequestBody Entrust entrust) {
        return R.ok(entrustService.updateById(entrust));
    }

    /**
     * 通过id删除任务委托
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务委托", notes = "通过id删除任务委托")
    @SysLog("通过id删除任务委托" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(entrustService.removeById(id));
    }






}
