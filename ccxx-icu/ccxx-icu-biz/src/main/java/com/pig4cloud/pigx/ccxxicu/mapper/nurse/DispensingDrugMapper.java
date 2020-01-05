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
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配药
 *
 * @author pigx code generator
 * @date 2019-09-05 15:53:09
 */
public interface DispensingDrugMapper extends BaseMapper<DispensingDrug> {

	/**
	 * 根据任务ID获取医嘱ID所在的列表信息，一个医嘱对应一个医嘱ID，暂存在List中
	 * @param taskId
	 * @return
	 */
	List<DispensingDrug> getDispensingDrugByTaskId(@Param("taskId") String taskId);

}
