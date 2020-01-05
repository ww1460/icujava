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

package com.pig4cloud.pigx.ccxxicu.controller.piping;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Drainage;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.piping.DrainageService;
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
 * 引流液
 *
 * @author pigx code generator
 * @date 2019-08-10 14:14:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/drainage" )
@Api(value = "drainage", tags = "引流液管理")
public class DrainageController {

    private final DrainageService drainageService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param drainage 引流液
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPipDrainagePage(Page page, Drainage drainage) {
    	drainage.setDelFlag(0);
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(drainageService.page(page, Wrappers.query(drainage)));
		}
		drainage.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室信息
        return R.ok(drainageService.page(page, Wrappers.query(drainage)));
    }


    /**
     * 通过id查询引流液
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(drainageService.getById(id));
    }

    /**
     * 新增引流液
     * @param drainage 引流液
     * @return R
     */
    @ApiOperation(value = "新增引流液", notes = "新增引流液")
    @SysLog("新增引流液" )
    @PostMapping("/add")
    public R save(@RequestBody Drainage drainage) {

    	drainage.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
		drainage.setDrainageId(SnowFlake.getId()+"");//雪花id
    	return R.ok(drainageService.save(drainage));
    }

    /**
     * 修改引流液
     * @param drainage 引流液
     * @return R
     */
    @ApiOperation(value = "修改引流液", notes = "修改引流液")
    @SysLog("修改引流液" )
    @PostMapping("/update")
    public R updateById(@RequestBody Drainage drainage) {
        return R.ok(drainageService.updateById(drainage));
    }

    /**
     * 通过id删除引流液
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除引流液", notes = "通过id删除引流液")
    @SysLog("通过id删除引流液" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		Drainage byId = drainageService.getById(id);
		byId.setDelFlag(1); //删除
		byId.setDelTime(LocalDateTime.now()); //删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");//删除用户
		return R.ok(drainageService.updateById(byId));
    }

}
