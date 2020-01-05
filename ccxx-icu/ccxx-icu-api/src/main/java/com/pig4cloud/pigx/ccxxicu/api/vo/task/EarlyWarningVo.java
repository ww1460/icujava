package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.EarlyWarning;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EarlyWarningVo extends EarlyWarning {


	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 护士姓名
	 */
	private String nurseName;

	private String bedName;




}
