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
 * 设备表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:58:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/device" )
@Api(value = "device", tags = "设备表管理")
public class DeviceController {

    private final DeviceService deviceService;
	/**
	 * 设备使用记录
	 */
    private final UseRecordService useRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param device 设备表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getDevDevicePage(Page page, Device device) {
		/* 判断一下当前登录的是谁， */
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 管理员 */
			/*查询未删除的数据*/
			device.setDelFlag(0);
			return R.ok(deviceService.page(page, Wrappers.query(device)));
		}
		/* 查询当前登录的用户科室信息 */
		Integer deptId = SecurityUtils.getUser().getDeptId();
		/*查询未删除的数据*/
		device.setDelFlag(0);
		/*当前用户科室的数据*/
		device.setDeptId(SecurityUtils.getUser().getDeptId()+"");
        return R.ok(deviceService.page(page, Wrappers.query(device)));
    }



	/**
	 *
	 * @param device 设备表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/list" )
	public R deviceLisr(Device device) {
		System.out.println(device+"-----------------设备传来的数据");
		/* 查询当前登录的用户科室信息 */
		Integer deptId = SecurityUtils.getUser().getDeptId();
		/*查询未删除的数据*/
		device.setDelFlag(0);
		/*当前用户科室的数据*/
		device.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		return R.ok(deviceService.selectAll(device));
	}




    /**
     * 通过id查询设备表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(deviceService.getById(id));
    }

    /**
     * 新增设备表
     * @param device 设备表
     * @return R
     */
    @ApiOperation(value = "新增设备表", notes = "新增设备表")
    @SysLog("新增设备表" )
    @PostMapping("/add")
    public R save(@RequestBody Device device) {
		Integer deptId = SecurityUtils.getUser().getDeptId();
		device.setDeptId(deptId+"");
		device.setDeviceId(SnowFlake.getId()+"");
    	return R.ok(deviceService.save(device));
    }

    /**
     * 修改设备表
     * @param device 设备表
     * @return R
     */
    @ApiOperation(value = "修改设备表", notes = "修改设备表")
    @SysLog("修改设备表" )
    @PostMapping("/update")
    public R updateById(@RequestBody Device device) {
		String nurseId  = SecurityUtils.getUser().getId()+"";
		device.setUpdateUserId(nurseId);
		/*  将状态变为未使用时*/
		if (device.getState() == DeviceEnum.NOT_USED.getCode()){
			Integer state =deviceService.getById(device.getId()).getState();
			if (state==DeviceEnum.IN_USE.getCode()){
				return R.failed(null,"当前设备正在连接中，请断开后操作！！！");
			}
		}
	   if (device.getState() == DeviceEnum.DISCONTINUE_USE.getCode()){  //  当将状态变为停用时
			///  设备变为停用时  查询当条设备是否正在连接中
			Integer state = deviceService.getById(device.getId()).getState();
			/* 查询当前设备的状态 如果是使用中时 */
			if (state==DeviceEnum.IN_USE.getCode()){
				return R.failed(null,"当前设备正在连接中，请断开后操作！！！");
			}
		}
        return R.ok(deviceService.updateById(device));
    }

    /**
     * 通过id删除设备表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除设备表", notes = "通过id删除设备表")
    @SysLog("通过id删除设备表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		Device device = deviceService.getById(id);
		UseRecord useRecord = new UseRecord();
		useRecord.setDeviceId(device.getDeviceId());
		List<UseRecordVo> useRecordVos = useRecordService.selectAll(useRecord);
		if (CollectionUtils.isNotEmpty(useRecordVos)){
			return R.failed(null,"当前设备正在使用中，不可以删除！！！！");
		}
		device.setDelFlag(1);
		device.setDelTime(LocalDateTime.now());
		device.setDelUserId(SecurityUtils.getUser().getId()+"");
        return R.ok(deviceService.updateById(device));
    }

}
