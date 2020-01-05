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

package com.pig4cloud.pigx.ccxxicu.service.nursing;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.*;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingRecordShow;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingRecordVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingReportThreeVo;
import com.pig4cloud.pigx.common.core.util.R;

import java.util.List;

/**
 * 护理记录
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:13
 */
public interface NursingRecordService extends IService<NursingRecord> {

	/**
	 * 护理模板新增护理记录
	 * @param templateRecordBo
	 * @return
	 */
	boolean templateAdd(TemplateRecordBo templateRecordBo);

	/**
	 * 分页查询护理记录
	 * @param page
	 * @param nursingRecordBo
	 * @return
	 */
	IPage<NursingRecordVo> selectByPage (Page page, NursingRecordBo nursingRecordBo);

	/**
	 * 修改护理记录前的查看
	 * @param nursingRecord
	 * @return
	 */
	R getNursingRecord(NursingRecord nursingRecord);

	/**
	 * 修改护理记录
	 * @return
	 */
	boolean updateNursingRecord(NursingRecordShow nursingRecordShow);

	/**
	 * 删除护理记录
	 * @param nursingRecord
	 * @return
	 */
	boolean deleteNursingRecord(NursingRecord nursingRecord);

	/**
	 * 悬浮窗录入
	 * @param windowsEnterBo
	 * @return
	 */
	boolean windowsAdd(WindowsEnterBo windowsEnterBo);

	/**
	 * 病情护理录入
	 * @date 2019/9/5
	 */
	boolean illness(List<IllnessRecordBo> illnessRecordBoList);


	/**
	 * 护理记录单三
	 * @param nursingBo
	 * @return
	 */
	List<NursingReportThreeVo> getReport(NursingBo nursingBo);


	boolean taskAdd(TaskSubTask taskSubTask);



}
