package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: pigx
 * @description: 床位的使用状态
 * @author: yyj
 * @create: 2019-09-20 10:38
 **/
@Getter
@AllArgsConstructor
public enum BedUseEnum {


	NOT_IN_USE(0, "未使用"),

	IN_USE(1, "使用中"),

	IN_MAINTENANCE(2, "维修中");


	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;

}
