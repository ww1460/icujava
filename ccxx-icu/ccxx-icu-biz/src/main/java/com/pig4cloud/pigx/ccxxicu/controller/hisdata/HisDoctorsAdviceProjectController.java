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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 医嘱项目表【一批医嘱下相对应的药品、项目数据】
 *
 * @author pigx code generator
 * @date 2019-08-30 10:58:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/hisdoctorsadviceproject" )
@Api(value = "hisdoctorsadviceproject", tags = "医嘱项目表【一批医嘱下相对应的药品、项目数据】管理")
public class HisDoctorsAdviceProjectController {

    private final HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param hisDoctorsAdviceProject 医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisDoctorsAdviceProjectPage(Page page, HisDoctorsAdviceProject hisDoctorsAdviceProject) {
        return R.ok(hisDoctorsAdviceProjectService.page(page, Wrappers.query(hisDoctorsAdviceProject)));
    }


    /**
     * 通过id查询医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(hisDoctorsAdviceProjectService.getById(id));
    }

    /**
     * 新增医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @param hisDoctorsAdviceProject 医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @return R
     */
    @ApiOperation(value = "新增医嘱项目表【一批医嘱下相对应的药品、项目数据】", notes = "新增医嘱项目表【一批医嘱下相对应的药品、项目数据】")
    @SysLog("新增医嘱项目表【一批医嘱下相对应的药品、项目数据】" )
    @PostMapping("/add")
    public R save(@RequestBody HisDoctorsAdviceProject hisDoctorsAdviceProject) {
    	hisDoctorsAdviceProject.setDoctorsAdviceProjectId(SnowFlake.getId()+"");//雪花低
        return R.ok(hisDoctorsAdviceProjectService.save(hisDoctorsAdviceProject));
    }

    /**
     * 修改医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @param hisDoctorsAdviceProject 医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @return R
     */
    @ApiOperation(value = "修改医嘱项目表【一批医嘱下相对应的药品、项目数据】", notes = "修改医嘱项目表【一批医嘱下相对应的药品、项目数据】")
    @SysLog("修改医嘱项目表【一批医嘱下相对应的药品、项目数据】" )
    @PostMapping("/update")
    public R updateById(@RequestBody HisDoctorsAdviceProject hisDoctorsAdviceProject) {
        return R.ok(hisDoctorsAdviceProjectService.updateById(hisDoctorsAdviceProject));
    }

    /**
     * 通过id删除医嘱项目表【一批医嘱下相对应的药品、项目数据】
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除医嘱项目表【一批医嘱下相对应的药品、项目数据】", notes = "通过id删除医嘱项目表【一批医嘱下相对应的药品、项目数据】")
    @SysLog("通过id删除医嘱项目表【一批医嘱下相对应的药品、项目数据】" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(hisDoctorsAdviceProjectService.removeById(id));
    }

}
