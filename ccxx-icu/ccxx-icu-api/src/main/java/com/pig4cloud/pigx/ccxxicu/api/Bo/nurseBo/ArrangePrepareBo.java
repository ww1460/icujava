package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "排班人员表")
public class ArrangePrepareBo extends Nurse {


	/**
	 * 参与排班人员
	 */
	@ApiModelProperty(value="参与排班人员")
	private List<Nurse> nurseList;

	/**
	 * 排班的开始时间
	 */
	@ApiModelProperty(value="排班的开始时间")
	private LocalDateTime startTime;



}
