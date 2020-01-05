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
@TableName("dev_device")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "设备表")
public class Device extends Model<Device> {
private static final long serialVersionUID = 1L;

    /**
     * 设备表 id
     */
    @TableId
    @ApiModelProperty(value="设备表 id")
    private Integer id;
    /**
     * 设备表
     */
    @ApiModelProperty(value="设备表")
    private String deviceId;
    /**
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    private String name;
    /**
     * 设备型号
     */
    @ApiModelProperty(value="设备型号")
    private String model;
    /**
     * RFID id编号
     */
    @ApiModelProperty(value="RFID id编号")
    private String rfidId;
	/**
	 * Bed RFID id编号
	 */
	@ApiModelProperty(value="病床的id编号")
    private String position;
    /**
     * 设备编号
     */
    @ApiModelProperty(value="设备编号")
    private String deviceNumber;
    /**
     * 状态 2 代表未使用.1代表使用中 0停用
     */
    @ApiModelProperty(value="状态 2 代表未使用.1代表使用中 0停用")
    private Integer state;
    /**
     * 厂家
     */
    @ApiModelProperty(value="厂家")
    private String factory;
    /**
     * 联系人
     */
    @ApiModelProperty(value="联系人")
    private String contacts;
    /**
     * 联系人 手机号
     */
    @ApiModelProperty(value="联系人 手机号")
    private String cellPhoneNumber;
    /**
     * 经销商
     */
    @ApiModelProperty(value="经销商")
    private String dealer;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 设备以使用时长（记录设备之前 的时长统计）
     */
    @ApiModelProperty(value="设备以使用时长（记录设备之前 的时长统计）")
    private Integer useTime;
    /**
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除该条数据的用户
     */
    @ApiModelProperty(value="删除该条数据的用户")
    private String delUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改该条数据的用户
     */
    @ApiModelProperty(value="修改该条数据的用户")
    private String updateUserId;
    }
