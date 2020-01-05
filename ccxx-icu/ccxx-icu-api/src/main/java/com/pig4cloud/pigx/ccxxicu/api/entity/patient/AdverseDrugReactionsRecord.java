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
 * 患者药物不良反应记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:46:08
 */
@Data
@TableName("pat_adverse_drug_reactions_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者药物不良反应记录表")
public class AdverseDrugReactionsRecord extends Model<AdverseDrugReactionsRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 患者药物不良反应记录表
     */
    @TableId
    @ApiModelProperty(value="患者药物不良反应记录表")
    private Integer id;
    /**
     * his医雪花id
     */
    @ApiModelProperty(value="his医嘱雪花id")
    private String hisDoctorsAdviceId;
    /**
     * 医嘱内容
     */
    @ApiModelProperty(value="医嘱内容")
    private String doctorsAdviceContent;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 患者姓名
     */
    @ApiModelProperty(value="患者姓名")
    private String patientName;
    /**
     * 患者性别  1男 0女
     */
    @ApiModelProperty(value="患者性别  1男 0女")
    private String gender;
    /**
     * 住院号
     */
    @ApiModelProperty(value="住院号")
    private String hospitalnumber;
    /**
     * 患者年龄
     */
    @ApiModelProperty(value="患者年龄")
    private String age;
    /**
     * 患者病情
     */
    @ApiModelProperty(value="患者病情")
    private String diagnosis;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 用药开始时间
     */
    @ApiModelProperty(value="用药开始时间")
    private LocalDateTime startTime;
    /**
     * 用药结束时间
     */
    @ApiModelProperty(value="用药结束时间")
    private LocalDateTime endTime;
    /**
     * 事件发生时间
     */
    @ApiModelProperty(value="事件发生时间")
    private LocalDateTime occurrenceTime;
    /**
     * 事件名/不良反应名
     */
    @ApiModelProperty(value="事件名/不良反应名")
    private String adverseName;
    /**
     * 不良反应的症状
     */
    @ApiModelProperty(value="不良反应的症状")
    private String symptom;
    /**
     * 药品名
     */
    @ApiModelProperty(value="药品名")
    private String drugName;
    /**
     * 用法用量
     */
    @ApiModelProperty(value="用法用量")
    private String dose;
    /**
     * 处理过程
     */
    @ApiModelProperty(value="处理过程")
    private String treatProcess;
    /**
     * 处理结果
     */
    @ApiModelProperty(value="处理结果")
    private String treatResult;
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
     * 删除该条数据的用户
     */
    @ApiModelProperty(value="删除该条数据的用户")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
