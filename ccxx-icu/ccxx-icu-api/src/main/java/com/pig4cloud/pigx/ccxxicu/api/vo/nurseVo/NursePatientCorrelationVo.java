package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NursePatientCorrelationVo extends NursePatientCorrelation {

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 患者信息
	 */
	private Patient patient;


	/**
	 * 护士姓名
	 */
	private String nurseName;

	/**
	 * 床位
	 */
	private String bedName;
}
