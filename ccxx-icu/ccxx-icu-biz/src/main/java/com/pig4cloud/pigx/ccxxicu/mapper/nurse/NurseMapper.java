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
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 护士表
 *
 * @author pigx code generator
 * @date 2019-08-02 21:15:05
 */
public interface NurseMapper extends BaseMapper<Nurse> {

	/**
	 * 根据用户id 查询护士信息
	 * @param userId
	 * @return
	 */
	Nurse selectByUserId(String userId);

	/**
	 * 根据用户id 查询护士信息
	 * @param rfidId
	 * @return
	 */
	@Select("SELECT * FROM nur_nurse WHERE nurse_rfid = #{rfidId}")
	Nurse selectByUserRfidId(@Param("rfidId") String rfidId);

	/**
	 * 根据护士等级 查询护士信息
	 * @param nurseGrade
	 * @return
	 */
	List<Nurse> selectByGrade(Integer nurseGrade);


	/**
	 * 根据科室查询护士
	 * @param deptId
	 * @return
	 */
	List<Nurse> selectByDept(@Param("deptId") String deptId);

	/**
	 * 查询该科下所有护士
	 * @param deptId
	 * @return
	 */
	List<Nurse>	selectAllNurse(@Param("deptId")String deptId);

	/**
	 * 查询该科下护士的userid
	 * @param
	 * @return
	 */
	List<Nurse>	selectNurseUserIdByRfid(List<String> rfidList);

	/**
	 * 查询患者的非责任护士的信息
	 * @param patientId
	 * @return
	 */
	List<Nurse> getPatientNurse(@Param("patientId")String patientId);


	/**
	 * 查询护士的科室【去重后】
	 * @return
	 */
	@Select("select DISTINCT(dept_id) from nur_nurse")
	List<Integer> nurseDept();


	/**
	 * 综合数据大屏-所有护士数
	 * @return
	 */
	List<Nurse>	selectAllNurseNumber();


}


