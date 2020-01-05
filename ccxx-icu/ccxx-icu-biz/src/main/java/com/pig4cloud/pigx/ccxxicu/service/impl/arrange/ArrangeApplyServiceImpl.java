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
package com.pig4cloud.pigx.ccxxicu.service.impl.arrange;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.arrange.ArrangeApply;
import com.pig4cloud.pigx.ccxxicu.mapper.arrange.ArrangeApplyMapper;
import com.pig4cloud.pigx.ccxxicu.service.arrange.ArrangeApplyService;
import org.springframework.stereotype.Service;

/**
 * 护士班次申请表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:09
 */
@Service
public class ArrangeApplyServiceImpl extends ServiceImpl<ArrangeApplyMapper, ArrangeApply> implements ArrangeApplyService {

	/**
	 * 分页查询
	 * @param page
	 * @param arrangeApply
	 * @return
	 */
	@Override
	public IPage<ArrangeApply> selectByPage(Page page, ArrangeApply arrangeApply) {
		return baseMapper.selectByPage(page,arrangeApply);
	}
}
