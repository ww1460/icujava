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
 * icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
 *
 * @author pigx code generator
 * @date 2019-10-03 16:48:03
 */
@Data
@TableName("pat_icu_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数")
public class IcuRecord extends Model<IcuRecord> {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value="id")
    private Integer id;
    /**
     * 雪花id
     */
    @ApiModelProperty(value="雪花id")
    private String icuRecordId;
    /**
     * 新入科患者
     */
    @ApiModelProperty(value="新入科患者")
    private Integer newDeptPatient;
    /**
     * 在科患者人数
     */
    @ApiModelProperty(value="在科患者人数")
    private Integer deptPatient;
	/**
	 * 气管插管患者
	 */
	@ApiModelProperty(value = "气管插管患者")
    private Integer tracheaPatient;

    /**
     * 留置导尿管患者人数
     */
    @ApiModelProperty(value="留置导尿管患者人数")
    private Integer indwellingCatheterPatient;
    /**
     * 动静脉插管患者人数
     */
    @ApiModelProperty(value="动静脉插管患者人数")
    private Integer arteriovenouPatient;
    /**
     * 使用呼吸机患者人数
     */
    @ApiModelProperty(value="使用呼吸机患者人数")
    private Integer respiratorUsePatient;
    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    private LocalDateTime recordTime;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 删除标识  0正常   1删除
     */
    @ApiModelProperty(value="删除标识  0正常   1删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    }
