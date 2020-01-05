package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateSubTaskVo extends TemplateSubTask {
	/**
	 * 登录护士id 用于条件查询时回填 当前登录的护士
	 */
	private String loginNurse;

	/**
	 * 任务主模板的使用范围
	 */
	private String purpose;

	/**
	 * 科室id
	 */
	private String deptId;

	/**
	 * 任务名称
	 */
	private String taskName;

}
