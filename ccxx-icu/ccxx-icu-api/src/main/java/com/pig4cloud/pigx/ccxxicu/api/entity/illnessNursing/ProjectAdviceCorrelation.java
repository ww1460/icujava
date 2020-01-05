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
 * 项目状态和建议的关联表
 *
 * @author pigx code generator
 * @date 2019-09-09 14:38:57
 */
@Data
@TableName("nur_project_advice_correlation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目状态和建议的关联表")
public class ProjectAdviceCorrelation extends Model<ProjectAdviceCorrelation> {
private static final long serialVersionUID = 1L;

    /**
     * 项目状态和建议的关联表 id
     */
    @TableId
    @ApiModelProperty(value="项目状态和建议的关联表 id")
    private Integer id;
    /**
     * 项目状态和建议的关联表
     */
    @ApiModelProperty(value="项目状态和建议的关联表")
    private String projectAdviceCorrelationId;
    /**
     * 项目状态id
     */
    @ApiModelProperty(value="项目状态id")
    private String projectStateId;
    /**
     * 护理建议id
     */
    @ApiModelProperty(value="护理建议id")
    private String nursingAdviceId;
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
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 删除标识  0 正常 1删除
     */
    @ApiModelProperty(value="删除标识  0 正常 1删除")
    private Integer delFlag;
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
