package com.pig4cloud.pigx.ccxxicu.common.emums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author bolei
 * @Date 2019/10/07 14:02
 * @Version 1.0
 * @Des his编号 用户匹配his跟icu系统接口交互的逻辑（患者和医嘱）
 */

@Getter
@AllArgsConstructor
public enum HisCodeEnum {

	DISCONTINUE_USE("zzsl", "枣庄市立医院");

	/**
	 * 类型
	 */
	private final String code;
	/**
	 * 描述
	 */
	private final String description;

}
