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

package com.pig4cloud.pigx.ccxxicu.service.hisdata;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisPatient;

import java.util.List;

/**
 * 患者his传输表
 *
 * @author pigx code generator
 * @date 2019-08-27 15:58:29
 */
public interface HisPatientService extends IService<HisPatient> {

	/*接收his传来的数据*/
	Boolean hisPatientAdd(HisPatient hisPatient);

	/**
	 * 通过his中的患者唯一id查询当前患者是否有
	 * @param id
	 * @return
	 */
	HisPatient selectHisPatientId(String id);

	List<HisPatient> selectAll();

	/**
	 * 利用his中的患者id修改患者出科信息
	 * @param hisPatient
	 * @return
	 */
	Boolean hisPatientId(HisPatient hisPatient);

	/**
	 * his患者id查询数据
	 * @param hisPatientId
	 * @return
	 */
	HisPatient hisPatientIdSelect(String hisPatientId);

	/**
	 * 分页查询数据
	 * @param hisPatient
	 * @return
	 */
	IPage selectPaging(Page page ,HisPatient hisPatient);


}
