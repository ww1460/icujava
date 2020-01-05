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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import com.pig4cloud.pigx.ccxxicu.mapper.task.ChangeShiftsTaskRecordMapper;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsTaskRecordService;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 交接班任务记录表
 *
 * @author pigx code generator
 * @date 2019-08-23 16:16:18
 */
@Service
public class ChangeShiftsTaskRecordServiceImpl extends ServiceImpl<ChangeShiftsTaskRecordMapper, ChangeShiftsTaskRecord> implements ChangeShiftsTaskRecordService {

	@Autowired
	private ChangeShiftsTaskRecordMapper changeShiftsTaskRecordMapper;


	/**
	 * 数据批量新增
	 * @param list
	 * @return
	 */
	@Override
	public boolean insetList(List<ChangeShiftsTaskRecord> list) {
		list.forEach(e->{
			e.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
		});
		return this.saveBatch(list);
	}

	/**
	 * 数据全查（条件查询）
	 * @param changeShiftsTaskRecord
	 * @return
	 */
	@Override
	public List<ChangeShiftsTaskRecord> selectAll(ChangeShiftsTaskRecord changeShiftsTaskRecord) {
		return changeShiftsTaskRecordMapper.selectAll(changeShiftsTaskRecord);
	}
}
