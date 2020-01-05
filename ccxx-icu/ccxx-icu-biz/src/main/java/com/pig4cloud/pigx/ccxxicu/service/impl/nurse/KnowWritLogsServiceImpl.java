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
package com.pig4cloud.pigx.ccxxicu.service.impl.nurse;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.KnowWritLogsBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.KnowWritLogs;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.KnowWritLogsVo;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.KnowWritLogsMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.KnowWritLogsService;
import org.springframework.stereotype.Service;

/**
 * 知情文书记录
 *
 * @author pigx code generator
 * @date 2019-08-17 16:10:44
 */
@Service
public class KnowWritLogsServiceImpl extends ServiceImpl<KnowWritLogsMapper, KnowWritLogs> implements KnowWritLogsService {
	/**
	 * 护理文书记录的分页查询
	 * @param page
	 * @param knowWritLogsBo
	 * @return
	 */
	@Override
	public IPage<KnowWritLogsVo> selectByPage(Page page, KnowWritLogsBo knowWritLogsBo) {
		return baseMapper.selectByPage(page,knowWritLogsBo);
	}
}
