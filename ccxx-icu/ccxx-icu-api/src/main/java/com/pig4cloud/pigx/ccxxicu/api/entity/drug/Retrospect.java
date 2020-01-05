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
 * 药品追溯表
 *
 * @author pigx code generator
 * @date 2019-11-05 20:57:55
 */
@Data
@TableName("drug_retrospect")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "药品追溯表")
public class Retrospect extends Model<Retrospect> {
private static final long serialVersionUID = 1L;

    /**
     * 自动生成id
     */
    @TableId
    @ApiModelProperty(value="自动生成id")
    private Integer id;
    /**
     * 雪花id
     */
    @ApiModelProperty(value="雪花id")
    private String retrospectId;
    /**
     * 关联医嘱id
     */
    @ApiModelProperty(value="关联医嘱id")
    private String hisDoctorsAdviceId;
    /**
     * 药品
     */
    @ApiModelProperty(value="药品")
    private String drugName;
    /**
     * 配药时间
     */
    @ApiModelProperty(value="配药时间")
    private LocalDateTime dispensingDrugTime;
    /**
     * 配药护士id
     */
    @ApiModelProperty(value="配药护士id")
    private String dispensingDrugNurseId;
    /**
     * 输液开始时间
     */
    @ApiModelProperty(value="输液开始时间")
    private LocalDateTime transfuseStartTime;
    /**
     * 输液结束时间
     */
    @ApiModelProperty(value="输液结束时间")
    private LocalDateTime transfuseEndTime;
    /**
     * 输液患者id
     */
    @ApiModelProperty(value="输液患者id")
    private String transfusePatientId;
    /**
     * 床位id
     */
    @ApiModelProperty(value="床位id")
    private String bedId;
    /**
     * 输液护士id
     */
    @ApiModelProperty(value="输液护士id")
    private String transfuseNurseId;
    /**
     * 废弃时间
     */
    @ApiModelProperty(value="废弃时间")
    private LocalDateTime discardTime;
    /**
     * 废弃护士id
     */
    @ApiModelProperty(value="废弃护士id")
    private String discardNurseId;
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
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改用户
     */
    @ApiModelProperty(value="修改用户")
    private String updateUserId;
    }
