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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.DrainageRecord;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.DrainageRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.piping.DrainageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 引流液记录表id
 *
 * @author pigx code generator
 * @date 2019-08-12 17:22:30
 */
@Service
public class DrainageRecordServiceImpl extends ServiceImpl<DrainageRecordMapper, DrainageRecord> implements DrainageRecordService {

	@Autowired
	private DrainageRecordMapper drainageRecordMapper;


	/**
	 * 分页查询数据
	 * @param page
	 * @param drainageRecord
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, DrainageRecord drainageRecord) {
		return drainageRecordMapper.selectPaging(page, drainageRecord);
	}


}
