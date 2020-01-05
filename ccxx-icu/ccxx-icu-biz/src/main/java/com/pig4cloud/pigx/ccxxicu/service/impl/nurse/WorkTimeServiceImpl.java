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
package com.pig4cloud.pigx.ccxxicu.service.impl.nurse;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.WorkTimeBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.WorkTime;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.WorkTimeVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.WorkTimeMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.WorkTimeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 护士在线时间
 *
 * @author pigx code generator
 * @date 2019-11-06 17:02:01
 */
@Service
public class WorkTimeServiceImpl extends ServiceImpl<WorkTimeMapper, WorkTime> implements WorkTimeService {


	@Override
	public List<WorkTimeVo> groupByBed(WorkTimeBo workTimeBo) {

		if (workTimeBo.getFirstTime() == null) {
			workTimeBo.setFirstTime(LocalDateTime.now());
		}

		if (workTimeBo.getTimeFlag() == null || workTimeBo.getTimeFlag() == 0) {
			workTimeBo.setTimeFlag(1);
		}
		List<WorkTimeVo> workTimeVos = baseMapper.groupByBed(workTimeBo);
		if (CollectionUtils.isEmpty(workTimeVos)) {
			return workTimeVos;
		}

		Integer num = 0;
		for (int i = 0; i < workTimeVos.size(); i++) {

			num += workTimeVos.get(i).getCount();

		}

		final Double nums = Double.valueOf(num);

		workTimeVos.forEach(ar->{

			ar.setProportion(ar.getCount()/nums);

		});


		return workTimeVos;
	}

	/**
	 * 添加护士的工作时间  只有开始时间
	 * @param workTime
	 * @return
	 */
	@Override
	public boolean add(WorkTime workTime) {

		workTime.setWorkTimeId(SnowFlake.getId() + "");
		workTime.setCreateTime(LocalDateTime.now());

		return this.save(workTime);
	}

	/**
	 * 补全护士工作时间  添加结束时间 计算总时间 总时间的单位是分钟
	 * @param workTime
	 * @return
	 */
	@Override
	public boolean addEndTime(WorkTime workTime) {

		WorkTime newRecord = this.getNewRecord(workTime);
		if (newRecord == null) {
			return false;
		}
		newRecord.setEndTime(workTime.getEndTime());
		long between = ChronoUnit.MINUTES.between(workTime.getStartTime(), workTime.getEndTime());
		between = between == 0 ? 1 : between;
		newRecord.setTimeCount(Integer.parseInt(between+""));

		return this.updateById(newRecord);
	}

	/**
	 * 获取该护士在该床位下 没有结束时间的最新的一条记录
	 * @param workTime
	 * @return
	 */
	@Override
	public WorkTime getNewRecord(WorkTime workTime) {

		if (workTime.getBedId() == null) {

			return null;
		}

		if (workTime.getUserId() == null) {
			return null;
		}

		return baseMapper.getNewRecord(workTime);
	}
}
