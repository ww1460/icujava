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


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeDemandBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ShiftDemandVo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeParticipant;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.ArrangeParticipantMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeParticipantService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeResultService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参与排班人员表
 *
 * @author pigx code generator
 * @date 2019-08-12 20:48:58
 */
@Service
public class ArrangeParticipantServiceImpl extends ServiceImpl<ArrangeParticipantMapper, ArrangeParticipant> implements ArrangeParticipantService {

	private LocalDateTime arrangeTime;

	@Autowired
	private ArrangeResultService arrangeResultService;


	/**
	 * 查询某天某等级的班次数据
	 *
	 * @param arrangeParticipant
	 * @return
	 */
	@Override
	public List<ArrangeParticipant> getNurseByShift(ArrangeParticipant arrangeParticipant) {
		return baseMapper.getNurseByShift(arrangeParticipant);
	}


	@Override
	public boolean arrange(ArrangeBo arrangeBo) {

		PigxUser user = SecurityUtils.getUser();

		arrangeTime = arrangeBo.getDemands().get(0).getDateTime();
		List<ArrangeDemandBo> demands = arrangeBo.getDemands();

		for (int i = 0; i < 7; i++) {
			//第i+1天的需求
			ArrangeDemandBo arrangeDemandBo = demands.get(i);
			ArrangeParticipant nurse = new ArrangeParticipant();
			nurse.setDeptId(user.getDeptId() + "");
			nurse.setStartTime(arrangeTime);
			List<ArrangeParticipant> nurseByShift = baseMapper.getNurseByShift(nurse);
			Map<Integer, List<ArrangeParticipant>> collect = nurseByShift.stream().collect(Collectors.groupingBy(ArrangeParticipant::getNurseGrade));
			List<ShiftDemandVo> morningShift = arrangeDemandBo.getMorningShift();
			List<ShiftDemandVo> dayShift = arrangeDemandBo.getDayShift();
			List<ShiftDemandVo> nightShift = arrangeDemandBo.getNightShift();
			List<ShiftDemandVo> swingShift = arrangeDemandBo.getSwingShift();
			int a = 0;
			int b = 0;
			int c = 0;
			int d = 0;
			//代表等级
			for (int j = 1; j < morningShift.size() + 1; j++) {
				//该等级下参与排班的人员
				List<ArrangeParticipant> arrangeParticipants = collect.get(j);

				//满足每个等级每个班次的需求
				a += toNurse(arrangeParticipants, morningShift.get(j - 1), 1, arrangeDemandBo.getDateTime(), a);
				b += toNurse(arrangeParticipants, swingShift.get(j - 1), 2, arrangeDemandBo.getDateTime(), b);
				c += toNurse(arrangeParticipants, nightShift.get(j - 1), 3, arrangeDemandBo.getDateTime(), c);
				d += toNurse(arrangeParticipants, dayShift.get(j - 1), 4, arrangeDemandBo.getDateTime(), d);

				if (CollectionUtils.isEmpty(arrangeParticipants)) {
					continue;
				}

				if (arrangeParticipants.size() > 0) {
					ShiftDemandVo shiftDemandVo = new ShiftDemandVo();
					shiftDemandVo.setZ1(arrangeParticipants.size());
					toNurse(arrangeParticipants, shiftDemandVo, 5, arrangeDemandBo.getDateTime(), 0);
				}
			}
		}
		return true;
	}

	/**
	 * 每个等级的需求
	 *
	 * @param nurse
	 * @param demand
	 */
	private int toNurse(List<ArrangeParticipant> nurse, ShiftDemandVo demand, Integer work, LocalDateTime time, Integer yu) {

		if (demand == null || demand.getZ1() == null || demand.getZ1() == 0) {
			return yu;
		}
		if (CollectionUtils.isEmpty(nurse)) {
			return demand.getZ1() + yu;
		}

		int num = demand.getZ1() + yu;
		System.out.println("班次：" + work);
		System.out.println("需求量：" + num);
		System.out.println("人数：" + nurse.size());
		Collections.shuffle(nurse);
		for (int i = 0; i < nurse.size(); i++) {

			ArrangeParticipant arrangeParticipant = nurse.get(i);
			if (arrangeParticipant.getWorkShift() == work && work != 5) {
				continue;
			}

			num--;
			arrangeParticipant.setWorkShift(work);
			arrangeParticipant.setCount(arrangeParticipant.getCount() + 1);
			baseMapper.updateById(arrangeParticipant);
			nurse.remove(i);
			i--;
			ArrangeResult result = new ArrangeResult();

			BeanUtil.copyProperties(arrangeParticipant, result);

			result.setId(null);
			result.setCreateTime(LocalDateTime.now());
			result.setArrangeResultId(SnowFlake.getId() + "");
			result.setDateTime(time);
			result.setWorkShift(work);
			arrangeResultService.save(result);
			if (0 == num) {
				break;
			}
		}

		if (num > 0) {

			return num;

		}

		return 0;

	}

}
