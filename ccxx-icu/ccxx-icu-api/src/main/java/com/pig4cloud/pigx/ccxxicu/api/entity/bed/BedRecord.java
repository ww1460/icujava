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

package com.pig4cloud.pigx.ccxxicu.api.entity.bed;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 床位使用时间记录
 *
 * @author pigx code generator
 * @date 2019-08-07 21:23:37
 */
@Data
@TableName("nur_bed_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "床位使用时间记录")
public class BedRecord extends Model<BedRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 床位使用时间记录id
     */
    @TableId
    @ApiModelProperty(value="床位使用时间记录id")
    private Integer id;
    /**
     * 床位使用时间记录
     */
    @ApiModelProperty(value="床位使用时间记录")
    private String bedRecordId;
    /**
     * 床位id
     */
    @ApiModelProperty(value="床位id")
    private String bedId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endTime;

	/**
	 * 使用时长
	 */
	@ApiModelProperty(value="使用时长")
	private String totalTime;
    /**
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delUserId;
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
    }
