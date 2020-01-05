package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: pigx
 * @description: 病情护理的状态
 * @author: yyj
 * @create: 2019-09-23 09:24
 **/
@Getter
@AllArgsConstructor
public enum  IllnessStateEnum {


	NORMAL(0, "正常"),

	ABNORMAL(1, "异常");



	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;





}
