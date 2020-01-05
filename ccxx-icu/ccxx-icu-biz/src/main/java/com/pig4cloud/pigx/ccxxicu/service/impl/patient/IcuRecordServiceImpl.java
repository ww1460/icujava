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
package com.pig4cloud.pigx.ccxxicu.service.impl.patient;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.IcuRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.IcuRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.patient.IcuRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceService;
import com.pig4cloud.pigx.ccxxicu.service.device.UseRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.IcuRecordService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.piping.UseRecordsService;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
 *
 * @author pigx code generator
 * @date 2019-10-03 16:48:03
 */
@Service
public class IcuRecordServiceImpl extends ServiceImpl<IcuRecordMapper, IcuRecord> implements IcuRecordService {

	/**
	 *
	 */
	@Autowired
	private IcuRecordMapper icuRecordMapper;

	/**
	 * 患者
	 */
	@Autowired
	private PatientService patientService;
	/**
	 * 管道
	 */
	@Autowired
	private UseRecordsService useRecordsService;

	/**
	 * 设备使用记录
	 */
	@Autowired
	private DeviceService deviceService;

	/**
	 * 设备使用记录
	 */
	@Autowired
	private UseRecordService useRecordService;
	/**
	 * 护士
	 */
	@Autowired
	private NurseService nurseService;


	/**
	 * 分页查询数据
	 * @param page
	 * @param icuRecord
	 * @return
	 */
	@Override
	public IPage page(Page page, IcuRecordVo icuRecord) {
		icuRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
		return icuRecordMapper.page(page,icuRecord);
	}

	/**
	 * 新增接口
	 * @return
	 */
	@Override
	public Boolean add(IcuRecord icuRecord) {

		List<IcuRecord> list = new ArrayList<>();
		List<Integer> deptList = nurseService.nurseDept();
		System.out.println("科室信息----------------------------------------"+deptList);

		if (CollectionUtils.isNotEmpty(deptList)){
			deptList.forEach(e->{
				IcuRecord icu = new IcuRecord();
				//  利用科室id查询新入科患者
				String dept = e+"";
				/* 当前时间 */
				LocalDateTime now = LocalDateTime.now();
				/* 查询出当天新入科患者有几人 */
				Integer newDeptPatient = patientService.newDeptPatient(dept);
				/* 查询数据当天在科的患者数据 */
				Integer deptPatient = patientService.deptPatient(dept);

				/* 查询呼吸机数据 */
				Device device = new Device();
				device.setName("呼吸机");
				List<Device> deviceList = deviceService.selectAll(device);
				/* 新建一个设备使用记录 */
				com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord  deviceUseRecord = new com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord();
				int d = 0;  //计数，统计所有呼吸机的使用记录
				/*判断是否有呼吸机*/
				if (CollectionUtils.isNotEmpty(deviceList)){
					/* 当有呼吸机时，查询数据*/
					for (int i =0 ;i<deviceList.size();i++){

						//  科室使用记录
						deviceUseRecord.setDeptId(dept);//科室
						deviceUseRecord.setDeviceId(deviceList.get(i).getDeviceId());  //  利用呼吸机模糊查询出来的数据 进行查询
						Integer deviceUse = useRecordService.deviceUse(deviceUseRecord); // 设备
						d =deviceUse+d;  //利用新查询出来的数据加上之前使用的记录
					}
				}
				UseRecord useRecord = new UseRecord();  // 管道
				useRecord.setDeptId(dept);
				useRecord.setPipingId("23385636506775552");  //模拟数据  假设备
				Integer integer = useRecordsService.pipingUse(useRecord);
				System.out.println("今天使用   【23385636506775552】  管道的人有-------------------------"+integer);

				icu.setIcuRecordId(SnowFlake.getId()+"");//雪花
				icu.setDeptPatient(deptPatient);//在科患者
				icu.setNewDeptPatient(newDeptPatient);//新入科患者  indwellingCatheterPatient
				icu.setIndwellingCatheterPatient(integer);// 尿管管道
				icu.setArteriovenouPatient(integer); // 静脉插管患者
				icu.setRespiratorUsePatient(d);//设备
				icu.setRecordTime(LocalDateTime.now());//记录时间
				icu.setDeptId(dept);//科室
				list.add(icu);
			});
		}else{
			return false;
		}
		return this.saveBatch(list);
	}
}
