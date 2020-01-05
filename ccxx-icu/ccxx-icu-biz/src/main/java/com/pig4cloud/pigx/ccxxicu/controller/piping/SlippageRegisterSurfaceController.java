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

package com.pig4cloud.pigx.ccxxicu.controller.piping;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.SlippageRegisterSurface;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipingVo;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.piping.PipingService;
import com.pig4cloud.pigx.ccxxicu.service.piping.SlippageRegisterSurfaceService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管道滑脱登记表
 *
 * @author pigx code generator
 * @date 2019-08-21 10:30:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/slippageregistersurface" )
@Api(value = "slippageregistersurface", tags = "管道滑脱登记表管理")
public class SlippageRegisterSurfaceController {

    private final SlippageRegisterSurfaceService slippageRegisterSurfaceService;

	/**
	 * 管道
	 */
	private final PipingService pipingService;

	/**
	 * 患者
	 */
	private final PatientService patientService;



    /**
     * 分页查询
     * @param page 分页对象
     * @param slippageRegisterSurface 管道滑脱登记表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPipSlippageRegisterSurfacePage(Page page, SlippageRegisterSurface slippageRegisterSurface) {

		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(slippageRegisterSurfaceService.selectPaging(page,slippageRegisterSurface));
		}
    	slippageRegisterSurface.setDeptId(SecurityUtils.getUser().getDeptId()+""); //科室id
        return R.ok(slippageRegisterSurfaceService.selectPaging(page,slippageRegisterSurface));
    }


    /**
     * 通过id查询管道滑脱登记表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(slippageRegisterSurfaceService.getById(id));
    }


	/**
	 * 新增前添加点击
	 * @return
	 */
	@ApiOperation(value = "新增前添加点击",notes = "新增前添加点击")
	@SysLog("新增前添加点击")
	@GetMapping("/preJump")
    public R preJump(String patientId){

		Map<String,Object> test = new HashMap<>();

		/* 管道信息    distinck*/
		Piping piping = new Piping();
		piping.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
		List<PipingVo> pipingVos = pipingService.selectAll(piping);
		if (CollectionUtils.isNotEmpty(pipingVos)){
			test.put("pipingVos",pipingVos);
		}

		Patient patient = patientService.getByPatientId(patientId);
		if (patient!=null){
			test.put("patient",patient);
		}

		List<String> dictionaries = new ArrayList<>();
		dictionaries.add("education_level"); //教育程度
		dictionaries.add("piping_causes_of_slippage"); //滑脱原因
		dictionaries.add("piping_fixed_method"); //  固定方法
		dictionaries.add("piping_treatment_measures"); // 处理措施
		dictionaries.add("complication_describe");  // 并发症

		test.put("dictionaries",dictionaries);

		return R.ok(test);
	}




    /**
     * 新增管道滑脱登记表
     * @param slippageRegisterSurface 管道滑脱登记表
     * @return R
     */
    @ApiOperation(value = "新增管道滑脱登记表", notes = "新增管道滑脱登记表")
    @SysLog("新增管道滑脱登记表" )
    @PostMapping("/add")
    public R save(@RequestBody SlippageRegisterSurface slippageRegisterSurface) {
    	slippageRegisterSurface.setDeptId(SecurityUtils.getUser().getDeptId()+"");
    	slippageRegisterSurface.setFiller(SecurityUtils.getUser().getId()+"");
    	if (slippageRegisterSurface.getFillerDate()==null){ // 当前台传来的填表时间为空的时候，自动吧当前时间设置为填表时间
			slippageRegisterSurface.setFillerDate(LocalDateTime.now());
		}

        return R.ok(slippageRegisterSurfaceService.save(slippageRegisterSurface));
    }

    /**
     * 修改管道滑脱登记表
     * @param slippageRegisterSurface 管道滑脱登记表
     * @return R
     */
    @ApiOperation(value = "修改管道滑脱登记表", notes = "修改管道滑脱登记表")
    @SysLog("修改管道滑脱登记表" )
    @PostMapping("/update")
    public R updateById(@RequestBody SlippageRegisterSurface slippageRegisterSurface) {
        return R.ok(slippageRegisterSurfaceService.updateById(slippageRegisterSurface));
    }

    /**
     * 通过id删除管道滑脱登记表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除管道滑脱登记表", notes = "通过id删除管道滑脱登记表")
    @SysLog("通过id删除管道滑脱登记表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {

		SlippageRegisterSurface byId = slippageRegisterSurfaceService.getById(id);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");
		byId.setDelFlag(1);

		return R.ok(slippageRegisterSurfaceService.updateById(byId));
    }

	/**
	 * 数据回填 【已连接管道】
	 * @param slippageRegisterSurface
	 * @return
	 */
	@ApiOperation(value = "数据回填 【已连接管道】", notes = "数据回填 【已连接管道】")
	@SysLog("数据回填 【已连接管道】" )
	@GetMapping("/dataDackfilling")
    public R dataDackfilling(SlippageRegisterSurface slippageRegisterSurface){
    	return R.ok(slippageRegisterSurfaceService.dataDackfilling(slippageRegisterSurface));
	}

}
