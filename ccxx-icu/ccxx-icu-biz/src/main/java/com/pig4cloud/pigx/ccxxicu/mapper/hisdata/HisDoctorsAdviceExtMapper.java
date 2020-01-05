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
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 医嘱扩展表
 *
 * @author pigx code generator
 * @date 2019-10-14 19:33:39
 */
public interface HisDoctorsAdviceExtMapper extends BaseMapper<HisDoctorsAdviceExt> {

	/**
	 * 查询在当前时间需要执行的医嘱
	 * @return
	 */
	@Select("SELECT * from his_doctors_advice_ext WHERE execute_type = 1 and pre_execute_time < NOW()")
	List<HisDoctorsAdviceExt> selectTime();

	/**
	 * 雪花id查询数据
	 */
	@Select("SELECT * FROM  his_doctors_advice_ext WHERE doctors_advice_ext_id = #{id}")
	HisDoctorsAdviceExt doctorsAdviceExt(@Param("id") String id);

	/**
	 * 通过his的执行医嘱id查询
	 * @param id
	 * @return
	 */
	@Select("SELECT * from  his_doctors_advice_ext WHERE his_f_z_y_exec_info_id = #{id}")
	HisDoctorsAdviceExt hisFZYExecInfoId(@Param("id")String id);

	/**
	 * 通过关联医嘱id  查询相关的医嘱数据
	 * @param id
	 * @return
	 */
	@Select("SELECT * from  his_doctors_advice_ext WHERE his_doctors_advice_id = #{id}")
	List<HisDoctorsAdviceExt> hisDoctorsAdviceId(@Param("id") String id);


}
