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

package com.pig4cloud.pigx.ccxxicu.service.hisdata;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DeptCorrelation;

import java.util.List;

/**
 * his【科室】与我们科室关联表
 *
 * @author pigx code generator
 * @date 2019-10-08 20:12:09
 */
public interface DeptCorrelationService extends IService<DeptCorrelation> {

	/**
	 * 利用科室id 删除
	 * @param deptCorrelation
	 * @return
	 */
	Boolean deptId(DeptCorrelation deptCorrelation);

	/**
	 * 通过科室id查询关联his的科室名称
	 * @param id
	 * @return
	 */
	DeptCorrelation deptIdSelect(String id);

	/**
	 * 模拟http接口请求数据
	 * @param url   路径
	 * @param Object  参数
	 * @return
	 */
	String http(String url, Object Object);

	/**
	 * 利用his科室数据查询数据
	 * @param id
	 * @return
	 */
	DeptCorrelation hisDeptId(String id);

	/**
	 * 全查科室数据
	 * @return
	 */
	List<DeptCorrelation> listDept();
}
