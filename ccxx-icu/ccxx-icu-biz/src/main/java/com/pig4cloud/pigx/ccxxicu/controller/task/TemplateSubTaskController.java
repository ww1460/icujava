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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.task.TemplateSubTaskBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TemplateSubTaskVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.TemplateEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceCodeService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateSubTaskService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 任务模板子模板
 *
 * @author pigx code generator
 * @date 2019-10-07 16:58:17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/templateSubTask" )
@Api(value = "templateSubTask", tags = "任务模板子模板管理")
public class TemplateSubTaskController {

    private final TemplateSubTaskService templateSubTaskService;

	/**
	 * 任务模板
	 */
	private final TemplateService templateService;

	/**
	 * 医嘱内容【标识】
	 */
	private final DoctorsAdviceCodeService doctorsAdviceCodeService;
	/**
	 * 项目
	 */
	private final ProjectCorrelationService projectCorrelationService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param templateSubTask 任务模板子模板
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getTakTemplateSubTaskPage(Page page, TemplateSubTask templateSubTask) {
        return R.ok(templateSubTaskService.page(page, Wrappers.query(templateSubTask)));
    }

    /**
     * 通过id查询任务模板子模板
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(templateSubTaskService.getById(id));
    }

	/**
	 * 新增前跳转数据
	 * @return
	 */
	@ApiOperation(value = "新增前跳转页面",notes = "新增前跳转页面")
	@GetMapping("/preJump")
	public R preJump(){
		ProjectCorrelation projectCorrelation = new ProjectCorrelation();

		if (!SecurityUtils.getRoleCodes().get(0).equals("ROLE_ADMIN")) {
			projectCorrelation.setDeptId(SecurityUtils.getUser().getDeptId()+"");
		}

		projectCorrelation.setNursingReportFlag(2);
		List<ProjectCorrelationVo> projectCorrelationVos = projectCorrelationService.selectReportProject(projectCorrelation);
		if (CollectionUtil.isNotEmpty(projectCorrelationVos)){
			return R.ok(projectCorrelationVos);
		}else{
			return null;
		}
	}








    /**
     * 新增任务模板子模板
     * @param templateSubTask 任务模板子模板
     * @return R
     */
    @ApiOperation(value = "新增任务模板子模板", notes = "新增任务模板子模板")
    @SysLog("新增任务模板子模板" )
    @PostMapping("/add")
    public R save(@RequestBody TemplateSubTask templateSubTask) {
    	templateSubTask.setCreateTime(LocalDateTime.now());//创建时间
		templateSubTask.setCreateUserId(SecurityUtils.getUser().getId()+"");//删除护士
		templateSubTask.setTemplateSubTaskId(SnowFlake.getId()+""); // 新增


		if (templateSubTask.getTaskTemplateId()==null){
			return R.failed("新增失败！！！失败原因【缺失关联父级数据】，请尽快联系管理员！！！！");
		}
		/*当子任务模板新增时，将将主任务条数+1 */
		Template template = templateService.getTemplateId(templateSubTask.getTaskTemplateId());
		if (template ==null){
			return R.failed("新增失败！！！失败原因【没有查询到相关父级信息！！！】  请联系管理员");
		}
		/* 当前任务模板已有的子任务条数加1 */
		template.setSubTaskNum(template.getSubTaskNum()+1);
		boolean b = templateService.updateById(template);
		if (b==false){
			return R.failed("新增失败！！！失败原因【父级改变失败！！！】  请联系管理员");
		}
		return R.ok(templateSubTaskService.save(templateSubTask));
    }

    /**
     * 修改任务模板子模板
     * @param templateSubTask 任务模板子模板
     * @return R
     */
    @ApiOperation(value = "修改任务模板子模板", notes = "修改任务模板子模板")
    @SysLog("修改任务模板子模板" )
    @PostMapping("/update")
    public R updateById(@RequestBody TemplateSubTask templateSubTask) {
        return R.ok(templateSubTaskService.updateById(templateSubTask));
    }

    /**
     * 通过id删除任务模板子模板
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务模板子模板", notes = "通过id删除任务模板子模板")
    @SysLog("通过id删除任务模板子模板" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		TemplateSubTask byId = templateSubTaskService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());//删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");//删除用户
		return R.ok(templateSubTaskService.updateById(byId));
    }

	/**
	 * 任务id查询任务模板的子任务
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "任务id查询任务模板的子任务",notes = "任务id查询任务模板的子任务")
	@SysLog("任务id查询任务模板的子任务")
	@GetMapping("/taskTemplateId/{id}")
    public R taskTemplateId(@PathVariable String id){
    	return R.ok(templateSubTaskService.taskTemplateId(id));
	}

	/**
	 *查询自建任务的数据
	 * @return
	 */
	@ApiOperation(value = "查询自建任务的数据",notes = "查询自建任务的数据")
	@SysLog("查询自建任务的数据")
	@PostMapping("/buildByOneself")
	public R buildByOneself(@RequestBody TemplateSubTaskVo templateSubTaskVo){
		templateSubTaskVo.setSource(TemplateEnum.FAST.getCode());//快捷
		return R.ok(templateSubTaskService.buildByOneself(templateSubTaskVo));
	}

	/**
	 *查询压疮护理的数据
	 * @return
	 */
	@ApiOperation(value = "查询压疮护理的数据",notes = "查询压疮护理的数据")
	@SysLog("查询压疮护理的数据")
	@PostMapping("/pressureSore")
	public R pressureSore(@RequestBody TemplateSubTaskVo templateSubTaskVo){
		templateSubTaskVo.setTaskName("压疮护理");
		templateSubTaskVo.setSource(TemplateEnum.PROJECT.getCode());
		return R.ok(templateSubTaskService.buildByOneself(templateSubTaskVo));
	}

	/**
	 *查询压疮护理的数据
	 * @return
	 */
	@ApiOperation(value = "查询跌倒坠床的数据",notes = "查询跌倒坠床的数据")
	@SysLog("查询跌倒坠床的数据")
	@GetMapping("/fallIntoBed")
	public R fallIntoBed(){
		TemplateSubTaskVo templateSubTaskVo = new TemplateSubTaskVo();
		templateSubTaskVo.setTaskName("跌倒坠床");
		templateSubTaskVo.setSource(TemplateEnum.PROJECT.getCode());
		return R.ok(templateSubTaskService.buildByOneself(templateSubTaskVo));
	}

	/**
	 * 通过判断任务模板，查询当条任务模板是否有固定结果     任务模板的来源 是否来源于项目中
	 * @param templateSubTask
	 * @return
	 */
	@ApiOperation(value = "通过判断任务模板，查询当条任务模板是否有固定结果     任务模板的来源 是否来源于项目中",notes = "通过判断任务模板，查询当条任务模板是否有固定结果     任务模板的来源 是否来源于项目中")
	@SysLog("通过判断任务模板，查询当条任务模板是否有固定结果     任务模板的来源 是否来源于项目中")
	@PostMapping("/templateProjectValue")
	public R selectTemplateProjectValue(@RequestBody TemplateSubTask templateSubTask){
		return R.ok(templateSubTaskService.selectTemplateProjectValue(templateSubTask));
	}



	/**
	 * 任务模板新增任务
	 * @param templateSubTaskBo
	 * @return
	 */
	@ApiOperation(value = "任务模板新增任务",notes = "任务模板新增任务")
	@SysLog("任务模板新增任务")
	@PostMapping("/addTask")
	public R addTask(@RequestBody TemplateSubTaskBo templateSubTaskBo){
		return R.ok(templateSubTaskService.addTask(templateSubTaskBo));
	}






}
