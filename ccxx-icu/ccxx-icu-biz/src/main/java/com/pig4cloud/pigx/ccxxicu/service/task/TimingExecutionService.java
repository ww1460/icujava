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

package com.pig4cloud.pigx.ccxxicu.service.task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TimingExecution;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TimingExecutionVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务定时执行
 *
 * @author pigx code generator
 * @date 2019-08-15 16:17:00
 */
public interface TimingExecutionService extends IService<TimingExecution> {

	/**
	 * 分页预览对象
	 * @param page
	 * @param timingExecution
	 * @return
	 */
	IPage selectPaging(Page page,TimingExecution timingExecution);


	/**
	 * 定时查询数据
	 * @return
	 */
	List<TimingExecutionVo> timingQuery();


	/**
	 * 通过患者id暂停长期任务
	 * @param patientId
	 * @return
	 */
	Boolean stopTimingExecution(String patientId);

	/**
	 * 将任务模板中的 开始时间 和结束时间 修改成当前的时间
	 * @param localDateTime
	 * @return
	 */
	LocalDateTime dateTime(LocalDateTime localDateTime);


}
