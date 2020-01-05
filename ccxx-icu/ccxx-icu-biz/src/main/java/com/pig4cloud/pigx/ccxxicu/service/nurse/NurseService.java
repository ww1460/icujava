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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UserBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseWatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 护士表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:02:36
 */
public interface NurseService extends IService<Nurse> {

	int add(UserBo userBo);

	/**
	 * 根据用户id 查询护士信息
	 *
	 * @param userId
	 * @return
	 */
	NurseInfo getNurseInfo(Integer userId);

	/**
	 * 根据护士rfid 查询护士信息
	 *
	 * @param rfidId
	 * @return
	 */
	Nurse getNurseInfoByRfidId(String rfidId);

	/**
	 * 根据护士等级 查询护士信息
	 *
	 * @param nurseGrade
	 * @return
	 */
	List<Nurse> selectByGrade(Integer nurseGrade);



	/**
	 * 根据科室查询护士
	 *
	 * @param deptId
	 * @return
	 */
	List<NurseWatch> AllNurse(String deptId);



	/**
	 * 修改护士
	 *
	 * @param nurseInfo
	 * @return
	 */
	int update(NurseInfo nurseInfo);

	/**
	 * 删除护士
	 *
	 * @param nurseId
	 * @return
	 */
	int deleteByNurseId(String nurseId);

	/**
	 * 根据科室查询护士(不包含护士长)
	 *
	 * @param deptId
	 * @return
	 */
	List<Nurse> selectByDept(String deptId);

	/**
	 * 查询该科下所有护士
	 *
	 * @param deptId
	 * @return
	 */
	List<Nurse> selectAllNurse(String deptId);

	/**
	 * 根据用户id 查询护士信息
	 * @param userId
	 * @return
	 */
	Nurse selectByUserId(String userId);

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
	List<Integer> nurseDept();

}
