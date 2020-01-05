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

package com.pig4cloud.pigx.ccxxicu.service.nurse;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.HealthAnswerBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthAnswer;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.HealthAnswerVo;

import java.util.List;

/**
 * 护士健康回答
 *
 * @author pigx code generator
 * @date 2019-08-05 15:20:12
 */
public interface HealthAnswerService extends IService<HealthAnswer> {
	/**
	 * 护士长通过科室 时间 护士姓名  护士id查询护士的个人评估记录
	 * @param page
	 * @param healthAnswerBo
	 * @return
	 */
	IPage<HealthAnswerVo> selectByPage(Page page, HealthAnswerBo healthAnswerBo);

	/**
	 * 查询单条护士的个人评估记录
	 * @param id
	 * @return
	 */
	HealthAnswerVo getById(Integer id);

	/**
	 * 通过评估项目id 查询
	 *
	 * @param subjectId
	 * @return
	 */
	List<HealthAnswerVo> getBySubject(String subjectId);

	/**
	 * 查询时间段内 该护士做过的项目
	 * @param healthAnswerBo
	 * @return
	 */
	List<String> selectNow(HealthAnswerBo healthAnswerBo);

	/**
	 * 查询某护士的评估记录  不分页
	 * @param healthAnswer
	 * @return
	 */
	List<HealthAnswerVo> selectByNurse(HealthAnswer healthAnswer);


}
