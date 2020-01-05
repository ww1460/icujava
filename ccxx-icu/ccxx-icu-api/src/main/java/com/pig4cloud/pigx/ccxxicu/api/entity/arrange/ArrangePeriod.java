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

package com.pig4cloud.pigx.ccxxicu.api.entity.arrange;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 排班周期结果表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:14
 */
@Data
@TableName("nur_arrange_period")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "排班周期结果表")
public class ArrangePeriod extends Model<ArrangePeriod> {
private static final long serialVersionUID = 1L;

    /**
     * 排班周期结果表
     */
    @TableId
    @ApiModelProperty(value="排班周期结果表")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String arrangePeriodId;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 周一班次
     */
    @ApiModelProperty(value="周一班次")
    private Integer monday;
    /**
     * 周二班次
     */
    @ApiModelProperty(value="周二班次")
    private Integer tuesday;
    /**
     * 周三班次
     */
    @ApiModelProperty(value="周三班次")
    private Integer wednesday;
    /**
     * 周四班次
     */
    @ApiModelProperty(value="周四班次")
    private Integer thursday;
    /**
     * 周五班次
     */
    @ApiModelProperty(value="周五班次")
    private Integer friday;
    /**
     * 周六班次
     */
    @ApiModelProperty(value="周六班次")
    private Integer saturday;
    /**
     * 周日班次
     */
    @ApiModelProperty(value="周日班次")
    private Integer sunday;
    /**
     * 上周剩余休息
     */
    @ApiModelProperty(value="上周剩余休息")
    private Double lastTimeNum;
	/**
	 * 本周剩余休息
	 */
	@ApiModelProperty(value="本周剩余休息")
	private Double thisTimeNum;
    /**
     * 排班的开始时间
     */
    @ApiModelProperty(value="排班的开始时间")
    private LocalDateTime startTime;
    /**
     * 排班的结束时间
     */
    @ApiModelProperty(value="排班的结束时间")
    private LocalDateTime endTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人id
     */
    @ApiModelProperty(value="创建人id")
    private String createUserId;
    /**
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
    private Integer delFlag;
    /**
     * 删除人id
     */
    @ApiModelProperty(value="删除人id")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
