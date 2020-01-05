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

package com.pig4cloud.pigx.ccxxicu.service.device;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.device.UseRecordVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 设备使用记录表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:50:06
 */
public interface UseRecordService extends IService<UseRecord> {

	/**
	 * 分页查询数据
	 * @param page
	 * @param useRecord
	 * @return
	 */
	IPage selectPaging(Page page,UseRecord useRecord);


	/**
	 * 条件数据查询
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectAll(UseRecord useRecord);

	/**
	 *数据查询连接中的设备
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectInConnected(UseRecord useRecord);

	/**
	 * 断开当前患者数据
	 * @param patientId
	 * @return
	 */
	Boolean stopDevice(String patientId);

	/**
	 * 断开设备
	 * @param id
	 * @return
	 */
	R breakDevice(String id);

	/**
	 * 通过科室查询 某条管道 的今天的使用记录
	 * @param useRecord
	 * @return
	 */
	Integer deviceUse(UseRecord useRecord);

}
