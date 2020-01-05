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
package com.pig4cloud.pigx.ccxxicu.service.impl.task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Entrust;
import com.pig4cloud.pigx.ccxxicu.mapper.task.EntrustMapper;
import com.pig4cloud.pigx.ccxxicu.service.task.EntrustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务委托
 *
 * @author pigx code generator
 * @date 2019-09-02 16:00:25
 */
@Service
public class EntrustServiceImpl extends ServiceImpl<EntrustMapper, Entrust> implements EntrustService {

	@Autowired
	private EntrustMapper entrustMapper;

	/**
	 * 分页查询数据
	 * @param page
	 * @param entrust
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, Entrust entrust) {
		return entrustMapper.selectPaging(page,entrust);
	}
}
