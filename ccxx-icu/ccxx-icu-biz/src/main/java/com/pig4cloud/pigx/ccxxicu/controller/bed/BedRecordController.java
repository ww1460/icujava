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

package com.pig4cloud.pigx.ccxxicu.controller.bed;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.BedRecordBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.bed.BedRecord;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.bed.BedRecordService;
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
 * 床位使用时间记录
 *
 * @author pigx code generator
 * @date 2019-08-07 21:23:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/bedRecord" )
@Api(value = "bedRecord", tags = "床位使用时间记录管理")
public class BedRecordController {

    private final BedRecordService bedRecordService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param bedRecordBo 床位使用时间记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getBedRecordPage(Page page, BedRecordBo bedRecordBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员  查询对应条件的数据
			return R.ok(bedRecordService.selectByPage(page,bedRecordBo));

		}
			//护士长 护士 查询对应科室的数据
			bedRecordBo.setDeptId(user.getDeptId()+"");
			return R.ok(bedRecordService.selectByPage(page,bedRecordBo));




    }


    /**
     * 通过id查询床位使用时间记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {


        return R.ok(bedRecordService.getById(id));
    }

    /**
     * 新增床位使用时间记录
	 * 用于患者入科绑定床位
     * @param bedRecord 床位使用时间记录
     * @return R
     */
    @ApiOperation(value = "新增床位使用时间记录", notes = "新增床位使用时间记录")
    @SysLog("新增床位使用时间记录" )
    @PostMapping("/add")
    public R save(@RequestBody BedRecord bedRecord) {
		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(bedRecord.getPatientId())||StringUtils.isEmpty(bedRecord.getBedId())) {
			return R.failed(1, "操作失败！");
		}

		bedRecord.setCreateTime(LocalDateTime.now());
		bedRecord.setCreateUserId(user.getId()+"");
		bedRecord.setStartTime(LocalDateTime.now());
		bedRecord.setDeptId(user.getDeptId()+"");
		bedRecord.setDelFlag(0);
		long id = SnowFlake.getId();
		bedRecord.setBedRecordId(id+"");
		bedRecord.setUpdateTime(LocalDateTime.now());
		return R.ok(bedRecordService.add(bedRecord));
    }

	/**
	 * 患者更换床位时修改床位使用时间记录
	 * 需要传患者id  和新的床位id
	 * @param bedRecord 床位使用时间记录
	 * @return R
	 */
	@ApiOperation(value = "患者更换床位时修改床位使用时间记录", notes = "患者更换床位时修改床位使用时间记录")
	@SysLog("患者更换床位时修改床位使用时间记录" )
	@PostMapping("/changeBed")
	public R update(@RequestBody BedRecord bedRecord) {
		/**
		 * 传过来的只有患者和床位id
		 */
		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(bedRecord.getBedId())||StringUtils.isEmpty(bedRecord.getPatientId())) {
			return R.failed(1, "操作失败！");
		}


		return bedRecordService.changeBed(bedRecord);
	}


    /**
     * 通过id删除床位使用时间记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除床位使用时间记录", notes = "通过id删除床位使用时间记录")
    @SysLog("通过id删除床位使用时间记录" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			R.failed(1, "操作有误！");

		}

		BedRecord byId = bedRecordService.getById(id);

		//还未记录完成的数据 不允许删除
		if (byId.getEndTime() == null) {

			return R.failed(1, "操作有误！");

		}

		byId.setDelFlag(1);
		byId.setDelUserId(user.getId()+"");
		byId.setDelTime(LocalDateTime.now());

		return R.ok(bedRecordService.updateById(byId));
    }

}
