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
 * 任务模板
 *
 * @author pigx code generator
 * @date 2019-08-15 10:09:38
 */
@Data
@TableName("tak_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务模板")
public class Template extends Model<Template> {
private static final long serialVersionUID = 1L;

    /**
     * 任务模板
     */
    @TableId
    @ApiModelProperty(value="任务模板")
    private Integer id;
    /**
     * 任务模板雪花
     */
    @ApiModelProperty(value="任务模板雪花")
    private String templateId;
    /**
     * 任务内容
     */
    @ApiModelProperty(value="任务名称")
    private String templateContent;
    /**
     * 任务描述
     */
    @ApiModelProperty(value="任务描述")
    private String templateDescribe;
	/**
	 * 来源id  当条用项目中的内容为条件时 保存项目的id
	 */
	@ApiModelProperty(value="来源id 当条数据项目为医嘱时，保存医嘱的id数据")
	private String sourceId;
	/**
	 * 项目【来源】类型  0普通  1医嘱 2快捷
	 */
	@ApiModelProperty(value="项目【来源】类型 ")
	private Integer source;
    /**
     * 用途（0个人   ，1科室）
     */
    @ApiModelProperty(value="用途（0个人   ，1科室）")
    private Integer purpose;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
    /**
     * 子模板条数
     */
    @ApiModelProperty(value="子模板条数")
    private Integer subTaskNum;
    /**
     * 创建的用户
     */
    @ApiModelProperty(value="创建的用户")
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
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private String delUserId;
    }
