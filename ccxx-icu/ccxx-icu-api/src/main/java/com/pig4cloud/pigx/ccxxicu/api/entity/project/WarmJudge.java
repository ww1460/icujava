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

package com.pig4cloud.pigx.ccxxicu.api.entity.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 预警项目判断表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:20
 */
@Data
@TableName("nur_warm_judge")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "预警项目判断表")
public class WarmJudge extends Model<WarmJudge> {
private static final long serialVersionUID = 1L;

    /**
     * 预警项目判断表 id自增id
     */
    @TableId
    @ApiModelProperty(value="预警项目判断表 id自增id")
    private Integer id;
    /**
     * 预警项目判断表 id
     */
    @ApiModelProperty(value="预警项目判断表 id")
    private String warmJudgeId;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * 值域上界
     */
    @ApiModelProperty(value="值域上界")
    private Integer topValue;
    /**
     * 值域下界
     */
    @ApiModelProperty(value="值域下界")
    private Integer bottomValue;
    /**
     * 最大预警值
     */
    @ApiModelProperty(value="最大预警值")
    private Integer maxWarm;
    /**
     * 最小预警值
     */
    @ApiModelProperty(value="最小预警值")
    private Integer minWarm;
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
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private Integer delFlag;
    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
