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

package com.pig4cloud.pigx.ccxxicu.mapper.nursing;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IntakeOutPutBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputShow;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeRemarksVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出入量记录
 *
 * @author pigx code generator
 * @date 2019-08-22 16:27:47
 */
public interface IntakeMapper extends BaseMapper<IntakeOutputRecord> {

	/**
	 * 获取平衡量
	 * @param nursingBo
	 * @return
	 */
	Integer getEquilibriumAmount(NursingBo nursingBo);

	/**
	 * 获取某患者某记录之后的数据
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	List<IntakeOutputRecord> selectAfter(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 获取关联数据
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	IntakeOutputRecord selectCorrelationValue(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 出入量分页查询
	 *
	 * @param page
	 * @param intakeOutPutBo
	 * @return
	 */
	IPage<IntakeOutputVo> selectByPage(Page page, @Param("query") IntakeOutPutBo intakeOutPutBo);

	/**
	 * 获取该患者的出入量数据
	 *
	 * @param nursingBo
	 * @return
	 */
	List<IntakeOutputShow> getDateShow(NursingBo nursingBo);


	List<IntakeOutputShow> selectEquilibriumAmount(NursingBo nursingBo);


	List<IntakeRemarksVo> getCounts(NursingBo nursingBo);
}
