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

package com.pig4cloud.pigx.ccxxicu.api.entity.assess;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 入科评估记录表
 *
 * @author pigx code generator
 * @date 2019-09-05 13:44:01
 */
@Data
@TableName("nur_entrance_assess_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "入科评估记录表")
public class EntranceAssessRecord extends Model<EntranceAssessRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 入科评估结果记录 id
     */
    @TableId
    @ApiModelProperty(value="入科评估结果记录 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String entranceAssessRecordId;
    /**
     * 入科时间
     */
    @ApiModelProperty(value="入科时间")
    private LocalDateTime entranceTime;
    /**
     * 入科诊断
     */
    @ApiModelProperty(value="入科诊断")
    private String entranceDiagnose;
    /**
     * 入科途径
     */
    @ApiModelProperty(value="入科途径")
    private String entrancePathway;
    /**
     * 患者联系电话
     */
    @ApiModelProperty(value="患者联系电话")
    private String patientPhone;
    /**
     * 患者地址
     */
    @ApiModelProperty(value="患者地址")
    private String patientAddress;
    /**
     * 资料来源
     */
    @ApiModelProperty(value="资料来源")
    private String dataSource;
    /**
     * 资料来源备注
     */
    @ApiModelProperty(value="资料来源备注")
    private String dataSourceRemarks;
    /**
     * 既往史
     */
    @ApiModelProperty(value="既往史")
    private String previousHistory;
    /**
     * 既往史备注
     */
    @ApiModelProperty(value="既往史备注")
    private String previousHistoryRemarks;
    /**
     * 家族史
     */
    @ApiModelProperty(value="家族史")
    private String familyHistory;
    /**
     * 家族史备注
     */
    @ApiModelProperty(value="家族史备注")
    private String familyHistoryRemarks;
    /**
     * 过敏药物
     */
    @ApiModelProperty(value="过敏药物")
    private String drugAllergy;
    /**
     * 过敏药物备注
     */
    @ApiModelProperty(value="过敏药物备注")
    private String drugAllergyRemarks;
    /**
     * 过敏食物
     */
    @ApiModelProperty(value="过敏食物")
    private String allergenicFood;
    /**
     * 过敏食物备注
     */
    @ApiModelProperty(value="过敏食物备注")
    private String allergenicFoodRemarks;
    /**
     * 其它过敏源
     */
    @ApiModelProperty(value="其它过敏源")
    private String otherAllergens;
    /**
     * 其它过敏源备注
     */
    @ApiModelProperty(value="其它过敏源备注")
    private String otherAllergensRemarks;
    /**
     * 皮肤情况
     */
    @ApiModelProperty(value="皮肤情况")
    private String skinCase;
    /**
     * 皮肤情况备注
     */
    @ApiModelProperty(value="皮肤情况备注")
    private String skinCaseRemarks;
    /**
     * 沟通是否有障碍
     */
    @ApiModelProperty(value="沟通是否有障碍")
    private String communicationBarriers;
    /**
     * 沟通是否有障碍备注
     */
    @ApiModelProperty(value="沟通是否有障碍备注")
    private String communicationBarriersRemarks;
    /**
     * 治疗依从性
     */
    @ApiModelProperty(value="治疗依从性")
    private String treatmentCompliance;
    /**
     * 患者民族
     */
    @ApiModelProperty(value="患者民族")
    private String patientNation;
    /**
     * 患者职业
     */
    @ApiModelProperty(value="患者职业")
    private String patientProfession;
    /**
     * 患者文化程度
     */
    @ApiModelProperty(value="患者文化程度")
    private String patientCulture;
    /**
     * 患者婚姻
     */
    @ApiModelProperty(value="患者婚姻")
    private String patientMarriage;
    /**
     * 患者居住状态
     */
    @ApiModelProperty(value="患者居住状态")
    private String patientDwellingState;
    /**
     * 患者费用来源
     */
    @ApiModelProperty(value="患者费用来源")
    private String patientCostSource;
    /**
     * 患者经济来源
     */
    @ApiModelProperty(value="患者经济来源")
    private String patientEconomySource;
    /**
     * 患者宗教信仰
     */
    @ApiModelProperty(value="患者宗教信仰")
    private String patientReligionFaith;
    /**
     * 患者宗教需求
     */
    @ApiModelProperty(value="患者宗教需求")
    private String patientReligionDemand;
    /**
     * 患者宗教需求备注
     */
    @ApiModelProperty(value="患者宗教需求备注")
    private String patientReligionDemandRemarks;
    /**
     * 患者抽烟情况
     */
    @ApiModelProperty(value="患者抽烟情况")
    private String patientSmoke;
    /**
     * 患者抽烟情况备注
     */
    @ApiModelProperty(value="患者抽烟情况备注")
    private String patientSmokeRemarks;
    /**
     * 患者喝酒情况
     */
    @ApiModelProperty(value="患者喝酒情况")
    private String patientDrink;
    /**
     * 患者喝酒情况备注
     */
    @ApiModelProperty(value="患者喝酒情况备注")
    private String patientDrinkRemarks;
    /**
     * 特殊人群
     */
    @ApiModelProperty(value="特殊人群")
    private String specialCrowd;
    /**
     * 特殊人群备注
     */
    @ApiModelProperty(value="特殊人群备注")
    private String specialCrowdRemarks;
    /**
     * 患者社会支持
     */
    @ApiModelProperty(value="患者社会支持")
    private String socialSupport;
    /**
     * 患者社会支持备注
     */
    @ApiModelProperty(value="患者社会支持备注")
    private String socialSupportRemarks;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
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
