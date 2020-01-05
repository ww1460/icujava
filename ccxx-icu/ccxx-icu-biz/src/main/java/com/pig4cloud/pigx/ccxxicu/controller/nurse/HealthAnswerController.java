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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.HealthAnswerBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthAnswer;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.HealthAnswerVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.HealthAnswerService;
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
 * 护士健康回答
 *
 * @author pigx code generator
 * @date 2019-08-05 15:20:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/healthAnswer" )
@Api(value = "healthAnswer", tags = "护士健康回答管理")
public class HealthAnswerController {

    private final HealthAnswerService healthAnswerService;

	/**
	 * 护士做完评估后  保存
	 * @param healthAnswers
	 * @return
	 */
	@ApiOperation(value = "新增护士健康回答", notes = "新增护士健康回答")
	@SysLog("新增护士健康回答" )
    @PostMapping("/add")
	public R save (@RequestBody List<HealthAnswer> healthAnswers) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}

		LocalDateTime now = LocalDateTime.now();
		if (CollectionUtils.isEmpty(healthAnswers)) {
			return R.failed(1, "操作失败！");
		}


		//将护士的评估逐条新增
		for (int i = 0; i < healthAnswers.size(); i++) {

			HealthAnswer healthAnswer = healthAnswers.get(i);

			healthAnswer.setAssessTime(now);

			healthAnswer.setNurseId(user.getId()+"");

			healthAnswer.setDelFlag(0);

			healthAnswer.setDeptId(user.getDeptId()+"");
			long id = SnowFlake.getId();
			healthAnswer.setHealthAnswerId(id+"");

			boolean save = healthAnswerService.save(healthAnswer);
			if (save == false) {

			return 	R.failed("失败");

			}

		}
		return R.ok(true);
	}

	/**
	 * 查询某护士的评估记录 生成图表
	 * 护士长查看  需要传入护士的id
	 * @param healthAnswer
	 * @return
	 */
	@ApiOperation(value = "查询某护士的评估记录生成图表", notes = "查询某护士的评估记录生成图表")
	@PostMapping("/getMap" )
	public R getMap(@RequestBody HealthAnswer healthAnswer) {

		PigxUser user = SecurityUtils.getUser();


		Integer id = user.getId();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {

			healthAnswer.setNurseId(id+"");

		}
		if (healthAnswer.getNurseId()== null) {

			return R.failed(1, "操作有误！");

		}


		return R.ok(healthAnswerService.selectByNurse(healthAnswer));

	}





    /**
     * 护士长登录时分页查询护士的评估
	 *
     * @param page 分页对象
     * @param healthAnswerBo 护士健康回答
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getNurHealthAnswerPage(Page page, HealthAnswerBo healthAnswerBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user ==null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")) {

			healthAnswerBo.setNurseId(user.getId()+"");

		}

		healthAnswerBo.setDeptId(user.getDeptId()+"");

		return R.ok(healthAnswerService.selectByPage(page, healthAnswerBo));
    }

	/**
	 * 修改护士健康回答
	 * 或者护士长对其回答做出评价
	 * @param healthAnswer 护士健康回答
	 * @return R
	 */
	@ApiOperation(value = "修改护士健康回答", notes = "修改护士健康回答/护士长对其回答做出评价")
	@SysLog("修改护士健康回答/护士长对其回答做出评价" )
    @PostMapping("/matronUpdate")
	public R matronUpdate(@RequestBody HealthAnswer healthAnswer) {

		PigxUser user = SecurityUtils.getUser();

		Integer id = user.getId();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		//判断用户权限
		for (int i = 0; i < roleCodes.size(); i++) {

			String s = roleCodes.get(i);

			if (s.equals("ROLE_MATRON")) {

				healthAnswer.setMatronId(id+"");

			}
		}

		if (healthAnswer.getMatronId() == null) {
			//如果护士长id 为null  说明没有权限
			return R.failed(1, "操作有误！");
		}

		healthAnswer.setAssessTimeOfMatorn(LocalDateTime.now());

		return R.ok(healthAnswerService.updateById(healthAnswer));

	}



    /**
     * 通过id查询护士健康回答
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable Integer id) {
    	return R.ok(healthAnswerService.getById(id));
    }



    /**
     * 通过id删除护士健康回答
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士健康回答", notes = "通过id删除护士健康回答")
    @SysLog("通过id删除护士健康回答" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed(1, "操作有误！");

		}

		HealthAnswerVo byId = healthAnswerService.getById(id);
		/**
		 * 这里如果出错  就是子类不能直接修改父类
		 */
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getDeptId()+"");

		return R.ok(healthAnswerService.updateById(byId));
    }



}
