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
 * 知情文书记录
 *
 * @author pigx code generator
 * @date 2019-08-17 16:10:44
 */
@Data
@TableName("nur_know_writ_logs")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "知情文书记录")
public class KnowWritLogs extends Model<KnowWritLogs> {
private static final long serialVersionUID = 1L;

    /**
     * 知情文书记录  id
     */
    @TableId
    @ApiModelProperty(value="知情文书记录  id")
    private Integer id;
    /**
     * 生成id
     */
    @ApiModelProperty(value="生成id")
    private String knowWritLogsId;
    /**
     * 知情文书生成id
     */
    @ApiModelProperty(value="知情文书生成id")
    private String knowWritId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 患者姓名
     */
    @ApiModelProperty(value="患者姓名")
    private String patientName;
	/**
	 * 文书内容
	 */
	@ApiModelProperty(value="文书内容")
	private String knowWritContent;
    /**
     * 患者或患者家属签字
     */
    @ApiModelProperty(value="患者或患者家属签字")
    private String patientRelationSignature;
	/**
	 * 患者或关系人的联系方式
	 */
	@ApiModelProperty(value="患者或关系人的联系方式")
	private String patientRelativePhone;
	/**
	 * 与患者的关系
	 */
	@ApiModelProperty(value="与患者的关系")
	private String patientRelation;
	/**
	 * 签字日期
	 */
	@ApiModelProperty(value="签字日期")
	private String signatureTime;
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
     * 删除标识 0正常 1删除
     */
    @ApiModelProperty(value="删除标识 0正常 1删除")
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
    }
