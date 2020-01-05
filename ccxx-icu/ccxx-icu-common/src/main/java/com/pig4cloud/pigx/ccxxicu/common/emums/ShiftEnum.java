package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 班次枚举
 */
@Getter
@AllArgsConstructor
public enum ShiftEnum {



	MORNING_SHIFT(1, "早班"),

	SWING_SHIFT(2, "小夜班"),

	NIGHT_SHIFT(3, "大夜班"),

	DAY_SHIFT(4, "白班"),

	REST_SHIFT(5, "休班"),

	MISSED_REST_SHIFT(6, "补休"),

	HOST_SHIFT(7, "主"),

	AUXILIARY_SHIFT(8, "辅助"),

	MANAGER_SHIFT(9, "总管");


	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;






}
