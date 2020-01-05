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

package com.pig4cloud.pigx.ccxxicu.service.hisdata;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceCode;

import java.util.List;

/**
 * 医嘱编码【用于储存医嘱的内容】
 *
 * @author pigx code generator
 * @date 2019-10-07 16:01:32
 */
public interface DoctorsAdviceCodeService extends IService<DoctorsAdviceCode> {

	/**
	 * 数据全查
	 * @return
	 */
	List<DoctorsAdviceCode> selectAll();

	/**
	 * 雪花id查询
	 * @param id
	 * @return
	 */
	DoctorsAdviceCode doctorsAdviceCodeId(String id);


	/**
	 * 分页条件查询数据
	 * @param page
	 * @param doctorsAdviceCode
	 * @return
	 */
	IPage selectPaging(Page page,DoctorsAdviceCode doctorsAdviceCode);

	/**
	 * 通过医嘱内容查询当条数据内容
	 * @param doctorsAdviceContent
	 * @return
	 */
	DoctorsAdviceCode doctorsAdviceContent(String doctorsAdviceContent);
}
