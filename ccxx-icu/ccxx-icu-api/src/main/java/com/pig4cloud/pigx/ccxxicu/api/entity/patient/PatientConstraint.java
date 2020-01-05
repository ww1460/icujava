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
 * 患者约束记录
 *
 * @author pigx code generator
 * @date 2019-08-29 11:14:08
 */
@Data
@TableName("pat_patient_constraint")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者约束记录")
public class PatientConstraint extends Model<PatientConstraint> {
private static final long serialVersionUID = 1L;

    /**
     * 患者约束记录 id
     */
    @TableId
    @ApiModelProperty(value="患者约束记录 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String patientConstraintId;
    /**
     * 约束原因
     */
    @ApiModelProperty(value="约束原因")
    private String constraintCause;
    /**
     * 约束时间
     */
    @ApiModelProperty(value="约束时间")
    private String constraintTime;
    /**
     * 约束带数目
     */
    @ApiModelProperty(value="约束带数目")
    private String constraintNumber;
    /**
     * 约束部位情况描述
     */
    @ApiModelProperty(value="约束部位情况描述")
    private String constraintPartDescribe;
    /**
     * 患者反应
     */
    @ApiModelProperty(value="患者反应")
    private String patientResponse;
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
	 * 是否知情
	 */
	@ApiModelProperty(value="是否知情")
	private String ifKnowInform;
    /**
     * 患者并发症
     */
    @ApiModelProperty(value="患者并发症")
    private String patientComplication;
    /**
     * 并发症的处理
     */
    @ApiModelProperty(value="并发症的处理")
    private String complicationManage;
    /**
     * 执行护士
     */
    @ApiModelProperty(value="执行护士")
    private String executeNurse;
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
