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

package com.pig4cloud.pigx.ccxxicu.mapper.bed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.BedRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.BedRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 床位表
 *
 * @author yyj
 * @date 2019-08-07 21:24:05
 */
public interface HospitalBedMapper extends BaseMapper<HospitalBed> {

	List<HospitalBed> selectByFlag(HospitalBed hospitalBed);

	/**
	 * 统计所有病床数
	 * @return
	 */
	Integer selectAllBedNumber();

	IPage<HospitalBed> selectByPage(Page page, @Param("query") HospitalBed hospitalBed);

}
