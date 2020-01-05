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

package com.pig4cloud.pigx.ccxxicu.controller.patient;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisPatient;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDoctorsAdviceExtVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.PatientEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.bed.BedRecordService;
import com.pig4cloud.pigx.ccxxicu.service.device.UseRecordService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.*;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientRecordService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.piping.UseRecordsService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.ccxxicu.service.task.TimingExecutionService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 患者表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:17:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/patpatient" )
@Api(value = "patpatient", tags = "患者表管理")
public class PatientController {

    private final PatientService patientService;

	/**
	 * 护士患者关联
	 * */
	private final NursePatientCorrelationService nursePatientCorrelationService;

	/**
	 * 医嘱
	 */
	private final HisDoctorsAdviceExtService hisDoctorsAdviceExtService;

	/**
	 * 患者
	 */
	private final TaskSubTaskService taskSubTaskService;

	/**
	 * 长期任务
	 */
	private final TimingExecutionService timingExecutionService;

	/**
	 * 设备
	 */
	private final UseRecordService useRecordService;
	/**
	 * 管路
	 */
	private final UseRecordsService useRecordsService;

	/**
	 * 关联记录
	 */
	private final NursePatientRecordService nursePatientRecordService;
	/**
	 * 床位新增
	 */
	private final BedRecordService bedRecordService;

	/**
	 * 医嘱内容
	 */
	private final HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;
	/**
	 * 医嘱
	 */
	private final HisDoctorsAdviceService hisDoctorsAdviceService;
	/**
	 * his数据
	 */
	private final HisDataService hisDataService;

	/**
	 * his患者数据
	 */
	private final HisPatientService hisPatientService;

	/**
     * 分页查询
     * @param page 分页对象
     * @param patient 患者表F
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPatPatientPage(Page page, PatientBo patient) {
    	// 管理员登录
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(patientService.selectPaging(page,patient));
		}
    	patient.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+""); // 科室查询
        return R.ok(patientService.selectPaging(page,patient));
    }


    /**
     * 通过id查询患者表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(patientService.idSelect(id));
    }

    /**
     * 新增患者表
     * @param patient 患者表
     * @return R
     */
    @ApiOperation(value = "新增患者表", notes = "新增患者表")
    @SysLog("新增患者表" )
    @PostMapping("/add")
    public R save(@RequestBody Patient patient) {
    	patient.setPatientId(SnowFlake.getId()+"");// 雪花
		Integer deptId = SecurityUtils.getUser().getDeptId();
		if (deptId==null){
			return R.failed(null,"您没有科室不能新增患者！！！");
		}

		patient.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+""); // 科室

        return R.ok(patientService.save(patient));
    }

    /**
     * 修改患者表
     * @param patient 患者表
     * @return R
     */
    @ApiOperation(value = "修改患者表", notes = "修改患者表")
    @SysLog("修改患者表" )
    @PutMapping
    public R updateById(@RequestBody Patient patient) {
        return R.ok(patientService.updateById(patient));
    }

    /**
     * 通过id删除患者表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除患者表", notes = "通过id删除患者表")
    @SysLog("通过id删除患者表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(patientService.removeById(id));
    }

	/**
	 * 条件分页查询数据源
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@ApiOperation(value = "条件分页查询数据",notes = "条件分页查询数据源")
	@SysLog("条件分页查询数据源")
	@GetMapping("/selectPaging")
    public R selectPaging(Page page, PatientBo patientBo){

		if("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){ //护士长
			patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+""); // 科室查询
			return R.ok(patientService.selectPaging(page,patientBo));
		}if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){//护士
			patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+""); // 科室查询
			patientBo.setRegisterNurseId(SecurityUtils.getUser().getId()+"");//护士id
			return R.ok(patientService.selectPaging(page,patientBo));
		}

   	return R.ok(patientService.selectPaging(page,patientBo));
	}

	/**
	 * 患者入科成功
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "患者入科成功",notes = "患者入科成功")
	@SysLog("患者入科成功")
	@Transactional
	@GetMapping("/patientInScience/{patientId}")
	public R patientInScience(@PathVariable("patientId") String patientId){

		Patient patient = patientService.getByPatientId(patientId);
		//  将患者状态变为在科
		patient.setEntryState(PatientEnum.IN_SCIENCE.getCode());
		patient.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+""); //  入科科室

		/*同时修改his患者数据，将状态修改为在科*/

		HisPatient hisPatient = hisPatientService.hisPatientIdSelect(patient.getHisid());
		hisPatient.setEntryState(PatientEnum.IN_SCIENCE.getCode());
		boolean b1 = hisPatientService.updateById(hisPatient);
		if (!b1){
			return R.failed(500,"患者入科失败！！！");
		}

		/*当患者入科成功后，用入科患者的hisid 添加当前患者的医嘱数据 */
		boolean b = patientService.updateById(patient);
		if (!b){
			return R.failed(500,"患者入科失败！！！");
		}
		Thread thread = new Thread(){
			@Override
			public void run() {

				Boolean doctorsAdvice = hisDataService.hisDoctorsAdvice(patient.getHisid());
				if (doctorsAdvice){
					List<HisDoctorsAdvice> hisDoctorsAdvices = hisDoctorsAdviceService.notGenerate(patient.getHisid());

					if (CollectionUtils.isNotEmpty(hisDoctorsAdvices)){

						for (int i = 0 ;i<hisDoctorsAdvices.size();i++){

							HisDoctorsAdviceExtVo hisDoctorsAdviceExtVo = new HisDoctorsAdviceExtVo();
							hisDoctorsAdviceExtVo.setHisPatientId(patient.getPatientId()); // 患者id
							Boolean aBoolean = hisDataService.hisDoctorsAdviceExt(hisDoctorsAdviceExtVo);
							if (!aBoolean){
								System.out.println("患者医嘱添加失败！！");
							}
						}
						System.out.println("患者入科成功");

					}else {
						System.out.println("患者入科成功！！");
					}

				}else{
					System.out.println("患者医嘱添加失败！！");
				}

			}
		};
		thread.start();

		return R.ok("成功");
	}








	/**
	 * 在科患者
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@ApiOperation(value = "在科患者",notes = "在科患者")
	@SysLog("在科患者")
	@Transactional
	@GetMapping("/inSciencePatient")
	public R inSciencePatient(Page page, PatientBo patientBo){
		patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");  //科室id
		/* 通过当前登录的护士查询一下当前登录的护士相关的患者，判断一下登录的护士等级 */

		/* 判断一下当前登录的是谁*/
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 护士长查询当前科室 AdmissionDepartment */
			patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");
			return R.ok(patientService.inSciencePatient(page,patientBo));
		}else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 护士查询护士关联的患者，先通过护士id查询向关联的患者id*/

			NursePatientCorrelation nursePatientCorrelation = new NursePatientCorrelation();
			nursePatientCorrelation.setNurseId(SecurityUtils.getUser().getId()+"");
			List<NursePatientCorrelationVo> correlationList = nursePatientCorrelationService.selectAll(nursePatientCorrelation);

			List<Patient> patientList  = new ArrayList<>();
			for (int i =0;i<correlationList.size();i++){
				// 通过护士关联 患者的id查询相应的患者
				Patient byId = patientService.getByPatientId(correlationList.get(i).getPatientId());
				patientList.add(byId);
			}
			return R.ok(patientList);
		}

		return R.ok(patientService.inSciencePatient(page,patientBo));
	}




	/**
	 * 在科患者 用于流程  【入科流程查询入科中和在和患者】
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@ApiOperation(value = "在科患者全查，用于入科流程",notes = "在科患者全查，用于入科流程")
	@SysLog("在科患者全查，用于入科流程")
	@GetMapping("/inSciencePatientAll")
	public R inSciencePatientAll(Page page, PatientBo patientBo){
		patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");  //科室id
		return R.ok(patientService.inSciencePatient(page,patientBo));
	}




	/**
	 * 在科患者 用于流程  【出科流程中的在科患者以及出科中的患者】
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@ApiOperation(value = "在科患者全查，出科流程中的在科患者,以及出科中的患者",notes = "在科患者全查，出科流程中的在科患者以及出科中的患者")
	@SysLog("在科患者全查，出科流程中的在科患者以及出科中的患者")
	@GetMapping("/inSciencePatientPage")
	public R inSciencePatientPage(Page page, PatientBo patientBo){
		patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");  //科室id
		return R.ok(patientService.entryStateSelect(page,patientBo));
	}





	/**
	 * 通过患者的雪花id 查询当条患者的数据【用于患者入科时返回当条数据的id】
	 * @return PathVariable
	 */
	@ApiOperation(value = "通过患者的雪花id 查询当条患者的数据【用于患者入科时返回当条数据的id】",notes = "通过患者的雪花id 查询当条患者的数据【用于患者入科时返回当条数据的id】")
	@SysLog("通过患者的雪花id 查询当条患者的数据【用于患者入科时返回当条数据的id】")
	@GetMapping("/patientId/{id}")
	public R patientId(@PathVariable("id") String id){
		if (id !=null || id.equals("")) {
			return R.ok(patientService.getByPatientId(id).getId());
		}else {
			return R.failed(null,"没有当前患者id请联系管理员");
		}

	}

	/**
	 * 出科患者
	 * @param page
	 * @param patientBo
	 * @return
	 */
	@ApiOperation(value = "出科患者",notes = "出科患者")
	@SysLog("出科患者")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@GetMapping("/departurePatient")
	public R departurePatient( Page page, PatientBo patientBo){
		patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");  //科室id
		return R.ok(patientService.departurePatient(page,patientBo));
	}


	/**
	 * 通过患者的雪花id查询数据
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "通过患者的雪花id查询数据",notes = "通过患者的雪花id查询数据")
	@SysLog("通过患者的雪花id查询数据")
	@GetMapping("/getByPatientId/{patientId}")
	public R getByPatientId(@PathVariable("patientId") String patientId){
		return R.ok(patientService.getByPatientId(patientId));
	}




	/**
	 * 通过患者的雪花id查询数据【有床位】
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "通过患者的雪花id查询数据【有床位】",notes = "通过患者的雪花id查询数据【有床位】")
	@SysLog("通过患者的雪花id查询数据【有床位】")
	@GetMapping("/patientIdSelect/{patientId}")
	public R patientIdSelect(@PathVariable("patientId") String patientId){
		return R.ok(patientService.patientIdSelect(patientId));
	}

	/***
	 *
	 *患者出科，
	 *  1、查询当前在科患者展示给护士
	 *  2、查询出一个月内出科的患者展示给护士
	 *  3、患者的出科评估
	 *  4、患者满足出科时
	 *  5、结束医嘱、长期任务、任务
	 *  6、结束护理项目、仪器、管路、断开与护士的关联
	 *  7、将当前患者出科【展示出科类别、转科、出院、死亡 】
	 *
	 */

	@ApiOperation(value = "查询30天内的出科患者",notes = "查询30天内的出科患者")
	@SysLog("查询30天内的出科患者")
	@GetMapping("/timeSelectDeparturePatient")
	public R timeSelectDeparturePatient(Page page, PatientBo patientBo){
		patientBo.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+"");  //科室id
		return R.ok(patientService.timeSelectDeparturePatient(page,patientBo));
	}

	/**
	 * 将患者的状态修改为出科中
	 * @return
	 */
	@ApiOperation(value = "修改出科中状态",notes = "修改出科中状态")
	@SysLog("修改出科中状态")
	@GetMapping("/departureMiddle/{patientId}")
	public R departureMiddle(@PathVariable("patientId") String patientId){
		Patient byPatientId = patientService.getByPatientId(patientId);
		byPatientId.setDischargeTime(LocalDateTime.now()); // 出科时间
	byPatientId.setEntryState(PatientEnum.DEPARTURE_MIDDLE.getCode());
		return R.ok(patientService.updateById(byPatientId));
	}

	/**
	 *  停止医嘱，及相关任务
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "停止医嘱，及相关任务",notes = "停止医嘱，及相关任务")
	@SysLog("停止医嘱，及相关任务")
	@Transactional
	@GetMapping("/stopDoctorsAndTask/{patientId}")
	public R stopDoctorsAdviceAndTask(@PathVariable("patientId") String patientId){ //通过患者的id查询相关的医嘱的数据且同时将相应医嘱【任务】停止

		if (patientId ==null || patientId.equals("")){
			R.failed(null,"没有患者信息，请联系技术人员！！！");
		}

		/**
		 * 任务
		 */

		Boolean task = taskSubTaskService.shiftsEndTask(patientId);
		if (task==false){
		return R.failed(null,"当前患者结束任务失败！！！");
		}

		/**
		 * 长期任务
		 */
		Boolean aBoolean = timingExecutionService.stopTimingExecution(patientId);
		if (aBoolean==false){
			return R.failed(null,"当前患者结束定时任务失败！！！");
		}
		return R.ok(null,"操作成功！！！");
	}



	/**
	 * 断开设备、及管路、床位
	 * @param patientId
	 * @return
	 */
	@ApiOperation(value = "断开设备、及管路、床位",notes = "断开设备、及管路、床位")
	@SysLog("断开设备、及管路、床位")
	@GetMapping("/stopDeviceAndPiping/{patientId}")
	public R stopDeviceAndPiping(@PathVariable("patientId") String patientId){
		System.out.println("进行床位等操作。。。。。。。。。");
		if (patientId ==null || patientId.equals("")){
			return R.failed(null,"没有患者信息，请联系技术人员！！！");
		}
		Boolean aBoolean = useRecordService.stopDevice(patientId);
		if (aBoolean == false){
		return R.failed(null,"当前患者有未断开设备 ，请手动断开！！！");
		}

		Boolean aBoolean1 = useRecordsService.stopPiping(patientId);
		if (aBoolean1 == false){
			return R.failed(null,"当前患者有未断开管道，请手动断开！！！");
		}
		boolean b = bedRecordService.delPatientBed(patientId);
		if (b==false){
			return R.failed(null,"当前患者有未断开床位 ，请手动断开！！！");
		}

		return R.ok(null,"成功");
	}


	/**
	 * 患者的出科方式
	 * @return
	 */
	@ApiOperation(value = "患者的出科方式",notes = "患者的出科方式")
	@SysLog("患者的出科方式")
	@GetMapping("/patientDepartureType")
	public R patientDepartureType(){
		String icu_departure = new String();
		return R.ok("icu_departure","患者的出科方式！！！");
	}

	/**
	 * 修改患者信息，及断开患者与护士的关联
	 * @param patient
	 * @return
	 */
	@ApiOperation(value = "修改患者信息，及断开患者与护士的关联",notes = "修改患者信息，及断开患者与护士的关联")
	@SysLog("修改患者信息，及断开患者与护士的关联")
	@PostMapping("/stopNursAndPatient")
	public R stopNursAndPatient(@RequestBody Patient patient){

		if (patient==null){
			R.failed(null,"没有患者信息，请联系技术人员！！！");
		}
		if (patient.getPatientId()==null||patient.getPatientId().equals("")){
			R.failed(null,"没有患者id，当前患者没有入科！！！");
		}
		/*  通过患者的id查询当前患者与护士的关联关系*/
		Boolean aBoolean = nursePatientRecordService.stopNursePatient(patient.getPatientId());
		if (aBoolean==false){
			return R.failed(null,"护士与患者断开关联失败！！！");
		}
		patient.setDischargeTime(LocalDateTime.now()); // 出科时间
		Integer i = (int)Duration.between(patient.getEntranceTime(), LocalDateTime.now()).toHours();


		if (i<24&&i>0){
			patient.setSectionTime(1); //在科天数
		}if (i/24>0){
			patient.setSectionTime(i/24+1);
		}else{
			patient.setSectionTime(i);
		}

		patient.setEntryState(PatientEnum.DEPARTURE.getCode());//出科标识
		boolean b = patientService.updateById(patient);
		if (b==false){
			return R.failed(null,"修改患者出科失败！！！");
		}

		return R.ok(null,"患者出科成功！！！");
	}





}
