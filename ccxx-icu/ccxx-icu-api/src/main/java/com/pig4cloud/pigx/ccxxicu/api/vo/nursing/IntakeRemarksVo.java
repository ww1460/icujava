package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: pigx
 * @description: 出入量班次查询
 * @author: yyj
 * @create: 2019-10-24 11:07
 **/
@Data
@ApiModel(value = "出入量班次查询")
public class IntakeRemarksVo {

	private String projectId;

	private String projectCode;

	private String projectName;

	private Integer countNum;

	private String remarks;

	/**
	 * 出入量标识   0是出量 1是入量
	 */
	@ApiModelProperty(value="出入量标识   0是出量 1是入量")
	private Integer intakeOutputType;


}
