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

package com.pig4cloud.pigx.ccxxicu.controller.nurse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ProcessRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.ProcessRecordVo;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ProcessRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 流程记录表
 *
 * @author pigx code generator
 * @date 2019-08-07 09:07:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/processrecord" )
@Api(value = "processrecord", tags = "流程记录表管理")
public class ProcessRecordController {


    private final ProcessRecordService processRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param nurProcessRecord 流程记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNurProcessRecordPage(Page page, ProcessRecord nurProcessRecord) {
        return R.ok(processRecordService.page(page, Wrappers.query(nurProcessRecord)));
    }


    /**
     * 通过id查询流程记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(processRecordService.getById(id));
    }

    /**
     * 新增流程记录表
     * @param nurProcessRecord 流程记录表
     * @return R
     */
    @ApiOperation(value = "新增流程记录表", notes = "新增流程记录表")
    @SysLog("新增流程记录表" )
    @PostMapping("/add")
    public R save(@RequestBody ProcessRecord nurProcessRecord) {

    	nurProcessRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
		nurProcessRecord.setSpecificStepId("1");
		nurProcessRecord.setStatus(1); // 执行状态
		nurProcessRecord.setStartTime(LocalDateTime.now());//开始时间
		nurProcessRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");//创建用户

        return R.ok(processRecordService.save(nurProcessRecord));
    }

    /**
     * 修改流程记录表
     * @param nurProcessRecord 流程记录表
     * @return R
     */
    @ApiOperation(value = "修改流程记录表", notes = "修改流程记录表")
    @SysLog("修改流程记录表" )
    @PostMapping("/update")
    public R updateById(@RequestBody ProcessRecord nurProcessRecord) {
		ProcessRecord byId = processRecordService.getById(nurProcessRecord.getId());//通过id查询一下当条数据之前的内容将之前的内容取出
		nurProcessRecord.setSpecificStepId(byId.getSpecificStepId()+","+nurProcessRecord.getSpecificStepId());//将之前的内容和新出传来的内容进行拼接
		// 当完成时
		if(nurProcessRecord.getStatus()!=null&&nurProcessRecord.getStatus()==2){
			nurProcessRecord.setEndTime(LocalDateTime.now());//结束时间
			nurProcessRecord.setCompleteNurseId(SecurityUtils.getUser().getId()+"");// 完成护士id
		}

		return R.ok(processRecordService.updateById(nurProcessRecord));
    }


    /**
     * 通过id删除流程记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除流程记录表", notes = "通过id删除流程记录表")
    @SysLog("通过id删除流程记录表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		ProcessRecord byId = processRecordService.getById(id);
		byId.setDelFlag(1);  //删除
		byId.setDelTime(LocalDateTime.now());  //删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+""); //删除用户
		return R.ok(processRecordService.updateById(byId));
    }


	/**
	 * 分页查询流程记录
	 * @param page
	 * @param nurProcessRecord
	 * @return
	 */
    @ApiOperation(value = "分页查询流程记录",notes = "分页查询流程记录")
	@SysLog("分页查询流程记录")
	@GetMapping("/selectPaging")
    private R selectPaging(Page page, ProcessRecord nurProcessRecord){
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(processRecordService.selectPaging(page, nurProcessRecord));
		}
		nurProcessRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		return R.ok(processRecordService.selectPaging(page, nurProcessRecord));
    }


	/**
	 * 通过患者id查询相应的数据
	 * @param processRecord
	 * @return
	 */
	@ApiOperation(value = "通过患者查询相应的数据",notes = "通过患者id查询相应的数据")
	@SysLog("通过患者查询相应的数据")
	@PostMapping("/selectPatient")
    public R selectPatient(@RequestBody ProcessRecord processRecord){
		/* 再查询是哪个科室的下的患者 */

		processRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
		List<ProcessRecordVo> processRecordVo = processRecordService.selectAll(processRecord);
		if (processRecordVo ==null||processRecordVo.size()==0){
			/* 查询出当前患者             1 没有流程             */
			return R.ok(null,"1");
		}else{                                  //     0   有流程             //
			return  R.ok(processRecordVo.get(0),"0");
		}
	}




}
