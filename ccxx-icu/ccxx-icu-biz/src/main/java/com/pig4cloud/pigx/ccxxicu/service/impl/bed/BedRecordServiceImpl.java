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
package com.pig4cloud.pigx.ccxxicu.service.impl.bed;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.BedRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.BedRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.PatientBedCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.BedRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.BedUseEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.bed.BedRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.bed.BedRecordService;
import com.pig4cloud.pigx.ccxxicu.service.bed.HospitalBedService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientBedCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 床位使用时间记录
 *
 * @author pigx code generator
 * @date 2019-08-07 21:23:37
 */
@Service
public class BedRecordServiceImpl extends ServiceImpl<BedRecordMapper, BedRecord> implements BedRecordService {

	@Autowired
	private  HospitalBedService hospitalBedService;

	@Autowired
	private  PatientService patientService;

	@Autowired
	private  PatientBedCorrelationService patientBedCorrelationService;


	/**
	 * 患者绑定床位的操作
	 * @param bedRecord
	 * @return
	 */
	@Override
	public boolean add(BedRecord bedRecord) {



		String bedId = bedRecord.getBedId();
		String patientId = bedRecord.getPatientId();

		PatientBedCorrelation msg = new PatientBedCorrelation();

		msg.setPatientId(patientId);

		PatientBedCorrelation patientBedCorrelation1 = patientBedCorrelationService.selectByCondition(msg);

		if (patientBedCorrelation1!= null) {

			return false;

		}

		//查询到该床位 并更改其床位状态
		HospitalBed hospitalBed = new HospitalBed();
		hospitalBed.setBedId(bedId);
		hospitalBed.setDeptId(bedRecord.getDeptId());
		List<HospitalBed> hospitalBeds = hospitalBedService.selectByFlag(hospitalBed);
		//床位不存在 或者 床位未处于 未使用状态
		if (hospitalBeds.isEmpty()||!(BedUseEnum.NOT_IN_USE.getCode().equals (hospitalBeds.get(0).getUseFlag()))) {

			return false;

		}

		//修改该床位的状态
		HospitalBed hospitalBed1 = hospitalBeds.get(0);
		hospitalBed1.setUseFlag(BedUseEnum.IN_USE.getCode());
		hospitalBed1.setUpdateUserId(bedRecord.getCreateUserId());
		boolean b = hospitalBedService.updateById(hospitalBed1);
		if (!b) {
			return false;

		}

		//查询该患者  修改患者床位信息
		Patient byPatientId = patientService.getByPatientId(patientId);
		byPatientId.setAdmissionBeds(bedId);
		patientService.updateById(byPatientId);

		//添加患者床位关联
		PatientBedCorrelation patientBedCorrelation = new PatientBedCorrelation();
		patientBedCorrelation.setBedId(bedId);
		patientBedCorrelation.setPatientId(patientId);
		boolean save = patientBedCorrelationService.save(patientBedCorrelation);
		if (!save) {
			return false;
		}
		//添加床位使用记录
		int insert = baseMapper.insert(bedRecord);
		if (insert >0) {
			return true;
		}

		return false;

	}

	/**
	 * 床位绑定修改（结束使用该床位）
	 * 出科时直接使用
	 * 其中需要对结束时间 修改人 进行封装
	 * @param patientId
	 * @return
	 */
	@Override
	public boolean delPatientBed(String  patientId) {
		System.out.println("开始断开床位。。。。");
		/**
		 * 删除关联  修改床位状态  修改床位使用记录的结束时间
		 */
		PigxUser user = SecurityUtils.getUser();

		Patient byPatientId = patientService.getByPatientId(patientId);
		if (byPatientId.getAdmissionBeds()==null||"".equals(byPatientId.getAdmissionBeds())){
			return true;
		}

		byPatientId.setDischargeBed(byPatientId.getAdmissionBeds());
		byPatientId.setAdmissionBeds("无");

		patientService.updateById(byPatientId);

		//查出该患者与床位的关联关系 并删除
		PatientBedCorrelation patientBedCorrelation = new PatientBedCorrelation();
		patientBedCorrelation.setPatientId(patientId);
		PatientBedCorrelation patientBedCorrelation1 = patientBedCorrelationService.selectByCondition(patientBedCorrelation);
		if (patientBedCorrelation1==null) {
			return true;
		}


		String bedId = patientBedCorrelation1.getBedId();

		boolean b = patientBedCorrelationService.removeById(patientBedCorrelation1.getId());
		if (!b) {
			return false;
		}
		//修改床位状态
		HospitalBed hospitalBed = new HospitalBed();
		hospitalBed.setBedId(bedId);
		hospitalBed.setDeptId(user.getDeptId()+"");
		List<HospitalBed> hospitalBeds = hospitalBedService.selectByFlag(hospitalBed);
		if (!hospitalBeds.isEmpty()) {
			//这里最多存在一条记录
			HospitalBed hospitalBed1 = hospitalBeds.get(0);
			hospitalBed1.setUseFlag(BedUseEnum.NOT_IN_USE.getCode());
			hospitalBed1.setUpdateUserId(user.getId()+"");
			boolean b1 = hospitalBedService.updateById(hospitalBed1);
			if (!b1) {
				return false;
			}
		}
		//对床位使用记录进行修改
		BedRecord bedRecord = new BedRecord();
		bedRecord.setDeptId(user.getDeptId()+"");
		bedRecord.setBedId(bedId);
		bedRecord.setDelFlag(0);
		bedRecord.setPatientId(patientId);
		List<BedRecord> bedRecords = this.selectByCondition(bedRecord);
		if (!bedRecords.isEmpty()) {

			BedRecord byId = bedRecords.get(0);

			//  床位使用记录添加结束时间
			byId.setEndTime(LocalDateTime.now());
			byId.setUpdateUserId(user.getId()+"");
			long totalTime = ChronoUnit.HOURS.between(byId.getStartTime(), byId.getEndTime());
			byId.setTotalTime(totalTime+"");
			boolean update = this.updateById(byId);
			if (update) {

				return true;

			}

		}
		System.out.println("床位断开完成-----");
		return true;
	}

	/**
	 * 根据患者和床位id和记录id（生成）组合  查询
	 * @param record
	 * @return
	 */
	@Override
	public List<BedRecord> selectByCondition(BedRecord record) {
		return baseMapper.selectByCondition(record);
	}

	/**
	 * 分页查询床位使用记录
	 * @param page
	 * @param bedRecordBo
	 * @return
	 */
	@Override
	public IPage<BedRecordVo> selectByPage(Page page, BedRecordBo bedRecordBo) {

		/**
		 * 这里处理了一下时间格式
		 * 将开始时间强转为那一天的零点  00:00:00
		 * 将结束时间强转为那一天的 23：59：59
		 */
		LocalDateTime firstTime = bedRecordBo.getFirstTime();

		LocalDateTime lastTime = bedRecordBo.getLastTime();

		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		if (firstTime != null) {

			String s = firstTime.toString();

			String substring = s.substring(0, 10);

			String s1 = substring + " 00:00:00";

			firstTime = LocalDateTime.parse(s1, df);

		}

		if (lastTime != null) {

			String s = lastTime.toString();

			String substring = s.substring(0, 10);

			String s1 = substring + " 23:59:59";

			lastTime = LocalDateTime.parse(s1, df);

		}

		bedRecordBo.setFirstTime(firstTime);
		bedRecordBo.setLastTime(lastTime);

		return baseMapper.selectByPage(page,bedRecordBo);
	}

	/**
	 * 给患者修改床位
	 * @param bedRecord
	 * @return
	 */
	@Override
	public R changeBed(BedRecord bedRecord) {

		PigxUser user = SecurityUtils.getUser();
		//查询到该床位 并更改其床位状态
		HospitalBed hospital = new HospitalBed();
		hospital.setBedId(bedRecord.getBedId());
		hospital.setDeptId(user.getDeptId()+"");
		List<HospitalBed> hospitalBeds = hospitalBedService.selectByFlag(hospital);
		//床位不存在 或者 床位未处于 未使用状态
		if (hospitalBeds.isEmpty()||!(BedUseEnum.NOT_IN_USE.getCode().equals (hospitalBeds.get(0).getUseFlag()))) {

			return R.failed(1,"该床位已被占用！");

		}
		//查询该患者和床位的关联关系  并删除
		PatientBedCorrelation patientBedCorrelation = new PatientBedCorrelation();

		patientBedCorrelation.setPatientId(bedRecord.getPatientId());
		//查询到原关联数据
		PatientBedCorrelation patientBedCorrelation1 = patientBedCorrelationService.selectByCondition(patientBedCorrelation);
		//将之前的患者和床位的关联删除  并修改床位的状态
		if (patientBedCorrelation1!=null) {

			patientBedCorrelationService.removeById(patientBedCorrelation1.getId());
			String bedId = patientBedCorrelation1.getBedId();
			List<HospitalBed> list = hospitalBedService.list(Wrappers.<HospitalBed>query().lambda()
					.eq(HospitalBed::getBedId, bedId)
					.eq(HospitalBed::getDelFlag, 0)
			);

			if (!CollectionUtils.isEmpty(list)) {
				HospitalBed hospitalBed = list.get(0);
				hospitalBed.setUseFlag(BedUseEnum.NOT_IN_USE.getCode());
				hospitalBed.setUpdateUserId(user.getId()+"");
				hospitalBedService.updateById(hospitalBed);
			}
		}
		//将之前的床位使用记录结束
		BedRecord record = new BedRecord();
		record.setPatientId(bedRecord.getPatientId());
		//查询原数据 这里的条件是 根据患者 和未有结束时间的数据
		List<BedRecord> bedRecords = this.selectByCondition(record);
		if (bedRecords.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		BedRecord byId = bedRecords.get(0);

		//进行 关联记录删除 床位状态改变  床位使用记录添加结束时间
		byId.setEndTime(LocalDateTime.now());
		byId.setUpdateUserId(user.getId()+"");
		long totalTime = ChronoUnit.HOURS.between(byId.getStartTime(), byId.getEndTime());
		byId.setTotalTime(totalTime+"");
		boolean update = this.updateById(byId);
		if (!update) {

			return R.failed(1, "操作有误！");

		}
		//添加新的关联和记录
		record.setStartTime(LocalDateTime.now());
		record.setBedId(bedRecord.getBedId());

		record.setDelFlag(0);
		record.setCreateUserId(user.getId()+"");
		record.setCreateTime(LocalDateTime.now());
		record.setDeptId(user.getDeptId()+"");
		long id = SnowFlake.getId();
		record.setBedRecordId(id+"");

		return R.ok(this.add(record));
	}
}
