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

package com.pig4cloud.pigx.ccxxicu.controller.nurse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.PersonalNotes;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.PersonalNotesService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 护士个人笔记
 *
 * @author pigx code generator
 * @date 2019-08-05 09:36:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/personalNotes")
@Api(value = "personalNotes", tags = "护士个人笔记管理")
public class PersonalNotesController {

	private final PersonalNotesService personalNotesService;

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param personalNotes 护士个人笔记
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@SysLog("分页查询")
	@GetMapping("/page")
	public R getNurPersonalNotesPage(Page page, PersonalNotes personalNotes) {

		personalNotes.setDelFlag(0);

		return R.ok(personalNotesService.page(page, Wrappers.query(personalNotes)));
	}

	/**
	 * 按时间查询当前护士的当月数据
	 *
	 * @param personalNotes 某月的时间点
	 * @return
	 */
	@ApiOperation(value = "按时间查询当月数据", notes = "按时间查询当月数据")
	@PostMapping("/getByDate")
	public R getByDate(@RequestBody PersonalNotes personalNotes) {
		return R.ok(personalNotesService.selectByDate(personalNotes.getCreateTime()));
	}

	/**
	 * 查询护士某天的笔记
	 * @param personalNotes 某天的时间 格式 yyyy-mm-DD
	 * @return
	 */
	@ApiOperation(value = "查询护士某天数据", notes = "查询护士某天数据")
	@SysLog("查询护士某天数据")
	@PostMapping("/getByDay")
	public R getByDay(@RequestBody PersonalNotes personalNotes) {
		return R.ok(personalNotesService.getByDay(personalNotes));
	}


	/**
	 * 通过id查询护士个人笔记
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@SysLog("通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(personalNotesService.getById(id));
	}

	/**
	 * 新增护士个人笔记
	 *
	 * @param personalNotes 护士个人笔记
	 * @return R
	 */
	@ApiOperation(value = "新增护士个人笔记", notes = "新增护士个人笔记")
	@SysLog("新增护士个人笔记")
	@PostMapping("/add")
	public R save(@RequestBody PersonalNotes personalNotes) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}
		//获取当前登录用户
		Integer id = user.getId();

		personalNotes.setUserId(id + "");

		personalNotes.setDelFlag(0);
		//生成id
		long personalNotesId = SnowFlake.getId();

		personalNotes.setPersonalNotesId(personalNotesId + "");

		return R.ok(personalNotesService.save(personalNotes));
	}

	/**
	 * 修改护士个人笔记
	 *
	 * @param personalNotes 护士个人笔记
	 * @return R
	 */
	@ApiOperation(value = "修改护士个人笔记", notes = "修改护士个人笔记")
	@SysLog("修改护士个人笔记")
	@PutMapping("/update")
	public R updateById(@RequestBody PersonalNotes personalNotes) {
		return R.ok(personalNotesService.updateById(personalNotes));
	}

	/**
	 * 通过id删除护士个人笔记
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除护士个人笔记", notes = "通过id删除护士个人笔记")
	@SysLog("通过id删除护士个人笔记")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {

		PersonalNotes byId = personalNotesService.getById(id);
		//更改删除标识
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}
		//封装当前操作者
		byId.setDelUser(user.getId() + "");

		return R.ok(personalNotesService.updateById(byId));
	}

}
