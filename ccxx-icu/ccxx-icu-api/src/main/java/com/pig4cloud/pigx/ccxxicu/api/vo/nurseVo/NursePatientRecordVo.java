package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NursePatientRecordVo extends NursePatientRecord {

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 护士姓名
	 */
	private String nurseName;



}
