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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.PatientCommunicate;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.PatientCommunicateMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.PatientCommunicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 护士与患者沟通
 *
 * @author pigx code generator
 * @date 2019-09-03 20:31:37
 */
@Service
public class PatientCommunicateServiceImpl extends ServiceImpl<PatientCommunicateMapper, PatientCommunicate> implements PatientCommunicateService {

	@Autowired
	private PatientCommunicateMapper patientCommunicateMapper;

	/**
	 * 全查
	 * @param patientCommunicate
	 * @return
	 */
	@Override
	public List<PatientCommunicate> selectAll(PatientCommunicate patientCommunicate) {
		return patientCommunicateMapper.selectAll(patientCommunicate);
	}
}
