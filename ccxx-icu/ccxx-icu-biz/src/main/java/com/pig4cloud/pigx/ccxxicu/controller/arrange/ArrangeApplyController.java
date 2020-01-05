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

package com.pig4cloud.pigx.ccxxicu.controller.arrange;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.arrange.ArrangeApply;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.arrange.ArrangeApplyService;
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
 * 护士班次申请表
 *
 * @author pigx code generator
 * @date 2019-10-30 10:55:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/arrangeApply" )
@Api(value = "arrangeApply", tags = "护士班次申请表管理")
public class ArrangeApplyController {

    private final ArrangeApplyService arrangeApplyService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param arrangeApply 护士班次申请表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getArrangeApplyPage(Page page, ArrangeApply arrangeApply) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员  查询对应条件的数据
			return R.ok();
		}else if(roleCodes.get(0).equals("ROLE_NURSE")){
			arrangeApply.setInitiatorNurseId(user.getId()+"");
		}

		arrangeApply.setDeptId(user.getDeptId()+"");

        return R.ok(arrangeApplyService.selectByPage(page, arrangeApply));
    }


    /**
     * 通过id查询护士班次申请表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(arrangeApplyService.getById(id));
    }

    /**
     * 新增护士班次申请表
     * @param arrangeApply 护士班次申请表
     * @return R
     */
    @ApiOperation(value = "新增护士班次申请表", notes = "新增护士班次申请表")
    @SysLog("新增护士班次申请表" )
    @PostMapping("/add")
    public R save(@RequestBody ArrangeApply arrangeApply) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}
		if (arrangeApply==null|| StringUtils.isEmpty(arrangeApply.getInitiatorNurseId())) {

			return R.failed("申请人不可为空");

		}

		arrangeApply.setArrangeApplyId(SnowFlake.getId()+"");
		arrangeApply.setCreateTime(LocalDateTime.now());
		arrangeApply.setCreateUserId(user.getId()+"");
		arrangeApply.setDeptId(user.getDeptId()+"");

        return R.ok(arrangeApplyService.save(arrangeApply));
    }

    /**
     * 修改护士班次申请表
     * @param arrangeApply 护士班次申请表
     * @return R
     */
    @ApiOperation(value = "修改护士班次申请表", notes = "修改护士班次申请表")
    @SysLog("修改护士班次申请表" )
    @PostMapping("/update")
    public R updateById(@RequestBody ArrangeApply arrangeApply) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		if(arrangeApply==null||arrangeApply.getId()==null){

			return R.failed("数据错误！");

		}


        return R.ok(arrangeApplyService.updateById(arrangeApply));
    }

    /**
     * 通过id删除护士班次申请表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除护士班次申请表", notes = "通过id删除护士班次申请表")
    @SysLog("通过id删除护士班次申请表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			return R.failed(1, "操作有误！");

		}

		ArrangeApply byId = arrangeApplyService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");

		return R.ok(arrangeApplyService.updateById(byId));
    }

}
