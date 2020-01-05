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


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessShowBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessType;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.assess.AssessProjectVo;
import com.pig4cloud.pigx.ccxxicu.mapper.assess.AssessTypeMapper;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectService;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessTypeService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评估类型表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:45:38
 */
@Service
public class AssessTypeServiceImpl extends ServiceImpl<AssessTypeMapper, AssessType> implements AssessTypeService {

	@Autowired
	private AssessProjectService assessProjectService;

	@Autowired
	private ProjectService projectService;





	/**
	 * 根据患者回填数据
	 * @param assessShowBo
	 * @return
	 */
	@Override
	public R getAssess(AssessShowBo assessShowBo) {
		Project project = projectService.selectByCode(assessShowBo.getProjectCode(), 4);

		if (project ==null) {
			return R.failed(1, "操作有误！");
		}
		List<AssessProjectVo> assessData = baseMapper.getAssessData(project.getProjectId(),assessShowBo.getPatientId());




		return R.ok(assessData);
	}

	@Override
	public List<AssessProjectVo> getAssessData(String projectId,String patientId) {
		return baseMapper.getAssessData(projectId,patientId);
	}



	/**
	 * 删除该类型  及该类型下的项目 及其条件
	 *
	 * @param assessType
	 * @return
	 */
	@Override
	public boolean updateAll(AssessType assessType) {

		this.updateById(assessType);
		//查询该类型下的项目  和条件
		List<AssessProject> type = assessProjectService.list(Wrappers
				.<AssessProject>query().lambda()
				.eq(AssessProject::getAssessTypeId, assessType.getAssessTypeId())
				.eq(AssessProject::getDelFlag, 0)
		);

		if (CollectionUtils.isNotEmpty(type)) {
			type.forEach(ar -> {
				//循环得到项目
				AssessProject assessProject = new AssessProject();
				BeanUtil.copyProperties(ar, assessProject);

				assessProject.setDelFlag(1);
				assessProject.setDelTime(LocalDateTime.now());
				assessProject.setDelUserId(assessType.getDelUserId());
				//删除该类型下的项目 及条件
				assessProjectService.deleteByUpdate(assessProject);
			});
		}
		return true;
	}

	/**
	 * 评估结构  多个记录结果
	 * @param assessRecord
	 * @return
	 */
	@Override
	public R selectAssess(AssessRecord assessRecord) {

		List<AssessProjectVo> assessData = baseMapper.assessRecord(assessRecord);

		return R.ok(assessData);
	}

}
