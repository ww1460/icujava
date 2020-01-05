package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: pigx
 * @description: 入量胶体
 * @author: yyj
 * @create: 2019-10-23 15:37
 **/
@Getter
@AllArgsConstructor
public enum InputDrugEnum {

	ONE(1, "羟乙基淀粉130/0.4氯化钠注射液"),
	TWO(1, "人血白蛋白"),
	THREE(1, "病毒灭活冰冻血浆"),
	FOUR(1, "破伤风免疫球蛋白"),
	FIVE(1, "静脉注射用人免疫球蛋白"),
	SIX(1, "去白细胞单采血小板"),
	SEVEN(1, "去白细胞悬浮红细胞"),
	EIGHT(1, "冷沉淀凝血因子");

	/**
	 * 类型
	 */
	private final Integer code;
	/**
	 * 描述
	 */
	private final String description;

}
