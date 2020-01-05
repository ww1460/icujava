package com.pig4cloud.pigx.ccxxicu.common.emums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DoctorsAdviceExtEnum {


	/**
	 * 医嘱执行情况
	 */
	UNEXECUTED(1, "未执行"),


	EXECUTION_IN_PROGRESS(2, "执行中"),


	REFUSING_TO_EXECUTE(3, "拒接执行"),


	COMPLETE(4, "执行完成"),


	BE_OVERDUE(5, "过期挂起"),


	NOT_EFFECTIVE(10, "未生效");


	private final Integer code;


	private final String description;






}
