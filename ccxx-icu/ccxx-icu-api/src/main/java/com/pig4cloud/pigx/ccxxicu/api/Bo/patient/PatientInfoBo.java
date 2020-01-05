package com.pig4cloud.pigx.ccxxicu.api.Bo.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: pigx
 * @description: 查询患者的条件
 * @author: yyj
 * @create: 2019-09-30 15:43
 **/
@Data
@ApiModel(value = "查询患者的条件")
public class PatientInfoBo {

	/**
	 * 护士id
	 */
	@ApiModelProperty(value="护士id")
	private String nurseId;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
}
