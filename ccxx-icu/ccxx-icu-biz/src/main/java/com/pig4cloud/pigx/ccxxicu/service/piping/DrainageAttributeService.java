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

package com.pig4cloud.pigx.ccxxicu.service.piping;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.DrainageAttribute;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.DrainageAttributeVo;

import java.util.List;
import java.util.Map;

/**
 * 引流液属性
 *
 * @author pigx code generator
 * @date 2019-08-10 15:48:46
 */
public interface DrainageAttributeService extends IService<DrainageAttribute> {



	/* 分页查询数据*/
	IPage selectPaging(Page page, DrainageAttribute drainageAttribute);

	/**
	 * 属性名称及属性类型查询
	 * @param drainageAttribute
	 * @return
	 */
	List<DrainageAttribute> selectNameType(DrainageAttribute drainageAttribute);

	/**
	 * 查询属性及类型
	 * @param drainageAttribute
	 * @return
	 */
	Map<String,Object> selectAttribute(DrainageAttribute drainageAttribute);


	/* 属性名去重  */
	List<DrainageAttribute> distinctName(DrainageAttribute drainageAttribute);

	/**
	 * 通过引流id查询相关属性
	 * @param id
	 * @return
	 */
	List<DrainageAttributeVo> drainageId(String id);
}
