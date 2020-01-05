package com.pig4cloud.pigx.ccxxicu.common.emums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 护士与患者关联
 */
@Getter
@AllArgsConstructor
public enum NursePatientRecordEnum {

	RESPONSIBLE_NURSE(0, "责任护士"),

	ORDINARY_NUESE(1, "普通护士");

	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;



}
