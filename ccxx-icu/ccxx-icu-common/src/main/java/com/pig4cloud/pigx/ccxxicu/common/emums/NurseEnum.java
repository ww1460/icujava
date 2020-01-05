package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 护士枚举
 */
@Getter
@AllArgsConstructor
public enum NurseEnum {

	NURSE(0, "护士"),

	HEAD_NUESE(1, "护士长");

	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;

}
