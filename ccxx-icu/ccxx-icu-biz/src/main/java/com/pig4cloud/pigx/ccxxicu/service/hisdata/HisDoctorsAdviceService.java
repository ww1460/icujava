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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.HisDoctorsAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TasksVo;

import java.util.List;
import java.util.Map;

/**
 * 医嘱表
 *
 * @author pigx code generator
 * @date 2019-08-30 11:23:01
 */
public interface HisDoctorsAdviceService extends IService<HisDoctorsAdvice> {


	/**
	 * 通过his医嘱id查询当前医嘱的详细数据
	 */
	HisDoctorsAdviceVo hisDoctorsAdviceId(String id);


	/***
	 * 接收his传来的数据源
	 * @param doctorsAdviceBo
	 * @return
	 */
	Boolean hisDoctorsAdvice(HisDoctorsAdviceBo doctorsAdviceBo);

	/**
	 * 全查医嘱数据
	 * @param hisDoctorsAdvice
	 * @return
	 */
	List<HisDoctorsAdviceVo> selectAll(HisDoctorsAdvice hisDoctorsAdvice);

	/**
	 * 长期任务查询用于定时
	 * @return
	 */
	List<HisDoctorsAdviceVo> longTermTask();

	/**
	 * 通过医嘱对任务的新增
	 * @param hisDoctorsAdviceVo
	 * @return
	 */
	TasksVo taskAdd(HisDoctorsAdviceVo hisDoctorsAdviceVo);

	/**
	 * 通过执行医嘱的内容对任务的新增
	 * @param hisDoctorsAdviceExt
	 * @return
	 */
	TasksVo HisDoctorsAdviceExtAddTask(HisDoctorsAdviceExt hisDoctorsAdviceExt);

	/**
	 * 通过批号查询医嘱
	 * @param batchNumber
	 * @return
	 */
	HisDoctorsAdvice getBatchNumber (String batchNumber);

	/**
	 * 通过患者id 查询当前患者没有生成任务的医嘱
	 * @return
	 */
	List<HisDoctorsAdvice> notGenerate(String hisPatientId);

	/**
	 * 通过医嘱id查询当条医嘱的所有数据
	 * @param id
	 * @return
	 */
	Map<String,Object> getByIdAll(Integer id);

	/**
	 * 通过医嘱雪花id查询数据
	 * @param doctorsAdviceId
	 * @return
	 */
	HisDoctorsAdviceVo getDoctorsAdviceId(String doctorsAdviceId);

	/**
	 * //通过患者id 停止当前患者的所有医嘱
	 */
	Boolean stopDoctorsAdvice(String patientId);

	/**
	 * 通过his患者id 查询当前患者没有完成的医嘱
	 * @param hisPaientId
	 * @return
	 */
	List<String> stopDoctorsAdviceHisPatientId(String hisPaientId);

	/**
	 * 通过患者id 查询当前患者有没有医嘱
	 * @param hisPatientId
	 * @return
	 */
	List<HisDoctorsAdvice> hisPatientId(String hisPatientId);

	/**
	 * 查询当前登录护士相关的医嘱内容
	 * @param hisDoctorsAdvice
	 * @return
	 */
	IPage LoginNurseDoctorsAdvice(Page page, HisDoctorsAdviceVo hisDoctorsAdvice);

	/**
	 * 通过his医嘱修改当前医嘱状态数据
	 * @param hisDoctorsAdvice
	 * @return
	 */
	Boolean updateHisDoctorsAdviceId(HisDoctorsAdvice hisDoctorsAdvice);
}
