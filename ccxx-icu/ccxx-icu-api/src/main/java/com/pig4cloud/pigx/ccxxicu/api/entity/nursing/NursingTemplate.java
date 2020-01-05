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

package com.pig4cloud.pigx.ccxxicu.api.entity.nursing;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 护理模板
 *
 * @author pigx code generator
 * @date 2019-08-19 17:00:03
 */
@Data
@TableName("nur_nursing_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护理模板")
public class NursingTemplate extends Model<NursingTemplate> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 护理模板id
     */
    @ApiModelProperty(value="护理模板id")
    private String nursingTemplateId;
    /**
     * 模板标题
     */
    @ApiModelProperty(value="模板标题")
    private String templateTitle;
    /**
     * 模板编号
     */
    @ApiModelProperty(value="模板编号")
    private String templateCode;

	/**
	 * 是否为项目类的模板  0是 1否
	 */
	@ApiModelProperty(value="是否为项目类的模板  0是 1否")
	private Integer isProjectFlag;


	/**
	 * 模板类型  1直接添加 2护理记录单 3 两者都有
	 */
	@ApiModelProperty(value="模板类型  1直接添加 2护理记录单 3 两者都有")
	private Integer templateType;

	/**
	 * 模板内容
	 */
	@ApiModelProperty(value="模板内容")
	private String templateContent;

    /**
     * 模板描述
     */
    @ApiModelProperty(value="模板描述")
    private String templateDescription;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * 项目描述
     */
    @ApiModelProperty(value="项目描述")
    private String projectDescribe;
    /**
     * 同一模板的项目索引
     */
    @ApiModelProperty(value="同一模板的项目索引")
    private Integer templateIndex;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 模板使用范围 0代表个人  1代表科室
     */
    @ApiModelProperty(value="模板使用范围 0代表个人  1代表科室")
    private Integer templateUseScope;
    /**
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private Integer delFlag;
    /**
     * 创建当前数据的用户
     */
    @ApiModelProperty(value="创建当前数据的用户")
    private String createUserId;
    /**
     * 创建当前数据的时间 
     */
    @ApiModelProperty(value="创建当前数据的时间 ")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
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
