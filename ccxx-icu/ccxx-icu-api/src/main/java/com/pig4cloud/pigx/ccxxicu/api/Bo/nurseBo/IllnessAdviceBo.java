package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: pigx
 * @description: 护理措施分页查询条件
 * @author: yyj
 * @create: 2019-09-09 19:08
 **/
@Data
@ApiModel(value = "护理措施的分页查询条件")
public class IllnessAdviceBo {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String illnessProjectId;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;




}
