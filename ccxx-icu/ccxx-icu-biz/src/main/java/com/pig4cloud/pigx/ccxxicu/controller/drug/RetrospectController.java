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
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.Retrospect;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.drug.RetrospectService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;

import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 药品追溯表
 *
 * @author pigx code generator
 * @date 2019-11-05 20:57:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/retrospect" )
@Api(value = "retrospect", tags = "药品追溯表管理")
public class RetrospectController {

    private final RetrospectService retrospectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param retrospect 药品追溯表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getDrugRetrospectPage(Page page, Retrospect retrospect) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return R.failed(1, "操作有误！");
        }
        retrospect.setDelFlag(0);
        return R.ok(retrospectService.page(page, Wrappers.query(retrospect)));
    }


    /**
     * 通过id查询药品追溯表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(retrospectService.getById(id));
    }

    /**
     * 新增药品追溯表
     * @param retrospect 药品追溯表
     * @return R
     */
    @ApiOperation(value = "新增药品追溯表", notes = "新增药品追溯表")
    @SysLog("新增药品追溯表" )
    @PostMapping("/addRetrospect")

    public R save(@RequestBody Retrospect retrospect) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return R.failed(1, "操作有误！");
        }

        retrospect.setRetrospectId(SnowFlake.getId()+"");
        retrospect.setCreateUserId(user.getId()+"");
        retrospect.setCreateTime(LocalDateTime.now());
        return R.ok(retrospectService.save(retrospect));
    }

    /**
     * 修改药品追溯表
     * @param retrospect 药品追溯表
     * @return R
     */
    @ApiOperation(value = "修改药品追溯表", notes = "修改药品追溯表")
    @SysLog("修改药品追溯表" )
    @PutMapping("/updateRetrospect")

    public R updateById(@RequestBody Retrospect retrospect) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return R.failed(1, "操作有误！");
        }
        retrospect.setUpdateUserId(user.getId()+"");
        return R.ok(retrospectService.updateById(retrospect));
    }

    /**
     * 通过id删除药品追溯表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除药品追溯表", notes = "通过id删除药品追溯表")
    @SysLog("通过id删除药品追溯表" )
    @DeleteMapping("/{id}" )

    public R removeById(@PathVariable Integer id) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return R.failed(1, "操作有误！");
        }
        Retrospect retrospect = retrospectService.getById(id);
        retrospect.setDelFlag(1);
        retrospect.setDelUserId(user.getId()+"");
        retrospect.setDelTime(LocalDateTime.now());
        return R.ok(retrospectService.updateById(retrospect));
        //return R.ok(retrospectService.removeById(id));
    }

}
