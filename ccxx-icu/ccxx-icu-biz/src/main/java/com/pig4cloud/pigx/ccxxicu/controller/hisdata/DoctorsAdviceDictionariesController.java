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

package com.pig4cloud.pigx.ccxxicu.controller.hisdata;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceDictionaries;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceDictionariesService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *  医嘱项目字典表，对医嘱中的一些项目进行描述翻译
 *
 * @author pigx code generator
 * @date 2019-08-28 10:38:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doctorsadvicedictionaries" )
@Api(value = "doctorsadvicedictionaries", tags = " 医嘱项目字典表，对医嘱中的一些项目进行描述翻译管理")
public class DoctorsAdviceDictionariesController {

    private final DoctorsAdviceDictionariesService doctorsAdviceDictionariesService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param doctorsAdviceDictionaries  医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisDoctorsAdviceDictionariesPage(Page page, DoctorsAdviceDictionaries doctorsAdviceDictionaries) {

     	doctorsAdviceDictionaries.setDelFlag(0);

        return R.ok(doctorsAdviceDictionariesService.page(page, Wrappers.query(doctorsAdviceDictionaries)));
    }


	/**
	 * 新增前跳转页面
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@PostMapping("/preJump")
	public R preJump(){
		/* 字典表中查询 */
		List<String> dictionaries = new ArrayList<>();
		dictionaries.add("doctorsadvice");
		return R.ok(dictionaries);
	}

    /**
     * 通过id查询 医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(doctorsAdviceDictionariesService.getById(id));
    }

    /**
     * 新增 医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @param doctorsAdviceDictionaries  医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @return R
     */
    @ApiOperation(value = "新增 医嘱项目字典表，对医嘱中的一些项目进行描述翻译", notes = "新增 医嘱项目字典表，对医嘱中的一些项目进行描述翻译")
    @SysLog("新增 医嘱项目字典表，对医嘱中的一些项目进行描述翻译" )
    @PostMapping("/add")
    public R save(@RequestBody DoctorsAdviceDictionaries doctorsAdviceDictionaries) {
    	doctorsAdviceDictionaries.setDoctorsAdviceDictionariesId(SnowFlake.getId()+"");
        return R.ok(doctorsAdviceDictionariesService.save(doctorsAdviceDictionaries));
    }

    /**
     * 修改 医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @param doctorsAdviceDictionaries  医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @return R
     */
    @ApiOperation(value = "修改 医嘱项目字典表，对医嘱中的一些项目进行描述翻译", notes = "修改 医嘱项目字典表，对医嘱中的一些项目进行描述翻译")
    @SysLog("修改 医嘱项目字典表，对医嘱中的一些项目进行描述翻译" )
   @PostMapping("/update")
    public R updateById(@RequestBody DoctorsAdviceDictionaries doctorsAdviceDictionaries) {
        return R.ok(doctorsAdviceDictionariesService.updateById(doctorsAdviceDictionaries));
    }

    /**
     * 通过id删除 医嘱项目字典表，对医嘱中的一些项目进行描述翻译
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除 医嘱项目字典表，对医嘱中的一些项目进行描述翻译", notes = "通过id删除 医嘱项目字典表，对医嘱中的一些项目进行描述翻译")
    @SysLog("通过id删除 医嘱项目字典表，对医嘱中的一些项目进行描述翻译" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		DoctorsAdviceDictionaries byId = doctorsAdviceDictionariesService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
	return R.ok(doctorsAdviceDictionariesService.updateById(byId));
    }

}
