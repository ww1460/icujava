package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医嘱
 */
@Getter
@AllArgsConstructor
public enum HisDoctorsAdviceEnum {

	/**
	 * 医嘱状态
	 */
	COMMONLY(1,"一般任务"),
	URGENT_NEED(2,"需要加急"),
	URGENT(3,"紧急"),


	/**
	 * 长期医嘱  以校对已发送
	 * 临时医嘱  停止 医嘱频率已经生成
	 */
	STATE_DETERMINE("2l","确认（医生确认，让护士去校对当前医嘱）"),
	STATE_UNSENT("23","校对未发送"),
	STATE_HAS_BEEN_SENT("24","校对已发送"),
	STATE_TEMPORARY_STORAGE("25","暂停"),
	STATE_ENABLE("26","启用"),
	STATE_STOP("27","停止"),
	STATE_DEFINITE_STOP("28","确认停止"),
	STATE_NURSE_INVALID("29","护士作废"),
	STATE_DOCTOR_INVALID("30","医生作废"),


	SHORT_TERM_DOCTORS_ADVICE(2,"短期医嘱"),
	LONG_TERM_DOCTORS_ADVICE(1,"长期医嘱"),

	//医嘱类型
	HISDOCTORS_ADVICE_PROJECT_TYPE_NULL(null,"当前医嘱为【描述医嘱】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_1(1,"当前医嘱为【西药】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_2(2,"当前医嘱为【中成药】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_3(3,"当前医嘱为【中草药】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_4(4,"当前医嘱为 【卫生材料】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_5(5,"当前医嘱为 【普通材料】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_6(6,"当前医嘱为 【病历】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_7(7,"当前医嘱为 【床位】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_8(8,"当前医嘱为 【护理】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_9(9,"当前医嘱为 【挂号】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_10(10,"当前医嘱 【检查】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_11(11,"当前医嘱 【检验】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_12(12,"当前医嘱 【麻醉】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_13(13,"当前医嘱 【手术】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_14(14,"当前医嘱 【输血】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_15(15,"当前医嘱 【输氧】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_16(16,"当前医嘱 【体检】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_17(17,"当前医嘱 【治疗】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_18(18,"当前医嘱 【其他】"),
	HISDOCTORS_ADVICE_PROJECT_TYPE_19(19,"当前医嘱 【诊疗方案】"),

	TYPE_ZERO("0","没有传输"),
	TYPE_ONE("1","传输成功"),

	/**
	 * 药品单位
	 */
	DRUG_UNIT_L("l","药品单位升 L"),
	DRUG_UNIT_ML("ml","药品单位 毫升 ML");







	/**
	 * 类型
	 */
	private final Object code;
	/**
	 * 描述
	 */
	private final String description;

}
