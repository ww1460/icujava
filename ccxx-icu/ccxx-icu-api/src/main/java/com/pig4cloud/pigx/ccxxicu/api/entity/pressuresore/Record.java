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
 * 压疮记录表 
 *
 * @author pigx code generator
 * @date 2019-08-30 16:19:42
 */
@Data
@TableName("pre_sore_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "压疮记录表 ")
public class Record extends Model<Record> {
private static final long serialVersionUID = 1L;

    /**
     * 压疮记录表 
     */
    @TableId
    @ApiModelProperty(value="压疮记录表 ")
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
     * 性别 
     */
    @ApiModelProperty(value="性别 ")
    private Integer gender;
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
     * 评估结果
     */
    @ApiModelProperty(value="评估结果")
    private String assessmentResults;
    /**
     * 压疮分期
     */
    @ApiModelProperty(value="压疮分期")
    private String byStages;

	/**
	 * 压疮来源   1是院外带入 0当前发生
	 */
	@ApiModelProperty(value="压疮来源   1是院外带入 0当前发生")
	private String preSource;

	/**
	 * 发生压疮时间
	 */
	@ApiModelProperty(value="发生压疮时间")
	private LocalDateTime occurrenceTime;

	/**
     * 压疮部位id 字典表
     */
    @ApiModelProperty(value="压疮部位id 字典表")
    private String prePosition;
    /**
     * 局部部位照片
     */
    @ApiModelProperty(value="局部部位照片")
    private String skinConditionPicture;
    /**
     * 局部皮肤情况
     */
    @ApiModelProperty(value="局部皮肤情况")
    private String skinCondition;
    /**
     * 范围（长*宽*深）
     */
    @ApiModelProperty(value="范围（长*宽*深）")
    private String soreRange;
    /**
     * 皮肤颜色
     */
    @ApiModelProperty(value="皮肤颜色")
    private String skinColor;
    /**
     * 渗出液体
     */
    @ApiModelProperty(value="渗出液体")
    private String exudate;
    /**
     * 周围皮肤情况
     */
    @ApiModelProperty(value="周围皮肤情况")
    private String skinSituation;
    /**
     * 局部处理
     */
    @ApiModelProperty(value="局部处理")
    private String localProcessing;
    /**
     * 敷料粘贴是否良好(0良好     1不好)
     */
    @ApiModelProperty(value="敷料粘贴是否良好(0良好     1不好)")
    private Integer dressingPasteIfGood;
    /**
     * 敷料表面有无渗液  ( 0 没有     1 有)
     */
    @ApiModelProperty(value="敷料表面有无渗液  ( 0 没有     1 有)")
    private Integer ifExudate;
    /**
     * 防范措施
     */
    @ApiModelProperty(value="防范措施")
    private String preventiveMeasures;
    /**
     * 护理措施
     */
    @ApiModelProperty(value="护理措施")
    private String nursingMeasures;
    /**
     * 记录人
     */
    @ApiModelProperty(value="记录人")
    private String noteTaker;
    /**
     * 记录时间
     */
    @ApiModelProperty(value="记录时间")
    private LocalDateTime noteTime;
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
