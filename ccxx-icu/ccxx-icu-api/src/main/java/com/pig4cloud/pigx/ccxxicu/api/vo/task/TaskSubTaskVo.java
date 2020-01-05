package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskSubTaskVo extends TaskSubTask {

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 完成护士姓名
	 */
	private String completedNurse;

	/**
	 * 接收护士姓名
	 */
	private String receptionNurse;

	/**
	 * 责任护士
	 */
	private String dutyNurseName;

	/**
	 * 登录护士
	 */
	private String associatedNurse;



}
