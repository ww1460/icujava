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

package com.pig4cloud.pigx.ccxxicu.mapper.piping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Nursing;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.NursingVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管道护理【对插管患者做的护理事项】
 *
 * @author pigx code generator
 * @date 2019-09-03 15:16:19
 */
public interface NursingMapper extends BaseMapper<Nursing> {

	/**
	 * 条件分页查询
	 * @param page
	 * @param nursing
	 * @return
	 */
	IPage<List<NursingVo>>selectPaging(Page page, @Param("nursing") Nursing nursing);



}
