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
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DeptCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisData;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisCodeEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisDataInterfaceEnum;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DeptCorrelationService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * his【科室】与我们科室关联表
 *
 * @author pigx code generator
 * @date 2019-10-08 20:12:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/hisdeptcorrelation" )
@Api(value = "hisdeptcorrelation", tags = "his【科室】与我们科室关联表管理")
public class DeptCorrelationController {

    private final DeptCorrelationService deptCorrelationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param deptCorrelation his【科室】与我们科室关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getHisDeptCorrelationPage(Page page, DeptCorrelation deptCorrelation) {
        return R.ok(deptCorrelationService.page(page, Wrappers.query(deptCorrelation)));
    }


    /**
     * 通过id查询his【科室】与我们科室关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(deptCorrelationService.getById(id));
    }

    /**
     * 新增his【科室】与我们科室关联表
     * @param deptCorrelation his【科室】与我们科室关联表
     * @return R
     */
    @ApiOperation(value = "新增his【科室】与我们科室关联表", notes = "新增his【科室】与我们科室关联表")
    @SysLog("新增his【科室】与我们科室关联表" )
    @PostMapping("/add")
    public R save(@RequestBody DeptCorrelation deptCorrelation) {
    	deptCorrelation.setCreateTime(LocalDateTime.now()); //新建时间
    	deptCorrelation.setCreateUserId(SecurityUtils.getUser().getId()+""); // 新建用户
    	deptCorrelation.setHospitalCode(HisCodeEnum.DISCONTINUE_USE.getCode());//医院表示
        return R.ok(deptCorrelationService.save(deptCorrelation));
    }

    /**
     * 修改his【科室】与我们科室关联表
     * @param deptCorrelation his【科室】与我们科室关联表
     * @return R
     */
    @ApiOperation(value = "修改his【科室】与我们科室关联表", notes = "修改his【科室】与我们科室关联表")
    @SysLog("修改his【科室】与我们科室关联表" )
    @PostMapping("/update")
    public R updateById(@RequestBody DeptCorrelation deptCorrelation) {
        return R.ok(deptCorrelationService.updateById(deptCorrelation));
    }

    /**
     * 通过id删除his【科室】与我们科室关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除his【科室】与我们科室关联表", notes = "通过id删除his【科室】与我们科室关联表")
    @SysLog("通过id删除his【科室】与我们科室关联表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		DeptCorrelation byId = deptCorrelationService.getById(id);
		byId.setDelFlag(1);//删除标识
		byId.setDelTime(LocalDateTime.now());//删除时间
		byId.setDelUserId(SecurityUtils.getUser()+"");
		return R.ok(deptCorrelationService.updateById(byId));
    }

	/**
	 * 通过科室id删除关联关系
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过科室id删除关联关系",notes = "通过科室id删除关联关系")
	@SysLog("通过科室id删除关联关系")
	@GetMapping("/deptId/{id}")
    public R deptId(@PathVariable("id")String id){
    	return R.ok(deptCorrelationService.deptIdSelect(id));
	}

	/**
	 *通过科室查询当前科室关联的his数据
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过科室查询当前科室关联的his数据",notes = "通过科室查询当前科室关联的his数据")
	@SysLog("通过科室查询当前科室关联的his数据")
	@GetMapping("/deptIdSelect/{id}")
	public R deptIdSelect(@PathVariable("id")String id){
		return R.ok(deptCorrelationService.deptIdSelect(id));
	}




	/**
	 * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * ///////////////////////////////////     //////        ///////     ///////////////////////////////////////////////
	 * ////////////////////////////////////////     测试数据    /////////////////////////////////////////////////////////
	 * ////////////////////////////////////////////         ////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * @return
	 */

	@GetMapping("/httpTest")
	public R http(){
			HisData hisData = new HisData();
			hisData.setDb("王伟同学");
			hisData.setFZYRegisterID("你妈喊你回家吃饭");

		String http = deptCorrelationService.http(HisDataInterfaceEnum.HIS_PATIENT.getCode(), hisData);
		System.out.println("接收到数据---------------"+http);
		return R.ok(http);
	}

	@Inner(value = false)
	@PostMapping("/postTest")
	public R postTest(@RequestBody HisData hisData){
		System.out.println("收到的数据--------------------"+ hisData);
		hisData.setDb("郑昌裕同学！！！！！");
		hisData.setFZYRegisterID("你妈喊你回家吃饭！！！！");
		return R.ok(hisData);
	}


}
