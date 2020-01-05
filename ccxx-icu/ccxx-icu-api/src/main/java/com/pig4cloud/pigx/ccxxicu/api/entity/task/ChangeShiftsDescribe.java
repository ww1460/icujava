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

/**
 * 交接班描述记录表   用户临时记录交接班的描述
 *
 * @author pigx code generator
 * @date 2019-08-23 13:42:51
 */
@Data
@TableName("tak_change_shifts_describe")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "交接班描述记录表   用户临时记录交接班的描述")
public class ChangeShiftsDescribe extends Model<ChangeShiftsDescribe> {
private static final long serialVersionUID = 1L;

    /**
     * 交接班描述记录表 
     */
    @TableId
    @ApiModelProperty(value="交接班描述记录表 ")
    private Integer id;
    /**
     * 任务id
     */
    @ApiModelProperty(value="任务id")
    private String taskId;
    /**
     * 交接班任务描述
     */
    @ApiModelProperty(value="交接班任务描述")
    private String shiftsTaskDescribe;
    }
