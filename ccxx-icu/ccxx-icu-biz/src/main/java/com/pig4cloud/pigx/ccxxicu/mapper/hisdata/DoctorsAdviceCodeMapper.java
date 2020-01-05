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
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 医嘱编码【用于储存医嘱的内容】
 *
 * @author pigx code generator
 * @date 2019-10-07 16:01:32
 */
public interface DoctorsAdviceCodeMapper extends BaseMapper<DoctorsAdviceCode> {

	/**
	 * 分页数据全查
	 * @return
	 */
	@Select("select id,doctors_advice_code_id,doctors_advice_content,hospital_code,remarks,del_flag from his_doctors_advice_code WHERE del_flag = 0")
	List<DoctorsAdviceCode> selectAll();

	/**
	 * 雪花id查询
	 * @param id
	 * @return
	 */
	@Select("select id,doctors_advice_code_id,doctors_advice_content,hospital_code,remarks,del_flag from his_doctors_advice_code WHERE doctors_advice_code_id = #{id}")
	DoctorsAdviceCode doctorsAdviceCodeId(@Param("id")String id);

	/**
	 * 分页条件查询数据
	 * @param page
	 * @param test
	 * @return
	 */
	IPage<List<DoctorsAdviceCode>> selectPaging(Page page,@Param("test") DoctorsAdviceCode test);

	/**
	 * 通过医嘱的内容查询医嘱的数据
	 * @param doctorsAdviceContent
	 * @return
	 */
	DoctorsAdviceCode doctorsAdviceContent(@Param("doctorsAdviceContent")String doctorsAdviceContent);

}
