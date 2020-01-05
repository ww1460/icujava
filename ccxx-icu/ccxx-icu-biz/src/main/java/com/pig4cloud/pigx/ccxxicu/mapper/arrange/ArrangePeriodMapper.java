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

package com.pig4cloud.pigx.ccxxicu.mapper.arrange;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.arrange.ArrangePeriod;
import com.pig4cloud.pigx.ccxxicu.api.vo.arrange.WeekShiftVo;

import java.util.List;

/**
 * 排班周期结果表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:14
 */
public interface ArrangePeriodMapper extends BaseMapper<ArrangePeriod> {

	/**
	 * 查询某科室下某周的班次
	 * @param arrangePeriod
	 */
	List<WeekShiftVo> selectWeekShift(ArrangePeriod arrangePeriod);



}
