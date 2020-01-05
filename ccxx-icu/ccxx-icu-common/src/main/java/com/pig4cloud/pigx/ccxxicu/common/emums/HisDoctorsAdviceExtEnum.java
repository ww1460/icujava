package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医嘱执行次数记录表
 */
@Getter
@AllArgsConstructor
public enum HisDoctorsAdviceExtEnum {




	COMPLETE(4, "医嘱完成！");



	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;
}
