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
package com.pig4cloud.pigx.ccxxicu.service.impl.patient;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.TransferRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import com.pig4cloud.pigx.ccxxicu.api.vo.pressuresore.RecorderVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.ProjectTypeEnum;
import com.pig4cloud.pigx.ccxxicu.mapper.patient.TransferRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.patient.TransferRecordService;
import com.pig4cloud.pigx.ccxxicu.service.pressuresore.RecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 患者转科交接记录
 *
 * @author pigx code generator
 * @date 2019-10-04 15:06:56
 */
@Service
public class TransferRecordServiceImpl extends ServiceImpl<TransferRecordMapper, TransferRecord> implements TransferRecordService {


	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectRecordService projectRecordService;

	@Autowired
	private RecordService recordService;

	@Autowired
	private ProjectRecordValueService projectRecordValueService;


	/**
	 * 查询该患者的历史数据进行回填
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public TransferRecord oldData(String patientId) {

		TransferRecord transferRecord = new TransferRecord();
		//查询该患者各项目的最新一次数据
		String t = this.selectDate("T", patientId);
		transferRecord.setPatientTemperature(t);
		String p = this.selectDate("P", patientId);
		transferRecord.setPatientPulse(p);
		String r = this.selectDate("R", patientId);
		transferRecord.setPatientBreathe(r);
		String bp = this.selectDate("BP", patientId);
		transferRecord.setPatientBloodPressure(bp);
		String copt = this.selectDate("COPT", patientId);
		transferRecord.setPatientPain(copt);
		String ys = this.getDate("意识", patientId);
		String sz = this.getDate("神志", patientId);
		if (StringUtils.isEmpty(ys)) {

			transferRecord.setPatientConsciousness(sz);
		} else {
			transferRecord.setPatientConsciousness(ys);
		}
		RecorderVo oneRecord = recordService.getOneRecord(patientId);
		if (oneRecord != null) {
			String byStages = oneRecord.getByStages();
			String prePosition = oneRecord.getPrePosition();
			String soreRange = oneRecord.getSoreRange();
			transferRecord.setPatientPressure("有");

			StringBuffer msg = new StringBuffer();
			msg.append(byStages==null?"":(byStages+";"));
			msg.append(prePosition==null?"":(prePosition+";"));
			msg.append(soreRange==null?"":(soreRange+";"));
			transferRecord.setPatientPressureRemarks(msg+"");

		}

		return transferRecord;
	}

	/**
	 * 查询历史数据
	 *
	 * @return
	 */
	private String selectDate(String projectCode, String patientId) {
		//查询对应的项目数据
		Project project = projectService.selectByCode(projectCode, ProjectTypeEnum.VITAL_SIGN_PROJECT.getCode());
		if (project != null) {
			//查询该项目的最新一次数据
			ProjectRecord oneRecord = projectRecordService.getOneRecord(patientId, project.getProjectId());
			if (oneRecord != null) {
				//判断是否为固定值项目
				if (project.getProjectRecordValueFlag().equals(0)) {

					List<ProjectRecordValue> list = projectRecordValueService.list(Wrappers.<ProjectRecordValue>query().lambda()
							.eq(ProjectRecordValue::getDelFlag, 0)
							.eq(ProjectRecordValue::getProjectId, project.getProjectId())
							.eq(ProjectRecordValue::getCreateTime, oneRecord.getProjectValue())

					);
					if (CollectionUtils.isNotEmpty(list)) {

						return list.get(0).getRecordValue();
					}
				}
				//直接返回值
				return oneRecord.getProjectValue();
			}
		}

		return null;
	}

	/**
	 * 查询历史数据
	 *
	 * @return
	 */
	private String getDate(String projectName, String patientId) {

		//查询对应的项目数据
		Project project = projectService.selectByName(projectName);
		if (project != null) {
			//查询该项目的最新一次数据
			ProjectRecord oneRecord = projectRecordService.getOneRecord(patientId, project.getProjectId());
			if (oneRecord != null) {
				//判断是否为固定值项目
				if (project.getProjectRecordValueFlag().equals(0)) {

					List<ProjectRecordValue> list = projectRecordValueService.list(Wrappers.<ProjectRecordValue>query().lambda()
							.eq(ProjectRecordValue::getDelFlag, 0)
							.eq(ProjectRecordValue::getProjectId, project.getProjectId())
							.eq(ProjectRecordValue::getCreateTime, oneRecord.getProjectValue())
					);
					if (CollectionUtils.isNotEmpty(list)) {

						return list.get(0).getRecordValue();
					}
				}
				return oneRecord.getProjectValue();
			}
		}
		return null;
	}
}
