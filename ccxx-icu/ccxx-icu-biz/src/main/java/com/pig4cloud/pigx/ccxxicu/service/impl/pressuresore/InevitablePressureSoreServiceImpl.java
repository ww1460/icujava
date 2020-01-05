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
package com.pig4cloud.pigx.ccxxicu.service.impl.pressuresore;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.InevitablePressureSore;
import com.pig4cloud.pigx.ccxxicu.mapper.pressuresore.InevitablePressureSoreMapper;
import com.pig4cloud.pigx.ccxxicu.service.pressuresore.InevitablePressureSoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 难免压疮申报表
 *
 * @author pigx code generator
 * @date 2019-08-26 11:09:03
 */
@Service
public class InevitablePressureSoreServiceImpl extends ServiceImpl<InevitablePressureSoreMapper, InevitablePressureSore> implements InevitablePressureSoreService {

	@Autowired
	private InevitablePressureSoreMapper inevitablePressureSoreMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param inevitablePressureSore
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, InevitablePressureSore inevitablePressureSore) {
		return inevitablePressureSoreMapper.selectPaging(page, inevitablePressureSore);
	}
}
