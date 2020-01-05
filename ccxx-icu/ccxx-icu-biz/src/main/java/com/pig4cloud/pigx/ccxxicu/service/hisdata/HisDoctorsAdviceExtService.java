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
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;

import java.util.List;

/**
 * 医嘱扩展表
 *
 * @author pigx code generator
 * @date 2019-10-14 19:33:39
 */
public interface HisDoctorsAdviceExtService extends IService<HisDoctorsAdviceExt> {



	/*
	通过定时任务查询数据，查询在当前时间之前的医嘱 是否医嘱产生任务，如果有则不做处理，反之生成一条任务。
	查询医嘱一定时间内的医嘱数据并将其生成任务 */
	Boolean doctorsAdviceExtAddTask();



	/* 通过查询his传来的医嘱信息新增任务【用于医嘱的新增】*/
	Boolean hisDoctorsAdviceExtAddTask(HisDoctorsAdviceExt hisDoctorsAdviceExt);

	/**
	 * 查询在当前时间需要执行的医嘱
	 * @return
	 */
	List<HisDoctorsAdviceExt> selectTime();

	/**
	 * 通过雪花id查询数据
	 * @param id
	 * @return
	 */
	HisDoctorsAdviceExt doctorsAdviceExt(String id);

	/**
	 * 从his端新增数据
	 * @param hisDoctorsAdviceExt
	 * @return
	 */
	Boolean hisAdd(HisDoctorsAdviceExt hisDoctorsAdviceExt);

	/**
	 * 通过his的执行医嘱id查询
	 * @param id
	 * @return
	 */
	HisDoctorsAdviceExt hisFZYExecInfoId(String id);

	/**
	 * 通过医嘱的执行记录雪花id，查询相对的医嘱数据
	 * @param id
	 * @return
	 */
	HisDoctorsAdviceVo doctorsAdvice(String id);



	/**
	 * 通过关联医嘱id  查询相关的医嘱数据
	 * @param id
	 * @return
	 */
	List<HisDoctorsAdviceExt> hisDoctorsAdviceId(String id);

}
