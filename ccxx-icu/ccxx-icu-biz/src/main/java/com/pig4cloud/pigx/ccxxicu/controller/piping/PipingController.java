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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.piping.DrainageService;
import com.pig4cloud.pigx.ccxxicu.service.piping.PipingService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 管路表
 *
 * @author pigx code generator
 * @date 2019-08-08 15:11:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/piping" )
@Api(value = "piping", tags = "管路表管理")
public class PipingController {

    private final PipingService pipingService;

	/**
	 * 引流液
	 */
	private final DrainageService drainageService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param piping 管路表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPipPipingPage(Page page, Piping piping) {
    	piping.setDelFlag(0);//未删除

		/* 判断一下当前登录的用户是谁  */
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(pipingService.page(page, Wrappers.query(piping)));
		}
		piping.setDeptId(SecurityUtils.getUser().getDeptId()+"");// 科室

        return R.ok(pipingService.page(page, Wrappers.query(piping)));
    }


    /**
     * 通过id查询管路表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(pipingService.getById(id));
    }


	/**
	 * ·新增前跳转页面
	 * @return
	 */
	@ApiOperation(value = "新增全跳转页面",notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@PostMapping("/preJump")
	public R preJump(){
		Drainage drainage = new Drainage();
		drainage.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		/**
		 * 查询出当前科室对应的引流液信息
		 */
		return R.ok(drainageService.selectAll(drainage));
	}





    /**
     * 新增管路表
     * @param piping 管路表
     * @return R
     */
    @ApiOperation(value = "新增管路表", notes = "新增管路表")
    @SysLog("新增管路表" )
    @PostMapping("/add")
    public R save(@RequestBody Piping piping) {
		piping.setPipingId(SnowFlake.getId()+""); // 雪花id
    	piping.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室

    	return R.ok(pipingService.save(piping));
    }

    /**
     * 修改管路表
     * @param piping 管路表
     * @return R
     */
    @ApiOperation(value = "修改管路表", notes = "修改管路表")
    @SysLog("修改管路表" )
    @PostMapping("/update")
    public R updateById(@RequestBody Piping piping) {
        return R.ok(pipingService.updateById(piping));
    }

    /**
     * 通过id删除管路表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除管路表", notes = "通过id删除管路表")
    @SysLog("通过id删除管路表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		Piping byId = pipingService.getById(id);
		byId.setDelFlag(1);//  删除标识
		byId.setDelTime(LocalDateTime.now());//删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");// 删除用户id

		return R.ok(pipingService.removeById(id));
    }

	/**
	 * 分页查询数据
	 * @param page
	 * @param piping
	 * @return
	 */
    @ApiOperation(value = "分页查询数据",notes = "分页查询数据源")
	@SysLog("分页查询数据源")
	@GetMapping("/selectPaging")
    public R selectPaging(Page page,Piping piping){
		/* 判断一下当前登录的用户是谁  */
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(pipingService.selectPaging(page, piping));
		}
		piping.setDeptId(SecurityUtils.getUser().getDeptId()+"");// 科室
		return R.ok(pipingService.selectPaging(page, piping));
	}

	/**
	 * 条件数据查询
	 * @param piping
	 * @return
	 */
	@ApiOperation(value = "条件数据查询",notes = "条件数据查询")
	@SysLog("条件数据查询")
	@GetMapping("/all")
	public R all(Piping piping){
		/* 判断一下当前登录的用户是谁  管理员登录时  */
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(pipingService.selectAll(piping));
		}
		piping.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
		return R.ok(pipingService.selectAll(piping));
	}


}
