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
package com.pig4cloud.pigx.ccxxicu.service.impl.task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TimingExecution;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TimingExecutionVo;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TimingExecutionMapper;
import com.pig4cloud.pigx.ccxxicu.service.task.TimingExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务定时执行
 *
 * @author pigx code generator
 * @date 2019-08-15 16:17:00
 */
@Service
public class TimingExecutionServiceImpl extends ServiceImpl<TimingExecutionMapper, TimingExecution> implements TimingExecutionService {


	@Autowired
	private TimingExecutionMapper timingExecutionMapper;

	/**
	 * 分页预览
	 * @param page
	 * @param timingExecution
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, TimingExecution timingExecution) {
		return timingExecutionMapper.selectPaging(page,timingExecution);
	}

	/**
	 * 定时查询
	 * @return
	 */
	@Override
	public List<TimingExecutionVo> timingQuery() {
		return timingExecutionMapper.timingQuery();
	}


	/**
	 * 通过患者id查询长期任务
	 * @param patientId
	 * @return
	 */
	@Override
	public Boolean stopTimingExecution(String patientId) {
		List<TimingExecution> timingExecutions = timingExecutionMapper.stopTimingExecution(patientId);
		if (CollectionUtils.isNotEmpty(timingExecutions)){
			timingExecutions.forEach(e->{
				e.setDelFlag(1);
			});
			return this.updateBatchById(timingExecutions);
		}else{
			return true;
		}
	}

	/**
	 * 将任务模板中的 开始时间 和结束时间 修改成当前的时间
	 * @param time
	 * @return
	 */
	@Override
	public LocalDateTime dateTime(LocalDateTime time) {
		int hour = time.getHour();
		int m =time.getMonthValue();
		int d = time.getDayOfMonth();
		return time.withHour(hour).withMonth(m).withDayOfMonth(d);
	}
}
