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
 * 任务主表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:07:21
 */
@Data
@TableName("tak_tasks")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务主表")
public class Tasks extends Model<Tasks> {
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
    private String taskId;
    /**
     * 任务类型【0自建   1项目   2模板   3医嘱】
     */
    @ApiModelProperty(value="任务类型【0自建   1项目   2模板   3医嘱】")
    private Integer taskType;
    /**
     * 任务类型id 
     */
    @ApiModelProperty(value="任务类型id ")
    private String taskTypeId;
    /**
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    private String taskName;
    /**
     * 任务描述
     */
    @ApiModelProperty(value="任务描述")
    private String taskDescribe;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 责任护士
     */
    @ApiModelProperty(value="责任护士")
    private String dutyNurseId;
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
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String creatorId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 是否为紧急任务可以分为3个等级 1一般 2 需要加急 3紧急   默认为 1
     */
    @ApiModelProperty(value="是否为紧急任务可以分为3个等级 1一般 2 需要加急 3紧急   默认为 1")
    private Integer emergency;
    /**
     * "任务完成状态   1待执行  3 完成  4停止（因为转科等等）  5患者出科
     */
    @ApiModelProperty(value="任务完成状态   1待执行  3 完成  4停止（因为转科等等）  5患者出科")
    private Integer state;
    /**
     * 执行类型【0  提醒     1是执行】
     */
    @ApiModelProperty(value="执行类型【0  提醒     1是执行】")
    private Integer executionType;
    /**
     * 子任务条数
     */
    @ApiModelProperty(value="子任务条数")
    private Integer subTaskNum;
    /**
     * 执行条数
     */
    @ApiModelProperty(value="执行条数")
    private Integer executingNum;
    /**
     * 任务关系【是1主，还是0子】
     */
    @ApiModelProperty(value="任务关系【是1主，还是0子】")
    private Integer taskRelation;
    /**
     * 接收护士id
     */
    @ApiModelProperty(value="接收护士id")
    private String receptionNurseId;
	/**
	 * 进度条
	 */
	@ApiModelProperty(value="进度条")
	private Integer progressBar;
	/**
	 * 完成护士id
	 */
	@ApiModelProperty(value="完成护士id")
	private String completedNurseId;
    /**
     * 接收时间
     */
    @ApiModelProperty(value="接收时间")
    private LocalDateTime receiveTime;
    /**
     * 任务开始时间
     */
    @ApiModelProperty(value="任务开始时间")
    private LocalDateTime startTime;
    /**
     * 任务结束时间
     */
    @ApiModelProperty(value="任务结束时间")
    private LocalDateTime endTime;
    /**
     * 任务结果
     */
    @ApiModelProperty(value="任务结果")
    private String result;
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
