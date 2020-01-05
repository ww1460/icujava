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
package com.pig4cloud.pigx.ccxxicu.service.impl.project;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordDuplicate;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectWarm;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipUseTime;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.NursingReportVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.NursingVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.RegularMatchUtils;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.project.ProjectRecordDuplicateMapper;
import com.pig4cloud.pigx.ccxxicu.service.piping.UseRecordsService;
import com.pig4cloud.pigx.ccxxicu.service.project.*;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目数据记录表复本（用于记录单的生成，每个患者的每个项目每小时只会产生一条数据）
 *
 * @author pigx code generator
 * @date 2019-09-11 14:35:16
 */
@Service
public class ProjectRecordDuplicateServiceImpl extends ServiceImpl<ProjectRecordDuplicateMapper, ProjectRecordDuplicate> implements ProjectRecordDuplicateService {


	@Autowired
	private ProjectRecordService projectRecordService;

	@Autowired
	private ProjectRecordValueService projectRecordValueService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectWarmService projectWarmService;

	@Autowired
	private UseRecordsService useRecordsService;

	@Value("${report.startTime}")
	private Integer startHour;//开始的小时

	@Value("${report.endTime}")
	private Integer endHour;//结束的小时


	@Override
	public boolean add(ProjectRecordDuplicate projectRecordDuplicate) {

		Project project = projectService.selectByProjectId(projectRecordDuplicate.getProjectId());

		if (project==null) {

			return false;

		}
		//判断预警
		if ("0".equals(project.getProjectWarmFlag())) {

			ProjectWarm projectWarm = new ProjectWarm();
			projectWarm.setWarmContent(projectRecordDuplicate.getProjectValue());
			projectWarm.setWarmValue(RegularMatchUtils.getDouble(projectRecordDuplicate.getProjectValue()) + "");
			projectWarm.setDeptId(project.getDeptId());
			projectWarm.setProjectId(project.getProjectId());
			projectWarm.setPatientId(projectRecordDuplicate.getPatientId());
			projectWarm.setCreateUserId(projectRecordDuplicate.getCreateUserId() + "");
			int add = projectWarmService.add(projectWarm);
			if (add == 1) {

				return false;

			}
		}

		return this.save(projectRecordDuplicate);
	}

	/**
	 * 生成记录
	 *
	 * @return
	 */
	@Override
	public boolean saveRecord() {
		//获取当前时间
		LocalDateTime now = LocalDateTime.now();
		//当前时间的上一个小时的数据
		LocalDateTime dateTime = now.minusHours(1);
		System.out.println("查询该时间点的数据 " + dateTime.toString());
		List<ProjectRecord> allRecord = projectRecordService.getAllRecord(dateTime);
		if (CollectionUtils.isEmpty(allRecord)) {

			return true;

		}
		//将数据按患者分类
		Map<String, List<ProjectRecord>> collect = allRecord.stream().collect(Collectors.groupingBy(ProjectRecord::getPatientId));
		//获取所有的患者id
		Set<String> strings = collect.keySet();
		strings.forEach(ar -> {
			List<ProjectRecord> projectRecords = collect.get(ar);

			if (CollectionUtils.isEmpty(projectRecords)) {

				return;

			}

			NursingBo nursingBo = new NursingBo();

			nursingBo.setStartTime(dateTime);
			nursingBo.setPatientId(ar);

			List<ProjectRecordDuplicate> result = new ArrayList<>();

			List<ProjectRecordDuplicate> recordByPatientId = baseMapper.getRecordByPatientId(nursingBo);
			//存放结果
			List<ProjectRecord> res = new ArrayList<>();
			if (!CollectionUtils.isEmpty(recordByPatientId)) {

				//对记录单的数据处理  按项目分组
				Map<String, List<ProjectRecordDuplicate>> collect1 = recordByPatientId.stream().collect(Collectors.groupingBy(ProjectRecordDuplicate::getProjectId));
				//对项目记录的数据处理 按项目分组  存在多条数据
				Map<String, List<ProjectRecord>> collect2 = allRecord.stream().collect(Collectors.groupingBy(ProjectRecord::getProjectId));
				//获取查到的所有项目
				Set<String> strings1 = collect2.keySet();

				//每个项目取最新的一条数据
				strings1.forEach(msg->{

					if (CollectionUtils.isEmpty(collect2.get(msg))) {

						return;

					}
					//取出时间最大的一个
					ProjectRecord projectRecord = collect2.get(msg).stream().max(Comparator.comparing(ProjectRecord::getCreateTime)).orElse(null);
					res.add(projectRecord);

				});
				for (int i = 0; i < res.size(); i++) {

					ProjectRecord msg = res.get(i);

					List<ProjectRecordDuplicate> projectRecordDuplicates = collect1.get(msg.getProjectId());

					if (!CollectionUtils.isEmpty(projectRecordDuplicates)) {

						ProjectRecordDuplicate projectRecordDuplicate = projectRecordDuplicates.get(0);

						Integer id = projectRecordDuplicate.getId();

						BeanUtil.copyProperties(ar,projectRecordDuplicate);

						projectRecordDuplicate.setId(id);
						projectRecordDuplicate.setSourceId(msg.getProjectRecordId());
						this.updateById(projectRecordDuplicate);

						res.remove(i);
						i--;

					}
				}
			}else{

				//对项目记录的数据处理 按项目分组  存在多条数据
				Map<String, List<ProjectRecord>> collect1 = allRecord.stream().collect(Collectors.groupingBy(ProjectRecord::getProjectId));
				//获取查到的所有项目
				Set<String> string = collect1.keySet();

				//每个项目取最新的一条数据
				string.forEach(arr->{

					if (CollectionUtils.isEmpty(collect1.get(arr))) {

						return;

					}
					//取出时间最大的一个
					ProjectRecord projectRecord = collect1.get(arr).stream().max(Comparator.comparing(ProjectRecord::getCreateTime)).orElse(null);
					res.add(projectRecord);

				});

			}

			if (CollectionUtils.isEmpty(res)) {

				return;

			}
			res.forEach(op -> {

				ProjectRecordDuplicate msg = new ProjectRecordDuplicate();
				BeanUtil.copyProperties(op, msg);
				msg.setId(null);
				msg.setProjectRecordDuplicateId(SnowFlake.getId() + "");
				msg.setSource(DataSourceEnum.PROJECT.getCode());
				msg.setSourceId(op.getProjectRecordId());
				result.add(msg);

			});
			this.saveBatch(result);
		});

		return true;

	}

	/**
	 * 查询生命体征24小时数据
	 * @param nursingBo1
	 * @return
	 */
	@Override
	public R getVital(NursingBo nursingBo1) {

		/**
		 * 判断时间并对其做处理
		 * 1、如果时间为当天 判断是否在8点后
		 * 		8点后则查询 当天8点到第二天7点的数据
		 * 		8点前则查询 前一天8点  到尽头8点的数据
		 * 2、如果时间为历史时间 则查询这一天8点到第二天7点的数据
		 */

		NursingBo nursingBo = disposeTime(nursingBo1);
		nursingBo1.setNursingReportFlag(3);

		/**
		 * 根据条件查询该类型数据
		 */
		List<NursingReportVo> records = baseMapper.getVital(nursingBo);
		List<NursingReportVo> nursingReportVos1 = baseMapper.selectReportProject(nursingBo1);
		//对项目做一下处理
		List<NursingReportVo> record = disposeProject(records, nursingReportVos1);
		/**
		 * 对数据作格式处理  每个项目的数据作24个  对应时间没有数据则为null
		 */
		List<NursingReportVo> nursingReportVos = setData(record);
		Project project = new Project();
		project.setProjectType(1);
		project.setDeptId(nursingBo1.getDeptId());
		//查询该类型下的项目是否存在固定值
		List<ProjectValueVo> projectRecordValues = projectRecordValueService.selectProjectValue(project);
		//最终结果值
		NursingVo nursingVo = new NursingVo();
		nursingVo.setNursingReportVos(nursingReportVos);
		nursingVo.setProjectValueVos(projectRecordValues);
		nursingVo.setHour(startHour);
		return R.ok(nursingVo);
	}

	/**
	 * 查询记录单一数据
	 *
	 * @param nursingBo1
	 * @return
	 */
	@Override
	public R getNursingReport(NursingBo nursingBo1) {
		/**
		 * 判断时间并对其做处理
		 * 1、如果时间为当天 判断是否在8点后
		 * 		8点后则查询 当天8点到第二天7点的数据
		 * 		8点前则查询 前一天8点  到尽头8点的数据
		 * 2、如果时间为历史时间 则查询这一天8点到第二天7点的数据
		 */

		NursingBo nursingBo = disposeTime(nursingBo1);

		/**
		 * 根据条件查询该类型数据
		 */
		List<NursingReportVo> records = this.getRecord(nursingBo);
		//查询该报表的所有项目
		List<NursingReportVo> nursingReportVo = this.selectReportProject(nursingBo1);
		//对项目做一下处理
		List<NursingReportVo> record = disposeProject(records, nursingReportVo);
		/**
		 * 对数据作格式处理  每个项目的数据作24个  对应时间没有数据则为null
		 */
		List<NursingReportVo> nursingReportVos = setData(record);
		//查询该类型下的项目是否存在固定值
		List<ProjectValueVo> projectRecordValues = projectRecordValueService.getProjectValue(nursingBo);
		//查询护理人
		List<Nurse> createName = this.getCreateName(nursingBo);

		//最终结果值
		NursingVo nursingVo = new NursingVo();
		nursingVo.setNursingReportVos(nursingReportVos);
		nursingVo.setProjectValueVos(projectRecordValues);
		nursingVo.setNurseName(createName);
		if (nursingBo1.getNursingReportFlag()==2) {
			List<PipUseTime> pipUseTimes = useRecordsService.ReportTwo(nursingBo1.getPatientId());
			nursingVo.setPipUseTimes(pipUseTimes);
		}
		nursingVo.setHour(startHour);
		return R.ok(nursingVo);
	}

	/**
	 * 查询护理报表数据
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public List<NursingReportVo> getRecord(NursingBo nursingBo) {
		return baseMapper.getRecord(nursingBo);
	}

	/**
	 * 将查询到的数据 按时间放入
	 */
	private List<NursingReportVo> setData(List<NursingReportVo> record) {


		List<ProjectRecordDuplicate> result = new ArrayList<>(24);

		for (int i = 0; i < 24; i++) {
			result.add(null);
		}
		//循环项目
		record.forEach(ar -> {
			//项目数据
			List<ProjectRecordDuplicate> records = ar.getProjectRecordDuplicates();
			List<ProjectRecordDuplicate> msg = new ArrayList<>(24);
			msg.addAll(result);
			if (CollectionUtils.isEmpty(records)) {

				ar.setProjectRecordDuplicates(msg);
				return;
			}
			//循环数据结果 将对应时间的结果替换
			records.forEach(rep -> {

				int hour1 = rep.getCreateTime().getHour();

				int i = hour1 - startHour;

				int num = i < 0 ? i + 24 : i;

				msg.set(num, rep);

			});

			ar.setProjectRecordDuplicates(msg);
		});
		record.sort(Comparator.comparing(NursingReportVo::getShowIndex));
		return record;

	}

	/**
	 * 将项目进行完善
	 *
	 * @param record
	 * @param nursingReportVos
	 * @return
	 */
	private List<NursingReportVo> disposeProject(List<NursingReportVo> record,List<NursingReportVo> nursingReportVos) {

		//对已经存在记录的结果 进行分组
		Map<String, List<NursingReportVo>> collect = record.stream().collect(Collectors.groupingBy(NursingReportVo::getProjectId));

		for (int i = 0; i < nursingReportVos.size(); i++) {

			NursingReportVo ar = nursingReportVos.get(i);

			//查询结果中是否包含该项目
			List<NursingReportVo> nursingReportVos1 = collect.get(ar.getProjectId());
			//如果该项目不存在数据
			if (CollectionUtils.isEmpty(nursingReportVos1)) {

				record.add(ar);

			}
		}
		//将数据做一下排序处理
		record.sort(Comparator.comparing(NursingReportVo::getId));

		return record;
	}


	/**
	 * 对时间进行处理
	 */
	private NursingBo disposeTime(NursingBo nursingBo) {

		LocalDateTime endTime = nursingBo.getEndTime();

		int hour = endTime.getHour();

		LocalDateTime now = LocalDateTime.now();

		String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String format = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		//首先判断传来的日期是否为当前时间 因为当前时间有着8点前后的区分
		if (formatNow.equals(format)) {
			nursingBo.setStartTime(now);
			this.getNowHourRecord(nursingBo);

			if (hour >= 8) {

				LocalDateTime dateTime = endTime.withHour(8);
				nursingBo.setStartTime(dateTime);

			} else {

				LocalDateTime dateTime = endTime.minusDays(1);
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
		return nursingBo;
	}

	/**
	 * 查询某记录单的项目
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public List<NursingReportVo> selectReportProject(NursingBo nursingBo) {
		return baseMapper.selectReportProject(nursingBo);
	}

	/**
	 * 获取当前小时的数据
	 */
	private boolean getNowHourRecord(NursingBo nursingBo) {


		//获取当前时间
		LocalDateTime now = LocalDateTime.now();

		System.out.println("查询该时间点的数据 " + now.toString());
		List<ProjectRecord> allRecord = projectRecordService.getAllRecordByPatient(nursingBo);

		if (CollectionUtils.isEmpty(allRecord)) {

			return true;

		}
		//最后新增到记录单的数据
		List<ProjectRecordDuplicate> result = new ArrayList<>();
		//记录单的数据
		List<ProjectRecordDuplicate> recordByPatientId = baseMapper.getRecordByPatientId(nursingBo);
		//存放结果
		List<ProjectRecord> res = new ArrayList<>();
		if (!CollectionUtils.isEmpty(recordByPatientId)) {
			//对记录单的数据处理  按项目分组
			Map<String, List<ProjectRecordDuplicate>> collect = recordByPatientId.stream().collect(Collectors.groupingBy(ProjectRecordDuplicate::getProjectId));
			//对项目记录的数据处理 按项目分组  存在多条数据
			Map<String, List<ProjectRecord>> collect1 = allRecord.stream().collect(Collectors.groupingBy(ProjectRecord::getProjectId));
			//获取查到的所有项目
			Set<String> strings = collect1.keySet();

			//每个项目取最新的一条数据
			strings.forEach(ar->{

				if (CollectionUtils.isEmpty(collect1.get(ar))) {

					return;

				}
				//取出时间最大的一个
				ProjectRecord projectRecord = collect1.get(ar).stream().max(Comparator.comparing(ProjectRecord::getCreateTime)).orElse(null);
				res.add(projectRecord);

			});

			//循环结果
			for (int i = 0; i < res.size(); i++) {

				ProjectRecord ar = res.get(i);
				//取出该项目的记录值
				List<ProjectRecordDuplicate> projectRecordDuplicates = collect.get(ar.getProjectId());

				if (!CollectionUtils.isEmpty(projectRecordDuplicates)) {

					ProjectRecordDuplicate projectRecordDuplicate = projectRecordDuplicates.get(0);

					Integer id = projectRecordDuplicate.getId();

					BeanUtil.copyProperties(ar,projectRecordDuplicate);

					projectRecordDuplicate.setId(id);
					projectRecordDuplicate.setSourceId(ar.getProjectRecordId());
					this.updateById(projectRecordDuplicate);

					res.remove(i);
					i--;

				}
			}
		}else{

			//对项目记录的数据处理 按项目分组  存在多条数据
			Map<String, List<ProjectRecord>> collect1 = allRecord.stream().collect(Collectors.groupingBy(ProjectRecord::getProjectId));
			//获取查到的所有项目
			Set<String> strings = collect1.keySet();

			//每个项目取最新的一条数据
			strings.forEach(ar->{

				if (CollectionUtils.isEmpty(collect1.get(ar))) {

					return;

				}
				//取出时间最大的一个
				ProjectRecord projectRecord = collect1.get(ar).stream().max(Comparator.comparing(ProjectRecord::getCreateTime)).orElse(null);
				res.add(projectRecord);

			});

		}

		if (CollectionUtils.isEmpty(res)) {
			return true;
		}

		res.forEach(ar -> {

			ProjectRecordDuplicate msg = new ProjectRecordDuplicate();
			BeanUtil.copyProperties(ar, msg);
			msg.setId(null);
			msg.setProjectRecordDuplicateId(SnowFlake.getId() + "");
			msg.setSource(DataSourceEnum.PROJECT.getCode());
			msg.setSourceId(ar.getProjectRecordId());

			result.add(msg);
		});




		return this.saveBatch(result);


	}

	/**
	 * 获取护理人姓名
	 *
	 * @return
	 */
	private List<Nurse> getCreateName(NursingBo nursingBo) {
		//创建结果集合
		List<Nurse> nurseName = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			Nurse nurse = new Nurse();
			nurseName.add(nurse);
		}

		//每小时查询一次
		for (int i = 0; i < 24; i++) {

			LocalDateTime dateTime = nursingBo.getStartTime().minusDays(-i);

			nursingBo.setStartTime(dateTime);
			//查询结果
			List<Nurse> createName = baseMapper.getCreateName(nursingBo);
			//当结果不为空时 跳到下一个时间段  每8小时一个结果即可
			if (!CollectionUtils.isEmpty(createName)&&createName.get(0)!= null) {
				nurseName.set(i/8, createName.get(0));
				System.out.println(createName.get(0));
				if (i>15) {
					break;
				}
			}
		}

		return nurseName;

	}

	/**
	 * 数据修改
	 * @param projectRecordDuplicate
	 * @return
	 */
	@Override
	public boolean updateRecord(ProjectRecordDuplicate projectRecordDuplicate) {
		//获取原数据
		ProjectRecordDuplicate byId = this.getById(projectRecordDuplicate.getId());
		//对数据的详细描述的数值进行替换
		String projectSpecificRecord = projectRecordDuplicate.getProjectSpecificRecord();

		String replace = projectSpecificRecord.replace(byId.getProjectValue(), projectRecordDuplicate.getProjectValue());

		projectRecordDuplicate.setProjectSpecificRecord(replace);
		//得到原数据的项目记录
		String sourceId = projectRecordDuplicate.getSourceId();

		if (StringUtils.isEmpty(sourceId)) {

			return this.updateById(projectRecordDuplicate);

		}

		ProjectRecord one = projectRecordService.getOne(Wrappers.<ProjectRecord>query().lambda()
				.eq(ProjectRecord::getProjectRecordId, sourceId));

		if (one==null) {

			return this.updateById(projectRecordDuplicate);

		}
		//对项目的记录的数据进行修改
		one.setProjectSpecificRecord(projectRecordDuplicate.getProjectSpecificRecord());
		one.setProjectValue(projectRecordDuplicate.getProjectValue());
		one.setUpdateUserId(projectRecordDuplicate.getUpdateUserId());

		projectRecordService.updateRecord(one);

		return this.updateById(projectRecordDuplicate);
	}
}
