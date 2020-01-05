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

package com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 病情护理病情护理建议表
 *
 * @author pigx code generator
 * @date 2019-09-09 11:21:27
 */
@Data
@TableName("nur_illness_advice")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "病情护理病情护理建议表")
public class IllnessAdvice extends Model<IllnessAdvice> {
private static final long serialVersionUID = 1L;

    /**
     * 病情护理病情护理建议  自增id
     */
    @TableId
    @ApiModelProperty(value="病情护理病情护理建议  自增id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String illnessAdviceId;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String illnessProjectId;

	/**
	 * 项目状态 0正常  1异常
	 */
	@ApiModelProperty(value="项目状态 0正常  1异常")
	private Integer illnessState;

    /**
     * 护理建议
     */
    @ApiModelProperty(value="护理建议")
    private String nursingAdvice;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;

    /**
     * 删除标识 0正常  1删除
     */
    @ApiModelProperty(value="删除标识 0正常  1删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除人id
     */
    @ApiModelProperty(value="删除人id")
    private String delUserId;
    /**
     * 创建人id
     */
    @ApiModelProperty(value="创建人id")
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
     * 修改人id
     */
    @ApiModelProperty(value="修改人id")
    private String updateUserId;
    }
