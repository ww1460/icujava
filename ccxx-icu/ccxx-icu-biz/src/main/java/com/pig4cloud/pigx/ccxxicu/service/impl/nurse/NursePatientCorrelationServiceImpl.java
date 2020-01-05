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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientInfoBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientInfo;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NursePatientCorrelationMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientBedCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * 患者和护士关联表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:08:26
 */
@Service
public class NursePatientCorrelationServiceImpl extends ServiceImpl<NursePatientCorrelationMapper, NursePatientCorrelation> implements NursePatientCorrelationService {


	@Autowired
	private NursePatientCorrelationMapper nursePatientCorrelationMapper;

	@Autowired
	private PatientService patientService;

	@Autowired
	private PatientBedCorrelationService patientBedCorrelationService;

	@Autowired
	private NurseService nurseService;

	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;




	/**
	 * 条件数据全查
	 *
	 * @param nursePatientCorrelation
	 * @return
	 */
	@Override
	public List<NursePatientCorrelationVo> selectAll(NursePatientCorrelation nursePatientCorrelation) {
		//根据条件查询出所有的关联
		List<NursePatientCorrelationVo> nursePatientCorrelationVos = nursePatientCorrelationMapper.selectAll(nursePatientCorrelation);
		Vector<Thread> threadVector = new Vector<>();
		//将上述的数据遍历
		nursePatientCorrelationVos.forEach(ar -> {
			Thread thread = new Thread() {

				@Override
				public void run() {
					//将患者详细数据进行封装
					ar.setPatient(patientService.getByPatientId(ar.getPatientId()));
					super.run();
				}
			};

			threadVector.add(thread);
			thread.start();
		});
		//等待所有的线程结束
		threadVector.forEach(ar -> {

			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		return nursePatientCorrelationVos;
	}

	/**
	 * 查询患者的责任护士
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public String selectDutyNurseId(String patientId) {
		if (StringUtils.isEmpty(patientId)) {
			return null;
		}
		NursePatientCorrelation nursePatientCorrelation = nursePatientCorrelationMapper.responsibleNurse(patientId);
		if (nursePatientCorrelation==null){
			return null;
		}
		return nursePatientCorrelationMapper.responsibleNurse(patientId).getNurseId();
	}

	/**
	 * 利用患者id，查询看护当前患者的护士数据 用于出科
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public Boolean stopNursePatient(String patientId) {
		/* 利用患者id 查询与多少护士关联 */

		List<NursePatientCorrelation> correlationList = nursePatientCorrelationMapper.currentCarePatientNurse(patientId);
		if (CollectionUtils.isNotEmpty(correlationList)) {
			/* 当数据不为空的时候删除掉这些数据 */
			correlationList.forEach(e -> {
				this.removeById(e.getId());
			});
			return true;
		} else {
			return true;
		}

	}

	/**
	 * 查询对应的患者id
	 * @param patientInfoBo
	 * @return
	 */
	@Override
	public List<PatientInfo> getPatientId(PatientInfoBo patientInfoBo) {
		//获取该用户可看的患者id
		List<String> patientId = baseMapper.getPatientId(patientInfoBo);
		//查询每个患者的对应的信息
		if (CollectionUtils.isEmpty(patientId)) {
			return null;
		}

		List<PatientInfo> patientInfos = new ArrayList<>();

		Vector<Thread> threadVector = new Vector<>();

		patientId.forEach(ar->{

			Thread thread = new Thread(){

				@Override
				public void run() {

					/*PatientInfo patientInfo = new PatientInfo();
					//患者信息
					Patient byPatientId = patientService.getOne(Wrappers.<Patient>query()
							.lambda().eq(Patient::getPatientId, ar));

					//床位信息
					HospitalBed patientBed = patientBedCorrelationService.getPatientBed(ar);

					//查询患者的所有的关联护士信息
					//首先查询患者的责任护士
					NursePatientCorrelation nursePatientCorrelation = baseMapper.responsibleNurse(ar);

					if (nursePatientCorrelation!= null) {

						Nurse nurse = nurseService.selectByUserId(nursePatientCorrelation.getNurseId());
						patientInfo.setNurse(nurse);
					}

					//查询辅助护士
					List<Nurse> patientNurse = nurseService.getPatientNurse(ar);

					patientInfo.setPatient(byPatientId);
					patientInfo.setHospitalBed(patientBed);
					patientInfo.setNurseList(patientNurse);
					patientInfo.setIndex(byPatientId.getId());*/
					PatientInfo patientInfo = nursePatientCorrelationService.selectPatientId(ar);
					patientInfos.add(patientInfo);

					super.run();
				}
			};

			thread.start();
			threadVector.add(thread);
		});

		threadVector.forEach(ar->{

			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		});
		if (CollectionUtils.isNotEmpty(patientInfos)) {
			try {
				patientInfos.sort(Comparator.comparing(PatientInfo::getIndex));
			} catch (NullPointerException  e) {
			}
		}


		return patientInfos;
	}


	@Override
	public PatientInfo selectPatientId(String patientId) {

		if (StringUtils.isEmpty(patientId)) {
			return null;
		}

		PatientInfo patientInfo = new PatientInfo();
		//患者信息
		Patient byPatientId = patientService.getOne(Wrappers.<Patient>query()
				.lambda().eq(Patient::getPatientId, patientId));

		//床位信息
		HospitalBed patientBed = patientBedCorrelationService.getPatientBed(patientId);

		//查询患者的所有的关联护士信息
		//首先查询患者的责任护士
		NursePatientCorrelation nursePatientCorrelation = baseMapper.responsibleNurse(patientId);

		if (nursePatientCorrelation!= null) {

			Nurse nurse = nurseService.selectByUserId(nursePatientCorrelation.getNurseId());
			patientInfo.setNurse(nurse);
		}

		//查询辅助护士
		List<Nurse> patientNurse = nurseService.getPatientNurse(patientId);

		patientInfo.setPatient(byPatientId);
		patientInfo.setHospitalBed(patientBed);
		patientInfo.setNurseList(patientNurse);
		patientInfo.setIndex(byPatientId.getId());
		patientInfo.setPatientIndex(byPatientId.getPatientId());
		return patientInfo;
	}
}
