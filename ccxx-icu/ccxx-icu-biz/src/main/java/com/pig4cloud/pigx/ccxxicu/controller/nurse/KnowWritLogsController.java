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


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.KnowWritLogsBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.KnowWritLogs;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.KnowWritLogsService;
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
 * 知情文书记录
 *
 * @author pigx code generator
 * @date 2019-08-17 16:10:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/knowWritLogs")
@Api(value = "knowWritLogs", tags = "知情文书记录管理")
public class KnowWritLogsController {

	private final KnowWritLogsService knowWritLogsService;


	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param knowWritLogsBo 知情文书记录
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getKnowWritLogsPage(Page page, KnowWritLogsBo knowWritLogsBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(knowWritLogsService.selectByPage(page, knowWritLogsBo));

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			knowWritLogsBo.setDeptId(user.getDeptId() + "");

			return R.ok(knowWritLogsService.selectByPage(page, knowWritLogsBo));
		}

		knowWritLogsBo.setNurseId(user.getId() + "");

		return R.ok(knowWritLogsService.selectByPage(page, knowWritLogsBo));
	}


	/**
	 * 通过id查询知情文书记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getById/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(knowWritLogsService.getById(id));
	}

	/**
	 * 新增知情文书记录
	 *
	 * @param knowWritLogs 知情文书记录
	 * @return R
	 */
	@ApiOperation(value = "新增知情文书记录", notes = "新增知情文书记录")
	@SysLog("新增知情文书记录")
	@PostMapping("/add")
	public R save(@RequestBody KnowWritLogs knowWritLogs) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(knowWritLogs.getPatientId())) {
			return R.failed(1, "操作失败！");
		}


		//对数据进行处理
		knowWritLogs.setKnowWritLogsId(SnowFlake.getId() + "");
		knowWritLogs.setCreateTime(LocalDateTime.now());
		knowWritLogs.setCreateUserId(user.getId() + "");
		knowWritLogs.setDelFlag(0);
		knowWritLogs.setDeptId(user.getDeptId() + "");
		return R.ok(knowWritLogsService.save(knowWritLogs));
	}

	/**
	 * 修改知情文书记录
	 *
	 * @param knowWritLogs 知情文书记录
	 * @return R
	 */
	@ApiOperation(value = "修改知情文书记录", notes = "修改知情文书记录")
	@SysLog("修改知情文书记录")
	@PostMapping("/update")
	public R updateById(@RequestBody KnowWritLogs knowWritLogs) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {

			R.failed(1, "操作有误！");

		}

		knowWritLogs.setUpdateUserId(user.getId() + "");

		return R.ok(knowWritLogsService.updateById(knowWritLogs));
	}

	/**
	 * 通过id删除知情文书记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除知情文书记录", notes = "通过id删除知情文书记录")
	@SysLog("通过id删除知情文书记录")
	@GetMapping("/del/{id}")
	public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {

			R.failed(1, "操作有误！");

		}

		KnowWritLogs byId = knowWritLogsService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId() + "");


		return R.ok(knowWritLogsService.updateById(byId));
	}

}
