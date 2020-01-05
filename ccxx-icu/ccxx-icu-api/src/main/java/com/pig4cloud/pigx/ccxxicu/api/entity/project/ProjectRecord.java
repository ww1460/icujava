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
 * 项目数据记录表
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:06
 */
@Data
@TableName("nur_project_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目数据记录表")
public class ProjectRecord extends Model<ProjectRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 项目数据记录  自增id
     */
    @TableId
    @ApiModelProperty(value="项目数据记录  自增id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String projectRecordId;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * 项目的详细数据
     */
    @ApiModelProperty(value="项目的详细数据")
    private String projectSpecificRecord;
    /**
     * 项目的数值数据
     */
    @ApiModelProperty(value="项目的数值数据")
    private String projectValue;
    /**
     * 该记录的来源
     */
    @ApiModelProperty(value="该记录的来源")
    private String source;
    /**
     * 数据关联的id
     */
    @ApiModelProperty(value="数据关联的id")
    private String sourceId;
    /**
     * 产生数据的患者id
     */
    @ApiModelProperty(value="产生数据的患者id")
    private String patientId;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 删除标识 0正常  1删除
     */
    @ApiModelProperty(value="删除标识 0正常  1删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除人id
     */
    @ApiModelProperty(value="删除人id")
    private String delUserId;
    /**
     * 创建人id
     */
    @ApiModelProperty(value="创建人id")
    private String createUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改人id
     */
    @ApiModelProperty(value="修改人id")
    private String updateUserId;
    }
