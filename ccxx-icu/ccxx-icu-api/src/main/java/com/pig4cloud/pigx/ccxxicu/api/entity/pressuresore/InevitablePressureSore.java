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

package com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 难免压疮申报表
 *
 * @author pigx code generator
 * @date 2019-08-26 11:09:03
 */
@Data
@TableName("pre_inevitable_pressure_sore")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "难免压疮申报表")
public class InevitablePressureSore extends Model<InevitablePressureSore> {
private static final long serialVersionUID = 1L;

    /**
     * 难免压疮申报表
     */
    @TableId
    @ApiModelProperty(value="难免压疮申报表")
    private Integer id;
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
     * 患者性别 
     */
    @ApiModelProperty(value="患者性别 ")
    private String gender;
    /**
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    private String age;
    /**
     * 住院号
     */
    @ApiModelProperty(value="住院号")
    private String hospitalnumber;
    /**
     * 床位id
     */
    @ApiModelProperty(value="床位id")
    private String bedId;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;
    /**
     * 入院时间
     */
    @ApiModelProperty(value="入院时间")
    private LocalDateTime timeOfAdmission;
    /**
     * 申报时间
     */
    @ApiModelProperty(value="申报时间")
    private LocalDateTime declareTime;
    /**
     * 申报人(id)
     */
    @ApiModelProperty(value="申报人(id)")
    private String declarer;
    /**
     * 诊断
     */
    @ApiModelProperty(value="诊断")
    private String diagnosis;
    /**
     * 护理级别
     */
    @ApiModelProperty(value="护理级别")
    private Integer nursingLevel;
    /**
     * 申报理由
     */
    @ApiModelProperty(value="申报理由")
    private String declareReason;
    /**
     * 采取预防护理措施
     */
    @ApiModelProperty(value="采取预防护理措施")
    private String preventiveNursingMeasures;
    /**
     * 压疮发生时间
     */
    @ApiModelProperty(value="压疮发生时间")
    private LocalDateTime occurrenceTime;
    /**
     * 压疮部位
     */
    @ApiModelProperty(value="压疮部位")
    private String prePosition;
    /**
     * 面积
     */
    @ApiModelProperty(value="面积")
    private String soreRange;
    /**
     * 程度(压疮分期)
     */
    @ApiModelProperty(value="程度(压疮分期)")
    private Integer byStages;
    /**
     * 责任护士签字
     */
    @ApiModelProperty(value="责任护士签字")
    private String responsibleNurse;
    /**
     * 责任护士签字时间
     */
    @ApiModelProperty(value="责任护士签字时间")
    private LocalDateTime responsibleNurseTime;
    /**
     * 护士长签字
     */
    @ApiModelProperty(value="护士长签字")
    private String headNurse;
    /**
     * 科护士长签字
     */
    @ApiModelProperty(value="科护士长签字")
    private String deptHeadNurse;
    /**
     * 科护士长 难免压疮申报条件(0是  1否)
     */
    @ApiModelProperty(value="科护士长 难免压疮申报条件(0是  1否)")
    private Integer deptHeadNurseDeclare;
    /**
     * 科护士长签字 时间
     */
    @ApiModelProperty(value="科护士长签字 时间")
    private LocalDateTime deptHeadNurseTime;
    /**
     * 伤口管理组签字
     */
    @ApiModelProperty(value="伤口管理组签字")
    private String woundManagementGroup;
    /**
     * 伤口管理难免压疮申报 条件 ( 0 是  1否)
     */
    @ApiModelProperty(value="伤口管理难免压疮申报 条件 ( 0 是  1否)")
    private Integer woundManagementGroupDeclare;
    /**
     * 伤口管理时间
     */
    @ApiModelProperty(value="伤口管理时间")
    private LocalDateTime woundManagementGroupTime;
    /**
     * 压疮转归情况   未发生    发生（治愈，好转，未愈合）
     */
    @ApiModelProperty(value="压疮转归情况   0未发生   1 发生（2治愈，3好转，4未愈合）")
    private String pressureSoreOutcome;
    /**
     * 护理部签字
     */
    @ApiModelProperty(value="护理部签字")
    private String nursingDepartment;
    /**
     * 护理部时间
     */
    @ApiModelProperty(value="护理部时间")
    private LocalDateTime nursingDepartmentTime;
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
