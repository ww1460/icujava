package com.pig4cloud.pigx.ccxxicu.api.Bo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskBo extends Task{


	/**
	 * 项目可选值 【项目名称】
	 */
	private String recordValue;

	/**
	 * 数据标识【项目标识】
	 */
	private Integer dataFlag;

	/**
	 * 任务模板的来源id
	 */
	private String templateSourceItemsId;

	/**
	 * 护理记录单id  用于任务完成后的数据回填
	 */
	private String nursingRecordId;

}
