package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "护理记录分页查询条件")
public class NursingRecordBo {


	/**
	 * 护士id
	 */
	@ApiModelProperty(value = "护士id")
	private String nurseId;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value = "科室id")
	private String deptId;


	/**
	 * 创建人id
	 */
	@ApiModelProperty(value = "创建人id")
	private String createUserId;


	/**
	 * 患者id
	 */
	@ApiModelProperty(value = "患者id")
	private String patientId;


	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private String createTime;

	/**
	 * 数据来源
	 */
	@ApiModelProperty(value = "数据来源")
	private String source;

	/**
	 * 数据范围
	 */
	@ApiModelProperty(value="数据范围 0关联患者的数据  1 为创建人的数据")
	private Integer range;






}
