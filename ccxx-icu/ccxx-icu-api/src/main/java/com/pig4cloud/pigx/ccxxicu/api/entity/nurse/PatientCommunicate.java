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
 * 护士与患者沟通
 *
 * @author pigx code generator
 * @date 2019-09-03 20:31:37
 */
@Data
@TableName("nur_patient_communicate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士与患者沟通")
public class PatientCommunicate extends Model<PatientCommunicate> {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value="id")
    private Integer id;
    /**
     * 问题,标题
     */
    @ApiModelProperty(value="问题,标题")
    private String title;
    /**
     * 当前标题对应的图片
     */
    @ApiModelProperty(value="当前标题对应的图片")
    private String titlePicture;
    /**
     * 回答
     */
    @ApiModelProperty(value="回答")
    private String answer;
    /**
     * 指示
     */
    @ApiModelProperty(value="指示")
    private String indication;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建当前数据的用户
     */
    @ApiModelProperty(value="创建当前数据的用户")
    private String createUserId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
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
