package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: pigx
 * @description: 护理记录、项目记录等数据的来源枚举
 * @author: yyj
 * @create: 2019-09-20 10:38
 **/
@Getter
@AllArgsConstructor
public enum DataSourceEnum {

	NURSING_TEMPLATE("1", "护理模板"),

	WINDOWS_ENTER("2", "悬浮窗"),

	DEVICE("3", "设备"),

	TASK("4", "任务"),

	ASSESS("5", "评估"),

	ILLNESS_NURSING("6", "病情护理"),

	PROJECT("7", "项目记录"),

	INTAKE_OUTPUT("8", "出入量");


	/**
	 * 类型
	 */
	private final String code;
	/**
	 * 描述
	 */
	private final String description;


}
