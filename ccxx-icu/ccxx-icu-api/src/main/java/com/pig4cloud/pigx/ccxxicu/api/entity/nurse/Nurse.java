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

package com.pig4cloud.pigx.ccxxicu.api.entity.nurse;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 护士表
 *
 * @author pigx code generator
 * @date 2019-08-02 21:15:05
 */
@Data
@TableName("nur_nurse")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士表")
public class Nurse extends Model<Nurse> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 护士表id
     */
    @ApiModelProperty(value="护士表id")
    private String nurseId;
	/**
	 * 护士在his中的id
	 */
	@ApiModelProperty(value="护士在his中的id")
	private String hisNurseId;
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private String userId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;

	/**
	 * 角色标识 0护士  1护士长
	 */
	@ApiModelProperty(value="角色标识  0护士  1护士长")
    private Integer roleFlag;
    /**
     * 护士等级（0 - 4）
     */
    @ApiModelProperty(value="护士等级（0 - 4）")
    private Integer nurseGrade;
    /**
     * 护士的RFID
     */
    @ApiModelProperty(value="护士的RFID")
    private String nurseRfid;
    /**
     * 护士真实姓名
     */
    @ApiModelProperty(value="护士真实姓名")
    private String realName;

	/**
	 * 护士个人警示语
	 */
	@ApiModelProperty(value="护士个人警示语")
	private String motto;

	/**
	 * 护士的个人签名
	 */
	@ApiModelProperty(value="护士的个人签名")
	private String signature;
    /**
     * 性别   1 男  0女
     */
    @ApiModelProperty(value="性别   1 男  0女")
    private Integer gender;
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
     * 删除标识  0 正常 1删除
     */
    @ApiModelProperty(value="删除标识  0 正常 1删除")
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
    }
