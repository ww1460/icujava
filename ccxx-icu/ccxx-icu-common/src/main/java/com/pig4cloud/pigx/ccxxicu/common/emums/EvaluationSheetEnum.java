package com.pig4cloud.pigx.ccxxicu.common.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author bolei
 * @Date 2019/8/28 9:45
 * @Version 1.0
 * @Des 评估表枚举
 */

@Getter
@AllArgsConstructor
public enum EvaluationSheetEnum {

	/**
	 * 疼痛评估
	 */
	CPOT("CPOT", "疼痛评估"),
	/**
	 * 数字疼痛量表
	 */
	NRS("NRS", "数字疼痛量表"),

	/**
	 * 跌倒/坠床评估
	 */
	Morse("MORSE", "跌倒/坠床评估"),
	/**
	 * 导管滑脱风险评估
	 */
	CatheterSlippage("CATHETERSLIPPAGE", "导管滑脱风险评估"),

	/**
	 * 肌力评估
	 */
	MuscleForce("MUSCLEFORCE", "肌力评估"),

	/**
	 * 外出评估
	 */
	MEWS("MEWS", "外出评估"),
	/**
	 * 镇静、躁动评估
	 */
	RASS("RASS", "镇静、躁动评估"),

	/**
	 * 压疮评估
	 */
	Braden("BRADEN", "压疮评估"),
	/**
	 * 血栓评估
	 */
	Caprini("CAPRINI", "血栓评估"),
	/**
	 * 非计划性拔管风险评估
	 */
	UEX("UEX", "非计划性拔管风险评估"),

	/**
	 * 导管评估
	 */
	Catheter("CATHETER", "导管评估"),
	/**
	 * 尿路尿道感染检测评估
	 */
	Infection("INFECTION", "尿路尿道感染检测评估"),
	/**
	 * APACHE-II急性生理学评分标准
	 */
	APACHE("APACHE", "APACHE-II评分");


	/**
	 * 类型
	 */
	private final String code;
	/**
	 * 描述
	 */
	private final String description;

}
