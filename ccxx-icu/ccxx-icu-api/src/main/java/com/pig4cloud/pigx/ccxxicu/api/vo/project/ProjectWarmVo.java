package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectWarm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者项目预警展示 ProjectWarmVo")
public class ProjectWarmVo extends ProjectWarm {

	/**
	 * 项目名/简称
	 */
	@ApiModelProperty(value = "项目名/简称")
	private String projectName;
	/**
	 * 护士id
	 */
	@ApiModelProperty(value = "护士id")
	private String nurseId;
	/**
	 * 患者名
	 */
	@ApiModelProperty(value = "患者名")
	private String patientName;
	/**
	 * 床位名/编号
	 */
	@ApiModelProperty(value = "床位名/编号")
	private String bedCode;

	/**
	 * 床位id
	 */
	@ApiModelProperty(value = "床位id")
	private String bedId;
	/**
	 * 查询的开始时间
	 */
	@ApiModelProperty(value = "查询的开始时间")
	private LocalDateTime startTime;
	/**
	 * 查询的结束时间
	 */
	@ApiModelProperty(value = "查询的开始时间")
	private LocalDateTime endTime;



}
