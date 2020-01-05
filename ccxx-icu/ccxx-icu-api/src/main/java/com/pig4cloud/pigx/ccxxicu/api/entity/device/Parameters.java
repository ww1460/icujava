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

package com.pig4cloud.pigx.ccxxicu.api.entity.device;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 设备参数表
 *
 * @author pigx code generator
 * @date 2019-08-05 09:40:44
 */
@Data
@TableName("dev_parameter")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "设备参数表")
public class Parameters extends Model<Parameters> {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备参数表 id
	 */
	@TableId
	@ApiModelProperty(value = "设备参数表 id")
	private Integer id;
	/**
	 * 设备参数表 id
	 */
	@ApiModelProperty(value = "设备参数表 code")
	private String parameterCode;
	/**
	 * 参数名称
	 */
	@ApiModelProperty(value = "参数名称")
	private String parameterName;
	/**
	 * 设备型号
	 */
	@ApiModelProperty(value = "设备型号")
	private String devModel;
	/**
	 * 创建当前数据的时间
	 */
	@ApiModelProperty(value = "创建当前数据的时间 ")
	private LocalDateTime createTime;
	/**
	 * 删除标识  0正常 1删除
	 */
	@ApiModelProperty(value = "删除标识  0正常 1删除")
	private Integer delFlag;
	/**
	 * 删除时间
	 */
	@ApiModelProperty(value = "删除时间")
	private LocalDateTime delTime;
	/**
	 * 删除该条数据的用户
	 */
	@ApiModelProperty(value = "删除该条数据的用户")
	private String delUserId;
	/**
	 * 科室表id
	 */
	@ApiModelProperty(value = "科室表id")
	private String deptId;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createUserId;
	/**
	 * 采集项所关联项目
	 */
	@ApiModelProperty(value = "采集项所关联项目")
	private String projectId;
}
