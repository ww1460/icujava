package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: pigx
 * @description: 出入量
 * @author: yyj
 * @create: 2019-09-23 09:26
 **/
@Getter
@AllArgsConstructor
public enum  IntakeOutputEnum {

	OUTPUT(0, "出量"),

	INTAKE(1, "入量");


	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;



}
