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
package com.pig4cloud.pigx.ccxxicu.service.impl.assess;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessCondition;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProject;
import com.pig4cloud.pigx.ccxxicu.common.utils.ChineseToPinYin;
import com.pig4cloud.pigx.ccxxicu.mapper.assess.AssessProjectMapper;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessConditionService;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评估项目条件表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:45:44
 */
@Service
public class AssessProjectServiceImpl extends ServiceImpl<AssessProjectMapper, AssessProject> implements AssessProjectService {

	@Autowired
	private AssessConditionService assessConditionService;



	/**
	 * 修改进行删除
	 *
	 * @param assessProject
	 * @return
	 */
	@Override
	public boolean deleteByUpdate(AssessProject assessProject) {

		if (assessProject==null) {
			return false;
		}


		//查询每个项目下的条件数据
		List<AssessCondition> list = assessConditionService.list(Wrappers.<AssessCondition>query().lambda()
				.eq(AssessCondition::getAssessProjectId, assessProject.getAssessProjectId())
				.eq(AssessCondition::getDelFlag, 0)
		);

		if (CollectionUtils.isNotEmpty(list)) {

			list.forEach(ar -> {
				ar.setDelFlag(1);
				ar.setDelTime(LocalDateTime.now());
				ar.setDelUserId(assessProject.getDelUserId());
			});
			assessConditionService.updateBatchById(list);
		}

		return this.updateById(assessProject);
	}

	/**
	 * 新增评估
	 *
	 * @param assessProject
	 * @return
	 */
	@Override
	public boolean add(AssessProject assessProject) {

		String assessProjectCode = assessProject.getAssessProjectCode();

		boolean msg = true;
		//当code为空时 将name值的拼音转换为code
		if (StringUtils.isEmpty(assessProjectCode)) {

			String s = ChineseToPinYin.HanZiToPinYin(assessProject.getAssessProjectName(), false);

			assessProjectCode = s;

		}

		//对code值验重 不重复直接结束   重复在后面追加一个随机的大写字母
		while (msg) {

			Integer integer = baseMapper.selectCount(Wrappers.<AssessProject>query().lambda()
					.eq(AssessProject::getAssessProjectCode, assessProjectCode)
					.eq(AssessProject::getDelFlag, 0));
			if (integer.equals(0)) {

				break;

			} else {

				assessProjectCode = assessProjectCode+"_"+(char)(int)(Math.random()*26+65);

			}
		}

		assessProject.setAssessProjectCode(assessProjectCode);

		return this.save(assessProject);
	}
}
