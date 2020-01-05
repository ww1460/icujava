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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeResultBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.ArrangeResultVo;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.ArrangeResultMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeResultService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 排班结果表
 *
 * @author pigx code generator
 * @date 2019-08-07 14:54:55
 */
@Service
public class ArrangeResultServiceImpl extends ServiceImpl<ArrangeResultMapper, ArrangeResult> implements ArrangeResultService {

	@Autowired
	private NurseService nurseService;


	/**
	 * 根据护士 日期  科室等条件查询
	 *
	 * @param arrangeResult
	 * @return
	 */
	@Override
	public List<ArrangeResult> getByDate(ArrangeResult arrangeResult) {
		return baseMapper.getByDate(arrangeResult);
	}

	/**
	 * 获取某护士最新一次的排班结果
	 *
	 * @param nurseId
	 * @return
	 */
	@Override
	public ArrangeResult selectNewWork(String nurseId) {
		return baseMapper.selectNewWork(nurseId);
	}

	/**
	 * 用于展示  可根据 科室、护士、等级、时间和时间周期
	 *
	 * @param arrangeResultBo
	 * @return
	 */
	@Override
	public R getShiftData(ArrangeResultBo arrangeResultBo) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		ArrangeResult arrangeResult = new ArrangeResult();

		arrangeResult.setDeptId(user.getDeptId()+"");
		arrangeResult.setDelFlag(0);
		arrangeResult.setDateTime(arrangeResultBo.getDateTime());
		arrangeResult.setStartTime(arrangeResultBo.getStartTime());

		List<ArrangeResult> byDate = this.getByDate(arrangeResult);

		if (CollectionUtils.isEmpty(byDate)) {

			return R.failed(10010, "结果为空！");

		}
		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员  查询对应科室的数据

			List<Nurse> nurses = nurseService.selectAllNurse(arrangeResultBo.getDeptId());
			List<List<?>> result = new ArrayList<>();
			for (int i = 0; i < nurses.size(); i++) {

				arrangeResultBo.setNurseId(nurses.get(i).getNurseId());
				result.add(dateScope(arrangeResultBo));

			}
			if (CollectionUtils.isEmpty(result)) {

				return R.ok(null);

			}

			return R.ok(result);

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长

			Integer deptId = user.getDeptId();
			arrangeResultBo.setDeptId(deptId + "");
			List<Nurse> nurses = nurseService.selectAllNurse(deptId + "");
			List<List<?>> result = new ArrayList<>(nurses.size());
			for (int i = 0; i < nurses.size(); i++) {
				arrangeResultBo.setNurseId(nurses.get(i).getNurseId());
				result.add(dateScope(arrangeResultBo));
			}
			if (CollectionUtils.isEmpty(result)) {

				return R.ok(null);
			}

			if (!CollectionUtils.isEmpty(result)) {

				while (CollectionUtils.isEmpty(result.get(0))) {

					Collections.shuffle(result);
				}
			}

			return R.ok(result);
		} else if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			Integer deptId = user.getDeptId();
			arrangeResultBo.setDeptId(deptId + "");
			arrangeResultBo.setNurseId(user.getId() + "");


			if (CollectionUtils.isEmpty(dateScope(arrangeResultBo))) {

				return R.ok(null);

			}
			return R.ok(dateScope(arrangeResultBo));

		}

		return R.failed(1, "操作有误！");
	}

	/**
	 * 根据时间区间作数据查询
	 * @param arrangeResultBo
	 * @return
	 */
	private List<?> dateScope(ArrangeResultBo arrangeResultBo) {

		//查询该周期的数据
		if (arrangeResultBo.getScope() == 2) {

			if (arrangeResultBo.getStartTime() != null) {

				return baseMapper.getShiftData(arrangeResultBo);

			}
			//查询出该日期所在的周期的 开始时间
			ArrangeResult arrangeResult = new ArrangeResult();

			arrangeResult.setDateTime(arrangeResultBo.getDateTime());

			arrangeResult.setDeptId(arrangeResultBo.getDeptId());

			List<ArrangeResult> byDate = baseMapper.getByDate(arrangeResult);
			//如果有数据存在  则按开始时间查询
			if (byDate.size() > 0) {

				LocalDateTime startTime = byDate.get(0).getStartTime();
				arrangeResultBo.setStartTime(startTime);
				arrangeResultBo.setDateTime(null);

			} else {
				//没有数据存在  则将选择的时间 作为开始时间 进行查询
				arrangeResultBo.setStartTime(arrangeResultBo.getDateTime());
				arrangeResultBo.setDateTime(null);

			}

			return baseMapper.getShiftData(arrangeResultBo);

		} else if (arrangeResultBo.getScope() == 3) {

			LocalDateTime dateTime = arrangeResultBo.getDateTime();
			//获取当月的天数
			int days = dateTime.getMonth().maxLength();
			//从第一天的开始查询

			List<ArrangeResultVo> res = new ArrayList<>(days);
			List<ArrangeResultVo> shiftData = baseMapper.getShiftData(arrangeResultBo);
			for (int i = 0; i < days; i++) {
				res.add(null);
			}
			if (shiftData.isEmpty()) {
				return res;
			}
			shiftData.forEach(ar->{

				int dayOfMonth = ar.getDateTime().getDayOfMonth();
				res.set(dayOfMonth-1,ar);

			});

			return res;

		}
		return baseMapper.getShiftData(arrangeResultBo);
	}
}
