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
package com.pig4cloud.pigx.ccxxicu.service.impl.nursing;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UseNursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingTemplate;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingShowVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingTemplateUseVo;
import com.pig4cloud.pigx.ccxxicu.mapper.nursing.NursingTemplateMapper;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 护理模板
 *
 * @author pigx code generator
 * @date 2019-08-19 17:00:03
 */
@Service
public class NursingTemplateServiceImpl extends ServiceImpl<NursingTemplateMapper, NursingTemplate> implements NursingTemplateService {

	/**
	 * 分页查询 护理模板
	 *
	 * @param page
	 * @param nursingTemplateBo
	 * @return
	 */
	@Override
	public IPage selectByPage(Page page, NursingTemplateBo nursingTemplateBo) {
		return baseMapper.selectByPage(page, nursingTemplateBo);
	}

	/**
	 * 查看某一模板的具体内容
	 *
	 * @param templateCode
	 * @return
	 */
	@Override
	public List<NursingShowVo> selectByCode(String templateCode) {
		return baseMapper.selectByCode(templateCode);
	}

	/**
	 * 调用模板
	 *
	 * @param useNursingTemplateBo
	 * @return
	 */
	@Override
	public List<NursingTemplateUseVo> useTemplate(UseNursingTemplateBo useNursingTemplateBo) {
		return baseMapper.useTemplate(useNursingTemplateBo);
	}
}
