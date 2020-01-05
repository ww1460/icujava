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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.NursingTaskRelation;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.task.NursingTaskRelationService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 护理项目与任务模板关联表
 *
 * @author pigx code generator
 * @date 2019-08-26 16:39:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursingtaskrelation")
@Api(value = "nursingtaskrelation", tags = "护理项目与任务模板关联表管理")
public class NursingTaskRelationController {

	private final NursingTaskRelationService nursingTaskRelationService;

	/**
	 * 项目类型
	 */
	private final ProjectService projectService;


	/**
	 * 分页查询
	 *
	 * @param page                分页对象
	 * @param nursingTaskRelation 护理项目与任务模板关联表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getTakNursingTaskRelationPage(Page page, NursingTaskRelation nursingTaskRelation) {
		return R.ok(nursingTaskRelationService.selectPaging(page, nursingTaskRelation));
	}


	/**
	 * 通过id查询护理项目与任务模板关联表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(nursingTaskRelationService.getById(id));
	}

	/**
	 * 新增护理项目与任务模板关联表
	 *
	 * @param nursingTaskRelation 护理项目与任务模板关联表
	 * @return R
	 */
	@ApiOperation(value = "新增护理项目与任务模板关联表", notes = "新增护理项目与任务模板关联表")
	@SysLog("新增护理项目与任务模板关联表")
	@PostMapping("/add")
	public R save(@RequestBody NursingTaskRelation nursingTaskRelation) {
		return R.ok(nursingTaskRelationService.save(nursingTaskRelation));
	}

	/**
	 * 修改护理项目与任务模板关联表
	 *
	 * @param nursingTaskRelation 护理项目与任务模板关联表
	 * @return R
	 */
	@ApiOperation(value = "修改护理项目与任务模板关联表", notes = "修改护理项目与任务模板关联表")
	@SysLog("修改护理项目与任务模板关联表")
	@PutMapping("/add")
	public R updateById(@RequestBody NursingTaskRelation nursingTaskRelation) {
		return R.ok(nursingTaskRelationService.updateById(nursingTaskRelation));
	}

	/**
	 * 通过id删除护理项目与任务模板关联表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除护理项目与任务模板关联表", notes = "通过id删除护理项目与任务模板关联表")
	@SysLog("通过id删除护理项目与任务模板关联表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return R.ok(nursingTaskRelationService.removeById(id));
	}

	/**
	 * 条件数据全查
	 *
	 * @param nursingTaskRelation
	 * @return
	 */
	@ApiOperation(value = "条件数据全查", notes = "条件数据全查")
	@SysLog("条件数据全查")
	@GetMapping("/selectAll")
	public R selectAll(NursingTaskRelation nursingTaskRelation) {
		nursingTaskRelation.setDeptId(SecurityUtils.getUser().getDeptId() + ""); // 科室id
		return R.ok(nursingTaskRelationService.selectAll(nursingTaskRelation));
	}


}
