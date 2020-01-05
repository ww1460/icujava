package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务模板枚举
 */
@Getter
@AllArgsConstructor
public enum TemplateEnum {

	/**
	 * 模板类型
	 */
	PROJECT(8, "自建时"),

	DOCTORS_ADVICE(5, "医嘱内容时"),
	FAST(9, "快捷内容时");

	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;
}
