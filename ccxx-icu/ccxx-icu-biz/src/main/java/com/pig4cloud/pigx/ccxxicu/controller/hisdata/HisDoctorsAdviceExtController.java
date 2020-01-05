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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceExtService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 医嘱扩展表
 *
 * @author pigx code generator
 * @date 2019-10-14 19:33:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doctorsAdviceExt" )
@Api(value = "doctorsAdviceExt", tags = "医嘱扩展表管理")
public class HisDoctorsAdviceExtController {

    private final HisDoctorsAdviceExtService hisDoctorsAdviceExtService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param hisDoctorsAdviceExt 医嘱扩展表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisDoctorsAdviceExtPage(Page page, HisDoctorsAdviceExt hisDoctorsAdviceExt) {
        return R.ok(hisDoctorsAdviceExtService.page(page, Wrappers.query(hisDoctorsAdviceExt)));
    }


    /**
     * 通过id查询医嘱扩展表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(hisDoctorsAdviceExtService.getById(id));
    }

    /**
     * 新增医嘱扩展表
     * @param hisDoctorsAdviceExt 医嘱扩展表
     * @return R
     */
    @ApiOperation(value = "新增医嘱扩展表", notes = "新增医嘱扩展表")
    @SysLog("新增医嘱扩展表" )
    @PostMapping("/add")
    public R save(@RequestBody HisDoctorsAdviceExt hisDoctorsAdviceExt) {

        return R.ok(hisDoctorsAdviceExtService.save(hisDoctorsAdviceExt));
    }

    /**
     * 修改医嘱扩展表
     * @param hisDoctorsAdviceExt 医嘱扩展表
     * @return R
     */
    @ApiOperation(value = "修改医嘱扩展表", notes = "修改医嘱扩展表")
    @SysLog("修改医嘱扩展表" )
    @PostMapping("/update")
    public R updateById(@RequestBody HisDoctorsAdviceExt hisDoctorsAdviceExt) {
        return R.ok(hisDoctorsAdviceExtService.updateById(hisDoctorsAdviceExt));
    }

    /**
     * 通过id删除医嘱扩展表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除医嘱扩展表", notes = "通过id删除医嘱扩展表")
    @SysLog("通过id删除医嘱扩展表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(hisDoctorsAdviceExtService.removeById(id));
    }


	/**
	 * 通过执行医嘱生成任务
	 * @return
	 */
	@GetMapping("/doctorsAdviceExtAddTask")
    public R doctorsAdviceExtAddTask(){
		Boolean task = hisDoctorsAdviceExtService.doctorsAdviceExtAddTask();
		if (task){
			return R.ok("成功");
		}else {
			return R.failed("失败！！！");
		}

	}



	// selectTime
	/**
	 *
	 * @return
	 */
	@GetMapping("/selectTime")
	public R selectTime(){
		List<HisDoctorsAdviceExt> list = hisDoctorsAdviceExtService.selectTime();

		if (CollectionUtils.isNotEmpty(list)){
			return R.ok(list);
		}else{
			return  R.failed("没有查询到数据啊啊啊啊啊啊啊");
		}

	}

}
