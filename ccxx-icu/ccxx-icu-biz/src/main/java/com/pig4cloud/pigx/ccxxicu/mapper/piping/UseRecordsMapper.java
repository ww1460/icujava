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

package com.pig4cloud.pigx.ccxxicu.mapper.piping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipUseTime;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.UseRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 管道使用记录
 *
 * @author pigx code generator
 * @date 2019-08-08 16:23:23
 */
public interface UseRecordsMapper extends BaseMapper<UseRecord> {


	/**
	 * 查想连接中的设备
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectInConnected (@Param("useRecord") UseRecord useRecord);

	/**
	 * 分页查询信息
	 * @param useRecord
	 * @return
	 */
	IPage<List<UseRecordVo>> selectPaging(Page page, @Param("useRecord") UseRecord useRecord);

	/**
	 * 全查管道信息
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo>selectAll(@Param("useRecord") UseRecordVo useRecord);

	/**
	 * 条件数据查询 查询患者以使用的管道信息
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectUsedAll(@Param("useRecord") UseRecordVo useRecord);

	/**
	 * 通过患者查询当前患者连接的管路信息  用于设备连接
	 * @param patientId
	 * @return
	 */
	List<UseRecord> stopPiping(@Param("patientId")String patientId);

	//通过科室查询 某条管道 的今天的使用记录
	@Select("SELECT COUNT(id)FROM pip_use_record WHERE del_flag and dept_id = #{useRecord.deptId} and piping_id =#{useRecord.pipingId} and end_time !=null or DATE_FORMAT(end_time,'%Y-%m-%d') =  DATE_FORMAT(NOW(),'%Y-%m-%d')")
	Integer pipingUse(@Param("useRecord") UseRecord useRecord);

	/**
	 * 查询某患者的已使用的管路开始使用的时间
	 * @param patientId
	 * @return
	 */
	List<PipUseTime> ReportTwo(String patientId);


}
