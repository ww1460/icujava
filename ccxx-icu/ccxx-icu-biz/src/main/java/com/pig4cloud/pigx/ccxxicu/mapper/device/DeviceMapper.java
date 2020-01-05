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

package com.pig4cloud.pigx.ccxxicu.mapper.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:58:40
 */
public interface DeviceMapper extends BaseMapper<Device> {


	/**
	 * 通过自增id查询数据
	 *
	 * @param deviceId
	 * @return
	 */
	Device selectByDeviceId(@Param("deviceId") String deviceId);

	/**
	 * 条件查询数据
	 *
	 * @return
	 */
	List<Device> selectAll(@Param("device") Device device);


	/**
	 * 根据设备rfid更新床位信息
	 *
	 * @param eRfid
	 * @param bedId
	 * @return
	 */
	@Update("update dev_device SET position = #{bedId} WHERE rfid_id = #{eRfid}")
	int updateDevicePosition(@Param("eRfid") String eRfid, @Param("bedId") String bedId);

	/**
	 * 通过rfid查询数据
	 *
	 * @param eRfid
	 * @return
	 */
	@Select("select * from dev_device WHERE rfid_id = #{eRfid}")
	Device selectByRfid(@Param("eRfid") String eRfid);

	/**
	 * 在线设备数
	 * @return
	 */
	List<Device> getOnlineDevice();
}
