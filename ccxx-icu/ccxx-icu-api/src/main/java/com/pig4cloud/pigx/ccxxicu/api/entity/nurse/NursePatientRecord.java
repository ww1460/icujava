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
 * 护士看护患者记录表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:12:54
 */
@Data
@TableName("nur_nurse_patient_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士看护患者记录表")
public class NursePatientRecord extends Model<NursePatientRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 护士看护患者记录表
     */
    @TableId
    @ApiModelProperty(value="护士看护患者记录表")
    private Integer id;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endTime;
    /**
     * 总看护时间
     */
    @ApiModelProperty(value="总看护时间")
    private String totalTime;
    /**
     * 创建人（创建关联关系的人）
     */
    @ApiModelProperty(value="创建人（创建关联关系的人）")
    private String founder;

	/***
	 * 当前护士是否为唯一责任护士  0是   1否
	 */
	@ApiModelProperty(value = "当前护士是否为唯一责任护士  0是   1否")
	private Integer onlyDutyNurse;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;

    /**
     * 删除定义  0 是正常  1是删除
     */
    @ApiModelProperty(value="删除定义  0 是正常  1是删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除当条数据的护士
     */
    @ApiModelProperty(value="删除当条数据的护士")
    private String delUserId;
    }
