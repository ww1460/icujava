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

package com.pig4cloud.pigx.ccxxicu.api.entity.task;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 任务定时执行
 *
 * @author pigx code generator
 * @date 2019-08-15 16:17:00
 */
@Data
@TableName("tak_timing_execution")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务定时执行")
public class TimingExecution extends Model<TimingExecution> {
private static final long serialVersionUID = 1L;

    /**
     * 任务定时执行表
     */
    @TableId
    @ApiModelProperty(value="任务定时执行表")
    private Integer id;
	/**
	 * 来源id
	 */
	@ApiModelProperty(value="来源id")
	private String sourceId;
	/**
	 * 来源类型【医嘱，还是自建】
	 */
	@ApiModelProperty(value="来源类型【医嘱，还是自建】")
	private Integer source;
	/**
	 * 任务名称
	 */
	@ApiModelProperty(value="任务名称")
	private String taskName;
	/**
	 * 任务描述
	 */
	@ApiModelProperty(value="任务描述")
	private String taskDescribe;
    /**
     * 下次执行时间
     */
    @ApiModelProperty(value="下次执行时间")
    private LocalDateTime nextExecutionTime;
    /**
     * 间隔几小时执行一次（单位 ：小时）
     */
    @ApiModelProperty(value="间隔几小时执行一次（单位 ：小时）")
    private Integer intervalTimes;
	/**
	 * 执行类型【0  提醒     1是执行 默认执行形式】
	 */
	@ApiModelProperty(value="执行类型【0  提醒     1是执行】")
	private Integer executionType;
    /**
     * 预定开始执行时间
     */
    @ApiModelProperty(value="预定开始执行时间")
    private LocalDateTime preStartTime;
    /**
     * 预定结束 时间
     */
    @ApiModelProperty(value="预定结束 时间")
    private LocalDateTime preEndTime;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 创建当前数据的用户
     */
    @ApiModelProperty(value="创建当前数据的用户")
    private String createUserId;
    /**
     * 创建当前数据的时间 
     */
    @ApiModelProperty(value="创建当前数据的时间 ")
    private LocalDateTime createTime;
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
    }
