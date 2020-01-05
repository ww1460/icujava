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

package com.pig4cloud.pigx.ccxxicu.controller.newlogin;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.newlogin.MacRfidUserRelation;
import com.pig4cloud.pigx.ccxxicu.common.utils.IpUtil;
import com.pig4cloud.pigx.ccxxicu.service.newlogin.MacRfidUserRelationService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * mac地址与护士RFID的关系
 *
 * @author 崔洪振
 * @date 2019-09-06 15:03:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/newlogin" )
@Api(value = "macrfiduserrelation", tags = "mac地址与护士RFID的关系管理")
public class MacRfidUserRelationController {

    private final MacRfidUserRelationService macRfidUserRelationService;

     /**
	 * 免登录页面调用，获取可免登录的用户名称列表
	 * @param
	 * @param request mac地址与护士RFID的关系
	 * @return
	 */
	@ApiOperation(value = "免登录逻辑获取可免登录用户名称", notes = "免登录逻辑获取可免登录用户名称")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Inner(value = false)
	public R login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return R.ok(macRfidUserRelationService.macAddressIsNotNull(IpUtil.getUserIP(request)));
	}

//	@ApiOperation(value = "mac免登录逻辑获取可免登录用户名称", notes = "mac免登录逻辑获取可免登录用户名称")
//	@GetMapping(value = "/logins/{macaddress}")
//	@Inner(value = false)
//	public R login(@PathVariable("macaddress") String macaddress) throws Exception {
//		return R.ok(macRfidUserRelationService.macAddressIsNotNull(IpUtil.getIpAddr(request)));
//	}

	/**
	 * 登录页面调用，直接进入登录页面
	 * @param
	 * @param request mac地址与护士RFID的关系
	 * @return
	 */
	@ApiOperation(value = "免登录逻辑", notes = "免登录逻辑")
	@RequestMapping(value = "/freelogin", method = RequestMethod.POST)
	public R freeLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = (String) request.getAttribute("username");
		if (StringUtils.isNotEmpty(username)){
			return R.ok();//首页地址
		}else {
			return R.failed();
		}
	}

	/**
	 * 登录页面调用，直接进入登录页面
	 * @param
	 * @param request mac地址与护士RFID的关系
	 * @return
	 */
	@ApiOperation(value = "免登录逻辑", notes = "免登录逻辑")
	@GetMapping(value = "/freelogins/{username}")
	public R freeLogins(@PathVariable("username") String username) throws Exception {
		//String username = (String) request.getAttribute("username");
		if (StringUtils.isNotEmpty(username)){
			return R.ok();//首页地址
		}else {
			return R.failed();
		}
	}

    /**
     * 分页查询
     * @param page 分页对象
     * @param macRfidUserRelation mac地址与护士RFID的关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getMacRfidUserRelationPage(Page page, MacRfidUserRelation macRfidUserRelation) {
        return R.ok(macRfidUserRelationService.page(page, Wrappers.query(macRfidUserRelation)));
    }


    /**
     * 通过id查询mac地址与护士RFID的关系
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(macRfidUserRelationService.getById(id));
    }

    /**
     * 新增mac地址与护士RFID的关系
     * @param macRfidUserRelation mac地址与护士RFID的关系
     * @return R
     */
    @ApiOperation(value = "新增mac地址与护士RFID的关系", notes = "新增mac地址与护士RFID的关系")
    @SysLog("新增mac地址与护士RFID的关系" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('generator_macrfiduserrelation_add')" )
    public R save(@RequestBody MacRfidUserRelation macRfidUserRelation) {
        return R.ok(macRfidUserRelationService.save(macRfidUserRelation));
    }

    /**
     * 修改mac地址与护士RFID的关系
     * @param macRfidUserRelation mac地址与护士RFID的关系
     * @return R
     */
    @ApiOperation(value = "修改mac地址与护士RFID的关系", notes = "修改mac地址与护士RFID的关系")
    @SysLog("修改mac地址与护士RFID的关系" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('generator_macrfiduserrelation_edit')" )
    public R updateById(@RequestBody MacRfidUserRelation macRfidUserRelation) {
        return R.ok(macRfidUserRelationService.updateById(macRfidUserRelation));
    }

    /**
     * 通过id删除mac地址与护士RFID的关系
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除mac地址与护士RFID的关系", notes = "通过id删除mac地址与护士RFID的关系")
    @SysLog("通过id删除mac地址与护士RFID的关系" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('generator_macrfiduserrelation_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(macRfidUserRelationService.removeById(id));
    }

}
