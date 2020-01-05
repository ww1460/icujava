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
package com.pig4cloud.pigx.ccxxicu.service.impl.hisdata;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceDictionaries;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.DoctorsAdviceDictionariesMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceDictionariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  医嘱项目字典表，对医嘱中的一些项目进行描述翻译
 *
 * @author pigx code generator
 * @date 2019-08-28 10:38:59
 */
@Service
public class DoctorsAdviceDictionariesServiceImpl extends ServiceImpl<DoctorsAdviceDictionariesMapper, DoctorsAdviceDictionaries> implements DoctorsAdviceDictionariesService {

	@Autowired
	private DoctorsAdviceDictionariesMapper doctorsAdviceDictionariesMapper;


	//	<!-- 通过条件查询数据  -->
	@Override
	public DoctorsAdviceDictionaries getLabelName(DoctorsAdviceDictionaries test) {
		return doctorsAdviceDictionariesMapper.getLabelName(test);
	}

	/*
		<!-- 条件数据查询  -->
	 */
	@Override
	public List<DoctorsAdviceDictionaries> selectAll(DoctorsAdviceDictionaries test) {
		return doctorsAdviceDictionariesMapper.selectAll(test);
	}
}
