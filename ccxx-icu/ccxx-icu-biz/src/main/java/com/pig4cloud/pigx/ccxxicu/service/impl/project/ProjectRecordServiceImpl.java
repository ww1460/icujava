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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ProjectRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingProjectVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.ProjectTypeEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.RegularMatchUtils;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.project.ProjectRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目数据记录表
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:06
 */
@Service
public class ProjectRecordServiceImpl extends ServiceImpl<ProjectRecordMapper, ProjectRecord> implements ProjectRecordService {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectRecordService projectRecordService;

	@Autowired
	private ProjectRecordValueService projectRecordValueService;


	/**
	 * 多个项目新增
	 *
	 * @param entityList
	 * @return
	 */
	@Override
	public boolean addList(Collection<ProjectRecord> entityList) {
		LocalDateTime now = LocalDateTime.now();

		for (ProjectRecord ar : entityList) {
			ar.setDelFlag(0);
			ar.setCreateTime(now);
			if (StringUtils.isEmpty(ar.getProjectRecordId())) {
				ar.setProjectRecordId(SnowFlake.getId() + "");
			}
			//判断主要数据是否为空
			if (StringUtils.isEmpty(ar.getProjectSpecificRecord())
					&& (StringUtils.isEmpty(ar.getRemarks()))) {
				continue;
			}
			boolean b1 = this.verifyValue(ar.getProjectId());

			//当有固定值  但是标识未添加时
			if (b1 && StringUtils.isEmpty(ar.getProjectValue())) {

				ar = this.setValue(ar);

			}

			Project project = projectService.selectByProjectId(ar.getProjectId());


			String replace = ar.getProjectSpecificRecord().replace(project.getProjectCode(), project.getProjectName());

			ar.setProjectSpecificRecord(replace);

			if (!b1) {

				if (RegularMatchUtils.getDouble(ar.getProjectSpecificRecord()) != null) {

					ar.setProjectValue(RegularMatchUtils.getDouble(ar.getProjectSpecificRecord()) + "");

				}

			}

			//判断是否为血压
			boolean b = this.verifyBp(ar, 1);
			if (b) {

				String bp = RegularMatchUtils.getBP(ar.getProjectSpecificRecord());
				ar.setProjectValue(bp);
				this.save(ar);
				continue;
			}
			boolean save = this.save(ar);
			if (!save) {

				return false;
			}
		}

		return true;
	}

	/**
	 * 当有固定的值的项目  无标识时  处理
	 *
	 * @param projectRecord
	 * @return
	 */
	private ProjectRecord setValue(ProjectRecord projectRecord) {

		List<ProjectRecordValue> list = projectRecordValueService.list(Wrappers.<ProjectRecordValue>query().lambda()
				.eq(ProjectRecordValue::getDelFlag, 0)
				.eq(ProjectRecordValue::getProjectId, projectRecord.getId())
		);

		if (CollectionUtils.isEmpty(list)) {

			return projectRecord;

		}

		for (int i = 0; i < list.size(); i++) {
			ProjectRecordValue ar = list.get(i);
			if (projectRecord.getProjectSpecificRecord().equals(ar.getRecordValue()) ||
					projectRecord.getProjectSpecificRecord().contains(ar.getRecordValue())) {
				projectRecord.setProjectValue(ar.getDataFlag() + "");
				return projectRecord;
			}
		}

		return projectRecord;
	}


	/**
	 * 判断项目是否有固定值
	 *
	 * @return
	 */
	private boolean verifyValue(String projectId) {

		Project project = projectService.selectByProjectId(projectId);

		if (project == null) {

			return false;

		}

		Integer projectRecordValueFlag = project.getProjectRecordValueFlag();

		if (projectRecordValueFlag == 0) {

			return true;

		}
		return false;
	}


	/**
	 * 单个项目新增数据
	 *
	 * @param projectRecord
	 * @return
	 */
	@Override
	public boolean add(ProjectRecord projectRecord) {

		projectRecord.setDelFlag(0);

		if (projectRecord.getCreateTime()==null) {
			projectRecord.setCreateTime(LocalDateTime.now());
		}

		if (StringUtils.isEmpty(projectRecord.getProjectRecordId())) {
			projectRecord.setProjectRecordId(SnowFlake.getId() + "");
		}

		boolean b1 = this.verifyValue(projectRecord.getProjectId());
		//当有固定值  但是标识未添加时
		if (b1 && (StringUtils.isEmpty(projectRecord.getProjectValue())||"null".equals(projectRecord.getProjectValue()))){

			projectRecord = this.setValue(projectRecord);

		}

		//项目数据  和 备注 全为空时
		if (projectRecord.getProjectSpecificRecord() == null && projectRecord.getRemarks() == null) {

			return true;

		}


		Project project = projectService.selectByProjectId(projectRecord.getProjectId());

		//将code值 转换为name
		String replace = projectRecord.getProjectSpecificRecord().replace(project.getProjectCode(), project.getProjectName());

		projectRecord.setProjectSpecificRecord(replace);

		if (!b1) {

			if (projectRecord.getProjectSpecificRecord() != null && RegularMatchUtils.getDouble(projectRecord.getProjectSpecificRecord()) != null) {

				projectRecord.setProjectValue(RegularMatchUtils.getDouble(projectRecord.getProjectSpecificRecord()) + "");

			}

		}

		//判断是否为血压
		boolean b = this.verifyBp(projectRecord, 1);

		if (b) {

			String bp = RegularMatchUtils.getBP(projectRecord.getProjectSpecificRecord());
			projectRecord.setProjectValue(bp);
			return this.save(projectRecord);

		}

		return this.save(projectRecord);
	}

	/**
	 * 获取多个项目的记录
	 *
	 * @param projectRecords
	 * @return
	 */
	@Override
	public List<ProjectRecord> selectNewRecord(List<ProjectRecord> projectRecords) {

		List<ProjectRecord> records = new ArrayList<>();

		projectRecords.forEach(ar ->
		{
			records.add(this.getOneRecord(ar.getPatientId(), ar.getProjectId()));
		});

		return records;
	}

	/**
	 * 获取单个项目的记录
	 *
	 * @return
	 */
	@Override
	public ProjectRecord getOneRecord(String patientId, String projectId) {
		return baseMapper.getOneRecord(patientId, projectId);
	}

	/**
	 * 查询某关联数据
	 *
	 * @param sourceId
	 * @return
	 */
	@Override
	public List<NursingProjectVo> getValueCorrelation(String sourceId) {
		return baseMapper.getValueCorrelation(sourceId);
	}

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param projectRecordBo
	 * @return
	 */
	@Override
	public IPage<ProjectRecordVo> selectByPage(Page page, ProjectRecordBo projectRecordBo) {
		return baseMapper.selectByPage(page, projectRecordBo);
	}

	/**
	 * 修改记录
	 *
	 * @param projectRecord
	 * @return
	 */
	@Override
	public boolean updateRecord(ProjectRecord projectRecord) {

		Project project = projectService.selectByProjectId(projectRecord.getProjectId());

		if (project.getProjectRecordValueFlag().equals(0)) {

			projectRecord = this.setValue(projectRecord);

		}

		//判断血压
		this.verifyBp(projectRecord, 2);
		boolean b = this.updateBP(projectRecord);
		if (b) {
			return true;
		}


		return this.updateById(projectRecord);
	}

	/**
	 * 删除记录
	 *
	 * @param projectRecord
	 * @return
	 */
	@Override
	public boolean delete(ProjectRecord projectRecord) {
		//判断血压
		this.verifyBp(projectRecord, 3);
		return this.updateById(projectRecord);
	}

	/**
	 * 拆分血压
	 *
	 * @param projectRecord
	 * @return
	 */
	private boolean verifyBp(ProjectRecord projectRecord, Integer type) {

		Project project = projectService.selectByName("血压");

		if (project == null) {
			return false;
		}

		if (!projectRecord.getProjectId().equals(project.getProjectId())) {

			return false;

		}

		Project sbp = projectService.selectByCode("SBP", ProjectTypeEnum.VITAL_SIGN_PROJECT.getCode());
		Project dbp = projectService.selectByCode("DBP", ProjectTypeEnum.VITAL_SIGN_PROJECT.getCode());
		if (sbp == null || dbp == null) {

			return false;

		}

		ProjectRecord ar = new ProjectRecord();
		BeanUtil.copyProperties(projectRecord, ar);
		//取出血压的值
		List<String> strings = RegularMatchUtils.splitBP(projectRecord.getProjectSpecificRecord());
		if (CollectionUtils.isEmpty(strings)) {
			return false;
		}
		String id = projectRecord.getProjectRecordId();
		if (type == 1) {
			//新增
			strings.forEach(msg -> {
				ar.setSourceId(id);
				ar.setProjectValue(msg);
				ar.setProjectId(strings.indexOf(msg) == 0 ? sbp.getProjectId() : dbp.getProjectId());
				ar.setProjectRecordId(SnowFlake.getId() + "");
				this.save(ar);
			});
		} else if (type == 2) {
			//修改
			List<NursingProjectVo> valueCorrelation = this.getValueCorrelation(id);

			if (CollectionUtils.isEmpty(valueCorrelation)) {

				return false;

			}

			valueCorrelation.forEach(msg -> {

				if (msg.getProjectName().equals("收缩压")) {

					msg.setProjectValue(strings.get(0));

				} else {
					msg.setProjectValue(strings.get(1));
				}
				msg.setUpdateUserId(projectRecord.getUpdateUserId());

				ProjectRecord projectRecord1 = new ProjectRecord();
				BeanUtil.copyProperties(msg, projectRecord1);
				this.updateById(projectRecord1);

			});

		} else if (type == 3) {
			//删除
			List<NursingProjectVo> valueCorrelation = this.getValueCorrelation(id);

			if (CollectionUtils.isEmpty(valueCorrelation)) {

				return false;

			}

			valueCorrelation.forEach(msg -> {

				ProjectRecord projectRecord1 = new ProjectRecord();
				BeanUtil.copyProperties(msg, projectRecord1);
				projectRecord1.setDelFlag(1);
				this.updateById(projectRecord1);

			});
		}

		return true;
	}


	/**
	 * 修改的为收缩压或舒张压
	 *
	 * @param projectRecord
	 * @return
	 */
	private boolean updateBP(ProjectRecord projectRecord) {

		Project sbp = projectService.selectByCode("SBP", ProjectTypeEnum.VITAL_SIGN_PROJECT.getCode());
		Project dbp = projectService.selectByCode("DBP", ProjectTypeEnum.VITAL_SIGN_PROJECT.getCode());
		if (sbp == null || dbp == null) {

			return false;

		}
		//判断该项目是否为SBP DBP
		if (!(sbp.getProjectId().equals(projectRecord.getProjectId()) || dbp.getProjectId().equals(projectRecord.getProjectId()))) {
			return false;
		}

		String sourceId = projectRecord.getSourceId();
		//查询出原数据血压
		List<ProjectRecord> list = this.list(Wrappers.<ProjectRecord>query().lambda()
				.eq(ProjectRecord::getDelFlag, 0)
				.eq(ProjectRecord::getProjectRecordId, sourceId)

		);
		//不存在原数据血压
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		ProjectRecord projectRecord1 = list.get(0);

		ProjectRecord byId = projectRecordService.getById(projectRecord.getId());

		String replace = projectRecord1.getProjectSpecificRecord().replace(byId.getProjectValue(), projectRecord.getProjectValue());

		projectRecord1.setProjectSpecificRecord(replace);

		this.verifyBp(projectRecord1, 2);
		return this.updateById(projectRecord1);

	}

	/**
	 * 获取折线图数据
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public HashMap<String, Object> selectLineChart(String patientId) {
		//查询所有的生命体征项目
		List<Project> projects = projectService.selectByType(ProjectTypeEnum.VITAL_SIGN_PROJECT.getCode());

		if (CollectionUtils.isEmpty(projects)) {

			return null;

		}

		HashMap<String, Object> result = new HashMap<>();
		Vector<Thread> threadVector = new Vector<>();
		//每个项目建立一个线程查询数据
		projects.forEach(ar -> {
			Thread thread = new Thread() {
				@Override
				public void run() {
					List<ProjectRecord> list = projectRecordService.list(Wrappers
							.<ProjectRecord>query().lambda()
							.eq(ProjectRecord::getProjectId, ar.getProjectId())
							.eq(ProjectRecord::getPatientId, patientId)
							.eq(ProjectRecord::getDelFlag, 0));
					//对结果 按创建时间排序w
					list.sort(Comparator.comparing(ProjectRecord::getCreateTime).reversed());
					//剔除字段为空 和为字符串null的数据
					List<ProjectRecord> collect = list.stream().filter(demo ->
							(StringUtils.isNotEmpty(demo.getProjectValue()) && !demo.getProjectValue().equals("null")))
							.collect(Collectors.toList());
					result.put(ar.getProjectCode(), collect);
					super.run();
				}
			};
			threadVector.add(thread);
			thread.start();

		});


		//等待所有线程结束
		threadVector.forEach(ar -> {
			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		return result;
	}

	/**
	 * 用于存副本表
	 *
	 * @param createTime
	 * @return
	 */
	@Override
	public List<ProjectRecord> getAllRecord(LocalDateTime createTime) {
		return baseMapper.getAllRecord(createTime);
	}


	/**
	 * 用于存副本表
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public List<ProjectRecord> getAllRecordByPatient(NursingBo nursingBo) {
		return baseMapper.getAllRecordByPatient(nursingBo);
	}


}
