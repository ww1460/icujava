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

package com.pig4cloud.pigx.ccxxicu.api.entity.piping;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 管道使用记录
 *
 * @author pigx code generator
 * @date 2019-08-08 16:23:23
 */
@Data
@TableName("pip_use_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "管道使用记录")
public class UseRecord extends Model<UseRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 管道使用记录
     */
    @TableId
    @ApiModelProperty(value="管道使用记录")
    private Integer id;
    /**
     * 管道使用记录
     */
    @ApiModelProperty(value="管道使用记录")
    private String useRecordsId;
    /**
     * 管道id
     */
    @ApiModelProperty(value="管道id")
    private String pipingId;
    /**
     * 管道部位
     */
    @ApiModelProperty(value="管道部位")
    private String bodyParts;
    /**
     * 开始插管时间
     */
    @ApiModelProperty(value="开始插管时间")
    private LocalDateTime startTime;
    /**
     * 结束插管时间
     */
    @ApiModelProperty(value="结束插管时间")
    private LocalDateTime endTime;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
    /**
     * 插入长度
     */
    @ApiModelProperty(value="插入长度")
    private String lengthBuiltin;
    /**
     * 外露长度
     */
    @ApiModelProperty(value="外露长度")
    private String lengthExposure;

	/**
	 * 固定方式方法
	 */
	@ApiModelProperty(value="固定方式方法")
	private String fixed;

    /**
	 * 标签名称
	 */
	@ApiModelProperty(value="标签名称")
	private String signName;
	/**
     * 标签颜色
     */
    @ApiModelProperty(value="标签颜色")
    private String signColor;
    /**
     * 皮肤局部情况
     */
    @ApiModelProperty(value="皮肤局部情况")
    private String localCondition;
    /**
     * 护理措施
     */
    @ApiModelProperty(value="护理措施")
    private String nursingMeasures;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 评论
     */
    @ApiModelProperty(value="评论")
    private String remark;
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
