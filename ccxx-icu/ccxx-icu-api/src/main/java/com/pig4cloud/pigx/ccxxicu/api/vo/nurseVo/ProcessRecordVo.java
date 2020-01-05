package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ProcessRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessRecordVo extends ProcessRecord {

	/**
	 * 患者姓名
	 */
	@ApiModelProperty(value = "患者姓名")
	private String patientName;

	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value = "创建人姓名")
	private String createUserName;

	/**
	 * 完成人姓名
	 */
	@ApiModelProperty(value = "完成人姓名 ")
	private String completeNurseName;




}
