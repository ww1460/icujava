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
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.HisDoctorsAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 医嘱表
 *
 * @author pigx code generator
 * @date 2019-08-30 11:23:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doctorsadvic" )
@Api(value = "doctorsadvic", tags = "医嘱表管理")
public class HisDoctorsAdviceController {

    private final HisDoctorsAdviceService hisDoctorsAdviceService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param hisDoctorsAdvice 医嘱表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisDoctorsAdvicePage(Page page, HisDoctorsAdvice hisDoctorsAdvice) {
        return R.ok(hisDoctorsAdviceService.page(page, Wrappers.query(hisDoctorsAdvice)));
    }


    /**
     * 通过id查询医嘱表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
    	return R.ok(hisDoctorsAdviceService.getByIdAll(id));
    }

    /**
     * 新增医嘱表
     * @param hisDoctorsAdvice 医嘱表
     * @return R
     */
    @ApiOperation(value = "新增医嘱表", notes = "新增医嘱表")
    @SysLog("新增医嘱表" )
    @PostMapping("/add")
    public R save(@RequestBody HisDoctorsAdvice hisDoctorsAdvice) {
        return R.ok(hisDoctorsAdviceService.save(hisDoctorsAdvice));
    }

    /**
     * 修改医嘱表
     * @param hisDoctorsAdvice 医嘱表
     * @return R
     */
    @ApiOperation(value = "修改医嘱表", notes = "修改医嘱表")
    @SysLog("修改医嘱表" )
    @PostMapping("/update")
    public R updateById(@RequestBody HisDoctorsAdvice hisDoctorsAdvice) {
        return R.ok(hisDoctorsAdviceService.updateById(hisDoctorsAdvice));
    }

    /**
     * 通过id删除医嘱表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除医嘱表", notes = "通过id删除医嘱表")
    @SysLog("通过id删除医嘱表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(hisDoctorsAdviceService.removeById(id));
    }

	/**
	 * 接收his传来的医嘱
	 * @param doctorsAdviceBo
	 * @return
	 */
	@ApiOperation(value = "接收his传来的医嘱",notes = "接收his传来的医嘱")
	@SysLog("接收his传来的医嘱")
	@PostMapping("/hisDoctorsAdvice")
    public R hisDoctorsAdvice(@RequestBody HisDoctorsAdviceBo doctorsAdviceBo){
		return R.ok(hisDoctorsAdviceService.hisDoctorsAdvice(doctorsAdviceBo));
	}


	/**
	 * 全查医嘱数据
	 * @retur
	 */
	@ApiOperation(value = "全查医嘱数据",notes = "全查医嘱数据")
	@SysLog("全查医嘱数据")
	@GetMapping("/selectAll")
	public R selectAll(HisDoctorsAdvice hisDoctorsAdvice){
		return R.ok(hisDoctorsAdviceService.selectAll(hisDoctorsAdvice));
	}


	/**
	 * 查询当前登录护士相关的医嘱内容
	 * @retur
	 */
	@ApiOperation(value = "查询当前登录护士相关的医嘱内容",notes = "查询当前登录护士相关的医嘱内容")
	@SysLog("查询当前登录护士相关的医嘱内容")
	@GetMapping("/loginNurseDoctorsAdvice")
	public R LoginNurseDoctorsAdvice(Page page, HisDoctorsAdviceVo hisDoctorsAdviceVo){

		if("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){ //护士长
			hisDoctorsAdviceVo.setDeptId(SecurityUtils.getUser().getDeptId()+"");// 科室查询
			return R.ok(hisDoctorsAdviceService.LoginNurseDoctorsAdvice(page,hisDoctorsAdviceVo));
		}if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){//护士
			hisDoctorsAdviceVo.setLoginNurse(SecurityUtils.getUser().getId()+"");//护士id
			return R.ok(hisDoctorsAdviceService.LoginNurseDoctorsAdvice(page,hisDoctorsAdviceVo));
		}

		return R.ok(hisDoctorsAdviceService.LoginNurseDoctorsAdvice(page,hisDoctorsAdviceVo));
	}



	/**
	 * 测试
	 * @retur
	 */
	@ApiOperation(value = "全查医嘱数据",notes = "全查医嘱数据")
	@SysLog("全查医嘱数据")
	@Inner(value = false)
	@PostMapping("/all")
	public Object all(HisDoctorsAdvice hisDoctorsAdvice){
	String id = "001234";
		return hisDoctorsAdviceService.hisDoctorsAdviceId(id);
	}

}
