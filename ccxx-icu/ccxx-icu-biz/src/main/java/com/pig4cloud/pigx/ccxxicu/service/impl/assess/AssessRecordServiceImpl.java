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


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordPage;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.assess.AssessRecordShowVo;
import com.pig4cloud.pigx.ccxxicu.mapper.assess.AssessRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessRecordService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评估记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:58:53
 */
@Service
public class AssessRecordServiceImpl extends ServiceImpl<AssessRecordMapper, AssessRecord> implements AssessRecordService {

	@Autowired
	private AssessProjectRecordService assessProjectRecordService;


	/**
	 * 评估记录分页查询
	 * @param page
	 * @param assessRecordPage
	 * @return
	 */
	@Override
	public IPage<AssessRecordShowVo> selectByPage(Page page, AssessRecordPage assessRecordPage) {
		return baseMapper.selectByPage(page,assessRecordPage);
	}

	/**
	 * 修改评估记录
	 * @param assessRecordBo
	 * @return
	 */
	@Override
	public boolean updateDate(AssessRecordBo assessRecordBo) {

		PigxUser user = SecurityUtils.getUser();

		AssessRecord assessRecord = assessRecordBo.getAssessRecord();

		List<AssessProjectRecord> assessProjectRecords = assessRecordBo.getAssessProjectRecords();

		assessRecord.setUpdateUserId(user.getId()+"");

		assessProjectRecords.forEach(ar->{

			ar.setUpdateUserId(user.getId()+"");

		});

		this.updateById(assessRecord);

		return assessProjectRecordService.updateBatchById(assessProjectRecords);
	}

	/**
	 * 查询某患者的评估项目
	 * @param patientId
	 * @return
	 */
	@Override
	public List<Project> getPatientAssessProject(String patientId) {
		return baseMapper.getPatientAssessProject(patientId);
	}
}
