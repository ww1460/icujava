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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Nursing;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.piping.NursingService;
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
 * 管道护理【对插管患者做的护理事项】
 *
 * @author pigx code generator
 * @date 2019-09-03 15:16:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursing" )
@Api(value = "nursing", tags = "管道护理【对插管患者做的护理事项】管理")
public class NursingController {

    private final NursingService nursingService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param nursing 管道护理【对插管患者做的护理事项】
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPipNursingPage(Page page, Nursing nursing) {
        return R.ok(nursingService.selectPaging(page,nursing));
    }


	/**
	 * 新增前跳转查询数据源
	 * @return
	 */
	@ApiOperation(value = "新增前跳转查询数据源",notes = "新增前跳转查询数据源")
	@SysLog("新增前跳转查询数据源")
	@GetMapping("/preJump")
	public R preJump(){

		List<String> dictionaries = new ArrayList<>();
		dictionaries.add("piping_preventive_measures");
		dictionaries.add("icu_departure");
		dictionaries.add("piping_causes_of_slippage");
		return R.ok(dictionaries);
	}



    /**
     * 通过id查询管道护理【对插管患者做的护理事项】
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(nursingService.getById(id));
    }

    /**
     * 新增管道护理【对插管患者做的护理事项】
     * @param nursing 管道护理【对插管患者做的护理事项】
     * @return R
     */
    @ApiOperation(value = "新增管道护理【对插管患者做的护理事项】", notes = "新增管道护理【对插管患者做的护理事项】")
    @SysLog("新增管道护理【对插管患者做的护理事项】" )
    @PostMapping("/add")
    public R save(@RequestBody Nursing nursing) {
    	nursing.setPipNursingId(SnowFlake.getId()+"");
    	nursing.setCreateUserId(SecurityUtils.getUser().getId()+"");
    	nursing.setDeptId(SecurityUtils.getUser().getDeptId()+"");
    	nursing.setCreateUserId(SecurityUtils.getUser().getId()+"");
    	nursing.setCreateTime(LocalDateTime.now());

    	return R.ok(nursingService.save(nursing));
    }

    /**
     * 修改管道护理【对插管患者做的护理事项】
     * @param nursing 管道护理【对插管患者做的护理事项】
     * @return R
     */
    @ApiOperation(value = "修改管道护理【对插管患者做的护理事项】", notes = "修改管道护理【对插管患者做的护理事项】")
    @SysLog("修改管道护理【对插管患者做的护理事项】" )
    @PostMapping("/update")
    public R updateById(@RequestBody Nursing nursing) {
        return R.ok(nursingService.updateById(nursing));
    }

    /**
     * 通过id删除管道护理【对插管患者做的护理事项】
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除管道护理【对插管患者做的护理事项】", notes = "通过id删除管道护理【对插管患者做的护理事项】")
    @SysLog("通过id删除管道护理【对插管患者做的护理事项】" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		Nursing byId = nursingService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");

		return R.ok(nursingService.updateById(byId));
    }

}
