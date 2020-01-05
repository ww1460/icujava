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

package com.pig4cloud.pigx.ccxxicu.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.SysUser;
import com.pig4cloud.pigx.ccxxicu.service.SysUserService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 用户表
 *
 * @author 博羸
 * @date 2019-07-20 15:04:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysuser" )
@Api(value = "sysuser", tags = "sysuser管理")
public class SysUserController {


    private final SysUserService sysUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param sysUser 用户表
     * @return
     */
    @GetMapping("/page" )
    public R getSysUserPage(Page page, SysUser sysUser) {
        return R.ok(sysUserService.page(page, Wrappers.query(sysUser)));
    }


    /**
     * 通过id查询用户表
     * @param userId id
     * @return R
     */
    @GetMapping("/{userId}" )
    public R getById(@PathVariable("userId" ) Integer userId) {
        return R.ok(sysUserService.getById(userId));
    }

    /**
     * 新增用户表
     * @param sysUser 用户表
     * @return R
     */
    @SysLog("新增用户表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('icu_sysuser_add')" )
    public R save(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.save(sysUser));
    }

    /**
     * 修改用户表
     * @param sysUser 用户表
     * @return R
     */
    @SysLog("修改用户表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('icu_sysuser_edit')" )
    public R updateById(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.updateById(sysUser));
    }

    /**
     * 通过id删除用户表
     * @param userId id
     * @return R
     */
    @SysLog("删除用户表" )
    @DeleteMapping("/{userId}" )
    @PreAuthorize("@pms.hasPermission('icu_sysuser_del')" )
    public R removeById(@PathVariable Integer userId) {
        return R.ok(sysUserService.removeById(userId));
    }

}
