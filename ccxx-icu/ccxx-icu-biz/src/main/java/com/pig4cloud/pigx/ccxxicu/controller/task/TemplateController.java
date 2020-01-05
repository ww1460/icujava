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

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.TemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceCode;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.TemplateEnum;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceCodeService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateSubTaskService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 任务模板
 *
 * @author pigx code generator
 * @date 2019-08-15 10:09:38
 */
@RestController
@AllArgsConstructor
@RequestMapping("/template" )
@Api(value = "template", tags = "任务模板管理")
public class TemplateController {

    private final TemplateService templateService;
	/**
	 * 项目表
	 */
    private final ProjectService projectService;

	/**
	 * 医嘱内容【标识】
	 */
	private final DoctorsAdviceCodeService doctorsAdviceCodeService;
	/**
	 * 任务模板子任务
	 */
	private final TemplateSubTaskService templateSubTaskService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param template 任务模板
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakTemplatePage(Page page,TemplateVo template) {
		//护士长
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 护士长查询当前科室 AdmissionDepartment */
			template.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
			return R.ok(templateService.selectPaging(page,template));
			//护士
		}else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			template.setLoginNurse(SecurityUtils.getUser().getId()+""); // 当前登录患者
			template.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
			return R.ok(templateService.selectPaging(page,template));
		}
        return R.ok(templateService.selectPaging(page,template));
    }

	/**
	 * 新增前跳转数据
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面")
	@GetMapping("/preJump")
	public R preJump(){

		Map<String,Object> test = new HashMap<>();
		Boolean source = templateService.source();
		if (source){
			test.put("source",1);//不展示
		}else{
			test.put("source",2);//展示
		}

		List<DoctorsAdviceCode> doctorsAdviceCodes = doctorsAdviceCodeService.selectAll();
		if (CollectionUtil.isNotEmpty(doctorsAdviceCodes)){
			test.put("doctorsAdviceCodes",doctorsAdviceCodes);
		}
		return R.ok(test);
	}


	/**
	 *查询自建任务的数据【用于任务页面头部的快捷任务展示】
	 * @return
	 */
	@ApiOperation(value = "查询自建任务的数据",notes = "查询自建任务的数据")
	@SysLog("查询自建任务的数据")
	@PostMapping("/buildByOneself")
	public R buildByOneself(){
		return R.ok(templateService.sourceType(TemplateEnum.PROJECT.getCode()));
	}

	/**
	 *查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】
	 * @return
	 */
	@ApiOperation(value = "查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】",notes = "查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】")
	@SysLog("查询子任务新增前所展示任务模板的数据 【没有快捷模板数据】")
	@PostMapping("/selectTaskPreJump")
	public R selectTaskPreJump(TemplateVo template){
		//护士长
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 护士长查询当前科室 AdmissionDepartment */
			template.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
			return R.ok(templateService.selectTaskPreJump(template));
			//护士
		}else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			template.setLoginNurse(SecurityUtils.getUser().getId()+""); // 当前登录护士
			template.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
			return R.ok(templateService.selectTaskPreJump(template));
		}
		return R.ok(templateService.selectTaskPreJump(template));
	}


    /**
     * 通过id查询任务模板
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(templateService.getById(id));
    }


	/**
	 * 验证该来源是否存在模板
	 * @param sourceId sourceId
	 * @return R
	 */
	@ApiOperation(value = "验证该来源是否存在模板", notes = "验证该来源是否存在模板")
	@GetMapping("/sourceId/{sourceId}" )
	public R getById(@PathVariable("sourceId") String sourceId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}
		if (StringUtils.isEmpty(sourceId)) {
			return R.failed("来源为空！");
		}

		Template template = new Template();

		template.setDeptId(user.getDeptId()+"");
		template.setSourceId(sourceId);
		template.setDelFlag(0);

		return R.ok(templateService.getOne(new QueryWrapper<>(template)));
	}



    /**
     * 新增任务模板
     * @param template 任务模板
     * @return R
     */
    @ApiOperation(value = "新增任务模板", notes = "新增任务模板")
    @SysLog("新增任务模板" )
    @PostMapping("/add")
    public R save(@RequestBody TemplateBo template) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(false);

		}

        return R.ok(templateService.add(template));
    }

    /**
     * 修改任务模板
     * @param template 任务模板
     * @return R
     */
    @ApiOperation(value = "修改任务模板", notes = "修改任务模板")
    @SysLog("修改任务模板" )
   	@PostMapping("/update")
    public R updateById(@RequestBody Template template) {
        return R.ok(templateService.updateById(template));
    }

    /**
     * 通过id删除任务模板
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务模板", notes = "通过id删除任务模板")
    @SysLog("通过id删除任务模板" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		Template byId = templateService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		/* 判断一下当条任务是否来源于医嘱内容*/
		if (byId.getSource()==TemplateEnum.DOCTORS_ADVICE.getCode()){
			/* 查询当前 任务模板关联的子任务 */
			List<TemplateSubTask> templateSubTasks = templateSubTaskService.taskTemplateId(byId.getTemplateId());
			/*  删除当前所有的任务*/
			Boolean deletes = templateSubTaskService.deletes(templateSubTasks);
			if (!deletes){
				return R.failed("任务模板子任务模板删除失败！！！！！！！！！！！！！！");
			}
		}
		return R.ok(templateService.updateById(byId));
    }

	/**
	 * 任务模板全查
	 * @param template
	 * @return
	 */
	@ApiOperation(value = "任务模板全查",notes = "任务模板全查")
	@SysLog("任务模板全查")
    @GetMapping("/selectAll")
    public R selectAll(TemplateVo template){

		//护士长
		if ("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){
			/* 护士长查询当前科室 AdmissionDepartment */
			template.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
			return R.ok(templateService.selectAll(template));
			//护士
		}else if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){
			template.setLoginNurse(SecurityUtils.getUser().getId()+""); // 当前登录护士
			template.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室
			return R.ok(templateService.selectAll(template));
		}
    	return R.ok(templateService.selectAll(template));
	}


	/**
	 * 任务模板添加任务
	 * @param templateBo
	 * @return
	 */
	@ApiOperation(value = "任务模板添加任务",notes = "任务模板添加任务")
	@SysLog("任务模板添加任务")
	@PostMapping("/templateAddTask")
	public R templateAddTask(@RequestBody com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateBo templateBo){
		return R.ok(templateService.templateAddTask(templateBo));
	};


}
