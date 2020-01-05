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

package com.pig4cloud.pigx.ccxxicu.mapper.nurse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientInfoBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 患者和护士关联表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:08:26
 */
public interface NursePatientCorrelationMapper extends BaseMapper<NursePatientCorrelation> {


	/**
	 * 条件数据全查
	 * @return
	 */
	List<NursePatientCorrelationVo> selectAll(@Param("nursePatientCorrelation") NursePatientCorrelation nursePatientCorrelation);

	/**
	 * 通过患者id查责任护士
	 * @param patientId
	 * @return
	 */
	NursePatientCorrelation responsibleNurse(@Param("patientId") String patientId);


	/**
	 * 通过护士ID获取患者ID列表
	 * @param nurseId
	 * @return
	 */
	List<NursePatientCorrelationVo> selectPatientsByNurseId(@Param("nurseId") String nurseId);

	/**
	 * 用患者id 查询当前看护患者的护士   用于出科
	 * @param patientId
	 * @return
	 */
	List<NursePatientCorrelation> currentCarePatientNurse(@Param("patientId")String patientId);

	/**
	 * 查询对应的患者id
	 * @param patientInfoBo
	 * @return
	 */
	List<String> getPatientId(PatientInfoBo patientInfoBo);

	/**
	 * 获取在线的护士列表信息
	 * @return
	 */
	List<NursePatientCorrelation> selectOnlineNurse();

}
