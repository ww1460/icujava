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

package com.pig4cloud.pigx.ccxxicu.mapper.patient;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.PatientBedCorrelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 患者与床位关联表
 *
 * @author pigx code generator
 * @date 2019-08-08 14:09:53
 */
public interface PatientBedCorrelationMapper extends BaseMapper<PatientBedCorrelation> {

	PatientBedCorrelation selectByCondition(PatientBedCorrelation record);

	@Select("SELECT * FROM pat_patient_bed_correlation WHERE bed_rfid = #{rfidId}")
	PatientBedCorrelation selectByRfidId(@Param("rfidId") String rfidId);

	/**
	 * 根据患者查询床位信息
	 * @param patientId
	 * @return
	 */
	HospitalBed getPatientBed(@Param("patientId") String patientId);

}
