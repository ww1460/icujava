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

package com.pig4cloud.pigx.ccxxicu.controller.assess;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.EntranceAssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.assess.EntranceAssessRecordService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 入科评估记录表
 *
 * @author pigx code generator
 * @date 2019-09-05 13:44:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/entranceAssessRecord")
@Api(value = "entranceAssessRecord", tags = "入科评估记录表管理")
public class EntranceAssessRecordController {

	private final EntranceAssessRecordService entranceAssessRecordService;

	private final PatientService patientService;

	/**
	 * 分页查询
	 *
	 * @param page                 分页对象
	 * @param entranceAssessRecord 入科评估记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getEntranceAssessRecordPage(Page page, EntranceAssessRecord entranceAssessRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		entranceAssessRecord.setDelFlag(0);

		return R.ok(entranceAssessRecordService.page(page, Wrappers.query(entranceAssessRecord)));
	}


	/**
	 * 通过id查询入科评估记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(entranceAssessRecordService.getById(id));
	}

	/**
	 * 新增入科评估记录表
	 *
	 * @param entranceAssessRecord 入科评估记录表
	 * @return R
	 */
	@ApiOperation(value = "新增入科评估记录表", notes = "新增入科评估记录表")
	@SysLog("新增入科评估记录表")
	@PostMapping("/add")
	public R save(@RequestBody EntranceAssessRecord entranceAssessRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(entranceAssessRecord.getPatientId())) {
			return R.failed(1, "操作失败！");
		}


		entranceAssessRecord.setEntranceAssessRecordId(SnowFlake.getId() + "");
		entranceAssessRecord.setCreateUserId(user.getId() + "");
		entranceAssessRecord.setCreateTime(LocalDateTime.now());
		entranceAssessRecord.setDelFlag(0);
		entranceAssessRecord.setDeptId(user.getDeptId() + "");

		String drugAllergy = entranceAssessRecord.getDrugAllergy();//过敏药物
		String drugAllergyRemarks = entranceAssessRecord.getDrugAllergyRemarks();
		String allergenicFood = entranceAssessRecord.getAllergenicFood();//过敏食物
		String allergenicFoodRemarks = entranceAssessRecord.getAllergenicFoodRemarks();
		String otherAllergens = entranceAssessRecord.getOtherAllergens();//其他过敏源
		String otherAllergensRemarks = entranceAssessRecord.getOtherAllergensRemarks();

		StringBuffer allergenic = new StringBuffer();



		if (drugAllergy.contains("-其他")) {

			String[] split = drugAllergy.split("-");

			for (int i = 0; i < split.length - 1; i++) {

				allergenic.append(split[i] + ";");

			}

			allergenic.append(StringUtils.isEmpty(drugAllergyRemarks) ? "" : drugAllergyRemarks+";");

		}

		if (StringUtils.isNotEmpty(drugAllergy) && drugAllergy.equals("其他")) {

			allergenic.append(StringUtils.isEmpty(drugAllergyRemarks) ? "" : drugAllergyRemarks+";");

		}


		if (!drugAllergy.contains("-其他")&&StringUtils.isNotEmpty(drugAllergy)&& !drugAllergy.equals("否认")) {

			String[] split = drugAllergy.split("-");

			for (int i = 0; i < split.length; i++) {

				allergenic.append(split[i] + ";");

			}
		}



		if (allergenicFood.contains("-其他")) {

			String[] split = allergenicFood.split("-");

			for (int i = 0; i < split.length - 1; i++) {

				allergenic.append(split[i] + ";");

			}

			allergenic.append(StringUtils.isEmpty(allergenicFoodRemarks) ? "" : allergenicFoodRemarks+";");

		}

		if (StringUtils.isNotEmpty(allergenicFood) && drugAllergy.equals("其他")) {

			allergenic.append(StringUtils.isEmpty(allergenicFoodRemarks) ? "" : allergenicFoodRemarks+";");

		}

		if (!allergenicFood.contains("-其他")&&StringUtils.isNotEmpty(allergenicFood)&& !allergenicFood.equals("否认") ) {

			String[] split = allergenicFood.split("-");

			for (int i = 0; i < split.length; i++) {

				allergenic.append(split[i] + ";");

			}
		}
		if (StringUtils.isNotEmpty(otherAllergens) && drugAllergy.equals("其他")) {

			allergenic.append(StringUtils.isEmpty(otherAllergensRemarks) ? "" : otherAllergensRemarks+";");

		}
		if (otherAllergens.contains("-其他")) {

			String[] split = otherAllergens.split("-");

			for (int i = 0; i < split.length - 1; i++) {

				allergenic.append(split[i] + ";");

			}

			allergenic.append(StringUtils.isEmpty(otherAllergensRemarks) ? "" : otherAllergensRemarks+";");

		}

		if (!otherAllergens.contains("-其他")&&StringUtils.isNotEmpty(otherAllergens)&& !otherAllergens.equals("否认") ) {

			String[] split = otherAllergens.split("-");

			for (int i = 0; i < split.length; i++) {

				allergenic.append(split[i] + ";");

			}
		}

		if (StringUtils.isNotEmpty(allergenic)) {

			Patient byPatientId = patientService.getByPatientId(entranceAssessRecord.getPatientId());

			if (byPatientId!=null) {

				byPatientId.setAllergichistory(allergenic.toString());
				patientService.updateById(byPatientId);

			}

		}


		return R.ok(entranceAssessRecordService.save(entranceAssessRecord));
	}


	/**
	 * 通过患者查询入科评估记录表
	 *
	 * @param patientId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByPatient/{patientId}")
	public R getByPatient(@PathVariable("patientId") String patientId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(patientId)) {
			return R.failed(1, "操作失败！");
		}


		return R.ok(entranceAssessRecordService.list(Wrappers.<EntranceAssessRecord>query().lambda()
				.eq(EntranceAssessRecord::getDelFlag, 0)
				.eq(EntranceAssessRecord::getPatientId, patientId)
		));
	}


	/**
	 * 修改入科评估记录表
	 *
	 * @param entranceAssessRecord 入科评估记录表
	 * @return R
	 */
	@ApiOperation(value = "修改入科评估记录表", notes = "修改入科评估记录表")
	@SysLog("修改入科评估记录表")
	@PostMapping("/update")
	public R updateById(@RequestBody EntranceAssessRecord entranceAssessRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		entranceAssessRecord.setUpdateUserId(user.getId() + "");

		String drugAllergy = entranceAssessRecord.getDrugAllergy();//过敏药物
		String drugAllergyRemarks = entranceAssessRecord.getDrugAllergyRemarks();
		String allergenicFood = entranceAssessRecord.getAllergenicFood();//过敏食物
		String allergenicFoodRemarks = entranceAssessRecord.getAllergenicFoodRemarks();
		String otherAllergens = entranceAssessRecord.getOtherAllergens();//其他过敏源
		String otherAllergensRemarks = entranceAssessRecord.getOtherAllergensRemarks();

		StringBuffer allergenic = new StringBuffer();

		if (StringUtils.isNotEmpty(drugAllergy) && drugAllergy.equals("其他")) {

			allergenic.append(StringUtils.isEmpty(drugAllergyRemarks) ? "" : drugAllergyRemarks+";");

		}

		if (drugAllergy.contains("-其他")) {

			String[] split = drugAllergy.split("-");

			for (int i = 0; i < split.length - 1; i++) {

				allergenic.append(split[i] + ";");

			}

			allergenic.append(StringUtils.isEmpty(drugAllergyRemarks) ? "" : drugAllergyRemarks+";");

		}


		if (!drugAllergy.contains("-其他")&&StringUtils.isNotEmpty(drugAllergy)&& !drugAllergy.equals("否认")) {

			String[] split = drugAllergy.split("-");

			for (int i = 0; i < split.length; i++) {

				allergenic.append(split[i] + ";");

			}
		}


		if (StringUtils.isNotEmpty(allergenicFood) && drugAllergy.equals("其他")) {

			allergenic.append(StringUtils.isEmpty(allergenicFoodRemarks) ? "" : allergenicFoodRemarks+";");

		}
		if (allergenicFood.contains("-其他")) {

			String[] split = allergenicFood.split("-");

			for (int i = 0; i < split.length - 1; i++) {

				allergenic.append(split[i] + ";");

			}

			allergenic.append(StringUtils.isEmpty(allergenicFoodRemarks) ? "" : allergenicFoodRemarks+";");

		}

		if (!allergenicFood.contains("-其他")&&StringUtils.isNotEmpty(allergenicFood)&& !allergenicFood.equals("否认") ) {

			String[] split = allergenicFood.split("-");

			for (int i = 0; i < split.length; i++) {

				allergenic.append(split[i] + ";");

			}
		}
		if (StringUtils.isNotEmpty(otherAllergens) && drugAllergy.equals("其他")) {

			allergenic.append(StringUtils.isEmpty(otherAllergensRemarks) ? "" : otherAllergensRemarks+";");

		}
		if (otherAllergens.contains("-其他")) {

			String[] split = otherAllergens.split("-");

			for (int i = 0; i < split.length - 1; i++) {

				allergenic.append(split[i] + ";");

			}

			allergenic.append(StringUtils.isEmpty(otherAllergensRemarks) ? "" : otherAllergensRemarks+";");

		}

		if (!otherAllergens.contains("-其他")&&StringUtils.isNotEmpty(otherAllergens)&& !otherAllergens.equals("否认") ) {

			String[] split = otherAllergens.split("-");

			for (int i = 0; i < split.length; i++) {

				allergenic.append(split[i] + ";");

			}
		}

		if (StringUtils.isNotEmpty(allergenic)) {

			Patient byPatientId = patientService.getByPatientId(entranceAssessRecord.getPatientId());

			if (byPatientId!=null) {

				byPatientId.setAllergichistory(allergenic.toString());
				patientService.updateById(byPatientId);

			}

		}



		return R.ok(entranceAssessRecordService.updateById(entranceAssessRecord));
	}

	/**
	 * 通过id删除入科评估记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除入科评估记录表", notes = "通过id删除入科评估记录表")
	@SysLog("通过id删除入科评估记录表")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		EntranceAssessRecord byId = entranceAssessRecordService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		return R.ok(entranceAssessRecordService.updateById(byId));
	}

}
