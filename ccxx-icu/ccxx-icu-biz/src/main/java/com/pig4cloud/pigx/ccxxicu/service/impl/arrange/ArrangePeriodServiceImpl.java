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
package com.pig4cloud.pigx.ccxxicu.service.impl.arrange;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.arrange.ArrangePeriod;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.vo.arrange.WeekShiftVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.ShiftEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.arrange.ArrangePeriodMapper;
import com.pig4cloud.pigx.ccxxicu.service.arrange.ArrangePeriodService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeResultService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 排班周期结果表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:14
 */
@Service
public class ArrangePeriodServiceImpl extends ServiceImpl<ArrangePeriodMapper, ArrangePeriod> implements ArrangePeriodService {

	@Autowired
	private ArrangeResultService arrangeResultService;



	/**
	 * 查询某科室下某周的班次
	 * @param arrangePeriod
	 */
	@Override
	public List<WeekShiftVo> selectWeekShift(ArrangePeriod arrangePeriod) {
		return baseMapper.selectWeekShift(arrangePeriod);
	}

	/**
	 * 复制排班结果
	 * @param record
	 * @return
	 */
	@Override
	public boolean copyShift(List<WeekShiftVo> record) {

		PigxUser user = SecurityUtils.getUser();

		List<ArrangePeriod> result = new ArrayList<>();

		record.forEach(ar->{

			ArrangePeriod arrangePeriod = new ArrangePeriod();
			BeanUtil.copyProperties(ar,arrangePeriod);
			arrangePeriod.setId(null);
			arrangePeriod.setArrangePeriodId(SnowFlake.getId()+"");
			arrangePeriod.setCreateTime(LocalDateTime.now());
			arrangePeriod.setCreateUserId(user.getId()+"");
			ArrangePeriod res = countRestTime(arrangePeriod);
			result.add(res);
		});
		saveResult(result);
		return this.saveBatch(result);
	}

	/**
	 * 整体修改
	 * @param record
	 * @return
	 */
	@Override
	public boolean updateList(List<ArrangePeriod> record) {

		List<WeekShiftVo> weekShiftVos = this.selectWeekShift(record.get(0));
		Map<String, List<WeekShiftVo>> collect = weekShiftVos.stream().collect(Collectors.groupingBy(ArrangePeriod::getNurseId));
		record.forEach(ar->{

			String nurseId = ar.getNurseId();

			List<WeekShiftVo> weekShiftVos1 = collect.get(nurseId);

			if (CollectionUtils.isEmpty(weekShiftVos1)) {



			}


		});
		return false;
	}

	/**
	 * 计算休息时间
	 *  仅在第一次生成时
	 * @param record
	 * @return
	 */
	private ArrangePeriod countRestTime(ArrangePeriod record) {
		int i = 2;

		if (record.getSaturday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getSaturday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;

		} else if (record.getFriday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getFriday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;
		}else if (record.getWednesday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getWednesday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;
		}else if (record.getTuesday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getTuesday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;
		}else if (record.getSunday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getSunday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;
		}else if (record.getMonday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getMonday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;
		}else if (record.getThursday().equals(ShiftEnum.REST_SHIFT.getCode())||
				(record.getThursday().equals(ShiftEnum.MISSED_REST_SHIFT.getCode()))) {
			i--;
		}

		Double thisTimeNum = record.getThisTimeNum();
		double v = thisTimeNum + i;
		record.setLastTimeNum(thisTimeNum);
		record.setThisTimeNum(v);
		return record;

	}

	/**
	 * 将排班结果复制到结果里
	 * @param result
	 */
	private void saveResult(List<ArrangePeriod> result) {

		if (CollectionUtils.isEmpty(result)) {
			return;
		}

		result.forEach(ar->{

			Thread thread = new Thread(){

				@Override
				public void run() {
					List<Integer> shift = new ArrayList<>();
					Integer monday = ar.getMonday();//1
					shift.add(monday);
					Integer tuesday = ar.getTuesday();//2
					shift.add(tuesday);
					Integer wednesday = ar.getWednesday();//3
					shift.add(wednesday);
					Integer thursday = ar.getThursday();//4
					shift.add(thursday);
					Integer friday = ar.getFriday();//5
					shift.add(friday);
					Integer saturday = ar.getSaturday();//6
					shift.add(saturday);
					Integer sunday = ar.getSunday();//7
					shift.add(sunday);

					for (int i = 0; i < shift.size(); i++) {

						ArrangeResult res = new ArrangeResult();

						res.setWorkShift(shift.get(i));
						res.setStartTime(ar.getStartTime());
						res.setDateTime(ar.getStartTime().minusDays(-i));
						res.setNurseId(ar.getNurseId());
						res.setDeptId(ar.getDeptId());
						res.setCreateTime(ar.getCreateTime());
						res.setCreateUserId(ar.getCreateUserId());
						res.setArrangeResultId(SnowFlake.getId()+"");
						res.setDelFlag(0);
						arrangeResultService.save(res);
					}


					super.run();
				}
			};


			thread.start();

		});
	}

}
