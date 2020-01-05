package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.NursingTaskRelation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NursingTaskRelationVo extends NursingTaskRelation {

	/**
	 * 任务内容
	 */
	private String templateContent;

	/**
	 * 任务描述
	 */
	private String templateDescribe;

	/**
	 * 任务描述
	 */
	private String taskResult;

	/**
	 * 任务模板id
	 */
	private String templateId;
	/**
	 * 任务模板来源
	 */
	private String sourceItemsId;
}
