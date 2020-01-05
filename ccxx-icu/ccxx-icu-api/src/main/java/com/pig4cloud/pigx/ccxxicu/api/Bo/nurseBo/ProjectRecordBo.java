package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "项目记录分页查询条件")
public class ProjectRecordBo {
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 护士id
	 */
	@ApiModelProperty(value="护士id")
	private String nurseId;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;


	/**
	 * 数据范围
	 */
	@ApiModelProperty(value="数据范围 0关联患者的数据  1 为创建人的数据")
	private Integer range;

}
