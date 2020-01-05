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

package com.pig4cloud.pigx.ccxxicu.service.nursing;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IntakeOutPutBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputShow;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeTableVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 出入量记录
 *
 * @author pigx code generator
 * @date 2019-08-22 16:27:47
 */
public interface IntakeOutputRecordService extends IService<IntakeOutputRecord> {

	/**
	 * 出入量添加
	 * @param intakeOutputRecord
	 * @return
	 */
	boolean add(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 出入量修改
	 * @param intakeOutputRecord
	 * @return
	 */
	boolean updateRecord(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 出入量删除
	 * @param intakeOutputRecord
	 * @return
	 */
	boolean delete(IntakeOutputRecord intakeOutputRecord);


	/**
	 * 查询某条记录前最近的平衡量  或者最新的出入量
	 * @param intakeOutputRecord
	 * @return
	 */
	Integer getEquilibriumAmount(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 查询某条数据之后的数据
	 * @param intakeOutputRecord
	 * @return
	 */
	List<IntakeOutputRecord> selectAfter (IntakeOutputRecord intakeOutputRecord);

	/**
	 * 查询某关联数据
	 * @param intakeOutputRecord
	 * @return
	 */
	IntakeOutputRecord selectCorrelationValue(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 出入量分页查询
	 * @param page
	 * @param intakeOutPutBo
	 * @return
	 */
	IPage<IntakeOutputVo> selectByPage(Page page,  IntakeOutPutBo intakeOutPutBo);


	/**
	 * 获取该患者的出入量数据
	 * @param patientId
	 * @return
	 */
	List<IntakeOutputShow> getDateShow(String patientId,String projectId);



	/**
	 * 查询某小时内的出入量总和
	 * @author yyj
	 * @date 2019/9/3
	 */
	Integer selectGetCount(@Param("patientId") String patientId, @Param("createTime") LocalDateTime createTime);

	/**
	 * 查询护理记录单一
	 * @param nursingBo
	 * @return
	 */
	IntakeTableVo getReport(NursingBo nursingBo);


}
