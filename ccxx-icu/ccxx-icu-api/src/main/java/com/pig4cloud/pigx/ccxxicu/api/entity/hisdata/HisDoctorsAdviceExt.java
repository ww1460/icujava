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
 * 医嘱扩展表
 *
 * @author pigx code generator
 * @date 2019-10-14 19:33:39
 */
@Data
@TableName("his_doctors_advice_ext")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "医嘱扩展表")
public class HisDoctorsAdviceExt extends Model<HisDoctorsAdviceExt> {
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
    private String doctorsAdviceExtId;
    /**
     * His执行情况id
     */
    @ApiModelProperty(value="His执行情况id")
    private String hisFZYExecInfoId;
    /**
     * 关联医嘱id
     */
    @ApiModelProperty(value="")
    private String hisDoctorsAdviceId;
	/**
	 * 医嘱id【医嘱内容】
	 */
	@ApiModelProperty(value="")
	private String hisDoctorsAdviceProjectId;

    /**
     * 预执行时间
     */
    @ApiModelProperty(value="预执行时间")
    private LocalDateTime preExecuteTime;
    /**
     * His患者id（住院登记号）
     */
    @ApiModelProperty(value="His患者id（住院登记号）")
    private String hisPatientId;

    /**
     * 科室
     */
    @ApiModelProperty(value="科室")
    private String hisDeptId;
    /**
     * 剂量文本【用量 如多少毫升】
     */
    @ApiModelProperty(value="剂量文本【用量 如多少毫升】")
    private Integer dosage;
	/**
	 * 单位
	 */
	@ApiModelProperty(value="单位")
	private String company;
    /**
     * 滴速
     */
    @ApiModelProperty(value="滴速")
    private String dropSpeed;
    /**
     * 滴数
     */
    @ApiModelProperty(value="滴数")
    private String dropNum;
    /**
     * 单次执行剂量
     */
    @ApiModelProperty(value="单次执行剂量")
    private String executeDosage;
	/**
	 * 单次执行剂量单位
	 */
	@ApiModelProperty(value="单次执行剂量单位")
	private String executeCompany;

	/**
     * 执行情况
     */
    @ApiModelProperty(value="执行情况 ")
    private String executeSituation;
    /**
     * 执行状态[4完成]
     */
    @ApiModelProperty(value="执行状态[4完成]")
    private Integer executeType;
    /**
     * 执行人
     */
    @ApiModelProperty(value="执行人")
    private String executeNurse;
	/**
	 * HIs执行记录创建时间
	 */
	@ApiModelProperty(value="HIs执行记录创建时间")
	private LocalDateTime hisFZYExecInfoCreateTime;
    /**
     * 执行时间
     */
    @ApiModelProperty(value="执行时间")
    private LocalDateTime executeTime;
    /**
     * 逻辑删除  0删除 1正常
     */
    @ApiModelProperty(value="逻辑删除  0删除 1正常")
    private Integer delFlag;
    /**
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    }
