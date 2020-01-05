package com.pig4cloud.pigx.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionEnum {
	/**
	 * 护士、病人、药品、设备等有任一状态发生变化
	 */
	PHARMACY("pharmacy", 1),
	/**
	 * 重症监护室用药
	 */
	ICU("icu", 2),
	/**
	 * 制药室制药
	 */
	DRUG_RECOVERY("drugRecovery", 3),
	;

	private String des;

	private Integer value;

	/**
	 * 由String 转换为枚举值
	 * @param des
	 * @return
	 */
	public static PositionEnum getFrom(String des){
		for(PositionEnum tmp: PositionEnum.values()){
			if(des.equals(tmp.getDes())){
				return tmp;
			}
		}
		return null;
	}
}
