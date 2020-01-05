/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.ccxxicu.service.nurse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientInfoBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientInfo;

import java.util.List;

/**
 * 患者和护士关联表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:08:26
 */
public interface NursePatientCorrelationService extends IService<NursePatientCorrelation> {


	/**
	 * 条件数据全查
	 * @param nursePatientCorrelation
	 * @return
	 */
	List<NursePatientCorrelationVo> selectAll(NursePatientCorrelation nursePatientCorrelation);

	/**
	 * 查询患者的责任护士
	 * @param patientId
	 * @return
	 */
	String selectDutyNurseId(String patientId);

	/**
	 * 利用患者id，查询看护当前患者的护士数据 用于出科
	 * @param patientId
	 * @return
	 */
	Boolean stopNursePatient(String patientId);

	/**
	 * 查询对应的患者id
	 * @param patientInfoBo
	 * @return
	 */
	List<PatientInfo> getPatientId(PatientInfoBo patientInfoBo);


	PatientInfo selectPatientId(String patientId);

}
