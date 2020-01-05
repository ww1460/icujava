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

package com.pig4cloud.pigx.ccxxicu.mapper.hisdata;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisPatient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 患者his传输表
 *
 * @author pigx code generator
 * @date 2019-08-27 15:58:29
 */
public interface HisPatientMapper extends BaseMapper<HisPatient> {

	/*接收his传来的数据*/
	Integer add(HisPatient hisPatient);

	/**
	 * 通过his中的患者唯一id查询当前患者是否有
	 * @param id
	 * @return
	 */
	@Select("select id,name,his_patient_id from his_patient WHERE his_patient_id = #{id}")
	HisPatient selectHisPatientId(@Param("id") String id);

	/**
	 * 利用his中的患者id修改患者出科信息
	 * @param hisPatient
	 * @return
	 */
	@Update("UPDATE his_patient SET discharge_time = #{hisPatient.dischargeTime}, discharge_dept = #{hisPatient.dischargeDept},entry_state = #{hisPatient.entryState},turndepartment_describe =#{hisPatient.turndepartmentDescribe} WHERE his_patient_id = #{hisPatient.hisPatientId}")
	Integer hisPatientId(@Param("hisPatient") HisPatient hisPatient);



	@Select("select * from his_patient")
	List<HisPatient> selectAll();

	/**
	 * his患者id查询数据
	 * @param hisPatientId
	 * @return
	 */
	@Select("select * from his_patient where his_patient_id = #{hisPatientId}")
	HisPatient hisPatientIdSelect(@Param("hisPatientId")String hisPatientId);

	/**
	 * 分页查询数据
	 * @param page
	 * @param hisPatient
	 * @return
	 */
	IPage<HisPatient> selectPaging(Page page,@Param("hisPatient")HisPatient hisPatient);

}
