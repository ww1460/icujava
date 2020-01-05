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
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 医嘱表
 *
 * @author pigx code generator
 * @date 2019-08-30 11:23:01
 */
public interface HisDoctorsAdviceMapper extends BaseMapper<HisDoctorsAdvice> {

	/**
	 * 全查医嘱数据
	 * @param hisDoctorsAdvice
	 * @return
	 */
	List<HisDoctorsAdviceVo> selectAll(@Param("hisDoctorsAdvice")HisDoctorsAdvice hisDoctorsAdvice);

	/**
	 * 查询当前登录护士相关的医嘱内容
	 * @param hisDoctorsAdvice
	 * @return
	 */
	IPage<HisDoctorsAdviceVo> LoginNurseDoctorsAdvice(Page page, @Param("hisDoctorsAdvice")HisDoctorsAdviceVo hisDoctorsAdvice);



	/**
	 * 长期任务查询用于定时
	 * @return
	 */
	List<HisDoctorsAdviceVo> longTermTask();


	/**
	 * 通过批号查询医嘱
	 * @param batchNumber
	 * @return
	 */
	HisDoctorsAdvice getBatchNumber (@Param("batchNumber")String batchNumber);

	/**
	 * 通过医嘱ID查询批次号
	 * @param adviceId
	 * @return
	 */
	HisDoctorsAdvice getBatchNumberByAdviceId (@Param("adviceId")String adviceId);

	/**
	 * 通过患者id 查询当前患者没有生成任务的医嘱
	 * @return
	 */
	List<HisDoctorsAdvice> notGenerate(@Param("hisPatientId") String hisPatientId);

	/**
	 * 通过id查询当条医嘱的数据
	 * @param id
	 * @return
	 */
	HisDoctorsAdviceVo getById(@Param("id") Integer id);

	/**
	 * 通过医嘱雪花id查询数据
	 * @param doctorsAdviceId
	 * @return
	 */
	HisDoctorsAdviceVo getDoctorsAdviceId(@Param("doctorsAdviceId") String doctorsAdviceId);

	/**
	 * 根据患者列表获取医生的医嘱id-表：his_doctors_advice
	 * @param patientId
	 * @return
	 */
	List<HisDoctorsAdvice> getHisDoctorAdviceListByPatientId(@Param("patientId") String patientId);

	/**
	 * 通过患者id 查询相关没有完成的医嘱
	 * @param patientId
	 * @return
	 */
	List<HisDoctorsAdvice> stopDoctorsAdvice(@Param("patientId") String patientId);


	/**
	 * 通过his医嘱id查询当前医嘱的详细数据
	 * @param hisDoctorsAdviceId
	 * @return
	 */
	@Select("SELECT * from his_doctors_advice WHERE del_flag = 0 and his_doctors_advice_id = #{hisDoctorsAdviceId}")
	HisDoctorsAdviceVo hisDoctorsAdviceId(@Param("hisDoctorsAdviceId") String hisDoctorsAdviceId);

	/**
	 * 获取当日的医嘱数据
	 * @return
	 */
	List<HisDoctorsAdvice> getDoctorsAdviceForToday();

	/**
	 * 通过HIs患者id 查询相关没有完成的医嘱【校对已发送】的长期医嘱 ，和停止的短期医嘱
	 * @param hisPaientId
	 * @return
	 */
	List<HisDoctorsAdvice> stopDoctorsAdviceHisPatientId(@Param("hisPaientId") String hisPaientId);

	/**
	 * 通过HIs患者id 查询当前患者有没有医嘱
	 * @param hisPaientId
	 * @return
	 */
	@Select("SELECT id,his_doctors_advice_id from his_doctors_advice WHERE his_patient_id = #{id}")
	List<HisDoctorsAdvice> hisPatientId(@Param("id") String hisPaientId);
	/**
	 * 通过his医嘱id修改当医嘱数据
	 * @param hisDoctorsAdvice
	 * @return
	 */
	@Select("update from his_doctors_advice set  state = #{hisDoctorsAdvice.state} WHERE del_flag = 0 and his_doctors_advice_id = #{hisDoctorsAdvice.hisDoctorsAdviceId}")
	Integer updateHisDoctorsAdviceId(@Param("hisDoctorsAdvice") HisDoctorsAdvice hisDoctorsAdvice);


}
