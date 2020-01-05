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

package com.pig4cloud.pigx.ccxxicu.mapper.task;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsDescribe;
import org.apache.ibatis.annotations.Param;

/**
 * 交接班描述记录表   用户临时记录交接班的描述
 *
 * @author pigx code generator
 * @date 2019-08-23 13:42:51
 */
public interface ChangeShiftsDescribeMapper extends BaseMapper<ChangeShiftsDescribe> {


	/* 删除表中所有数据源 */
	Integer deleteAll();

	/**
	 * 任务id查询数据源
	 * @param taskId
	 * @return
	 */
	ChangeShiftsDescribe getTaskId(@Param("taskId") String taskId);


}
