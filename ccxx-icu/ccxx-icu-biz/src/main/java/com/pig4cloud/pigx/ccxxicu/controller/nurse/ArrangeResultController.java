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

package com.pig4cloud.pigx.ccxxicu.controller.nurse;

import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.ArrangeResultBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeResultService;
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
 * 排班结果表  
 *
 * @author pigx code generator
 * @date 2019-08-07 14:54:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/arrangeResult" )
@Api(value = "arrangeResult", tags = "排班结果表  管理")
public class ArrangeResultController {

    private final ArrangeResultService arrangeResultService;

	/**
	 *排班结果查询
	 * @param arrangeResultBo
	 * @return
	 */
	@ApiOperation(value = "排班结果查询  ", notes = "排班结果查询  ")
	@SysLog("排班结果查询  " )
	@PostMapping("/getArrangeResult")
	public R getArrangeResult(@RequestBody ArrangeResultBo arrangeResultBo) {
		long l = System.currentTimeMillis();
		R shiftData = arrangeResultService.getShiftData(arrangeResultBo);
		System.out.println(System.currentTimeMillis()-l);


		return shiftData;
	}



    /**
     * 修改排班结果表  
     * @param arrangeResult 排班结果表
     * @return R
     */
    @ApiOperation(value = "修改排班结果表  ", notes = "修改排班结果表  ")
    @SysLog("修改排班结果表  " )
    @PostMapping("/update")
    public R updateById(@RequestBody ArrangeResult arrangeResult) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员

			arrangeResult.setUpdateUserId(user.getId()+"");

			return R.ok(arrangeResultService.updateById(arrangeResult));

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			arrangeResult.setUpdateUserId(user.getId()+"");

			return R.ok(arrangeResultService.updateById(arrangeResult));
		}

        return R.failed(1, "操作有误！");
    }

    /**
     * 通过id删除排班结果表  
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除排班结果表  ", notes = "通过id删除排班结果表  ")
    @SysLog("通过id删除排班结果表  " )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		ArrangeResult arrangeResult = arrangeResultService.getById(id);

		arrangeResult.setDelFlag(1);

		arrangeResult.setDelTime(LocalDateTime.now());

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员

			arrangeResult.setDelUserId(user.getId()+"");

			return R.ok(arrangeResultService.updateById(arrangeResult));

		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			arrangeResult.setDelUserId(user.getId()+"");

			return R.ok(arrangeResultService.updateById(arrangeResult));
		}

		return R.failed(1, "操作有误！");
    }

}
