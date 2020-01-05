package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 患者科室
 */
@Getter
@AllArgsConstructor
public enum PatientEnum {


	/**
	 * 患者状态
	 */
	ENROLLED_EN_ROUTE(1, "入科中"),

	WAITING_FOR_ADMISSION(0, "待入科"),

	IN_SCIENCE(2, "在科"),

	DEPARTURE_MIDDLE(4, "出科中"),

	DEPARTURE(3, "出科"),

	/**
	 * 出院、自动出院
	 *
	 * 死亡
	 *
	 * 处次之外都是转科
	 */

	AAA(1, " 出院、自动出院"),
	BBB(2, "死亡"),
	CCC(3, "其他都是转科");







	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;










}
