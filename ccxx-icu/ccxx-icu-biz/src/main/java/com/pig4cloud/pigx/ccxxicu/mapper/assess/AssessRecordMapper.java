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

package com.pig4cloud.pigx.ccxxicu.mapper.assess;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordPage;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.assess.AssessRecordShowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:58:53
 */
public interface AssessRecordMapper extends BaseMapper<AssessRecord> {

	/**
	 * 评估记录分页查询
	 * @param page
	 * @param assessRecordPage
	 * @return
	 */
	IPage<AssessRecordShowVo> selectByPage(@Param("page")Page page, @Param("query")AssessRecordPage assessRecordPage);

	/**
	 * 查询某患者的评估项目
	 * @param patientId
	 * @return
	 */
	List<Project> getPatientAssessProject(@Param("patientId") String patientId);
}
