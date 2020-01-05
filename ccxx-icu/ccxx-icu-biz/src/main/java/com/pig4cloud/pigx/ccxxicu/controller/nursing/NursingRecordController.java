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

package com.pig4cloud.pigx.ccxxicu.controller.nursing;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.*;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingRecordShow;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingRecordService;
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
 * 护理记录
 *
 * @author pigx code generator
 * @date 2019-08-21 13:46:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursingRecord")
@Api(value = "nursingRecord", tags = "护理记录管理")
public class NursingRecordController {

	private final NursingRecordService nursingRecordService;

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param nursingRecordBo 护理记录
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getNursingRecordPage(Page page, NursingRecordBo nursingRecordBo) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(nursingRecordService.selectByPage(page, nursingRecordBo));

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			nursingRecordBo.setDeptId(user.getDeptId() + "");
			return R.ok(nursingRecordService.selectByPage(page, nursingRecordBo));
		}

		nursingRecordBo.setNurseId(user.getId() + "");
		return R.ok(nursingRecordService.selectByPage(page, nursingRecordBo));
	}


	/**
	 * 护理记录三
	 *
	 * @param nursingBo 护理记录
	 * @return
	 */
	@ApiOperation(value = "护理记录三", notes = "护理记录三")
	@PostMapping("/getReportThree")
	public R getReportThree(@RequestBody NursingBo nursingBo) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if (StringUtils.isEmpty(nursingBo.getPatientId())) {
			return R.ok(null);
		}


		return R.ok(nursingRecordService.getReport(nursingBo));

	}


	/**
	 * 护理模板新增护理记录
	 *
	 * @param templateRecordBo 护理记录
	 * @return R
	 */
	@ApiOperation(value = "新增护理记录", notes = "新增护理记录")
	@SysLog("新增护理记录")
	@PostMapping("/templateAdd")
	public R templateAdd(@RequestBody TemplateRecordBo templateRecordBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(templateRecordBo.getPatientId())) {
			return R.failed(1, "操作失败！");
		}


		//新增护理记录
		boolean save = nursingRecordService.templateAdd(templateRecordBo);

		return R.ok(save);
	}

	/**
	 * 悬浮窗录入
	 *
	 * @param windowsEnterBo 护理记录
	 * @return R
	 */
	@ApiOperation(value = "悬浮窗录入", notes = "悬浮窗录入")
	@SysLog("悬浮窗录入")
	@PostMapping("/windowsEnter")
	public R windowsEnter(@RequestBody WindowsEnterBo windowsEnterBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(windowsEnterBo.getPatientId())) {
			return R.failed(1, "操作失败！");
		}


		//新增护理记录
		boolean save = nursingRecordService.windowsAdd(windowsEnterBo);

		return R.ok(save);
	}


	/**
	 * 病情护理
	 *
	 * @param illnessRecordBoList 病情护理
	 * @return R
	 */
	@ApiOperation(value = "病情护理", notes = "病情护理")
	@SysLog("病情护理")
	@PostMapping("/Illness")
	public R Illness(@RequestBody List<IllnessRecordBo> illnessRecordBoList) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		//新增护理记录
		boolean save = nursingRecordService.illness(illnessRecordBoList);

		return R.ok(save);
	}


	/**
	 * 通过id查询护理记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		return R.ok(nursingRecordService.getById(id));
	}

	/**
	 * 新增护理记录
	 *
	 * @param nursingRecord 护理记录
	 * @return R
	 */
	@ApiOperation(value = "新增护理记录", notes = "新增护理记录")
	@SysLog("新增护理记录")
	@PostMapping("/add")
	public R save(@RequestBody NursingRecord nursingRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		nursingRecord.setCreateTime(LocalDateTime.now());
		nursingRecord.setCreateUserId(user.getId() + "");
		nursingRecord.setDelFlag(0);
		nursingRecord.setNursingRecordId(SnowFlake.getId() + "");

		return R.ok(nursingRecordService.save(nursingRecord));
	}

	/**
	 * 修改护理记录
	 *
	 * @param nursingRecordShow 护理记录
	 * @return R
	 */
	@ApiOperation(value = "修改护理记录", notes = "修改护理记录")
	@SysLog("修改护理记录")
	@PostMapping("/update")
	public R updateById(@RequestBody NursingRecordShow nursingRecordShow) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return  R.failed(1, "操作有误！");

		}

		return R.ok(nursingRecordService.updateNursingRecord(nursingRecordShow));
	}

	/**
	 * 修改单条护理记录的前提查看
	 *
	 * @return
	 */
	@ApiOperation(value = "修改单条护理记录的前提查看", notes = "修改单条护理记录的前提查看")
	@SysLog("修改护理记录的前提查看")
	@GetMapping("/getNursingRecord/{id}")
	public R getNursingRecord(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		NursingRecord byId = nursingRecordService.getById(id);
		//如果修改者是护士  如果该记录者不是本人 不能进行修改
		if (roleCodes.get(0).equals("ROLE_NURSE")) {
			//护士
			if (!byId.getCreateUserId().equals(user.getId() + "")) {

				return R.failed(1, "权限不足！");

			}
		}

		return nursingRecordService.getNursingRecord(byId);
	}


	/**
	 * 通过id删除护理记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除护理记录", notes = "通过id删除护理记录")
	@SysLog("通过id删除护理记录")
	@GetMapping("/delete/{id}")
	public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		NursingRecord byId = nursingRecordService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");

		return R.ok(nursingRecordService.deleteNursingRecord(byId));
	}

}
