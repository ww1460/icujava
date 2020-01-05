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

package com.pig4cloud.pigx.ccxxicu.mapper.nursing;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UseNursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingTemplate;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingShowVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingTemplateUseVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingTemplateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 护理模板
 *
 * @author pigx code generator
 * @date 2019-08-19 17:00:03
 */
public interface NursingTemplateMapper extends BaseMapper<NursingTemplate> {


	IPage<NursingTemplateVo> selectByPage(Page page, @Param("query") NursingTemplateBo nursingTemplateBo);

	List<NursingShowVo> selectByCode(@Param("templateCode") String templateCode);

	List<NursingTemplateUseVo> useTemplate(UseNursingTemplateBo useNursingTemplateBo);
}
