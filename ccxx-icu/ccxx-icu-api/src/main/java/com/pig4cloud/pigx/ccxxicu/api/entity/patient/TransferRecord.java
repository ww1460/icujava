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
 * 患者转科交接记录
 *
 * @author pigx code generator
 * @date 2019-10-04 15:06:56
 */
@Data
@TableName("pat_transfer_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者转科交接记录")
public class TransferRecord extends Model<TransferRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 患者转科交接记录 id
     */
    @TableId
    @ApiModelProperty(value="患者转科交接记录 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String transferRecordId;
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
     * 患者年龄
     */
    @ApiModelProperty(value="患者年龄")
    private String patientAge;
    /**
     * 患者性别
     */
    @ApiModelProperty(value="患者性别")
    private String patientSex;
    /**
     * 患者诊断
     */
    @ApiModelProperty(value="患者诊断")
    private String patientDiagnose;
    /**
     * 患者的住院号
     */
    @ApiModelProperty(value="患者的住院号")
    private String patientAdmissionNumber;
    /**
     * 患者体温
     */
    @ApiModelProperty(value="患者体温")
    private String patientTemperature;
    /**
     * 患者呼吸
     */
    @ApiModelProperty(value="患者呼吸")
    private String patientBreathe;
    /**
     * 患者脉搏
     */
    @ApiModelProperty(value="患者脉搏")
    private String patientPulse;
    /**
     * 患者血压
     */
    @ApiModelProperty(value="患者血压")
    private String patientBloodPressure;
    /**
     * 患者 疼痛评分
     */
    @ApiModelProperty(value="患者 疼痛评分")
    private String patientPain;
    /**
     * 患者意识
     */
    @ApiModelProperty(value="患者意识")
    private String patientConsciousness;
    /**
     * 患者心理状态
     */
    @ApiModelProperty(value="患者心理状态")
    private String patientPsychological;
    /**
     * 患者心理状态备注
     */
    @ApiModelProperty(value="患者心理状态备注")
    private String patientPsychologicalRemarks;
    /**
     * 患者自理能力
     */
    @ApiModelProperty(value="患者自理能力")
    private String patientCareOneself;
    /**
     * 患者皮肤黏膜
     */
    @ApiModelProperty(value="患者皮肤黏膜")
    private String patientSkinMucosa;
    /**
     * 患者皮肤黏膜备注
     */
    @ApiModelProperty(value="患者皮肤黏膜备注")
    private String patientSkinMucosaRemarks;
    /**
     * 患者压疮
     */
    @ApiModelProperty(value="患者压疮")
    private String patientPressure;
    /**
     * 患者压疮备注
     */
    @ApiModelProperty(value="患者压疮备注")
    private String patientPressureRemarks;
    /**
     * 患者经脉输液
     */
    @ApiModelProperty(value="患者经脉输液")
    private String patientTransfusion;
    /**
     * 患者经脉输液备注
     */
    @ApiModelProperty(value="患者经脉输液备注")
    private String patientTransfusionRemarks;
    /**
     * 患者药物交接
     */
    @ApiModelProperty(value="患者药物交接")
    private String patientDrugDelivery;
    /**
     * 患者药物交接备注
     */
    @ApiModelProperty(value="患者药物交接备注")
    private String patientDrugDeliveryRemarks;
    /**
     * 患者的管道情况
     */
    @ApiModelProperty(value="患者的管道情况")
    private String patientPipeline;
    /**
     * 患者伤口
     */
    @ApiModelProperty(value="患者伤口")
    private String patientWound;
    /**
     * 患者伤口备注
     */
    @ApiModelProperty(value="患者伤口备注")
    private String patientWoundRemarks;
    /**
     * 其它
     */
    @ApiModelProperty(value="其它")
    private String patientOthers;
    /**
     * 转出科室
     */
    @ApiModelProperty(value="转出科室")
    private String patientDischargeDept;
    /**
     * 转出时间
     */
    @ApiModelProperty(value="转出时间")
    private LocalDateTime patientDischargeTime;
    /**
     * 填表人
     */
    @ApiModelProperty(value="填表人")
    private String nurseName;
    /**
     * 科室
     */
    @ApiModelProperty(value="科室")
    private String deptId;
    /**
     * 删除标识 0正常 1 删除
     */
    @ApiModelProperty(value="删除标识 0正常 1 删除")
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
