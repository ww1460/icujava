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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.HealthAnswerBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthAnswer;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.HealthSubject;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.HealthAnswerVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.HealthAnswerService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.HealthSubjectService;
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
 * 护士健康评估题目
 *
 * @author pigx code generator
 * @date 2019-08-05 15:20:07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/healthSubject" )
@Api(value = "healthSubject", tags = "护士健康评估题目管理")
public class HealthSubjectController {

    private final HealthSubjectService healthSubjectService;

	private final HealthAnswerService healthAnswerService;

    /**
     * 分页查询用于管理 护士长
     * @param page 分页对象
     * @param healthSubject 护士健康评估题目
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getNurHealthSubjectPage(Page page, HealthSubject healthSubject) {

		PigxUser user = SecurityUtils.getUser();
		healthSubject.setDelFlag(0);
		List<String> roleCodes = SecurityUtils.getRoleCodes();
		//判断权限  是否是护士长及以上
		if (roleCodes.size() > 0 ) {
			for (int i = 0; i < roleCodes.size(); i++) {

				String s = roleCodes.get(i);
				//管理员查看所有科室的项目
				if (s.equals("ROLE_ADMIN")) {

					return R.ok(healthSubjectService.page(page, Wrappers.query(healthSubject)));
					//护士长查看该科室的项目
				} else if (s.equals("ROLE_MATRON")) {

					healthSubject.setDeptId(user.getDeptId() + "");

					return R.ok(healthSubjectService.page(page, Wrappers.query(healthSubject)));

				}
			}

		}

		return R.failed("权限不足");
    }


    /**
     * 通过id查询护士健康评估题目 护士长用于修改
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(healthSubjectService.getById(id));
    }

    /**
     * 新增护士健康评估题目，护士长用于管理
     * @param healthSubject 护士健康评估题目
     * @return R
     */
    @ApiOperation(value = "新增护士健康评估题目", notes = "新增护士健康评估题目")
    @SysLog("新增护士健康评估题目" )
    @PostMapping("/add")
    public R save(@RequestBody HealthSubject healthSubject) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}

		healthSubject.setDeptId(user.getDeptId()+"");
		healthSubject.setCreateTime(LocalDateTime.now());
		healthSubject.setDelFlag(0);
		healthSubject.setCreateUserId(user.getId()+"");
		long id = SnowFlake.getId();
		healthSubject.setHealthSubjectId(id+"");

		return R.ok(healthSubjectService.save(healthSubject));
    }

    /**
     * 修改护士健康评估题目 ，护士长用于管理
     * @param healthSubject 护士健康评估题目
     * @return R
     */
    @ApiOperation(value = "修改护士健康评估题目", notes = "修改护士健康评估题目")
    @SysLog("修改护士健康评估题目" )
    @PostMapping("/update")
    public R updateById(@RequestBody HealthSubject healthSubject) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed("登录后操作");

		}

		healthSubject.setUpdateUserId(user.getId()+"");

        return R.ok(healthSubjectService.updateById(healthSubject));
    }

    /**
     * 通过id删除护士健康评估题目，护士长用于管理
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士健康评估题目", notes = "通过id删除护士健康评估题目")
    @SysLog("通过id删除护士健康评估题目" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

    	//删除项目后  护士对应的项目记录也将删除

		HealthSubject byId = healthSubjectService.getById(id);
		PigxUser user = SecurityUtils.getUser();
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");

		/**
		 * 目前仅写了删除项目  项目记录还未写
		 */
		List<HealthAnswerVo> bySubject = healthAnswerService.getBySubject(byId.getHealthSubjectId());
		//判断该项目的记录是否为空
		if (!bySubject.isEmpty()) {
			//将所有该项目记录删除
			for (int i = 0; i < bySubject.size(); i++) {
				HealthAnswerVo healthAnswerVo = bySubject.get(i);
				HealthAnswer healthAnswer = new HealthAnswer();
				BeanUtil.copyProperties(healthAnswerVo,healthAnswer);
				healthAnswer.setDelFlag(1);
				healthAnswer.setDelTime(LocalDateTime.now());
				healthAnswer.setDelUserId(user.getId()+"");
				boolean b = healthAnswerService.updateById(healthAnswer);
				if (!b) {
					R.failed(1, "操作有误！");
				}
			}
		}
		return R.ok(healthSubjectService.updateById(byId));
    }




	/**
	 * 护士获取评估题目，护士页面展示
	 * @return
	 */
	@ApiOperation(value = "护士获取评估题目", notes = "护士获取评估题目")
	@SysLog("护士获取评估题目" )
	@GetMapping("/getSubject")
	public R getSubject() {

		//获取登录护士
		PigxUser user = SecurityUtils.getUser();
		List<String> roleCodes = SecurityUtils.getRoleCodes();
		//判断用户是否登录  是否存在权限  是否是护士
		if (user == null|| roleCodes.isEmpty()||roleCodes.get(0).equals("ROLE_MATRON")) {

			return R.failed(1, "操作有误！");

		}
		System.out.println(user.getDeptId() + "---当前护士的科室");
		//当前时间
		LocalDateTime now = LocalDateTime.now();
		//10小时前的时间
		LocalDateTime startTime = now.plusHours(-10);
		//查询该科室下所有的题目
		List<HealthSubject> healthSubjects = healthSubjectService.selectAll(user.getDeptId() + "");

		HealthAnswerBo healthAnswerBo = new HealthAnswerBo();
		//查询该护士10小时内 做过的项目
		healthAnswerBo.setStartTime(startTime);
		healthAnswerBo.setEndTime(now);
		healthAnswerBo.setNurseId(user.getId()+"");
		List<String> subjectId = healthAnswerService.selectNow(healthAnswerBo);
		//如果项目集合为空  直接返回错误报告
		if (healthSubjects.isEmpty()) {

			R.failed(1,"暂无题目，请前去录入！");

		}

		//剔除十小时内做过的评估项
		subjectId.forEach(ar->{

			for (int i = 0; i < healthSubjects.size(); i++) {

				if (healthSubjects.get(i).getHealthSubjectId().equals(ar)) {

					healthSubjects.remove(i);
					i--;

				}
			}
		});

		if (healthSubjects.size()==0) {

			return R.failed(1, "今日题目已全部完成！");

		}

		return R.ok(healthSubjects);
	}




}
