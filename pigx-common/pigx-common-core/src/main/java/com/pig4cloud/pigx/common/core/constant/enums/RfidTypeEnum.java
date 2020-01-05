package com.pig4cloud.pigx.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 各个类型的RFID的枚举值
 */
@Getter
@AllArgsConstructor
public enum RfidTypeEnum {

	/**
	 * 护士RFID标签类型-N-Nurse
	 */
	N("N", "护士RFID标签类型"),

	/**
	 * 病人RFID标签类型-P-Patient
	 */
	P("P", "病人RFID标签类型"),

	/**
	 * 设备RFID标签类型-E-Equipment
	 */
	E("E", "设备RFID标签类型"),

	/**
	 * 药品RFID标签类型-M-Medicine
	 */
	M("M", "药品RFID标签类型"),

	/**
	 * 病床RFID标签类型-B-Bed
	 */
	B("B", "病床RFID标签类型"),

	/**
	 * 卡片电脑RFID标签类型-C-Computer
	 */
	C("C", "卡片电脑RFID标签类型");


	/**
	 * 标签RFID的类型
	 */
	private final String type;


	/**
	 * 标签类型的描述
	 */
	private final String description;

	public static RfidTypeEnum getFrom(String type) {
		for (RfidTypeEnum tmp : RfidTypeEnum.values()) {
			if (type.equals(tmp.getType())) {
				return tmp;
			}
		}
		return null;
	}
}
