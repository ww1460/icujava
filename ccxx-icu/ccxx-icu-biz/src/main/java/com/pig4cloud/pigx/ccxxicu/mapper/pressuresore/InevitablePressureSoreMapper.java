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

package com.pig4cloud.pigx.ccxxicu.mapper.pressuresore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.InevitablePressureSore;
import com.pig4cloud.pigx.ccxxicu.api.vo.pressuresore.InevitablePressureSoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 难免压疮申报表
 *
 * @author pigx code generator
 * @date 2019-08-26 11:09:03
 */
public interface InevitablePressureSoreMapper extends BaseMapper<InevitablePressureSore> {

	/* 分页查询数据 */
	IPage<List<InevitablePressureSoreVo>> selectPaging(Page page, @Param("form") InevitablePressureSore inevitablePressureSore);

}
