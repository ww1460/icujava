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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeResultBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Entrust;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.ArrangeResultVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectValueVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisDoctorsAdviceEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.ArrangeResultMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TaskMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.nursing.IntakeOutputRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordValueService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsTaskRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.EntrustService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务表
 *
 * @author pigx code generator
 * @date 2019-08-13 16:30:28
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

	@Autowired
	private TaskMapper taskMapper;
	/**
	 * 护士
	 */
	@Autowired
	private NurseService nurseService;
	/**
	 * 护士患者关联
	 */
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 医嘱
	 */
	@Autowired
	private HisDoctorsAdviceService hisDoctorsAdviceService;
	/**
	 * 医嘱项目表
	 */
	@Autowired
	private HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	/**
	 * 任务委托
	 */
	@Autowired
	private EntrustService entrustService;

	/**
	 * 项目结果
	 */
	@Autowired
	private ProjectRecordValueService projectRecordValueService;

	/**
	 * 项目结果表
	 */
	@Autowired
	private ProjectRecordService projectRecordService;

	/**
	 * 排班结果
	 */
	@Autowired
	private ArrangeResultMapper arrangeResultMapper;
	/**
	 * 交接班任务记录描述
	 */
	@Autowired
	private ChangeShiftsTaskRecordService changeShiftsTaskRecordService;
	/**
	 * 任务模板
	 */
	@Autowired
	private TemplateService templateService;

	//项目表
	@Autowired
	private ProjectService projectService;

	/* 出入量 */
	@Autowired
	private IntakeOutputRecordService intakeOutputRecordService;
	/**
	 * 护理记录
	 */
	@Autowired
	private NursingRecordService nursingRecordService;








	/**
	 * 分页查询数据
	 * @param page
	 * @param task
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page, TaskVo task) {

		return taskMapper.selectPaging(page,task);
	}


	/**
	 * 任务全查
	 * @param task
	 * @return
	 */
	@Override
	public List<TaskVo> selectAll(TaskVo task) {
		return taskMapper.selectAll(task);
	}

	/**
	 * id查询
	 * @param id
	 * @return
	 */
	@Override
	public TaskVo selectId(Integer id) {
		return taskMapper.selectId(id);
	}

	/**
	 * 雪花id查询
	 * @param taskId
	 * @return
	 */
	@Override
	public TaskVo getTaskId(String taskId) {
		return taskMapper.getTaskId(taskId);
	}

	/**
	 * id查询数据 【任务详情】
	 * @param taskId
	 * @return
	 */
	@Override
	public R taskId(String taskId) {
		Map<String,Object> test = new HashMap<>();
		if (taskId !=null || !taskId.equals("")){
		TaskVo task = taskMapper.getTaskId(taskId);
		if (task !=null){
			test.put("task",task);
		}else {
			test.put("task",task);
		}
		// 创建一个交接班任务描述对象
		ChangeShiftsTaskRecord shiftsTaskRecord =  new ChangeShiftsTaskRecord();
		/*将任务id 放进去 查询 */
		shiftsTaskRecord.setTaskId(taskId);
		List<ChangeShiftsTaskRecord> changeShiftsTaskRecord = changeShiftsTaskRecordService.selectAll(shiftsTaskRecord);
		if (CollectionUtils.isNotEmpty(changeShiftsTaskRecord)){
			test.put("shiftsTask",changeShiftsTaskRecord);
		}else{
			test.put("shiftsTask",changeShiftsTaskRecord);
		}
			return R.ok(test);
		}else{
			 return R.failed(null,"任务id有误！！");
		}

	}

	/**
	 * 超时任务
	 * @return
	 */
	@Override
	public List<Task> overtimeTask() {
		return taskMapper.overtimeTask();
	}

	/**
	 * 多条任务新增
	 * @param list
	 * @return
	 */
	@Override
	public R inserts(List<Task> list) {
		/*判断多条数据是否为空  CollectionUtils*/
		if (CollectionUtils.isEmpty(list)){
			return R.failed(null,"没有任务");
		}
		/* 查询出责任护士id */
		String nurseId = nursePatientCorrelationService.selectDutyNurseId(list.get(0).getPatientId());
		if(nurseId==null){
			return R.failed(null,"当前护士没有责任护士，无法添加任务，请为当前患者添加责任护士");
		}
		list.forEach(e->{
			e.setTaskId(SnowFlake.getId()+"");//雪花
			e.setCreatorId(SecurityUtils.getUser().getId()+"");//创建人
			e.setCreateTime(LocalDateTime.now());//创建时间
			e.setDutyNurseId(nurseId);//责任护士
			e.setReceptionNurseId(nurseId); //接收护士
			e.setReceiveTime(LocalDateTime.now());//接收时间
			e.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
			e.setState(TaskEnum.TO_BE_IMPLEMENTED.getNumber()); // 已接收
		});
		return R.ok(this.saveBatch(list));
	}
	/**
	 * 点击修改任务状态
	 * @param taskBo
	 * @return
	 */
	@Override
	public R updateState(TaskBo taskBo) {
		Task task = new Task();
		/*  将任务中需要的参数从 BO 类中取出*/
		task.setId(taskBo.getId());
		task.setTaskId(taskBo.getTaskId()); // 任务雪花id
		task.setTaskName(taskBo.getTaskName());
		task.setState(taskBo.getState());
		task.setTaskId(taskBo.getTaskId());
		task.setTaskDescribe(taskBo.getTaskDescribe());
		task.setResult(taskBo.getResult());
		task.setSourceItems(taskBo.getSourceItems());
		task.setSourceItemsId(taskBo.getSourceItemsId());
		switch (task.getState()){
			case -1:  //   传-1的时候默认为开始及结束任务
				task.setStartTime(LocalDateTime.now());//开始时间
				task.setState(TaskEnum.COMPLETE.getNumber());//修改为完成
				task.setEndTime(LocalDateTime.now());//结束 时间
				task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");//完成护士id
				task.setProgressBar(100);
				task.setReceptionNurseId(SecurityUtils.getUser().getId()+"");//接收护士
				task.setReceiveTime(LocalDateTime.now());//接收时间
				Boolean aBoolean1 = this.taskResult(taskBo);
				if (!aBoolean1){
					return  R.failed(null,"任务结果添加失败！！！");
				}
				break;
			case 1:

			/*  执行中的任务可以直接变成完成，不走执行中这个状态了*/

				task.setState(TaskEnum.COMPLETE.getNumber());//修改为完成
				task.setEndTime(LocalDateTime.now());//接收时间
				task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");//完成护士id
				task.setProgressBar(100);
				task.setStartTime(LocalDateTime.now());//开始时间
				Boolean aBoolean2 = this.taskResult(taskBo);
				if (!aBoolean2){
					return  R.failed(null,"任务结果添加失败！！！");
				}



	/* */
      //	task.setState(TaskEnum.EXECUTION_IN_PROGRESS.getNumber());//修改为执行中
      //	task.setStartTime(LocalDateTime.now());//开始时间
	  //	task.setProgressBar(1);//进度条
 	  //

				break;
			case 2:  //当点击二时 证明任务要结果
				task.setState(TaskEnum.COMPLETE.getNumber());//修改为完成
				task.setEndTime(LocalDateTime.now());//接收时间
				task.setCompletedNurseId(SecurityUtils.getUser().getId()+"");//完成护士id
				task.setProgressBar(100);
				Boolean aBoolean = this.taskResult(taskBo);
				if (!aBoolean){
					return  R.failed(null,"任务结果添加失败！！！");
				}
				break;
			case 3:
				break;
			case 4:
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
				break;
		}
		return R.ok(this.updateById(task));
	}

	/**
	 * 点击任务模板接收时
	 * @param taskBo
	 * @return
	 */
	@Override
	public R insertTemplate(TaskBo taskBo) {
		/*  当传来的任务状态为3 的时候   任务即为开始即结束任务 */

		/* 项目来源id（任务模板中的id） */

		taskBo.setSourceItems(TaskEnum.TASK_TEMPLATE.getNumber());//任务来源
		/* 任务状态   3为完成 */
		taskBo.setState(TaskEnum.COMPLETE.getNumber());
		taskBo.setEmergency(TaskEnum.TASK_COMMONLY.getNumber());//一般任务
		taskBo.setTaskId(SnowFlake.getId()+"");
		taskBo.setCreateTime(LocalDateTime.now()); //创建时间
		taskBo.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		taskBo.setCreatorId(SecurityUtils.getUser().getId()+"");  // 创建人
		taskBo.setCreatorId(SecurityUtils.getUser().getId()+"");// 如果是护士自己创建的任务、任务创建人就是当前登录的护士本人
		taskBo.setReceptionNurseId(SecurityUtils.getUser().getId()+"");//  接收护士
		taskBo.setDutyNurseId(SecurityUtils.getUser().getId()+""); // 责任护士
		taskBo.setCompletedNurseId(SecurityUtils.getUser().getId()+"");// 完成护士
		taskBo.setReceiveTime(LocalDateTime.now());// 接收任务时间
		taskBo.setStartTime(LocalDateTime.now());// 任务开始时间
		taskBo.setEndTime(LocalDateTime.now());// 任务结束时间
		taskBo.setPreStartTime(LocalDateTime.now());//  预开始时间
		taskBo.setPreEndTime(LocalDateTime.now()); // 预开始时间
		taskBo.setProgressBar(100); // 任务进度
		//  新建任务新增用
		Task task = new Task();
		BeanUtil.copyProperties(taskBo,task);
		boolean save = this.save(task);
		if (!save){
			return R.failed(null,"生成任务失败！！！请联系管理员");
		}
		/* 新增成功后进行数据回填 */
		return R.ok(this.taskResult(taskBo));

	}

	/**
	 * 添加任务
	 * @param task
	 * @return
	 */
	@Override
	public Boolean taskAdd(Task task) {
		task.setTaskId(SnowFlake.getId()+""); //雪花
		return this.save(task);
	}

	/**
	 * 通过项目id查询对应的数据 （医嘱）
	 * @param sourceItemsId
	 * @return
	 */
	@Override
	public List<Task> getsSourceItemsId(String sourceItemsId) {
		return taskMapper.getsSourceItemsId(sourceItemsId);
	}

	/**
	 * 任务委托
	 * @param task
	 * @return
	 */
	@Override
	public Boolean taskEntrust(Task task) {
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
		int i = taskMapper.updateById(task);
		if (i!=1){
			return false;
		}
		boolean save = entrustService.save(entrust);
		if (!save){
			return false;
		}
		return true;
	}








	/***
	 * 通过判断当条任务为项目任务，查询当条项目是否结果
	 * @param task
	 * @return
	 */
	@Override
	public List<ProjectValueVo> selectProjectValue(Task task) {;
		if ((task.getSourceItems()==TaskEnum.TASK_PROJECT.getNumber()|| task.getSourceItems()==TaskEnum.TASK_TEMPLATE.getNumber()) && task.getSourceItemsId()!=null){

			Project project = new Project();

			/* 当条任务的来源为任务模板时单独处理 */
			if(task.getSourceItems() ==TaskEnum.TASK_TEMPLATE.getNumber()){
				/*通过数据的来源id查询到当条任务模板的数据 */
				Template templateId = templateService.getTemplateId(task.getSourceItemsId());
				/* 判断查询查询出的任务模板数据不为空时    任务模板中的来源id不为 null   和为空的字符串时做处理  */
				if(templateId !=null&&(templateId.getSourceId() !=null &&!templateId.getSourceId().equals(""))){
					/*  将任务模板中的来源id作为数据查询*/
					project.setProjectId(templateId.getSourceId());
				}
			}else {
				/* 将任务的来源id作为数据查询*/
				project.setProjectId(task.getSourceItemsId());
			}

				List<ProjectValueVo> projectValueVos = projectRecordValueService.selectProjectValue(project);
				/* 判断通过项目查询固定结果不为空的时候 */
				if (CollectionUtils.isNotEmpty(projectValueVos)){
					return projectValueVos;
				}

		}
		return null;
	}


	/**
	 * 通过患者id 查询相关未完成的任务，停止任务
	 * @param patientId
	 * @return
	 */
	@Override
	public Boolean stopTask(String patientId) {
		List<Task> tasks = taskMapper.stopTask(patientId);
		if (CollectionUtils.isNotEmpty(tasks)){
			tasks.forEach(e->{
				e.setState(TaskEnum.LEAVE_HOSPITAL.getNumber());//将任务状态变为患者出院
				e.setResult("患者出科");
			});
			return this.updateBatchById(tasks);
		}else{
			return true;
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










	/* 将任务结果添加入护理记录单中*/
	@Override
	public Boolean taskAddNursingRecord(TaskBo taskBo) {

		NursingRecord nursingRecord = new NursingRecord();

		PigxUser user = SecurityUtils.getUser();
		LocalDateTime now = LocalDateTime.now();

		String id = SnowFlake.getId()+""; //新建雪花id
		taskBo.setNursingRecordId(id);//将护理记录单的雪花id回填到Bo类中 用后后期可能 用到回填来源id

		nursingRecord.setNursingRecordId(id);//雪花id
		nursingRecord.setCreateTime(now);
		nursingRecord.setCreateUserId(user.getId() + "");
		nursingRecord.setDelFlag(0);
		nursingRecord.setNursingRecordId(SnowFlake.getId() + "");
		nursingRecord.setDeptId(user.getDeptId() + "");
		nursingRecord.setPatientId(taskBo.getPatientId());// 患者id
		/* 任务描述   空的话为 无    */

		if (taskBo.getResult() ==null||taskBo.getResult().equals("")){
			taskBo.setResult("无");
		}
		nursingRecord.setRecordContent(taskBo.getTaskName()+taskBo.getResult());
		nursingRecord.setSource(DataSourceEnum.TASK.getCode());//来源
		return nursingRecordService.save(nursingRecord);
	}









	/**
	 *  将任务结果添加入出入量中
	 * @param taskBo
	 * @return
	 */
	@Override
	public Boolean taskAddIntakeOutput(TaskBo taskBo) {


		/*  查询一下当前任务关联的医嘱是长期还是短期，如果是短期则结束当前医嘱，如果是长期则修改长期医嘱的执行状态*/
		HisDoctorsAdvice batchNumber = hisDoctorsAdviceService.getBatchNumber(taskBo.getSourceItemsId());
		/* 判断一下当前医嘱是长期还是短期 */
		if (batchNumber.getType().equals(TaskEnum.LONG_TERM_DOCTORS_ADVICE.getNumber()+"")){
			/* 为长期时医嘱时,为医嘱添加一次执行次数*/
			Integer i = batchNumber.getFrequencyCount();
			batchNumber.setFrequencyCount(++i);
			/* 修改当条医嘱 */
			boolean b = hisDoctorsAdviceService.updateById(batchNumber);
			if (!b){
				return false;
			}

		}else{
			/* 如果是短期任务的话 完成当前医嘱 */
			batchNumber.setState("1");
			boolean b = hisDoctorsAdviceService.updateById(batchNumber);
			if (!b){
				return false;
			}
		}

		/* 通过医嘱批号，查询当条医嘱的药品内容 */
		List<HisDoctorsAdviceProject> lsit = hisDoctorsAdviceProjectService.selectBatchNumber(taskBo.getSourceItemsId());
		/* 数据不为空的时候做处理 */
		if (CollectionUtils.isNotEmpty(lsit)){
			//  创建一个出入量
			IntakeOutputRecord intakeOutputRecord = new IntakeOutputRecord();
			/* 出入量的值   */
			Integer intakeOutputValue =0;
			String valueDescription="";

			/* 进行循环判断,将出入量的值 和描述取出 */

			for (int i =0;i < lsit.size();i++){
				/*判断一下每条医嘱的剂量是否为升，为升的时候将其转换为毫升*/
				if(lsit.get(i).getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_L.getCode())){
					/* 当满足时，将单条医嘱的内容及  值  单位取出 */
					intakeOutputValue = intakeOutputValue+ Integer.valueOf(lsit.get(i).getConsumption()+1000);
					valueDescription = valueDescription+ "【"+i+1+"】"+ lsit.get(i).getContent()+lsit.get(i).getConsumption()+lsit.get(i).getCompany();
					/*判断一下每条医嘱的剂量是否为毫升*/
				}if(lsit.get(i).getCompany().equals(HisDoctorsAdviceEnum.DRUG_UNIT_ML.getCode())){
					/* 当满足时，将单条医嘱的内容及  值  单位取出 */
					intakeOutputValue = intakeOutputValue+ Integer.valueOf(lsit.get(i).getConsumption());
					valueDescription = valueDescription+ "【"+i+1+"】"+ lsit.get(i).getContent()+lsit.get(i).getConsumption()+lsit.get(i).getCompany();

				}
			}
			////////////////////////////////////////////////////////////////////////////////// projectId

			/* 查询引流液中项目中的编号 */
			Project project = projectService.selectByName("静脉入量");
			if (project!=null&&project.getProjectId()!=null && !project.getProjectId().equals("")){
				intakeOutputRecord.setProjectId(project.getProjectId());
			}


			/* 同时添加出入量数据 */
			intakeOutputRecord.setCauseRemark(taskBo.getSourceItemsId());  //  任务中医嘱批次号
			intakeOutputRecord.setIntakeOutputId(SnowFlake.getId()+"");// //雪花
			intakeOutputRecord.setPatientId(taskBo.getPatientId());//患者id
			intakeOutputRecord.setIntakeOutputType(1);//出量标识
			intakeOutputRecord.setIntakeOutputValue(intakeOutputValue); //出入量值
			intakeOutputRecord.setDelFlag(0);//删除标识
			intakeOutputRecord.setCreateTime(LocalDateTime.now());//创建时间
			intakeOutputRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");//创建人
			intakeOutputRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); ///科室
			intakeOutputRecord.setSourceId(taskBo.getTaskId());//数据关联id
			intakeOutputRecord.setValueDescription(valueDescription); //描述
			/* 数据归0 */
			intakeOutputValue =0;
			valueDescription="";
			boolean add = intakeOutputRecordService.add(intakeOutputRecord);
			if (!add){
				return false;
			}

		}

		return true;
	}












	/***
	 * 任务添加到项目表中
	 * @param taskBo
	 * @return
	 */
	@Override
	public Boolean taskAddProject(TaskBo taskBo) {
		ProjectRecord projectRecord = new ProjectRecord();
		/* 当条任务的来源为任务模板时单独处理 */
		if(taskBo.getSourceItems() ==TaskEnum.TASK_TEMPLATE.getNumber()&&taskBo.getTemplateSourceItemsId()!=null&&!taskBo.getTemplateSourceItemsId().equals("")){

			Template templateId = templateService.getTemplateId(taskBo.getSourceItemsId());

			if (templateId !=null &&templateId.getSourceId()!=null&&!templateId.getSourceId().equals("")){
				/*  将任务模板中的来源id作为数据查询*/
				projectRecord.setProjectId(taskBo.getTemplateSourceItemsId()); //项目id
			}
			/* 判断一下来源是否为项目时 */
		}if(taskBo.getSourceItems() ==TaskEnum.TASK_PROJECT.getNumber()) {
			/* 将任务的来源id作为数据查询*/
			projectRecord.setProjectId(taskBo.getSourceItemsId()); //项目id
		}else{
			// 当项目来源不为任务模板同时模板内容来源没有项目id时，同时当条数据的项目来源不为项目时，直接返回真
			return true;
		}
		projectRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室
		projectRecord.setCreateUserId(SecurityUtils.getUser().getId()+""); //创建人
		projectRecord.setSource(DataSourceEnum.TASK.getCode());// 来源
		projectRecord.setRemarks(taskBo.getTaskDescribe()); //  备注
		projectRecord.setSourceId(taskBo.getNursingRecordId()); //来源id  //  护理记录单id
		projectRecord.setPatientId(taskBo.getPatientId()); //患者

		//  if 判断一下前台传来的结果中的数据不为空的时候
		if (taskBo.getDataFlag()==null||taskBo.getRecordValue() ==null){
			//projectSpecificRecord
			//  将任务结果 传到项目记录表汇总
			projectRecord.setProjectSpecificRecord(taskBo.getResult());
		}else{
			projectRecord.setProjectSpecificRecord(taskBo.getRecordValue());
			projectRecord.setProjectValue(taskBo.getDataFlag()+"");
		}

		return projectRecordService.add(projectRecord);
	}








	/**
	 * 任务结果需要做的处理
	 * @param byId
	 * @return
	 */
	@Override
	public Boolean taskResult(TaskBo byId) {
		/* 通过任务模板查询数据 */


		/* 将任务的结果保存到护理记录单中  */
		Boolean aBoolean1 = this.taskAddNursingRecord(byId);
		if (!aBoolean1){
			return false;
		}

		/*当任务来源  为医嘱的时候，同时来源id 不为空的时候 ，将医嘱数据添加到出入量中 */
		if (byId.getSourceItems()==TaskEnum.TASK_DOCTORS_ADVICE.getNumber()&&byId.getSourceItemsId()!=null){
			/* 将医嘱添加到出入量中 */
			Boolean aBoolean = this.taskAddIntakeOutput(byId);
			if (!aBoolean){
				return false;}
			/***
			 * 当任务来源为项目或者任务模板时,要做判断将任务的结果添加到项目表中
			 */
		}	if ((byId.getSourceItems()==TaskEnum.TASK_PROJECT.getNumber()||byId.getSourceItems()==TaskEnum.TASK_TEMPLATE.getNumber()) && byId.getSourceItemsId()!=null){
			Boolean aBoolean = this.taskAddProject(byId);
			if (!aBoolean){
				return false;
			}

		}
		return true;
	}
}
