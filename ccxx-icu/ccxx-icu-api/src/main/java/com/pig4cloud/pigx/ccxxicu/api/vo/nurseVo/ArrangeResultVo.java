package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "排班结果展示")
public class ArrangeResultVo extends ArrangeResult {

	/**
	 * 护士姓名
	 */
	@ApiModelProperty(value="护士姓名")
	private String nurseName;





}
