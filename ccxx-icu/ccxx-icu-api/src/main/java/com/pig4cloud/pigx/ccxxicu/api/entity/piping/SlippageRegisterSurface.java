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
 * 管道滑脱登记表
 *
 * @author pigx code generator
 * @date 2019-08-21 10:30:22
 */
@Data
@TableName("pip_slippage_register_surface")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "管道滑脱登记表")
public class SlippageRegisterSurface extends Model<SlippageRegisterSurface> {
private static final long serialVersionUID = 1L;

    /**
     * 管道滑脱登记表
     */
    @TableId
    @ApiModelProperty(value="管道滑脱登记表")
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
     * 住院号 
     */
    @ApiModelProperty(value="住院号 ")
    private String hospitalnumber;
    /**
     * 性别  1男 0女
     */
    @ApiModelProperty(value="性别  1男 0女")
    private Integer gender;
    /**
     * 年龄
     */
    @ApiModelProperty(value="年龄")
    private String age;
    /**
     * 患者诊断
     */
    @ApiModelProperty(value="患者诊断")
    private String diagnosis;
    /**
     * 教育程度  0文盲   1小学   2初中    3高中  4大专   5本科及本科以上
     */
    @ApiModelProperty(value="教育程度  0文盲   1小学   2初中    3高中  4大专   5本科及本科以上")
    private Integer educationLevel;
    /**
     * 管道id   当护士选择管道更多时  默认id9999
     */
    @ApiModelProperty(value="管道id  ")
    private String pipingId;
    /**
     * 管道表描述管道名称        
     */
    @ApiModelProperty(value="管道表描述管道名称        ")
    private String pipingDescribe;
    /**
     * 管道滑脱发生时间
     */
    @ApiModelProperty(value="管道滑脱发生时间")
    private LocalDateTime pipingSlippageTime;
    /**
     * 管道插入时间(置管时间)
     */
    @ApiModelProperty(value="管道插入时间(置管时间)")
    private LocalDateTime pipingStartTime;
    /**
     * 手术时间
     */
    @ApiModelProperty(value="手术时间")
    private LocalDateTime operationTime;
    /**
     * 护理级别  0特技  1一级  2二级   3三级
     */
    @ApiModelProperty(value="护理级别  0特技  1一级  2二级   3三级")
    private Integer nursingLevel;
    /**
     * 意识状态   0清醒  1嗜睡  2朦胧   3 躁动   4昏迷
     */
    @ApiModelProperty(value="意识状态   0清醒  1嗜睡  2朦胧   3 躁动   4昏迷")
    private Integer consciousness;
    /**
     * 意识状态记录时间
     */
    @ApiModelProperty(value="意识状态记录时间")
    private LocalDateTime consciousnessTime;
    /**
     * 精神状态   0平静   1烦躁   2焦虑  3恐惧
     */
    @ApiModelProperty(value="精神状态   0平静   1烦躁   2焦虑  3恐惧")
    private Integer mentality;
    /**
     * 精神状态记录时间
     */
    @ApiModelProperty(value="精神状态记录时间")
    private LocalDateTime mentalityTime;
    /**
     * 活动能力   0行动正常  1使用助行器  2残肢  3无法行动
     */
    @ApiModelProperty(value="活动能力   0行动正常  1使用助行器  2残肢  3无法行动")
    private Integer activityAbility;

    /**
     * 精神状态记录时间
     */
    @ApiModelProperty(value="活动能力计算时间")
    private LocalDateTime activityAbilityTime;
    /**
     * 管道滑落原因
     */
    @ApiModelProperty(value="管道滑落原因")
    private String pipingCausesOfSlippage;
    /**
     * 管道滑脱处理措施
     */
    @ApiModelProperty(value="管道滑脱处理措施")
    private String pipingTreatmentMeasures;
    /**
     * 管道固定方法
     */
    @ApiModelProperty(value="管道固定方法")
    private String pipingFixedMethod;
    /**
     * 健康宣教  是否进行健康宣教  1有  0没有
     */
    @ApiModelProperty(value="健康宣教  是否进行健康宣教  1有  0没有")
    private Integer healthEducation;
    /**
     * 健康宣教时间
     */
    @ApiModelProperty(value="健康宣教时间")
    private LocalDateTime healthEducationTime;
    /**
     * 是否使用过约束带   1使用过   0没有使用过
     */
    @ApiModelProperty(value="是否使用过约束带   1使用过   0没有使用过")
    private Integer restraintZone;
    /**
     * 约束带使用时间
     */
    @ApiModelProperty(value="约束带使用时间")
    private LocalDateTime restraintZoneTime;
    /**
     * 管路滑脱时工作人员身边是否有人  1有人  0没人
     */
    @ApiModelProperty(value="管路滑脱时工作人员身边是否有人  1有人  0没人")
    private String ifSomeone;
    /**
     * 管道滑脱并发症
     */
    @ApiModelProperty(value="管道滑脱并发症")
    private String pipelineSlipComplication;
    /**
     * 并发症描述
     */
    @ApiModelProperty(value="并发症描述")
    private String complicationDescribe;
    /**
     * 是否使用过药物  1有  0没有
     */
    @ApiModelProperty(value="是否使用过药物  1有  0没有")
    private Integer medicine;
    /**
     * 药物名称
     */
    @ApiModelProperty(value="药物名称")
    private String medicineName;
    /**
     * 填表人
     */
    @ApiModelProperty(value="填表人")
    private String filler;
    /**
     * 填表日期
     */
    @ApiModelProperty(value="填表日期")
    private LocalDateTime fillerDate;
    /**
     * 科室
     */
    @ApiModelProperty(value="科室")
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
