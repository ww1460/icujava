package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @program: pigx
 * @description: 项目的固定值
 * @author: yyj
 * @create: 2019-09-13 09:19
 **/
@Data
@ApiModel(value = "项目的固定值")
public class ProjectValueVo {

	private String projectId;

	private String projectCode;

	private String projectName;

	List<ProjectRecordValue> projectRecordValues;

}
