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
package com.pig4cloud.pigx.ccxxicu.service.impl.project;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.WarmJudge;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.WarmJudgeVo;
import com.pig4cloud.pigx.ccxxicu.mapper.project.WarmJudgeMapper;
import com.pig4cloud.pigx.ccxxicu.service.project.WarmJudgeService;
import org.springframework.stereotype.Service;

/**
 * 预警项目判断表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:20
 */
@Service
public class WarmJudgeServiceImpl extends ServiceImpl<WarmJudgeMapper, WarmJudge> implements WarmJudgeService {

	/**
	 * 分页查询所有项目的预警判断值
	 * @param page
	 * @param warmJudgeVo
	 * @return
	 */
	@Override
	public IPage<WarmJudgeVo> selectByPage(Page page, WarmJudgeVo warmJudgeVo) {
		return baseMapper.selectByPage(page,warmJudgeVo);
	}

	/**
	 * 根据项目id   查询该项目的预警判断
	 * @param projectId
	 * @return
	 */
	@Override
	public WarmJudge selectByProjectId(String projectId) {
		return baseMapper.selectByProjectId(projectId);
	}
}
