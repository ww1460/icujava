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

package com.pig4cloud.pigx.ccxxicu.controller.knowledgeBase;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.PersonalSummary;
import com.pig4cloud.pigx.ccxxicu.api.vo.knowledgeBase.PersonalSummaryVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.knowledgeBase.PersonalSummaryService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 个人总结
 *
 * @author pigx code generator
 * @date 2019-11-01 19:55:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/personalsummary" )
@Api(value = "personalsummary", tags = "个人总结管理")
public class PersonalSummaryController {

    private final PersonalSummaryService personalSummaryService;

    /**
     * 分页查询
     *@param page 分页对象
     * @param personalSummary 个人总结
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R selectPersonalSummary(Page page,PersonalSummary personalSummary) {
        PigxUser user = SecurityUtils.getUser();
        List<String> roleCodes = SecurityUtils.getRoleCodes();
        if (user == null|| CollectionUtils.isEmpty(roleCodes)) {
            return R.failed(1, "操作有误！");
        }
        personalSummary.setDelFlag(0);
        if(roleCodes.get(0).equals("ROLE_ADMIN")){
            return R.ok(personalSummaryService.selectAll(page,personalSummary));
        }
        personalSummary.setDeptId(user.getDeptId()+"");

        return R.ok(personalSummaryService.selectAll(page,personalSummary));
    }

    /**
     * 分页查询
     *@param page 分页对象
     * @param personalSummary 个人总结
     * @return
     */
    @ApiOperation(value = "分页查询自己的个人总结", notes = "分页查询自己的个人总结")
    @GetMapping("/selectPersonal" )
    public R selectPersonal(Page page,PersonalSummary personalSummary) {
        PigxUser user = SecurityUtils.getUser();
        List<String> roleCodes = SecurityUtils.getRoleCodes();
        if (user == null|| CollectionUtils.isEmpty(roleCodes)) {
            return R.failed(1, "操作有误！");
        }

        if(roleCodes.get(0).equals("ROLE_ADMIN")){
            return R.ok(personalSummaryService.selectPersonal(page,personalSummary));
        }
        personalSummary.setDeptId(user.getDeptId()+"");
        personalSummary.setCreateUserId(user.getId()+"");

        return R.ok(personalSummaryService.selectPersonal(page,personalSummary));
    }

    /**
     * 分页查询我收藏的
     * @param page 分页对象
     * @param personalSummaryVo 个人总结
     * @return
     */
    @ApiOperation(value = "分页查询我收藏的", notes = "分页查询我收藏的")
    @GetMapping("/selectCollect" )
    public R getselectCollectPage(Page page,PersonalSummaryVo personalSummaryVo) {
        PigxUser user = SecurityUtils.getUser();
        List<String> roleCodes = SecurityUtils.getRoleCodes();
        if (user == null|| CollectionUtils.isEmpty(roleCodes)) {
            return R.failed(1, "操作有误！");
        }
        personalSummaryVo.setDelFlag(0);
        if(roleCodes.get(0).equals("ROLE_ADMIN")){
            return R.ok(personalSummaryService.selectCollect(page,personalSummaryVo));
        }
        personalSummaryVo.setDeptId(user.getDeptId()+"");
        personalSummaryVo.setNurseId(user.getId()+"");

        return R.ok(personalSummaryService.selectCollect(page,personalSummaryVo));
    }


    /**
     * 通过id查询个人总结
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(personalSummaryService.getById(id));
    }

    /**
     * 新增个人总结
     * @param personalSummary 个人总结
     * @return R
     */
    @ApiOperation(value = "新增个人总结", notes = "新增个人总结")
    @SysLog("新增个人总结" )
    @PostMapping("/add")
    public R save(@RequestBody PersonalSummary personalSummary) {

        PigxUser user = SecurityUtils.getUser();
        if (user == null) {

            return R.failed(1, "操作有误！");

        }
        personalSummary.setCreateUserId(user.getId()+"");
        personalSummary.setCreateTime(LocalDateTime.now());
        personalSummary.setDeptId(user.getDeptId()+"");
        personalSummary.setPersonalSummaryId(SnowFlake.getId()+"");
        return R.ok(personalSummaryService.save(personalSummary));
    }

    /**
     * 修改个人总结
     * @param personalSummary 个人总结
     * @return R
     */
    @ApiOperation(value = "修改个人总结", notes = "修改个人总结")
    @SysLog("修改个人总结" )
    @PutMapping("/updateSummary")
    public R updateById(@RequestBody PersonalSummary personalSummary) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {

            return R.failed(1, "操作有误！");

        }
        personalSummary.setUpdateUserId(user.getId()+"");
        return R.ok(personalSummaryService.updateById(personalSummary));
    }

    /**
     * 通过id删除个人总结
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除个人总结", notes = "通过id删除个人总结")
    @SysLog("通过id删除个人总结" )
    @Transactional
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return R.failed(1, "操作有误！");
        }
        return R.ok(personalSummaryService.delete(id));
    }

}
