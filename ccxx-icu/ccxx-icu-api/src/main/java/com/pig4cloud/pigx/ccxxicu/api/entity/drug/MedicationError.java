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

package com.pig4cloud.pigx.ccxxicu.api.entity.drug;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用药错误登记
 *
 * @author pigx code generator
 * @date 2019-09-18 16:31:43
 */
@Data
@TableName("drug_medication_error")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用药错误登记")
public class MedicationError extends Model<MedicationError> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 用药错误登记 id
     */
    @ApiModelProperty(value="用药错误登记 id")
    private String medicationErrorId;
    /**
     * 发生时间
     */
    @ApiModelProperty(value="发生时间")
    private LocalDateTime occurrenceTime;
    /**
     * 发现时间
     */
    @ApiModelProperty(value="发现时间")
    private LocalDateTime discoveryTime;
    /**
     * 错误内容
     */
    @ApiModelProperty(value="错误内容")
    private String errorContent;
    /**
     * 错误内容备注
     */
    @ApiModelProperty(value="错误内容备注")
    private String errorContentRemarks;
    /**
     * 错误药品是否发给患者
     */
    @ApiModelProperty(value="错误药品是否发给患者")
    private String whetherGivePatient;
    /**
     * 患者是否使用了错误药品
     */
    @ApiModelProperty(value="患者是否使用了错误药品")
    private String whetherPatientUse;
    /**
     * 用药错误等级
     */
    @ApiModelProperty(value="用药错误等级")
    private String medicationErrorLevel;
    /**
     * 用药错误等级备注
     */
    @ApiModelProperty(value="用药错误等级备注")
    private String medicationErrorLevelRemarks;
    /**
     * 患者伤害情况
     */
    @ApiModelProperty(value="患者伤害情况")
    private String patientInjury;
    /**
     * 患者伤害情况备注
     */
    @ApiModelProperty(value="患者伤害情况备注")
    private String patientInjuryRemarks;

	/**
	 * 患者恢复过程
	 */
	@ApiModelProperty(value="患者恢复过程")
	private String patientRecover;
    /**
     * 引发错误的因素
     */
    @ApiModelProperty(value="引发错误的因素")
    private String causeOfTheError;
    /**
     * 引发错误的因素备注
     */
    @ApiModelProperty(value="引发错误的因素备注")
    private String causeOfTheErrorRemarks;
    /**
     * 发生错误的场所
     */
    @ApiModelProperty(value="发生错误的场所")
    private String occurrenceSites;
    /**
     * 发生错误的场所备注
     */
    @ApiModelProperty(value="发生错误的场所备注")
    private String occurrenceSitesRemark;
    /**
     * 引起错误的工作人员职位
     */
    @ApiModelProperty(value="引起错误的工作人员职位")
    private String staffPosition;
    /**
     * 引起错误的工作人员职位备注
     */
    @ApiModelProperty(value="引起错误的工作人员职位备注")
    private String staffPositionRemarks;
    /**
     * 其他与错误相关的工作人员
     */
    @ApiModelProperty(value="其他与错误相关的工作人员")
    private String relatedPersonPosition;
    /**
     * 其他与错误相关的工作人员备注
     */
    @ApiModelProperty(value="其他与错误相关的工作人员备注")
    private String relatedPersonPositionRemarks;
    /**
     * 发现错误人员
     */
    @ApiModelProperty(value="发现错误人员")
    private String findErrorPerson;
    /**
     * 发现错误人员备注
     */
    @ApiModelProperty(value="发现错误人员备注")
    private String findErrorPersonRemarks;
    /**
     * 错误是如何发现或避免的
     */
    @ApiModelProperty(value="错误是如何发现或避免的")
    private String howToFind;
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
     * 患者的诊断
     */
    @ApiModelProperty(value="患者的诊断")
    private String patientIllness;
    /**
     * 患者的联系方式
     */
    @ApiModelProperty(value="患者的联系方式")
    private String patientPhone;
    /**
     * 药品通用名
     */
    @ApiModelProperty(value="药品通用名")
    private String drugCommonName;
    /**
     * 药品商品名
     */
    @ApiModelProperty(value="药品商品名")
    private String drugTradeName;
    /**
     * 药品生产厂家
     */
    @ApiModelProperty(value="药品生产厂家")
    private String drugManufacturers;
    /**
     * 药品类别
     */
    @ApiModelProperty(value="药品类别")
    private String drugType;
    /**
     * 药品剂型
     */
    @ApiModelProperty(value="药品剂型")
    private String drugDosageForms;
    /**
     * 药品规格
     */
    @ApiModelProperty(value="药品规格")
    private String drugSpecifications;
    /**
     * 药品包装类型
     */
    @ApiModelProperty(value="药品包装类型")
    private String drugPackaging;
    /**
     * 药品的用法用量
     */
    @ApiModelProperty(value="药品的用法用量")
    private String drugUsageDosage;


	/**
	 * 药品的疗程
	 */
	@ApiModelProperty(value="药品的疗程")
	private String drugTreatment;
    /**
     * 药品的服药频次
     */
    @ApiModelProperty(value="药品的服药频次")
    private String drugFrequency;
    /**
     * 是否能够提供药品标签、处方复印件等资料
     */
    @ApiModelProperty(value="是否能够提供药品标签、处方复印件等资料")
    private String whetherBringsData;

	/**
	 * 是否能够提供药品标签、处方复印件等资料备注
	 */
	@ApiModelProperty(value="是否能够提供药品标签、处方复印件等资料备注")
	private String whetherBringsDataRemarks;


    /**
     * 错误发生的经过
     */
    @ApiModelProperty(value="错误发生的经过")
    private String processOccurs;
    /**
     * 对预防类似错误发生的建议
     */
    @ApiModelProperty(value="对预防类似错误发生的建议")
    private String preventionAdvice;
    /**
     * 填表人
     */
    @ApiModelProperty(value="填表人")
    private String preparerName;
    /**
     * 填表人联系方式
     */
    @ApiModelProperty(value="填表人联系方式")
    private String preparerPhone;
    /**
     * 填表人e-mail
     */
    @ApiModelProperty(value="填表人e-mail")
    private String preparerEmail;
    /**
     * 填表人地址
     */
    @ApiModelProperty(value="填表人地址")
    private String preparerAddress;
    /**
     * 创建当前数据的时间 
     */
    @ApiModelProperty(value="创建当前数据的时间 ")
    private LocalDateTime createTime;
    /**
     * 科室 id
     */
    @ApiModelProperty(value="科室 id")
    private String deptId;
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
