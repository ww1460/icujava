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
package com.pig4cloud.pigx.ccxxicu.service.impl.nurse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.NursePatientRecordEnum;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NursePatientCorrelationMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NursePatientRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 护士看护患者记录表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:12:54
 */
@Service
public class NursePatientRecordServiceImpl extends ServiceImpl<NursePatientRecordMapper, NursePatientRecord> implements NursePatientRecordService {

	@Autowired
	private NursePatientRecordMapper nursePatientRecordMapper;

	@Autowired
	private NursePatientCorrelationMapper nursePatientCorrelationMapper;

	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 条件分页查询数据
	 *
	 * @param page
	 * @param nursePatientRecord
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, NursePatientRecord nursePatientRecord) {
		return nursePatientRecordMapper.selectPaging(page, nursePatientRecord);
	}

	/**
	 * 条件查询数据源
	 *
	 * @param nursePatientRecord
	 * @return
	 */
	@Override
	public List<NursePatientRecord> selectAll(NursePatientRecord nursePatientRecord) {
		return nursePatientRecordMapper.selectAll(nursePatientRecord);
	}

	/**
	 * 护士患者断开关联
	 *
	 * @param nursePatientRecord
	 * @return
	 */
	@Override
	public Boolean disconnection(NursePatientRecord nursePatientRecord) {

		// 通过患者id 护士is 及科室id查询到当条数据的信息
		List<NursePatientRecord> nursePatientRecords = nursePatientRecordMapper.selectAll(nursePatientRecord);
		if (CollectionUtils.isEmpty(nursePatientRecords)) {

			return false;
		}

		NursePatientRecord byId = nursePatientRecords.get(0);

		byId.setEndTime(LocalDateTime.now()); // 结束时间
		/* 计算照顾多长时间多长时间 */
		long totalTime = ChronoUnit.HOURS.between(byId.getStartTime(), byId.getEndTime());
		byId.setTotalTime(totalTime + "");
		int i = nursePatientRecordMapper.updateById(byId);
		if (i == 0) {
			return false;
		}
		/* 修改完成后，删除护士与患者的关联数据源 */
		NursePatientCorrelation nursePatientCorrelation = new NursePatientCorrelation();//护士患者关联表
		nursePatientCorrelation.setNurseId(byId.getNurseId()); // 护士姓名
		nursePatientCorrelation.setPatientId(byId.getPatientId()); // 患者姓名
		nursePatientCorrelation.setDeptId(byId.getDeptId());//科室id
		List<NursePatientCorrelationVo> nursePatientCorrelationsList = nursePatientCorrelationMapper.selectAll(nursePatientCorrelation);
		if (nursePatientCorrelationsList.size() > 0) {
			int i1 = nursePatientCorrelationMapper.deleteById(nursePatientCorrelationsList.get(0).getId());
			if (i1 == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 护士与患者产生关联
	 *
	 * @param nursePatientRecord
	 * @return
	 */
	@Override
	public R nurseRelationPatient(NursePatientRecord nursePatientRecord) {

		if (nursePatientRecord.getOnlyDutyNurse() == null) {

			return R.failed("系统错误：未获得护士职务！请联系管路员。");

		}

		/*填充基本数据 */
		nursePatientRecord.setStartTime(LocalDateTime.now());//开始关联 时间
		nursePatientRecord.setFounder(SecurityUtils.getUser().getId() + ""); //创建关联关系的人
		nursePatientRecord.setDeptId(SecurityUtils.getUser().getDeptId() + "");//科室

		/* 判断一下有没有传来护士id如果没选择当前登录护士 */
		if (nursePatientRecord.getNurseId() == null || nursePatientRecord.equals("")) {
			nursePatientRecord.setNurseId(SecurityUtils.getUser().getId() + "");
		}

		/* 判断一下当前患者于护士是否已经产生关联关系了 */
		NursePatientCorrelation npcorrelation = new NursePatientCorrelation();
		npcorrelation.setPatientId(nursePatientRecord.getPatientId()); //患者id
		npcorrelation.setNurseId(nursePatientRecord.getNurseId()); //护士id
		npcorrelation.setDeptId(SecurityUtils.getUser().getDeptId() + "");   //科室id

		List<NursePatientCorrelationVo> nursePatientCorrelationVos = nursePatientCorrelationMapper.selectAll(npcorrelation);

		if (CollectionUtils.isNotEmpty(nursePatientCorrelationVos)) {
			return R.failed(null, "当前护士与患者已经有关联关系 ");
		}else{
			//护士标识
			npcorrelation.setOnlyDutyNurse(nursePatientRecord.getOnlyDutyNurse());

		}

		/* 判断给患者添加的是否为责任护士*/
		if (nursePatientRecord.getOnlyDutyNurse().equals(NursePatientRecordEnum.RESPONSIBLE_NURSE.getCode())) {
			/*如果给当前患者添加的是责任护士时，查询一下当前患者是否有责任护士，有的情况提示*/
			NursePatientCorrelation npcorrelations = new NursePatientCorrelation();
			npcorrelations.setPatientId(nursePatientRecord.getPatientId()); //患者id
			npcorrelations.setOnlyDutyNurse(NursePatientRecordEnum.RESPONSIBLE_NURSE.getCode());//责任护士
			List<NursePatientCorrelationVo> nursePatientCorrelations = nursePatientCorrelationMapper.selectAll(npcorrelations);
			if (CollectionUtils.isNotEmpty(nursePatientCorrelations)) {
				return R.failed(null, "当前患者已经有责任护士 ");
			}
		}

		/**
		 * 产生关联记录的同时在关联表 中也要产生相应的一条记录
		 */
		int insert1 = nursePatientRecordMapper.insert(nursePatientRecord);
		if (insert1 == 0) {
			return R.failed(null, "新增护士与患者记录表关联失败");
		}
		int insert = nursePatientCorrelationMapper.insert(npcorrelation);
		if (insert == 0) {
			return R.failed(null, "新增护士与患者关联表失败");
		}


		return R.ok();
	}

	/**
	 * 利用患者id，查询看护当前患者的护士数据 用于出科
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public Boolean stopNursePatient(String patientId) {

		/* 利用患者id 查询与多少护士关联关系 */
		Boolean aBoolean = nursePatientCorrelationService.stopNursePatient(patientId);
		/* 没有关联时直接返回真 */
		if (!aBoolean) {
			return false;
		}

		/* 通过患者id查询 当前患者当前有多少护士有关联记录 */
		List<NursePatientRecord> nursePatientRecords = nursePatientRecordMapper.currentCarePatientNurse(patientId);
		if (CollectionUtils.isNotEmpty(nursePatientRecords)) {
			nursePatientRecords.forEach(e -> {
				e.setEndTime(LocalDateTime.now()); // 结束时间
				/* 计算照顾多长时间多长时间 */
				long totalTime = ChronoUnit.HOURS.between(e.getStartTime(), e.getEndTime());
				e.setTotalTime(totalTime + "");
				e.setDelFlag(1);//删除
			});
			return this.updateBatchById(nursePatientRecords);
		} else {
			return true;
		}
	}

	/**
	 * 患者修改看护护士
	 *
	 * @param nursePatientRecord
	 * @return
	 */
	@Override
	public Boolean nursePatientupdate(NursePatientRecord nursePatientRecord) {

		Boolean disconnection = this.disconnection(nursePatientRecord);
		if (disconnection == false) {
			return false;
		}
		nursePatientRecord.setStartTime(LocalDateTime.now()); //开始时间
		nursePatientRecord.setDeptId(SecurityUtils.getUser().getDeptId() + "");//科室id
		boolean save = this.save(nursePatientRecord);
		if (save == false) {
			return false;
		}
		return null;
	}
}
