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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.HealthAnswerBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthAnswer;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.HealthAnswerVo;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.HealthAnswerMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.HealthAnswerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 护士健康回答
 *
 * @author pigx code generator
 * @date 2019-08-05 15:20:12
 */
@Service
public class HealthAnswerServiceImpl extends ServiceImpl<HealthAnswerMapper, HealthAnswer> implements HealthAnswerService {
	/**
	 * 护士长分页查询 护士的个人评估
	 *
	 * @param page
	 * @param healthAnswerBo
	 * @return
	 */
	@Override
	public IPage<HealthAnswerVo> selectByPage(Page page, HealthAnswerBo healthAnswerBo) {
		/**
		 * 这里处理了一下时间格式
		 * 将开始时间强转为那一天的零点  00:00:00
		 * 将结束时间强转为那一天的 23：59：59
		 */
		LocalDateTime startTime = healthAnswerBo.getStartTime();

		LocalDateTime endTime = healthAnswerBo.getEndTime();

		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		if (startTime != null) {

			String s = startTime.toString();

			String substring = s.substring(0, 10);

			String s1 = substring + " 00:00:00";

			startTime = LocalDateTime.parse(s1, df);

		}

		if (endTime != null) {

			String s = endTime.toString();

			String substring = s.substring(0, 10);

			String s1 = substring + " 23:59:59";

			endTime = LocalDateTime.parse(s1, df);

		}


		healthAnswerBo.setEndTime(endTime);
		healthAnswerBo.setStartTime(startTime);

		return baseMapper.selectByPage(page, healthAnswerBo);
	}

	/**
	 * 查询单条护士的个人评估记录
	 *
	 * @param id
	 * @return
	 */
	@Override
	public HealthAnswerVo getById(Integer id) {
		return baseMapper.getById(id);
	}

	/**
	 * 通过评估项目id 查询
	 *
	 * @param subjectId
	 * @return
	 */
	@Override
	public List<HealthAnswerVo> getBySubject(String subjectId) {
		return baseMapper.getBySubject(subjectId);
	}

	/**
	 * 查询十小时内该护士做过的项目id
	 *
	 * @param healthAnswerBo
	 * @return
	 */
	@Override
	public List<String> selectNow(HealthAnswerBo healthAnswerBo) {
		return baseMapper.selectNow(healthAnswerBo);
	}

	/**
	 * 查询某护士的评估记录  不分页
	 *
	 * @param healthAnswer
	 * @return
	 */
	@Override
	public List<HealthAnswerVo> selectByNurse(HealthAnswer healthAnswer) {
		return baseMapper.selectByNurse(healthAnswer);
	}
}
