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
package com.pig4cloud.pigx.ccxxicu.service.impl.piping;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Nursing;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.NursingMapper;
import com.pig4cloud.pigx.ccxxicu.service.piping.NursingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管道护理【对插管患者做的护理事项】
 *
 * @author pigx code generator
 * @date 2019-09-03 15:16:19
 */
@Service
public class NursingServiceImpl extends ServiceImpl<NursingMapper, Nursing> implements NursingService {

	@Autowired
	private NursingMapper nursingMapper;

	/**
	 * 条件分页查询
	 * @param page
	 * @param nursing
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, Nursing nursing) {
		return nursingMapper.selectPaging(page,nursing);
	}
}
