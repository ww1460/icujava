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

package com.pig4cloud.pigx.ccxxicu.api.entity.nurse;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 护士健康回答
 *
 * @author pigx code generator
 * @date 2019-08-05 15:20:12
 */
@Data
@TableName("nur_health_answer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士健康回答")
public class HealthAnswer extends Model<HealthAnswer> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 护士健康回答
     */
    @ApiModelProperty(value="护士健康回答")
    private String healthAnswerId;
    /**
     * 答题护士id
     */
    @ApiModelProperty(value="答题护士id")
    private String nurseId;
    /**
     * 评估分数(护士回答题的答案)
     */
    @ApiModelProperty(value="评估分数(护士回答题的答案)")
    private Integer indicatorValue;
    /**
     * 评估时间
     */
    @ApiModelProperty(value="评估时间")
    private LocalDateTime assessTime;
    /**
     * 评估题目 id
     */
    @ApiModelProperty(value="评估题目 id")
    private String healthSubjectId;
    /**
     * 护士长id
     */
    @ApiModelProperty(value="护士长id")
    private String matronId;
    /**
     * 护士长建议
     */
    @ApiModelProperty(value="护士长建议")
    private String indicatorValueOfMatron;
    /**
     * 护士长评估时间
     */
    @ApiModelProperty(value="护士长评估时间")
    private LocalDateTime assessTimeOfMatorn;
    /**
     * 科室表id
     */
    @ApiModelProperty(value="科室表id")
    private String deptId;
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
