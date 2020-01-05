package com.pig4cloud.pigx.ccxxicu.api.vo.patient;

import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: pigx
 * @description: 患者的详细信息
 * @author: yyj
 * @create: 2019-09-30 15:36
 **/
@Data
@ApiModel(description = "患者的详细信息")
public class PatientInfo {

	private Integer index;
	/**
	 * 患者基本信息
	 */
	@ApiModelProperty(value="患者基本信息")
	private Patient patient;

	/**
	 * 患者的床位信息
	 */
	@ApiModelProperty(value="患者的床位信息")
	private HospitalBed hospitalBed;

	/**
	 * 患者的责任护士信息
	 */
	@ApiModelProperty(value="患者的责任护士信息")
	private Nurse nurse;

	/**
	 * 患者的辅助护士信息
	 */
	@ApiModelProperty(value="患者的辅助护士信息")
	private List<Nurse> nurseList;

	private String patientIndex;




}
