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


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordDuplicate;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.NursingReportVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 项目数据记录表复本（用于记录单的生成，每个患者的每个项目每小时只会产生一条数据）
 *
 * @author pigx code generator
 * @date 2019-09-11 14:35:16
 */
public interface ProjectRecordDuplicateService extends IService<ProjectRecordDuplicate> {

	/**
	 * 生成记录
	 * @return
	 */
	boolean saveRecord();



	/**
	 * 查询记录单一数据
	 * @param nursingBo
	 * @return
	 */
	R getNursingReport(NursingBo nursingBo);


	/**
	 * 查询护理报表数据
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportVo> getRecord(NursingBo nursingBo);

	/**
	 * 查询生命体征数据
	 * @param nursingBo1
	 * @return
	 */
	R getVital(NursingBo nursingBo1);


	/**
	 * 查询某记录单的项目
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportVo> selectReportProject(NursingBo nursingBo);


	boolean add(ProjectRecordDuplicate projectRecordDuplicate);

	/**
	 * 修改数据
	 * @param projectRecordDuplicate
	 * @return
	 */
	boolean updateRecord(ProjectRecordDuplicate projectRecordDuplicate);

}
