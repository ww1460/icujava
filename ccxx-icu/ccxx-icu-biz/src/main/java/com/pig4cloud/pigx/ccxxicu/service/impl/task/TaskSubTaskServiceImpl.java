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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeResultBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TaskSubTaskBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Entrust;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.ArrangeResultVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.SubTaskDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskSubTaskVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DoctorsAdviceExtEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisDoctorsAdviceEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.ArrangeResultMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TaskSubTaskMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDataService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceExtService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.scenes.WatchOpenService;
import com.pig4cloud.pigx.ccxxicu.service.task.EntrustService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TasksService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务子表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:13:52
 */
@Service
public class TaskSubTaskServiceImpl extends ServiceImpl<TaskSubTaskMapper, TaskSubTask> implements TaskSubTaskService {


	/**
	 * 子任务
	 */
	@Autowired
	private TaskSubTaskMapper taskSubTaskMapper;

	/**
	 * 任务id
	 */
	@Autowired
	private TasksService taskService;

	/**
	 * 护士患者关联
	 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 项目结果
	 */
	@Autowired
	private ProjectRecordValueService projectRecordValueService;
;
	/**
	 * 护理记录
	 */
	@Autowired
	private NursingRecordService nursingRecordService;
	/**
	 * 医嘱内容
	 */
	@Autowired
	private HisDoctorsAdviceService hisDoctorsAdviceService;
	/**
	 * 医嘱扩展表
	 */
	@Autowired
	private HisDoctorsAdviceExtService hisDoctorsAdviceExtService;
	/**
	 * 医嘱内容
	 */
	@Autowired
	private HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;
	/**
	 * his数据
	 */
	@Autowired
	private HisDataService hisDataService;

	/**
	 * 任务委托
	 */
	@Autowired
	private EntrustService entrustService;
	/**
	 * 排班结果
	 */
	@Autowired
	private ArrangeResultMapper arrangeResultMapper;
	//护士
	@Autowired
	private NurseService nurseService;
	//任务模板
	@Autowired
	private TemplateService templateService;

	//腕表推送数据
	@Autowired
	private WatchOpenService watchOpenService;

	/**
	 * 根据主任务id删除数据
	 * @param id
	 * @return
	 */
	@Override
	public Boolean delete(String id) {
		//  利用传来的 主任务id 查询当前主任务有多少子任务进行删除
		List<TaskSubTask> taskSubTasks = this.mainTaskId(id);
		if (CollectionUtils.isNotEmpty(taskSubTasks)){
			taskSubTasks.forEach(e->{
				e.setDelFlag(1);
				e.setDelTime(LocalDateTime.now());
				e.setDelUserId(SecurityUtils.getUser().getId()+""); //删除人
			});
			boolean b = this.updateBatchById(taskSubTasks);
			if (b){
				return true;
			}else{
				return false;
			}
		}else{
			//  判断查询出来的语句为空的时候，就表示当前主任务已经没有子任务了 ，所以返回真。
			return false;
		}
	}
	/**
	 * 通过主任务id查询
	 * @param id
	 * @return
	 */
	@Override
	public List<TaskSubTask> mainTaskId(String id) {
		return this.mainTaskId(id);
	}

	/**
	 * 新增【不可调用】
	 * @param taskSubTask
	 * @return
	 */
	@Override
	public Boolean add(TaskSubTask taskSubTask) {
		/* 新增子任务的同时要在相关的主任务总条数加 +1
		* 1、通过子任务关联的主任务id ，查询相关的主任务
		* 2、新增
		* */
		Tasks byId = taskService.getById(taskSubTask.getMainTaskId());
		byId.setSubTaskNum(byId.getSubTaskNum()+1);

		boolean b = taskService.updateById(byId);
		if (!b){
			return false;
		}

		String nurseId = nursePatientCorrelationService.selectDutyNurseId(taskSubTask.getPatientId());//责任护士id
		LocalDateTime time = LocalDateTime.now();  //时间
		String creatotId = SecurityUtils.getUser().getId()+"";


		taskSubTask.setTaskSubTaskId(SnowFlake.getId()+"");//雪花 id
		taskSubTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
		taskSubTask.setDelFlag(0);//删除标识
		taskSubTask.setReceiveTime(LocalDateTime.now()); // 添加接收时间
		taskSubTask.setCreatorId(creatotId);//创建人
		taskSubTask.setCreateTime(time);//创建时间
		taskSubTask.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 任务状态为待执行
		taskSubTask.setDutyNurseId(nurseId);//责任护士
		taskSubTask.setReceiveTime(time); //创建时间
		taskSubTask.setProgressBar(0);//进度条
		/**
		 * 判断一下创建任务的是护士吗？
		 */
		if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {//护士
			//是的情况将接受护士变为创建护士
			taskSubTask.setReceptionNurseId(creatotId);

		}else{
			//反之为责任护士
			taskSubTask.setReceptionNurseId(nurseId);
		}
		return this.save(taskSubTask);
	}

	/**
	 * 通过子任务新增【同时新增主任务】
	 * @param taskSubTask
	 * @return
	 */
	@Override
	public Boolean addTask(TaskSubTask taskSubTask) {
		String taskId =SnowFlake.getId()+"";//主任务雪花id

		String nurseId = nursePatientCorrelationService.selectDutyNurseId(taskSubTask.getPatientId());//责任护士id
		LocalDateTime time = LocalDateTime.now();  //时间
		String creatotId = SecurityUtils.getUser().getId()+"";


		taskSubTask.setTaskSubTaskId(SnowFlake.getId()+"");//雪花 id
		taskSubTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
		taskSubTask.setDelFlag(0);//删除标识
		taskSubTask.setCreatorId(creatotId);//创建人
		taskSubTask.setCreateTime(time);//创建时间
		taskSubTask.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 任务状态为待执行
		taskSubTask.setDutyNurseId(nurseId);//责任护士
		taskSubTask.setReceiveTime(time); //创建时间
		taskSubTask.setProgressBar(0);//进度条
		taskSubTask.setMainTaskId(taskId);//主任务id
		/**
		 * 判断一下创建任务的是护士吗？
		 */
		if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {//护士
			//是的情况将接受护士变为创建护士
			taskSubTask.setReceptionNurseId(creatotId);

		}else{
			//反之为责任护士
			taskSubTask.setReceptionNurseId(nurseId);
		}

		Tasks tasks = new Tasks();
		BeanUtil.copyProperties(taskSubTask,tasks);
		tasks.setSubTaskNum(1);
		tasks.setTaskId(taskId);//雪花id
		tasks.setExecutingNum(0);
		boolean save = taskService.save(tasks);
		if (!save){
			return false;
		}
		boolean b = watchOpenService.addTaskToWatch(taskSubTask);
		if (!b){
			System.out.println("推送腕表失败！！！");
		}
		return this.save(taskSubTask);
	}

	/**
	 * 点击修改任务状态
	 * @param taskBo
	 * @return
	 */
	@Override
	public Boolean updateState(TaskSubTaskBo taskBo) {
		TaskSubTask task = new TaskSubTask();
		/*  将任务中需要的参数从 BO 类中取出*/
		BeanUtil.copyProperties(taskBo,task);

		// 当前任务为开始及结束任务时 【暂不使用】
		if (task.getState() == TaskEnum.TASK_STAET_END.getNumber()){
			task.setStartTime(LocalDateTime.now());//开始时间
				task.setEndTime(LocalDateTime.now());//结束 时间
				task.setState(TaskEnum.COMPLETE.getNumber());//修改为完成
				task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");//完成护士id
				task.setProgressBar(100);
				task.setReceptionNurseId(SecurityUtils.getUser().getId()+"");//接收护士
				task.setReceiveTime(LocalDateTime.now());//接收时间
				Boolean aBoolean = this.taskResult(taskBo);
			if (!aBoolean){
				throw new ApiException(ResponseCode.TASK_ADD_SUB_FAIL.getCode(),ResponseCode.TASK_ADD_SUB_FAIL.getMessage());
			}
			/* 当前任务为待执行时 */
		}if(task.getState() ==TaskEnum.TO_BE_IMPLEMENTED.getNumber()){
			task.setStartTime(LocalDateTime.now());//开始时间
			task.setEndTime(LocalDateTime.now());//结束 时间
			task.setState(TaskEnum.COMPLETE.getNumber());//修改为完成
			task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");//完成护士id
			task.setProgressBar(100);
			task.setReceptionNurseId(SecurityUtils.getUser().getId()+"");//接收护士
			task.setReceiveTime(LocalDateTime.now());//接收时间
			//为执行形 任务时，保存任务结果
			Boolean aBoolean = this.taskResult(taskBo);
				if (!aBoolean){
					throw new ApiException(ResponseCode.TASK_ADD_SUB_FAIL.getCode(),ResponseCode.TASK_ADD_SUB_FAIL.getMessage());
				}

			// 当前任务为已接收任务时
		}if (task.getState() ==TaskEnum.TASK_RECEIVED.getNumber()){
			task.setState(TaskEnum.COMPLETE.getNumber());//修改为完成
			task.setEndTime(LocalDateTime.now());//接收时间
			task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");//完成护士id
			task.setProgressBar(100);

			//  任务状态为结束时不操作
		}if (task.getState() ==TaskEnum.COMPLETE.getNumber()){


			//当前台传来 4   停止时
		}if (task.getState() ==TaskEnum.SUSPEND.getNumber()){
			/* 前台传来为4的时候，判断一下当条数据之前状态，之前状态为4的时候，则把他修改为执行 1 反正变为 4 */
			Integer state1 = this.getById(task.getId()).getState();
			if (state1==4&&task.getState()==4){
				/* 把他变为执行*/
				String nurseId = nursePatientCorrelationService.selectDutyNurseId(task.getPatientId());
				task.setDutyNurseId(nurseId);//责任护士
				task.setReceptionNurseId(nurseId); //接收护士
				task.setReceiveTime(LocalDateTime.now());//接收时间
				task.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 已接收
			}

		}
		return this.updateById(task);
	}

	/**
	 * 任务结果需要做的处理
	 * @param
	 * @return
	 */
	@Transactional
	@Override
	public Boolean taskResult(TaskSubTaskBo taskBo) {

		// 1、当子任务结束时，用子任务关联主任任务的id查询数据,
		Tasks tasks = taskService.taskId(taskBo.getMainTaskId());
		if (tasks ==null){
			return false;
		}
		/* 给主任务的执行次数 +1  */
  		tasks.setExecutingNum(tasks.getExecutingNum() + 1);
			/* 当数据不为空时，判断查询出的主任务的总次数是否等于执行次数 */
			if (tasks.getSubTaskNum() == tasks.getExecutingNum()) {//当主任务判断完成时
					/*  如果总次数 等于执行次数+1 【之前的执行次数添加上本次的执行次数】
			那么在结束子任务的同时 将主任务结束
			*/tasks.setState(TaskEnum.COMPLETE.getNumber()); //完成状态

				/* 当主任务结束后查询主任务的来源，判断主任务的来源 是否为医嘱 */
				if (tasks.getTaskType() == TaskEnum.DOCTORS_ADVICE.getNumber()) {
					/* 满足条件时为医嘱任务，通过主任务的医嘱id，去查询医嘱的信息，判断医嘱的信息，当医嘱为长期时【触发his的数据，更新医嘱数据】，当为短期医嘱时，完成当前医嘱*/
					/* 通过医嘱的id去医嘱查询表中查询，当条任务关联的是那条医嘱 */
					HisDoctorsAdviceExt hisDoctorsAdviceExt = hisDoctorsAdviceExtService.doctorsAdviceExt(tasks.getTaskTypeId());
					if (hisDoctorsAdviceExt==null){	//当没有医嘱执行记录时返回
						throw new ApiException(ResponseCode.DOCTORS_ADVICE_EXT_IS_NULL.getCode(),ResponseCode.DOCTORS_ADVICE_EXT_IS_NULL.getMessage());
					}
					HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceService.hisDoctorsAdviceId(hisDoctorsAdviceExt.getHisDoctorsAdviceId());
					if (hisDoctorsAdviceVo ==null){ //当医嘱为空时
						throw new ApiException(ResponseCode.DOCTORS_ADVICE_PROJECT.getCode(),ResponseCode.DOCTORS_ADVICE_PROJECT.getCode());
					}
					// 判断当前医嘱是否为短期任务、和当条医嘱的的his传输是否是否为未传输数据
					if (hisDoctorsAdviceVo.getType()== HisDoctorsAdviceEnum.SHORT_TERM_DOCTORS_ADVICE.getCode()&&hisDoctorsAdviceVo.getHisNoticeFlag()==HisDoctorsAdviceEnum.TYPE_ONE.getCode()){

//						//触发接口
//						Thread thread = new Thread(){
//							@Override
//							public void run() {
//								Boolean b =true;
//								int i =0;
//
//								while (b&&i>5){
//									Boolean aBoolean = hisDataService.updateDoctorsAdviceExt(hisDoctorsAdviceExt);
//									if (aBoolean){
//										i=5;
//										b =false;
//									}else{
//										i++;
//									}
//									try {
//										//当前线程睡眠一秒
//										Thread.sleep(3000);
//									}catch (Exception e){
//
//									}
//								}
//							}
//						};
//						thread.start();
						HisDoctorsAdvice hisDoctorsAdvice = new HisDoctorsAdvice();
						BeanUtil.copyProperties(hisDoctorsAdviceVo,hisDoctorsAdvice);
						hisDoctorsAdvice.setHisNoticeFlag((Integer) HisDoctorsAdviceEnum.TYPE_ONE.getCode());//修改状态
						boolean b = hisDoctorsAdviceService.updateById(hisDoctorsAdvice);
						if (!b){
							throw new ApiException(ResponseCode.DOCTORS_ADVICE_UPDATE.getCode(),ResponseCode.DOCTORS_ADVICE_UPDATE.getMessage());
						}
					}

					// 将当条医嘱的执行状态修改为完成
					hisDoctorsAdviceExt.setExecuteType(DoctorsAdviceExtEnum.COMPLETE.getCode());
					hisDoctorsAdviceExt.setExecuteNurse(SecurityUtils.getUser().getId()+"");//用户id
					hisDoctorsAdviceExt.setExecuteTime(LocalDateTime.now());//执行时间
					boolean b = hisDoctorsAdviceExtService.updateById(hisDoctorsAdviceExt);
					if (!b){
						return false;
					}

				}
			}

				LocalDateTime time = LocalDateTime.now();
				tasks.setCompletedNurseId(SecurityUtils.getUser().getId() + "");//完成护士id
				tasks.setStartTime(time); //开始时间
				tasks.setEndTime(time); //结束时间
				tasks.setProgressBar(100);// 进度条


			/*  最后修改 主任务*/
					boolean b = taskService.updateById(tasks);
					if (!b) {
						return false;
					}



		/*  处理任务结果*/
		TaskSubTask taskSubTask= new TaskSubTask();
		BeanUtil.copyProperties(taskBo,taskSubTask);

		boolean b1 = nursingRecordService.taskAdd(taskSubTask);
		if (b1==false){
			return false;
		}
			return true;
		}

	/**
	 * 分页查询任务
	 * @param page
	 * @param taskSubTaskVo
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, TaskSubTaskVo taskSubTaskVo) {

		return taskSubTaskMapper.selectPaging(page,taskSubTaskVo);
	}

	/**
	 * 任务不分页
	 * @param taskSubTask
	 * @return
	 */
	@Override
	public List<TaskSubTaskVo> taskList(TaskSubTaskVo taskSubTask) {
		return taskSubTaskMapper.taskList(taskSubTask);
	}

	/**
	 * 通过判断当条任务为项目任务，查询当条项目是否结果
	 * @param taskSubTask
	 * @return
	 */
	@Override
	public List<ProjectValueVo> selectProjectValue(TaskSubTask taskSubTask) {
		/*判断当前任务的来源，判断当前任务中是否有来源的信息*/

			Project project = new Project();
			/* 当任务为项目时 */
			if (taskSubTask.getTaskType()==TaskEnum.PROJECT.getNumber()){ //项目
				project.setProjectId(taskSubTask.getTaskTypeId());
			}if(taskSubTask.getTaskType()==TaskEnum.TEMPLATE_PROJECT.getNumber()){ //模板  项目
				String[] split = taskSubTask.getTaskTypeId().split("-");
				project.setProjectId(split[split.length-1]);
			}if (taskSubTask.getTaskType()==TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()){//医嘱项目
				String[] split = taskSubTask.getTaskTypeId().split("-");
				project.setProjectId(split[split.length-1]);
			}if (taskSubTask.getTaskType()==TaskEnum.FAST_PROJECT.getNumber()){//快捷项目
			project.setProjectId(taskSubTask.getTaskTypeId());
		}
			if (project.getProjectId()==null){
				return null;
			}else{
				List<ProjectValueVo> projectValueVos = projectRecordValueService.selectProjectValue(project);
				/* 判断通过项目查询固定结果不为空的时候 */
				if (CollectionUtils.isNotEmpty(projectValueVos)){
					return projectValueVos;
				}else{
					return null;
				}
			}

	}

	/**
	 * 任务
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> getId(Integer id) {
		Map<String,Object> test = new HashMap<>();

		TaskSubTaskVo taskSubTaskVo = taskSubTaskMapper.getId(id);
		if (taskSubTaskVo!=null){
			test.put("taskSubTaskVo",taskSubTaskVo);
		}
		if (taskSubTaskVo.getTaskTypeId() !=null){

		/*  通过子任务中的执行医嘱id，查询真正医嘱数据，首先判断当前任务是否来源于医嘱*/
		if (taskSubTaskVo.getTaskType()==TaskEnum.DOCTORS_ADVICE_PROJECT.getNumber()){// 医嘱项目
			String[] split = taskSubTaskVo.getTaskTypeId().split("-");
			HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceProjectService.doctorsAdviceProjectId(split[1]);
			test.put("hisDoctorsAdviceVo",hisDoctorsAdviceVo);
			return test;

		}if (taskSubTaskVo.getTaskType()==TaskEnum.DOCTORS_ADVICE.getNumber()){//来源于医嘱

			HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceProjectService.doctorsAdviceProjectId(taskSubTaskVo.getTaskTypeId());
			test.put("hisDoctorsAdviceVo",hisDoctorsAdviceVo);
			return test;

		}if (taskSubTaskVo.getTaskType()==TaskEnum.DOCTORS_ADVICE_BUILD_BY_ONESELF.getNumber()){//医嘱自建
			String[] split = taskSubTaskVo.getTaskTypeId().split("-");
			HisDoctorsAdviceVo hisDoctorsAdviceVo = hisDoctorsAdviceProjectService.doctorsAdviceProjectId(split[split.length - 1]);
			test.put("hisDoctorsAdviceVo",hisDoctorsAdviceVo);
			return test;
		}
		}
		return test;
	}

	/**
	 * 通过患者id查询当前患者未完成的任务【交接班】
	 * @param id
	 * @return
	 */
	@Override
	public List<TaskSubTask> stopTask(String id) {
		return taskSubTaskMapper.stopTask(id);
	}

	/**
	 * 通过子任务的雪花id查询子任务的数据
	 * @param taskSubTaskId
	 * @return
	 */
	@Override
	public TaskSubTask taskSubTaskId(String taskSubTaskId) {
		return taskSubTaskMapper.taskSubTaskId(taskSubTaskId);
	}

	/**
	 * 护理模板新增
	 * @param subTask
	 * @return
	 */
	@Override
	public Boolean nursingScheme(TaskSubTask subTask) {

		//任务中的来源id查询任务模板的数据
		if (StringUtils.isEmpty(subTask.getTaskTypeId())){//当任务模板id为空的时候
			throw new ApiException(ResponseCode.TASK_NOT_TYPE_ID.getCode(),ResponseCode.TASK_NOT_TYPE_ID.getMessage());
		}

		Template template = templateService.getTemplateId(subTask.getTaskTypeId());
		TemplateBo templateBo = new TemplateBo();
		templateBo.setStartTime(subTask.getPreStartTime());//开始时间
		templateBo.setEndTime(subTask.getPreEndTime());//结束时间
		templateBo.setPatientId(subTask.getPatientId());
		BeanUtil.copyProperties(template,templateBo);

		Boolean aBoolean = templateService.templateAddTask(templateBo);
		if (aBoolean){
			return true;
		}else{
			return false;
		}

		//创建主任务，和回填子任务数据
//		Tasks tasks = new Tasks();
//		String taskTd = SnowFlake.getId()+""; //主任务雪花id
//		tasks.setTaskId(taskTd);
//		tasks.setTaskName("预定护理计划");
//		tasks.setTaskDescribe("对当前的患者预定护理计划");
//		String nurseId = nursePatientCorrelationService.selectDutyNurseId(list.get(0).getPatientId());//责任护士id
//		list.forEach(e->{
//			LocalDateTime time = LocalDateTime.now();  //时间
//			String creatotId = SecurityUtils.getUser().getId()+"";
//			e.setTaskSubTaskId(SnowFlake.getId()+"");//雪花 id
//			e.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室id
//			e.setDelFlag(0);//删除标识
//			e.setReceiveTime(LocalDateTime.now()); // 添加接收时间
//			e.setCreatorId(creatotId);//创建人
//			e.setCreateTime(time);//创建时间
//			e.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 任务状态为待执行
//			e.setDutyNurseId(nurseId);//责任护士
//			e.setReceiveTime(time); //创建时间
//			e.setProgressBar(0);//进度条
//			e.setMainTaskId(taskTd);
//
//			/**
//			 * 判断一下创建任务的是护士吗？
//			 */
//			if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {//护士
//				//是的情况将接受护士变为创建护士
//				e.setReceptionNurseId(creatotId);
//
//			}else{
//				//反之为责任护士
//				e.setReceptionNurseId(nurseId);
//			}
//		});
//
//
//			boolean b = this.saveBatch(list);
//			if (!b){
//			return false;
//			}
//			BeanUtil.copyProperties(list.get(0),tasks);
//			tasks.setSubTaskNum(list.size()+1);
//			tasks.setExecutingNum(0);

//			boolean save = taskService.save(tasks);
//			if (save){
//				return true;
//			}else{
//				return false;
//			}


	}

	/**
	 * 【出科时使用，利用患者id查询还在在出科时未完成的任务将其完成】
	 * @param patientId
	 * @return
	 */
	@Transactional
	@Override
	public Boolean shiftsEndTask(String patientId) {
		/*通过患者id查询当前为 未完成的子任务*/
		List<TaskSubTask> taskSubTasks = this.stopTask(patientId);

		for (int i =0;i<taskSubTasks.size();i++){
			/*将未完成的任务状态修改为  出科*/
			taskSubTasks.get(i).setState(TaskEnum.LEAVE_HOSPITAL.getNumber());
			taskSubTasks.get(i).setEndTime(LocalDateTime.now());
			taskSubTasks.get(i).setCompletedNurseId(SecurityUtils.getUser().getId()+"");
			Tasks task = taskService.taskId(taskSubTasks.get(i).getMainTaskId());
			task.setState(TaskEnum.LEAVE_HOSPITAL.getNumber());
			task.setEndTime(LocalDateTime.now());
			task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");
			boolean b = taskService.updateById(task);
			if (!b){
				return false;
			}
		}

		boolean b = this.updateBatchById(taskSubTasks);
	if (b){
		return true;
	}else{
		return false;
	}
	}



	/**
	 * 通过医嘱数据,查询当前医嘱所产生的任务
	 * @param hisDoctorsAdviceId
	 * @return
	 */
	@Override
	public List<SubTaskDoctorsAdviceVo> doctorsAdviceSubTask(String hisDoctorsAdviceId) {
		return taskSubTaskMapper.doctorsAdviceSubTask(hisDoctorsAdviceId);
	}


	/**
	 * 任务委托
	 * @param task
	 * @return
	 */
	@Override
	public Boolean taskEntrust(TaskSubTask task) {
		/* 通过传来任务将任务的接收人修改为护士选择的护士，通过将当条记录保存在任务委托记录表中*/

		Entrust entrust = new Entrust();
		/*  发布人为当前登录的护士 */
		entrust.setOriginatorId(SecurityUtils.getUser().getId()+"");
		/* 接收人为接收护士 */
		entrust.setRecipientId(task.getReceptionNurseId());
		/* 雪花id */
		entrust.setTaskEntrustId(SnowFlake.getId()+"");
		/*发生时间 */
		entrust.setTime(LocalDateTime.now());
		/* 修改接收时间 */
		task.setReceiveTime(LocalDateTime.now());
		int i = taskSubTaskMapper.updateById(task);
		if (i!=1){
			return false;
		}
		boolean save = entrustService.save(entrust);
		if (!save){
			return false;
		}
		return true;
	}

	/**
	 * 任务拆分
	 * @param list
	 * @return
	 */
	@Override
	public R taskSubTaskSplit(List<TaskSubTask> list) {
			/*数据不用空时同时数据不能多*/
		if (CollectionUtils.isNotEmpty(list)&&list.size()==2){
			TaskSubTask  taskSubTaskSplit= new TaskSubTask(); //拆分数据
			TaskSubTask  taskSubTask = new TaskSubTask(); //原数据

			// 拆分后的数据
			int split = 0;
			for (int i =0;i<list.size();i++){

				if (list.get(i).getId()==null&&list.get(i).getTaskSubTaskId() ==null){//拆分的数据
					taskSubTaskSplit = list.get(i);
				}if (list.get(i).getId()!=null&&list.get(i).getTaskSubTaskId() !=null){ //没有拆分的类型
					taskSubTask = list.get(i);
				}
			}
			/*原数据的 值取出*/
			if (taskSubTaskSplit.getDosage()!=null){
			/*为空时0值不做处理*/
				split = taskSubTask.getDosage() - taskSubTaskSplit.getDosage();
				taskSubTask.setDosage(split);
			}



			taskSubTaskSplit.setTaskSubTaskId(SnowFlake.getId()+"");
			taskSubTaskSplit.setCreatorId(SecurityUtils.getUser().getId()+"");//创建人
			taskSubTaskSplit.setCreateTime(LocalDateTime.now());

			boolean b = this.updateById(taskSubTask);
			if (b==false){
				return R.failed("拆分主任务失败！！！！");
			}
			boolean save = this.save(taskSubTaskSplit);
			if (b){
				return R.ok("拆分成功！！！！");
			}else {
				return R.failed("子任务拆分失败！！！");
			}
		}else{
			return R.failed("任务拆分数据有误，请联系管理员");
		}

	}


	/**
	 * 任务委托护士查询
	 * @return
	 */
	@Override
	public R taskEntrustNurse() {
		/* 通过排班查询当前登录的护士是哪个班次 */
		Map<String,Object> text = new HashMap<>();
		ArrangeResult arrangeResult = new ArrangeResult();
		arrangeResult.setNurseId(SecurityUtils.getUser().getId()+"");//护士id
		arrangeResult.setDateTime(LocalDateTime.now());
		arrangeResult.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		/* 通过护士id   当前时间   科室  查询当前护士是什么班次 */
		List<ArrangeResult> byDate = arrangeResultMapper.getByDate(arrangeResult);
		/* 当前时间内当前护士只能有一个班次*/
		if (byDate.size()==1){
			ArrangeResultBo arrangeResultBo = new ArrangeResultBo();
			arrangeResultBo.setDateTime(LocalDateTime.now());
			arrangeResultBo.setWorkShift(byDate.get(0).getWorkShift());
			arrangeResultBo.setScope(1);  //  查询当天的时间
			List<ArrangeResultVo> shiftData = arrangeResultMapper.getShiftData(arrangeResultBo);
			/* 当查询到当前班次的护士不为空的时候然后*/
			if (CollectionUtils.isNotEmpty(shiftData)){
				text.put("byDate",shiftData);
			}

		}else{
			R.failed(null,"查询当前护士班次错误 ！！！");
		}
		/* 通过护士科室查询  当前科室有多少护士 */
		List<Nurse> nurses = nurseService.selectByDept(SecurityUtils.getUser().getDeptId() + "");
		if(CollectionUtils.isNotEmpty(nurses)){
			text.put("nurses",nurses);
		}
		return R.ok(text);
	}

	/**
	 * 超时任务
	 * @return
	 */
	@Override
	public List<TaskSubTask> overtimeTask() {
		return taskSubTaskMapper.overtimeTask();
	}
}
