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

package com.pig4cloud.pigx.ccxxicu.api.entity.device;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 设备表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:58:40
 */
@Data
@TableName("dev_data_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "设备采集数据记录表")
public class DeviceDataRecord extends Model<DeviceDataRecord> {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备表 id
	 */
	@TableId
	@ApiModelProperty(value = "设备表 id")
	private Integer id;
	/**
	 * 设备表
	 */
	@ApiModelProperty(value = "设备Rfid")
	private String deviceRfid;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "病人Rfid")
	private String patientRfid;
	/**
	 * 设备型号
	 */
	@ApiModelProperty(value = "设备所在的IP")
	private String deviceIp;
	/**
	 * Bed RFID id编号
	 */
	@ApiModelProperty(value = "采集到的数据的code")
	private String parameterCode;

	/**
	 * Bed RFID id编号
	 */
	@ApiModelProperty(value = "采集到的数据的value")
	private String parameterValue;
	/**
	 * 设备编号
	 */
	@ApiModelProperty(value = "数据插入时间")
	private LocalDateTime createTime;
	/**
	 * Unix时间戳
	 */
	@ApiModelProperty(value = "采集到的时间戳")
	private Long timestamp;

}
