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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.ChangeShiftsBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsDescribe;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.ChangeShiftsVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskVo;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsDescribeService;
import com.pig4cloud.pigx.ccxxicu.service.task.ChangeShiftsRecordService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 交接班记录
 *
 * @author pigx code generator
 * @date 2019-08-23 16:11:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/changeshiftsrecord" )
@Api(value = "changeshiftsrecord", tags = "交接班记录管理")
public class ChangeShiftsRecordController {

    private final ChangeShiftsRecordService changeShiftsRecordService;

	/**
	 * 护士关联患者
	 */
	private final NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 护士表
	 */
	private final NurseService nurseService;

	/**
	 * 任务
	 */
	private final TaskService taskService;

	/**
	 * 患者表
	 */
	private final PatientService patientService;

	/***
	 * 护士患者关联表记录表
	 */
	private final NursePatientRecordService nursePatientRecordService;

	/**
	 * 交接班任务描述表
	 */
	private final ChangeShiftsDescribeService changeShiftsDescribeService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param changeShiftsRecord 交接班记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakChangeShiftsRecordPage(Page page, ChangeShiftsRecord changeShiftsRecord) {
        return R.ok(changeShiftsRecordService.page(page, Wrappers.query(changeShiftsRecord)));
    }


    /**
     * 通过id查询交接班记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(changeShiftsRecordService.getById(id));
    }

    /**
     * 新增交接班记录
     * @param changeShiftsRecord 交接班记录
     * @return R
     */
    @ApiOperation(value = "新增交接班记录", notes = "新增交接班记录")
    @SysLog("新增交接班记录" )
    @PostMapping("/add")
    public R save(@RequestBody ChangeShiftsRecord changeShiftsRecord) {
        return R.ok(changeShiftsRecordService.save(changeShiftsRecord));
    }

    /**
     * 修改交接班记录
     * @param changeShiftsRecord 交接班记录
     * @return R
     */
    @ApiOperation(value = "修改交接班记录", notes = "修改交接班记录")
    @SysLog("修改交接班记录" )
    @PostMapping("/update")
    public R updateById(@RequestBody ChangeShiftsRecord changeShiftsRecord) {
        return R.ok(changeShiftsRecordService.updateById(changeShiftsRecord));
    }

    /**
     * 通过id删除交接班记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除交接班记录", notes = "通过id删除交接班记录")
    @SysLog("通过id删除交接班记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		ChangeShiftsRecord byId = changeShiftsRecordService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		return R.ok(changeShiftsRecordService.updateById(byId));
    }



	/**
	 * 点击跳转
	 * @return
	 */
	@ApiOperation(value = "点击跳转",notes = "点击跳转")
	@SysLog("点击跳转")
	@GetMapping("/preJump")
	public R preJump(){
		//查询一下当前护士是是否有作为唯一责任护士看护患者
		NursePatientCorrelation nursePatientCorrelation  = new NursePatientCorrelation();
		nursePatientCorrelation.setNurseId(SecurityUtils.getUser().getId()+""); // 护士id
		nursePatientCorrelation.setOnlyDutyNurse(0);//责任护士
		List<NursePatientCorrelationVo> nursePatientCorrelationVos = nursePatientCorrelationService.selectAll(nursePatientCorrelation);
		if (nursePatientCorrelationVos ==null || nursePatientCorrelationVos.size()==0){
			return R.failed(null,"您不是唯一责任护士，不需要交接班任务");
		}
		return R.ok(nursePatientCorrelationVos );
	}



	/**
	 * 查询要交班的任务
	 * @param task
	 * @return
	 */
	@ApiOperation(value = "查询要交班的任务",notes = "查询要交班的任务")
	@SysLog("查询要交班的任务")
	@GetMapping("/shiftsTask")
	public R shiftsTask(TaskVo task){

		/* 查询当前护士没有 交班的任务，同时查询一下子交接班表中是否  */
		task.setAssociatedNurse(SecurityUtils.getUser().getId()+""); //   护士id
		task.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室id
		List<ChangeShiftsVo> changeShifts = changeShiftsRecordService.shiftsTask(task);

		if (changeShifts.size()==0||changeShifts==null){
			return R.failed(null,"您没有要交接的任务,是否确定要交接当前患者");
		}
		return R.ok(changeShifts);
	}


	/***
	 * 护士点击保存交接班描述时
	 * @return
	 */
	@ApiOperation(value = "护士点击保存交接班描述时",notes = "护士点击保存交接班描述时")
	@SysLog("护士点击保存交接班描述时")
	@PostMapping("/keepRecords")
	public R keepRecords(@RequestBody List<ChangeShiftsDescribe> list){

		//  接收到前台传来的数据后，需要判断list集合中是否有值
		if(list.size()==0||list ==null){
			return R.failed(null,"没有需要保存的任务");
		}

		list.forEach(e -> {
			/* 判断一下发来的数据 是否有重复的数据源 */
			ChangeShiftsDescribe taskId = changeShiftsDescribeService.getTaskId(e.getTaskId());
			if (taskId!=null){
				/* 当通过任务id查询到有数据时是为修改 */
				e.setId(taskId.getId());
				R.ok(changeShiftsDescribeService.updateById(e));
			}else{
				/* 没有时候就是新增 */
				R.ok(changeShiftsDescribeService.save(e));
			}
		});
		return R.ok();
	}

	/**
	 * 点击交班记录
	 * @return
	 */
	@ApiOperation(value = "点击交班",notes = "点击交班")
	@SysLog("点击交班记录")
	@Transactional
	@PostMapping("/clickShifts")
	public R clickShiftsRecord(@RequestBody ChangeShiftsBo changeShiftsRecordBo){
		System.out.println("接到的数据*************"+changeShiftsRecordBo);
		Boolean aBoolean = nursePatientRecordService.stopNursePatient(changeShiftsRecordBo.getChangeShiftsRecord().getPatientId());
		if (aBoolean==false){
			R.failed(null,"护士患者断开关联失败！！！");
		}
		/* 交接班记录 */
		return  R.ok(changeShiftsRecordService.clickShiftsRecord(changeShiftsRecordBo.getChangeShiftsRecord(),changeShiftsRecordBo.getList()));
	}

	/**
	 * 数据全查
	 * @param changeShiftsRecord
	 * @return
	 */
	@ApiOperation(value = "数据全查查询交接班的患者",notes = "数据全查查询交接班的患者")
	@SysLog("数据全查查询交接班的患者")
	@GetMapping("/selectAll")
	public R selectAll(ChangeShiftsRecord changeShiftsRecord){
		/* 查询  */
		changeShiftsRecord.setState(0);
		return  R.ok(changeShiftsRecordService.selectAll(changeShiftsRecord));
	}


	/**
	 * 点击接收
	 * @return
	 */
	@ApiOperation(value = "点击接收任务,患者没有任务",notes = "点击接收任务,患者没有任务")
	@SysLog("点击接收任务,患者没有任务")
	@PostMapping("/clickSuccession")
	public R clickSuccession(@RequestBody ChangeShiftsBo changeShiftsRecordBo){
		System.out.println("接班数据源-------------------------"+changeShiftsRecordBo);

		return R.ok(changeShiftsRecordService.clickSuccession(changeShiftsRecordBo.getChangeShiftsRecord(),changeShiftsRecordBo.getList()));
	}








}
