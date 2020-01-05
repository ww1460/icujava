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

package com.pig4cloud.pigx.ccxxicu.controller.assess;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.AssessRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProjectRecord;
import com.pig4cloud.pigx.ccxxicu.service.assess.AssessProjectRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 评估项目记录表
 *
 * @author pigx code generator
 * @date 2019-08-27 16:58:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assessProjectRecord" )
@Api(value = "assessProjectRecord", tags = "评估项目记录表管理")
public class AssessProjectRecordController {

    private final AssessProjectRecordService assessProjectRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param assessProjectRecord 评估项目记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAssessProjectRecordPage(Page page, AssessProjectRecord assessProjectRecord) {
        return R.ok(assessProjectRecordService.page(page, Wrappers.query(assessProjectRecord)));
    }

    /**
     * 通过id查询评估项目记录表
     * @param assessRecordId 记录id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/getAssessRecord/{assessRecordId}" )
    public R getById(@PathVariable("assessRecordId" ) String assessRecordId) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(assessRecordId)) {
			return R.failed(1, "操作失败！");
		}


		return R.ok(assessProjectRecordService.getAssessRecord(assessRecordId));
    }

    /**
     * 新增评估项目记录表
     * @param assessRecordBo 评估项目记录表
     * @return R
     */
    @ApiOperation(value = "新增评估项目记录表", notes = "新增评估项目记录表")
    @SysLog("新增评估项目记录表" )
    @PostMapping("/add")
    public R save(@RequestBody AssessRecordBo assessRecordBo) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}


        return R.ok(assessProjectRecordService.add(assessRecordBo));
    }

    /**
     * 修改评估项目记录表
     * @param assessProjectRecord 评估项目记录表
     * @return R
     */
    @ApiOperation(value = "修改评估项目记录表", notes = "修改评估项目记录表")
    @SysLog("修改评估项目记录表" )
    @PutMapping
    public R updateById(@RequestBody AssessProjectRecord assessProjectRecord) {
        return R.ok(assessProjectRecordService.updateById(assessProjectRecord));
    }

    /**
     * 通过id删除评估项目记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除评估项目记录表", notes = "通过id删除评估项目记录表")
    @SysLog("通过id删除评估项目记录表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(assessProjectRecordService.removeById(id));
    }

}
