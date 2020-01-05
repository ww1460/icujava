package com.pig4cloud.pigx.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 卡片电脑和icu后端的交互场景
 */
@Getter
@AllArgsConstructor
public enum ScenesActionEnum {
	/**
	 * 护士、病人、药品、设备等有任一状态发生变化
	 */
	COMMON("common", "任一状态变化"),
	/**
	 * 重症监护室用药
	 */
	MEDICATION("medication", "重症监护室用药"),
	/**
	 * 制药室制药
	 */
	PHARMACY("pharmacy", "制药室制药"),
	/**
	 * 医疗垃圾回收
	 */
	MEDICALWASTE("medicalwaste", "医疗垃圾回收"),
	/**
	 * 病人状态变化
	 */
	PMOVE("pmove", "病人状态变化"),
	/**
	 * 设备变化
	 */
	EQUIPMENT("equipment", "设备状态变化"),
	/**
	 * 用药结束
	 */
	MMOVE("mmove", "药品状态变化"),
	;

	/**
	 * 标签RFID的类型
	 */
	private final String type;


	/**
	 * 标签类型的描述
	 */
	private final String description;
}
