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

/**
 * 患者与床位关联表
 *
 * @author pigx code generator
 * @date 2019-08-08 14:09:53
 */
@Data
@TableName("pat_patient_bed_correlation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者与床位关联表")
public class PatientBedCorrelation extends Model<PatientBedCorrelation> {
	private static final long serialVersionUID = 1L;

	/**
	 * 患者与床位关联表  id
	 */
	@TableId
	@ApiModelProperty(value = "患者与床位关联表  id")
	private Integer id;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value = "患者id")
	private String patientId;
	/**
	 * 床位id
	 */
	@ApiModelProperty(value = "床位id")
	private String bedId;
	/**
	 * 床位rfid
	 */
	@ApiModelProperty(value = "床位rfid")
	private String bed_rfid;
}
