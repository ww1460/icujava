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
 * 医嘱编码【用于储存医嘱的内容】
 *
 * @author pigx code generator
 * @date 2019-10-07 16:01:32
 */
@Data
@TableName("his_doctors_advice_code")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "医嘱编码【用于储存医嘱的内容】")
public class DoctorsAdviceCode extends Model<DoctorsAdviceCode> {
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
    private String doctorsAdviceCodeId;
    /**
     * 医嘱内容
     */
    @ApiModelProperty(value="医嘱内容")
    private String doctorsAdviceContent;

	/**
	 * 医嘱简称
	 */
	@ApiModelProperty(value="医嘱简称")
	private String shortName;

    /**
     * 医院编号
     */
    @ApiModelProperty(value="医院编号")
    private String hospitalCode;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;
    /**
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private Integer delFlag;
    /**
     * 删除成功
     */
    @ApiModelProperty(value="删除成功")
    private LocalDateTime delTime;
    /**
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建护士id
     */
    @ApiModelProperty(value="创建护士id")
    private String createUserId;
    }
