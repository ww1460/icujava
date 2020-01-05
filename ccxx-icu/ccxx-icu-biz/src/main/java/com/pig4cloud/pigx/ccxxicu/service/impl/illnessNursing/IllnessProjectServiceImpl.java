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
package com.pig4cloud.pigx.ccxxicu.service.impl.illnessNursing;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessProject;
import com.pig4cloud.pigx.ccxxicu.api.vo.illnessNursing.IllnessProjectStateVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.illnessNursing.IllnessProjectVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectVo;
import com.pig4cloud.pigx.ccxxicu.mapper.illnessNursing.IllnessProjectMapper;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessNursingStateService;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessProjectService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 病情护理项目
 *
 * @author pigx code generator
 * @date 2019-10-16 16:36:40
 */
@Service
public class IllnessProjectServiceImpl extends ServiceImpl<IllnessProjectMapper, IllnessProject> implements IllnessProjectService {

	@Autowired
	private IllnessNursingStateService illnessNursingStateService;


	/**
	 * 通过项目类型查询
	 * @param illnessNursingTypeFlag
	 * @return
	 */
	@Override
	public List<IllnessProjectVo> getByType(Integer illnessNursingTypeFlag) {
		return baseMapper.getByType(illnessNursingTypeFlag,new DataScope());
	}

	/**
	 * 修改代删除
	 * @param illnessProject
	 * @return
	 */
	@Override
	public boolean delByUpdate(IllnessProject illnessProject) {

		//查询该项目的状态
		R projectState = illnessNursingStateService.getProjectState(illnessProject.getIllnessProjectId());

		List<IllnessProjectStateVo> data = (List<IllnessProjectStateVo>)projectState.getData();

		if (CollectionUtils.isEmpty(data)) {

			return this.updateById(illnessProject);

		}

		data.forEach(ar->{

			illnessNursingStateService.delByUpdate(ar.getId());
		});

		return this.updateById(illnessProject);
	}

	/**
	 * 获取该类型下的项目
	 * @param illnessNursingTypeFlag
	 * @return
	 */
	@Override
	public List<ProjectVo> getProject(Integer illnessNursingTypeFlag) {
		return baseMapper.getProject(illnessNursingTypeFlag,new DataScope());
	}
}
