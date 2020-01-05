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

package com.pig4cloud.pigx.ccxxicu.service.task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.EarlyWarning;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.EarlyWarningVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务预警表
 *
 * @author pigx code generator
 * @date 2019-08-16 14:57:57
 */
public interface EarlyWarningService extends IService<EarlyWarning> {

	/**
	 * 	定时查询是否有重复任务的数据
	 * @param taskId
	 * @return
	 */
	EarlyWarning timingSelect(@Param("taskId")String taskId);

	/**
	 * 定时查询全部数据
	 * @return
	 */
	List<EarlyWarning> timingAll();

	/**
	 * 分页查询数据
	 * @param page
	 * @param earlyWarning
	 * @return
	 */
	IPage selectPaging(Page page, EarlyWarning earlyWarning);

	List<EarlyWarning> getEarlyTask(String patientId);


	void setToWatch(EarlyWarning earlyWarning);


	List<EarlyWarningVo> getTask(String nurseId);

}
