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

package com.pig4cloud.pigx.ccxxicu.mapper.hisdata;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DeptCorrelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * his【科室】与我们科室关联表
 *
 * @author pigx code generator
 * @date 2019-10-08 20:12:09
 */
public interface DeptCorrelationMapper extends BaseMapper<DeptCorrelation> {

	/**
	 * 利用科室id 删除
	 * @param correlation
	 * @return
	 */
	@Select("update his_dept_correlation set del_flag = #{correlation.delFlag},del_user_id = #{correlation.delTime},del_time = #{correlation.delUserId} WHERE dept_id = #{correlation.id}")
	Boolean deptId(@Param("correlation")DeptCorrelation correlation);

	/**
	 * 通过科室id查询关联his的科室名称
	 * @param id
	 * @return
	 */
	@Select("select id,his_dept_id,his_dept_name,dept_id,dept_name FROM his_dept_correlation WHERE del_flag = 0 and dept_id = #{id}")
	DeptCorrelation deptIdSelect(@Param("id")String id);

	/**
	 * 利用his科室数据查询数据
	 * @param id
	 * @return
	 */
	@Select("select id,his_dept_id,his_dept_name,dept_id,dept_name FROM his_dept_correlation WHERE del_flag = 0 and his_dept_id = #{id}")
	DeptCorrelation hisDeptId(@Param("id")String id);

	/**
	 * 全查科室数据
	 * @return
	 */
	@Select("select id,his_dept_id,his_dept_name,dept_id,dept_name FROM his_dept_correlation")
	List<DeptCorrelation> listDept();

}




