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

package com.pig4cloud.pigx.ccxxicu.controller.nurse;


import cn.hutool.core.bean.BeanUtil;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeDemandBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangePrepareBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ShiftDemandVo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeParticipant;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeParticipantService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeResultService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 参与排班人员表
 *
 * @author pigx code generator
 * @date 2019-08-12 20:48:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/arrangeParticipant")
@Api(value = "arrangeParticipant", tags = "参与排班人员表管理")
public class ArrangeParticipantController {

	private final ArrangeParticipantService arrangeParticipantService;

	private final ArrangeResultService arrangeResultService;

	private final NurseService nurseService;


	@ApiOperation(value = "校验时间，确定人员", notes = "校验时间，确定人员  ")
	@SysLog("校验时间，确定人员")
	@PostMapping("/choiceNurse")
	public R choiceNurse(@RequestBody ArrangePrepareBo arrangePrepareBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (arrangePrepareBo!= null && CollectionUtils.isEmpty(arrangePrepareBo.getNurseList())) {

			return R.failed("参与排班的护士不可为空！");

		}


		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员


		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			Integer deptId = user.getDeptId();

			LocalDateTime startTime = arrangePrepareBo.getStartTime();
			//判断该时间点是否存在排班
			ArrangeResult arrangeResult = new ArrangeResult();

			arrangeResult.setDeptId(deptId + "");
			arrangeResult.setDateTime(startTime);
			ArrangeParticipant arrangeParticipant = new ArrangeParticipant();
			arrangeParticipant.setDeptId(deptId + "");
			arrangeParticipant.setStartTime(startTime);

			List<ArrangeResult> byDate = arrangeResultService.getByDate(arrangeResult);
			List<ArrangeParticipant> nurseByShift = arrangeParticipantService.getNurseByShift(arrangeParticipant);
			if (!byDate.isEmpty() || byDate.size() != 0) {

				return R.failed("已存在排班结果！");

			}

			if (nurseByShift.size() > 0) {

				return R.ok(true);

			}

			//将参与本次排班的护士 进行添加
			for (int i = 0; i < arrangePrepareBo.getNurseList().size(); i++) {

				ArrangeParticipant part = new ArrangeParticipant();

				Nurse nurse = arrangePrepareBo.getNurseList().get(i);

				long id1 = SnowFlake.getId();
				part.setArrangeParticipantId(id1 + "");
				part.setDelFlag(0);
				part.setDeptId(deptId + "");
				part.setNurseGrade(nurse.getNurseGrade());
				part.setNurseId(nurse.getNurseId());
				part.setStartTime(startTime);
				part.setCreateTime(LocalDateTime.now());
				part.setCreateUserId(user.getId() + "");
				/**
				 * 这里应该查询该护士上次的班次 进行封装
				 */
				ArrangeResult arrangeResult1 = arrangeResultService.selectNewWork(nurse.getNurseId());
				if (arrangeResult1 == null) {

					part.setWorkShift(5);

				} else {
					part.setWorkShift(arrangeResult1.getWorkShift());
				}

				boolean save = arrangeParticipantService.save(part);

				if (!save) {

					return R.failed(1, "操作有误！");

				}

			}

		}
		return R.ok(true);
	}


	@ApiOperation(value = "开始排班", notes = "开始排班")
	@SysLog("开始排班")
	@PostMapping("/arrange")
	public R startArrange(@RequestBody ArrangeBo arrangeBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.failed(1, "操作有误！");
		} else if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			return R.failed(1, "权限不足！");

		}



		Thread thread;
		thread = new Thread(){
			@Override
			public void run() {
				LocalDateTime startTime = null;

				List<ArrangeDemandBo> demands = arrangeBo.getDemands();
				//获取需求
				for (int i = 0; i < demands.size(); i++) {

					ArrangeDemandBo arrangeDemandBo = demands.get(i);

					if (i == 0) {

						startTime = arrangeDemandBo.getDateTime();
					}


					//排班日期
					LocalDateTime dateTime = arrangeDemandBo.getDateTime();
					//早班需求
					List<ShiftDemandVo> morningShift = arrangeDemandBo.getMorningShift();
					//小夜班需求
					List<ShiftDemandVo> swingShift = arrangeDemandBo.getSwingShift();
					//大夜班需求
					List<ShiftDemandVo> nightShift = arrangeDemandBo.getNightShift();
					//白班需求
					List<ShiftDemandVo> dayShift = arrangeDemandBo.getDayShift();
					//查询上次班次中护士的上班情况
					ArrangeParticipant msg = new ArrangeParticipant();

					msg.setStartTime(startTime);
					msg.setDeptId(user.getDeptId() + "");

					for (int j = 0; j < morningShift.size(); j++) {

						msg.setNurseGrade(j + 1);

						//查询该等级下的上次每个班次情况
						//早班
						msg.setWorkShift(1);
						List<ArrangeParticipant> morningList = arrangeParticipantService.getNurseByShift(msg);
						//小夜
						msg.setWorkShift(2);
						List<ArrangeParticipant> swingList = arrangeParticipantService.getNurseByShift(msg);
						//大夜
						msg.setWorkShift(3);
						List<ArrangeParticipant> nightList = arrangeParticipantService.getNurseByShift(msg);
						//白班
						msg.setWorkShift(4);
						List<ArrangeParticipant> dayList = arrangeParticipantService.getNurseByShift(msg);
						//休息
						msg.setWorkShift(5);
						List<ArrangeParticipant> restList = arrangeParticipantService.getNurseByShift(msg);
						//早班 j+1等级护士的需求量
						Integer morning =morningShift==null?0:(morningShift.get(j)==null?0:morningShift.get(j).getZ1()==null?0:morningShift.get(j).getZ1());
						//小夜班 j+1等级护士的需求量
						Integer swing =swingShift==null?0:( swingShift.get(j)==null?0:swingShift.get(j).getZ1()==null?0:swingShift.get(j).getZ1());
						//大夜班 j+1等级护士的需求量
						Integer night = nightShift==null?0:(nightShift.get(j)==null?0:nightShift.get(j).getZ1()==null?0:nightShift.get(j).getZ1());
						//白班 j+1等级护士的需求量
						Integer day = dayShift==null?0:(dayShift.get(j)==null?0:dayShift.get(j).getZ1()==null?0:dayShift.get(j).getZ1());

						/**
						 * 无论哪个班次优先安排前一天休息的人员
						 * 早班：休息-白班-小夜-早班-大夜
						 * 小夜：休息-早班-白班-大夜-小夜
						 * 大夜：休息- 早班-白班-小夜-大夜
						 * 白班：休息-早班-小夜-白班-大夜
						 *
						 */
						/**
						 * 这里做了个随机安排  没有固定的安排顺序
						 */
						List<Integer> nums = new ArrayList<>();

						for (int k = 0; k < 4; k++) {
							nums.add(k);
						}
						//将nums的数据打乱
						Collections.shuffle(nums);

						for (int k = 0; k < nums.size(); k++) {

							switch (nums.get(k)) {

								case 0:

									if (morning > restList.size()) {
										//将休息的护士全部安排到早班
										arrange(restList, restList.size(), 1, dateTime);
										morning = morning - restList.size();
										if (morning > dayList.size()) {
											//将白班的护士全部安排到早班
											arrange(dayList, dayList.size(), 1, dateTime);
											morning = morning - dayList.size();
											if (morning > swingList.size()) {
												//将小夜班的护士全部安排到早班
												arrange(swingList, swingList.size(), 1, dateTime);
												morning = morning - swingList.size();
												if (morning > morningList.size()) {
													//将早班的护士全部安排到早班
													arrange(morningList, morningList.size(), 1, dateTime);
													morning = morning - restList.size();
													if (morning > nightList.size()) {
														//将大夜班的护士全部安排到早班
														arrange(nightList, nightList.size(), 1, dateTime);
													} else {
														//将morning数量的大夜班护士安排到早班
														arrange(nightList, morning, 1, dateTime);

													}
												} else {
													//将morning数量的早班护士安排到早班
													arrange(morningList, morning, 1, dateTime);

												}
											} else {
												//将morning数量的小夜护士安排到早班
												arrange(swingList, morning, 1, dateTime);
											}
										} else {
											//将morning数量的白班护士安排到早班
											arrange(dayList, morning, 1, dateTime);
										}
									} else {
										//将morning数量的休息护士安排到早班
										arrange(restList, morning, 1, dateTime);
									}
									break;
								case 1:
									if (swing > restList.size()) {
										//将休班人员全部安排到小夜班
										arrange(restList, restList.size(), 2, dateTime);
										swing = swing - restList.size();

										if (swing > morningList.size()) {
											//将早班人员全部安排到小夜班
											arrange(morningList, morningList.size(), 2, dateTime);
											swing = swing - morningList.size();
											if (swing > dayList.size()) {
												//将白班人员全部安排到小夜班
												arrange(dayList, dayList.size(), 2, dateTime);
												swing = swing - dayList.size();
												if (swing > nightList.size()) {
													//将大夜班人员全部安排到小夜班
													arrange(nightList, nightList.size(), 2, dateTime);
													swing = swing - nightList.size();
													if (swing > swingList.size()) {
														//将小夜班人员全部安排到小夜班
														arrange(swingList, swingList.size(), 2, dateTime);

													} else {
														//将swing数量的小夜班护士安排到小夜班
														arrange(swingList, swing, 2, dateTime);
													}
												} else {
													//将swing数量的大夜班护士安排到小夜班
													arrange(nightList, swing, 2, dateTime);
												}
											} else {
												//将swing数量的白班护士安排到小夜班
												arrange(dayList, swing, 2, dateTime);
											}
										} else {
											//将swing数量的早班护士安排到小夜班
											arrange(morningList, swing, 2, dateTime);
										}


									} else {
										//将swing数量的休息热护士安排到小夜班
										arrange(restList, swing, 2, dateTime);
									}
									break;
								case 2:
									if (night > restList.size()) {
										//将全部休息人员安排到大夜班
										arrange(restList, restList.size(), 3, dateTime);
										night = night - restList.size();
										if (night > morningList.size()) {
											//将全部早班人员安排到大夜班
											arrange(morningList, morningList.size(), 3, dateTime);
											night = night - morningList.size();
											if (night > dayList.size()) {
												//将全部白班人员安排到大夜班
												arrange(dayList, dayList.size(), 3, dateTime);
												night = night - dayList.size();
												if (night > swingList.size()) {
													//将全部小夜班人员安排到大夜班
													arrange(swingList, swingList.size(), 3, dateTime);
													night = night - swingList.size();
													if (night > nightList.size()) {
														//将全部大夜班人员安排到大夜班
														arrange(nightList, nightList.size(), 3, dateTime);

													} else {
														//将night数量的大夜班护士 安排到大夜班
														arrange(nightList, night, 3, dateTime);
													}
												} else {
													//将night数量的小夜班护士 安排到大夜班
													arrange(swingList, night, 3, dateTime);
												}
											} else {
												//将night数量的白班护士 安排到大夜班
												arrange(dayList, night, 3, dateTime);
											}
										} else {
											//将night数量的早班护士 安排到大夜班
											arrange(morningList, night, 3, dateTime);
										}
									} else {
										//将night数量的休息护士 安排到大夜班
										arrange(restList, night, 3, dateTime);
									}

									break;
								case 3:
									if (day > restList.size()) {
										//将全部休班人员安排到白班
										arrange(restList, restList.size(), 4, dateTime);
										day = day - restList.size();

										if (day > morningList.size()) {
											//将全部早班人员安排到白班
											arrange(morningList, morningList.size(), 4, dateTime);
											day = day - morningList.size();

											if (day > swingList.size()) {
												//将全部小夜班人员安排到白班
												arrange(swingList, swingList.size(), 4, dateTime);
												day = day - swingList.size();

												if (day > dayList.size()) {
													//将全部白班人员安排到白班
													arrange(dayList, dayList.size(), 4, dateTime);
													day = day - dayList.size();

													if (day > nightList.size()) {
														//将全部大夜班人员安排到白班
														arrange(nightList, dayList.size(), 4, dateTime);

													} else {
														//将day数量的大夜班护士 安排到白班
														arrange(nightList, day, 4, dateTime);
													}
												} else {
													//将day数量的白班护士 安排到白班
													arrange(dayList, day, 4, dateTime);
												}
											} else {
												//将day数量的小夜班护士 安排到白班
												arrange(swingList, day, 4, dateTime);
											}

										} else {
											//将day数量的早班护士 安排到白班
											arrange(morningList, day, 4, dateTime);
										}

									} else {
										//将day数量的休班护士 安排到白班
										arrange(restList, day, 4, dateTime);
									}


									break;
							}
						}




						/**
						 * 将剩余未安排的护士  进行休班安排
						 */
						if (morningList.size() > 0) {

							arrange(morningList, morningList.size(), 5, dateTime);

						}
						if (swingList.size() > 0) {
							arrange(swingList, swingList.size(), 5, dateTime);

						}
						if (nightList.size() > 0) {
							arrange(nightList, nightList.size(), 5, dateTime);

						}
						if (dayList.size() > 0) {
							arrange(dayList, dayList.size(), 5, dateTime);

						}
						if (restList.size() > 0) {
							arrange(restList, restList.size(), 5, dateTime);

						}


					}
				}


				//将未进行安排的护士  作休班处理
				List<Nurse> nurses = nurseService.selectByDept(user.getDeptId() + "");

				ArrangeParticipant arrangeParticipant = new ArrangeParticipant();

				arrangeParticipant.setStartTime(startTime);
				arrangeParticipant.setDeptId(user.getDeptId() + "");
				List<ArrangeParticipant> nurseByShift = arrangeParticipantService.getNurseByShift(arrangeParticipant);
				ArrangeParticipant arrangeParticipant1 = null;
				for (int i = 0; i < nurses.size(); i++) {
					Nurse nurse = nurses.get(i);
					for (int j = 0; j < nurseByShift.size(); j++) {
						arrangeParticipant1 = nurseByShift.get(j);
						if (nurse.getNurseId().equals(arrangeParticipant1.getNurseId())) {

							nurseByShift.remove(j);
							nurses.remove(i);
							i--;
							break;
						}
					}
				}

				if (nurses.size() > 0) {

					ArrangeResult arrangeResult = new ArrangeResult();
					arrangeResult.setDeptId(user.getDeptId() + "");
					arrangeResult.setStartTime(startTime);
					arrangeResult.setNurseId(arrangeParticipant1.getNurseId());
					List<ArrangeResult> byDate = arrangeResultService.getByDate(arrangeResult);


					for (int i = 0; i < nurses.size(); i++) {

						for (int j = 0; j < byDate.size(); j++) {

							ArrangeResult arrangeResult1 = byDate.get(j);

							ArrangeResult result = new ArrangeResult();

							BeanUtil.copyProperties(arrangeResult1, result);
							result.setCreateTime(LocalDateTime.now());
							result.setDelFlag(0);
							result.setId(null);
							long id = SnowFlake.getId();
							result.setArrangeResultId(id + "");
							result.setNurseId(nurses.get(i).getNurseId());
							result.setWorkShift(5);
							arrangeResultService.save(result);
						}

					}


				}
				super.run();
			}
		};

		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return R.ok(0, "后台正在排班，请稍后查看！");
	}

	/**
	 * 对班次进行安排
	 *
	 * @param old  将要被安排的护士
	 * @param num  需要被安排的数量
	 * @param work 被安排的班次
	 * @return
	 */
	private void arrange(List<ArrangeParticipant> old, Integer num, Integer work, LocalDateTime dateTime) {

		//将数据打乱，避免同一需求下 结果重复
		Collections.shuffle(old);

		//将人员进行安排
		for (int i = 0; i < num; i++) {
			//对中间数据进行修改
			ArrangeParticipant arrangeParticipant = old.get(i);

			arrangeParticipant.setWorkShift(work);

			arrangeParticipantService.updateById(arrangeParticipant);

			//新增结果数据
			ArrangeResult result = new ArrangeResult();

			result.setDeptId(arrangeParticipant.getDeptId());

			result.setDelFlag(0);

			result.setWorkShift(work);

			result.setNurseId(arrangeParticipant.getNurseId());

			result.setDateTime(dateTime);

			result.setCreateUserId(arrangeParticipant.getCreateUserId());

			result.setCreateTime(LocalDateTime.now());

			result.setStartTime(arrangeParticipant.getStartTime());

			long id = SnowFlake.getId();

			result.setArrangeResultId(id + "");
			//保存结果
			arrangeResultService.save(result);
		}
		//将被安排完的人员进行移除
		for (int i = 0; i < num; i++) {
			old.remove(0);
		}

	}





	@ApiOperation(value = "开始排班", notes = "开始排班")
	@SysLog("开始排班")
	@PostMapping("/toArrange")
	public R toArrange(@RequestBody ArrangeBo arrangeBo) {


		List<ArrangeDemandBo> demands = arrangeBo.getDemands();

		if (CollectionUtils.isEmpty(demands)) {

			return R.failed("未安排人数需求");

		}

		Thread thread = new Thread() {
			@Override
			public void run() {
				arrangeParticipantService.arrange(arrangeBo);
				super.run();
			}
		};
		 thread.start();

		return R.ok(0, "后台正在排班，请稍后查看！");
	}




}


