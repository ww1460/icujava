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
 * 护理项目与任务模板关联表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:39:31
 */
@Data
@TableName("tak_nursing_task_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护理项目与任务模板关联表")
public class NursingTaskRelation extends Model<NursingTaskRelation> {
private static final long serialVersionUID = 1L;

    /**
     * 护理项目与任务模板关联表
     */
    @TableId
    @ApiModelProperty(value="护理项目与任务模板关联表")
    private Integer id;
	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目Id")
	private String projectId;

	/**
	 * 项目名称
	 */
	@ApiModelProperty(value="项目名称")
	private String name;
	/**
	 * 任务id
	 */
	@ApiModelProperty(value="任务id查询")
	private String takTemplateId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;


	// 下面两个字段暂时无用

	/**

    /**
     * 项目简称
     */
    @ApiModelProperty(value="项目简称")
    private String code;

    /**
     * 项目编码 护理项目id 当前 在字典表中的id
     */
    @ApiModelProperty(value="项目编码")
    private String projectType;

    }
