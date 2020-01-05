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

package com.pig4cloud.pigx.ccxxicu.mapper.task;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TimingExecution;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TimingExecutionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务定时执行
 *
 * @author pigx code generator
 * @date 2019-08-15 16:17:00
 */
public interface TimingExecutionMapper extends BaseMapper<TimingExecution> {


	/**
	 * 分页查询展示 数据
	 * @param page
	 * @param timingExecution
	 * @return
	 */
	IPage<List<TimingExecutionVo>> selectPaging (Page page, @Param("timingExecution")TimingExecution timingExecution);

	/**
	 * 定时查询数据
	 * @return
	 */
	List<TimingExecutionVo> timingQuery();


	/**
	 * 通过患者id查询长期任务
	 * @param patientId
	 * @return
	 */
	List<TimingExecution> stopTimingExecution(@Param("patientId")String patientId);


}
