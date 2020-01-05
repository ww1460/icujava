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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeResultBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.ArrangeResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 排班结果表  
 *
 * @author pigx code generator
 * @date 2019-08-07 14:54:55
 */
public interface ArrangeResultMapper extends BaseMapper<ArrangeResult> {

	List<ArrangeResult> getByDate(ArrangeResult arrangeResult);

	ArrangeResult selectNewWork(@Param("nurseId") String nurseId);

	List<ArrangeResultVo> getShiftData(ArrangeResultBo arrangeResultBo);




}
