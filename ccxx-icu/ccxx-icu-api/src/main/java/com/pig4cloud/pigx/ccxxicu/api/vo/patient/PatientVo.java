package com.pig4cloud.pigx.ccxxicu.api.vo.patient;

import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientVo extends Patient {

	/**
	 * 在科床位名称
	 */
	private String bedName;

	/**
	 * 出科床位
	 */
	private String admDedName;



}
