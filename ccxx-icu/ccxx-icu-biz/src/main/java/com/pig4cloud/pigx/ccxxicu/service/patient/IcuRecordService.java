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

package com.pig4cloud.pigx.ccxxicu.service.patient;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.IcuRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.IcuRecordVo;

/**
 * icu 记录表，记录每天在科、新入科、留置导尿管、动静脉插管、呼吸机使用人数
 *
 * @author pigx code generator
 * @date 2019-10-03 16:48:03
 */
public interface IcuRecordService extends IService<IcuRecord> {
	/**
	 * 分页查询
	 * @param page
	 * @param icuRecord
	 * @return
	 */
	IPage page(Page page, IcuRecordVo icuRecord);

	/**
	 * 新增接口
	 * @return
	 */
	Boolean add(IcuRecord icuRecord);


}
