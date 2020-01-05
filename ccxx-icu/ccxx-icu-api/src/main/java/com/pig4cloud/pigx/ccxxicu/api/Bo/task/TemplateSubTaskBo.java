package com.pig4cloud.pigx.ccxxicu.api.Bo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateSubTaskBo extends TemplateSubTask {

	// 患者id
	private String patientId;

	/**
	 * 任务的结果
	 */
	private String result;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="预开始时间")
	private LocalDateTime startTime;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="预结束时间")
	private LocalDateTime endTime;



}
