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
package com.pig4cloud.pigx.ccxxicu.service.impl.task;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsDescribe;
import com.pig4cloud.pigx.ccxxicu.mapper.task.ChangeShiftsDescribeMapper;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsDescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交接班描述记录表   用户临时记录交接班的描述
 *
 * @author pigx code generator
 * @date 2019-08-23 13:42:51
 */
@Service
public class ChangeShiftsDescribeServiceImpl extends ServiceImpl<ChangeShiftsDescribeMapper, ChangeShiftsDescribe> implements ChangeShiftsDescribeService {


	@Autowired
	private ChangeShiftsDescribeMapper changeShiftsDescribeMapper;


	/**
	 * 交接班完成后删除表中的所有数据
	 * @return
	 */
	@Override
	public Integer deleteAll() {
		return changeShiftsDescribeMapper.deleteAll();
	}

	/**
	 * 任务id查询数据
	 * @param taskId
	 * @return
	 */
	@Override
	public ChangeShiftsDescribe getTaskId(String taskId) {
		return changeShiftsDescribeMapper.getTaskId(taskId);
	}

}

