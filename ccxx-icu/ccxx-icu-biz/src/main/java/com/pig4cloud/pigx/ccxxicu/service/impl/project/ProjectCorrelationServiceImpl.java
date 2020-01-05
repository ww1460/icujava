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


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.mapper.project.ProjectCorrelationMapper;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectCorrelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目标识表(主要是对护理单一二作关联)
 *
 * @author pigx code generator
 * @date 2019-09-13 16:11:58
 */
@Service
public class ProjectCorrelationServiceImpl extends ServiceImpl<ProjectCorrelationMapper, ProjectCorrelation> implements ProjectCorrelationService {

	/**
	 * 查询某记录单关联项目
	 * @param projectCorrelation
	 * @return
	 */
	@Override
	public List<ProjectCorrelationVo> selectReportProject(ProjectCorrelation projectCorrelation) {
		return baseMapper.selectReportProject(projectCorrelation);
	}
}
