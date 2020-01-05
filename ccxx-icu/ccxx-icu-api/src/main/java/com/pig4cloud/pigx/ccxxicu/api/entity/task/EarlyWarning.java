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

package com.pig4cloud.pigx.ccxxicu.api.entity.task;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 任务预警表
 *
 * @author pigx code generator
 * @date 2019-08-16 14:57:57
 */
@Data
@TableName("tak_early_warning")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务预警表")
public class EarlyWarning extends Model<EarlyWarning> {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value="id")
    private Integer id;
    /**
     *  任务id
     */
    @ApiModelProperty(value="")
    private String taskId;
    /**
     * 触发时间
     */
    @ApiModelProperty(value="触发时间")
    private LocalDateTime triggerTime;
    /**
     * 任务内容
     */
    @ApiModelProperty(value="")
    private String taskContent;
	/**
	 *超时原因
	 */
	@ApiModelProperty(value="")
	private String reason;
	/**
     * 相关患者id
     */
    @ApiModelProperty(value="相关患者id")
    private String patientId;
    /**
     * 相关护士id
     */
    @ApiModelProperty(value="相关护士id")
    private String nurseId;
    /**
     * 科室表id
     */
    @ApiModelProperty(value="科室表id")
    private String deptId;
    /**
     * 状态      0为提醒  1为关闭提醒
     */
    @ApiModelProperty(value="状态      0为提醒  1为关闭提醒")
    private Integer state;
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
     * 删除 用户id
     */
    @ApiModelProperty(value="删除 用户id")
    private String delUserId;
    }
