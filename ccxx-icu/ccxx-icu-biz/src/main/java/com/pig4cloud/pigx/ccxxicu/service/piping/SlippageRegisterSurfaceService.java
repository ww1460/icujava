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
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.SlippageRegisterSurface;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.SlippageRegisterSurfaceVo;

/**
 * 管道滑脱登记表
 *
 * @author pigx code generator
 * @date 2019-08-21 10:30:22
 */
public interface SlippageRegisterSurfaceService extends IService<SlippageRegisterSurface> {

	/***
	 * 分页查询数据源
	 * @param page
	 * @param slippageRegisterSurface
	 * @return
	 */
	IPage selectPaging(Page page,SlippageRegisterSurface slippageRegisterSurface);

	/**
	 * 数据回填 【已连接管道】
	 * @param slippageRegisterSurface
	 * @return
	 */
	SlippageRegisterSurfaceVo dataDackfilling(SlippageRegisterSurface slippageRegisterSurface);


}
