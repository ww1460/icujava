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
package com.pig4cloud.pigx.ccxxicu.service.impl.device;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.device.UseRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DeviceEnum;
import com.pig4cloud.pigx.ccxxicu.mapper.device.UseRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceService;
import com.pig4cloud.pigx.ccxxicu.service.device.UseRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备使用记录表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:50:06
 */
@Service
public class UseRecordServiceImpl extends ServiceImpl<UseRecordMapper, UseRecord> implements UseRecordService {


	@Autowired
	private UseRecordMapper useRecordMapper;

	/**
	 * 设备
	 */
	@Autowired
	private DeviceService deviceService;

	/**
	 * 分页查询数据
	 * @param page
	 * @param useRecord
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, UseRecord useRecord) {
		return useRecordMapper.selectPaging(page,useRecord);
	}



	/**
	 * 条件数据查询
	 * @param useRecord
	 * @return
	 */
	@Override
	public List<UseRecordVo> selectAll(UseRecord useRecord) {
		return useRecordMapper.selectAll(useRecord);
	}

	/**
	 * 条件查询连接中的设备
	 * @param useRecord
	 * @return
	 */
	@Override
	public List<UseRecordVo> selectInConnected(UseRecord useRecord) {
		return useRecordMapper.selectInConnected(useRecord);
	}

	/**
	 * 断开当前患者连接的所有设备
	 * @param patientId
	 * @return
	 */
	@Override
	public Boolean stopDevice(String patientId) {
		/* 通过患者id查询当前患者关联的设备 */
		List<UseRecord> useRecords = useRecordMapper.stopDevice(patientId);
		if (CollectionUtils.isNotEmpty(useRecords)){
			/*创建一个设备的集合 ，用于断开连接后将设备变为 未连接状态*/
			List<Device> devices = new ArrayList<>();
			useRecords.forEach(e->{
				e.setEndTime(LocalDateTime.now());//结束时间
				/* 计算使用多长时间 */
				long hours = ChronoUnit.HOURS.between(e.getStartTime(), e.getEndTime());
				e.setTotalLengthOfTime(hours+"");
				/* 用连接中的设备查询到设备的相关参数 */
				Device device = deviceService.selectByDeviceId(e.getDeviceId());
				device.setState(DeviceEnum.NOT_USED.getCode());  // 将设备设置为未使用
				devices.add(device);
			});
			/* 修改连接中的设备状态 */
			boolean b = this.updateBatchById(useRecords);
			if (b ==false){
				return false;
			}
			/* 修改设备的连接状态  */
			boolean b1 = deviceService.updateBatchById(devices);
			if (b1==false){
				return  false;
			}

			return true;
		}else {
			/* 在没有设备的时候直接就是成功 */
			return true;
		}
	}

	/**
	 * 断开设备 selectInConnected
	 * @param id
	 * @return
	 */
	@Override
	public R breakDevice(String id) {
		UseRecord useRecord = this.getById(id);

		useRecord.setEndTime(LocalDateTime.now()); //断开时间
		/* 计算使用多长时间 */
		long hours = ChronoUnit.MINUTES.between(useRecord.getStartTime(), useRecord.getEndTime());
		useRecord.setTotalLengthOfTime(hours+"");
		/*同时将使用的设备状态变为未使用 */
		Device device = deviceService.selectByDeviceId(useRecord.getDeviceId());
		device.setState(DeviceEnum.NOT_USED.getCode());
		boolean b = deviceService.updateById(device);
		if (b==false){
			return R.failed(null,"关闭设备失败");
		}
		return R.ok(this.updateById(useRecord));
	}


	/**
	 * 通过科室查询 某条管道 的今天的使用记录
	 * @param useRecord
	 * @return
	 */
	@Override
	public Integer deviceUse(UseRecord useRecord) {
		return useRecordMapper.deviceUse(useRecord);
	}
}
