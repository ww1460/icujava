package com.pig4cloud.pigx.ccxxicu.api.entity.websocket;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态枚举
 */
@Getter
@AllArgsConstructor
public enum TaskStateEnum {
	//1待执行  2 执行中  3完成  4停止（因为出院 、或者转科时） 5患者出科
	WAIT("1", "待执行"),
	EXECUTING("2", "执行中"),
	COMPLETE("3", "完成"),
	STOP("4", "停止（因为出院 、或者转科时）"),
	FINISH("5", "患者出科"),
	;

	private String code;

	private String des;

	/**
	 * 由String 转换为枚举值
	 *
	 * @param code
	 * @return
	 */
	public static TaskStateEnum getFrom(String code) {
		for (TaskStateEnum tmp : TaskStateEnum.values()) {
			if (code.equals(tmp.getCode())) {
				return tmp;
			}
		}
		return null;
	}

}
