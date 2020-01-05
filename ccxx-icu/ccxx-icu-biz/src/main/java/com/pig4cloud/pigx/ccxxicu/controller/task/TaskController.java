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

package com.pig4cloud.pigx.ccxxicu.controller.task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceRedisVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.NursePatientRecordEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.PatientEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsTaskRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 任务表
 *
 * @author pigx code generator
 * @date 2019-08-13 16:30:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task" )
@Api(value = "task", tags = "任务表管理")
public class TaskController {

	private final TaskService taskService;

	/**
	 * 护士
	 */
	private final NurseService nurseService;
	/**
	 * 护士患者关联表
	 */
	private final NursePatientCorrelationService nursePatientCorrelationService;
	/**
	 * 护士患者记录表
	 */
	private final NursePatientRecordService nursePatientRecordService;

	/**
	 * 任务模板
	 */
	private final TemplateService templateService;


	/**
	 * 患者
	 */
	private final PatientService patientService;

	/**
	 * 任务交接班
	 */
	private final ChangeShiftsTaskRecordService changeShiftsTaskRecordService;

	/**
	 * 医嘱
	 */
	private final HisDoctorsAdviceService hisDoctorsAdviceService;

	/**
	 * 医嘱 内容
	 */
	private final HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

	private final RedisTemplate<String, Object> redisTemplates;

	/**
	 * 通过id查询任务表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(taskService.getById(id));
	}


	/**
	 * 通过id查询任务详情
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getDetails")
	public R getDetails(Integer id, ChangeShiftsTaskRecord changeShiftsTaskRecord) {
		Map<String, Object> test = new HashMap<>();

		Task byId = taskService.getById(id);
		List<ChangeShiftsTaskRecord> changeShiftsTaskRecords = changeShiftsTaskRecordService.selectAll(changeShiftsTaskRecord);
		if (CollectionUtils.isNotEmpty(changeShiftsTaskRecords)) {
			test.put("changeShiftsTaskRecords", changeShiftsTaskRecords);
		}
		if (byId.getSourceItems() == 1 || byId.getSourceItemsId() != null) {
			/* 医嘱内容 */
			HisDoctorsAdvice batchNumber = hisDoctorsAdviceService.getBatchNumber(byId.getSourceItemsId());
			if (batchNumber != null) {
				test.put("batchNumber", batchNumber);
			}
			/* 医嘱内容 */
			List<HisDoctorsAdviceProject> hisDoctorsAdviceProjects = hisDoctorsAdviceProjectService.selectBatchNumber(byId.getSourceItemsId());
			if (hisDoctorsAdviceProjects != null) {
				test.put("hisDoctorsAdviceProjects", hisDoctorsAdviceProjects);
			}
		}
		test.put("byId", byId);
		return R.ok(test);
	}


	/**
	 * 新增前跳转页面
	 *
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面", notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@GetMapping("/preJump")
	public R preJump() {
		/* 判断一下，当前登录的是护士长还是护士 */
		Map<String, Object> test = new HashMap<>();
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))) {
			/* 如果当前登录的是护士长的时候需要将当前科室所有的护士，及患者展示给护士长查看 */
			/* 查询当前科室下所有的护士 */
			List<Nurse> nurses = nurseService.selectByDept(SecurityUtils.getUser().getDeptId() + "");

			if (CollectionUtils.isNotEmpty(nurses)) {
				test.put("nurses", nurses);
			}
			PatientBo patientBo = new PatientBo();
			patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId() + "");
			patientBo.setEntryState(PatientEnum.IN_SCIENCE.getCode());//在科标识
			List<PatientVo> patients = patientService.inSciencePatientAll(patientBo);
			if (patients != null || patients.size() > 0) {
				test.put("patients", patients);
			}
		} else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {
			/* 如果当前登录的是护士时，展示护士所关联的患者 */

			NursePatientCorrelation nursePatientCorrelation = new NursePatientCorrelation();
			nursePatientCorrelation.setNurseId(SecurityUtils.getUser().getId() + "");
			List<NursePatientCorrelationVo> list = nursePatientCorrelationService.selectAll(nursePatientCorrelation);
			TemplateVo template = new TemplateVo();
			List<Template> templates = templateService.selectAll(template);
			if (CollectionUtils.isNotEmpty(list)) {
				return R.failed(null, "没有看护患者");
			}
			test.put("list", list);
			if (CollectionUtils.isNotEmpty(templates)) {
				return R.failed(null, "任务模板");
			}
			test.put("templates", templates);

		}
		List<String> list = new ArrayList<>();
		return R.ok(test);
	}


	/**
	 * 护士长点击护士时展示当前护士关联的患者
	 *
	 * @return
	 */
	@ApiOperation(value = "护士长点击护士时展示当前护士关联的患者", notes = "护士长点击护士时展示当前护士关联的患者")
	@SysLog("护士长点击护士时展示当前护士关联的患者")
	@PostMapping("/nursePatient")
	public R nursePatient(@RequestBody NursePatientCorrelation nursePatientCorrelation) {
		/*护士长查询相关数据*/
		List<NursePatientCorrelationVo> list = nursePatientCorrelationService.selectAll(nursePatientCorrelation);
		if (CollectionUtils.isNotEmpty(list)) {
			return R.failed(null, "没有看护患者");
		}
		return R.ok(list);

	}


	/**
	 * 新增任务表:新增任务时需要 判断一下当前创建的任务是否为开始即结束的任务
	 *
	 * @param task 任务表
	 * @return R
	 */
	@ApiOperation(value = "新增任务表", notes = "新增任务表")
	@SysLog("新增任务表")
	@PostMapping("/add")
	public R save(@RequestBody Task task) {
		task.setCreatorId(SecurityUtils.getUser().getId() + ""); // 创建人
		task.setCreateTime(LocalDateTime.now()); // 创建时间
		task.setDeptId(SecurityUtils.getUser().getDeptId() + "");// 科室
		task.setTaskId(SnowFlake.getId() + ""); // 雪花id

		/* 查询 当前患者关联的当天责任护士id*/
		String nurseId = nursePatientCorrelationService.selectDutyNurseId(task.getPatientId());
		task.setDutyNurseId(nurseId);//责任护士
		task.setReceiveTime(LocalDateTime.now()); // 添加接收时间
		task.setReceiveTime(LocalDateTime.now());  //接收时间

		// 查询创建用户是护士的时候 将当前登录的护士id  作为执行护士id   反之 执行护士为责任护士
		if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {
			task.setReceptionNurseId(SecurityUtils.getUser().getId() + ""); // 执行护士
		} else {
			task.setReceptionNurseId(nurseId); // 执行护士
		}

		return R.ok(taskService.save(task));
	}

	/**
	 * 修改任务表
	 *
	 * @param task 任务表
	 * @return R
	 */
	@ApiOperation(value = "修改任务表", notes = "修改任务表")
	@SysLog("修改任务表")
	@PostMapping("/update")
	public R updateById(@RequestBody Task task) {
		return R.ok(taskService.updateById(task));
	}

	/**
	 * 通过id删除任务表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除任务表", notes = "通过id删除任务表")
	@SysLog("通过id删除任务表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		Task byId = taskService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());//删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId() + "");// 删除人
		return R.ok(taskService.updateById(byId));
	}


	/**
	 * 分页查询任务
	 *
	 * @param page
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "分页查询任务", notes = "分页查询任务")
	@SysLog("分页查询任务")
	@GetMapping("/selectPaging")
	public R selectPaging(Page page, TaskVo task) {
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))) {
			task.setDeptId(SecurityUtils.getUser().getDeptId() + "");
			return R.ok(taskService.selectPaging(page, task));
		} else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))) {
			task.setAssociatedNurse(SecurityUtils.getUser().getId() + ""); // 当前登录护士id
			return R.ok(taskService.selectPaging(page, task));
		}
		return R.ok(taskService.selectPaging(page, task));
	}


	/**
	 * id查询数据 【任务详情】
	 *
	 * @param taskId
	 * @return
	 */
	@ApiOperation(value = "id查询数据 【任务详情】", notes = "id查询数据 【任务详情】")
	@SysLog("id查询数据 【任务详情】")
	@GetMapping("/taskIdDetails/{taskId}")
	public R taskId(@PathVariable("taskId") String taskId) {

		return R.ok(taskService.taskId(taskId));
	}


	/**
	 * id查询数据
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "id查询数据", notes = "id查询数据")
	@SysLog("id查询数据")
	@PostMapping("/selectId")
	public R selectId(@RequestBody Integer id) {

		return R.ok(taskService.selectId(id));
	}


	/**
	 * 雪花id查询
	 *
	 * @param taskId
	 * @return
	 */
	@ApiOperation(value = "雪花id查询", notes = "雪花id查询")
	@SysLog("雪花id查询")
	@GetMapping("/getTaskId/{taskId}")
	public R getTaskId(@PathVariable("taskId") String taskId) {
		return R.ok(taskService.getTaskId(taskId));
	}


	/**
	 * 多条任务新增
	 *
	 * @param taskLists
	 * @return
	 */
	@ApiOperation(value = "多条任务新增*护理模板", notes = "多条任务新增*护理模板")
	@SysLog("多条任务新增*护理模板")
	@PostMapping("inserts")
	public R inserts(@RequestBody List<Task> taskLists) {
		return R.ok(taskService.inserts(taskLists));
	}


	/**
	 * 点击修改任务状态
	 *
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "点击修改任务状态", notes = "点击修改任务状态")
	@SysLog("点击修改任务状态")
	@PostMapping("/updateState")
	public R updateState(@RequestBody TaskBo task) {

		return R.ok(taskService.updateState(task));
	}


	/**
	 * 点击任务模板时新增
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value = "点击任务模板时新增", notes = "点击任务模板时新增")
	@SysLog("点击任务模板时新增")
	@PostMapping("/insertTemplate")
	public R insertTemplate(@RequestBody TaskBo task) {
		return R.ok(taskService.insertTemplate(task));
	}


	/**
	 * 责任护士分配辅助护士任务
	 *
	 * @param nursePatientRecord
	 * @return
	 */
	@ApiOperation(value = "责任护士分配辅助护士任务", notes = "责任护士分配辅助护士任务")
	@SysLog("责任护士分配辅助护士任务")
	@PostMapping("/assistNurse")
	public R assistNurse(@RequestBody NursePatientRecord nursePatientRecord) {
		/* 创建护士患者关联 */
		nursePatientRecord.setOnlyDutyNurse(NursePatientRecordEnum.ORDINARY_NUESE.getCode());//辅助护士
		return R.ok(nursePatientRecordService.nurseRelationPatient(nursePatientRecord));
	}

	/**
	 * 任务委托护士查询
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value = "任务委托护士查询", notes = "任务委托护士查询")
	@SysLog("任务委托护士查询")
	@GetMapping("/taskEntrustNurse")
	public R taskEntrustNurse() {
		return taskService.taskEntrustNurse();
	}


	/**
	 * 任务委托
	 *
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "任务委托", notes = "任务委托")
	@SysLog("任务委托")
	@PostMapping("/taskEntrust")
	public R taskEntrust(@RequestBody Task task) {
		/* 需要数据    id   交班护士 */
		Boolean aBoolean = taskService.taskEntrust(task);
		if (aBoolean == false) {
			return R.failed(null, "任务委托失败！！！");
		}
		return R.ok(null, "任务委托成功！！！");
	}

	/**
	 * 通过判断当条任务为项目任务，查询当条项目是否结果
	 *
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "通过判断当条任务为项目任务，查询当条项目是否结果", notes = "通过判断当条任务为项目任务，查询当条项目是否结果")
	@SysLog("通过判断当条任务为项目任务，查询当条项目是否结果")
	@PostMapping("/selectProjectValue")
	public R selectProjectValue(@RequestBody Task task) {
		return R.ok(taskService.selectProjectValue(task));
	}


	@GetMapping("/test")
	public R test() {
		LocalDateTime now = LocalDateTime.now();
		System.out.println("方法进来-----------" + now);
		int b = 100000;
		List<HisDoctorsAdviceRedisVo> list = new ArrayList<>();
		ValueOperations<String, Object> redis = redisTemplates.opsForValue();

		for (int i = 0; i < b; i++) {
			HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
			hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(i + "a");
			hisDoctorsAdviceRedisVo.setIntsetTime(LocalDateTime.now());//开始时间
			hisDoctorsAdviceRedisVo.setUpdateTime(LocalDateTime.now().plusDays(i));
			list.add(hisDoctorsAdviceRedisVo);
			redis.set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(), hisDoctorsAdviceRedisVo,10,TimeUnit.MINUTES);
			/**
			 *
			 * his传来数据 有数据是修改，无数据时新增
			 * 判断list<his>循环遍历 新增时间和修改时间在当前时间之内的数据  ？怎么判断是第一次还是第二次
			 *	可以通过患者id 去查询医嘱，查询看有没有数据，如果没有数据，证明是第一次，反之
			 *
			 *
			 *
			 */



		}

		return R.ok(list);
	}


	@GetMapping("/testss/{id}")
	public R testss(@PathVariable("id") String id) {

		ValueOperations<String, Object> redis = redisTemplates.opsForValue();
		Object o = redis.get(id);
		HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo =(HisDoctorsAdviceRedisVo)o;
		System.out.println("强制转换数据------------"+hisDoctorsAdviceRedisVo);
		return R.ok(o);
	}


}