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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.DrainageAttribute;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.DrainageAttributeVo;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.DrainageAttributeMapper;
import com.pig4cloud.pigx.ccxxicu.service.piping.DrainageAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 引流液属性
 *
 * @author pigx code generator
 * @date 2019-08-10 15:48:46
 */
@Service
public class DrainageAttributeServiceImpl extends ServiceImpl<DrainageAttributeMapper, DrainageAttribute> implements DrainageAttributeService {


	@Autowired
	private DrainageAttributeMapper drainageAttributeMapper;

	/**
	 * 分页查询数据源
	 * @param page
	 * @param drainageAttribute
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, DrainageAttribute drainageAttribute) {
		return drainageAttributeMapper.selectPaging(page,drainageAttribute);
	}


	/**
	 * 属性名称及属性类型查询
	 * @param drainageAttribute
	 * @return
	 */
	@Override
	public Map<String,Object> selectAttribute(DrainageAttribute drainageAttribute) {

		/**
		 * 查询去重后的属性名称，在通过属性名称查询相应的属性类型
		 */
		List<DrainageAttribute> strings = drainageAttributeMapper.distinctName(drainageAttribute);
		Map<String,Object> test = new HashMap<>();

		for (int i=0;i<strings.size();i++){
			/**
			 * 通过属性名称查询对象的类型
			 */
			String attributeName = strings.get(i).getAttributeName();
			List<DrainageAttribute> list = drainageAttributeMapper.selectNameType(drainageAttribute);
			test.put(attributeName,list);
		}

		Map<String,Object> DrainageAttribute = new HashMap<>();
		DrainageAttribute.put("list",strings);
		DrainageAttribute.put("Attribute",test);
		return DrainageAttribute;
	}


	/**
	 * 通过属性名称查询对应的类型
	 * @param drainageAttribute
	 * @return
	 */
	@Override
	public List<DrainageAttribute> selectNameType(DrainageAttribute drainageAttribute) {

		return drainageAttributeMapper.selectNameType(drainageAttribute);
	}

	/**
	 * 属性名称去重
	 * @param drainageAttribute
	 * @return
	 */
	@Override
	public List<DrainageAttribute> distinctName(DrainageAttribute drainageAttribute) {
		return drainageAttributeMapper.distinctName(drainageAttribute);
	}

	/**
	 * 通过引流id查询相关属性
	 * @param id
	 * @return
	 */
	@Override
	public List<DrainageAttributeVo> drainageId(String id) {
		return drainageAttributeMapper.drainageId(id);
	}
}
