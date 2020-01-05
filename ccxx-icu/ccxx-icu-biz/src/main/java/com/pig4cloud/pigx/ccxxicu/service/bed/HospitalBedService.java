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
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.HospitalBed;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.HospitalBedVo;

import java.util.List;

/**
 * 床位表
 *
 * @author yyj
 * @date 2019-08-07 21:24:05
 */
public interface HospitalBedService extends IService<HospitalBed> {
	/**
	 * 查询某科室下  某状态的床位
	 * @param hospitalBed
	 * @return
	 */
	List<HospitalBed> selectByFlag(HospitalBed hospitalBed);


	/**
	 * 查询某科室下  某状态的床位
	 * @param hospitalBed
	 * @return
	 */
	List<HospitalBedVo> selectByFlags(HospitalBed hospitalBed);

	/**
	 * 分页查询床位
	 * @param page
	 * @param hospitalBed
	 * @return
	 */
	IPage<HospitalBed> selectByPage(Page page, HospitalBed hospitalBed);

}
