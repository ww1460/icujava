package com.pig4cloud.pigx.ccxxicu.schedule;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.*;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TasksVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TimingExecutionVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.PatientEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TemplateEnum;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.*;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/timing" )
@Api(value = "timing", tags = "定时模块")
public class TaskSchedule {



	/**
	 * 任务模板
	 */
	private final TemplateService templateService;

	/**
	 * 定时任务
	 */
	private final TimingExecutionService timingExecutionService;

	/**
	 * 患者
	 */
	private final PatientService patientService;

	/**
	 *任务
	 */
	private final TaskService taskService;

	/**
	 * 任务预警
	 */
	private final EarlyWarningService earlyWarningService;

	/**
	 * 医嘱
	 */
	private final HisDoctorsAdviceService hisDoctorsAdviceService;

	/**
	 * 医嘱详情
	 */
	private final HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	/**
	 * 子任务
	 */
	private final TaskSubTaskService taskSubTaskService;




	/**
	 * 定时模块 定时查询数据
	 */
	//@Scheduled(fixedRate = 120000)
	public void timing(){
		//**************************************************************************************************************
		// ********************************************  定时任务查询  **************************************************
		// *************************************************************************************************************
		/**
		 * 1、查询定时任务模块中的数据时，查询一下定时任务中的数据 如果没有数据的情况下不做任何处理【在数据库中已做处理，查询出来的任务当前当前时间需要执行的任务】
		 * 2、判断一下当前任务的患者是否已经出科了，如果已经出科了就删除当前的长期任务。
		 * 3、当患者没有出科时，生成任务，并修改开始及结束的时间【开始、结束时间 + 上间隔 = 下次的执行时间】
		 */
		List<TimingExecutionVo> timingExecutions = timingExecutionService.timingQuery();
		/* 在查询到的定时任务不为空且不为0的情况下做处理   */
		if (CollectionUtils.isNotEmpty(timingExecutions)) {
			//   生成一条任务
			for (int i = 0; i < timingExecutions.size(); i++) {
				TaskSubTask task = new TaskSubTask();
				TimingExecutionVo timing = timingExecutions.get(i);
				System.out.println("几条数据-------------------------------------"+timingExecutions.size());
				/* 新建一个定时任务模板 方便houq */
				TimingExecution timingExecution = new TimingExecution();
				timingExecution.setId(timing.getId());
				BeanUtil.copyProperties(timing,timingExecution);
				System.out.println("打印数据---------------"+timing);
				/* 判断一下当前的患者是否出院，如果出院将当前的定时任务删除 */
				/* 判断当前患者的出科时间为空的时候同时出科类型为空的时候 就默认为当前患者没有出科 */
				if (patientService.getByPatientId(timing.getPatientId()).getEntryState()!= PatientEnum.DEPARTURE.getCode()&&timingExecution.getSource()==TemplateEnum.PROJECT.getCode())
				{
				//	利用长期任务中的【主】任务模板id查询当前模板的数据
					Template templateId = templateService.getTemplateId(timingExecution.getSourceId());
					TemplateBo templateBo = new TemplateBo();
					templateBo.setPatientId(timingExecutions.get(i).getPatientId());
					BeanUtil.copyProperties(templateId,templateBo);
					Boolean aBoolean = templateService.templateAddTask(templateBo);

					if (!aBoolean){
						System.out.println("定时任务 ==> 长期定时任务  【定时任务生成有误！！！！！！！！！！！！！！！！】");
					}
					// 修改当条数据的下次执行时间   当前的下次执行时间 + 上间隔时间 等于下次间隔时间，

						timingExecution.setPreStartTime(timing.getPreStartTime().plusHours(timing.getIntervalTimes())); // 修改预开始时间
						System.out.println("修改预开始时间-------------------------"+timing.getPreStartTime());
						timingExecution.setPreEndTime(timing.getPreEndTime().plusHours(timing.getIntervalTimes()));     // 修改预结束时间
						System.out.println("修改预结束时间------------------------"+timing.getPreEndTime());
						timingExecution.setNextExecutionTime(timingExecution.getPreStartTime());        // 将下次执行时间修改为新的预开始时间
				    	System.out.println("修改预执行时间------------------------"+timing.getNextExecutionTime());

					boolean b = timingExecutionService.updateById(timingExecution);
					if (b == false) {
						System.out.println("定时任务 ==> 长期定时任务  【定时任务（任务）修改失败！！！！！！！！！！！】");
					}

				}else{
					timingExecution.setDelFlag(1);
					timingExecution.setDelTime(LocalDateTime.now());
					boolean b = timingExecutionService.updateById(timingExecution);
					if (b==false){
						System.out.println("定时任务 ==> 长期定时任务  【查询到当前患者以出科，但定时任务没有删除成功，请联系管理员】");
					}
				}
			}
		}else{
			System.out.println("定时任务 ==> 长期定时任务【没有长期定时任务 └(^o^)┘└(^o^)┘】");
		}


/***********************************************************************************************************************
 ***********************************************  任务预警  *************************************************************
 ***********************************************************************************************************************/


		/* 同时查询所有的任务，查询其中的任务是否有完成的如果有就删除记录 */
		List<EarlyWarning> list = earlyWarningService.timingAll();
		if (CollectionUtils.isNotEmpty(list)){
			for (int i =0;i<list.size();i++){
				EarlyWarning earlyWarning = list.get(i);
				/* 查询出每条任务的状态，判断状态是否为完成或者中止 */
				Integer state = taskSubTaskService.taskSubTaskId(earlyWarning.getTaskId()).getState();
				/* 判断一下任务是否已经完成 或者中止了 */
				if (state==TaskEnum.COMPLETE.getNumber() ||state ==TaskEnum.SUSPEND.getNumber()||state == TaskEnum.LEAVE_HOSPITAL.getNumber()){
					earlyWarning.setDelFlag(1);
					earlyWarning.setDelTime(LocalDateTime.now());
					boolean b = earlyWarningService.updateById(earlyWarning);
					if (b==false){
						System.out.println("定时任务 ==> 任务预警【有任务已经完成或者中止，但是我没有将它删除成功！！！！】");
					}else{
						System.out.println("定时任务 ==> 任务预警【有任务已经完成或者中止，但是我将它删除成功 └(^o^)┘└(^o^)┘】");
					}
				}
			}
		}else{
			System.out.println("定时任务 ==> 任务预警：没有完成的任务 └(^o^)┘ ");

		}

		/* 查询所有超时的任务  stopTask*/
		List<TaskSubTask> tasks = taskSubTaskService.overtimeTask();

		/* 任务不为空的时候处理 */
		if (CollectionUtils.isNotEmpty(tasks)){
			for (int i =0;i<tasks.size();i++){
				TaskSubTask task = tasks.get(i);
				/* 判断一下查询出来的任务id是否已经发生过预警，如果预警表中有当前数据的预警则不需要再次新增 */
				EarlyWarning early = earlyWarningService.timingSelect(task.getTaskSubTaskId());
				/* 当查询当条任务不在预警表中时 */
				if (early == null){
					/* 将查询出来的任务添加到预警表中*/
					EarlyWarning earlyWarning = new EarlyWarning();
					/* 任务未接收的时候，没有护士护士信息 */
					earlyWarning.setTaskId(task.getTaskSubTaskId());   //  任务id
					earlyWarning.setTriggerTime(LocalDateTime.now()); // 触发时间
					earlyWarning.setReason("当前任务未按时完成");// 预警原因
					earlyWarning.setPatientId(task.getPatientId());// 患者id
					earlyWarning.setDeptId(task.getDeptId()); // 科室
					earlyWarning.setState(TaskEnum.TASK_REMIND.getNumber()); // 状态 [超时任务提醒]
					earlyWarning.setNurseId(task.getReceptionNurseId()); // 当条任务关联的护士    【执行护士id】
					boolean save = earlyWarningService.save(earlyWarning);
					earlyWarningService.setToWatch(earlyWarning);
					if (save==false){
						System.out.println("定时任务 ==> 任务预警【添加失败！！！！！！！】");
					}else{
						System.out.println("定时任务 ==> 任务预警【添加成功 └(^o^)┘ 】");
					}
				}
			}
		}else{
			System.out.println("定时任务 ==> 任务预警【没有超时的任务 └(^o^)┘ 】");
		}


/***********************************************************************************************************************
 ***********************************************  医嘱定时生成任务  *****************************************************
 ***********************************************************************************************************************/

		/* 查询所有的长期医嘱，进行间隔查询数据 */
		List<HisDoctorsAdviceVo> hisDoctorsAdviceList = hisDoctorsAdviceService.longTermTask();
		if (CollectionUtils.isNotEmpty(hisDoctorsAdviceList)){
			//* 查询所有医嘱中，每条医嘱中（下医嘱的时间+频率*相关任务条数)是小于当前时间，如果小于当前时间，则要创建一条相对应的任务，如果是大于这不创建*//*
			System.out.println(" 查询所有的长期医嘱，进行间隔查询数据 ---------"+hisDoctorsAdviceList+"长度"+hisDoctorsAdviceList.size());
			hisDoctorsAdviceList.forEach(e->{
				// 按照医嘱的批次号查询，生成的任务条数，
				List<Task> taskList = taskService.getsSourceItemsId(e.getBatchNumber());
				//当前时间
				LocalDateTime currentTime = LocalDateTime.now();
				//取出下医嘱的时间
				//	用频率 * 相关任务条数
				//	用下医嘱的时间  加上    (         频率   *    相对的任务  )
				LocalDateTime dateTime = e.getDoctorsAdviceTime().plusHours(Integer.parseInt(e.getDataValue()) * taskList.size());
				System.out.println(currentTime+"当前时间--------------");
				System.out.println(dateTime+"下次医嘱改执-------------------"+dateTime);

				long hours = ChronoUnit.MILLIS.between(currentTime,dateTime);
				//*当前时间 减去 下次医嘱执行时间 大于0时，要创建 一条相关的任务*//*
				if (hours< 0) {


			TasksVo tasksVo = hisDoctorsAdviceService.taskAdd(e);
//					if (aBoolean ==false){
//						System.out.println("定时任务 ==> 医嘱生成任务失败！！！！！！ ");
//					}else{
//						System.out.println("定时任务 ==> 医嘱生成任务：【成功  └(^o^)┘└(^o^)┘】");
//					}


				}else{
					System.out.println("时间没到");
				}
			});

		}else{
			System.out.println("定时任务 ==> 医嘱生成任务：【没有医嘱  └(^o^)┘└(^o^)┘ 】");
		}



	}



	@GetMapping("/test")
	public void test(){
		/* 同时查询所有的任务，查询其中的任务是否有完成的如果有就删除记录 */
		List<EarlyWarning> list = earlyWarningService.timingAll();
		if (CollectionUtils.isNotEmpty(list)){
			for (int i =0;i<list.size();i++){
				EarlyWarning earlyWarning = list.get(i);
				/* 查询出每条任务的状态，判断状态是否为完成或者中止 */
				Integer state = taskService.getTaskId(earlyWarning.getTaskId()).getState();
				/* 判断一下任务是否已经完成 或者中止了 */
				if (state==TaskEnum.COMPLETE.getNumber() ||state ==TaskEnum.SUSPEND.getNumber()||state == TaskEnum.LEAVE_HOSPITAL.getNumber()){
					earlyWarning.setDelFlag(1);
					earlyWarning.setDelTime(LocalDateTime.now());
					boolean b = earlyWarningService.updateById(earlyWarning);
					if (b==false){
						System.out.println("定时任务 ==> 任务预警【有任务已经完成或者中止，但是我没有将它删除成功！！！！】");
					}else{
						System.out.println("定时任务 ==> 任务预警【有任务已经完成或者中止，但是我将它删除成功 └(^o^)┘└(^o^)┘】");
					}
				}
			}
		}else{
			System.out.println("定时任务 ==> 任务预警：没有完成的任务 └(^o^)┘ ");

		}

		/* 查询所有超时的任务 */
		List<Task> tasks = taskService.overtimeTask();

		/* 任务不为空的时候处理 */
		if (CollectionUtils.isNotEmpty(tasks)){
			for (int i =0;i<tasks.size();i++){
				Task task = tasks.get(i);
				/* 判断一下查询出来的任务id是否已经发生过预警，如果预警表中有当前数据的预警则不需要再次新增 */
				EarlyWarning early = earlyWarningService.timingSelect(task.getTaskId());
				/* 当查询当条任务不在预警表中时 */
				if (early == null){
					/* 将查询出来的任务添加到预警表中*/
					EarlyWarning earlyWarning = new EarlyWarning();
					/* 任务未接收的时候，没有护士护士信息 */
					earlyWarning.setTaskId(task.getTaskId());   //  任务id
					earlyWarning.setTriggerTime(LocalDateTime.now()); // 触发时间
					earlyWarning.setReason("当前任务未按时完成");// 预警原因
					earlyWarning.setPatientId(task.getPatientId());// 患者id
					earlyWarning.setDeptId(task.getDeptId()); // 科室
					earlyWarning.setState(TaskEnum.TASK_REMIND.getNumber()); // 状态 [超时任务提醒]
					earlyWarning.setNurseId(task.getReceptionNurseId()); // 当条任务关联的护士    【执行护士id】
					boolean save = earlyWarningService.save(earlyWarning);
					if (save==false){
						System.out.println("定时任务 ==> 任务预警【添加失败！！！！！！！】");
					}else{
						System.out.println("定时任务 ==> 任务预警【添加成功 └(^o^)┘ 】");
					}
				}
			}
		}else{
			System.out.println("定时任务 ==> 任务预警【没有超时的任务 └(^o^)┘ 】");
		}
	}











}
