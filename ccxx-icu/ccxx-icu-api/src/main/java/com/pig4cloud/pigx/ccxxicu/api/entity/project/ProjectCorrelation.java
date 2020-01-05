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

package com.pig4cloud.pigx.ccxxicu.api.entity.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目标识表(主要是对护理单一二作关联)
 *
 * @author pigx code generator
 * @date 2019-09-13 16:11:58
 */
@Data
@TableName("nur_project_correlation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目标识表(主要是对护理单一二作关联)")
public class ProjectCorrelation extends Model<ProjectCorrelation> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;
    /**
     * 护理报表标识 1:护理单一 2：护理单二 3:生命体征
     */
    @ApiModelProperty(value="护理报表标识 1:护理单一 2：护理单二 3:生命体征")
    private Integer nursingReportFlag;
    /**
     * 科室id
     */
    @ApiModelProperty(value="科室id")
    private String deptId;

	/**
	 * 展示形式 0 固定值编号  1 是否完成  2  具体数据
	 */
	@ApiModelProperty(value="展示形式 0 固定值编号  1 是否完成  2  具体数据")
	private Integer showWayFlag;

	/**
	 * 展示顺序
	 */
	@ApiModelProperty(value="展示顺序")
	private Integer showIndex;



    }
