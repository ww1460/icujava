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

package com.pig4cloud.pigx.ccxxicu.mapper.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.device.UseRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设备使用记录表
 *
 * @author pigx code generator
 * @date 2019-08-02 20:50:06
 */
public interface UseRecordMapper extends BaseMapper<UseRecord> {

	/**
	 * 分页条件查询设备
	 * @param page
	 * @param useRecord
	 * @param
	 * @return
	 */
	IPage<List<UseRecordVo>> selectPaging(Page page, @Param("useRecord") UseRecord  useRecord);

	/**
	 * 条件数据查询
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectAll(@Param("useRecord") UseRecord useRecord);

	/**
	 *数据查询连接中的设备
	 * @param useRecord
	 * @return
	 */
	List<UseRecordVo> selectInConnected(@Param("useRecord") UseRecord useRecord);

	/**
	 * 患者id查询关联的设备用于患者出科
	 * @param patientId
	 * @return
	 */
	List<UseRecord> stopDevice(@Param("patientId") String patientId);

	/**
	 *通过科室及科室的信息 查询某个设备当在当天是否有使用
	 * @param useRecord
	 * @return
	 */
	@Select("SELECT COUNT(id) from dev_use_record WHERE del_flag = 0 and dept_id = #{useRecord.deptId} and device_id = #{useRecord.deviceId} and end_time is null or DATE_FORMAT(end_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')")
	Integer deviceUse(@Param("useRecord")UseRecord useRecord);



}
