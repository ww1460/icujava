package com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateBo extends Template {

	/**
	 * 任务子模板
	 */
	private List<TemplateSubTask> templateSubTasks;

}
