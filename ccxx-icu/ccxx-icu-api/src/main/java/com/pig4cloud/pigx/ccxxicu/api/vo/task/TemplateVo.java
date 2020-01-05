package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateVo extends Template {

	/**
	 * 登录护士id 用于条件查询时回填 当前登录的护士
	 */
	private String loginNurse;
	/**
	 *项目名称
	 */
	private String projectName;

	/**
	 护理项目id  用于查询数据
	 */
	private String projectId;


}
