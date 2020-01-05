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
package com.pig4cloud.pigx.ccxxicu.service.impl.device;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Parameters;
import com.pig4cloud.pigx.ccxxicu.mapper.device.ParametersMapper;
import com.pig4cloud.pigx.ccxxicu.service.device.ParametersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 设备参数表
 *
 * @author pigx code generator
 * @date 2019-08-05 09:40:44
 */
@Service
public class ParametersServiceImpl extends ServiceImpl<ParametersMapper, Parameters> implements ParametersService {


	@Resource
	private ParametersMapper parametersMapper;



	@Override
	public List<Parameters> selectByCode(Set<String> codes, String eRfid) {
		return parametersMapper.selectByCode(codes, eRfid);
	}

}
