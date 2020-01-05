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
 * 患者his传输表
 *
 * @author pigx code generator
 * @date 2019-08-27 15:58:29
 */
@Data
@TableName("his_patient")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者his传输表")
public class HisPatient extends Model<HisPatient> {
private static final long serialVersionUID = 1L;

    /**
     * 患者中间表
     */
    @TableId
    @ApiModelProperty(value="患者中间表")
    private Integer id;
	/**
	 * 患者表id
	 */
	@ApiModelProperty(value="患者表id")
	private String patientId;

    /**
     * 患者his 【his中的患者唯一标识 用它来处理患者所有逻辑】
     */
    @ApiModelProperty(value="患者his 【his中的患者唯一标识 用它来处理患者所有逻辑】")
    private String hisPatientId;
    /**
     * 患者姓名
     */
    @ApiModelProperty(value="患者姓名")
    private String name;
    /**
     * 患者性别  1男 0女
     */
    @ApiModelProperty(value="患者性别  1男 0女")
    private String gender;


    /**
     * 患者出生日期
     */
    @ApiModelProperty(value="患者出生日期")
    private LocalDateTime birthday;
    /**
     * 患者血型
     */
    @ApiModelProperty(value="患者血型")
    private String blood;
    /**
     * 国籍
     */
    @ApiModelProperty(value="国籍")
    private String nationality;
    /**
     * 民族
     */
    @ApiModelProperty(value="民族")
    private String nation;
    /**
     * 身份证件类型
     */
    @ApiModelProperty(value="身份证件类型")
    private String idType;
    /**
     * 患者身份证件号
     */
    @ApiModelProperty(value="患者身份证件号")
    private String idNumber;
    /**
     * 患者联系电话
     */
    @ApiModelProperty(value="患者联系电话")
    private String phone;
    /**
     * 婚姻状况
     */
    @ApiModelProperty(value="婚姻状况")
    private String marriage;
    /**
     * 患者职业
     */
    @ApiModelProperty(value="患者职业")
    private String career;
    /**
     * 患者年龄
     */
    @ApiModelProperty(value="患者年龄")
    private String age;
    /**
     * 患者月龄
     */
    @ApiModelProperty(value="患者月龄")
    private Integer monthAge;
    /**
     * 新生儿出生体重
     */
    @ApiModelProperty(value="新生儿出生体重")
    private String birthWeight;
    /**
     * 新生儿入院体重
     */
    @ApiModelProperty(value="新生儿入院体重")
    private String admissionWeight;
    /**
     * 患者病历号
     */
    @ApiModelProperty(value="患者病历号")
    private String medicalRecords;
    /**
     * 患者门诊号
     */
    @ApiModelProperty(value="患者门诊号")
    private String outpatient;
    /**
     * 患者住院号
     */
    @ApiModelProperty(value="患者住院号")
    private String hospitalnumber;
    /**
     * 患者健康卡号 
     */
    @ApiModelProperty(value="患者健康卡号 ")
    private String ohip;
    /**
     * 患者就诊卡号
     */
    @ApiModelProperty(value="患者就诊卡号")
    private String seedoctor;
    /**
     * 患者现住省份
     */
    @ApiModelProperty(value="患者现住省份")
    private String currentAddressProvince;
    /**
     * 患者现居住城市
     */
    @ApiModelProperty(value="患者现居住城市")
    private String currentAddressCity;
    /**
     * 患者现居住地址区县
     */
    @ApiModelProperty(value="患者现居住地址区县")
    private String currentAddressCounty;
    /**
     * 患者现住详细地址
     */
    @ApiModelProperty(value="患者现住详细地址")
    private String currentAddressDetails;
    /**
     * 患者其他联系人
     */
    @ApiModelProperty(value="患者其他联系人")
    private String otherContacts;
    /**
     * 联系人与患者关系
     */
    @ApiModelProperty(value="联系人与患者关系")
    private String relation;
    /**
     * 联系人所在详情地址
     */
    @ApiModelProperty(value="联系人所在详情地址")
    private String contactAddress;
    /**
     * 联系人联系电话
     */
    @ApiModelProperty(value="联系人联系电话")
    private String contactPhone;
    /**
     * 患者入科途径
     */
    @ApiModelProperty(value="患者入科途径")
    private String admissionPath;
    /**
     * 转科（来源）科室
     */
    @ApiModelProperty(value="转科（来源）科室")
    private String transferDepartments;
	/**
	 * 患者入科科室
	 */
	@ApiModelProperty(value="患者入科科室")
	private String admissionDept;
	/**
	 * 患者入科时间
	 */
	@ApiModelProperty(value="患者入科时间")
	private LocalDateTime admissionTime;

	/**
	 * 患者出科科室
	 */
	@ApiModelProperty(value="患者出科科室")
	private String dischargeDept;
	/**
	 * 出科时间
	 */
	@ApiModelProperty(value="出科时间")
	private LocalDateTime dischargeTime;

	/**
	 * 转科内容【转科描述】
	 */
	@ApiModelProperty(value="转科内容【转科描述】")
	private String turndepartmentDescribe;

    /**
     * 入院时间
     */
    @ApiModelProperty(value="入院时间")
    private LocalDateTime timeOfAdmission;
    /**
     * 患者住院次数
     */
    @ApiModelProperty(value="患者住院次数")
    private Integer frequency;
    /**
     * 患者过敏史
     */
    @ApiModelProperty(value="患者过敏史")
    private String allergichi;

    /**
     * 患者病情描述
     */
    @ApiModelProperty(value="患者病情描述")
    private String diagnosis;
    /**
     * 评估医生姓名
     */
    @ApiModelProperty(value="评估医生姓名")
    private String fadviceUserName;
    /**
     * 评估时间
     */
    @ApiModelProperty(value="评估时间")
    private LocalDateTime fadviceTime;
    /**
     * 逻辑删除  0删除 1正常
     */
    @ApiModelProperty(value="逻辑删除  0删除 1正常")
    private Integer delFlag;
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
     * 修改该条数据的用户
     */
    @ApiModelProperty(value="修改该条数据的用户")
    private String updateUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

	/**
	 * 转到科室的id
	 */
	@ApiModelProperty(value="转到科室的id")
	private String deptId;
	/**
	 /**
	 * 转到科室的编码（当前患者要转去什么科室）
	 */
	@ApiModelProperty(value="转到科室的编码（当前患者要转去什么科室）")
	private String deptNumber;

	/**
	 /**
	 * 转来科室id
	 */
	@ApiModelProperty(value="转来科室id")
	private String sourceDeptId;
	/**
	 * 出科类型（1转科，2出院，3死亡）
	 */
	@ApiModelProperty(value="出科类型（1转科，2出院，3死亡）")
	private String dischargeType;
	/**
	 /**
	 * 转来科室编码
	 */
	@ApiModelProperty(value="转来科室编码")
	private String sourceDeptNumber;
	/**

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
    /**
     * 患者入科状态   0为待入科 ，1入科中。
     */
    @ApiModelProperty(value="患者入科状态   0为待入科 ，1为入科中")
    private Integer entryState;
    }
