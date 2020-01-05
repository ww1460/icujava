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
 * 引流液
 *
 * @author pigx code generator
 * @date 2019-08-10 14:14:01
 */
@Data
@TableName("pip_drainage")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "引流液")
public class Drainage extends Model<Drainage> {
private static final long serialVersionUID = 1L;

    /**
     * 引流液id
     */
    @TableId
    @ApiModelProperty(value="引流液id")
    private Integer id;
    /**
     * 引流液id
     */
    @ApiModelProperty(value="引流液id")
    private String drainageId;
    /**
     * 引流液名称
     */
    @ApiModelProperty(value="引流液名称")
    private String drainageName;
    /**
     * 描述对引流液的描述
     */
    @ApiModelProperty(value="描述对引流液的描述")
    private String drainageDescribe;
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
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    }
