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


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeResultBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.common.core.util.R;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 排班结果表  
 *
 * @author pigx code generator
 * @date 2019-08-07 14:54:55
 */
public interface ArrangeResultService extends IService<ArrangeResult> {

	/**
	 * 根据护士 日期  科室等条件查询
	 * @param arrangeResult
	 * @return
	 */
	List<ArrangeResult> getByDate(ArrangeResult arrangeResult);

	/**
	 *获取某护士最新一次的排班结果
	 * @param nurseId
	 * @return
	 */
	ArrangeResult selectNewWork(@Param("nurseId") String nurseId);

	/**
	 * 用于展示  可根据 科室、护士、等级、时间和时间周期
	 * @param arrangeResultBo
	 * @return
	 */
	R getShiftData(ArrangeResultBo arrangeResultBo);


}
