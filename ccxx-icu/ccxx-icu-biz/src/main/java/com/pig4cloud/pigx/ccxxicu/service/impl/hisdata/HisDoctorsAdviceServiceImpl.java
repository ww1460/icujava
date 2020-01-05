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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.HisDoctorsAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceCode;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.*;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.SubTaskDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TasksVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisDoctorsAdviceEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisDoctorsAdviceMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceCodeService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceExtService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.*;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医嘱表
 *
 * @author pigx code generator
 * @date 2019-08-30 11:23:01
 */
@Service
public class HisDoctorsAdviceServiceImpl extends ServiceImpl<HisDoctorsAdviceMapper, HisDoctorsAdvice> implements HisDoctorsAdviceService {

	@Autowired
	private HisDoctorsAdviceMapper  hisDoctorsAdviceMapper;

	/**
	 * 医嘱项目
	 */
	@Autowired
	private HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	/**
	 * 任务
	 */
	@Autowired
	private TasksService taskService;

	/**
	 * 患者id
	 */
	@Autowired
	private PatientService patientService;

	/**
	 * 护士患者关联记录
	 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 医嘱项目字典表
	 */
	@Autowired
	private DoctorsAdviceCodeService doctorsAdviceCodeService;

	/**
	 * 任务模板
	 */
	@Autowired
	private TemplateService templateService;
	/**
	 * 任务模板子任务
	 */
	@Autowired
	private TemplateSubTaskService templateSubTaskService;
	/**
	 * 长期任务
	 */
	@Autowired
	private TimingExecutionService timingExecutionService;

	/**
	 * 医嘱执行医嘱id
	 */
	@Autowired
	private HisDoctorsAdviceExtService hisDoctorsAdviceExtService;

	/**
	 *子任务
	 */
	@Autowired
	private TaskSubTaskService taskSubTaskService;




	/**
	 * 通过his医嘱id查询当前医嘱的详细数据
	 * @param id
	 * @return
	 */
	@Override
	public HisDoctorsAdviceVo hisDoctorsAdviceId(String id) {

		HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceMapper.hisDoctorsAdviceId(id);
		if (hisDoctorsAdviceVo !=null){
			List<HisDoctorsAdviceProject> hisDoctorsAdviceProjects = hisDoctorsAdviceProjectService.hisDoctorsAdviceId(hisDoctorsAdviceVo.getHisDoctorsAdviceId());
			/* 判断用医嘱id查询数据是由有，如果有的情况回填到Vo类中 */
			if (CollectionUtils.isNotEmpty(hisDoctorsAdviceProjects)){
				hisDoctorsAdviceVo.setProjectList(hisDoctorsAdviceProjects);
			}
			return hisDoctorsAdviceVo;
		}else{
			return hisDoctorsAdviceVo;
		}
	}

	/***
	 * 接收his传来的数据源
	 * @param doctorsAdviceBo
	 * @return
	 */
	@Override
	@Transactional
	public Boolean hisDoctorsAdvice(HisDoctorsAdviceBo doctorsAdviceBo) {

		/* 先将医嘱新增 */
		HisDoctorsAdvice hisDoctorsAdvice = new HisDoctorsAdvice();
		BeanUtil.copyProperties(doctorsAdviceBo,hisDoctorsAdvice);
		/* 通过his的患者id查询到相对应的患者数据 */
		PatientVo hisId = patientService.getHisId(hisDoctorsAdvice.getHisPatientId());
		String patientId = hisId.getPatientId();

		/* 保留医嘱内容 */
		hisDoctorsAdvice.setPatientId(patientId); //患者id
		hisDoctorsAdvice.setDoctorsAdviceId(SnowFlake.getId()+""); // 雪花
		hisDoctorsAdvice.setCreateTime(LocalDateTime.now());// 创建时间
		int insert = hisDoctorsAdviceMapper.insert(hisDoctorsAdvice);
		if(insert!=1){
			return false;
		}
		if(CollectionUtils.isNotEmpty(doctorsAdviceBo.getProjectList())){
			/* 将当前医嘱的批次号传输到医嘱内容中 */
			doctorsAdviceBo.getProjectList().forEach(e->{
				e.setBatchNumber(doctorsAdviceBo.getBatchNumber());
			});
		}
		/*对医嘱内容新增 */
		Boolean b = hisDoctorsAdviceProjectService.addAll(doctorsAdviceBo.getProjectList());
			if (b==false){
				return false;
			}

		/**
		 * 判断医嘱内容，判断当前医嘱的类型是否为描述形医嘱
		 */
		doctorsAdviceBo.getProjectList().forEach(e->{
			if (e.getDoctorsAdviceProjectType()==HisDoctorsAdviceEnum.HISDOCTORS_ADVICE_PROJECT_TYPE_NULL.getCode()){//判断当前医嘱是提醒医嘱，创建任务
				/* 通过护士id查询责任护士 */
				String s = nursePatientCorrelationService.selectDutyNurseId(patientId);
				hisDoctorsAdvice.setPatientId(patientId);//系统中h患者id
				/* 通过医嘱项目对任务进行填充数据 */
				Tasks task = new Tasks();//任务
				task.setTaskId(SnowFlake.getId()+"");//雪花id
				task.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室id
				task.setTaskType(TaskEnum.HIS_DOCTORS_ADVICE_PROJECT.getNumber());  //判断来源为医嘱
				task.setTaskTypeId(e.getDoctorsAdviceProjectId());// 批号作为来源id
				task.setPatientId(patientId);  //患者
				task.setExecutionType(TaskEnum.REMIND.getNumber());//当前任务为提醒任务
				task.setTaskName(e.getContent());//任务内容
				task.setTaskDescribe(e.getRemarks());//任务描述
				task.setDutyNurseId(s);//  责任护士
				task.setPreStartTime(hisDoctorsAdvice.getDoctorsAdviceTime());//预开始时间
				task.setCreateTime(LocalDateTime.now()); //创建时间
				task.setEmergency((hisDoctorsAdvice.getEmergency()==null||hisDoctorsAdvice.getEmergency().equals(""))?1:Integer.valueOf(hisDoctorsAdvice.getEmergency())); // 是否紧急
				task.setReceptionNurseId(s);//接收护士默认为责任护士
				task.setTaskRelation(TaskEnum.TASK_MAIN.getNumber());//主任务
				task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 任务状态为待执行
				task.setProgressBar(0);//进度条
				boolean save = taskService.save(task);
				if (!save){
					throw new ApiException(ResponseCode.TASK_ADD_FAIL.getCode(),ResponseCode.TASK_ADD_FAIL.getMessage());
				}
				Boolean aBoolean = taskService.addSubTask(task);
				if(aBoolean==false){
					throw new ApiException(ResponseCode.TASK_ADD_SUB_FAIL.getCode(),ResponseCode.TASK_ADD_FAIL.getMessage());
				}

			}

		});

		return true;
	}


	/**
	 * 全查询医嘱内容
	 * @param hisDoctorsAdvice
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceVo> selectAll(HisDoctorsAdvice hisDoctorsAdvice) {
		/* 查询到的数据 */
		List<HisDoctorsAdviceVo> list = hisDoctorsAdviceMapper.selectAll(hisDoctorsAdvice);
		if(list.size()>0){
			list.forEach(e->{
				/* 通过医嘱的批号查询相应的数据 */
				List<HisDoctorsAdviceProject> hisDoctorsAdviceProject = hisDoctorsAdviceProjectService.hisDoctorsAdviceId(e.getHisDoctorsAdviceId());
				e.setProjectList(hisDoctorsAdviceProject);
			});
		}else{
			return null;
		}
		return list;
	}

	/**
	 * 长期任务查询 用于定时
	 * @param
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceVo> longTermTask() {

		return hisDoctorsAdviceMapper.longTermTask();
	}

	/**
	 * 通过医嘱对任务的新增
	 * @param hisDoctorsAdviceVo
	 * @return
	 */
	@Override
	public TasksVo taskAdd(HisDoctorsAdviceVo hisDoctorsAdviceVo) {

		TasksVo tasksVo = new TasksVo();
		//  将当前医嘱数据的内容取出
		List<HisDoctorsAdviceProject> list = hisDoctorsAdviceVo.getProjectList();
		//  医嘱内容不为空的时候
		if (CollectionUtils.isNotEmpty(list)){
		/**
		 * 通过查询到的数据进行任务的新增
		 */
		/* 通过患者的id查询出当前患者的责任护士id */
		String s = nursePatientCorrelationService.selectDutyNurseId(hisDoctorsAdviceVo.getPatientId());

		// 通过患者 查询当前患者在那个科室中
		String dept = patientService.getHisId(hisDoctorsAdviceVo.getHisPatientId()).getAdmissionDepartment();
		/**
		 * 1、利用医嘱的内容生成一条主任务，
		 */
		Tasks task = hisDoctorsAdviceProjectService.taskAll(hisDoctorsAdviceVo.getProjectList());
		task.setTaskId(SnowFlake.getId()+"");//雪花id
		task.setTaskType(TaskEnum.DOCTORS_ADVICE.getNumber());  //将任务类型添加为医嘱类型
		task.setDutyNurseId(s);//  责任护士
		task.setPatientId(hisDoctorsAdviceVo.getPatientId());//患者id
		task.setPreStartTime(hisDoctorsAdviceVo.getDoctorsAdviceTime());//预开始时间
		task.setCreateTime(LocalDateTime.now()); //创建时间
		task.setEmergency(Integer.valueOf(hisDoctorsAdviceVo.getEmergency())); // 是否紧急
		task.setReceptionNurseId(s);//接收护士默认为责任护士
		task.setReceiveTime(LocalDateTime.now()); //接收时间
		task.setExecutionType(TaskEnum.IMPLEMENT.getNumber());//任务的执行类型
		task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//任务状态待执行
		task.setSubTaskNum(0);//子任务条数
		task.setExecutingNum(0);//现在执行0条
		task.setTaskRelation(TaskEnum.TASK_MAIN.getNumber());//主任务
		task.setProgressBar(0);//进度条
		task.setDeptId(dept);//科室id
		tasksVo.setTask(task);

				/* 创建一个子任务集合 */
				List<TaskSubTask> taskSubTaskList = new ArrayList<>();
				// 循环医嘱的内容
					for (int i = 0;i<list.size();i++){
						/* 通过当前医嘱内容，判断医嘱内容，与医嘱编码是否有医嘱的*/
						System.out.println("用什么医嘱内容来查询的  ----------------"+list.get(i).getContent());
						DoctorsAdviceCode doctorsAdviceCode = doctorsAdviceCodeService.doctorsAdviceContent(list.get(i).getContent());
						System.out.println("医嘱查询出来数据   ----------------"+doctorsAdviceCode);
						/* 当通过医嘱内容 查询医嘱编码 【有医嘱编码的情况】 */
						if (doctorsAdviceCode !=null&&!doctorsAdviceCode.getDoctorsAdviceContent().equals("")&&doctorsAdviceCode.getDoctorsAdviceContent() !=null){
							/*当医嘱编码与医嘱内容有一致的时候，通过医嘱的编码 ID 查询医嘱模板的数据 */
							Template template = templateService.sourceId(doctorsAdviceCode.getDoctorsAdviceCodeId());
							/*  当通过医嘱编码查询任务模板，有任务模板时*/
							if (template != null){
								//通过主模板id 查询当前主模板下有哪些子模板数据
								List<TemplateSubTask> templateSub = templateSubTaskService.taskTemplateIdTime(template.getTemplateId());
								/*判断子模板数据不为空的时候*/
								if (CollectionUtils.isNotEmpty(templateSub)){
									/* 将任务模板中的数据生成关联当前主任务的子任务*/
									for (int j =0;j<templateSub.size();j++){
										/*判断当前的任务模板中的任务是否为长期任务，判断任务有没有间隔  当间隔时间不等于空 同时不等于0时 将当前任务模板，新增到长期任务中 */
										if (templateSub.get(j).getIntervalTime() !=null && templateSub.get(j).getIntervalTime() !=0){

											/**
											 * 长期任务操作
											 */
											TimingExecution timingExecution = new TimingExecution();
											timingExecution.setDelFlag(1);//删除B标识
											timingExecution.setSource(TaskEnum.DOCTORS_ADVICE.getNumber());//来源为医嘱
											timingExecution.setSourceId(hisDoctorsAdviceVo.getHisDoctorsAdviceId());//保存his医嘱id

											if (templateSub.get(j).getPreStartTime()!=null){
												LocalDateTime startTime = timingExecutionService.dateTime(templateSub.get(j).getPreStartTime());
												timingExecution.setPreStartTime(startTime);//预开始时间
												timingExecution.setNextExecutionTime(startTime);//下次执行执行为预开始时间
											}

											if (templateSub.get(j).getPreEndTime()!=null){
												LocalDateTime entTime = timingExecutionService.dateTime(templateSub.get(j).getPreEndTime());
												/* 判断结束时间 */
												LocalDateTime now = LocalDateTime.now();
												//  判断结束时间是否比当前时间之后
												boolean before = entTime.isBefore(now);
												if (before){ //为真时，回填结束时间
													timingExecution.setPreEndTime(entTime);//预结束时间
												}else{//为加上证明结束时间比当前时间小，那么次长期任务不执行数据，将开始和结束时间修改到第二天
													LocalDateTime startTime = timingExecutionService.dateTime(templateSub.get(j).getPreStartTime());
													timingExecution.setPreStartTime(startTime.plusDays(1));//预开始时间
													timingExecution.setNextExecutionTime(startTime.plusDays(1));//下次执行执行为预开始时间
													timingExecution.setPreEndTime(entTime.plusDays(1));//预结束时间
												}

											}
											timingExecution.setTaskName(templateSub.get(j).getTaskContent());//任务名称
											timingExecution.setTaskDescribe(templateSub.get(j).getTaskDescribe());//任务描述
											timingExecution.setIntervalTimes(templateSub.get(j).getIntervalTime());//间隔时间
											timingExecution.setPatientId(hisDoctorsAdviceVo.getPatientId());//患者id
											timingExecution.setDeptId(dept);//科室id
											timingExecution.setCreateTime(LocalDateTime.now());//创建时间
											timingExecution.setExecutionType(templateSub.get(j).getExecutionType());//任务执行类型

											boolean save = timingExecutionService.save(timingExecution);
											if (!save){
												return null;
											}
											System.out.println("长期任务----------");

										}else{
											TaskSubTask subTask = new TaskSubTask();
											subTask.setTaskSubTaskId(SnowFlake.getId()+"");//雪花
											/*  判断一下当前医嘱的任务模板，是否有来源id，如果有情况下情*/
											if (templateSub.get(j).getSource()==TaskEnum.DOCTORS_ADVICE_BUILD_BY_ONESELF.getNumber()){	/*  当前任务模板为，医嘱自建时 */
												subTask.setTaskType(TaskEnum.DOCTORS_ADVICE_BUILD_BY_ONESELF.getNumber());
												subTask.setTaskTypeId(templateSub.get(j).getTaskTemplateId()+"-"+list.get(i).getDoctorsAdviceProjectId());
											}if (templateSub.get(j).getSource()==TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()){/*  当前任务模板为，医嘱项目时 */
												subTask.setTaskType(TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber());
												subTask.setTaskTypeId(templateSub.get(j).getTaskTemplateId()+"-"+list.get(i).getDoctorsAdviceProjectId()+"-"+templateSub.get(j).getSourceId());
											}
											subTask.setTaskName(templateSub.get(j).getTaskContent());//任务名称
											subTask.setTaskDescribe(templateSub.get(j).getTaskDescribe());//任务内容
											subTask.setPatientId(hisDoctorsAdviceVo.getPatientId());//患者id
											subTask.setDutyNurseId(s);//责任护士

											if (templateSub.get(j).getPreStartTime()!=null){
												LocalDateTime startTime = timingExecutionService.dateTime(templateSub.get(j).getPreStartTime());
												subTask.setPreStartTime(startTime);//预开始时间
											}

											if (templateSub.get(j).getPreEndTime()!=null){
												LocalDateTime entTime = timingExecutionService.dateTime(templateSub.get(j).getPreEndTime());
												subTask.setPreStartTime(entTime);//预开始时间
											}
											subTask.setCreateTime(LocalDateTime.now());//创建时间
											subTask.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//待执行状态
											subTask.setEmergency(Integer.valueOf(hisDoctorsAdviceVo.getEmergency()));//紧急 状态
											subTask.setExecutionType(templateSub.get(j).getExecutionType());//执行类型
											subTask.setMainTaskId(task.getTaskId());//主任务id
											subTask.setTaskRelation(TaskEnum.TASK_SON.getNumber());//子任务标识
											subTask.setProgressBar(0);//进度条
											subTask.setReceptionNurseId(task.getReceptionNurseId());//与接收护士
											subTask.setDeptId(dept);//科室
											subTask.setDelFlag(0);//删除

											//添加剂量   【判断当前医嘱是否有剂量】
											if (list.get(j).getConsumption()!=null &&!"".equals(list.get(j).getConsumption())){
												/*  在剂量单位不为空的时候*/
												Integer intakeOutputValue =0; //剂量
												if(list.get(j).getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_L.getCode())){
													/* 当满足时，将单条医嘱的内容及  值  单位取出 */
													intakeOutputValue = intakeOutputValue+ Integer.valueOf(list.get(j).getConsumption()+1000);
												}if(list.get(j).getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_ML.getCode())){
													/* 当满足时，将单条医嘱的内容及  值  单位取出 */
													intakeOutputValue = intakeOutputValue+ Integer.valueOf(list.get(j).getConsumption());
												}
												subTask.setDosage(intakeOutputValue); //剂量

											}

											// 相关主任务子任务+1
											task.setSubTaskNum(task.getSubTaskNum()+1);
											/* 当数据填充完成后，将数据回填到list集合 */
											taskSubTaskList.add(subTask);

										}
									};

								}else{//当任务模板没有查询到 子模板时
									System.out.println(" 2  当任务模板没有查询到 子模板时");
									/* 当任务模板为空时，用当条医嘱的内容进行新增子任务*/
									TaskSubTask taskSubTask = hisDoctorsAdviceProjectService.addTask(list.get(i));
									taskSubTask.setPatientId(hisDoctorsAdviceVo.getPatientId());//患者id
									taskSubTask.setDutyNurseId(s);//责任护士
									taskSubTask.setReceptionNurseId(s);//接收护士
									taskSubTask.setEmergency(Integer.valueOf(hisDoctorsAdviceVo.getEmergency()));//紧急 状态
									taskSubTask.setMainTaskId(task.getTaskId());//主任务id
									taskSubTask.setDeptId(dept);//科室
									taskSubTask.setDelFlag(0);//删除
									// 相关主任务子任务+1
									task.setSubTaskNum(task.getSubTaskNum()+1);
									/* 当数据填充完成后，将数据回填到list集合 */
									taskSubTaskList.add(taskSubTask);
								}

							}else{//当医嘱编码查询 没有查询到任务模板时候
								System.out.println(" 3  当医嘱编码查询 没有查询到任务模板时候");
								/* 当任务模板为空时，用当条医嘱的内容进行新增子任务*/
								TaskSubTask taskSubTask = hisDoctorsAdviceProjectService.addTask(list.get(i));
								taskSubTask.setPatientId(hisDoctorsAdviceVo.getPatientId());//患者id
								taskSubTask.setDutyNurseId(s);//责任护士
								taskSubTask.setReceptionNurseId(s);//接收护士
								taskSubTask.setEmergency(Integer.valueOf(hisDoctorsAdviceVo.getEmergency()));//紧急 状态
								taskSubTask.setMainTaskId(task.getTaskId());//主任务id
								taskSubTask.setDeptId(dept);//科室
								taskSubTask.setDelFlag(0);//删除
								// 相关主任务子任务+1
								task.setSubTaskNum(task.getSubTaskNum()+1);
								/* 当数据填充完成后，将数据回填到list集合 */
								taskSubTaskList.add(taskSubTask);
							}

						}else{  //  当条医嘱没有医嘱编码的时候
							System.out.println(" 4  当条医嘱没有医嘱编码的时候");
							/* 当任务模板为空时，用当条医嘱的内容进行新增子任务*/
							TaskSubTask taskSubTask = hisDoctorsAdviceProjectService.addTask(list.get(i));
							taskSubTask.setPatientId(hisDoctorsAdviceVo.getPatientId());//患者id
							taskSubTask.setDutyNurseId(s);//责任护士
							taskSubTask.setReceptionNurseId(s);//接收护士
							taskSubTask.setEmergency(Integer.valueOf(hisDoctorsAdviceVo.getEmergency()));//紧急 状态
							taskSubTask.setMainTaskId(task.getTaskId());//主任务id
							taskSubTask.setDeptId(dept);//科室
							taskSubTask.setDelFlag(0);//删除
							// 相关主任务子任务+1
							task.setSubTaskNum(task.getSubTaskNum()+1);
							/* 当数据填充完成后，将数据回填到list集合 */
							taskSubTaskList.add(taskSubTask);
						}

					}
					/*  将循环出来子任务模板中的数据进行回填*/
					tasksVo.setSubTask(taskSubTaskList);
			return tasksVo;

			}else{
				/*  当医嘱的医嘱内容为空时*/
			return null;
			}

	}

	/**
	 * 通过执行医嘱的内容对任务的填充
	 * @param hisDoctorsAdviceExt
	 * @return
	 */
	@Override
	public TasksVo HisDoctorsAdviceExtAddTask(HisDoctorsAdviceExt hisDoctorsAdviceExt) {


		if (hisDoctorsAdviceExt==null) {
			throw new ApiException(ResponseCode.DOCTORS_ADVICE_EXT_IS_NULL.getCode(),ResponseCode.DOCTORS_ADVICE_EXT_IS_NULL.getMessage());
		}
			/**
			 * 通过查询到的数据进行任务的新增
			 */

			//创建任务模板
			TasksVo tasksVo = new TasksVo();

			PatientVo patinet = patientService.getHisId(hisDoctorsAdviceExt.getHisPatientId());//通过his患者id查询当前患者数据
			// 通过患者 查询当前患者在那个科室中
			String dept = patinet.getAdmissionDepartment();
				/* 通过患者的id查询出当前患者的责任护士id */
			String s = nursePatientCorrelationService.selectDutyNurseId(patinet.getPatientId());

				/**
				 * 1、利用医嘱的内容生成一条主任务，
				 */
				Tasks task = new Tasks();
				// 利用his的医嘱id【我们的医嘱内容id】，查询当前执行医嘱对应的医嘱内容
				HisDoctorsAdviceProject hisDoctorsAdviceProject = hisDoctorsAdviceProjectService.hisDoctorsAdviceProjectId(hisDoctorsAdviceExt.getHisDoctorsAdviceProjectId());
				if (hisDoctorsAdviceProject !=null){
					task.setTaskName(hisDoctorsAdviceProject.getContent());
					task.setTaskDescribe(hisDoctorsAdviceProject.getRemarks());
				}
				task.setTaskTypeId(hisDoctorsAdviceExt.getHisFZYExecInfoId());//任务项目id
			    task.setTaskType(TaskEnum.DOCTORS_ADVICE.getNumber());//任务项目类型   医嘱类型
				task.setTaskId(SnowFlake.getId() + "");//雪花id
				task.setDutyNurseId(s);//  责任护士
				task.setPatientId(patinet.getPatientId());//患者id
				task.setPreStartTime(hisDoctorsAdviceExt.getPreExecuteTime());//预开始时间
				task.setCreateTime(LocalDateTime.now()); //创建时间
				task.setEmergency(TaskEnum.TASK_COMMONLY.getNumber()); // 是否紧急
				task.setReceptionNurseId(s);//接收护士默认为责任护士
				task.setReceiveTime(LocalDateTime.now()); //接收时间
				task.setExecutionType(TaskEnum.IMPLEMENT.getNumber());//任务的执行类型
				task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//任务状态待执行
				task.setSubTaskNum(0);//子任务条数
				task.setExecutingNum(0);//现在执行0条
				task.setTaskRelation(TaskEnum.TASK_MAIN.getNumber());//主任务
				task.setProgressBar(0);//进度条
				task.setDeptId(dept);//科室id
				tasksVo.setTask(task);
			/* 创建一个子任务集合 */
			List<TaskSubTask> taskSubTaskList = new ArrayList<>();


			/* 通过当前医嘱内容，判断医嘱内容，与医嘱编码是否有医嘱的*/
			DoctorsAdviceCode doctorsAdviceCode = doctorsAdviceCodeService.doctorsAdviceContent(hisDoctorsAdviceProject.getContent());
			/* 当通过医嘱内容 查询医嘱编码 【有医嘱编码的情况】 */
			if (doctorsAdviceCode !=null&&!doctorsAdviceCode.getDoctorsAdviceContent().equals("")&&doctorsAdviceCode.getDoctorsAdviceContent() !=null){
				/*当医嘱编码与医嘱内容有一致的时候，通过医嘱的编码 ID 查询医嘱模板的数据 */
				Template template = templateService.sourceId(doctorsAdviceCode.getDoctorsAdviceCodeId());
				/*  当通过医嘱编码查询任务模板，有任务模板时*/
				if (template != null){
					//通过主模板id 查询当前主模板下有哪些子模板数据
					List<TemplateSubTask> templateSub = templateSubTaskService.taskTemplateIdTime(template.getTemplateId());
					/*判断子模板数据不为空的时候*/
					if (CollectionUtils.isNotEmpty(templateSub)){
						/* 将任务模板中的数据生成关联当前主任务的子任务*/
						for (int j =0;j<templateSub.size();j++){
							/*判断当前的任务模板中的任务是否为长期任务，判断任务有没有间隔  当间隔时间不等于空 同时不等于0时 将当前任务模板，新增到长期任务中 */
							if (templateSub.get(j).getIntervalTime() !=null && templateSub.get(j).getIntervalTime() !=0){

								/**
								 * 长期任务操作
								 */
								TimingExecution timingExecution = new TimingExecution();
								timingExecution.setDelFlag(0);//删除B标识
								timingExecution.setSource(TaskEnum.DOCTORS_ADVICE.getNumber());//来源为医嘱
								timingExecution.setSourceId(hisDoctorsAdviceProject.getHisDoctorsAdviceProjectId());//保存his医嘱id【icu的医嘱内容id】

								if (templateSub.get(j).getPreStartTime()!=null){
									LocalDateTime startTime = timingExecutionService.dateTime(templateSub.get(j).getPreStartTime());
									timingExecution.setPreStartTime(startTime);//预开始时间
									timingExecution.setNextExecutionTime(startTime);//下次执行执行为预开始时间
								}else{
									timingExecution.setPreStartTime(LocalDateTime.now());//预开始时间
									timingExecution.setNextExecutionTime(LocalDateTime.now());//下次执行执行为预开始时间
								}

								if (templateSub.get(j).getPreEndTime()!=null){
									LocalDateTime entTime = timingExecutionService.dateTime(templateSub.get(j).getPreEndTime());
									/* 判断结束时间 */
									LocalDateTime now = LocalDateTime.now();
									//  判断结束时间是否比当前时间之后
									boolean before = entTime.isBefore(now);
									if (before){ //为真时，回填结束时间
										timingExecution.setPreEndTime(entTime);//预结束时间
									}else{//为加上证明结束时间比当前时间小，那么次长期任务不执行数据，将开始和结束时间修改到第二天
										LocalDateTime startTime = timingExecutionService.dateTime(templateSub.get(j).getPreStartTime());
										timingExecution.setPreStartTime(startTime.plusDays(1));//预开始时间
										timingExecution.setNextExecutionTime(startTime.plusDays(1));//下次执行执行为预开始时间
										timingExecution.setPreEndTime(entTime.plusDays(1));//预结束时间
									}

								}else{
									timingExecution.setPreEndTime(LocalDateTime.now().plusHours(1));//预结束时间
								}
								timingExecution.setTaskName(templateSub.get(j).getTaskContent());//任务名称
								timingExecution.setTaskDescribe(templateSub.get(j).getTaskDescribe());//任务描述
								timingExecution.setIntervalTimes(templateSub.get(j).getIntervalTime());//间隔时间
								timingExecution.setPatientId(patinet.getPatientId());//患者id
								timingExecution.setDeptId(dept);//科室id
								timingExecution.setCreateTime(LocalDateTime.now());//创建时间
								timingExecution.setExecutionType(templateSub.get(j).getExecutionType());//任务执行类型

								boolean save = timingExecutionService.save(timingExecution);
								if (!save){
									return null;
								}


							}else{
								TaskSubTask subTask = new TaskSubTask();
								subTask.setTaskSubTaskId(SnowFlake.getId()+"");//雪花
								/*  判断一下当前医嘱的任务模板，是否有来源id，如果有情况下情*/
								if (templateSub.get(j).getSource()==TaskEnum.DOCTORS_ADVICE_BUILD_BY_ONESELF.getNumber()){	/*  当前任务模板为，医嘱自建时 */
									subTask.setTaskType(TaskEnum.DOCTORS_ADVICE_BUILD_BY_ONESELF.getNumber());
									subTask.setTaskTypeId(templateSub.get(j).getTaskTemplateId()+"-"+hisDoctorsAdviceProject.getDoctorsAdviceProjectId());
								}if (templateSub.get(j).getSource()==TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()){/*  当前任务模板为，医嘱项目时 */
									subTask.setTaskType(TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber());
									subTask.setTaskTypeId(templateSub.get(j).getTaskTemplateId()+"-"+hisDoctorsAdviceProject.getDoctorsAdviceProjectId()+"-"+templateSub.get(j).getSourceId());
								}
								subTask.setTaskName(templateSub.get(j).getTaskContent());//任务名称
								subTask.setTaskDescribe(templateSub.get(j).getTaskDescribe());//任务内容
								subTask.setPatientId(patinet.getPatientId());//患者id
								subTask.setDutyNurseId(s);//责任护士

								if (templateSub.get(j).getPreStartTime()!=null){
									LocalDateTime startTime = timingExecutionService.dateTime(templateSub.get(j).getPreStartTime());
									subTask.setPreStartTime(startTime);//预开始时间
								}else{
									subTask.setPreStartTime(LocalDateTime.now());//预开始时间
								}

								if (templateSub.get(j).getPreEndTime()!=null){
									LocalDateTime entTime = timingExecutionService.dateTime(templateSub.get(j).getPreEndTime());
									subTask.setPreStartTime(entTime);//预开始时间
								}else{
									subTask.setPreStartTime(LocalDateTime.now().plusHours(1));//预开始时间
								}
								subTask.setCreateTime(LocalDateTime.now());//创建时间
								subTask.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber());//待执行状态
								subTask.setEmergency(TaskEnum.TASK_COMMONLY.getNumber());//紧急 状态
								subTask.setExecutionType(templateSub.get(j).getExecutionType());//执行类型
								subTask.setMainTaskId(task.getTaskId());//主任务id
								subTask.setTaskRelation(TaskEnum.TASK_SON.getNumber());//子任务标识
								subTask.setProgressBar(0);//进度条
								subTask.setReceptionNurseId(task.getReceptionNurseId());//与接收护士
								subTask.setDeptId(dept);//科室
								subTask.setDelFlag(0);//删除

								//添加剂量   【判断当前医嘱是否有剂量,单次执行数量】
								if (hisDoctorsAdviceExt.getExecuteDosage()!=null &&!"".equals(hisDoctorsAdviceExt.getExecuteDosage())){
									/*  在剂量单位不为空的时候*/
									Integer intakeOutputValue =0; //剂量
									if(hisDoctorsAdviceExt.getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_L.getCode())){
										/* 当满足时，将单条医嘱的内容及  值  单位取出 */
										intakeOutputValue = intakeOutputValue+ Integer.valueOf(hisDoctorsAdviceExt.getExecuteDosage()+1000);
									}if(hisDoctorsAdviceExt.getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_ML.getCode())){
										/* 当满足时，将单条医嘱的内容及  值  单位取出 */
										intakeOutputValue = intakeOutputValue+ Integer.valueOf(hisDoctorsAdviceExt.getExecuteDosage());
									}
									subTask.setDosage(intakeOutputValue); //剂量

								}

								// 相关主任务子任务+1
								task.setSubTaskNum(task.getSubTaskNum()+1);
								/* 当数据填充完成后，将数据回填到list集合 */
								taskSubTaskList.add(subTask);

							}
						};

					}else{//当任务模板没有查询到 子模板时
						System.out.println(" 2  当任务模板没有查询到 子模板时");
						/* 当任务模板为空时，用当条医嘱的内容进行新增子任务*/
						TaskSubTask taskSubTask = hisDoctorsAdviceProjectService.addTask(hisDoctorsAdviceProject);

						taskSubTask.setPatientId(patinet.getPatientId());//患者id
						taskSubTask.setDutyNurseId(s);//责任护士
						taskSubTask.setReceptionNurseId(s);//接收护士
						taskSubTask.setEmergency(Integer.valueOf(TaskEnum.TASK_COMMONLY.getNumber()));//紧急 状态
						taskSubTask.setMainTaskId(task.getTaskId());//主任务id
						taskSubTask.setDeptId(dept);//科室
						taskSubTask.setDelFlag(0);//删除
						// 相关主任务子任务+1
						task.setSubTaskNum(task.getSubTaskNum()+1);
						/* 当数据填充完成后，将数据回填到list集合 */
						taskSubTaskList.add(taskSubTask);
					}

				}else{//当医嘱编码查询 没有查询到任务模板时候
					System.out.println(" 3  当医嘱编码查询 没有查询到任务模板时候");
					/* 当任务模板为空时，用当条医嘱的内容进行新增子任务*/
					TaskSubTask taskSubTask = hisDoctorsAdviceProjectService.addTask(hisDoctorsAdviceProject);
					taskSubTask.setPatientId(patinet.getPatientId());//患者id
					taskSubTask.setDutyNurseId(s);//责任护士
					taskSubTask.setReceptionNurseId(s);//接收护士
					taskSubTask.setEmergency(Integer.valueOf(TaskEnum.TASK_COMMONLY.getNumber()));//紧急 状态
					taskSubTask.setMainTaskId(task.getTaskId());//主任务id
					taskSubTask.setDeptId(dept);//科室
					taskSubTask.setDelFlag(0);//删除
					// 相关主任务子任务+1
					task.setSubTaskNum(task.getSubTaskNum()+1);
					/* 当数据填充完成后，将数据回填到list集合 */
					taskSubTaskList.add(taskSubTask);
				}

			}else{  //  当条医嘱没有医嘱编码的时候
				System.out.println(" 4  当条医嘱没有医嘱编码的时候");
				/* 当任务模板为空时，用当条医嘱的内容进行新增子任务*/
				TaskSubTask taskSubTask = hisDoctorsAdviceProjectService.addTask(hisDoctorsAdviceProject);
				taskSubTask.setPatientId(patinet.getPatientId());//患者id
				taskSubTask.setDutyNurseId(s);//责任护士
				taskSubTask.setReceptionNurseId(s);//接收护士
				taskSubTask.setEmergency(Integer.valueOf(TaskEnum.TASK_COMMONLY.getNumber()));//紧急 状态
				taskSubTask.setMainTaskId(task.getTaskId());//主任务id
				taskSubTask.setDeptId(dept);//科室
				taskSubTask.setDelFlag(0);//删除
				// 相关主任务子任务+1
				task.setSubTaskNum(task.getSubTaskNum()+1);
				/* 当数据填充完成后，将数据回填到list集合 */
				taskSubTaskList.add(taskSubTask);
			}
			tasksVo.setSubTask(taskSubTaskList);
			return tasksVo;

	}

	/**
	 * 通过批号查询医嘱
	 * @param batchNumber
	 * @return
	 */
	@Override
	public HisDoctorsAdvice getBatchNumber(String batchNumber) {
		return hisDoctorsAdviceMapper.getBatchNumber(batchNumber);
	}

	/**
	 * 通过患者id 查询当前患者没有生成任务的医嘱
	 * @return
	 */
	@Override
	public List<HisDoctorsAdvice> notGenerate(String hisPatientId) {
		return hisDoctorsAdviceMapper.notGenerate(hisPatientId);
	}

	/**
	 * 通过医嘱id  查询当条医嘱中的所有数据
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> getByIdAll(Integer id) {

		Map<String,Object> test  = new HashMap<>();

		/* 通过id查询当条医嘱的内容 */
		HisDoctorsAdviceVo byId = hisDoctorsAdviceMapper.getById(id);
		if (byId==null){
			return test;
		}
		test.put("byId",byId);

		/* 通过当条医嘱的批号查询医嘱的内容*/
		List<HisDoctorsAdviceProject> hisDoctorsAdviceProjects = hisDoctorsAdviceProjectService.selectBatchNumber(byId.getBatchNumber());
		if (CollectionUtils.isNotEmpty(hisDoctorsAdviceProjects)){
			test.put("hisDoctorsAdviceProjects",hisDoctorsAdviceProjects);
		}

		/* 通过医嘱id 查询出当条医嘱执生成了多少条执行记录 */

		List<SubTaskDoctorsAdviceVo> subTaskDoctorsAdviceVos = taskSubTaskService.doctorsAdviceSubTask(byId.getDoctorsAdviceId());
		if (CollectionUtils.isNotEmpty(subTaskDoctorsAdviceVos)){
			test.put("subTaskDoctorsAdviceVo",subTaskDoctorsAdviceVos);
		}
		return test;
	}

	/**
	 * 通过医嘱雪花id查询数据
	 * @param doctorsAdviceId
	 * @return
	 */
	@Override
	public HisDoctorsAdviceVo getDoctorsAdviceId(String doctorsAdviceId) {
		return hisDoctorsAdviceMapper.getDoctorsAdviceId(doctorsAdviceId);
	}

	/**
	 * 通过医嘱id查询数据
	 * @param hisPatientId
	 * @return
	 */
	@Override
	public List<HisDoctorsAdvice> hisPatientId(String hisPatientId) {
		return hisDoctorsAdviceMapper.hisPatientId(hisPatientId);
	}

	/**
	 * /通过患者id 停止当前患者的所有医嘱
	 * @param patientId
	 * @return
	 */
	@Override
	public Boolean stopDoctorsAdvice(String patientId) {
		// 1、通过患者id查询到当前患者所有的医嘱
		List<HisDoctorsAdvice> hisDoctorsAdvices = hisDoctorsAdviceMapper.stopDoctorsAdvice(patientId);

		if (CollectionUtils.isNotEmpty(hisDoctorsAdvices)){
			// 2、将查询到的所有医嘱修改状态为  护士停止医嘱 3
			hisDoctorsAdvices.forEach(e->{
				e.setState("3");
			});
			return this.updateBatchById(hisDoctorsAdvices);
		}else{
			return true;
		}

	}

	/**
	 * 通过his患者id 查询当前患者没有完成的医嘱
	 * @param hisPaientId
	 * @return
	 */
	@Override
	public List<String> stopDoctorsAdviceHisPatientId(String hisPaientId) {

		List<HisDoctorsAdvice> hisDoctorsAdvices = hisDoctorsAdviceMapper.stopDoctorsAdviceHisPatientId(hisPaientId);
		List<String> list = new ArrayList<>();
		hisDoctorsAdvices.forEach(e->{
			if (e.getType()!=null&&e.getType().equals(HisDoctorsAdviceEnum.SHORT_TERM_DOCTORS_ADVICE.getCode())){//短期
				/* 通过关联医嘱id查询 短期医嘱是否已经生成过医嘱执行记录，如果生成过删除 集合中的数据 */
				List<HisDoctorsAdviceExt> HisDoctorsAdviceExt = hisDoctorsAdviceExtService.hisDoctorsAdviceId(e.getHisDoctorsAdviceId());
				if (CollectionUtils.isNotEmpty(HisDoctorsAdviceExt)){//  当通过短期医嘱查询出数据时,不做保存
				}else {//为空的时候将数据保存
					list.add(e.getHisDoctorsAdviceId());
				}
			}else{//长期医嘱，保存 his医嘱id
			list.add(e.getHisDoctorsAdviceId());
			}
		});

		return list;
	}
	/**
	 * 查询当前登录护士相关的医嘱内容
	 * @param hisDoctorsAdvice
	 * @return
	 */
	@Override
	public IPage LoginNurseDoctorsAdvice(Page page, HisDoctorsAdviceVo hisDoctorsAdvice) {
		IPage<HisDoctorsAdviceVo> listIPage = hisDoctorsAdviceMapper.LoginNurseDoctorsAdvice(page, hisDoctorsAdvice);
		List<HisDoctorsAdviceVo> list = listIPage.getRecords();

		if (CollectionUtils.isNotEmpty(list)){
				list.forEach(e->{
					/* 通过医嘱的批号查询相应的数据 */
					List<HisDoctorsAdviceProject> hisDoctorsAdviceProject = hisDoctorsAdviceProjectService.hisDoctorsAdviceId(e.getHisDoctorsAdviceId());
					e.setProjectList(hisDoctorsAdviceProject);
				});
		}
		return listIPage;
	}

	/**
	 * 通过his医嘱修改当前医嘱状态数据
	 * @param hisDoctorsAdvice
	 * @return
	 */
	@Override
	public Boolean updateHisDoctorsAdviceId(HisDoctorsAdvice hisDoctorsAdvice) {
		Integer integer = hisDoctorsAdviceMapper.updateHisDoctorsAdviceId(hisDoctorsAdvice);
		if (integer==1){
			return true;
		}else{
			return  false;
		}
	}
}
