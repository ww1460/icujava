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

package com.pig4cloud.pigx.ccxxicu.api.entity.arrange;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 护士班次申请表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:09
 */
@Data
@TableName("nur_arrange_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士班次申请表")
public class ArrangeApply extends Model<ArrangeApply> {
private static final long serialVersionUID = 1L;

    /**
     * 护士班次申请表
     */
    @TableId
    @ApiModelProperty(value="护士班次申请表")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String arrangeApplyId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 发起人护士id
     */
    @ApiModelProperty(value="发起人护士id")
    private String initiatorNurseId;
    /**
     * 发起人护士姓名
     */
    @ApiModelProperty(value="发起人护士姓名")
    private String initiatorNurseName;
    /**
     * 原始班次
     */
    @ApiModelProperty(value="原始班次")
    private String originalShift;
    /**
     * 意愿班次
     */
    @ApiModelProperty(value="意愿班次")
    private String hopeShift;
	/**
	 * 申请类型  0本次调班  1 需求班次意愿
	 */
	@ApiModelProperty(value="申请类型  0本次调班  1 需求班次意愿")
	private String applyType;
    /**
     * 被调换护士id
     */
    @ApiModelProperty(value="被调换护士id")
    private String changeNurseId;
    /**
     * 被调换护士姓名
     */
    @ApiModelProperty(value="被调换护士姓名")
    private String changeNurseName;
    /**
     * 申请理由
     */
    @ApiModelProperty(value="申请理由")
    private String applyReason;
    /**
     * 是否批准 0批准 1拒绝 2处理中
     */
    @ApiModelProperty(value="是否批准 0批准 1拒绝 2处理中")
    private Integer approvalFlag;
    /**
     * 申请班次所在时间
     */
    @ApiModelProperty(value="申请班次所在时间")
    private LocalDateTime dateTime;
    /**
     * 处理意见
     */
    @ApiModelProperty(value="处理意见")
    private String approvalOpinion;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人id
     */
    @ApiModelProperty(value="创建人id")
    private String createUserId;
    /**
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
    private Integer delFlag;
    /**
     * 删除人id
     */
    @ApiModelProperty(value="删除人id")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
