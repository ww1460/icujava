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
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 医嘱项目表【一批医嘱下相对应的药品、项目数据】
 *
 * @author pigx code generator
 * @date 2019-08-30 10:58:12
 */
public interface HisDoctorsAdviceProjectMapper extends BaseMapper<HisDoctorsAdviceProject> {


	/**
	 * 通过医嘱批号查询数据
	 * @param batchNumber
	 * @return
	 */
	List<HisDoctorsAdviceProject> selectBatchNumber(@Param("batchNumber") String  batchNumber);


	/**
	 * 通过医嘱id查询数据
	 * @param hisDoctorsAdviceId
	 * @return
	 */
	@Select("select pro.id, pro.batch_number, pro.doctors_advice_project_type, pro.doctors_advice_project_id, pro.his_doctors_advice_id, pro.content, pro.drug_use, pro.consumption, pro.company, pro.remarks from his_doctors_advice_project pro where pro.del_flag = 0 and pro.his_doctors_advice_id = #{hisDoctorsAdviceId}")
	List<HisDoctorsAdviceProject> hisDoctorsAdviceId(@Param("hisDoctorsAdviceId") String  hisDoctorsAdviceId);


	/**
	 * 通过医嘱内容的雪花id查询数据
	 * @param doctorsAdviceProjectId
	 * @return
	 */
	@Select("select pro.id, pro.batch_number, pro.doctors_advice_project_id, pro.doctors_advice_project_type, pro.his_doctors_advice_id, pro.content, pro.drug_use, pro.consumption, pro.company, pro.remarks from his_doctors_advice_project pro where pro.del_flag = 0 and pro.doctors_advice_project_id = #{doctorsAdviceProjectId}")
	HisDoctorsAdviceProject doctorsAdviceProjectId(@Param("doctorsAdviceProjectId") String  doctorsAdviceProjectId);

	/**
	 * 通过his的医嘱id【icu的医嘱内容id，查询关联医嘱id】
	 * @param
	 * @return
	 */
	@Select("SELECT  * from his_doctors_advice_project pro WHERE pro.his_doctors_advice_project_id = #{hisDoctorsAdviceProjectId}")
	HisDoctorsAdviceProject hisDoctorsAdviceProjectId(@Param("hisDoctorsAdviceProjectId") String  hisDoctorsAdviceProjectId);


}
