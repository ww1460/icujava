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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 患者表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:17:11
 */
public interface PatientMapper extends BaseMapper<Patient> {

	/**
	 * 条件分页查询数据源
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage<List<PatientVo>> selectPaging (Page page, @Param("patientBo") PatientBo patientBo );


	/**
	 * 可通过在科状态分别查询  在科 、入科中 、出科的患者
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage<List<PatientVo>> entryStateSelect (Page page, @Param("patientBo") PatientBo patientBo );



	/**
	 * 在科患者分页
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage<List<PatientVo>> inSciencePatient (Page page, @Param("patientBo") PatientBo patientBo );

	/**
	 * 出科患者分页
	 * @param page
	 * @param patientBo
	 * @return
	 */
	IPage<List<PatientVo>> departurePatient (Page page, @Param("patientBo") PatientBo patientBo );


	/**
	 * 查询在科患者不分页
	 * @param patientBo
	 * @return
	 */
	List<PatientVo> inSciencePatientAll(@Param("patientBo") PatientBo patientBo);

	/**
	 * 通过雪花id查询相应的数据
	 * @param patientId
	 * @return
	 */
	Patient getByPatientId(@Param("patientId") String patientId);

	/**
	 * 通过雪花id查询相应[包含床位]
	 * @param patientId
	 * @return
	 */
	PatientVo patientIdSelect(@Param("patientId") String patientId);

	/**
	 * 通过病人rfid查询相应的数据
	 * @param rfidId
	 * @return
	 */
	@Select("select * from pat_patient where rfid_id = #{rfidId}")
	Patient getByPatientRfid(@Param("rfidId") String rfidId);

	/**
	 * 通过his中的患者id查询数据
	 * @param hisId
	 * @return
	 */
	PatientVo getHisId(@Param("hisId")String hisId);

	@Select("SELECT nurse_rfid FROM nur_nurse_patient_correlation a LEFT JOIN nur_nurse b ON a.nurse_id=b.nurse_id  WHERE patient_id=#{patientId}")
	String getNurseRfidByPatientId(@Param("patientId") String patientId);

	/**
	 * 通过科室查询 每天新入科患者的
	 * @param dept
	 * @return
	 */

	@Select("SELECT COUNT(id) from pat_patient WHERE del_flag =0 and admission_department = #{dept} and DATE_FORMAT(entrance_time,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d')")
	Integer newDeptPatient(@Param("dept") String dept);

	/**
	 * 通过科室查询当条在科的患者
	 * @param dept
	 * @return
	 */
	@Select("SELECT COUNT(id) FROM pat_patient WHERE del_flag =0 and admission_department = #{dept} and discharge_time is null or DATE_FORMAT(discharge_time,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d')")
	Integer deptPatient(@Param("dept")String dept);

	/**
	 * id查询数据源
	 * @param id
	 * @return
	 */
	@Select("select\n" +
			"\t\tpat.id,\n" +
			"\t\tpat.patient_id,\n" +
			"\t\tpat.hisid,\n" +
			"\t\tpat.rfid_id,\n" +
			"\t\tpat.`name`,\n" +
			"\t\tpat.gender,\n" +
			"\t\tpat.birthday,\n" +
			"\t\tpat.blood,\n" +
			"\t\tpat.nationality,\n" +
			"\t\tpat.nation,\n" +
			"\t\tpat.id_type,\n" +
			"\t\tpat.id_number,\n" +
			"\t\tpat.phone,\n" +
			"\t\tpat.marriage,\n" +
			"\t\tpat.career,\n" +
			"\t\tpat.age,\n" +
			"\t\tpat.monthage,\n" +
			"\t\tpat.birthweight,\n" +
			"\t\tpat.hospitalweight,\n" +
			"\t\tpat.medical_records,\n" +
			"\t\tpat.outpatient,\n" +
			"\t\tpat.hospitalnumber,\n" +
			"\t\tpat.ohip,\n" +
			"\t\tpat.seedoctor,\n" +
			"\t\tpat.education_level,\n" +
			"\t\tpat.currentaddressprovince,\n" +
			"\t\tpat.currentaddresscity,\n" +
			"\t\tpat.currentaddresscounty,\n" +
			"\t\tpat.currentaddress,\n" +
			"\t\tpat.contactperson,\n" +
			"\t\tpat.relation,\n" +
			"\t\tpat.contactlocation,\n" +
			"\t\tpat.contactphone,\n" +
			"\t\tpat.admission_path,\n" +
			"\t\tpat.entrance_time,\n" +
			"\t\tpat.admission_department,\n" +
			"\t\tpat.time_of_admission,\n" +
			"\t\tpat.frequency,\n" +
			"\t\tpat.admission_ward,\n" +
			"\t\tpat.admission_beds,\n" +
			"\t\tpat.turndepartment,\n" +
			"\t\tpat.discharge_department,\n" +
			"\t\tpat.discharge_time,\n" +
			"\t\tpat.discharge_ward,\n" +
			"\t\tpat.entry_state,\n" +
			"\t\tpat.discharge_type,\n" +
			"\t\tpat.discharge_where_about,\n" +
			"\t\tpat.allergichistory,\n" +
			"\t\tpat.fzyregisterid,\n" +
			"\t\tpat.diagnosis,\n" +
			"\t\tpat.section_time,\n" +
			"\t\tpat.fadvicetime,\n" +
			"\t\tpat.fadviceusername,\n" +
			"\t\tpat.fadvicetime,\n" +
			"\t\tpat.del_flag,\n" +
			"\t\tbed.bed_id as bed_id,\n" +
			"\t\tbed.bed_name as bed_name\n" +
			"\t\tfrom pat_patient pat\n" +
			"\t\tleft join pat_patient_bed_correlation cor on pat.patient_id =  cor.patient_id\n" +
			"\t\tleft join nur_hospital_bed bed on cor.bed_id = bed.bed_id\n" +
			"\t\twhere  pat.id = #{id}")
	PatientVo idSelect(@Param("id")Integer id);


	/**
	 * 获取在线病人数
	 * @return
	 */
	List<PatientVo> getOnlinePatient();

	/**
	 * 今日出科人数（入科包括今日和之前入科人数）
	 * @return
	 */
	List<PatientVo> getDischargeNumber();

	/**
	 * 获取所有入科人数的总数
	 * @return
	 */
	Integer getAllPatient();

	/**
	 * 获取今日入科人数的总数
	 * @return
	 */
	Integer getTodayBuildPatientNumber();

	/**
	 * 获取当日入科并出科的人数
	 * @return
	 */
	Integer getTodayBuildPatientAndDischargeNumber();

	/**
	 * his入科到我们患者表中未出科的患者
	 * @return
	 */
	@Select("SELECT DISTINCT(id),hisid,patient_id,entry_state name from pat_patient WHERE entry_state =2")
	List<Patient> hisEnterPatient();

}
