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

package com.pig4cloud.pigx.ccxxicu.service.hisdata;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;

import java.util.List;

/**
 * 医嘱项目表【一批医嘱下相对应的药品、项目数据】
 *
 * @author pigx code generator
 * @date 2019-08-30 10:58:12
 */
public interface HisDoctorsAdviceProjectService extends IService<HisDoctorsAdviceProject> {

	/***
	 * 多条医嘱内容新增新增
	 * @param hisDoctorsAdviceProjects
	 * @return
	 */
	Boolean addAll(List<HisDoctorsAdviceProject> hisDoctorsAdviceProjects);


	/**
	 * 通过his医嘱id查询当前医嘱的详细数据
	 */
	List<HisDoctorsAdviceProject> hisDoctorsAdviceId(String id);


	/**
	 * 通过医嘱批号查询数据
	 * @param batchNumber
	 * @return
	 */
	List<HisDoctorsAdviceProject> selectBatchNumber(String  batchNumber);

	/**
	 * 通过医嘱项目对任务进行内容填充
	 * @param list
	 * @return
	 */
	Tasks taskAll(List<HisDoctorsAdviceProject> list);

	/**
	 *	// 医嘱的内容【药物】生成一条任务
	 * 	 * @return
	 */
	TaskSubTask addTask(HisDoctorsAdviceProject hisDoctorsAdviceProject);

	/**
	 * 医嘱内容的雪花id查询完整医嘱数据
	 * @param id
	 * @return
	 */
	HisDoctorsAdviceVo doctorsAdviceProjectId(String id);

	/**
	 * 通过his的医嘱id【icu的医嘱内容id，查询关联医嘱id】
	 * @param
	 * @return
	 */
	HisDoctorsAdviceProject hisDoctorsAdviceProjectId(String  hisDoctorsAdviceProjectId);

}
