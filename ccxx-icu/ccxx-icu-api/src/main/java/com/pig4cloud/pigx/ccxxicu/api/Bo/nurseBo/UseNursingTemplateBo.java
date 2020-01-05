package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @program: pigx
 * @description: 使用护理模板的条件
 * @author: yyj
 * @create: 2019-10-05 14:34
 **/
@Data
@ApiModel(value = "使用护理模板的条件")
public class UseNursingTemplateBo {

	private String templateCode;

	private String patientId;
	/**
	 * 是否为项目模板
	 */
	private Integer isProjectFlag;





}
