package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TimingExecution;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimingExecutionVo extends TimingExecution {

	/**
	 * 任务模板 名称
	 */
	private String templateName;

	/**
	 * 任务描述
	 */
	private String templateDescribe;

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 护士姓名
	 */
	private String nurseName;



}
