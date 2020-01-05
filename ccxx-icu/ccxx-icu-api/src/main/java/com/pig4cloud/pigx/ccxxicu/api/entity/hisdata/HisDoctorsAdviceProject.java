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

package com.pig4cloud.pigx.ccxxicu.api.entity.hisdata;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 医嘱项目表【一批医嘱下相对应的药品、项目数据】
 *
 * @author pigx code generator
 * @date 2019-08-30 10:58:12
 */
@Data
@TableName("his_doctors_advice_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "医嘱项目表【一批医嘱下相对应的药品、项目数据】")
public class HisDoctorsAdviceProject extends Model<HisDoctorsAdviceProject> {
private static final long serialVersionUID = 1L;

    /**
     * 医嘱项目表【一批医嘱下相对应的药品、项目数据】
     */
    @TableId
    @ApiModelProperty(value="医嘱项目表【一批医嘱下相对应的药品、项目数据】")
    private Integer id;
	/**
	 * 雪花id
	 */
	@ApiModelProperty(value="雪花id")
	private String doctorsAdviceProjectId;
	/**
	 * his医嘱id
	 */
	@ApiModelProperty(value="关联医嘱id【His中的医嘱id】")
	private String hisDoctorsAdviceId;

	/**
	 * his当条医嘱id
	 */
	@ApiModelProperty(value="his当条医嘱id")
	private String hisDoctorsAdviceProjectId;

    /**
     * 医嘱批号，与医嘱的关联
     */
    @ApiModelProperty(value="医嘱批号，与医嘱的关联")
    private String batchNumber;
    /**
     * 医嘱内容
     */
    @ApiModelProperty(value="医嘱内容")
    private String content;
    /**
     * 用药方式
     */
    @ApiModelProperty(value="用药方式")
    private String drugUse;
	/**
	 * 医嘱类型【判断当前医嘱的类型，目前处理的只有 为 null  时，为描述性医嘱，直接生成任务，不产生医嘱执行情况】
	 */
	@ApiModelProperty(value="医嘱类型")
	private String doctorsAdviceProjectType;

    /**
     * 用药剂量
     */
    @ApiModelProperty(value="用药剂量 ")
    private String consumption;
    /**
     * 单位
     */
    @ApiModelProperty(value="单位")
    private String company;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remarks;
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
     * 删除该条数据的用户
     */
    @ApiModelProperty(value="删除该条数据的用户")
    private String delUserId;
    }
