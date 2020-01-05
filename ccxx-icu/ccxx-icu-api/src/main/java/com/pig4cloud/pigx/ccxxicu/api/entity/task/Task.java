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
 * 任务表
 *
 * @author pigx code generator
 * @date 2019-08-13 16:30:28
 */
@Data
@TableName("tak_task")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务表")
public class Task extends Model<Task> {
private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @TableId
    @ApiModelProperty(value="任务id")
    private Integer id;
    /**
     * 雪花id
     */
    @ApiModelProperty(value="雪花id")
    private String taskId;
    /**
     * 1代表医嘱 2代表护理  3代表任务元件库中任务    任务类型
     */
    @ApiModelProperty(value="1代表医嘱 2代表护理  3代表任务元件库中任务  4项目表中 任务类型")
    private Integer sourceItems;
    /**
     * 如果是医嘱或者是任务元件库的任务直接记录id就行
     */
    @ApiModelProperty(value="如果是医嘱或者是任务元件库的任务直接记录id就行")
    private String sourceItemsId;
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
     * 责任护士id
     */
    @ApiModelProperty(value="预执行护士id")
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
    @ApiModelProperty(value="创建人 ")
    private String creatorId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 是否为紧急任务可以分为3个等级 1一般 2 需要加急 3紧急   默认为 1
     */
    @ApiModelProperty(value="是否为紧急任务可以分为3个等级 1一般 2 需要加急 3紧急   默认为 1 ")
    private Integer emergency;
    /**
     * 任务完成状态    1待执行  2 执行中  3完成  4停止（因为出院 、或者转科时）
     */
    @ApiModelProperty(value="任务完成状态   待执行  2 执行中  3完成  4停止（因为转科等等）  5患者出科 ")
    private Integer state;
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
     * 接收任务时间
     */
    @ApiModelProperty(value="接收任务时间")
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
