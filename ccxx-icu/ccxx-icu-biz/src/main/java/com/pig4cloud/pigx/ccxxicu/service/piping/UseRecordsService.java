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

package com.pig4cloud.pigx.ccxxicu.service.piping;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessShowBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipUseTime;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.UseRecordVo;

import java.util.List;

/**
 * 管道使用记录
 *
 * @author pigx code generator
 * @date 2019-08-08 16:23:23
 */
public interface UseRecordsService extends IService<UseRecord> {


	/**
	 * 查想连接中的管道
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectInConnected (UseRecord useRecord);

	/**
	 * 分页查询数据
	 * @param page
	 * @param useRecord
	 * @return
	 */
	IPage selectPaging(Page page, UseRecord useRecord);

	/**
	 * 全查数据
	 * @param useRecords
	 * @return
	 */
	List<UseRecordVo> selectAll(UseRecordVo useRecords);


	/**
	 * 条件数据查询 查询患者以使用的管道信息
	 * @param useRecords
	 * @return
	 */
	List<UseRecordVo> selectUsedAll(UseRecordVo useRecords);


	/**
	 * 查询管路的最后一次评估结果展示在页面
	 * @param assessShowBo
	 * @return
	 */
	String assessResult (AssessShowBo assessShowBo,String useRecordsId);

	/**
	 * 通过患者id查询当前患者连接的管道，用于患者出科
	 * @param pagientId
	 * @return
	 */
	Boolean stopPiping(String pagientId);


	/**
	 * 通过科室查询 某条管道 的今天的使用记录
	 * @param useRecord
	 * @return
	 */
	Integer pipingUse(UseRecord useRecord);

	/**
	 * 查询某患者的已使用的管路开始使用的时间
	 * @param patientId
	 * @return
	 */
	List<PipUseTime> ReportTwo(String patientId);

}
