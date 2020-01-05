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

package com.pig4cloud.pigx.ccxxicu.controller.illnessNursing;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IllnessAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessAdviceService;
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
 * 病情护理病情护理建议表
 *
 * @author pigx code generator
 * @date 2019-09-09 11:21:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/illnessAdvice")
@Api(value = "illnessAdvice", tags = "病情护理病情护理建议表管理")
public class IllnessAdviceController {

	private final IllnessAdviceService illnessAdviceService;

	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param illnessAdviceBo 病情护理病情护理建议表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getIllnessAdvicePage(Page page, IllnessAdviceBo illnessAdviceBo) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(illnessAdviceService.selectByPage(page, illnessAdviceBo));
	}


	/**
	 * 通过id查询病情护理病情护理建议表
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

			R.failed(1, "操作有误！");

		}
		return R.ok(illnessAdviceService.getById(id));
	}


	/**
	 * 通过id查询病情护理病情护理建议表
	 *
	 * @param illnessProjectId 项目id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByProject/{illnessProjectId}")
	public R getByProject(@PathVariable("illnessProjectId") String illnessProjectId) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(illnessProjectId)) {
			return R.failed(1, "操作失败！");
		}


		return R.ok(illnessAdviceService.list(Wrappers.<IllnessAdvice>query().lambda()
				.eq(IllnessAdvice::getDelFlag, 0)
				.eq(IllnessAdvice::getIllnessProjectId, illnessProjectId)
		));
	}

	/**
	 * 通过id查询病情护理病情护理建议表
	 *
	 * @param illnessNursingState 项目id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@PostMapping("/getAdvice")
	public R getAdvice(@RequestBody IllnessNursingState illnessNursingState) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(illnessAdviceService.selectByProject(illnessNursingState));
	}




	/**
	 * 新增病情护理病情护理建议表
	 *
	 * @param illnessAdvice 病情护理病情护理建议表
	 * @return R
	 */
	@ApiOperation(value = "新增病情护理病情护理建议表", notes = "新增病情护理病情护理建议表")
	@SysLog("新增病情护理病情护理建议表")
	@PostMapping("/add")
	public R save(@RequestBody IllnessAdvice illnessAdvice) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (StringUtils.isEmpty(illnessAdvice.getIllnessProjectId())) {
			return R.failed(1, "操作失败！");
		}

		illnessAdvice.setIllnessAdviceId(SnowFlake.getId() + "");
		illnessAdvice.setDelFlag(0);
		illnessAdvice.setCreateUserId(user.getId() + "");
		illnessAdvice.setCreateTime(LocalDateTime.now());

		return R.ok(illnessAdviceService.save(illnessAdvice));
	}

	/**
	 * 修改病情护理病情护理建议表
	 *
	 * @param illnessAdvice 病情护理病情护理建议表
	 * @return R
	 */
	@ApiOperation(value = "修改病情护理病情护理建议表", notes = "修改病情护理病情护理建议表")
	@SysLog("修改病情护理病情护理建议表")
	@PostMapping("/update")
	public R updateById(@RequestBody IllnessAdvice illnessAdvice) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		illnessAdvice.setUpdateUserId(user.getId() + "");

		return R.ok(illnessAdviceService.updateById(illnessAdvice));
	}

	/**
	 * 通过id删除病情护理病情护理建议表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除病情护理病情护理建议表", notes = "通过id删除病情护理病情护理建议表")
	@SysLog("通过id删除病情护理病情护理建议表")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		return R.ok(illnessAdviceService.delByUpdate(id));
	}


}
