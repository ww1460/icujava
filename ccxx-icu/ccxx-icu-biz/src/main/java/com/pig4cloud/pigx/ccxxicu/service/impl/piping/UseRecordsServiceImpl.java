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
package com.pig4cloud.pigx.ccxxicu.service.impl.piping;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessShowBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipUseTime;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.UseRecordVo;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.UseRecordsMapper;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessRecordService;
import com.pig4cloud.pigx.ccxxicu.service.piping.UseRecordsService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 管道使用记录
 *
 * @author pigx code generator
 * @date 2019-08-08 16:23:23
 */
@Service
public class UseRecordsServiceImpl extends ServiceImpl<UseRecordsMapper, UseRecord> implements UseRecordsService {

	@Autowired
	private UseRecordsMapper useRecordsMapper;
	@Autowired
	private AssessRecordService assessRecordService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UseRecordsService useRecordsService;
	/**
	 * 查询正在连接中的设备
	 * @param useRecord
	 * @return
	 */
	@Override
	public List<UseRecordVo> selectInConnected(UseRecord useRecord) {

		List<UseRecordVo> useRecordVos = useRecordsMapper.selectInConnected(useRecord);

		if (CollectionUtils.isEmpty(useRecordVos)){
			return useRecordVos;
		}
		AssessShowBo assessShowBo = new AssessShowBo();
		useRecordVos.forEach(e->{
			assessShowBo.setPatientId(e.getPatientId());
			String s = useRecordsService.assessResult(assessShowBo,e.getUseRecordsId());
			if (s!=null){
				e.setAssessmentScore(s);
			}

		});

		return useRecordVos;
	}

	/**
	 * 分页查询数据源
	 * @param page
	 * @param useRecord
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, UseRecord useRecord) {
		return useRecordsMapper.selectPaging(page, useRecord);
	}

	/**
	 * 全查数据
	 * @param useRecords
	 * @return
	 */
	@Override
	public List<UseRecordVo> selectAll(UseRecordVo useRecords) {
		return useRecordsMapper.selectAll(useRecords);
	}

	/**
	 * 条件数据查询  查询未出科的患者
	 * @param useRecords
	 * @return
	 */
	@Override
	public List<UseRecordVo> selectUsedAll(UseRecordVo useRecords) {
		return useRecordsMapper.selectUsedAll(useRecords);
	}

	/**
	 * 查询管路的最后一次评估结果展示在页面
	 * @param assessShowBo
	 * @return
	 */
	@Override
	public String assessResult(AssessShowBo assessShowBo,String useRecordsId) {
		/* 查询项目名称 */
		Project project = projectService.selectByCode("slippage",null);
		//返回记录中该患者的记录数据
		if (project !=null){
		List<AssessRecord> list = assessRecordService.list(Wrappers.<AssessRecord>query().lambda()
				.eq(AssessRecord::getPatientId, assessShowBo.getPatientId())
				.eq(AssessRecord::getProjectId, project.getProjectId())
				.eq(AssessRecord::getDelFlag, 0)
				.eq(AssessRecord::getRemarks,useRecordsId)
				.orderByDesc(AssessRecord::getCreateTime));
			if (CollectionUtils.isEmpty(list)){
				return "";
			}
			return list.get(0).getAssessResult();

		}else{
			return "";
		}

	}

	/**
	 * 通过患者id查询当前患者连接的管道，用于患者出科
	 * @param pagientId
	 * @return
	 */
	@Override
	public Boolean stopPiping(String pagientId) {
		/*  利用患者id查询数据*/
		List<UseRecord> useRecords = useRecordsMapper.stopPiping(pagientId);
		/* 有关联的管路时，断开管道 */
		if (CollectionUtils.isNotEmpty(useRecords)){
			useRecords.forEach(e->{
				e.setEndTime(LocalDateTime.now());//结束时间
				e.setNurseId(SecurityUtils.getUser().getId()+"");//护士id
			});
			return this.updateBatchById(useRecords);
		}else{
			/* 没有关联管路时 */
			return true;
		}

	}

	/**
	 * 通过科室查询 某条管道 的今天的使用记录
	 * @param useRecord
	 * @return
	 */
	@Override
	public Integer pipingUse(UseRecord useRecord) {
		return useRecordsMapper.pipingUse(useRecord);
	}

	/**
	 * 查询某患者的已使用的管路开始使用的时间
	 * @param patientId
	 * @return
	 */
	@Override
	public List<PipUseTime> ReportTwo(String patientId) {
		return baseMapper.ReportTwo(patientId);
	}
}
