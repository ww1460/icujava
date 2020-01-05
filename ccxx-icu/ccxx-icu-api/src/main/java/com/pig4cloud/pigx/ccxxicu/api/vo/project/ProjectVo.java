package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 项目展示
 * @author: yyj
 * @create: 2019-10-17 17:48
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者项目预警展示 ProjectWarmVo")
public class ProjectVo extends Project {

	/**
	 * 有值代表选中  无值则不选中
	 */
	private String pitchOn;


}
