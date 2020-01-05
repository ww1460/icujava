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
 * 流程记录表
 *
 * @author pigx code generator
 * @date 2019-08-07 09:07:45
 */
@Data
@TableName("nur_process_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程记录表")
public class ProcessRecord extends Model<ProcessRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 流程记录表
     */
    @TableId
    @ApiModelProperty(value="流程记录表")
    private Integer id;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 流程名称（1表示入科流程，2标识出科流程）
     */
    @ApiModelProperty(value="流程名称（1表示入科流程，2标识出科流程）")
    private Integer flowName;
    /**
     * 具体步骤id 非外键
     */
    @ApiModelProperty(value="具体步骤id 非外键")
    private String specificStepId;
    /**
     * 状态 1 执行中 2完成
     */
    @ApiModelProperty(value="状态 1 执行中 2完成")
    private Integer status;
    /**
     * 创建当条数据的时间
     */
    @ApiModelProperty(value="创建当条数据的时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endTime;
    /**
     * 创建用户id
     */
    @ApiModelProperty(value="创建用户id")
    private String createUserId;
    /**
     * 完成护士id
     */
    @ApiModelProperty(value="完成护士id")
    private String completeNurseId;
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
     * 删除当条数据的用户
     */
    @ApiModelProperty(value="删除当条数据的用户")
    private String delUserId;
    }
