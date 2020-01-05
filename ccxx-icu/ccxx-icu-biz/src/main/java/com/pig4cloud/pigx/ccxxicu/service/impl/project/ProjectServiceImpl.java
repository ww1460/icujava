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


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordValue;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.WarmJudge;
import com.pig4cloud.pigx.ccxxicu.common.utils.ChineseToPinYin;
import com.pig4cloud.pigx.ccxxicu.mapper.project.ProjectMapper;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessNursingStateService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.project.WarmJudgeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:09
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

	@Autowired
	private WarmJudgeService warmJudgeService;

	@Autowired
	private IllnessNursingStateService illnessNursingStateService;

	@Autowired
	private ProjectRecordValueService projectRecordValueService;

	@Autowired
	private ProjectCorrelationService projectCorrelationService;



	/**
	 * 新增项目
	 *
	 * @param project
	 * @return
	 */
	@Override
	public boolean add(Project project) {

		PigxUser user = SecurityUtils.getUser();
		project.setDeptId(user.getDeptId()+"");
		//验证code值
		String projectCode = this.verifyCode(project);
		project.setProjectName(project.getProjectName().toUpperCase());
		project.setProjectCode(projectCode);
		return this.save(project);

	}

	/**
	 * 验证项目的code值
	 * @param project
	 * @return
	 */
	private String verifyCode(Project project) {

		String projectCode = project.getProjectCode();

		boolean msg = true;
		//当code为空时 将name值的首字母转换为code
		if (StringUtils.isEmpty(projectCode)) {

			String s = ChineseToPinYin.HanZiToPinYin(project.getProjectName(), false);

			projectCode = s;

		}

		projectCode = projectCode.toUpperCase();

		//对code值验重 不重复直接结束   重复在后面追加一个随机的大写字母
		while (msg) {

			Project integer = baseMapper.selectByCode(projectCode, project.getProjectType(),project.getDeptId());

			if (integer==null) {

				break;

			} else if (integer.getProjectId().equals(project.getProjectId())) {
				break;
			} else {
				projectCode = projectCode  + (char) (int) (Math.random() * 26 + 65);
			}
		}

		return projectCode.toUpperCase();

	}



	/**
	 * 根据项目名查询
	 *
	 * @param projectName
	 * @return
	 */
	@Override
	public Project selectByName(String projectName) {

		PigxUser user = SecurityUtils.getUser();

		return baseMapper.selectByName(projectName.toUpperCase(),user.getDeptId()+"");
	}

	/**
	 * 根据项目编号查询
	 *
	 * @param projectCode
	 * @return
	 */
	@Override
	public Project selectByCode(String projectCode, Integer projectType) {
		PigxUser user = SecurityUtils.getUser();

		return baseMapper.selectByCode(projectCode.toUpperCase(), projectType,user.getDeptId()+"");
	}

	/**
	 * 根据项目类型
	 *
	 * @param projectType
	 * @return
	 */
	@Override
	public List<Project> selectByType(Integer projectType) {
		PigxUser user = SecurityUtils.getUser();


		return baseMapper.selectByType(projectType,user.getDeptId()+"");
	}

	/**
	 * 获取项目所有类型
	 *
	 * @return
	 */
	@Override
	public List<Integer> getProjectType() {
		PigxUser user = SecurityUtils.getUser();

		return baseMapper.getProjectType(user.getDeptId()+"");
	}

	/**
	 * 根据项目id  查询
	 *
	 * @param projectId
	 * @return
	 */
	@Override
	public Project selectByProjectId(String projectId) {

		PigxUser user = SecurityUtils.getUser();

		return baseMapper.selectByProjectId(projectId,user.getDeptId()+"");
	}

	/**
	 * 修改项目
	 *
	 * @param project
	 * @return
	 */
	@Override
	public boolean updateProject(Project project) {

		/**
		 * 修改项目  因项目表有许多标识  需进行判断
		 *
		 * 如果修改成不存在 则将对应的数据删除
		 */

		String projectCode = this.verifyCode(project);

		//首先判断预警标识
		if (1== project.getProjectWarmFlag()) {

			this.operationWarm(project.getProjectId());

		}
		//病情标识
		if (1== project.getIllnessNursingFlag()){

			this.operationIllness(project);

		}
		//固定值标识
		if (project.getProjectRecordValueFlag()==1) {

			this.operationValue(project.getProjectId());

		}
		project.setProjectCode(projectCode);
		return this.updateById(project);
	}

	/**
	 * 删除项目
	 *
	 * @param project
	 * @return
	 */
	@Override
	public boolean deleteProject(Project project) {

		/**
		 * 修改项目  因项目表有许多标识  需进行判断
		 */

		//首先判断预警标识
		if (0== project.getProjectWarmFlag()) {

			this.operationWarm(project.getProjectId());

		}
		//病情标识
		if (0== project.getIllnessNursingFlag()) {

			this.operationIllness(project);

		}
		//固定值标识
		if (0== project.getProjectRecordValueFlag()) {

			this.operationValue(project.getProjectId());

		}
		//删除项目展示的关联
		List<ProjectCorrelation> list = projectCorrelationService.list(Wrappers.<ProjectCorrelation>query().lambda()
				.eq(ProjectCorrelation::getDeptId, project.getDeptId())
				.eq(ProjectCorrelation::getProjectId, project.getProjectId())

		);

		if (CollectionUtils.isNotEmpty(list)) {

			projectCorrelationService.removeByIds(list);

		}

		return this.updateById(project);

	}


	/**
	 * 对该项目的预警的操作
	 *
	 * @param projectId 项目id
	 * @return
	 */
	private boolean operationWarm(String projectId) {

		PigxUser user = SecurityUtils.getUser();

		WarmJudge warmJudge = warmJudgeService.selectByProjectId(projectId);

		if (warmJudge == null) {

			return true;
		}
		//删除预警
		warmJudge.setDelFlag(1);
		warmJudge.setDelTime(LocalDateTime.now());
		warmJudge.setDelUserId(user.getId() + "");

		return warmJudgeService.updateById(warmJudge);
	}


	/**
	 * 对该项目 的病情护理操作
	 * 删除
	 * @param project 项目id
	 * @return
	 */
	private boolean operationIllness(Project project) {

		boolean b = illnessNursingStateService.delProjectByUpdate(project);

		return b;
	}

	/**
	 * 对该项目的固定值的操作
	 *
	 * @param projectId 项目id
	 * @return
	 */
	private boolean operationValue(String projectId) {

		PigxUser user = SecurityUtils.getUser();
		//查询出该项目的固定值
		List<ProjectRecordValue> list = projectRecordValueService.list(Wrappers.<ProjectRecordValue>query().lambda()
				.eq(ProjectRecordValue::getDelFlag, 0)
				.eq(ProjectRecordValue::getProjectId, projectId)
		);

		if (CollectionUtils.isEmpty(list)) {

			return true;

		}
		//删除处理
		list.forEach(ar->{

			ar.setDelFlag(1);
			ar.setDelTime(LocalDateTime.now());
			ar.setDelUserId(user.getId()+"");

		});

		return projectRecordValueService.updateBatchById(list);
	}

	/**
	 * 管理病情护理项目
	 * @param projects
	 * @return
	 */
	@Override
	public boolean illnessProject(List<Project> projects) {

		PigxUser user = SecurityUtils.getUser();
		//查询该科室下的病情护理项目
		List<Project> projects1 = baseMapper.selectList(Wrappers.<Project>query().lambda()
				.eq(Project::getDelFlag, 0)
				.eq(Project::getIllnessNursingFlag, 0)
				.eq(Project::getDeptId, user.getDeptId() + "")
		);
		//当原来的病情护理项目为空时  直接关联
		if (CollectionUtils.isEmpty(projects1)) {
			return this.updateBatchById(projects);
		}
		//存放修改的项目 是否为病情护理项目之间转换
		List<Project> projectList = new ArrayList<>();
		//找出之前是病情项目  现在不是的项目
		projects1.forEach(ar->{
			projects.forEach(msg->{
				if (ar.getProjectId().equals(msg.getProjectId())) {
					return;
				}
			});
			projectList.add(ar);
		});

		//取消病情护理项目
		projectList.forEach(ar->{
			ar.setIllnessNursingFlag(1);
			//删除该项目的病情护理的结构数据
			this.operationIllness(ar);
		});
		//取消病情护理项目
		this.updateBatchById(projectList);
		//添加病情护理项目
		return this.updateBatchById(projects);
	}
}
