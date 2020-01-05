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
 * 护士在线时间
 *
 * @author pigx code generator
 * @date 2019-11-06 17:02:01
 */
@Data
@TableName("nur_work_time")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士在线时间")
public class WorkTime extends Model<WorkTime> {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	@ApiModelProperty(value = "自增id")
	private Integer id;
	/**
	 * 护士在线时间 id
	 */
	@ApiModelProperty(value = "护士在线时间 id")
	private String workTimeId;
	/**
	 * 护士id
	 */
	@ApiModelProperty(value = "护士id")
	private String userId;
	/**
	 * 地点  0代表电脑
	 */
	@ApiModelProperty(value = "地点  0代表电脑")
	private String position;

	/**
	 * 床位id
	 */
	@ApiModelProperty(value = "床位id")
	private String bedId;
	/**
	 * 在某地的开始时间
	 */
	@ApiModelProperty(value = "在某地的开始时间")
	private LocalDateTime startTime;
	/**
	 * 在某地的结束时间
	 */
	@ApiModelProperty(value = "在某地的结束时间")
	private LocalDateTime endTime;
	/**
	 * 时间合计
	 */
	@ApiModelProperty(value = "时间合计")
	private Integer timeCount;
	/**
	 * 删除标识 0正常  1删除
	 */
	@ApiModelProperty(value = "删除标识 0正常  1删除")
	private Integer delFlag;
	/**
	 * 删除时间
	 */
	@ApiModelProperty(value = "删除时间")
	private LocalDateTime delTime;
	/**
	 * 删除人
	 */
	@ApiModelProperty(value = "删除人")
	private String delUserId;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;
	/**
	 * 修改数据人id
	 */
	@ApiModelProperty(value = "修改数据人id")
	private String updateUserId;
	/**
	 * 创建人id
	 */
	@ApiModelProperty(value = "创建人id")
	private String createUserId;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
}
