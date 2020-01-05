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

package com.pig4cloud.pigx.ccxxicu.api.entity.hisdata;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 医嘱表
 *
 * @author pigx code generator
 * @date 2019-08-30 11:23:01
 */
@Data
@TableName("his_doctors_advice")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "医嘱表")
public class HisDoctorsAdvice extends Model<HisDoctorsAdvice> {
private static final long serialVersionUID = 1L;

    /**
     * 医嘱表
     */
    @TableId
    @ApiModelProperty(value="医嘱表")
    private Integer id;
    /**
     * 雪花
     */
    @ApiModelProperty(value="雪花")
    private String doctorsAdviceId;

	/**
	 * his医嘱id
	 */
	@ApiModelProperty(value="his医嘱id")
	private String hisDoctorsAdviceId;
    /**
     * 组号【批次】
     */
    @ApiModelProperty(value="组号【批次】")
    private String batchNumber;
    /**
     * 执行次数
     */
    @ApiModelProperty(value="执行次数")
    private Integer frequencyCount;
    /**
     * 下医嘱时间
     */
    @ApiModelProperty(value="下医嘱时间")
    private LocalDateTime doctorsAdviceTime;
	/**
	 * 首次执行时间
	 */
	@ApiModelProperty(value="首次执行时间")
	private LocalDateTime firstExecutionTime;

	/**
	 * 自动停止时间
	 */
	@ApiModelProperty(value="自动停止时间")
	private LocalDateTime automaticStopTime;

    /**
     * 医嘱类型   2临时     1长期
     */
    @ApiModelProperty(value="医嘱类型   2临时     1长期")
    private String type;
    /**
     * 是否为紧急任务可以分为3个等级 1一般 2 需要加急 3紧急ip
     */
    @ApiModelProperty(value="是否为紧急任务可以分为3个等级 1一般 2 需要加急 3紧急")
    private String emergency;
	/**
	 * HIS中患者id
	 */
	@ApiModelProperty(value="患者id（his）")
	private String hisPatientId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id（his）")
    private String patientId;
    /**
     * 医嘱状态  1为医嘱结束    2为医生停止医嘱   3为护士停止医嘱
     */
    @ApiModelProperty(value="医嘱状态")
    private String state;
    /**
     * 医嘱创建接收时间
     */
    @ApiModelProperty(value="医嘱创建接收时间")
    private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;
	/**
	 * His通知标识 0 未通知  1已通知
	 */
	@ApiModelProperty(value = "His通知标识 0 未通知  1已通知")
	private Integer hisNoticeFlag;
    /**
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private String delFlag;
	/**
	 * 频率
	 */
	@ApiModelProperty(value="频率")
	private String frequency;
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
    }
