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

package com.pig4cloud.pigx.ccxxicu.mapper.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Parameters;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 设备参数表
 *
 * @author pigx code generator
 * @date 2019-08-05 09:40:44
 */
public interface ParametersMapper extends BaseMapper<Parameters> {


	@Select("<script>" +
			"select parameter_code,project_id,dept_id FROM dev_parameter WHERE parameter_code in" +
			"<foreach item='item' index='index' collection='codes' open='(' separator=',' close=')'>" +
			"#{item}" +
			"</foreach>" +
			"</script>")
	List<Parameters> selectByCode(@Param("codes") Set<String> codes, String deptId);

}
