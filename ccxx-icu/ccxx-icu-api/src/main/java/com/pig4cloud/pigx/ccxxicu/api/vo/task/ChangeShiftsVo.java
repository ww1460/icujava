package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import lombok.Data;

@Data
public class ChangeShiftsVo {

	/*  任务表中描述*/

	/**
	 * 任务id
	 */
	private String id;

	/**
	 * 子任务雪花
	 */
	private String taskSubTaskId;

	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务描述
	 */
	private String taskDescribe;

	/**
	 * 患者id
	 */
	private String patientId;


	/**
	 * 任务状态
	 */
	private String taskState;

	/***
	 * 临时交接班的任务状态描述
	 */
	private String temporaryTaskDescribe;




	/**
	 * 登录护士id 用于条件查询时回填 当前登录的护士
	 */
	private String associatedNurse;


	/**
	 *患者姓名
	 */
	private String patientName;

	/**
	 * 交班护士姓名
	 */
	private String shiftNurseName;

	/**
	 * 交班护士姓名
	 */
	private String successionNurseName;


}
