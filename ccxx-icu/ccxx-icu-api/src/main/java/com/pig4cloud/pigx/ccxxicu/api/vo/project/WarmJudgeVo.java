package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.pig4cloud.pigx.ccxxicu.api.entity.project.WarmJudge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士健康评估展示 HealthAnswerVo")
public class WarmJudgeVo extends WarmJudge {

	/**
	 * 项目名
	 */
	@ApiModelProperty(value = "项目名")
	private String projectName;
	/**
	 * 项目简称
	 */
	@ApiModelProperty(value = "项目简称")
	private String projectCode;




}
