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
 * 床位表
 *
 * @author yyj
 * @date 2019-08-07 21:24:05
 */
@Data
@TableName("nur_hospital_bed")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "床位表")
public class HospitalBed extends Model<HospitalBed> {
private static final long serialVersionUID = 1L;

    /**
     * 床位表 主键id
     */
    @TableId
    @ApiModelProperty(value="床位表 主键id")
    private Integer id;
    /**
     * 生成的id
     */
    @ApiModelProperty(value="生成的id")
    private String bedId;
    /**
     * 床位名
     */
    @ApiModelProperty(value="床位名")
    private String bedName;
    /**
     * 床位编号
     */
    @ApiModelProperty(value="床位编号")
    private String bedCode;

	/**
	 * 床位RFID
	 */
	@ApiModelProperty(value="床位RFID")
	private String rfid;
    /**
     * 床位使用状态  0未使用  1使用中  2维修中
     */
    @ApiModelProperty(value="床位使用状态  0未使用  1使用中  2维修中")
    private Integer useFlag;
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
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delUserId;
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
