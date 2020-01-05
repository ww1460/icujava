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

package com.pig4cloud.pigx.ccxxicu.controller.hisdata;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisPatient;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.common.emums.PatientEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisPatientService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 患者his传输表
 *
 * @author pigx code generator
 * @date 2019-08-27 15:58:29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/hispatient" )
@Api(value = "hispatient", tags = "患者his传输表管理")
public class HisPatientController {

    private final HisPatientService hisPatientService;

	/**
	 * 患者
	 */
	private final PatientService patientService;



    /**
     * 分页查询
     * @param page 分页对象
     * @param hisPatient 患者his传输表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisPatientPage(Page page, HisPatient hisPatient) {

		// 管理员登录
		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(hisPatientService.selectPaging(page, hisPatient));
		}
		hisPatient.setAdmissionDept(SecurityUtils.getUser().getDeptId()+""); // 科室查询
		return R.ok(hisPatientService.selectPaging(page, hisPatient));
    }


	/**
	 * 测试查询
	 * @return
	 */
	@Inner(value = false)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/test")
	public List<HisPatient> test() {
		return hisPatientService.selectAll();
	}

    /**
     * 通过id查询患者his传输表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(hisPatientService.getById(id));
    }

    /**
     * 新增患者his传输表
     * @param hisPatient 患者his传输表
     * @return R
     */
    @ApiOperation(value = "新增患者his传输表", notes = "新增患者his传输表")
    @SysLog("新增患者his传输表" )
    @PostMapping("/add")
    public R save(@RequestBody HisPatient hisPatient) {
    	hisPatient.setDeptId(SecurityUtils.getUser().getDeptId()+"");
    	return R.ok(hisPatientService.save(hisPatient));
    }

    /**
     * 修改患者his传输表
     * @param hisPatient 患者his传输表
     * @return R
     */
    @ApiOperation(value = "修改患者his传输表", notes = "修改患者his传输表")
    @SysLog("修改患者his传输表" )
   @PostMapping("/update")
    public R updateById(@RequestBody HisPatient hisPatient) {
        return R.ok(hisPatientService.updateById(hisPatient));
    }

    /**
     * 通过id删除患者his传输表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除患者his传输表", notes = "通过id删除患者his传输表")
    @SysLog("通过id删除患者his传输表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(hisPatientService.removeById(id));
    }






	/**
	 * 待入科患者添加到患者表中的数据
	 */
	@ApiOperation(value = "待入科患者添加到患者表中",notes = "待入科患者添加到患者表中")
	@SysLog("待入科患者添加到患者表中")
	@Transactional
	@PostMapping("/enrolledPatient")
	public R enrolledPatient(@RequestBody String id){

		/* 查询当前护护士有没有科室 */
		if (SecurityUtils.getUser().getDeptId()+""==null&&(SecurityUtils.getUser().getDeptId()+"").equals("")){
			return R.failed(null,"当前护士没有科室！！！！请确定科室信息！！！！");
		}

		/* 通过前台传来的待入科患者id */
		HisPatient hisPatient = hisPatientService.getById(id);
		if (hisPatient ==null){
			return R.failed(null,"没有查询到相应的患者信息");
		}
		if (hisPatient.getEntryState()==PatientEnum.ENROLLED_EN_ROUTE.getCode()){
			return R.failed(null,"当前患者已入科，请勿重复点击！！！！");
		}

		//  当数据不为空的情况，将查询出来的待入科患者信息添加到患者表中，

		Patient patPatient = new Patient();
		/* 雪花生成id */
		String patientId = SnowFlake.getId()+"";
		patPatient.setPatientId(patientId);                             // id
		patPatient.setHisid(hisPatient.getHisPatientId());                            // HIS id
		patPatient.setName(hisPatient.getName());                              // 姓名
		patPatient.setTimeOfAdmission(hisPatient.getTimeOfAdmission());       // 入院时间
		patPatient.setGender(hisPatient.getGender());                          // 性别
		patPatient.setBirthday(hisPatient.getBirthday());                      // 初始日期
		patPatient.setBlood(hisPatient.getBlood());                            // 血型
		patPatient.setNationality(hisPatient.getNationality());                // 国籍
		patPatient.setNation(hisPatient.getNation());                          // 民族
		patPatient.setIdType(hisPatient.getIdType());                          // 身份证类型
		patPatient.setIdNumber(hisPatient.getIdNumber());                      // 身份证号
		patPatient.setPhone(hisPatient.getPhone());                            // 患者联系电话
		patPatient.setMarriage(hisPatient.getMarriage());                      // 婚姻状况r
		patPatient.setCareer(hisPatient.getCareer());                          // 患者职业
		patPatient.setAge(hisPatient.getAge());                                // 患者年龄
		patPatient.setMonthage(hisPatient.getMonthAge());                      // 患者月龄
		patPatient.setBirthweight(hisPatient.getBirthWeight());                // 新生儿出生体重
		patPatient.setHospitalweight(hisPatient.getAdmissionWeight());          // 新生儿入院体重
		patPatient.setMedicalRecords(hisPatient.getMedicalRecords());          // 患者病历号
		patPatient.setOutpatient(hisPatient.getOutpatient());                  // 患者门诊号
		patPatient.setHospitalnumber(hisPatient.getHospitalnumber());          // 患者住院号
		patPatient.setOhip(hisPatient.getOhip());                              // 患者健康卡号
		patPatient.setSeedoctor(hisPatient.getSeedoctor());                    // 患者就诊卡号
		patPatient.setCurrentaddressprovince(hisPatient.getCurrentAddressProvince());  //患者现住省份
		patPatient.setCurrentaddresscity(hisPatient.getCurrentAddressCity());  // 现居住城市
		patPatient.setCurrentaddresscounty(hisPatient.getCurrentAddressCounty());// 患者现居住地址区县
		patPatient.setCurrentaddress(hisPatient.getContactAddress());           // 现居住详细地址
		patPatient.setContactperson(hisPatient.getOtherContacts());             // 患者其他联系人
		patPatient.setRelation(hisPatient.getRelation());                       // 联系人与患者的关系
		patPatient.setContactlocation(hisPatient.getContactAddress());         // 联系人所在详情地址
		patPatient.setContactphone(hisPatient.getContactPhone());               // 联系人联系电话
		patPatient.setAdmissionDepartment(SecurityUtils.getUser().getDeptId()+""); //  入科科室
		patPatient.setAdmissionPath(hisPatient.getAdmissionPath());             // 患者入科途径
		patPatient.setTurndepartment(hisPatient.getTransferDepartments());           // 患者入科（转科来源科室）
		patPatient.setFrequency(hisPatient.getFrequency());                     // 患者住院次数
		LocalDateTime date = LocalDateTime.now();
		patPatient.setEntranceTime(date);                                    // 患者入科时间
		patPatient.setAllergichistory(hisPatient.getAllergichi());         // 患者过敏史
		patPatient.setDiagnosis(hisPatient.getDiagnosis());                     // 患者病情描述
		patPatient.setEntryState(PatientEnum.ENROLLED_EN_ROUTE.getCode()); ///患者入科状态   【入科中】
		// 将数据新增，同时将当条数据的状态改为入科中
		hisPatient.setEntryState(PatientEnum.ENROLLED_EN_ROUTE.getCode());
		hisPatient.setUpdateTime(LocalDateTime.now());// 修改时间
		hisPatient.setPatientId(patientId);//雪花id
		boolean b = hisPatientService.updateById(hisPatient);

		if (!b) {
			return R.failed(null,"修改状态失败");
		}

		boolean save = patientService.save(patPatient);
		if(!save){
			return R.failed(null,"用户入科失败");
		}

		return R.ok(patientId,"成功");
	}

	/**
	 * 入科患者接口
	 * @param hisPatient
	 * @return
	 */
	@ApiOperation(value = "admissionPatient",notes = "入科患者接口")
	@PostMapping("/admissionPatient")
	public R admissionPatient(@RequestBody HisPatient hisPatient){


		Boolean aBoolean = hisPatientService.hisPatientAdd(hisPatient);
		if (aBoolean){
			return R.ok(0,"患者入科成功");
		}else{
			return R.failed(1,"患者入科失败");
		}
	}



}
