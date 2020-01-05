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


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IllnessAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.ProjectAdviceCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IllnessAdviceVo;
import com.pig4cloud.pigx.ccxxicu.mapper.illnessNursing.IllnessAdviceMapper;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.ProjectAdviceCorrelationService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 病情护理病情护理建议表
 *
 * @author pigx code generator
 * @date 2019-09-09 11:21:27
 */
@Service
public class IllnessAdviceServiceImpl extends ServiceImpl<IllnessAdviceMapper, IllnessAdvice> implements IllnessAdviceService {

	@Autowired
	private ProjectAdviceCorrelationService projectAdviceCorrelationService;

	/**
	 * 修改代删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean delByUpdate(Integer id) {

		PigxUser user = SecurityUtils.getUser();
		//查询该数据
		IllnessAdvice byId = baseMapper.selectById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");
		//查询该数据对应的关联
		List<ProjectAdviceCorrelation> list = projectAdviceCorrelationService.list(Wrappers.<ProjectAdviceCorrelation>query().lambda()
				.eq(ProjectAdviceCorrelation::getDelFlag, 0)
				.eq(ProjectAdviceCorrelation::getNursingAdviceId, byId.getIllnessAdviceId())

		);

		if (CollectionUtils.isEmpty(list)) {
			return this.updateById(byId);
		}
		//将对应 的关联关系删除
		list.forEach(ar -> {
			ar.setDelFlag(1);
			ar.setDelTime(LocalDateTime.now());
			ar.setDelUserId(user.getId() + "");
		});
		projectAdviceCorrelationService.updateBatchById(list);
		return this.updateById(byId);
	}

	/**
	 * 数据的分页查询
	 *
	 * @param page
	 * @param illnessAdviceBo
	 * @return
	 */
	@Override
	public R selectByPage(Page page, IllnessAdviceBo illnessAdviceBo) {

		return R.ok(baseMapper.selectByPage(page, illnessAdviceBo));
	}

	/**
	 * 查询某项目及其状态下的建议
	 *
	 * @param illnessNursingState
	 * @return
	 */
	@Override
	public List<IllnessAdviceVo> selectByProject(IllnessNursingState illnessNursingState) {

		//查询该项目状态下所有的护理建议
		List<IllnessAdvice> list = this.list(Wrappers.<IllnessAdvice>query().lambda()
				.eq(IllnessAdvice::getDelFlag, 0)
				.eq(IllnessAdvice::getIllnessProjectId, illnessNursingState.getIllnessProjectId())
				.eq(IllnessAdvice::getIllnessState, illnessNursingState.getProjectStateFlag())
		);

		//根据项目状态id  查询对应已绑定的建议
		List<IllnessAdviceVo> advice = baseMapper.getAdvice(illnessNursingState.getIllnessNursingStateId());
		//创建集合存储结果
		List<IllnessAdviceVo> result = new ArrayList<>();

		//遍历该项目的某状态下的所有建议
		list.forEach(ar -> {

			IllnessAdviceVo illnessAdviceVo = new IllnessAdviceVo();
			//属性copy
			BeanUtil.copyProperties(ar, illnessAdviceVo);
			//选择的标识 默认为未选择
			illnessAdviceVo.setPitchOnId(1);

			result.add(illnessAdviceVo);
		});

		//绑定建议为空时，直接返回结果
		if (CollectionUtils.isEmpty(advice)) {
			return result;

		} else {
			//循环遍历 找到已经绑定到该项目状态的建议 将该条数据的选择标识改为0
			advice.forEach(ar -> {

				result.forEach(msg -> {

					if (msg.getIllnessAdviceId().equals(ar.getIllnessAdviceId())) {

						msg.setPitchOnId(0);

					}

				});
			});
		}

		return result;
	}
}
