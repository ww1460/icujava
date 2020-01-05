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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientInfoBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TaskSubTaskBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskSubTaskVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.NursePatientRecordEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.TaskEnum;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
@RestController
@AllArgsConstructor
@RequestMapping("/subtask" )
@Api(value = "subtask", tags = "任务子表管理")
public class TaskSubTaskController {

    private final TaskSubTaskService taskSubTaskService;
	/**
	 * 护士
	 */
	private final NurseService nurseService;
	/**
	 * 患者
	 */
	private final PatientService patientService;
	/**
	 * 护士患者关联
	 */
	private final NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 护士患者记录表
	 */
	private final NursePatientRecordService nursePatientRecordService;

	/**
	 * 主模板
	 */
	private final TemplateService templateService;

	/**
	 * 新增前跳转页面
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@GetMapping("/preJump")
	public R preJump(){
		/* 判断一下，当前登录的是护士长还是护士 */
		Map<String,Object> test = new HashMap<>();
		PatientInfoBo patientInfoBo = new PatientInfoBo();
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 如果当前登录的是护士长的时候需要将当前科室所有的护士，及患者展示给护士长查看 */
			/* 查询当前科室下所有的护士 */
			List<Nurse> nurses = nurseService.selectByDept(SecurityUtils.getUser().getDeptId() + "");

			if (CollectionUtils.isNotEmpty(nurses)){
				test.put("nurses",nurses);
			}
			/*  患者*/
			patientInfoBo.setDeptId(SecurityUtils.getUser().getDeptId()+"");
			List<PatientInfo> patientId = nursePatientCorrelationService.getPatientId(patientInfoBo);
			if (CollectionUtils.isNotEmpty(patientId)){
				test.put("patientId",patientId);
			}
		}else if("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 如果当前登录的是护士时，展示护士所关联的患者 */
			patientInfoBo.setNurseId(SecurityUtils.getUser().getId()+"");
			patientInfoBo.setDeptId(SecurityUtils.getUser().getDeptId()+"");
			List<PatientInfo> patientId = nursePatientCorrelationService.getPatientId(patientInfoBo);

			if (CollectionUtils.isNotEmpty(patientId)){
				test.put("patientId",patientId);
			}

		}
		return R.ok(test);
	}












    /**
     * 分页查询
     * @param page 分页对象
     * @param taskSubTask 任务子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakTaskSubTaskPage(Page page, TaskSubTaskVo taskSubTask) {

		System.out.println("任务状态-------------"+taskSubTask.getState());
		TaskSubTask taskSubTask1 = new TaskSubTask();
		BeanUtil.copyProperties(taskSubTask,taskSubTask1);

		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			taskSubTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");
			return R.ok(taskSubTaskService.selectPaging(page,taskSubTask));
		}else if("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			taskSubTask.setAssociatedNurse(SecurityUtils.getUser().getId()+""); // 当前登录护士id
			return R.ok(taskSubTaskService.selectPaging(page,taskSubTask));
		}
		return R.ok(taskSubTaskService.selectPaging(page,taskSubTask));
    }

	/**
	 * 任务不分页
	 * @param taskSubTask 任务不分页
	 * @return
	 */
	@ApiOperation(value = "任务不分页", notes = "任务不分页")
	@GetMapping("/taskList" )
	public R taskList(TaskSubTaskVo taskSubTask) {

		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			taskSubTask.setDeptId(SecurityUtils.getUser().getDeptId()+"");
			return R.ok(taskSubTaskService.taskList(taskSubTask));
		}else if("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			taskSubTask.setAssociatedNurse(SecurityUtils.getUser().getId()+""); // 当前登录护士id
			return R.ok(taskSubTaskService.taskList(taskSubTask));
		}
		return R.ok(taskSubTaskService.taskList(taskSubTask));
	}




    /**
     * 通过id查询任务子表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(taskSubTaskService.getById(id));
    }

	/**
	 * 任务详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "任务详情", notes = "任务详情")
	@GetMapping("getDetails/{id}" )
	public R getDetails(@PathVariable("id" ) Integer id) {
		return R.ok(taskSubTaskService.getId(id));
	}

	/**
     * 新增任务子表
     * @param taskSubTask 任务子表
     * @return R
     */
    @ApiOperation(value = "新增任务子表", notes = "新增任务子表")
    @SysLog("新增任务子表" )
    @PostMapping("/add")
    public R save(@RequestBody TaskSubTask taskSubTask) {
        /* 判断任务的来源 是否为任务模板 */
		if (taskSubTask.getTaskType()== TaskEnum.TEMPLATE.getNumber()){ //来源为自建任务模板时,通任务模板的数据查询子任务模板 从而新增数据
			Template templateId = templateService.getTemplateId(taskSubTask.getTaskTypeId());//通过来源id查询数据
			TemplateBo templateBo = new TemplateBo();
			BeanUtil.copyProperties(templateId,templateBo);
			templateBo.setPatientId(taskSubTask.getPatientId());
			return R.ok(templateService.templateAddTask(templateBo));
		}

    	return R.ok(taskSubTaskService.addTask(taskSubTask));
    }

    /**
     * 修改任务子表
     * @param taskSubTask 任务子表
     * @return R
     */
    @ApiOperation(value = "修改任务子表", notes = "修改任务子表")
    @SysLog("修改任务子表" )
    @PostMapping("update")
    public R updateById(@RequestBody TaskSubTask taskSubTask) {
        return R.ok(taskSubTaskService.updateById(taskSubTask));
    }

    /**
     * 通过id删除任务子表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务子表", notes = "通过id删除任务子表")
    @SysLog("通过id删除任务子表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		TaskSubTask byId = taskSubTaskService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		return R.ok(taskSubTaskService.updateById(byId));
    }


	/**
	 * 修改任务状态
	 * @return
	 */
	@PostMapping("/updateState")
    public R updateState(@RequestBody TaskSubTaskBo taskBo){

    	return R.ok(taskSubTaskService.updateState(taskBo));
	}

	/**
	 * 通过判断当条任务为项目任务，查询当条项目是否结果
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "通过判断当条任务为项目任务，查询当条项目是否结果",notes = "通过判断当条任务为项目任务，查询当条项目是否结果")
	@SysLog("通过判断当条任务为项目任务，查询当条项目是否结果")
	@PostMapping("/selectProjectValue")
	public R selectProjectValue(@RequestBody TaskSubTask task){

		return R.ok(taskSubTaskService.selectProjectValue(task));
	}


	/**
	 * 护理模板新增【位于预定护理计划新增任务】
	 * @param taskSubTask
	 * @return
	 */
	@ApiOperation(value = "护理模板新增",notes = "护理模板新增")
	@SysLog("护理模板新增")
	@PostMapping("/nursingScheme")
	public R nursingScheme(@RequestBody TaskSubTask taskSubTask){
		return R.ok(taskSubTaskService.nursingScheme(taskSubTask));
	}


	/**
	 * 任务委托护士查询
	 * @param
	 * @return
	 */
	@ApiOperation(value = "任务委托护士查询",notes = "任务委托护士查询")
	@SysLog("任务委托护士查询")
	@GetMapping("/taskEntrustNurse")
	public R taskEntrustNurse(){
		return taskSubTaskService.taskEntrustNurse();
	}


	/**
	 * 任务委托
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "任务委托",notes = "任务委托")
	@SysLog("任务委托")
	@PostMapping("/taskEntrust")
	public R taskEntrust(@RequestBody TaskSubTask task){
		/* 需要数据    id   交班护士 */
		Boolean aBoolean = taskSubTaskService.taskEntrust(task);
		if (aBoolean==false){
			return R.failed(null,"任务委托失败！！！");
		}
		return R.ok(null,"任务委托成功！！！");
	}


	/**
	 * 任务拆分
	 * @param list
	 * @return
	 */
	@ApiOperation(value = "任务拆分",notes = "任务拆分")
	@SysLog("任务拆分")
	@PostMapping("/taskSubTaskSplit")
	public R taskSubTaskSplit(@RequestBody List<TaskSubTask> list){
		return taskSubTaskService.taskSubTaskSplit(list);
	}


	/**
	 * 责任护士分配辅助护士任务
	 * @param nursePatientRecord
	 * @return
	 */
	@ApiOperation(value = "责任护士分配辅助护士任务",notes = "责任护士分配辅助护士任务")
	@SysLog("责任护士分配辅助护士任务")
	@PostMapping("/assistNurse")
	public R assistNurse(@RequestBody NursePatientRecord nursePatientRecord){
		/* 创建护士患者关联 */
		nursePatientRecord.setOnlyDutyNurse(NursePatientRecordEnum.ORDINARY_NUESE.getCode());//辅助护士
		return R.ok(nursePatientRecordService.nurseRelationPatient(nursePatientRecord));
	}










}
