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

package com.pig4cloud.pigx.ccxxicu.service.patient;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;

import java.util.List;

/**
 * 患者表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:17:11
 */
public interface PatientService extends IService<Patient> {

	/**
	 * 条件分页查询数据源
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage selectPaging (Page page, PatientBo patientBo);


	/**
	 * 在科患者分页
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage inSciencePatient (Page page, PatientBo patientBo);


	/**
	 * 可通过在科状态分别查询  在科  、入科中    、出科的患者-->
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage entryStateSelect (Page page, PatientBo patientBo );


	/**
	 * 查询在科患者不分页
	 * @param patientBo
	 * @return
	 */
	List<PatientVo> inSciencePatientAll(PatientBo patientBo);


	/**
	 * 出科患者分页
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage departurePatient (Page page, PatientBo patientBo);


	/**
	 * 查询30内出科患者
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage timeSelectDeparturePatient (Page page, PatientBo patientBo);


	/**
	 * 通过雪花id查询相应的数据
	 * @param patientId
	 * @return
	 */
	Patient getByPatientId(String patientId);

	/**
	 * 通过雪花id查询相应的数据【床位】
	 * @param patientId
	 * @return
	 */
	PatientVo patientIdSelect(String patientId);
	/**
	 * 通过病人的rfid查询到病人信息
	 * @param patientRfid
	 * @return
	 */
	Patient getByPatientRfid(String patientRfid);

	/**
	 * 通过病人的Id查询到护士rfid
	 * @param patientId
	 * @return
	 */
	String getNurseRfidByPatientId(String patientId);

	/**
	 * 通过his中的患者id查询数据
	 * @param hisId
	 * @return
	 */
	PatientVo getHisId(String hisId);


	/**
	 * 通过科室查询 每天新入科患者的
	 * @param dept
	 * @return
	 */
	Integer newDeptPatient(String dept);

	/**
	 * 通过科室查询 每天的在科患者
	 * @param dept
	 * @return
	 */
	Integer deptPatient(String dept);

	/**
	 * id全查数据
	 * @param id
	 * @return
	 */
	PatientVo idSelect(Integer id);

	/**
	 * his入科到我们患者表中未出科的患者
	 * @return
	 */
	List<Patient> hisEnterPatient();

}
