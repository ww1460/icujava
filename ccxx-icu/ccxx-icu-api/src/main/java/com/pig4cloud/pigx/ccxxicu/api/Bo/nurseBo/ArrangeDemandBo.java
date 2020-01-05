package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "排班需求")
public class ArrangeDemandBo {


	/**
	 * 当前排班时间
	 */
	@ApiModelProperty(value="当前排班时间")
	private LocalDateTime dateTime;
	/**
	 * 早班每等级的需求人数
	 */
	@ApiModelProperty(value="早班每等级的需求人数")
	private List<ShiftDemandVo> morningShift;
	/**
	 * 小夜班每等级的需求人数
	 */
	@ApiModelProperty(value="小夜班每等级的需求人数")
	private List<ShiftDemandVo> swingShift;
	/**
	 * 大夜班每等级的需求人数
	 */
	@ApiModelProperty(value="大夜班每等级的需求人数")
	private List<ShiftDemandVo> nightShift;
	/**
	 * 白班每等级的需求人数
	 */
	@ApiModelProperty(value="白班每等级的需求人数")
	private List<ShiftDemandVo> dayShift;

}
