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

package com.pig4cloud.pigx.ccxxicu.mapper.nurse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 护士看护患者记录表
 *
 * @author pigx code generator
 * @date 2019-08-05 15:12:54
 */
public interface NursePatientRecordMapper extends BaseMapper<NursePatientRecord> {



	/**
	 * 条件分页查询数据
	 */
	IPage<List<NursePatientRecordVo>> selectPaging(Page page,@Param("nursePatientRecord") NursePatientRecord nursePatientRecord);

	/**
	 * 条件查询
	 * @param nursePatientRecord
	 * @return
	 */
	List<NursePatientRecord> selectAll(@Param("nursePatientRecord") NursePatientRecord nursePatientRecord);

	/**
	 * 用患者id 查询当前看护患者的护士   用于出科
	 * @param patientId
	 * @return
	 */
	List<NursePatientRecord> currentCarePatientNurse(@Param("patientId")String patientId);

}
