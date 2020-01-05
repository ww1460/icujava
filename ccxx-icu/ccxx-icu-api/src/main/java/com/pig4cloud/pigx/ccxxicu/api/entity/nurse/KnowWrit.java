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
 * 知情文书表 
 *
 * @author pigx code generator
 * @date 2019-08-17 14:55:05
 */
@Data
@TableName("nur_know_writ")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "知情文书表 ")
public class KnowWrit extends Model<KnowWrit> {
private static final long serialVersionUID = 1L;

    /**
     * 知情文书表 id
     */
    @TableId
    @ApiModelProperty(value="知情文书表 id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String knowWritId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 文书标题
     */
    @ApiModelProperty(value="文书标题")
    private String writTitle;

    private String writCode;

    /**
     * 文书描述
     */
    @ApiModelProperty(value="文书描述")
    private String writDescribe;
	/**
	 * 文书类型
	 */
	@ApiModelProperty(value="文书类型")
	private String writType;
    /**
     * 文书内容
     */
    @ApiModelProperty(value="文书内容")
    private String writContent;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createUserId;
    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String updateUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;
    /**
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
    private Integer delFlag;
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
