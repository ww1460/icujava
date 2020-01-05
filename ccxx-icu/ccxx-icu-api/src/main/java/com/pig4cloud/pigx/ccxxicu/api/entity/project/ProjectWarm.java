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
 * 项目预警表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:59:14
 */
@Data
@TableName("nur_project_warm")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目预警表")
public class ProjectWarm extends Model<ProjectWarm> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 项目预警表id
     */
    @ApiModelProperty(value="项目预警表id")
    private String projectWarmId;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * 预警值
     */
    @ApiModelProperty(value="预警值")
    private String warmValue;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 预警详情
     */
    @ApiModelProperty(value="预警详情")
    private String warmContent;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
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
    /**
     * del_flag	删除标识  0正常 1删除
     */
    @ApiModelProperty(value="del_flag	删除标识  0正常 1删除")
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
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 最后一次修改人
     */
    @ApiModelProperty(value="最后一次修改人")
    private String updateUserId;
    }
