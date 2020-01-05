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

package com.pig4cloud.pigx.ccxxicu.service.illnessNursing;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.illnessNursing.IllnessProjectVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectVo;

import java.util.List;

/**
 * 病情护理项目
 *
 * @author pigx code generator
 * @date 2019-10-16 16:36:40
 */
public interface IllnessProjectService extends IService<IllnessProject> {

	/**
	 * 通过项目类型查询
	 * @param illnessNursingTypeFlag
	 * @return
	 */
	List<IllnessProjectVo> getByType(Integer illnessNursingTypeFlag );

	/**
	 * 修改代删除
	 * @param illnessProject
	 * @return
	 */
	boolean delByUpdate(IllnessProject illnessProject);


	List<ProjectVo> getProject(Integer illnessNursingTypeFlag);

}
