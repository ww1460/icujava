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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipingVo;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.PipingMapper;
import com.pig4cloud.pigx.ccxxicu.service.piping.PipingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管路表
 *
 * @author pigx code generator
 * @date 2019-08-08 15:11:10
 */
@Service
public class PipingServiceImpl extends ServiceImpl<PipingMapper, Piping> implements PipingService {

	@Autowired
	private PipingMapper pipingMapper;

	/**
	 * 通过雪花生成id查询数据
	 * @param piping
	 * @return
	 */
	@Override
	public PipingVo getPipingId(Piping piping) {
		return pipingMapper.getPipingId(piping);
	}

	/**
	 * 全查管路id
	 * @param piping
	 * @return
	 */
	@Override
	public List<PipingVo> selectAll(Piping piping) {
		return pipingMapper.selectAll(piping);
	}

	/**
	 * 管道数据分页查询
	 * @param page
	 * @param piping
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, Piping piping) {
		return pipingMapper.selectPaging(page,piping);
	}
}
