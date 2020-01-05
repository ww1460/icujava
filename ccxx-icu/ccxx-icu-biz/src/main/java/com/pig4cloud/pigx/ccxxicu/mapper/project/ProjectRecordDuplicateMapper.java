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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordDuplicate;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.NursingReportVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目数据记录表复本（用于记录单的生成，每个患者的每个项目每小时只会产生一条数据）
 *
 * @author pigx code generator
 * @date 2019-09-11 14:35:16
 */
public interface ProjectRecordDuplicateMapper extends BaseMapper<ProjectRecordDuplicate> {

	/**
	 * 查询护理报表数据
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportVo> getRecord(NursingBo nursingBo);

	/**
	 * 查询生命体征数据
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportVo> getVital(NursingBo nursingBo);
	/**
	 * 查询某记录单的项目
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportVo> selectReportProject(NursingBo nursingBo);

	/**
	 * 查询生命体征的项目
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportVo> selectVitalProject(NursingBo nursingBo);

	/**
	 * 查询某小时的数据
	 * @param startTime
	 * @return
	 */
	List<ProjectRecordDuplicate> getHistoryRecord(LocalDateTime startTime);

	/**
	 * 查询当前小时内的患者数据
	 * @param nursingBo
	 * @return
	 */
	List<ProjectRecordDuplicate> getRecordByPatientId(NursingBo nursingBo);

	/**
	 * 获取创建人
	 * @param nursingBo
	 * @return
	 */
	List<Nurse> getCreateName(NursingBo nursingBo);

}
