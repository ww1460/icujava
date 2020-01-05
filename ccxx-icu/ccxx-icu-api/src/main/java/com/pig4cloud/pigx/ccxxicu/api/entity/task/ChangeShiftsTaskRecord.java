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
 * 交接班任务记录表
 *
 * @author pigx code generator
 * @date 2019-08-23 16:16:18
 */
@Data
@TableName("tak_change_shifts_task_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "交接班任务记录表")
public class ChangeShiftsTaskRecord extends Model<ChangeShiftsTaskRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 交接班任务表
     */
    @TableId
    @ApiModelProperty(value="交接班任务表")
    private Integer id;
    /**
     * 交接班记录表id
     */
    @ApiModelProperty(value="交接班记录表id")
    private String changeShiftsRecordId;
    /**
     * 任务id
     */
    @ApiModelProperty(value="任务id")
    private String taskId;
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
     * 任务状态
     */
    @ApiModelProperty(value="任务状态")
    private Integer taskState;
    /**
     * 交接班描述
     */
    @ApiModelProperty(value="交接班描述")
    private String shiftsTaskDescribe;
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
     * 删除该条数据的用户
     */
    @ApiModelProperty(value="删除该条数据的用户")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
