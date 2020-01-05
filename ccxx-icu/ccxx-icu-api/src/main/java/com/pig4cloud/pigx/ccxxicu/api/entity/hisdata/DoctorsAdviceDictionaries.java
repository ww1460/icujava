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
 *  医嘱项目字典表，对医嘱中的一些项目进行描述翻译
 *
 * @author pigx code generator
 * @date 2019-08-28 10:38:59
 */
@Data
@TableName("his_doctors_advice_dictionaries")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = " 医嘱项目字典表，对医嘱中的一些项目进行描述翻译")
public class DoctorsAdviceDictionaries extends Model<DoctorsAdviceDictionaries> {
private static final long serialVersionUID = 1L;

    /**
     *  医嘱项目字典表
     */
    @TableId
    @ApiModelProperty(value=" 医嘱项目字典表")
    private Integer id;
    /**
     * 雪花id
     */
    @ApiModelProperty(value="雪花id")
    private String doctorsAdviceDictionariesId;
    /**
     * 医嘱项目类型名称（字典表中）
     */
    @ApiModelProperty(value="医嘱项目类型名称（字典表中）")
    private String doctorsAdviceType;
    /**
     * 医嘱项目类型id（字典表中）
     */
    @ApiModelProperty(value="医嘱项目类型id（字典表中）")
    private Integer doctorsAdviceTypeId;
    /**
     * 数据值
     */
    @ApiModelProperty(value="数据值")
    private String dataValue;
    /**
     * 标签名
     */
    @ApiModelProperty(value="标签名")
    private String labelName;
    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String labelDescribe;
    /**
     * 备注信息
     */
    @ApiModelProperty(value="备注信息")
    private String remarksInformation;

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


    }
