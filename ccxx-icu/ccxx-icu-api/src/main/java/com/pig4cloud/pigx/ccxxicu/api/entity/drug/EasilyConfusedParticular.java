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

package com.pig4cloud.pigx.ccxxicu.api.entity.drug;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 易混淆详情
 *
 * @author pigx code generator
 * @date 2019-09-19 10:41:31
 */
@Data
@TableName("drug_easily_confused_particular")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "易混淆详情")
public class EasilyConfusedParticular extends Model<EasilyConfusedParticular> {
private static final long serialVersionUID = 1L;

    /**
     * 易混淆详情
     */
    @TableId
    @ApiModelProperty(value="易混淆详情")
    private Integer id;
    /**
     * 易混淆详情 id
     */
    @ApiModelProperty(value="易混淆详情 id")
    private String easilyConfusedParticularId;
    /**
     * 药品易混淆目录 id
     */
    @ApiModelProperty(value="药品易混淆目录 id")
    private String easilyConfusedCatalogId;
    /**
     * 药品名称
     */
    @ApiModelProperty(value="药品名称")
    private String drugName;

	/**
	 * 药品图片
	 */
	@ApiModelProperty(value="药品图片")
	private String drugPicture;
    /**
     * 药品规格
     */
    @ApiModelProperty(value="药品规格")
    private String drugSpecification;
    /**
     * 药品类别
     */
    @ApiModelProperty(value="药品类别")
    private String drugType;
    /**
     * 药品适应症
     */
    @ApiModelProperty(value="药品适应症")
    private String drugIndications;
    /**
     * 药理作用
     */
    @ApiModelProperty(value="药理作用")
    private String pharmacologicalAction;
    /**
     * 药品不良反应
     */
    @ApiModelProperty(value="药品不良反应")
    private String adverseReaction;
    /**
     * 药品分级
     */
    @ApiModelProperty(value="药品分级")
    private String drugTaboo;
    /**
     * 特殊说明
     */
    @ApiModelProperty(value="特殊说明")
    private String remarks;
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
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改该条数据的用户
     */
    @ApiModelProperty(value="修改该条数据的用户")
    private String updateUserId;
    }
