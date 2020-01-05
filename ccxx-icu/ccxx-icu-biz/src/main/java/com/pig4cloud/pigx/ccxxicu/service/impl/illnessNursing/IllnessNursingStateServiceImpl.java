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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.ProjectAdviceCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IllnessNursingStateVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IllnessTree;
import com.pig4cloud.pigx.ccxxicu.mapper.illnessNursing.IllnessNursingStateMapper;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessNursingStateService;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.ProjectAdviceCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 病情护理项目状态表
 *
 * @author pigx code generator
 * @date 2019-09-04 11:30:06
 */
@Service
public class IllnessNursingStateServiceImpl extends ServiceImpl<IllnessNursingStateMapper, IllnessNursingState> implements IllnessNursingStateService {


	@Autowired
	private ProjectRecordService projectRecordService;

	@Autowired
	private IllnessAdviceService illnessAdviceService;

	@Autowired
	private ProjectAdviceCorrelationService projectAdviceCorrelationService;


	/**
	 * 获取病情护理的结构性数据
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public R selectTree(String patientId) {

		//获取所有的数据
		List<IllnessTree> illnessTrees = baseMapper.selectTree(new DataScope());

		//查询该项目的记录
		for (int i = 0; i < illnessTrees.size(); i++) {

			IllnessTree ar = illnessTrees.get(i);

			List<IllnessNursingStateVo> illness = ar.getIllness();
			//移除状态为空的项目记录
			if (CollectionUtils.isEmpty(illness)) {

				illnessTrees.remove(i);
				i--;
				continue;
			}

			//项目id
			String projectId = ar.getProjectId();
			//查询患者该项目的最新的一次数据记录
			if (StringUtils.isEmpty(patientId)) {

				continue;

			}
			ProjectRecord oneRecord = projectRecordService.getOneRecord(patientId, projectId);
			//封装数据
			ar.setProjectRecord(oneRecord == null ? null : oneRecord.getProjectSpecificRecord());
			ar.setProjectValue(oneRecord == null ? null : oneRecord.getProjectValue());

		}

		//将数据按项目类型进行分类
		Map<Integer, List<IllnessTree>> collect = illnessTrees.stream()
				.collect(Collectors.groupingBy(IllnessTree::getIllnessNursingTypeFlag));


		return R.ok(collect);
	}


	/**
	 * 查询某项目的状态及其护理建议
	 *
	 * @param illnessProjectId
	 * @return
	 */
	@Override
	public R getProjectState(String illnessProjectId) {

		return R.ok(baseMapper.getProjectState(illnessProjectId));
	}

	/**
	 * 删除项目
	 *
	 * @param project
	 * @return
	 */
	@Override
	public boolean delProjectByUpdate(Project project) {
		/**
		 * 删除该项目的状态数据
		 * 删除该项目的护理建议
		 * 删除状态和护理建议的关联
		 */
		//查询出该项目的病情护理数据
		List<IllnessNursingState> list = this.list(Wrappers.<IllnessNursingState>query().lambda()
				.eq(IllnessNursingState::getDelFlag, 0)
				.eq(IllnessNursingState::getIllnessProjectId, project.getProjectId()
				));

		//结果不为空
		if (!CollectionUtils.isEmpty(list)) {

			//依次进行删除项目状态
			list.forEach(ar -> {

				this.delByUpdate(ar.getId());

			});

		}

		//查询出项目的护理建议 并进行删除
		List<IllnessAdvice> illnessAdvices = illnessAdviceService.list(Wrappers.<IllnessAdvice>query().lambda()
				.eq(IllnessAdvice::getDelFlag, 0)
				.eq(IllnessAdvice::getIllnessProjectId, project.getProjectId()));

		//结果不为空
		if (!CollectionUtils.isEmpty(illnessAdvices)) {

			//依次进行删除项目建议
			illnessAdvices.forEach(ar -> {

				ar.setDelUserId(SecurityUtils.getUser().getId() + "");
				ar.setDelTime(LocalDateTime.now());
				ar.setDelFlag(1);
				illnessAdviceService.updateById(ar);

			});

		}

		return true;
	}

	/**
	 * 删除记录
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean delByUpdate(Integer id) {

		PigxUser user = SecurityUtils.getUser();
		//查询出对应的数据  进行删除
		IllnessNursingState byId = this.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		//查询关联的建议
		List<ProjectAdviceCorrelation> list = projectAdviceCorrelationService.list(Wrappers.<ProjectAdviceCorrelation>query().lambda()
				.eq(ProjectAdviceCorrelation::getDelFlag, 0)
				.eq(ProjectAdviceCorrelation::getProjectStateId, byId.getIllnessNursingStateId())
		);

		//当建议为空时  直接删除项目状态数据
		if (CollectionUtils.isEmpty(list)) {

			return this.updateById(byId);

		}

		//不为空时，循环删除建议的关联
		list.forEach(ar -> {

			ar.setDelFlag(1);
			ar.setDelTime(LocalDateTime.now());
			ar.setDelUserId(user.getId() + "");

		});

		//将关联删除
		projectAdviceCorrelationService.updateBatchById(list);

		return this.updateById(byId);
	}
}
