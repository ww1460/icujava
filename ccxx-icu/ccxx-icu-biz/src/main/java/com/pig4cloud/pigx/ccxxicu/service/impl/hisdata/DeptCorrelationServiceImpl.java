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
package com.pig4cloud.pigx.ccxxicu.service.impl.hisdata;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DeptCorrelation;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.DeptCorrelationMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DeptCorrelationService;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * his【科室】与我们科室关联表
 *
 * @author pigx code generator
 * @date 2019-10-08 20:12:09
 */
@Service
public class DeptCorrelationServiceImpl extends ServiceImpl<DeptCorrelationMapper, DeptCorrelation> implements DeptCorrelationService {

	@Autowired
	private DeptCorrelationMapper deptCorrelationMapper;

	/**
	 * 利用科室id 删除
	 * @param deptCorrelation
	 * @return
	 */
	@Override
	public Boolean deptId(DeptCorrelation deptCorrelation) {

		deptCorrelation.setDelFlag(1);
		deptCorrelation.setDelUserId(SecurityUtils.getUser().getId()+"");
		deptCorrelation.setDelTime(LocalDateTime.now());
		return deptCorrelationMapper.deptId(deptCorrelation);
	}

	/**
	 * 通过科室id查询关联his的科室名称
	 * @param id
	 * @return
	 */
	@Override
	public DeptCorrelation deptIdSelect(String id) {
		return deptCorrelationMapper.deptIdSelect(id);
	}

	/**
	 * 模拟http接口请求数据
	 * @param url   路径
	 * @param object  参数
	 * @return
	 */
	@Override
	public String http(String url, Object object) {
		/* 将对象变为json 格式 */
		JSONObject jsonObject = JSONUtil.parseObj(object);
		/* 将json 格式变为字符串*/
		String json = jsonObject.toString();

		/**
		 * 捕获一下异常数据
		 */
		try {
			return HttpUtil.post(url, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		}

	/**
	 * 利用his科室数据查询数据
	 * @param id
	 * @return
	 */
	@Override
	public DeptCorrelation hisDeptId(String id) {
		return deptCorrelationMapper.hisDeptId(id);
	}

	/**
	 * 全查科室数据
	 * @return
	 */
	@Override
	public List<DeptCorrelation> listDept() {
		return deptCorrelationMapper.listDept();
	}
}
