package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;


import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "床位表")
public class HospitalBedVo extends HospitalBed {

	/**
	 * 患者id
	 */
	private String patientId;

	private Patient patient;


}
