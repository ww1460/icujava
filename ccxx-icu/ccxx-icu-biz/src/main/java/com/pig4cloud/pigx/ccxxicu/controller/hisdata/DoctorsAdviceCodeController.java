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

package com.pig4cloud.pigx.ccxxicu.controller.hisdata;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceCode;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Template;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TemplateSubTask;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisCodeEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DoctorsAdviceCodeService;
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
 * 医嘱编码【用于储存医嘱的内容】
 *
 * @author pigx code generator
 * @date 2019-10-07 16:01:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doctorsadvicecode" )
@Api(value = "doctorsadvicecode", tags = "医嘱编码【用于储存医嘱的内容】管理")
public class DoctorsAdviceCodeController {

    private final DoctorsAdviceCodeService doctorsAdviceCodeService;
	/**
	 * 任务模板【子任务】
	 */
	private final TemplateSubTaskService templateSubTaskService;
	/**
	 * 任务模板
	 */
	private final TemplateService templateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param doctorsAdviceCode 医嘱编码【用于储存医嘱的内容】
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisDoctorsAdviceCodePage(Page page, DoctorsAdviceCode doctorsAdviceCode) {

        return R.ok(doctorsAdviceCodeService.selectPaging(page,doctorsAdviceCode));
    }

    /**
     * 通过id查询医嘱编码【用于储存医嘱的内容】
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(doctorsAdviceCodeService.getById(id));
    }

    /**
     * 新增医嘱编码【用于储存医嘱的内容】
     * @param doctorsAdviceCode 医嘱编码【用于储存医嘱的内容】
     * @return R
     */
    @ApiOperation(value = "新增医嘱编码【用于储存医嘱的内容】", notes = "新增医嘱编码【用于储存医嘱的内容】")
    @SysLog("新增医嘱编码【用于储存医嘱的内容】" )
    @PostMapping("/add")
    public R save(@RequestBody DoctorsAdviceCode doctorsAdviceCode) {
    	doctorsAdviceCode.setCreateTime(LocalDateTime.now());//创建时间
    	doctorsAdviceCode.setCreateUserId(SecurityUtils.getUser().getId()+""); //创建人
    	doctorsAdviceCode.setDoctorsAdviceCodeId(SnowFlake.getId()+""); //雪花
		doctorsAdviceCode.setHospitalCode(HisCodeEnum.DISCONTINUE_USE.getCode());//医院标识
        return R.ok(doctorsAdviceCodeService.save(doctorsAdviceCode));
    }

    /**
     * 修改医嘱编码【用于储存医嘱的内容】
     * @param doctorsAdviceCode 医嘱编码【用于储存医嘱的内容】
     * @return R
     */
    @ApiOperation(value = "修改医嘱编码【用于储存医嘱的内容】", notes = "修改医嘱编码【用于储存医嘱的内容】")
    @SysLog("修改医嘱编码【用于储存医嘱的内容】" )
    @PostMapping("/update")
    public R updateById(@RequestBody DoctorsAdviceCode doctorsAdviceCode) {
        return R.ok(doctorsAdviceCodeService.updateById(doctorsAdviceCode));
    }

    /**
     * 通过id删除医嘱编码【用于储存医嘱的内容】
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除医嘱编码【用于储存医嘱的内容】", notes = "通过id删除医嘱编码【用于储存医嘱的内容】")
    @SysLog("通过id删除医嘱编码【用于储存医嘱的内容】" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		DoctorsAdviceCode byId = doctorsAdviceCodeService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now()); // 删除时间
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");//护士id
		/* 同时删除关联任务模板的数据 */
		Template template = templateService.sourceId(byId.getDoctorsAdviceCodeId()); // 医嘱内容查询相关的数据
		if (template!=null){
			template.setDelFlag(1);
			template.setDelUserId(SecurityUtils.getUser().getId()+"");//删除护士
			template.setDelTime(LocalDateTime.now());
			boolean b = templateService.updateById(template);
			if (!b){
				return R.failed(null,"相关任务模板删除失败！！！");
			}


			List<TemplateSubTask> list = templateSubTaskService.taskTemplateId(template.getTemplateId());
			Boolean e = templateSubTaskService.deletes(list);
			if (!e){
				return R.failed(null,"任务模板子任务删除失败！！！！！");
			}

		}

		return R.ok(doctorsAdviceCodeService.updateById(byId));
    }

}
