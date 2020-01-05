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
 * 管路表
 *
 * @author pigx code generator
 * @date 2019-08-08 15:11:10
 */
@Data
@TableName("pip_piping")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "管路表")
public class Piping extends Model<Piping> {
private static final long serialVersionUID = 1L;

    /**
     * 管路表id
     */
    @TableId
    @ApiModelProperty(value="管路表id")
    private Integer id;
    /**
     * 管路表id
     */
    @ApiModelProperty(value="管路表id")
    private String pipingId;
    /**
     * 管道名称
     */
    @ApiModelProperty(value="管道名称")
    private String pipingName;
    /**
     * 管道类型（4大分类 1、供给类 2、排出类 3、检测类  4、综合类）
     */
    @ApiModelProperty(value="管道类型（4大分类 1、供给类 2、排出类 3、检测类  4、综合类）")
    private String pipingType;
    /**
     * 制造商
     */
    @ApiModelProperty(value="制造商")
    private String manufacturer;
    /**
     * 入库时间
     */
    @ApiModelProperty(value="入库时间")
    private LocalDateTime dateOfStorage;
    /**
     * 有效时间
     */
    @ApiModelProperty(value="有效时间")
    private LocalDateTime inDate;
    /**
     * rfid编号
     */
    @ApiModelProperty(value="rfid编号")
    private String rfid;
    /**
     * 使用状态  未使用0 使用中 1
     */
    @ApiModelProperty(value="使用状态  未使用0 使用中 1")
    private Integer state;
    /**
     * 风险水平
     */
    @ApiModelProperty(value="风险水平")
    private String risklevel;
    /**
     * 相关联的引流液id
     */
    @ApiModelProperty(value="相关联的引流液id")
    private String drainageId;
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
     * 删除该条数据的用户
     */
    @ApiModelProperty(value="删除该条数据的用户")
    private String delUserId;
    }
