package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备枚举类型
 */
@Getter
@AllArgsConstructor
public enum DeviceEnum {



	NOT_USED(2, "未使用"),

	IN_USE(1, "使用中"),
	DISCONTINUE_USE(0, "停用");

	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;



}
