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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceDictionaries;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.DispensingDrugMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceDictionariesService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.DispensingDrugService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配药
 *
 * @author pigx code generator
 * @date 2019-09-05 15:53:09
 */
@Service
public class DispensingDrugServiceImpl extends ServiceImpl<DispensingDrugMapper, DispensingDrug> implements DispensingDrugService {


	/**
	 * 医嘱
	 */
	@Autowired
	private HisDoctorsAdviceService hisDoctorsAdviceService;
	/**
	 * 护士与患者关联
	 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 患者
	 */
	@Autowired
	private PatientService patientService;
	/**
	 * 医嘱字典表
	 */
	@Autowired
	private DoctorsAdviceDictionariesService doctorsAdviceDictionariesService;

	/**
	 * 医嘱药物
	 */
	@Autowired
	private HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	@Resource
	private DispensingDrugMapper dispensingDrugMapper;

	/**
	 * 医嘱配药页面的展示
	 * @return
	 */
	@Override
	public Map<String, Object> dispensingDrugPage(String doctorsAdviceId) {

		Map<String,Object> test = new HashMap<>();

		/* 通过前台传来的医嘱id查询相应的数据 */
		HisDoctorsAdviceVo doctorsAdviceVo = hisDoctorsAdviceService.getDoctorsAdviceId(doctorsAdviceId);
		if (doctorsAdviceVo!=null){
			/* 通过一条医嘱的数据查询当条医嘱相关的所有内容 */
			List<HisDoctorsAdviceProject> drugsList = hisDoctorsAdviceProjectService.selectBatchNumber(doctorsAdviceVo.getBatchNumber());
			test.put("doctorsAdvice",doctorsAdviceVo);
			test.put("drugsList",drugsList);


		if (doctorsAdviceVo.getPatientId()!=null){

		/*通过医嘱中的患者信息查询当前患者关联的护士*/
		NursePatientCorrelation nursePatientCorrelation = new NursePatientCorrelation();
		nursePatientCorrelation.setPatientId(doctorsAdviceVo.getPatientId());
		List<NursePatientCorrelationVo> nursePatientCorrelationVos = nursePatientCorrelationService.selectAll(nursePatientCorrelation);

			if (CollectionUtils.isEmpty(nursePatientCorrelationVos)){

			}else{
				test.put("nursePatientCorrelation",nursePatientCorrelationVos);
			}

		/* 取出患者的过敏史 */
		String allergichistory = patientService.getByPatientId(doctorsAdviceVo.getPatientId()).getAllergichistory();
		if (allergichistory !=null){
			test.put("allergichistory",allergichistory);
		}else{
			test.put("allergichistory","");
		}
		}
		/* 通过医嘱查询出用药方式 */
		DoctorsAdviceDictionaries doctorsAdviceDictionaries = new DoctorsAdviceDictionaries();
		doctorsAdviceDictionaries.setDoctorsAdviceTypeId(2);
		List<DoctorsAdviceDictionaries> doctorsAdviceDictionariesList = doctorsAdviceDictionariesService.selectAll(doctorsAdviceDictionaries);
		if (CollectionUtils.isNotEmpty(doctorsAdviceDictionariesList)){
			test.put("drugUse",doctorsAdviceDictionariesList);
		}
		}else{
			test.put("0","当前医嘱没有相对参数！！！");
		}
		return test;
	}

	@Override
	public List<DispensingDrug> getDispensingDrugByRfidId(String rfidId) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("rfid_id", rfidId);
		columnMap.put("del_flag", 0);
		return dispensingDrugMapper.selectByMap(columnMap);
	}

	@Override
	public int updateDispensingDrugState(Integer id) {
		DispensingDrug dispensingDrug = new DispensingDrug();
		dispensingDrug.setId(id);
		dispensingDrug.setDelFlag(1);
		return dispensingDrugMapper.updateById(dispensingDrug);
	}
}
