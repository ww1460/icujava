package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士健康评估展示 HealthAnswerVo")
public class HealthAnswerVo extends HealthAnswer {


	/**
	 * 指标名称
	 */
	@ApiModelProperty(value = "指标名称")
	private String indicator;
	/**
	 * 指标描述
	 */
	@ApiModelProperty(value = "指标描述")
	private String indicatorScript;
	/**
	 * 护士姓名
	 */
	@ApiModelProperty(value = "护士姓名")
	private String nurseName;
	/**
	 * 护士长姓名
	 */
	@ApiModelProperty(value = "护士长姓名")
	private String matronName;



}
