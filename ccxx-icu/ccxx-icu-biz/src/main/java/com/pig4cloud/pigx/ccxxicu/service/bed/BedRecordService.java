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

package com.pig4cloud.pigx.ccxxicu.service.bed;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.BedRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.BedRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.BedRecordVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 床位使用时间记录
 *
 * @author pigx code generator
 * @date 2019-08-07 21:23:37
 */
public interface BedRecordService extends IService<BedRecord> {


	/**
	 * 患者绑定床位的操作
	 * @param bedRecord
	 * @return
	 */
	 boolean add(BedRecord bedRecord);

	/**
	 * 出科时直接使用
	 * 其中需要对结束时间 修改人 进行封装
	 * @param patientId
	 * @return
	 */
	 boolean delPatientBed(String patientId);
	/**
	 * 根据患者和床位id和记录id（生成）组合  查询
	 * @param record
	 * @return
	 */
	 List<BedRecord> selectByCondition(BedRecord record);

	/**
	 * 分页查询床位使用记录
	 * @param page
	 * @param bedRecordBo
	 * @return
	 */
	IPage<BedRecordVo> selectByPage(Page page, BedRecordBo bedRecordBo);

	/**
	 * 修改床位
	 * @param bedRecord
	 * @return
	 */
	R changeBed(BedRecord bedRecord);


}
