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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.UseRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.piping.PipingService;
import com.pig4cloud.pigx.ccxxicu.service.piping.UseRecordsService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 管道使用记录
 *
 * @author pigx code generator
 * @date 2019-08-08 16:23:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pinping/userecord" )
@Api(value = "/pinping/userecord", tags = "管道使用记录管理")
public class UseRecordsController {

    private final UseRecordsService useRecordsService;

	/**
	 * 管路表
	 */
	private final PipingService pipingService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param useRecord 管道使用记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPipUseRecordsPage(Page page, UseRecord useRecord) {
        return R.ok(useRecordsService.page(page, Wrappers.query(useRecord)));
    }

	/**
	 * 新增前跳转查询数据源
	 * @return
	 */
	@ApiOperation(value = "新增前跳转查询数据源",notes = "新增前跳转查询数据源")
	@SysLog("新增前跳转查询数据源")
	@PostMapping("/preJump")
    public R preJump(){
    	/*新增前查询当前所有的管路信息 */
		Piping piping = new Piping();
		piping.setDeptId(SecurityUtils.getUser().getDeptId()+"");  // 科室
		return R.ok(pipingService.selectAll(piping));
	}

    /**
     * 通过id查询管道使用记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {

    	return R.ok(useRecordsService.getById(id));
    }

    /**
     * 新增管道使用记录
     * @param useRecord 管道使用记录
     * @return R
     */
    @ApiOperation(value = "新增管道使用记录", notes = "新增管道使用记录")
    @SysLog("新增管道使用记录" )
    @PostMapping("/add")
    public R save(@RequestBody UseRecord useRecord) {
    	/*  当前患者使用管道 之后其他 患者也可以使用相同管道 */
    	useRecord.setUseRecordsId(SnowFlake.getId()+"");//雪花id
    	useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
		useRecord.setStartTime(LocalDateTime.now()); // 开始时间
		useRecord.setNurseId(SecurityUtils.getUser().getId()+""); // 护士is
        return R.ok(useRecordsService.save(useRecord));
    }


    /**
     * 修改管道使用记录
     * @param useRecord 管道使用记录
     * @return R
     */
    @ApiOperation(value = "修改管道使用记录", notes = "修改管道使用记录")
    @SysLog("修改管道使用记录" )
    @PostMapping("/update")
    public R updateById(@RequestBody UseRecord useRecord) {

    	return R.ok(useRecordsService.updateById(useRecord));
    }

	/**
	 * 断开当前管道
	 * @param useRecord
	 * @return
	 */
	@ApiOperation(value = "断开当前管道",notes = "断开当前管道")
	@SysLog("断开当前管道")
	@PostMapping("/breakPiping")
	public R breakPiping(@RequestBody UseRecord useRecord){
		/* 补全使用记录 */
		useRecord.setEndTime(LocalDateTime.now());//结束时间
		useRecord.setNurseId(SecurityUtils.getUser().getId()+"");//护士 id

		return  R.ok(useRecordsService.updateById(useRecord));
	}


	/**
     * 通过id删除管道使用记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除管道使用记录", notes = "通过id删除管道使用记录")
    @SysLog("通过id删除管道使用记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		UseRecord byId = useRecordsService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());/* 删除时间 */
		byId.setDelUserId(SecurityUtils.getUser().getId()+""); //删除人

		return R.ok(useRecordsService.updateById(byId));
    }


	/**
	 * 查询连接中的管道
	 * @param useRecord
	 * @return
	 */
	@ApiOperation(value = "查询连接中的管理",notes = "查询连接中的管路")
	@SysLog("查询连接中的设备")
	@PostMapping("/selectInConnected")
    public R selectInConnected(@RequestBody UseRecord useRecord){
		useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
    	return R.ok(useRecordsService.selectInConnected(useRecord));
	}

	/**
	 * 分页查询数据源
	 * @param page
	 * @param useRecord
	 * @return
	 */
	@ApiOperation(value = "分页查询数据源",notes = "分页查询数据源")
	@SysLog("分页查询数据源")
	@GetMapping("/selectPaging")
	public R selectPaging(Page page, UseRecord useRecord){
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(useRecordsService.selectPaging(page, useRecord));
		}
		useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室id
		return R.ok(useRecordsService.selectPaging(page, useRecord));
	}

	/**
	 * 数据全查
	 * @param useRecords
	 * @return
	 */
	@ApiOperation(value = "数据全查",notes = "数据全查")
	@SysLog("数据全查")
	@PostMapping("/selectAll")
	public R selectAll(@RequestBody UseRecordVo useRecords){
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(useRecordsService.selectAll(useRecords));
		}
		useRecords.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室id
		return R.ok(useRecordsService.selectAll(useRecords));
	}

	/**
	 * 条件数据查询，查询患者以使用过的管道
	 * @param useRecords
	 * @return
	 */
	@ApiOperation(value = "条件数据查询，查询患者以使用过的管道",notes = "条件数据查询，查询患者以使用过的管道")
	@SysLog("条件数据查询，查询患者以使用过的管道")
	@GetMapping("/selectUsedAll")
	public R selectUsedAll(UseRecordVo useRecords){
		return R.ok(useRecordsService.selectUsedAll(useRecords));
	}




}
