package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务枚举
 */
@Getter
@AllArgsConstructor
public enum TaskEnum {



	TASK_STAET_END(-1, "任务状态 * 开始即为结束任务"),
	TO_BE_IMPLEMENTED(1, "任务状态 * 待执行"),
	TASK_RECEIVED(2, "任务状态 * 已接收"),
	COMPLETE(3, "任务状态 * 完成"),
	SUSPEND(4, "任务状态  *  暂停"),
	LEAVE_HOSPITAL(5, "任务状态 * 出科"),



	SHORT_TERM_DOCTORS_ADVICE(2,"短期医嘱"),
	LONG_TERM_DOCTORS_ADVICE(1,"长期医嘱"),


	//无
	BUILD_BY_ONESELF(1,"任务来源 * 自建"),
	//项目id
	PROJECT(2,"任务来源 * 项目"),
	// 模板
	TEMPLATE_BUILD_BY_ONESELF(3,"任务来源 * 模板自建"),
	// 模板  项目
	TEMPLATE_PROJECT(4,"任务来源 * 模板项目"),
	// 医嘱执行记录
	DOCTORS_ADVICE(5,"任务来源 * 医嘱执行记录"),
	/*  模板  医嘱  项目*/
	DOCTORS_ADVICE_PROJECT(6,"任务来源 * 医嘱项目"),
	//  模板  医嘱
	DOCTORS_ADVICE_BUILD_BY_ONESELF(7,"任务来源 * 医嘱自建"),
	//  模板 【长期任务】
	TEMPLATE(8,"任务来源 * 模板"),
	//模板快捷
	FAST(9,"任务来源 * 模板快捷"),
	 //模板 项目
	FAST_PROJECT(10,"任务来源 * 模板快捷项目"),
	//模板快捷自建
	FAST_BUILD_BY_ONESELF(11,"任务来源 *模板快捷自建"),
	// 医嘱内容id
	HIS_DOCTORS_ADVICE_PROJECT(13,"任务来源 * 医嘱内容id【提示医嘱】"),



	/**
	 * 任务状态
	 */
	TASK_COMMONLY(1,"一般任务"),
	TASK_URGENT_NEED(2,"需要加急"),//his没有加急任务
	TASK_URGENT(3,"紧急"),

	/**
	 * 任务预警
	 */
	TASK_REMIND(0,"超时任务提醒"),
	TASK_NO_REMIND(1,"超时任务不提醒"),

	/**
	 * 子任务还是主任务
	 */

	TASK_SON(0,"当前任务为子任务"),
	TASK_MAIN(1,"当前任务为主任务"),
	/**
	 * 执行类型【0  提醒     1是执行】
	 */
	REMIND(0,"当前任务的执行类型为 提醒类型任务"),
	IMPLEMENT(1,"当前任务的执行类型为 执行类型任务"),


	/**
	 * 后期删除
	 */

	/**
	 * 任务来源医嘱
	 */
	TASK_DOCTORS_ADVICE(1,"医嘱"),
	/**
	 * 任务来源护理
	 */
	TASK_NURSING(2,"护理"),
	/**
	 * 任务来源任务模板
	 */
	TASK_TEMPLATE(3,"任务模板"),

	/**
	 * 项目
	 */
	TASK_PROJECT(4,"项目");
















	/**
	 * 编号
	 */
	private final Integer number;
	/**
	 * 描述
	 */
	private final String description;

}
