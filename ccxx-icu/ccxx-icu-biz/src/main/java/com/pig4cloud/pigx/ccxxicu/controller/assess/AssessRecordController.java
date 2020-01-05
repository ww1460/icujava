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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordPage;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessShowBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessRecordService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
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
 * 评估记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:58:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assessRecord")
@Api(value = "assessRecord", tags = "评估记录表管理")
public class AssessRecordController {

	private final AssessRecordService assessRecordService;
	private final AssessProjectRecordService assessProjectRecordService;
	private final ProjectService projectService;

	/**
	 * 分页查询
	 *
	 * @param page             分页对象
	 * @param assessRecordPage 评估记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getAssessRecordPage(Page page, AssessRecordPage assessRecordPage) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(assessRecordService.selectByPage(page, assessRecordPage));

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			assessRecordPage.setDeptId(user.getDeptId()+"");
			return R.ok(assessRecordService.selectByPage(page, assessRecordPage));
		}

		assessRecordPage.setNurseId(user.getId() + "");

		return R.ok(assessRecordService.selectByPage(page, assessRecordPage));
	}

	/**
	 * 获取某患者某评估的所有记录（用于折线图）
	 *
	 * @param assessShowBo
	 * @return
	 */
	@ApiOperation(value = "获取某患者某评估的所有记录", notes = "获取某患者某评估的所有记录")
	@GetMapping("/getMapData")
	public R getMapData(AssessShowBo assessShowBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		Project project = projectService.selectByCode(assessShowBo.getProjectCode(), 4);

		if (project==null) {

			return R.failed("该评估尚未维护项目及内容");

		}


		//返回记录中该患者的记录数据
		return R.ok(assessRecordService.list(Wrappers.<AssessRecord>query().lambda()
				.eq(AssessRecord::getPatientId, assessShowBo.getPatientId())
				.eq(AssessRecord::getProjectId, project.getProjectId())
				.eq(AssessRecord::getDelFlag, 0)
				.orderByDesc(AssessRecord::getCreateTime)));
	}


	/**
	 * 通过id查询评估记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(assessRecordService.getById(id));
	}


	/**
	 * 修改评估记录表
	 *
	 * @param assessRecordBo 评估记录表
	 * @return R
	 */
	@ApiOperation(value = "修改评估记录表", notes = "修改评估记录表")
	@SysLog("修改评估记录表")
	@PostMapping("/update")
	public R updateById(@RequestBody AssessRecordBo assessRecordBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}


		return R.ok(assessRecordService.updateDate(assessRecordBo));
	}

	/**
	 * 查询某患者的评估项目
	 *
	 * @param patientId patientId
	 * @return R
	 */
	@ApiOperation(value = "查询某患者的评估项目", notes = "查询某患者的评估项目")
	@SysLog("查询某患者的评估项目")
	@GetMapping("/patientAssessProject")
	public R getPatientAssessProject(@RequestParam String patientId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(patientId)) {
			return R.failed("系统错误！");
		}

		return R.ok(assessRecordService.getPatientAssessProject(patientId));
	}

	/**
	 * 通过id删除评估记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除评估记录表", notes = "通过id删除评估记录表")
	@SysLog("通过id删除评估记录表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(id+"")) {
			return R.failed(1, "操作失败！");
		}

		//查询该数据
		AssessRecord byId = assessRecordService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");
		//查询该记录的详细数据
		List<AssessProjectRecord> assessRecord = assessProjectRecordService.getAssessRecord(byId.getAssessRecordId());
		if (CollectionUtils.isEmpty(assessRecord)) {

			return R.ok(assessRecordService.updateById(byId));

		}
		assessRecord.forEach(ar -> {
			ar.setDelFlag(1);
			ar.setDelTime(LocalDateTime.now());
			ar.setDelUserId(user.getId() + "");
		});

		boolean b = assessProjectRecordService.updateBatchById(assessRecord);

		if (!b) {
			return R.failed(1, "操作有误！");
		}

		return R.ok(assessRecordService.updateById(byId));
	}



}
