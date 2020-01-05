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
package com.pig4cloud.pigx.ccxxicu.service.impl.drug;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.EasilyConfusedCatalog;
import com.pig4cloud.pigx.ccxxicu.mapper.drug.EasilyConfusedCatalogMapper;
import com.pig4cloud.pigx.ccxxicu.service.drug.EasilyConfusedCatalogService;
import org.springframework.stereotype.Service;

/**
 * 药品易混淆目录
 *
 * @author pigx code generator
 * @date 2019-09-19 10:41:34
 */
@Service
public class EasilyConfusedCatalogServiceImpl extends ServiceImpl<EasilyConfusedCatalogMapper, EasilyConfusedCatalog> implements EasilyConfusedCatalogService {
	/**
	 * 查询所有的易混淆药品
	 * @param page
	 * @param easilyConfusedCatalog
	 * @return
	 */
	@Override
	public IPage getList(Page page, EasilyConfusedCatalog easilyConfusedCatalog) {
		return baseMapper.getList(page,easilyConfusedCatalog);
	}
}
