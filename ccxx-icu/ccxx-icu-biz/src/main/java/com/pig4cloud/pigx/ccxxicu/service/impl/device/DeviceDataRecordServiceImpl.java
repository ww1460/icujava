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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.DeviceDataRecord;
import com.pig4cloud.pigx.ccxxicu.mapper.device.DeviceDataRecordMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.device.DeviceMapper;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceDataRecordService;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:58:40
 */
@Service
public class DeviceDataRecordServiceImpl extends ServiceImpl<DeviceDataRecordMapper, DeviceDataRecord> implements DeviceDataRecordService {

	@Resource
	private DeviceMapper deviceMapper;


}
