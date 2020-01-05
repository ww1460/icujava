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
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.SlippageRegisterSurface;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.SlippageRegisterSurfaceVo;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.SlippageRegisterSurfaceMapper;
import com.pig4cloud.pigx.ccxxicu.service.piping.SlippageRegisterSurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管道滑脱登记表
 *
 * @author pigx code generator
 * @date 2019-08-21 10:30:22
 */
@Service
public class SlippageRegisterSurfaceServiceImpl extends ServiceImpl<SlippageRegisterSurfaceMapper, SlippageRegisterSurface> implements SlippageRegisterSurfaceService {


	@Autowired
	private SlippageRegisterSurfaceMapper slippageRegisterSurfaceMapper;

	/**
	 * 分页查询数据
	 * @param page
	 * @param slippageRegisterSurface
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, SlippageRegisterSurface slippageRegisterSurface) {
		return slippageRegisterSurfaceMapper.selectPaging(page,slippageRegisterSurface);
	}

	/**
	 * 数据回填 【已连接管道】
	 * @param slippageRegisterSurface
	 * @return
	 */
	@Override
	public SlippageRegisterSurfaceVo dataDackfilling(SlippageRegisterSurface slippageRegisterSurface) {
		List<SlippageRegisterSurfaceVo> all = slippageRegisterSurfaceMapper.all(slippageRegisterSurface);
		if (CollectionUtils.isNotEmpty(all)){
			return all.get(0);
		}else{
			return null;
		}

	}
}
