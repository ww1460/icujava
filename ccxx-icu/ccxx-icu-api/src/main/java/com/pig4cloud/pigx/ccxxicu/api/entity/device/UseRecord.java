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
 * 设备使用记录表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:50:06
 */
@Data
@TableName("dev_use_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "设备使用记录表")
public class UseRecord extends Model<UseRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 设备使用记录表
     */
    @ApiModelProperty(value="设备使用记录表")
    private String useRecordId;
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
     * 设备管理表id
     */
    @ApiModelProperty(value="设备管理表id")
    private String deviceId;
	 /**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 项目编号，该仪器当前做的是什么项目
	 */
	@ApiModelProperty(value="项目编号，该仪器当前做的是什么项目")
	private String itemNumber;
    /**
     * 仪器开始使用的时间
     */
    @ApiModelProperty(value="仪器开始使用的时间")
    private LocalDateTime startTime;
    /**
     * 仪器结束使用时间
     */
    @ApiModelProperty(value="仪器结束使用时间")
    private LocalDateTime endTime;
    /**
     * 科室id(部门表)
     */
    @ApiModelProperty(value="科室id(部门表)")
    private String deptId;
    /**
     * 使用总时长
     */
    @ApiModelProperty(value="使用总时长")
    private String totalLengthOfTime;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
    }
