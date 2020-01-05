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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.assess.AssessProjectRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评估项目记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:58:49
 */
@Service
public class AssessProjectRecordServiceImpl extends ServiceImpl<AssessProjectRecordMapper, AssessProjectRecord> implements AssessProjectRecordService {

	@Autowired
	private AssessRecordService assessRecordService;

	@Autowired
	private ProjectRecordService projectRecordService;



	@Autowired
	private ProjectService projectService;




	/**
	 * 新增评估结果及其记录
	 * @param assessRecordBo
	 * @return
	 */
	@Override
	public boolean add(AssessRecordBo assessRecordBo) {

		PigxUser user = SecurityUtils.getUser();

		AssessRecord assessRecord = assessRecordBo.getAssessRecord();
		long id = SnowFlake.getId();
		//新增评估结果
		assessRecord.setAssessRecordId(id+"");
		assessRecord.setCreateTime(LocalDateTime.now());
		assessRecord.setCreateUserId(user.getId()+"");
		assessRecord.setDelFlag(0);
		assessRecord.setDeptId(user.getDeptId()+"");

		List<AssessProjectRecord> assessProjectRecords = assessRecordBo.getAssessProjectRecords();

		if (CollectionUtils.isEmpty(assessProjectRecords)) {

			return assessRecordService.save(assessRecord);

		}

		boolean save = assessRecordService.save(assessRecord);

		if (!save) {

			return false;

		}
		//循环新增评估项目记录
		assessProjectRecords.forEach(ar->{

			ar.setAssessProjectRecordId(SnowFlake.getId()+"");
			ar.setCreateTime(LocalDateTime.now());
			ar.setCreateUserId(user.getId()+"");
			ar.setDelFlag(0);
			ar.setAssessRecordId(id+"");
			ar.setDeptId(user.getDeptId()+"");
			ar.setPatientId(assessRecord.getPatientId());

		});
		if (assessRecord.getAssessResult()==null) {

			return this.saveBatch(assessProjectRecords);

		}

		//添加项目
		ProjectRecord projectRecord = new ProjectRecord();

		projectRecord.setProjectSpecificRecord(assessRecord.getAssessResult()+ (StringUtils.isEmpty(assessRecord.getRemarks())?"":assessRecord.getRemarks()));

		projectRecord.setSourceId(assessRecord.getAssessRecordId());

		projectRecord.setSource(DataSourceEnum.ASSESS.getCode());

		projectRecord.setProjectId(assessRecord.getProjectId());
		projectRecord.setPatientId(assessRecord.getPatientId());

		projectRecord.setDeptId(assessRecord.getDeptId());

		projectRecordService.add(projectRecord);

		Project project = projectService.selectByProjectId(assessRecord.getProjectId());

		NursingRecord nursingRecord = new NursingRecord();

		nursingRecord.setRecordContent(project.getProjectName()+":"+ assessRecord.getAssessResult());

		return this.saveBatch(assessProjectRecords);
	}

	/**
	 * 获取该记录的项目
	 * @param assessRecordId
	 */
	@Override
	public List<AssessProjectRecord> getAssessRecord(String assessRecordId) {

		if (StringUtils.isEmpty(assessRecordId)) {
			return null;
		}


		List<AssessProjectRecord> list = this.list(Wrappers.<AssessProjectRecord>query().lambda()
				.eq(AssessProjectRecord::getAssessRecordId, assessRecordId)
				.eq(AssessProjectRecord::getDelFlag, 0));

		return list;

	}

	/**
	 * 获取某患者某项目的最新的一条记录
	 * @param assessProjectId
	 * @param patientId
	 * @return
	 *//*
	@Override
	public List<AssessProjectRecord> selectNewRecord(String assessProjectId,String projectId, String patientId) {

		if (StringUtils.isEmpty(assessProjectId)) {
			return null;
		}
		if (StringUtils.isEmpty(projectId)) {
			return null;
		}
		if (StringUtils.isEmpty(patientId)) {
			return null;
		}

		return baseMapper.selectNewRecord(assessProjectId, projectId,patientId);
	}*/
}
