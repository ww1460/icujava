package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: pigx
 * @description: 项目类型枚举
 * @author: yyj
 * @create: 2019-09-20 10:38
 **/
@Getter
@AllArgsConstructor
public enum ProjectTypeEnum {

	VITAL_SIGN_PROJECT(1, "生命体征"),

	OUTPUT_PROJECT(2, "出量"),

	INTAKE_PROJECT(3, "入量"),

	ASSESS_PROJECT(4, "评估"),

	ILLNESS_PROJECT(5, "病情护理"),

	NURSING_PROJECT(6, "护理项目"),

	TYPE_PROJECT(7, "护理类型"),
	TONG_KONG(8, "瞳孔");


	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;


}
