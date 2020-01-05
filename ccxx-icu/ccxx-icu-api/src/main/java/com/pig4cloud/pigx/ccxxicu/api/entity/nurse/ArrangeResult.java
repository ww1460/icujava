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
 * 排班结果表  
 *
 * @author pigx code generator
 * @date 2019-08-07 14:54:55
 */
@Data
@TableName("nur_arrange_result")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "排班结果表  ")
public class ArrangeResult extends Model<ArrangeResult> {
private static final long serialVersionUID = 1L;

    /**
     * 排班结果主键 id
     */
    @TableId
    @ApiModelProperty(value="排班结果主键 id")
    private Integer id;
    /**
     * 生成 id
     */
    @ApiModelProperty(value="生成 id")
    private String arrangeResultId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
    /**
     * 排班开始时间
     */
    @ApiModelProperty(value="排班开始时间")
    private LocalDateTime startTime;
    /**
     * 班次所在时间 （哪天上这个班）
     */
    @ApiModelProperty(value="班次所在时间 （哪天上这个班）")
    private LocalDateTime dateTime;
    /**
     * 班次  1 早班  2 小夜  3 大夜  4 白班  5 休息
     */
    @ApiModelProperty(value="班次  1 早班  2 小夜  3 大夜  4 白班  5 休息")
    private Integer workShift;
    /**
     * 创建人 
     */
    @ApiModelProperty(value="创建人 ")
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
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private LocalDateTime updateTime;
    /**
     * 删除 标识   0正常  1删除
     */
    @ApiModelProperty(value="删除 标识   0正常  1删除")
    private Integer delFlag;
    /**
     * 删除人
     */
    @ApiModelProperty(value="删除人")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
