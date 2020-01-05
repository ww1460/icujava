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
 * 任务模板子模板
 *
 * @author pigx code generator
 * @date 2019-10-07 16:58:17
 */
@Data
@TableName("tak_template_sub_task")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务模板子模板")
public class TemplateSubTask extends Model<TemplateSubTask> {
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
    private String templateSubTaskId;
    /**
     * 任务内容
     */
    @ApiModelProperty(value="任务内容")
    private String taskContent;
    /**
     * 任务描述
     */
    @ApiModelProperty(value="任务描述")
    private String taskDescribe;
	/**
	 * 预开始时间
	 */
	@ApiModelProperty(value="预开始时间")
	private LocalDateTime preStartTime;
	/**
	 * 预结束时间
	 */
	@ApiModelProperty(value="预结束时间")
	private LocalDateTime preEndTime;
	/**
	 * 间隔
	 */
	@ApiModelProperty(value="间隔")
	private Integer intervalTime;
	/**
	 * 来源id
	 */
	@ApiModelProperty(value="来源id")
	private String sourceId;
	/**
	 * 来源类型【】
	 */
	@ApiModelProperty(value="来源类型【】")
	private Integer source;
	/**
	 * 执行类型【0  提醒     1是执行】
	 */
	@ApiModelProperty(value="执行类型【0  提醒     1是执行】")
	private Integer executionType;
    /**
     * 关联模板id【父级id】
     */
    @ApiModelProperty(value="关联模板id【父级id】")
    private String taskTemplateId;
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
