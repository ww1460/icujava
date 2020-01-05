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

package com.pig4cloud.pigx.ccxxicu.service.nurse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 护士看护患者记录表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:12:54
 */
public interface NursePatientRecordService extends IService<NursePatientRecord> {


	/**
	 * 条件分页查询数据源
	 * @param page
	 * @param nursePatientRecord
	 * @return
	 */
	IPage selectPaging (Page  page,NursePatientRecord nursePatientRecord);

	/**
	 * 条件查询
	 * @param nursePatientRecord
	 * @return
	 */
	List<NursePatientRecord> selectAll(NursePatientRecord nursePatientRecord);

	/**
	 * 护士患者 断开关联
	 * @param nursePatientRecord
	 * @return
	 */
	Boolean disconnection(NursePatientRecord nursePatientRecord);

	/**
	 * 护士与患者产生关联
	 * @param nursePatientRecord
	 * @return
	 */
	R nurseRelationPatient(NursePatientRecord nursePatientRecord);

	/**
	 * 患者修改看护护士
	 * @param nursePatientRecord
	 * @return
	 */
	Boolean nursePatientupdate(NursePatientRecord nursePatientRecord);

	/**
	 * 利用患者id，查询看护当前患者的护士数据，断开所有的护士关联 用于出科
	 * @param patientId
	 * @return
	 */
	Boolean stopNursePatient(String patientId);


}
