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

package com.pig4cloud.pigx.ccxxicu.api.entity.patient;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 跌倒、坠床记录表
 *
 * @author pigx code generator
 * @date 2019-09-29 10:10:03
 */
@Data
@TableName("pat_tumble_fall")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "跌倒、坠床记录表")
public class TumbleFall extends Model<TumbleFall> {
private static final long serialVersionUID = 1L;

    /**
     * 跌倒、坠床记录 自增id
     */
    @TableId
    @ApiModelProperty(value="跌倒、坠床记录 自增id")
    private Integer id;
    /**
     * 跌倒、坠床记录 生成id
     */
    @ApiModelProperty(value="跌倒、坠床记录 生成id")
    private String TumbleFallId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 床号
     */
    @ApiModelProperty(value="床号")
    private String bedCode;
    /**
     * 患者姓名
     */
    @ApiModelProperty(value="患者姓名")
    private String patientName;
    /**
     * 患者年龄
     */
    @ApiModelProperty(value="患者年龄")
    private String patientAge;
    /**
     * 住院号
     */
    @ApiModelProperty(value="住院号")
    private String admissionNumber;
    /**
     * 诊断
     */
    @ApiModelProperty(value="诊断")
    private String patientDiagnose;
    /**
     * 病区
     */
    @ApiModelProperty(value="病区")
    private String deptName;
    /**
     * 主要的照顾者
     */
    @ApiModelProperty(value="主要的照顾者")
    private String mainMinder;

	/**
	 * 发生地点
	 */
	@ApiModelProperty(value="发生地点")
	private String occurrenceAddress;
    /**
     * 发生时间
     */
    @ApiModelProperty(value="发生时间")
    private LocalDateTime occurrenceTime;
    /**
     * 跌倒的患者原因
     */
    @ApiModelProperty(value="跌倒的患者原因")
    private String fallPatientReason;
	/**
	 * 跌倒的患者原因备注
	 */
	@ApiModelProperty(value="跌倒的患者原因备注")
	private String fallPatientReasonRemarks;
    /**
     * 跌倒的医疗原因
     */
    @ApiModelProperty(value="跌倒的医疗原因")
    private String fallMedicalReason;
    /**
     * 跌倒的环境原因
     */
    @ApiModelProperty(value="跌倒的环境原因")
    private String fallEnvironmentReason;
    /**
     * 跌倒的其它原因
     */
    @ApiModelProperty(value="跌倒的其它原因")
    private String fallOtherReason;
	/**
	 * 跌倒的其它原因备注
	 */
	@ApiModelProperty(value="跌倒的其它原因备注")
	private String fallOtherReasonRemarks;
    /**
     * 跌倒(坠床)引起的外伤
     */
    @ApiModelProperty(value="跌倒(坠床)引起的外伤")
    private String fallCauseTrauma;
    /**
     * 引起的外伤
     */
    @ApiModelProperty(value="引起的外伤")
    private String fallCauseTraumaLevel;
    /**
     * 共计跌倒次数
     */
    @ApiModelProperty(value="共计跌倒次数")
    private String totalFallNum;
    /**
     * 处理情况
     */
    @ApiModelProperty(value="处理情况")
    private String handlingInformation;
    /**
     * 护理签名
     */
    @ApiModelProperty(value="护理签名")
    private String nurseName;
    /**
     * 讨论时间
     */
    @ApiModelProperty(value="讨论时间")
    private String discussionTime;
    /**
     * 主持人
     */
    @ApiModelProperty(value="主持人")
    private String questionMaster;
    /**
     * 讨论记录
     */
    @ApiModelProperty(value="讨论记录")
    private String discussionRecord;
    /**
     * 参与人员
     */
    @ApiModelProperty(value="参与人员")
    private String participantName;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
    private Integer delFlag;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delUserId;
    }
