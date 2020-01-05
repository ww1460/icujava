package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.Entrust;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntrustVo extends Entrust {

	/**
	 * 发起人姓名
	 */
	private String originatorNurseName;
	/**
	 * 接收患者姓名
	 */
	private String recipientNurseName;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务描述
	 */
	private String taskDescribe;

}
