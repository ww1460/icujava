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

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TasksVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DoctorsAdviceExtEnum;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisDoctorsAdviceExtMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceExtService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.task.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医嘱扩展表
 *
 * @author pigx code generator
 * @date 2019-10-14 19:33:39
 */
@Service
public class HisDoctorsAdviceExtServiceImpl extends ServiceImpl<HisDoctorsAdviceExtMapper, HisDoctorsAdviceExt> implements HisDoctorsAdviceExtService {


	@Autowired
	private HisDoctorsAdviceExtMapper hisDoctorsAdviceExtMapper;

	/**
	 * 主任务
	 */
	@Autowired
	private TasksService tasksService;
	/**
	 * 医嘱
	 */
	@Autowired
	private HisDoctorsAdviceService hisDoctorsAdviceService;
	/**
	 * 医嘱内容
	 */
	@Autowired
	private HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	/*
	通过定时任务查询数据，查询在当前时间之前的医嘱 是否医嘱产生任务，
	是否可通过his的字段借用，将创建完成任务的医嘱修改为执行中
	如果有则不做处理，反之生成一条任务。
	查询医嘱一定时间内的医嘱数据并将其生成任务
	 * @return
	 */
	@Override
	public Boolean doctorsAdviceExtAddTask() {
		/*  查询未执行的医嘱扩展数据*/
		List<HisDoctorsAdviceExt> list = this.selectTime();
		if (CollectionUtil.isNotEmpty(list)){
			/* 当医嘱内容内容不为空时！！ 将查询到的医嘱生成任务同时修改状态为执行中， */
			 //1、拿取医嘱的内容，将医嘱的内容与 医嘱标签表做对比查询是否有一致的数

			for (int i =0;i<list.size();i++){
				/*  利用数据中的医嘱id查询医嘱的详细数据，医嘱数据及医嘱的内容*/
				HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceService.hisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());

				/* 判断查询的数据不为空的时候生成任务 */
				TasksVo tasksVo = hisDoctorsAdviceService.taskAdd(hisDoctorsAdviceVo);
				/* 将当条产生医嘱内容扩展数据中的预开始时间回填到任务中*/
				/* 先将主任务里面的预开始时间，修改 */
				tasksVo.getTask().setPreEndTime(list.get(i).getPreExecuteTime());
				tasksVo.getTask().setTaskTypeId(list.get(i).getDoctorsAdviceExtId()); // 医嘱来源的id 为医嘱批次号的id
				/* 在修改子任务中的预执行时间，前提当前任务的预执行时间为空*/

				for (int j =0;j<tasksVo.getSubTask().size();j++ ){
					/* 当有子任务的预时预开始时间为空的时候，将当前医嘱的预开始时间回填 */
					if (tasksVo.getSubTask().get(j).getPreStartTime()==null){
						tasksVo.getSubTask().get(j).setPreStartTime(list.get(i).getPreExecuteTime());
					}
				}

				/*将主任务和子任务新增*/
				Boolean aBoolean = tasksService.addTaskVo(tasksVo);
				if (!aBoolean){
					return false;
				}
				//子任务和主任务创建成功后，修改医嘱扩展表中的状态
				list.forEach(e->{
					//将医嘱的状态修改为执行中
					e.setExecuteType(DoctorsAdviceExtEnum.EXECUTION_IN_PROGRESS.getCode());
				});
				/*同时将创建完成的医嘱状态修改为执行中*/
				boolean b = this.updateBatchById(list);
				if (b){
					return true;
				}else {
					return false;
				}

			}
			return true;
		}else{
			return true;
		}

	}

	/**
	 * 通过查询his传来的医嘱信息新增任务【用于医嘱的新增】
	 * @param hisDoctorsAdviceExt
	 * @return
	 */
	@Override
	public Boolean hisDoctorsAdviceExtAddTask(HisDoctorsAdviceExt hisDoctorsAdviceExt) {
		/*  利用数据中的医嘱id查询医嘱的详细数据，医嘱数据及医嘱的内容*/
		//HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceService.hisDoctorsAdviceId(hisDoctorsAdviceExt.getHisDoctorsAdviceId());

		/* 判断查询的数据不为空的时候生成任务 */
		TasksVo tasksVo = hisDoctorsAdviceService.HisDoctorsAdviceExtAddTask(hisDoctorsAdviceExt);
		/* 将当条产生医嘱内容扩展数据中的预开始时间回填到任务中*/
		/* 先将主任务里面的预开始时间，修改 */
		tasksVo.getTask().setPreEndTime(hisDoctorsAdviceExt.getPreExecuteTime());
		tasksVo.getTask().setTaskTypeId(hisDoctorsAdviceExt.getDoctorsAdviceExtId()); // 医嘱来源的id 为医嘱批次号的id
		/* 在修改子任务中的预执行时间，前提当前任务的预执行时间为空*/

		for (int j =0;j<tasksVo.getSubTask().size();j++ ){
			/* 当有子任务的预时预开始时间为空的时候，将当前医嘱的预开始时间回填 */
			if (tasksVo.getSubTask().get(j).getPreStartTime()==null){
				tasksVo.getSubTask().get(j).setPreStartTime(hisDoctorsAdviceExt.getPreExecuteTime());
			}
		}

		/*将主任务和子任务新增*/
		Boolean aBoolean = tasksService.addTaskVo(tasksVo);
		if (!aBoolean){
			return false;
		}
		//子任务和主任务创建成功后，修改医嘱扩展表中的状态
			//将医嘱的状态修改为执行中
		hisDoctorsAdviceExt.setExecuteType(DoctorsAdviceExtEnum.EXECUTION_IN_PROGRESS.getCode());

		/*同时将创建完成的医嘱状态修改为执行中*/
		boolean b = this.updateById(hisDoctorsAdviceExt);

		if (b){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 查询在当前时间需要执行的医嘱
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceExt> selectTime() {
		return hisDoctorsAdviceExtMapper.selectTime();
	}

	/**
	 * 通过雪花id查询数据
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public HisDoctorsAdviceExt doctorsAdviceExt(String id) {
		return hisDoctorsAdviceExtMapper.doctorsAdviceExt(id);
	}

	/**
	 * 从his端新增数据
	 * @param hisDoctorsAdviceExt
	 * @return
	 */
	@Override
	@Transactional
	public Boolean hisAdd(HisDoctorsAdviceExt hisDoctorsAdviceExt) {

		/* 判断查询到的数据是否已经存在 */
	//	HisDoctorsAdviceExt doctorsAdviceExt = this.hisFZYExecInfoId(hisDoctorsAdviceExt.getHisFZYExecInfoId());
		/*同时判断当前查询到的数据是否已经完成*/

		//通过his的医嘱id【icu的医嘱内容id，查询关联医嘱id】
		HisDoctorsAdviceProject hisDoctorsAdviceProject = hisDoctorsAdviceProjectService.hisDoctorsAdviceProjectId(hisDoctorsAdviceExt.getHisDoctorsAdviceProjectId());
		if (hisDoctorsAdviceProject !=null){
			/* 将查询出来的医嘱id 保存到执行记录中 */
			hisDoctorsAdviceExt.setHisDoctorsAdviceId(hisDoctorsAdviceProject.getHisDoctorsAdviceId());//
			hisDoctorsAdviceExt.setExecuteCompany(hisDoctorsAdviceProject.getCompany());//剂量单位
			hisDoctorsAdviceExt.setExecuteDosage(hisDoctorsAdviceProject.getConsumption()); //剂量
		}else{
			throw new ApiException(ResponseCode.DOCTORS_ADVICE_PROJECT.getCode(),ResponseCode.DOCTORS_ADVICE_PROJECT.getMessage());
		}
			hisDoctorsAdviceExt.setDoctorsAdviceExtId(SnowFlake.getId()+"");
			boolean save = this.save(hisDoctorsAdviceExt);
			if (!save){
				return false;
			}
			// 当医嘱接收完成后，新增任务  doctorsAdviceExtAddTask
			Boolean task = this.hisDoctorsAdviceExtAddTask(hisDoctorsAdviceExt);
			if (!task){
				return false;
			}
		//if (doctorsAdviceExt==null){
//		}if(doctorsAdviceExt.getExecuteType()!= HisDoctorsAdviceExtEnum.COMPLETE.getCode() &&doctorsAdviceExt!=null){//判断当前查到的数据是否已经完成，如果已经完成【4】,或者数据不为空时不做处理
//			/* 存在是为修改数据*/
//			hisDoctorsAdviceExt.setId(doctorsAdviceExt.getId());
//			hisDoctorsAdviceExt.setDoctorsAdviceExtId(doctorsAdviceExt.getDoctorsAdviceExtId());
//			boolean b = this.updateById(hisDoctorsAdviceExt);
//			if (!b){
//				return false;
//			}
//
//		}

		return true;
	}

	/**
	 * 通过his的执行医嘱id查询
	 * @param id
	 * @return
	 */
	@Override
	public HisDoctorsAdviceExt hisFZYExecInfoId(String id) {

		return hisDoctorsAdviceExtMapper.hisFZYExecInfoId(id);
	}


	/**
	 * 通过医嘱的执行记录雪花id，查询相对的医嘱数据
	 */
	@Override
	public HisDoctorsAdviceVo doctorsAdvice(String id) {
		//通过雪花id查询到数据


		HisDoctorsAdviceExt hisDoctorsAdviceExt = hisDoctorsAdviceExtMapper.doctorsAdviceExt(id);
		if (hisDoctorsAdviceExt==null){
			return null;
		}
		HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceService.hisDoctorsAdviceId(hisDoctorsAdviceExt.getHisDoctorsAdviceId());
		if (hisDoctorsAdviceVo!=null){
		return hisDoctorsAdviceVo;
		}else{
			return null;
		}
	}


	/**
	 * 通过关联医嘱id  查询相关的医嘱所有数据
	 * @param id
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceExt> hisDoctorsAdviceId(String id) {
		return hisDoctorsAdviceExtMapper.hisDoctorsAdviceId(id);
	}
}
