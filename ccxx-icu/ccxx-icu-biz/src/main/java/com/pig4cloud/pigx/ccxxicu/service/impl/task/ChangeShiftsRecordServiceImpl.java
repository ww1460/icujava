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
package com.pig4cloud.pigx.ccxxicu.service.impl.task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.ChangeShiftsRecordVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.ChangeShiftsVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.task.ChangeShiftsRecordMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TaskMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsDescribeService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsTaskRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交接班记录
 *
 * @author pigx code generator
 * @date 2019-08-23 16:11:43
 */
@Service
public class ChangeShiftsRecordServiceImpl extends ServiceImpl<ChangeShiftsRecordMapper, ChangeShiftsRecord> implements ChangeShiftsRecordService {


	@Autowired
	private ChangeShiftsRecordMapper changeShiftsRecordMapper;

	/**
	 * 交接班任务记录表
	 */
	@Autowired
	private ChangeShiftsTaskRecordService changeShiftsTaskRecordService;

	/**
	 * 任务
	 */
	@Autowired
	private TaskMapper taskMapper;


	/***
	 * 护士患者关联表记录表
	 */
	@Autowired
	private  NursePatientRecordService nursePatientRecordService;

	/**
	 * 护士患者关联表
	 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 交接班任务描述表
	 */
	@Autowired
	private ChangeShiftsDescribeService changeShiftsDescribeService;

	/**
	 * 子任务
	 */
	@Autowired
	private TaskSubTaskService taskSubTaskService;



	/**
	 * 查询交接班的任务
	 * @param task
	 * @return
	 */
	@Override
	public List<ChangeShiftsVo> shiftsTask(TaskVo task) {
		return changeShiftsRecordMapper.shiftsTask(task);
	}


	/**
	 * 点击交班记录
	 * @return
	 */
	@Transactional
	@Override
	public Boolean clickShiftsRecord(ChangeShiftsRecord changeShiftsRecord, List<ChangeShiftsTaskRecord> list) {


		if(list.size()==0){
			/* 任务条数 */
			changeShiftsRecord.setTaskCondition(0);
		}
		changeShiftsRecord.setState(0);//等待接班
		String id = SnowFlake.getId()+"";
		changeShiftsRecord.setChangeShiftsRecordId(id);//雪花id
		changeShiftsRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室id
		changeShiftsRecord.setShiftNurseId(SecurityUtils.getUser().getId()+""); //交班护士
		changeShiftsRecord.setShiftTime(LocalDateTime.now());  // 交班时间
		boolean save = this.save(changeShiftsRecord);
		if (save==false){
			return false;
		}

		/* 判断一下当前患者有没有交班的任务 */

		/* 任务条数 */
		changeShiftsRecord.setTaskCondition(0);
		if(CollectionUtils.isNotEmpty(list)){

			/* 新增多个交接班任务 */
			for (int i =0;i<list.size();i++){

				ChangeShiftsTaskRecord c =list.get(i);

				list.get(i).setChangeShiftsRecordId(id); // 记录表雪花id
				/*  同时将任务将当前任务的接收人修改为null*/
				TaskSubTask task = taskSubTaskService.taskSubTaskId(list.get(i).getTaskId());
				/* 同时将所有的任务接收人修改为null，及责任护士id 的字段等待接收护士接收 */
				TaskSubTask taskSubTask = new TaskSubTask();
				taskSubTask.setTaskSubTaskId(task.getTaskSubTaskId());
				taskSubTask.setId(task.getId());
				taskSubTask.setDutyNurseId("暂无");
				taskSubTask.setReceptionNurseId("暂无");
				taskSubTask.setReceiveTime(LocalDateTime.now());
				boolean b = taskSubTaskService.updateById(taskSubTask);
				if (!b){
					return false;
				}

				/* 同时断开当前患者与护士的关联关系 */
				NursePatientRecord nursePatientRecord = new NursePatientRecord();
				nursePatientRecord.setNurseId(SecurityUtils.getUser().getId()+""); //护士id
				nursePatientRecord.setPatientId(changeShiftsRecord.getPatientId());//患者id
				nursePatientRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
				Boolean disconnection = nursePatientRecordService.disconnection(nursePatientRecord);
				if (disconnection==false) {
					R.failed(null, "护士关联断开失败");
				}

			}
			boolean b = changeShiftsTaskRecordService.insetList(list);

			/* 同时删除任务交接班描述表中的数据 */
			changeShiftsDescribeService.deleteAll();
		}

		return true;
	}

	/**
	 * 全查交接班数据
	 * @param changeShiftsRecord
	 * @return
	 */
	@Override
	public List<ChangeShiftsRecordVo> selectAll(ChangeShiftsRecord changeShiftsRecord) {

		return changeShiftsRecordMapper.selectAll(changeShiftsRecord);
	}

	/**
	 * 点击接班
	 * @param changeShiftsRecord
	 * @param list
	 * @return
	 */
	@Transactional
	@Override
	public R clickSuccession(ChangeShiftsRecord changeShiftsRecord, List<ChangeShiftsTaskRecord> list) {



		/* 判断一下当前护士是否有接班的任务， */
		if(list.size()>0){
			/* 将交接班任务记录表中是任务的接收人变为当前护士  */


			for (int i =0;i<list.size();i++){
				/* 通过任务id查询任务的详情  */
				TaskSubTask task = taskSubTaskService.taskSubTaskId(list.get(i).getTaskId());
				TaskSubTask taskSubTask = new TaskSubTask();
				taskSubTask.setId(task.getId()); // 任务id
				taskSubTask.setDutyNurseId(SecurityUtils.getUser().getId()+"");//责任护士id
				taskSubTask.setReceptionNurseId(SecurityUtils.getUser().getId()+"");//接收护士
				taskSubTask.setReceiveTime(LocalDateTime.now()); // 接收时间
				boolean b = taskSubTaskService.updateById(taskSubTask);
				if (!b){
				return R.failed("修改任务失败！！！！！");
				}
			}

		}
		/* 完善护士的交接班记录 */
		ChangeShiftsRecordVo recordId = changeShiftsRecordMapper.getRecordId(changeShiftsRecord.getChangeShiftsRecordId());
		ChangeShiftsRecord changeShifts = new ChangeShiftsRecord();
		changeShifts.setId(recordId.getId());
		changeShifts.setState(1);//完成接班
		changeShifts.setSuccessionNurseId(SecurityUtils.getUser().getId()+"");//接班护士
		changeShifts.setSuccessionTime(LocalDateTime.now());//接班时间
		int insert = changeShiftsRecordMapper.updateById(changeShifts);
		if (insert==0){
			return R.failed(null,"交接班记录完善失败");
		}
		/* 删除交接班临时记录表的的数据 */
		changeShiftsDescribeService.deleteAll();

		/* 将当前护士和患者相互关联 */


		/* 给接收当前任务的护士与患者创建关联关系 */
		NursePatientRecord nursePatientRecord = new NursePatientRecord();
		nursePatientRecord.setNurseId(SecurityUtils.getUser().getId()+""); //护士id
		nursePatientRecord.setPatientId(recordId.getPatientId()); // 患者id
		nursePatientRecord.setOnlyDutyNurse(0);         // 接班护士默认为唯一责任护士
		return nursePatientRecordService.nurseRelationPatient(nursePatientRecord);


	}

	/**
	 * 雪花id查询数据源
	 * @param changeShiftsRecordId
	 * @return
	 */
	@Override
	public ChangeShiftsRecordVo getRecordId(String changeShiftsRecordId) {
		return changeShiftsRecordMapper.getRecordId(changeShiftsRecordId);
	}
}
