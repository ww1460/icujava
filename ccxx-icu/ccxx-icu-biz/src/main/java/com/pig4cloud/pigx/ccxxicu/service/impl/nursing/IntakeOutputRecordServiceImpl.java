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
package com.pig4cloud.pigx.ccxxicu.service.impl.nursing;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IntakeOutPutBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeCountVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputShow;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeTableVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.InputDrugEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.IntakeOutputEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.ProjectTypeEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nursing.IntakeOutputRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.nursing.IntakeOutputRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 出入量记录
 *
 * @author pigx code generator
 * @date 2019-08-22 16:27:47
 */
@Service
public class IntakeOutputRecordServiceImpl extends ServiceImpl<IntakeOutputRecordMapper, IntakeOutputRecord> implements IntakeOutputRecordService {


	@Autowired
	private ProjectService projectService;

	@Value("${report.startTime}")
	private Integer startHour;//开始的小时

	@Value("${report.endTime}")
	private Integer endHour;//结束的小时


	/**
	 * 查询平衡量
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public Integer getEquilibriumAmount(IntakeOutputRecord intakeOutputRecord) {
		/**
		 *在新增时查询出入量的平衡量
		 * 查询的为今天的最新的一条
		 */
		IntakeOutputRecord record = baseMapper.getEquilibriumAmount(intakeOutputRecord);
		if (record == null) {

			return null;

		}
		int hour = intakeOutputRecord.getCreateTime().getHour();
		int hour1 = record.getCreateTime().getHour();
		//该条数据是属于重新计算的边界的数据
		if (hour >= 8 && hour1 < 8) {

			return null;

		}



		return record.getEquilibriumAmount();
	}

	/**
	 * 出入量添加
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public boolean add(IntakeOutputRecord intakeOutputRecord) {
		//获取该患者最新的平衡量
		Integer equilibriumAmount = this.getEquilibriumAmount(intakeOutputRecord);

		if (intakeOutputRecord.getIntakeOutputType().equals(IntakeOutputEnum.OUTPUT.getCode())) {

			if (intakeOutputRecord.getIntakeOutputValue() > 0) {
				intakeOutputRecord.setIntakeOutputValue(-intakeOutputRecord.getIntakeOutputValue());
			}

		} else {

			List<InputDrugEnum> inputDrugEnums = Arrays.asList(InputDrugEnum.values());

			inputDrugEnums.forEach(ar->{

				String description = ar.getDescription();

				if (intakeOutputRecord.getValueDescription().contains(description)) {

					intakeOutputRecord.setRemarks("胶体");

				}

			});

		}

		//当平衡量不为空时  将此次的出入量 加到平衡量中  为空则将出入量作为平衡量
		int num = (equilibriumAmount == null ? 0 : equilibriumAmount) + intakeOutputRecord.getIntakeOutputValue();

		intakeOutputRecord.setEquilibriumAmount(num);

		intakeOutputRecord.setIntakeOutputId(SnowFlake.getId() + "");

		return this.save(intakeOutputRecord);
	}

	/**
	 * 出入量修改
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public boolean updateRecord(IntakeOutputRecord intakeOutputRecord) {
		//该数据前 最新的出入量
		Integer equilibriumAmount = this.getEquilibriumAmount(intakeOutputRecord);
		//查询该出入的原数据
		IntakeOutputRecord byId = this.getById(intakeOutputRecord.getId());

		//判断出入的值是否发生改变
		if (intakeOutputRecord.getIntakeOutputValue().equals(byId.getIntakeOutputValue())) {
			//出入量值 未改变  直接修改
			return this.updateById(intakeOutputRecord);

		}

		//当平衡量不为空时  将此次的出入量 加到平衡量中  为空则将出入量作为平衡量
		intakeOutputRecord.setEquilibriumAmount((equilibriumAmount == null ? 0 : equilibriumAmount) + intakeOutputRecord.getIntakeOutputValue());
		//进行修改该条数据
		boolean b = this.updateById(intakeOutputRecord);

		//查询某条数据之后的数据
		List<IntakeOutputRecord> intakeOutputRecords = this.selectAfter(intakeOutputRecord);
		//后面不存在数据时  直接返回结果
		if (intakeOutputRecords == null) {

			return b;

		}
		//获取现在的平衡量
		Integer num = intakeOutputRecord.getEquilibriumAmount();
		//将数据逐条修改
		for (int i = 0; i < intakeOutputRecords.size(); i++) {
			IntakeOutputRecord ar = intakeOutputRecords.get(i);
			ar.setEquilibriumAmount(num + ar.getIntakeOutputValue());
			num = ar.getEquilibriumAmount();

		}

		return this.updateBatchById(intakeOutputRecords);
	}

	/**
	 * 出入量删除
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public boolean delete(IntakeOutputRecord intakeOutputRecord) {

		//查询某条数据之后的数据
		List<IntakeOutputRecord> intakeOutputRecords = this.selectAfter(intakeOutputRecord);

		//后面数据为空  直接删除
		if (CollectionUtils.isEmpty(intakeOutputRecords)) {

			return this.updateById(intakeOutputRecord);

		}
		//获取到该数据的出入量
		Integer num = intakeOutputRecord.getIntakeOutputValue();
		//对数据进行修改
		for (int i = 0; i < intakeOutputRecords.size(); i++) {
			IntakeOutputRecord ar = intakeOutputRecords.get(i);
			ar.setEquilibriumAmount(ar.getEquilibriumAmount() - num);
		}
		//删除该条数据
		this.updateById(intakeOutputRecord);
		//修改后续数据
		return this.updateBatchById(intakeOutputRecords);
	}

	/**
	 * 查询该数据之后的出入量
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public List<IntakeOutputRecord> selectAfter(IntakeOutputRecord intakeOutputRecord) {
		return baseMapper.selectAfter(intakeOutputRecord);
	}

	/**
	 * 查询某关联数据
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public IntakeOutputRecord selectCorrelationValue(IntakeOutputRecord intakeOutputRecord) {
		return baseMapper.selectCorrelationValue(intakeOutputRecord);
	}

	/**
	 * 出入量分页查询
	 *
	 * @param page
	 * @param intakeOutPutBo
	 * @return
	 */
	@Override
	public IPage<IntakeOutputVo> selectByPage(Page page, IntakeOutPutBo intakeOutPutBo) {
		return baseMapper.selectByPage(page, intakeOutPutBo);
	}

	/**
	 * 查询该患者的出入量数据
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public List<IntakeOutputShow> getDateShow(String patientId, String projectId) {
		return baseMapper.getDateShow(patientId, projectId);
	}


	/**
	 * 查询某小时内的平衡量
	 *
	 * @param patientId
	 * @param createTime
	 * @return
	 */
	@Override
	public Integer selectGetCount(String patientId, LocalDateTime createTime) {
		return baseMapper.selectGetCount(patientId, createTime);
	}

	/**
	 * 护理记录单一
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public IntakeTableVo getReport(NursingBo nursingBo) {

		LocalDateTime endTime = nursingBo.getEndTime();

		int hour = endTime.getHour();

		LocalDateTime now = LocalDateTime.now();

		String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String format = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		//首先判断传来的日期是否为当前时间 因为当前时间有着8点前后的区分
		if (formatNow.equals(format)) {

			if (hour > 8) {

				LocalDateTime dateTime = endTime.withHour(8);
				nursingBo.setStartTime(dateTime);

			} else {

				LocalDateTime dateTime = endTime.minusDays(-1);
				LocalDateTime dateTime1 = dateTime.withHour(8);
				nursingBo.setStartTime(dateTime1);

			}

		} else {

			LocalDateTime dateTime = endTime.withHour(8);
			nursingBo.setStartTime(dateTime);
			LocalDateTime dateTime1 = endTime.minusDays(-1);
			LocalDateTime dateTime2 = dateTime1.withHour(7);
			nursingBo.setEndTime(dateTime2);

		}
		List<String> remarks = baseMapper.getRemarks(nursingBo);
		//查出出入量都有哪些项目
		List<Project> projects = projectService.selectByType(ProjectTypeEnum.OUTPUT_PROJECT.getCode());
		List<Project> projectLists1 = projectService.selectByType(ProjectTypeEnum.INTAKE_PROJECT.getCode());
		projects.addAll(projectLists1);
		//每个项目的24小时数据
		HashMap<String, List<List<IntakeOutputShow>>> intake = new HashMap<>();
		HashMap<String, List<List<IntakeOutputShow>>> output = new HashMap<>();
		HashMap<String, String> projectName = new HashMap<>();

		//线程池
		Vector<Thread> threadVector = new Vector<>();
		//返回值
		IntakeTableVo intakeTableVo = new IntakeTableVo();
		//每小时的总计
		List<Integer> count = new ArrayList<>();

		//根据项目进行查询
		for (int j = 0; j < projects.size(); j++) {

			Project ar = projects.get(j);

			int finalJ = j;
			Thread thread = new Thread() {

				@Override
				public void run() {
					List<List<IntakeOutputShow>> res = new ArrayList<>();
					for (int i = 0; i < 24; i++) {
						//从开始时间开始查询
						LocalDateTime dateTime = nursingBo.getStartTime().minusHours(-i);
						List<IntakeOutputShow> allDay = baseMapper.getAllDay(nursingBo.getPatientId(), ar.getProjectId(), dateTime);
						res.add(allDay);
						if (finalJ == 0) {
							//该小时的平衡量 只需计算一次即可
							Integer integer = baseMapper.selectGetCount(nursingBo.getPatientId(), dateTime);
							count.add(integer);
						}

					}
					//存放项目数据
					projectName.put(ar.getProjectCode(), ar.getProjectName());
					//封装出入量数据
					if (ar.getProjectType().equals(ProjectTypeEnum.OUTPUT_PROJECT.getCode())) {

						output.put(ar.getProjectCode(), res);
					} else {
						intake.put(ar.getProjectCode(), res);
					}
					super.run();
				}
			};
			threadVector.add(thread);
			thread.start();

		}
		//等待线程结束
		threadVector.forEach(ar -> {
			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		IntakeCountVo intakeCountVo = this.ReportOneRight(intake);
		IntakeCountVo intakeCountVo1 = this.ReportOneRight(output);
		IntakeCountVo res = this.DataMerge(intakeCountVo, intakeCountVo1);
		intakeTableVo.setCountNum(count);
		intakeTableVo.setProjectName(projectName);
		intakeTableVo.setIntakeCountVo(res);
		if (CollectionUtils.isNotEmpty(remarks)) {
			HashMap<String, List<List<IntakeOutputShow>>> setIndex = setIndex(remarks, intake);
			intakeTableVo.setIntakeShows(setIndex);
		} else {
			intakeTableVo.setIntakeShows(intake);
		}
		intakeTableVo.setOutputShows(output);
		return intakeTableVo;
	}


	/**
	 * 对护理记录单一的入量 作索引
	 */
	private HashMap<String, List<List<IntakeOutputShow>>> setIndex(List<String> remarks,
																   HashMap<String, List<List<IntakeOutputShow>>> intake) {

		Set<String> strings = intake.keySet();
		Vector<Thread> threadVector = new Vector<>();
		strings.forEach(ar -> {

			Thread thread = new Thread() {

				@Override
				public void run() {

					List<List<IntakeOutputShow>> lists = intake.get(ar);

					if (!CollectionUtils.isEmpty(lists)) {

						lists.forEach(msg -> {

							if (CollectionUtils.isEmpty(msg)) {

								return;
							}

							msg.forEach(res -> {

								String causeRemark = res.getCauseRemark();

								if (StringUtils.isEmpty(causeRemark)) {
									return;
								}

								for (int i = 0; i < remarks.size(); i++) {

									if (causeRemark.equals(remarks.get(i))) {
										res.setCauseRemark((i + 1) + "");
									}
								}
							});
						});
					}
					super.run();
				}
			};

			thread.start();
			threadVector.add(thread);
		});

		threadVector.forEach(ar -> {

			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});
		return intake;
	}


	/**
	 * 护理记录单一右边展示的数据
	 */
	private IntakeCountVo ReportOneRight(HashMap<String, List<List<IntakeOutputShow>>> intake) {

		if (CollectionUtils.isEmpty(intake)) {
			return null;
		}

		Set<String> strings = intake.keySet();

		HashMap<String, HashMap<Integer, List<List<IntakeOutputShow>>>> result = new HashMap<>();
		int nums = 0;
		strings.forEach(ar -> {

			HashMap<Integer, List<List<IntakeOutputShow>>> res = new HashMap<>();

			List<List<IntakeOutputShow>> lists = intake.get(ar);

			List<List<IntakeOutputShow>> lists1 = lists.subList(0, 8);

			List<List<IntakeOutputShow>> lists2 = lists.subList(8, 15);

			List<List<IntakeOutputShow>> lists3 = lists.subList(15, 24);

			res.put(1, lists1);
			res.put(2, lists2);
			res.put(3, lists3);

			result.put(ar, res);

		});

		List<Integer> projectCounts = new ArrayList<>(3);//项目上的数据合计

		List<Integer> timeCount = new ArrayList<>();//时间上的数据合计

		HashMap<String, List<Integer>> outPut = new HashMap<>();//出量


		int baiBan = 0;//白班合计
		int xiaoYe = 0;//小夜班合计
		int daYe = 0;//大夜合计

		Iterator<String> iterator = strings.iterator();
		while (iterator.hasNext()) {
			String ar = iterator.next();
			Integer projectCount = 0;
			int baiBan1 = 0;//白班合计
			int xiaoYe1 = 0;//小夜班合计
			int daYe1 = 0;//大夜合计

			HashMap<Integer, List<List<IntakeOutputShow>>> map = result.get(ar);
			//早班的出入量
			List<List<IntakeOutputShow>> lists = map.get(1);
			List<Integer> projectRecord = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(lists)) {

				for (int i = 0; i < lists.size(); i++) {

					List<IntakeOutputShow> msg = lists.get(i);

					if (CollectionUtils.isNotEmpty(msg)) {

						for (int j = 0; j < msg.size(); j++) {

							IntakeOutputShow num = msg.get(j);

							if (num != null) {

								baiBan1 += num.getIntakeOutputValue();

							}
						}
					}
				}
			}
			projectCount = projectCount + baiBan1;
			baiBan = baiBan + projectCount;
			projectRecord.add(baiBan1 == 0 ? null : baiBan1);

			//小夜的出入量
			List<List<IntakeOutputShow>> lists1 = map.get(2);

			if (CollectionUtils.isNotEmpty(lists1)) {

				for (int i = 0; i < lists1.size(); i++) {

					List<IntakeOutputShow> msg = lists1.get(i);

					if (CollectionUtils.isNotEmpty(msg)) {

						for (int j = 0; j < msg.size(); j++) {

							IntakeOutputShow num = msg.get(j);

							if (num != null) {

								xiaoYe1 = xiaoYe1 + num.getIntakeOutputValue();

							}
						}
					}
				}
			}

			projectCount = projectCount + xiaoYe1;
			xiaoYe = xiaoYe + xiaoYe1;
			projectRecord.add(xiaoYe1 == 0 ? null : xiaoYe1);


			//大夜的出入量
			List<List<IntakeOutputShow>> lists2 = map.get(3);

			if (CollectionUtils.isNotEmpty(lists2)) {
				//这里计算的是 每个项目的
				for (int i = 0; i < lists2.size(); i++) {

					List<IntakeOutputShow> msg = lists2.get(i);

					if (CollectionUtils.isNotEmpty(msg)) {

						for (int j = 0; j < msg.size(); j++) {

							IntakeOutputShow num = msg.get(j);

							if (num != null) {

								daYe1 += num.getIntakeOutputValue();
							}
						}
					}
				}
			}

			projectCount = projectCount + daYe1;
			daYe = daYe + daYe1;
			projectRecord.add(daYe1 == 0 ? null : daYe1);
			projectCounts.add(projectCount == 0 ? null : projectCount);
			projectRecord.add(projectCount == 0 ? null : projectCount);
			nums = nums + projectCount;
			outPut.put(ar, projectRecord);
		}

		timeCount.add(baiBan == 0 ? null : baiBan);
		timeCount.add(xiaoYe == 0 ? null : xiaoYe);
		timeCount.add(daYe == 0 ? null : daYe);
		timeCount.add(nums == 0 ? null : nums);
		outPut.put("HEJISHUJU", timeCount);
		IntakeCountVo intakeCountVo = new IntakeCountVo();

		intakeCountVo.setIntake(outPut);

		return intakeCountVo;


	}

	/**
	 * 将出入量合并一个对象
	 *
	 * @param intake
	 * @param output
	 * @return
	 */
	private IntakeCountVo DataMerge(IntakeCountVo intake, IntakeCountVo output) {

		HashMap<String, List<Integer>> intake1 = intake.getIntake();
		HashMap<String, List<Integer>> outPut = output.getIntake();

		List<Integer> hejishuju = intake1.get("HEJISHUJU");

		List<Integer> hejishuju1 = outPut.get("HEJISHUJU");

		List<Integer> num = new ArrayList<>();//平衡量

		for (int i = 0; i < 4; i++) {

			Integer integer = hejishuju.get(i);

			int a = integer == null ? 0 : integer;

			Integer integer1 = hejishuju1.get(i);

			int b = integer1 == null ? 0 : integer1;

			num.add(a + b == 0 ? null : a + b);

		}
		intake.setOutPut(outPut);
		intake.setEquilibriumAmount(num);
		return intake;

	}
}
