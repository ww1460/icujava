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

package com.pig4cloud.pigx.ccxxicu.controller.device;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.device.UseRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DeviceEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceService;
import com.pig4cloud.pigx.ccxxicu.service.device.UseRecordService;
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
 * 设备使用记录表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:50:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device/userecord" )
@Api(value = "/device/userecord", tags = "设备使用记录表管理")
public class UseRecordController {

    private final UseRecordService useRecordService;

	/**
	 * 设备表
	 */
	private final DeviceService deviceService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param useRecord 设备使用记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getDevUseRecordPage(Page page, UseRecord useRecord) {
    	useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
        return R.ok(useRecordService.page(page, Wrappers.query(useRecord)));
    }


    /**
     * 通过id查询设备使用记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(useRecordService.getById(id));
    }



	/**
	 * 新增前跳转页面
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes ="新增前跳转页面")
	@SysLog("新增设备使用记录表")
	@PostMapping("/preJump")
    public R preJump(){
	Device equipment = new Device();
	  equipment.setState(DeviceEnum.NOT_USED.getCode()); //未使用的
	 // equipment.setDeptId(SecurityUtils.getUser().getDeptId()+"");  //科室信息
		/*  查询到所有未使用的设备*/
		return R.ok(deviceService.selectAll(equipment));
	}


    /**
     * 新增设备使用记录表
     * @param useRecord 设备使用记录表
     * @return R
     */
    @ApiOperation(value = "新增设备使用记录表", notes = "新增设备使用记录表")
    @SysLog("新增设备使用记录表" )
    @PostMapping("/add")
    public R save(@RequestBody UseRecord useRecord) {
		/**
		 * 新增设备使用记录，新增时，使用的设备状态也会变为使用中
		 */
		/* 查询到当前设备的数据 */
		Device device = deviceService.selectByDeviceId(useRecord.getDeviceId());
		/* 判断一下当前的设备是否在使用中 */
		if (device.getState()==DeviceEnum.IN_USE.getCode()){
			return R.failed(null,"当前设备正在使用中！！！！！");
		}

		/* 将其使用状态变为使用中*/
		device.setState(DeviceEnum.IN_USE.getCode());
		boolean b = deviceService.updateById(device);
		if (b==false){
			return R.ok(null,"修改设备状态失败");
		}
		useRecord.setStartTime(LocalDateTime.now());//开始连接时间
		 useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
		 useRecord.setNurseId(SecurityUtils.getUser().getId()+"");// 用户
		useRecord.setUseRecordId(SnowFlake.getId()+""); //自增id

		return R.ok(useRecordService.save(useRecord));
    }

    /**
     * 修改设备使用记录表
     * @param useRecord 设备使用记录表
     * @return R
     */
    @ApiOperation(value = "修改设备使用记录表", notes = "修改设备使用记录表")
    @SysLog("修改设备使用记录表" )
    @PostMapping("/update")
    public R updateById(@RequestBody UseRecord useRecord) {
    	return R.ok(useRecordService.updateById(useRecord));
    }


	/**
	 * 断开设备
	 * @return
	 */
	@ApiOperation(value = "断开当前设备",notes = "断开当前设备")
	@SysLog("断开当前设备")
	@PostMapping("/breakDevice")
	public R breakDevice(@RequestBody String id){
		return R.ok(useRecordService.breakDevice(id));
	}




    /**
     * 通过id删除设备使用记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除设备使用记录表", notes = "通过id删除设备使用记录表")
    @SysLog("通过id删除设备使用记录表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		UseRecord useRecord = useRecordService.getById(id);
		useRecord.setDelFlag(1);  // 删除标识
		useRecord.setDelTime(LocalDateTime.now());  // 删除时间
		useRecord.setDelUserId(SecurityUtils.getUser().getId()+""); //   护士信息

		/* 查询 当前设备是否在连接中  如果是这要求患者断开 */
		UseRecord useRecords = new UseRecord();
		useRecords.setDeviceId(useRecord.getDeviceId()); // 设备名称
		useRecords.setDeptId(useRecord.getDeptId());//科室
		useRecords.setPatientId(useRecord.getPatientId()); //患者
		List<UseRecordVo> useRecordVos = useRecordService.selectInConnected(useRecords);
		if (CollectionUtils.isNotEmpty(useRecordVos)){
			return R.failed(null,"当前患者正在使用中不可删除！！！");
		}

		return R.ok(useRecordService.updateById(useRecord));
    }

	/**
	 * 分页查询数据
	 * @param page
	 * @param useRecord
	 * @return
	 */
    @ApiOperation(value = "分页查询数据",notes ="分页查询数据")
	@SysLog("分页查询数据")
	@GetMapping("/selectPaging")
    public R selectPaging(Page page,UseRecord useRecord){
		//IPage iPage = useRecordService.selectPaging(page, useRecord);

		/* 判断一下当前登录的是谁， */
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 管理员 */
			return R.ok(useRecordService.selectPaging(page, useRecord));
		}

		useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");  // 科室id查询
		return  R.ok(useRecordService.selectPaging(page, useRecord));
	}


	/**
	 * 条件数据全查
	 * @param useRecord
	 * @return
	 */
	@ApiOperation(value = "条件数据全查",notes = "条件数据全查")
	@SysLog("条件数据全查")
	@GetMapping("/selectAll")
	public R selectAll(UseRecord useRecord){
		return R.ok(useRecordService.selectAll(useRecord));
	}



	/**
	 * 查询连接中的设备
	 * @param useRecord
	 * @return
	 */
	@ApiOperation(value = "查询连接中的设备",notes = "查询连接中的设备")
	@SysLog("查询连接中的设备")
	@GetMapping("/selectInConnected")
	public R selectInConnected(UseRecord useRecord){
		useRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		return R.ok(useRecordService.selectInConnected(useRecord));
	}





}
