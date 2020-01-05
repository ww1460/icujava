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

package com.pig4cloud.pigx.ccxxicu.api.entity.assess;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 评估项目记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:58:49
 */
@Data
@TableName("nur_assess_project_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "评估项目记录表")
public class AssessProjectRecord extends Model<AssessProjectRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 评估项目结果记录 id
     */
    @TableId
    @ApiModelProperty(value="评估项目结果记录 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String assessProjectRecordId;
    /**
     * 评估记录id
     */
    @ApiModelProperty(value="评估记录id")
    private String assessRecordId;
    /**
     * 评估项目id
     */
    @ApiModelProperty(value="评估项目id")
    private String assessProjectId;
    /**
     * 评估条件id
     */
    @ApiModelProperty(value="评估条件id")
    private String assessConditionId;

	/**
	 * 评估结果备注
	 */
	@ApiModelProperty(value="评估结果备注")
	private String remarks;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 科室
     */
    @ApiModelProperty(value="科室")
    private String deptId;
    /**
     * 删除标识 0正常 1 删除
     */
    @ApiModelProperty(value="删除标识 0正常 1 删除")
    private Integer delFlag;
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
