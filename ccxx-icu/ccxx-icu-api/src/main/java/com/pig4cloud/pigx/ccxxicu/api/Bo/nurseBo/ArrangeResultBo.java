package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询排班结果条件")
public class ArrangeResultBo extends ArrangeResult {

	/**
	 * 时间区间 标识
	 * 1 目标当天   2 目标所在周期  3 目标所在月
	 */
	@ApiModelProperty(value="时间区间标识，1:目标当天;2:目标所在周期;3:目标所在月")
	private Integer scope;
	/**
	 * 护士等级 1-5
	 */
	@ApiModelProperty(value="护士等级 1-5")
	private Integer nurseGrade;
	/**
	 * 护士姓名
	 */
	@ApiModelProperty(value="护士姓名")
	private String nurseName;

}
