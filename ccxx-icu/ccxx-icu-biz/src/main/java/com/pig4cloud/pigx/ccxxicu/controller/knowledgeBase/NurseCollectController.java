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
import com.pig4cloud.pigx.ccxxicu.api.Bo.knowledgeBase.NurseCollectBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.NurseCollect;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.PersonalSummary;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.knowledgeBase.NurseCollectService;
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

import java.util.List;


/**
 * 护士个人总结收藏关联表
 *
 * @author pigx code generator
 * @date 2019-11-01 20:38:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursecollect" )
@Api(value = "nursecollect", tags = "护士个人总结收藏关联表管理")
public class NurseCollectController {

    private final NurseCollectService nurseCollectService;

    private final PersonalSummaryService personalSummaryService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param nurseCollect 护士个人总结收藏关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getKnowNurseCollectPage(Page page, NurseCollect nurseCollect) {
        return R.ok(nurseCollectService.page(page, Wrappers.query(nurseCollect)));
    }


    /**
     * 通过id查询护士个人总结收藏关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(nurseCollectService.getById(id));
    }

    /**
     * 新增护士个人总结收藏关联表
     * @param nurseCollectBo 护士个人总结收藏关联表
     * @return R
     */
    @ApiOperation(value = "新增护士个人总结收藏关联表", notes = "新增护士个人总结收藏关联表")
    @SysLog("新增护士个人总结收藏关联表" )
    @PostMapping("/addCollect")
    @Transactional
    public R save(@RequestBody NurseCollectBo nurseCollectBo) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {

            return R.failed(1, "操作有误！");

        }
        nurseCollectBo.setNurseId(user.getId()+"");
        /**
         * 利用护士id和雪花id查询当前登录护士是否收藏了本条数据
         */
        if(nurseCollectBo.getCollectFlag()==0) {

            List<NurseCollect> nurseCollects = nurseCollectService.queryCollect(nurseCollectBo);
            if (CollectionUtils.isNotEmpty(nurseCollects)) {
                return R.failed(1, "已收藏，请勿重复操作");
            }
            boolean b = nurseCollectService.save(nurseCollectBo);
            if (b) {
                /**
                 * 根据关联表的雪花id查询个人总结表的对应的数据
                 */
                PersonalSummary personalSummary = personalSummaryService.selectId(nurseCollectBo.getPersonalSummaryId());
                personalSummary.setCollectCount(personalSummary.getCollectCount() + 1);
                boolean b1 = personalSummaryService.updateById(personalSummary);
                if (!b1) {
                    return R.failed(1, "操作错误，请联系管理员");
                } else {
                    return R.ok(0, "操作成功");
                }
            } else {
                return R.failed(1, "操作错误，请联系管理员");
            }
        }else {

            PersonalSummary personalSummary = personalSummaryService.selectId(nurseCollectBo.getPersonalSummaryId());
            personalSummary.setCollectCount(personalSummary.getCollectCount() - 1);
            return R.ok(personalSummaryService.updateById(personalSummary));
        }

    }

    /**
     * 修改护士个人总结收藏关联表
     * @param nurseCollect 护士个人总结收藏关联表
     * @return R
     */
    @ApiOperation(value = "修改护士个人总结收藏关联表", notes = "修改护士个人总结收藏关联表")
    @SysLog("修改护士个人总结收藏关联表" )
    @PutMapping
    public R updateById(@RequestBody NurseCollect nurseCollect) {
        return R.ok(nurseCollectService.updateById(nurseCollect));
    }

    /**
     * 通过id删除护士个人总结收藏关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士个人总结收藏关联表", notes = "通过id删除护士个人总结收藏关联表")
    @SysLog("通过id删除护士个人总结收藏关联表" )
    @DeleteMapping("/{id}" )
    @Transactional
    public R removeById(@PathVariable Integer id) {
        NurseCollect nurseCollect = nurseCollectService.getById(id);
        PersonalSummary personalSummary = personalSummaryService.selectId(nurseCollect.getPersonalSummaryId());
        if(personalSummary == null){
            return R.failed(1,"没有当前数据");
        }
        personalSummary.setCollectCount(personalSummary.getCollectCount()-1);
        boolean b = personalSummaryService.updateById(personalSummary);
        if(!b){
            return R.failed(1,"删除数据错误，请联系管理员");
        }
        return R.ok(nurseCollectService.removeById(id));
    }


    /**
     * 收藏、取消收藏
     * @param nurseCollectBo 护士个人总结收藏关联表
     * @return R
     */
    @ApiOperation(value = "收藏、取消收藏", notes = "收藏、取消收藏")
    @SysLog("收藏、取消收藏" )
    @PostMapping("/collect")
    @Transactional
    public R collect(@RequestBody NurseCollectBo nurseCollectBo) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return R.failed(1, "操作有误！");
        }
        nurseCollectBo.setNurseId(user.getId()+"");
        Integer flag = nurseCollectBo.getCollectFlag();
        if(flag == 0){
            boolean b = nurseCollectService.save(nurseCollectBo);
            if (b) {
                /**
                 * 根据关联表的雪花id查询个人总结表的对应的数据
                 */
                PersonalSummary personalSummary = personalSummaryService.selectId(nurseCollectBo.getPersonalSummaryId());
                personalSummary.setCollectCount(personalSummary.getCollectCount() + 1);
                boolean b1 = personalSummaryService.updateById(personalSummary);
                if (!b1) {
                    return R.failed(1, "操作错误，请联系管理员");
                } else {
                    return R.ok(0, "操作成功");
                }
            } else {
                return R.failed(1, "操作错误，请联系管理员");
            }

        }else{
            NurseCollect nurseCollect = nurseCollectService.selectColl(nurseCollectBo);
            if(nurseCollect != null){
                Integer id = nurseCollect.getId();
                boolean b = nurseCollectService.removeById(id);
                if(b){
                    PersonalSummary personalSummary = personalSummaryService.selectId(nurseCollectBo.getPersonalSummaryId());
                    personalSummary.setCollectCount(personalSummary.getCollectCount() - 1);
                    boolean b1 = personalSummaryService.updateById(personalSummary);
                    if (!b1) {
                        return R.failed(1, "操作错误，请联系管理员");
                    } else {
                        return R.ok(0, "操作成功");
                    }
                }else {
                    return R.failed(1, "操作错误，请联系管理员");
                }
            }else {
                return R.failed(1, "操作错误，请联系管理员");
            }

        }

    }

}
