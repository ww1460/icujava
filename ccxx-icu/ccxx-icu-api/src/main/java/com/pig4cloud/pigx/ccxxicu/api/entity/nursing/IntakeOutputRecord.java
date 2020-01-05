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
 * 出入量记录
 *
 * @author pigx code generator
 * @date 2019-08-22 16:27:47
 */
@Data
@TableName("nur_intake_output_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "出入量记录")
public class IntakeOutputRecord extends Model<IntakeOutputRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 出入量 自增id
     */
    @TableId
    @ApiModelProperty(value="出入量 自增id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String intakeOutputId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;
    /**
     * 出入量标识   0是出量 1是入量
     */
    @ApiModelProperty(value="出入量标识   0是出量 1是入量")
    private Integer intakeOutputType;
    /**
     * 出入量的值
     */
    @ApiModelProperty(value="出入量的值")
    private Integer intakeOutputValue;
    /**
     * 当前的出入量的平衡量
     */
    @ApiModelProperty(value="当前的出入量的平衡量")
    private Integer equilibriumAmount;
    /**
     * 出入量描述
     */
    @ApiModelProperty(value="出入量描述")
    private String valueDescription;
    /**
     * 产生出入量的原因备注
     */
    @ApiModelProperty(value="产生出入量的原因备注")
    private String causeRemark;
	/**
	 * 数据来源
	 */
	@ApiModelProperty(value="数据来源")
	private String source;
    /**
     * 数据产生关联的id
     */
    @ApiModelProperty(value="数据产生关联的id")
    private String sourceId;

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
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
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
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    }
