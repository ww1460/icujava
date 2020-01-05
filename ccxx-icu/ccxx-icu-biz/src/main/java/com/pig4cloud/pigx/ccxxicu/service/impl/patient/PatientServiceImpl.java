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
package com.pig4cloud.pigx.ccxxicu.service.impl.patient;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import com.pig4cloud.pigx.ccxxicu.mapper.patient.PatientMapper;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 患者表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:17:11
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {


	@Autowired
	private PatientMapper patientMapper;


	/**
	 * 条件分页查询数据源
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, PatientBo patientBo) {
		return patientMapper.selectPaging(page,patientBo);
	}

	/**
	 * 在科患者分页查询
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@Override
	public IPage inSciencePatient(Page page, PatientBo patientBo) {
		return patientMapper.inSciencePatient(page,patientBo);
	}

	/**
	 * 可通过在科状态分别查询  在科 、入科中    、出科的患者-->
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@Override
	public IPage entryStateSelect(Page page, PatientBo patientBo) {

		return patientMapper.entryStateSelect(page,patientBo);
	}

	/**
	 * 在科患者不分页
	 * @param patientBo
	 * @return
	 */
	@Override
	public List<PatientVo> inSciencePatientAll(PatientBo patientBo) {
		return patientMapper.inSciencePatientAll(patientBo);
	}

	/**
	 * 出科患者分页查询
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@Override
	public IPage departurePatient(Page page, PatientBo patientBo) {
		patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");//查询当前科室的出科患者
		return patientMapper.departurePatient(page,patientBo);
	}

	/**
	 * 通过雪花id查询相应的数据
	 * @param patientId
	 * @return
	 */
	@Override
	public Patient getByPatientId(String patientId) {
		return patientMapper.getByPatientId(patientId);
	}

	@Override
	public Patient getByPatientRfid(String patientRfid) {
		return patientMapper.getByPatientRfid(patientRfid);
	}

	@Override
	public String getNurseRfidByPatientId(String patientId) {
		return patientMapper.getNurseRfidByPatientId(patientId);
	}

	/**
	 * his中的患者id 查询数据
	 * @param hisId
	 * @return
	 */
	@Override
	public PatientVo getHisId(String hisId) {

		return patientMapper.getHisId(hisId);
	}

	/**
	 * 查询30内出科患者
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@Override
	public IPage timeSelectDeparturePatient(Page page, PatientBo patientBo) {
		/**
		 * 当前时间
		 */
		LocalDateTime startDay = LocalDateTime.now();
		/**
		 * 当前时间减去30天前的时间
		 */
		LocalDateTime endDay = startDay.minus( 30, ChronoUnit.DAYS );
		patientBo.setDischargeStartTime(endDay);  //开始时间
		patientBo.setDischargeEndTime(startDay);  // 结束时间
		return patientMapper.departurePatient(page,patientBo);
	}

	/**
	 * * 通过科室查询 每天新入科患者的
	 * @param
	 * @return
	 */
	@Override
	public Integer newDeptPatient(String dept) {

		return patientMapper.newDeptPatient(dept);
	}

	/**
	 * 通过科室查询 每天的在科的患者
	 * @param dept
	 * @return
	 */
	@Override
	public Integer deptPatient(String dept) {
		return patientMapper.deptPatient(dept);
	}

	/**
	 * id全查数据
	 * @param id
	 * @return
	 */
	@Override
	public PatientVo idSelect(Integer id) {
		return patientMapper.idSelect(id);
	}

	/**
	 * 通过雪花id查询相应的数据【床位】
	 * @param patientId
	 * @return
	 */
	@Override
	public PatientVo patientIdSelect(String patientId) {
		return patientMapper.patientIdSelect(patientId);
	}

	/**
	 * his入科到我们患者表中未出科的患者
	 * @return
	 */
	@Override
	public List<Patient> hisEnterPatient() {
		return patientMapper.hisEnterPatient();
	}
}
