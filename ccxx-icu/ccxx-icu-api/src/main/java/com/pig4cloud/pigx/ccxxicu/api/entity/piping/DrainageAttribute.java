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
 * 引流液属性
 *
 * @author pigx code generator
 * @date 2019-08-10 15:48:46
 */
@Data
@TableName("pip_drainage_attribute")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "引流液属性")
public class DrainageAttribute extends Model<DrainageAttribute> {
private static final long serialVersionUID = 1L;

    /**
     * 引流液属性表
     */
    @TableId
    @ApiModelProperty(value="引流液属性表")
    private Integer id;
    /**
     * 引流液属性表id
     */
    @ApiModelProperty(value="引流液属性表id")
    private String drainageAttributeId;
    /**
     * 引流液id
     */
    @ApiModelProperty(value="引流液id")
    private String drainageId;
    /**
     * 属性名称（颜色、气味、等等）
     */
    @ApiModelProperty(value="属性名称（颜色、气味、等等）")
    private String attributeName;
    /**
     * 属性类型（颜色种类 、气味种类）
     */
    @ApiModelProperty(value="属性类型（颜色种类 、气味种类）")
    private String attributeType;
    /**
     * 正常还是异常（0正常 1异常 ）
     */
    @ApiModelProperty(value="正常还是异常（0正常 1异常 ）")
    private Integer normal;
    /**
     * 当前引流液对应属性的措施
     */
    @ApiModelProperty(value="当前引流液对应属性的措施")
    private String measures;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
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
     * 删除数据的用户
     */
    @ApiModelProperty(value="删除数据的用户")
    private String delUserId;
    }
