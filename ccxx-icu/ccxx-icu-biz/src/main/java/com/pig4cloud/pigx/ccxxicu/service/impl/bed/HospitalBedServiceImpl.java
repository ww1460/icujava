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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.HospitalBedVo;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.PatientBedCorrelation;
import com.pig4cloud.pigx.ccxxicu.mapper.bed.HospitalBedMapper;
import com.pig4cloud.pigx.ccxxicu.service.bed.HospitalBedService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientBedCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 床位表
 *
 * @author yyj
 * @date 2019-08-07 21:24:05
 */
@Service
public class HospitalBedServiceImpl extends ServiceImpl<HospitalBedMapper, HospitalBed> implements HospitalBedService {

	@Autowired
	private PatientBedCorrelationService patientBedCorrelationService;

	@Autowired
	private PatientService patientService;



	/**
	 * 查询某科室下  某状态的床位
	 *
	 * @param hospitalBed
	 * @return
	 */
	@Override
	public List<HospitalBedVo> selectByFlags(HospitalBed hospitalBed) {

		List<HospitalBed> hospitalBeds = baseMapper.selectByFlag(hospitalBed);
		List<HospitalBedVo> hospitalBedVos = new ArrayList<>();

		hospitalBeds.forEach(ar -> {
			//判断 将使用中的床位 中的患者信息绑定
			HospitalBedVo hospitalBedVo = new HospitalBedVo();
			BeanUtil.copyProperties(ar, hospitalBedVo);
			if (ar.getUseFlag() == 1) {
				PatientBedCorrelation patientBedCorrelation = new PatientBedCorrelation();
				patientBedCorrelation.setBedId(ar.getBedId());
				PatientBedCorrelation patientBedCorrelation1 = patientBedCorrelationService.selectByCondition(patientBedCorrelation);
				if (patientBedCorrelation1==null) {

					ar.setUseFlag(0);

					this.updateById(ar);

					return;

				}
				hospitalBedVo.setPatientId(patientBedCorrelation1.getPatientId());
				Patient byPatientId = patientService.getByPatientId(patientBedCorrelation1.getPatientId());
				hospitalBedVo.setPatient(byPatientId);

			}
			hospitalBedVos.add(hospitalBedVo);
		});

		return hospitalBedVos;
	}

	/**
	 * 查询某科室下  某状态的床位
	 *
	 * @param hospitalBed
	 * @return
	 */
	@Override
	public List<HospitalBed> selectByFlag(HospitalBed hospitalBed) {

		return baseMapper.selectByFlag(hospitalBed);

	}

	@Override
	public IPage<HospitalBed> selectByPage(Page page, HospitalBed hospitalBed) {
		return baseMapper.selectByPage(page,hospitalBed);
	}
}
