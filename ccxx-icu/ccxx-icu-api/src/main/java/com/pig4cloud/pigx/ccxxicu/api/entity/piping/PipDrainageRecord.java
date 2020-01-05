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
 * 引流液记录表id
 *
 * @author pigx code generator
 * @date 2019-08-30 16:50:50
 */
@Data
@TableName("pip_drainage_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "引流液记录表id")
public class PipDrainageRecord extends Model<PipDrainageRecord> {
private static final long serialVersionUID = 1L;

    /**
     * 引流液记录表
     */
    @TableId
    @ApiModelProperty(value="引流液记录表")
    private Integer id;
    /**
     * 引流液记录表id
     */
    @ApiModelProperty(value="引流液记录表id")
    private String drainageRecordsId;
    /**
     * 管道id
     */
    @ApiModelProperty(value="管道id")
    private String pipingId;
    /**
     * 引流液id
     */
    @ApiModelProperty(value="引流液id")
    private String drainageId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 引流液属性
     */
    @ApiModelProperty(value="引流液属性")
    private String drainageAttributeId;
    /**
     * 液体值（重量）
     */
    @ApiModelProperty(value="液体值（重量）")
    private String value;
    /**
     * 实际操作
     */
    @ApiModelProperty(value="实际操作")
    private String measures;
    /**
     * 正常还是异常（0正常 1异常 ）
     */
    @ApiModelProperty(value="正常还是异常（0正常 1异常 ）")
    private Integer normal;
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
     * 创建当前数据的用户
     */
    @ApiModelProperty(value="创建当前数据的用户")
    private String createUserId;
    /**
     * 创建数据时间
     */
    @ApiModelProperty(value="创建数据时间")
    private LocalDateTime createTime;
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
