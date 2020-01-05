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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.PatientEnum;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsTaskRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TasksService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 任务主表
 *
 * @author pigx code generator
 * @date 2019-10-12 14:07:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/taktasks" )
@Api(value = "taktasks", tags = "任务主表管理")
public class TasksController {

    private final TasksService tasksService;

	/**
	 * 子任务
	 */
	private final TaskSubTaskService taskSubTaskService;

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
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 如果当前登录的是护士长的时候需要将当前科室所有的护士，及患者展示给护士长查看 */
			/* 查询当前科室下所有的护士 */
			List<Nurse> nurses = nurseService.selectByDept(SecurityUtils.getUser().getDeptId() + "");

			if (CollectionUtils.isNotEmpty(nurses)){
				test.put("nurses",nurses);
			}
			PatientBo patientBo = new PatientBo();
			patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");
			patientBo.setEntryState(PatientEnum.IN_SCIENCE.getCode());//在科标识
			List<PatientVo> patients = patientService.inSciencePatientAll(patientBo);
			if (patients!=null||patients.size()>0){
				test.put("patients",patients);
			}
		}else if("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 如果当前登录的是护士时，展示护士所关联的患者 */

			NursePatientCorrelation nursePatientCorrelation = new NursePatientCorrelation();
			nursePatientCorrelation.setNurseId(SecurityUtils.getUser().getId()+"");
			List<NursePatientCorrelationVo> list = nursePatientCorrelationService.selectAll(nursePatientCorrelation);
			TemplateVo template = new TemplateVo();
			List<Template> templates = templateService.selectAll(template);
			if (CollectionUtils.isNotEmpty(list)){
				return R.failed(null,"没有看护患者");
			}
			test.put("list",list);
			if (CollectionUtils.isNotEmpty(templates)){
				return R.failed(null,"任务模板");
			}
			test.put("templates",templates);

		}
		List<String> list = new ArrayList<>();
		return R.ok(test);
	}
    /**
     * 分页查询
     * @param page 分页对象
     * @param tasks 任务主表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakTasksPage(Page page, Tasks tasks) {
        return R.ok(tasksService.page(page, Wrappers.query(tasks)));
    }


    /**
     * 通过id查询任务主表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(tasksService.getById(id));
    }

    /**
     * 新增任务主表
     * @param tasks 任务主表
     * @return R
     */
    @ApiOperation(value = "新增任务主表", notes = "新增任务主表")
    @SysLog("新增任务主表" )
    @PostMapping("/add")
    public R save(@RequestBody Tasks tasks) {
        return R.ok(tasksService.add(tasks));
    }

    /**
     * 修改任务主表
     * @param tasks 任务主表
     * @return R
     */
    @ApiOperation(value = "修改任务主表", notes = "修改任务主表")
    @SysLog("修改任务主表" )
   @PostMapping("/update")
    public R updateById(@RequestBody Tasks tasks) {
        return R.ok(tasksService.updateById(tasks));
    }

    /**
     * 通过id删除任务主表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务主表", notes = "通过id删除任务主表")
    @SysLog("通过id删除任务主表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		Tasks byId = tasksService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		Boolean delete = taskSubTaskService.delete(byId.getTaskId());
		if (!delete){
			return R.failed("相关子任务删除失败！！！");
		}

		return R.ok(tasksService.removeById(id));
    }

}
