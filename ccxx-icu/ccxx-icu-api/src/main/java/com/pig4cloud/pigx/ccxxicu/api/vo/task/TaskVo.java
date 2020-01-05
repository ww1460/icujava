package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskVo extends TaskSubTask {

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 完成护士姓名
	 */
	private String nurseCompletedName;

	/**
	 * 关联护士
	 */
	private String associatedNurse;


	/**
	 * 任务完成状态  0为未接受   1已接收  2 执行中  3完成  4停止（因为出院 、或者转科时）
	 */
	@ApiModelProperty(value="任务完成状态  0为未接受   1已接收  2 执行中  3完成  4停止（因为出院 、或者转科时）")
	private Integer state;











}
