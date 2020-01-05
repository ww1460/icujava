package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "评估展示条件")
public class AssessShowBo {

	/**
	 * 评估简称
	 */
	@ApiModelProperty(value="评估简称")
	private String projectCode;

	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;

}
