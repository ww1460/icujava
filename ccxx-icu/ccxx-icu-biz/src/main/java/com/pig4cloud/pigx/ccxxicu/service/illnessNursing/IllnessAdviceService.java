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

package com.pig4cloud.pigx.ccxxicu.service.illnessNursing;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IllnessAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IllnessAdviceVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 病情护理病情护理建议表
 *
 * @author pigx code generator
 * @date 2019-09-09 11:21:27
 */
public interface IllnessAdviceService extends IService<IllnessAdvice> {

	/**
	 * 修改代删除
	 *
	 * @param id
	 * @return
	 */
	boolean delByUpdate(Integer id);

	/**
	 * 数据的分页查询
	 *
	 * @param page
	 * @param illnessAdviceBo
	 * @return
	 */
	R selectByPage(Page page, IllnessAdviceBo illnessAdviceBo);

	/**
	 * 查询某项目及其状态下的建议
	 *
	 * @param illnessNursingState
	 * @return
	 */
	List<IllnessAdviceVo> selectByProject(IllnessNursingState illnessNursingState);


}
