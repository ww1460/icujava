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

package com.pig4cloud.pigx.ccxxicu.service.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDeptVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDoctorsAdviceExtVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisNurseVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisPatientVo;

public interface HisDataService {


	/**
	 * 医嘱执行情况修改
	 * @return
	 */
	Boolean updateDoctorsAdviceExt(HisDoctorsAdviceExt hisDoctorsAdviceExt);

	/**
	 * 科室查询
	 * @return
	 */
	Boolean hisDept(HisDeptVo hisDeptVo);

	/**
	 * 护士查询
	 * @return
	 */
	Boolean hisNurse(HisNurseVo hisNurseVo);

	/**
	 * 患者查询
	 * @return
	 */
	Boolean hisPatient(HisPatientVo hisPatientVo);

	/**
	 * his医嘱查询
	 * @return
	 */
	Boolean hisDoctorsAdvice(String patientId);

	/**
	 * 医嘱执行情况查询
	 * @return
	 */
	Boolean hisDoctorsAdviceExt(HisDoctorsAdviceExtVo hisDoctorsAdviceExtVo);
}
