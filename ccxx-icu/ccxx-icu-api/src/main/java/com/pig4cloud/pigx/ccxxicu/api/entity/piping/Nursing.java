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
 * 管道护理【对插管患者做的护理事项】
 *
 * @author pigx code generator
 * @date 2019-09-03 15:16:19
 */
@Data
@TableName("pip_nursing")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "管道护理【对插管患者做的护理事项】")
public class Nursing extends Model<Nursing> {
private static final long serialVersionUID = 1L;

    /**
     * 管道护理【对插管患者做的护理事项】
     */
    @TableId
    @ApiModelProperty(value="管道护理【对插管患者做的护理事项】")
    private Integer id;
    /**
     * 雪花id 
     */
    @ApiModelProperty(value="雪花id ")
    private String pipNursingId;
    /**
     * 管道使用记录的id
     */
    @ApiModelProperty(value="管道使用记录的id")
    private String pipUseRecordId;
    /**
     * 管路的插管时间
     */
    @ApiModelProperty(value="管路的插管时间")
    private LocalDateTime pipingStartTime;
    /**
     * 插管的护士id
     */
    @ApiModelProperty(value="插管的护士id")
    private String cannulaNurseId;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
    /**
     * 防范措施
     */
    @ApiModelProperty(value="防范措施")
    private String preventiveMeasures;
    /**
     * 护理结果   是否发生脱管  0未脱管   1脱管
     */
    @ApiModelProperty(value="护理结果   是否发生脱管  0未脱管   1脱管")
    private Integer nursingOutcomes;
    /**
     * 脱管原因  1患者/家属自行拔除   2医护操作不当   3其他意外因素
     */
    @ApiModelProperty(value="脱管原因  1患者/家属自行拔除   2医护操作不当   3其他意外因素")
    private Integer causesOfDetachment;
    /**
     * 脱管后处理   1重新置管   0未重新置管
     */
    @ApiModelProperty(value="脱管后处理   1重新置管   0未重新置管")
    private Integer handle;
    /**
     * 患者动态   1出院   0 转科   2死亡
     */
    @ApiModelProperty(value="患者动态   1出院   0 转科   2死亡")
    private Integer patientDynamics;
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
     * 创建当前数据的时间 
     */
    @ApiModelProperty(value="创建当前数据的时间 ")
    private LocalDateTime createTime;
    /**
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private Integer delFlag;
    /**
     * 删除该条数据的用户
     */
    @ApiModelProperty(value="删除该条数据的用户")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
