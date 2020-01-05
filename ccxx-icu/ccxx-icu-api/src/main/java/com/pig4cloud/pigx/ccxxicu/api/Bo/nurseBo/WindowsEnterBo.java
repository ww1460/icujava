package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "悬浮窗录入数据")
public class WindowsEnterBo {


	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 录入内容
	 */
	@ApiModelProperty(value="录入内容")
	private String enterContent;


	/**
	 *模板项目及数据
	 */
	@ApiModelProperty(value="是否进入护理记录  0是  1否")
	private Integer inNursingFlag = 1;

}
