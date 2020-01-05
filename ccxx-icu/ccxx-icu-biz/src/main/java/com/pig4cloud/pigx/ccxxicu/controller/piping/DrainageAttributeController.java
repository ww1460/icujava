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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.DrainageAttribute;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.piping.DrainageAttributeService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * 引流液属性
 *
 * @author pigx code generator
 * @date 2019-08-10 15:48:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/drainageattribute" )
@Api(value = "drainageattribute", tags = "引流液属性管理")
public class DrainageAttributeController {

    private final DrainageAttributeService drainageAttributeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param pipDrainageAttribute 引流液属性
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPipDrainageAttributePage(Page page, DrainageAttribute pipDrainageAttribute) {

    	pipDrainageAttribute.setDelFlag(0);

        return R.ok(drainageAttributeService.page(page, Wrappers.query(pipDrainageAttribute)));
    }

	/**
	 * 新增前跳转页面
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@GetMapping("/preJump")
    public R preJump(DrainageAttribute drainageAttribute){
			drainageAttribute.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室
			return R.ok(drainageAttributeService.selectAttribute(drainageAttribute));
	}

    /**
     * 通过id查询引流液属性
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {

    	Map<String,Object> test = new HashMap<>();
    	DrainageAttribute drainageAttribute  = new DrainageAttribute();
		drainageAttribute.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室
		DrainageAttribute byId = drainageAttributeService.getById(id);
		if (byId==null){
			return R.failed(null,"没有查询到相应数据");
		}
		Map<String, Object> stringObjectMap = drainageAttributeService.selectAttribute(drainageAttribute);

		test.put("byId",byId);
		test.put("stringObjectMap",stringObjectMap);
		return R.ok(test);
    }

    /**
     * 新增引流液属性
     * @param pipDrainageAttribute 引流液属性
     * @return R
     */
    @ApiOperation(value = "新增引流液属性", notes = "新增引流液属性")
    @SysLog("新增引流液属性" )
    @PostMapping("/add")
    public R save(@RequestBody DrainageAttribute pipDrainageAttribute) {
    	pipDrainageAttribute.setDeptId(SecurityUtils.getUser().getDeptId()+"");
    	pipDrainageAttribute.setDrainageAttributeId(SnowFlake.getId()+"");
        return R.ok(drainageAttributeService.save(pipDrainageAttribute));
    }

    /**
     * 修改引流液属性
     * @param pipDrainageAttribute 引流液属性
     * @return R
     */
    @ApiOperation(value = "修改引流液属性", notes = "修改引流液属性")
    @SysLog("修改引流液属性" )
    @PostMapping("/updata")
    public R updateById(@RequestBody DrainageAttribute pipDrainageAttribute) {
        return R.ok(drainageAttributeService.updateById(pipDrainageAttribute));
    }

    /**
     * 通过id删除引流液属性
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除引流液属性", notes = "通过id删除引流液属性")
    @SysLog("通过id删除引流液属性" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		DrainageAttribute byId = drainageAttributeService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		return R.ok(drainageAttributeService.updateById(byId));
    }


	/**
	 * 分页查询数据
	 * @param page
	 * @param drainageAttribute
	 * @return
	 */
    @ApiOperation(value = "分页查询数据",notes = "分页查询数据")
	@SysLog("分页查询数据")
	@GetMapping("/selectPaging")
    public R selectPaging(Page page,DrainageAttribute drainageAttribute){

    	drainageAttribute.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室;
    	return R.ok(drainageAttributeService.selectPaging(page,drainageAttribute));
    	}

	/**
	 * 通过引流液id查询属性
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过引流液id查询属性",notes ="通过引流液id查询属性")
	@SysLog("通过引流液id查询属性")
    	@GetMapping("/drainageId/{id}")
    	public R drainageId(@PathVariable("id") String id){
    	return R.ok(drainageAttributeService.drainageId(id));
		}



}
