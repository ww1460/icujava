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

package com.pig4cloud.pigx.ccxxicu.service.project;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ProjectRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingProjectVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectRecordVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 项目数据记录表
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:06
 */
public interface ProjectRecordService extends IService<ProjectRecord> {


	/**
	 * 新增多个项目记录
	 * @param entityList
	 * @return
	 */
	boolean addList(Collection<ProjectRecord> entityList);


	/**
	 * 新增一条项目记录
	 * @param projectRecord
	 * @return
	 */
	boolean add(ProjectRecord projectRecord);


	/**
	 * 这里是根据多个项目id  和患者  获取最新的一条数据
	 * @param projectRecords
	 * @return
	 */
	List<ProjectRecord> selectNewRecord(List<ProjectRecord> projectRecords);

	/**
	 * 修改数据
	 * @param projectRecord
	 * @return
	 */
	boolean updateRecord(ProjectRecord projectRecord);

	/**
	 * 删除数据
	 * @param projectRecord
	 * @return
	 */
	boolean delete(ProjectRecord projectRecord);
	/**
	 * 查询某个患者的某个项目的最新数据
	 * @param patient
	 * @param projectId
	 * @return
	 */
	ProjectRecord getOneRecord(String patient,String projectId);

	/**
	 * 查询某关联数据
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
	IPage<ProjectRecordVo> selectByPage(Page page, @Param("query") ProjectRecordBo projectRecordBo);

	/**
	 * 获取折线图数据
	 * @param patientId
	 * @return
	 */
	HashMap<String,Object> selectLineChart(String patientId);

	/**
	 * 用于存副本表
	 * @param createTime
	 * @return
	 */
	List<ProjectRecord> getAllRecord(LocalDateTime createTime);


	/**
	 * 用于存副本表
	 * @param nursingBo
	 * @return
	 */
	List<ProjectRecord> getAllRecordByPatient(NursingBo nursingBo);

}
