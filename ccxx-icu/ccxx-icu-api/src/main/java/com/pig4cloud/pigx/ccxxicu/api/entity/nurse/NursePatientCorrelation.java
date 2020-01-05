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

/**
 * 患者和护士关联表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:08:26
 */
@Data
@TableName("nur_nurse_patient_correlation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者和护士关联表")
public class NursePatientCorrelation extends Model<NursePatientCorrelation> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;

	/***
	 * 当前护士是否为唯一责任护士  0是   1否
	 */
	@ApiModelProperty(value = "当前护士是否为唯一责任护士  0是   1否")
	private Integer onlyDutyNurse;




    }
