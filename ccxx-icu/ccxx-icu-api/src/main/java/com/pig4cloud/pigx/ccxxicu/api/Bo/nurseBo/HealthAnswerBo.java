package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士健康评估查询条件 HealthAnswerBo")
public class HealthAnswerBo extends HealthAnswer {
	/**
	 * 查询的开始时间
	 */
	@ApiModelProperty(value = "查询的开始时间")
	private LocalDateTime startTime;
	/**
	 * 查询的结束时间
	 */
	@ApiModelProperty(value = "查询的结束时间")
	private LocalDateTime endTime;
	/**
	 * 护士姓名
	 */
	@ApiModelProperty(value = "护士姓名")
	private String realName;





}
