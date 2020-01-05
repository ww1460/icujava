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
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估项目条件表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:45:44
 */
public interface AssessProjectMapper extends BaseMapper<AssessProject> {


	List<AssessProject> getType(@Param("assessTypeId") String assessTypeId);

	List<AssessProject> getCondition(@Param("assessProjectName") String assessProjectName);

}
