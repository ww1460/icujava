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
 * 任务委托
 *
 * @author pigx code generator
 * @date 2019-09-02 16:00:25
 */
@Data
@TableName("tak_entrust")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务委托")
public class Entrust extends Model<Entrust> {
private static final long serialVersionUID = 1L;

    /**
     * 任务委托id
     */
    @TableId
    @ApiModelProperty(value="任务委托id")
    private Integer id;
    /**
     * 雪花id
     */
    @ApiModelProperty(value="雪花id")
    private String taskEntrustId;
    /**
     * 发起人id
     */
    @ApiModelProperty(value="发起人id")
    private String originatorId;
    /**
     * 接收人
     */
    @ApiModelProperty(value="接收人")
    private String recipientId;
    /**
     * 任务id
     */
    @ApiModelProperty(value="任务id")
    private String taskId;
    /**
     * 请求时间
     */
    @ApiModelProperty(value="请求时间")
    private LocalDateTime time;
    }
