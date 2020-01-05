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
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TimingExecution;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.task.TemplateService;
import com.pig4cloud.pigx.ccxxicu.service.task.TimingExecutionService;
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
 * 任务定时执行
 *
 * @author pigx code generator
 * @date 2019-08-15 16:17:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("timingexecution" )
@Api(value = "timingexecution", tags = "任务定时执行管理")
public class TimingExecutionController {

    private final TimingExecutionService timingExecutionService;

	/**
	 * 任务模板
	 */
	private final TemplateService templateService;

	/**
	 * 患者
	 */
	private final PatientService patientService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param timingExecution 任务定时执行
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTakTimingExecutionPage(Page page, TimingExecution timingExecution) {
		System.out.println(timingExecution);

		if("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){ //管理员
			return R.ok(timingExecutionService.selectPaging(page,timingExecution));
		}
    	timingExecution.setDelFlag(0);
		timingExecution.setDeptId(SecurityUtils.getUser().getDeptId()+""); // 科室id
        return R.ok(timingExecutionService.selectPaging(page,timingExecution));
    }




    /**
     * 通过id查询任务定时执行
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(timingExecutionService.getById(id));
    }

    /**
     * 新增任务定时执行
     * @param timingExecution 任务定时执行
     * @return R
     */
    @ApiOperation(value = "新增任务定时执行", notes = "新增任务定时执行")
    @SysLog("新增任务定时执行" )
    @PostMapping("/add")
    public R save(@RequestBody TimingExecution timingExecution) {

    	timingExecution.setCreateUserId(SecurityUtils.getUser().getId()+"");//创建人
		timingExecution.setCreateTime(LocalDateTime.now());// 创建时间
		timingExecution.setNextExecutionTime(timingExecution.getPreStartTime());  // 下次执行 时间默认为预开始时间
		timingExecution.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室

        return R.ok(timingExecutionService.save(timingExecution));
    }


	/**
	 * 预定护理模板新增
	 * @return
	 */
	@ApiOperation(value = "预定护理模板新增",notes = "预定护理模板新增")
	@SysLog("预定护理模板新增")
	@PostMapping("/updateList")
    public R updateList(@RequestBody List<TimingExecution> list){

		for (int i=0;i<list.size();i++){
			TimingExecution timingExecution = list.get(i);
			timingExecution.setCreateUserId(SecurityUtils.getUser().getId()+"");//创建人
			timingExecution.setCreateTime(LocalDateTime.now());// 创建时间
			timingExecution.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
			boolean save = timingExecutionService.save(timingExecution);
			if (save==false){
				return R.failed(null,"添加参数有误");
			}
		}

    	return R.ok();
	}





    /**
     * 修改任务定时执行
     * @param timingExecution 任务定时执行
     * @return R
     */
    @ApiOperation(value = "修改任务定时执行", notes = "修改任务定时执行")
    @SysLog("修改任务定时执行" )
    @PostMapping("/update")
    public R updateById(@RequestBody TimingExecution timingExecution) {
		System.out.println("修改数据----------------------------------"+timingExecution);

        return R.ok(timingExecutionService.updateById(timingExecution));
    }

    /**
     * 通过id删除任务定时执行
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除任务定时执行", notes = "通过id删除任务定时执行")
    @SysLog("通过id删除任务定时执行" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		TimingExecution byId = timingExecutionService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		return R.ok(timingExecutionService.updateById(byId));
    }





}
