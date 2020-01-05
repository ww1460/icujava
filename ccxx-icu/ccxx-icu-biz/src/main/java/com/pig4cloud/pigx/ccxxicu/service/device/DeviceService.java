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

package com.pig4cloud.pigx.ccxxicu.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;

import java.util.List;

/**
 * 设备表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:58:40
 */
public interface DeviceService extends IService<Device> {


	/**
	 * 通过自增id查询数据
	 *
	 * @param deviceId
	 * @return
	 */
	Device selectByDeviceId(String deviceId);

	/**
	 * 条件查询数据
	 *
	 * @return
	 */
	List<Device> selectAll(Device device);

	/**
	 * 根据rfid查询设备信息
	 *
	 * @param eRfid
	 * @return
	 */
	Device selectByRfid(String eRfid);

	/**
	 * 根据设备rfid更新床位信息
	 *
	 * @return
	 */
	int updateByERfid(String eRfid, String bedId);


}
