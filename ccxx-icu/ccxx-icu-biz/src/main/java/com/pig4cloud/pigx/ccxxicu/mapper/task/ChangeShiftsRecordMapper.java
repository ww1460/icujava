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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.ChangeShiftsRecordVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.ChangeShiftsVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交接班记录
 *
 * @author pigx code generator
 * @date 2019-08-23 16:11:43
 */
public interface ChangeShiftsRecordMapper extends BaseMapper<ChangeShiftsRecord> {

	/**
	 * 交接班查询所有未完成的任务
	 * @param task
	 * @return
	 */
	List<ChangeShiftsVo> shiftsTask (@Param("task") TaskVo task);

	/**
	 * 全查数据
	 * @param changeShiftsRecord
	 * @return
	 */
	List<ChangeShiftsRecordVo> selectAll(@Param("changeShiftsRecord") ChangeShiftsRecord changeShiftsRecord);


	/**
	 * 雪花id查询数据
	 * @param changeShiftsRecordId
	 * @return
	 */
	ChangeShiftsRecordVo  getRecordId(@Param("changeShiftsRecordId") String changeShiftsRecordId);

}
