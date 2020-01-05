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

package com.pig4cloud.pigx.ccxxicu.service.nursing;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UseNursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingTemplate;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingShowVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingTemplateUseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 护理模板
 *
 * @author pigx code generator
 * @date 2019-08-19 17:00:03
 */
public interface NursingTemplateService extends IService<NursingTemplate> {

	/**
	 * 分页查询 护理模板
	 * @param page
	 * @param nursingTemplateBo
	 * @return
	 */
	IPage selectByPage(Page page, NursingTemplateBo nursingTemplateBo);

	/**
	 * 查看某一模板的具体内容
	 * @param templateCode
	 * @return
	 */
	List<NursingShowVo> selectByCode(@Param("templateCode") String templateCode);

	/**
	 * 调用模板
	 * @param templateCode
	 * @param patientId
	 * @return
	 */
	List<NursingTemplateUseVo> useTemplate(UseNursingTemplateBo useNursingTemplateBo);

}
