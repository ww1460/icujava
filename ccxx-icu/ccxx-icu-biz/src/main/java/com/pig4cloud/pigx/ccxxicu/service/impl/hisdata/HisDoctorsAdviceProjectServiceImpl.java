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
package com.pig4cloud.pigx.ccxxicu.service.impl.hisdata;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisDoctorsAdviceEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisDoctorsAdviceProjectMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 医嘱项目表【一批医嘱下相对应的药品、项目数据】
 *
 * @author pigx code generator
 * @date 2019-08-30 10:58:12
 */
@Service
public class HisDoctorsAdviceProjectServiceImpl extends ServiceImpl<HisDoctorsAdviceProjectMapper, HisDoctorsAdviceProject> implements HisDoctorsAdviceProjectService {

	@Autowired
	private HisDoctorsAdviceProjectMapper hisDoctorsAdviceProjectMapper;

	@Autowired
	private HisDoctorsAdviceService hisDoctorsAdviceService;



	/***
	 * 多个数据新增
	 * @param hisDoctorsAdviceProjects
	 * @return
	 */
	@Override
	public Boolean addAll(List<HisDoctorsAdviceProject> hisDoctorsAdviceProjects) {

		hisDoctorsAdviceProjects.forEach(e->{
			e.setDoctorsAdviceProjectId(SnowFlake.getId()+"");//雪花id
		});

		return this.saveBatch(hisDoctorsAdviceProjects);
	}

	/**
	 * 通过his医嘱id查询当前医嘱的详细数据
	 * @param id
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceProject> hisDoctorsAdviceId(String id) {
		return hisDoctorsAdviceProjectMapper.hisDoctorsAdviceId(id);
	}

	/**
	 * 通过医嘱批号查询数据
	 * @param batchNumber
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceProject> selectBatchNumber(String batchNumber) {
		return hisDoctorsAdviceProjectMapper.selectBatchNumber(batchNumber);
	}

	/**
	 * 通过医嘱项目对任务进行内容填充
	 * @param list
	 * @return
	 */
	@Override
	public Tasks taskAll(List<HisDoctorsAdviceProject> list) {

			Tasks task = new Tasks();
			/* 任务内容 */
			String taskname="";
			/*任务描述*/
			String dos ="";
			for (int i =0;i<list.size();i++){
				String content = list.get(i).getContent();
				int j =  i+1;
				taskname = "  医嘱"+j+"："+content+taskname;
				String rematks =  list.get(i).getRemarks();
				if(rematks==null|| rematks.equals("")){
					rematks = "无";
				}
				dos = "  医嘱"+j+"描述："+rematks+dos;
			}
			task.setTaskDescribe(dos);
			task.setTaskName(taskname);
			return task;


	}

	/**
	 *  医嘱的内容【药物】生成一条任务
	 * @param hisDoctorsAdviceProject
	 * @return
	 */
	@Override
	public TaskSubTask addTask(HisDoctorsAdviceProject hisDoctorsAdviceProject) {

		TaskSubTask task = new TaskSubTask();
		task.setTaskSubTaskId(SnowFlake.getId()+"");//雪花
		task.setTaskType(TaskEnum.DOCTORS_ADVICE.getNumber());//医嘱
		task.setTaskTypeId(hisDoctorsAdviceProject.getDoctorsAdviceProjectId());//类型id
		task.setTaskName(hisDoctorsAdviceProject.getContent());//任务内容
		task.setTaskDescribe(hisDoctorsAdviceProject.getRemarks());//描述
		task.setCreateTime(LocalDateTime.now());//创建时间
		task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//执行状态
		task.setExecutionType(TaskEnum.IMPLEMENT.getNumber());//执行新形任务
		//添加剂量   【判断当前医嘱是否有剂量】
		if (hisDoctorsAdviceProject.getConsumption()!=null &&!"".equals(hisDoctorsAdviceProject.getConsumption())){
			/*  在剂量单位不为空的时候*/

			Integer intakeOutputValue =0; //剂量
			if(hisDoctorsAdviceProject.getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_L.getCode())){
				/* 当满足时，将单条医嘱的内容及  值  单位取出 */
				intakeOutputValue = intakeOutputValue+ Integer.valueOf(hisDoctorsAdviceProject.getConsumption()+1000);
			}if(hisDoctorsAdviceProject.getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_ML.getCode())){
				/* 当满足时，将单条医嘱的内容及  值  单位取出 */
				intakeOutputValue = intakeOutputValue+ Integer.valueOf(hisDoctorsAdviceProject.getConsumption());
			}
			task.setDosage(intakeOutputValue); //剂量

		}
		task.setTaskRelation(TaskEnum.TASK_SON.getNumber());//子任务、
		task.setProgressBar(0);//进度条

		return task;
	}

	/**
	 * 医嘱内容的雪花id查询完整医嘱数据
	 * @param id
	 * @return
	 */
	@Override
	public HisDoctorsAdviceVo doctorsAdviceProjectId(String id) {

		HisDoctorsAdviceProject hisDoctorsAdviceProject = hisDoctorsAdviceProjectMapper.doctorsAdviceProjectId(id);

		HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceService.hisDoctorsAdviceId(hisDoctorsAdviceProject.getHisDoctorsAdviceId());
		if (hisDoctorsAdviceVo!=null){
			return hisDoctorsAdviceVo;
		}else{
			return null;
		}
	}

	/**
	 *  通过his的医嘱id【icu的医嘱内容id，查询关联医嘱id】
	 * @param hisDoctorsAdviceProjectId
	 * @return
	 */
	@Override
	public HisDoctorsAdviceProject hisDoctorsAdviceProjectId(String hisDoctorsAdviceProjectId) {
		return hisDoctorsAdviceProjectMapper.hisDoctorsAdviceProjectId(hisDoctorsAdviceProjectId);
	}
}
