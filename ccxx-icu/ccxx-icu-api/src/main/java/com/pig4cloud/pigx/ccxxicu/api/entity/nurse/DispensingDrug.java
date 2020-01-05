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
 * 配药
 *
 * @author pigx code generator
 * @date 2019-09-05 15:53:09
 */
@Data
@TableName("nur_dispensing_drug")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "配药")
public class DispensingDrug extends Model<DispensingDrug> {
private static final long serialVersionUID = 1L;

    /**
     * 配药 id 
     */
    @TableId
    @ApiModelProperty(value="配药 id ")
    private Integer id;
    /**
     * 雪花id
     */
    @ApiModelProperty(value="雪花id")
    private String dispensingDrugId;
    /**
     * 医嘱id
     */
    @ApiModelProperty(value="医嘱id")
    private String doctorsAdviceId;
    /**
     * rfid_id
     */
    @ApiModelProperty(value="rfid_id")
    private String rfidId;
    /**
     * 患者id
     */
    @ApiModelProperty(value="患者id")
    private String patientId;
    /**
     * 任务id
     */
    @ApiModelProperty(value="任务id")
    private String taskId;
    /**
     * 滴速
     */
    @ApiModelProperty(value="滴速")
    private String droppingSpeed;
    /**
     * 给药途途径确定(用药方式) 遵医嘱(或者将修改内容填充）
     */
    @ApiModelProperty(value="给药途途径确定(用药方式) 遵医嘱(或者将修改内容填充）")
    private String druguse;
    /**
     * 患者过敏史
     */
    @ApiModelProperty(value="患者过敏史")
    private String allergichistory;
    /**
     * 核对护士
     */
    @ApiModelProperty(value="核对护士")
    private String nurseId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 删除标识  0正常 1删除
     */
    @ApiModelProperty(value="删除标识  0正常 1删除")
    private Integer delFlag;
    /**
     * 删除用户
     */
    @ApiModelProperty(value="删除用户")
    private String delUserId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value="删除时间")
    private LocalDateTime delTime;
    }
