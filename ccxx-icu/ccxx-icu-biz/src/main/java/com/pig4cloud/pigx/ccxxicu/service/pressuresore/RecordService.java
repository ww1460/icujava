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

package com.pig4cloud.pigx.ccxxicu.service.pressuresore;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.Record;
import com.pig4cloud.pigx.ccxxicu.api.vo.pressuresore.RecorderVo;

/**
 * 压疮记录表 
 *
 * @author pigx code generator
 * @date 2019-08-30 16:19:42
 */
public interface RecordService extends IService<Record> {
	/**
	 * 分页查询数据
	 * @param page
	 * @param recorder
	 * @return
	 */
	IPage selectPaging(Page page, Record recorder);

	/**
	 * id查询全部数据展示
	 * @param id
	 * @return
	 */
	RecorderVo getByAll(Integer id);

	/**
	 * 查询某患者的最新一条数据
	 * @param patientId
	 * @return
	 */
	RecorderVo	getOneRecord(String patientId);
}
