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
package com.pig4cloud.pigx.ccxxicu.service.impl.illnessNursing;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.ProjectAdviceCorrelation;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.illnessNursing.ProjectAdviceCorrelationMapper;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.ProjectAdviceCorrelationService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目状态和建议的关联表
 *
 * @author pigx code generator
 * @date 2019-09-09 14:38:57
 */
@Service
public class ProjectAdviceCorrelationServiceImpl extends ServiceImpl<ProjectAdviceCorrelationMapper, ProjectAdviceCorrelation> implements ProjectAdviceCorrelationService {


	/**
	 * 修改关联  先删除 后新增
	 * @param projectAdviceCorrelations
	 * @return
	 */
	@Override
	public boolean updateCorrelation(List<ProjectAdviceCorrelation> projectAdviceCorrelations) {

		PigxUser user = SecurityUtils.getUser();
		/**
		 * 获取关联的项目状态id  当传来的没有任何关联时  会默认一个状态
		 */
		String projectStateId = projectAdviceCorrelations.get(0).getProjectStateId();
		//查询该项目之前的关联
		List<ProjectAdviceCorrelation> list = this.list(Wrappers.<ProjectAdviceCorrelation>query().lambda()
				.eq(ProjectAdviceCorrelation::getDelFlag, 0)
				.eq(ProjectAdviceCorrelation::getProjectStateId, projectStateId));
		//如果之前的关联不为空  将之前的关联删除
		if (!CollectionUtils.isEmpty(list)) {

			list.forEach(ar->{

				ar.setDelFlag(1);
				ar.setDelUserId(user.getId()+"");
				ar.setDelTime(LocalDateTime.now());

			});

			this.updateBatchById(list);

		}
		//将现在的关联进行新增
		for (int i = 0; i < projectAdviceCorrelations.size(); i++) {

			ProjectAdviceCorrelation ar = projectAdviceCorrelations.get(i);
			//将没有建议id 的项移除
			if (ar.getNursingAdviceId()==null) {
				projectAdviceCorrelations.remove(i);
				i--;
				continue;
			}
			ar.setCreateTime(LocalDateTime.now());
			ar.setDelFlag(0);
			ar.setCreateUserId(user.getId()+"");
			ar.setProjectAdviceCorrelationId(SnowFlake.getId()+"");
		}
		return this.saveBatch(projectAdviceCorrelations);
	}
}
