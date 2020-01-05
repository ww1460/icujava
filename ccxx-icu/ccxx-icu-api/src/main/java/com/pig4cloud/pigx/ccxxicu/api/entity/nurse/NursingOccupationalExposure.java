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

package com.pig4cloud.pigx.ccxxicu.api.entity.nurse;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 护理职业暴露记录
 *
 * @author pigx code generator
 * @date 2019-08-30 16:38:09
 */
@Data
@TableName("nur_nursing_occupational_exposure")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护理职业暴露记录")
public class NursingOccupationalExposure extends Model<NursingOccupationalExposure> {
private static final long serialVersionUID = 1L;

    /**
     * 评估结果记录 id
     */
    @TableId
    @ApiModelProperty(value="评估结果记录 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String occupationalExposureId;


	/**
	 * 护士id
	 */
	@ApiModelProperty(value="护士id")
	private String nurseId;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String nurseName;
    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
    private String nurseSex;
    /**
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    private String nurseAge;
    /**
     * 职业
     */
    @ApiModelProperty(value="职业")
    private String nurseProfession;
    /**
     * 科室
     */
    @ApiModelProperty(value="科室")
    private String nurseDept;
    /**
     * 发生时间
     */
    @ApiModelProperty(value="发生时间")
    private LocalDateTime occurrenceTime;
    /**
     * 发生地点
     */
    @ApiModelProperty(value="发生地点")
    private String occurrenceSite;
    /**
     * 发生经过
     */
    @ApiModelProperty(value="发生经过")
    private String occurrenceProcess;
    /**
     * 发生原因分析
     */
    @ApiModelProperty(value="发生原因分析")
    private String occurrenceReason;
    /**
     * 个人防护情况
     */
    @ApiModelProperty(value="个人防护情况")
    private String personalProtection;
    /**
     * 接触暴露之皮肤
     */
    @ApiModelProperty(value="接触暴露之皮肤")
    private String exposureSkin;
    /**
     * 接触暴露之黏膜
     */
    @ApiModelProperty(value="接触暴露之黏膜")
    private String exposureMucosa;
    /**
     * 接触部位
     */
    @ApiModelProperty(value="接触部位")
    private String exposurePart;
    /**
     * 接触面积
     */
    @ApiModelProperty(value="接触面积")
    private String exposureArea;
    /**
     * 污染物来源
     */
    @ApiModelProperty(value="污染物来源")
    private String contaminantSource;
    /**
     * 何种器械
     */
    @ApiModelProperty(value="何种器械")
    private String applianceKind;
    /**
     * 器械型号：
     */
    @ApiModelProperty(value="器械型号：")
    private String applianceModelNumber;
    /**
     * 损伤程度及其危险度
     */
    @ApiModelProperty(value="损伤程度及其危险度")
    private String injuryDegreeAndRisk;
    /**
     * 致伤方式
     */
    @ApiModelProperty(value="致伤方式")
    private String injuryWay;
    /**
     * 有无破损或出血
     */
    @ApiModelProperty(value="有无破损或出血")
    private String breakageAndBleeding;

	/**
	 * 器械污染物来源
	 */
	@ApiModelProperty(value="器械污染物来源")
	private String applianceContaminantSource;

    /**
     * 实验室标本之血液
     */
    @ApiModelProperty(value="实验室标本之血液")
    private String labBlood;
    /**
     * 实验室标本之何种体液
     */
    @ApiModelProperty(value="实验室标本之何种体液")
    private String labBodyFluidKind;
    /**
     * 实验室标本之其他
     */
    @ApiModelProperty(value="实验室标本之其他")
    private String labOther;
    /**
     * 实验室标本之病毒含量
     */
    @ApiModelProperty(value="实验室标本之病毒含量")
    private String labVirusContent;
    /**
     * 实验室标本之其它情况
     */
    @ApiModelProperty(value="实验室标本之其它情况")
    private String labOtherCondition;
    /**
     * 来源患者姓名
     */
    @ApiModelProperty(value="来源患者姓名")
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
     * 确诊时间
     */
    @ApiModelProperty(value="确诊时间")
    private LocalDateTime patientConfirmedTime;
    /**
     * 患者病情
     */
    @ApiModelProperty(value="患者病情")
    private String patientIllness;
    /**
     * 病毒载量
     */
    @ApiModelProperty(value="病毒载量")
    private String viralLoad;
    /**
     * CD4细胞计数
     */
    @ApiModelProperty(value="CD4细胞计数")
    private String cellCounting;
	/**
	 * 填表人
	 */
	@ApiModelProperty(value="填表人")
	private String preparerName;
    /**
     * 联系电话（手机）
     */
    @ApiModelProperty(value="联系电话（手机）")
    private String preparerPhone;
    /**
     * 填表时间
     */
    @ApiModelProperty(value="填表时间")
    private LocalDateTime preparerTime;
    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private LocalDateTime reviewTime;
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
