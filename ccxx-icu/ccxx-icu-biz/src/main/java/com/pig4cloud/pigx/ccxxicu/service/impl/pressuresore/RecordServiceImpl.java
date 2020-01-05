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
import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.Record;
import com.pig4cloud.pigx.ccxxicu.api.vo.pressuresore.RecorderVo;
import com.pig4cloud.pigx.ccxxicu.mapper.pressuresore.RecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.pressuresore.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 压疮记录表 
 *
 * @author pigx code generator
 * @date 2019-08-30 16:19:42
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

	@Autowired
	private RecordMapper recordMapper;


	/**
	 * 分页查询数据
	 * @param page
	 * @param recorder
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, Record recorder) {
		recorder.setDelFlag(0);
		return recordMapper.selectPaging(page,recorder);
	}

	/**
	 * id查询全部数据
	 * @param id
	 * @return
	 */
	@Override
	public RecorderVo getByAll(Integer id) {
		return recordMapper.getByAll(id);
	}

	/**
	 * 查询某患者的最新一条数据
	 * @param patientId
	 * @return
	 */
	@Override
	public RecorderVo getOneRecord(String patientId) {
		return baseMapper.getOneRecord(patientId);
	}
}
