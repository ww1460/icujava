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

package com.pig4cloud.pigx.ccxxicu.api.entity.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 项目管理表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:09
 */
@Data
@TableName("nur_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目管理表")
public class Project extends Model<Project> {
private static final long serialVersionUID = 1L;

    /**
     * 项目管理表 id
     */
    @TableId
    @ApiModelProperty(value="项目管理表 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String projectId;
    /**
     * 项目名
     */
    @ApiModelProperty(value="项目名")
    private String projectName;
    /**
     * 项目简称
     */
    @ApiModelProperty(value="项目简称")
    private String projectCode;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
    /**
     * 项目类型
     */
    @ApiModelProperty(value="项目类型")
    private Integer projectType;

	/**
	 * 是否为病情护理项目 0是 1否
	 */
	@ApiModelProperty(value="是否为病情护理项目 0是 1否")
	private Integer illnessNursingFlag;
    /**
     * 是否存在预警  0存在  1不存在
     */
    @ApiModelProperty(value="是否存在预警  0存在  1不存在")
    private Integer projectWarmFlag;

	/**
	 * 是否存在记录固定值  0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在记录固定值  0存在  1不存在")
	private Integer projectRecordValueFlag;

    /**
     * 删除标识 0正常   1删除
     */
    @ApiModelProperty(value="删除标识 0正常   1删除")
    private Integer delFlag;
    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createUserId;
    }
