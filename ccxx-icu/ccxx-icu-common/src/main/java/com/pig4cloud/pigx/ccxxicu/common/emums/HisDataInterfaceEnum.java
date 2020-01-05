package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * his接口枚举
 */
@Getter
@AllArgsConstructor
public enum HisDataInterfaceEnum {


	/**
	 * 接口路径
	 */

	HIS_DEPT("http://10.192.12.241:30001/HIS/GetIcuDept","his的科室接口"),

	HIS_PATIENT("http://10.192.12.241:30001/HIS/GetIcuPatientInfo ","his的患者接口"),

	HIS_DOCTORS_ADVICE("http://10.192.12.241:30001/HIS/GetIcuAdviceInfos","his的医嘱接口"),

	HIS_NURSE("http://10.192.12.241:30001/HIS/GetIcuUser","his的护士接口"),

	DOCTORS_ADVICE_EXT("http://10.192.12.241:30001/HIS/GetIcuAdviceExecInfo","his的请求医嘱扩展【拆分后的医嘱】接口"),

	DOCTORS_ADVICE_EXT_UPDATE("http://10.192.12.241:30001/HIS/IcuExecAdvice","his的请求修改医嘱扩展【拆分后的医嘱】接口"),


	/**
	 * 请求数据时的标识
	 */
	PATIENT_Db("cs","请求患者时给his传的标识"),

	DEPT_Db("cs","请求科室时给his传的标识"),

	NURSE_Db("cs","请求护士时给his传的标识"),

	DOCTORS_ADVICE_Db("cs","请求医嘱时给his传的标识"),

	DOCTORS_ADVICE_EXT_Db("cs","请求医嘱扩展【拆分后的医嘱】给his传的标识"),

	DOCTORS_ADVICE_EXT_UPDATE_Db("cs","请求医嘱扩展【拆分后的医嘱】修改时 给his传的标识");









	/**
	 * 类型
	 */
	private final String code;
	/**
	 * 描述
	 */
	private final String description;

}
