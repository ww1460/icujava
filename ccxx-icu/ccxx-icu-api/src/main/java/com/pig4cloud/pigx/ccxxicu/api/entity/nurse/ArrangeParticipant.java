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
 * 参与排班人员表
 *
 * @author pigx code generator
 * @date 2019-08-12 20:48:58
 */
@Data
@TableName("nur_arrange_participant")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "参与排班人员表")
public class ArrangeParticipant extends Model<ArrangeParticipant> {
private static final long serialVersionUID = 1L;

    /**
     * 参与排班人员表主键id
     */
    @TableId
    @ApiModelProperty(value="参与排班人员表主键id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String arrangeParticipantId;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 护士等级
     */
    @ApiModelProperty(value="护士等级")
    private Integer nurseGrade;
    /**
	 * 工作班次
	 */
	@ApiModelProperty(value="工作班次")
	private Integer workShift;
	/**
	 * 上班次数
	 */
	@ApiModelProperty(value="上班次数")
	private Integer count;
    /**
     * 排班的开始时间
     */
    @ApiModelProperty(value="排班的开始时间")
    private LocalDateTime startTime;
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
