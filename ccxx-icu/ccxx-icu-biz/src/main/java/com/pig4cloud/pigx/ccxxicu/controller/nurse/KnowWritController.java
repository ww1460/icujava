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


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.KnowWrit;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nurse.KnowWritService;
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
 * 知情文书表 
 *
 * @author pigx code generator
 * @date 2019-08-17 14:55:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/knowWrit" )
@Api(value = "knowWrit", tags = "知情文书表 管理")
public class KnowWritController {

    private final KnowWritService knowWritService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param knowWrit 知情文书表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getKnowWritPage(Page page, KnowWrit knowWrit) {

		PigxUser user = SecurityUtils.getUser();
		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (!roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员  查看全部

 			knowWrit.setDeptId(user.getDeptId()+"");

		}

		knowWrit.setDelFlag(0);

		return R.ok(knowWritService.page(page, Wrappers.query(knowWrit)));
    }


    /**
     * 通过id查询知情文书表 
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(knowWritService.getById(id));
    }

    /**
     * 新增知情文书表 
     * @param knowWrit 知情文书表
     * @return R
     */
    @ApiOperation(value = "新增知情文书表 ", notes = "新增知情文书表 ")
    @SysLog("新增知情文书表 " )
    @PostMapping("/add")
    public R save(@RequestBody KnowWrit knowWrit) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (StringUtils.isEmpty(knowWrit.getWritContent())) {
			return R.failed(1, "操作失败！");
		}


		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员 在新增时 需要添加科室id
			if (knowWrit.getDeptId()==null) {

				return R.failed("请选择科室");

			}


		} else if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长
			knowWrit.setDeptId(user.getDeptId()+"");

		} else {

			return R.failed(1, "操作有误！");

		}
		//生成id
		knowWrit.setKnowWritId(SnowFlake.getId()+"");
		knowWrit.setCreateUserId(user.getId()+"");
		knowWrit.setCreateTime(LocalDateTime.now());
		knowWrit.setDelFlag(0);

    	return R.ok(knowWritService.add(knowWrit));
    }

    /**
     * 修改知情文书表 
     * @param knowWrit 知情文书表
     * @return R
     */
    @ApiOperation(value = "修改知情文书表 ", notes = "修改知情文书表 ")
    @SysLog("修改知情文书表 " )
    @PostMapping("/update")
    public R updateById(@RequestBody KnowWrit knowWrit) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")){

			return R.failed(1, "操作有误！");

		}

		knowWrit.setUpdateUserId(user.getId()+"");

        return R.ok(knowWritService.updateById(knowWrit));
    }

    /**
     * 通过id删除知情文书表 
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除知情文书表 ", notes = "通过id删除知情文书表 ")
    @SysLog("通过id删除知情文书表 " )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_NURSE")){

			return R.failed(1, "操作有误！");

		}

		KnowWrit byId = knowWritService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");
		/*knowWritService.update(Wrappers.lambdaQuery(byId).eq(KnowWrit::getDelFlag,1)


		);
*/
		return R.ok(knowWritService.updateById(byId));
    }

	/**
	 * 通过类型查询该科室的文书
	 * @param writType 文书类型
	 * @return
	 */
	@ApiOperation(value = "通过类型查询该科室的文书 ", notes = "通过类型查询该科室的文书 ")
	@SysLog("通过类型查询该科室的文书 " )
    @GetMapping("/getByType/{writType}")
	public R getByType(@PathVariable Integer writType) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		if (StringUtils.isEmpty(writType+"")) {
			return R.failed(1, "操作失败！");
		}

		//返回该科室下的该类型的文书
		return R.ok(knowWritService.list(Wrappers
				.<KnowWrit>query().lambda()
				.eq(KnowWrit::getWritType, writType)
				.eq(KnowWrit::getDeptId,user.getDeptId()+"")
		));

	}

	/**
	 * 通过类型查询该科室的文书
	 * @param writCode 文书简称
	 * @return
	 */
	@ApiOperation(value = "通过类型查询该科室的文书 ", notes = "通过类型查询该科室的文书 ")
	@SysLog("通过类型查询该科室的文书 " )
	@GetMapping("/getByCode/{writCode}")
	public R getByCode(@PathVariable String writCode) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//返回该科室下的该类型的文书
		return R.ok(knowWritService.selectByCode(writCode));

	}

}
