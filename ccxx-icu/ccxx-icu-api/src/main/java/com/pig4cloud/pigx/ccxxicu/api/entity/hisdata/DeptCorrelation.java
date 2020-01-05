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
 * his【科室】与我们科室关联表
 *
 * @author pigx code generator
 * @date 2019-10-08 20:12:09
 */
@Data
@TableName("his_dept_correlation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "his【科室】与我们科室关联表")
public class DeptCorrelation extends Model<DeptCorrelation> {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value="id")
    private Integer id;
    /**
     * his科室id
     */
    @ApiModelProperty(value="his科室id")
    private String hisDeptId;
    /**
     * his科室名称
     */
    @ApiModelProperty(value="his科室名称")
    private String hisDeptName;
    /**
     * 我方科室id
     */
    @ApiModelProperty(value="我方科室id")
    private String deptId;
    /**
     * 我方科室名称
     */
    @ApiModelProperty(value="我方科室名称")
    private String deptName;
    /**
     * 医院编号
     */
    @ApiModelProperty(value="医院编号")
    private String hospitalCode;
    /**
     * 删除标识 0 正常   1是删除
     */
    @ApiModelProperty(value="删除标识 0 正常   1是删除")
    private Integer delFlag;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    /**
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private String createUserId;
    }
