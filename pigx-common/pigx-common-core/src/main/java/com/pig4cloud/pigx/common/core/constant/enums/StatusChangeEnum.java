package com.pig4cloud.pigx.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusChangeEnum {
	/**
	 * 1->1
	 * 一直不在
	 */
	A("11", 1),
	/**
	 * 1->0
	 * 不在变为在
	 */
	B("10", 2),
	/**
	 * 0->1
	 * 在变为不在
	 */
	C("01", 3),
	/**
	 * 0->0
	 * 一直在
	 */
	D("00", 4),
	;

	private String des;

	private Integer value;

	/**
	 * 由String 转换为枚举值
	 * @param des
	 * @return
	 */
	public static StatusChangeEnum getFrom(String des){
		for(StatusChangeEnum tmp: StatusChangeEnum.values()){
			if(des.equals(tmp.getDes())){
				return tmp;
			}
		}
		return null;
	}
}
