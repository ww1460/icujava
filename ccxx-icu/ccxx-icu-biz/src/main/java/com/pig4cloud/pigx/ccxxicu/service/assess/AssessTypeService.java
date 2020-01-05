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

package com.pig4cloud.pigx.ccxxicu.service.assess;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessShowBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessType;
import com.pig4cloud.pigx.ccxxicu.api.vo.assess.AssessProjectVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 评估类型表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:45:38
 */
public interface AssessTypeService extends IService<AssessType> {


	/**
	 * 删除该类型  及该类型下的项目 及其条件
	 *
	 * @param assessType
	 * @return
	 */
	boolean updateAll(AssessType assessType);

	/**
	 * 查询某评估的结构数据
	 * @param projectId
	 * @param patientId
	 * @return
	 */
	List<AssessProjectVo> getAssessData(String projectId, String patientId);
	/**
	 * 根据患者回填数据
	 * @param assessShowBo
	 * @return
	 */
	R getAssess(AssessShowBo assessShowBo);


	R selectAssess(AssessRecord assessRecord);

}
