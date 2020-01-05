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

package com.pig4cloud.pigx.ccxxicu.api.entity.assess;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 评估项目条件表 
 *
 * @author pigx code generator
 * @date 2019-08-27 11:26:43
 */
@Data
@TableName("nur_assess_condition")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "评估项目条件表 ")
public class AssessCondition extends Model<AssessCondition> {
private static final long serialVersionUID = 1L;

    /**
     * 评估项目条件表 id
     */
    @TableId
    @ApiModelProperty(value="评估项目条件表 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String assessConditionId;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String assessProjectId;
    /**
     * 项目评估条件
     */
    @ApiModelProperty(value="项目评估条件")
    private String assessCondition;
    /**
     * 评估分数
     */
    @ApiModelProperty(value="评估分数")
    private Integer assessScore;
    /**
     * 评估结果
     */
    @ApiModelProperty(value="评估结果")
    private String assessResult;
    /**
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
    private Integer delFlag;
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
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
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
