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

package com.pig4cloud.pigx.ccxxicu.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ProjectRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingProjectVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectRecordVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目数据记录表
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:06
 */
public interface ProjectRecordMapper extends BaseMapper<ProjectRecord> {
	/**
	 * 查询某患者某个项目的最新的一条数据
	 * @param patientId
	 * @param projectId
	 * @return
	 */
	ProjectRecord getOneRecord(@Param("patientId") String patientId,@Param("projectId") String projectId);

	/**
	 * 查询关联数据
	 * @param sourceId
	 * @return
	 */
	List<NursingProjectVo> getValueCorrelation(String sourceId);

	/**
	 * 分页查询
	 * @param page
	 * @param projectRecordBo
	 * @return
	 */
	IPage<ProjectRecordVo> selectByPage(Page page, @Param("query")ProjectRecordBo projectRecordBo);

/*	@MapKey("projectCode")
	HashMap<String, List<ProjectRecordVo>>  selectLineChart(String patientId);*/

	List<ProjectRecord> getAllRecord(LocalDateTime createTime);


	/**
	 * 用于存副本表
	 * @param nursingBo
	 * @return
	 */
	List<ProjectRecord> getAllRecordByPatient(NursingBo nursingBo);
}
