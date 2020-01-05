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


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.*;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipingVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.UseRecordVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.RegularMatchUtils;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nursing.IntakeOutputRecordService;
import com.pig4cloud.pigx.ccxxicu.service.piping.*;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 引流液记录表id
 *
 * @author pigx code generator
 * @date 2019-08-12 17:22:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/drainagerecords" )
@Api(value = "drainagerecords", tags = "引流液记录表id管理")
public class DrainageRecordController {

    private final DrainageRecordService drainageRecordService;
	/**
	 * 管道使用记录
	 */
	private final UseRecordsService useRecordsService;
	/**
	 * 管道信息
	 */
	private final PipingService pipingService;

	/**
	 * 引流液属性
	 */
	private final DrainageAttributeService drainageAttributeService;
	/**
	 * 出入量
	 */
	private final IntakeOutputRecordService intakeOutputRecordService;
	/**
	 * 引流液
	 */
	private final DrainageService drainageService;

	/**
	 * 项目表
	 */
	private final ProjectService projectService;









    /**
     * 通过id查询引流液记录表id
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {


    	return R.ok(drainageRecordService.getById(id));
    }


	/**
	 * 在新增引流液之后需要查询相应关闭的管道信息，并展示当前管道相对应的属性
	 *
	 * @return
	 */
	@ApiOperation(value = "新增全跳转页面",notes = "新增前跳转页面")
	@SysLog("新增前跳转页面")
	@PostMapping("/preJump")
    public R preJump(){

		UseRecordVo useRecordsVo = new UseRecordVo();
		if("ROLE_MATRON".equals(SecurityUtils.getRoleCodes().get(0))){ //护士长
			useRecordsVo.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
		}if ("ROLE_NURSE".equals(SecurityUtils.getRoleCodes().get(0))){//护士
			useRecordsVo.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
			useRecordsVo.setNurseId(SecurityUtils.getUser().getId()+""); //护士id
		}
		LocalDateTime nowTime = LocalDateTime.now();// 当前时间
		LocalDateTime sdateTime = LocalDateTime.now().plusHours(-12); // 减去12小时之后的时间
		useRecordsVo.setSTime(sdateTime); // .开始时间
		useRecordsVo.setETime(nowTime);  // 结束时间
		return R.ok(useRecordsService.selectAll(useRecordsVo));
	}


	/**通过管道查询相应的引流液的数据
	 * 当护士选择一条管道信息时，查询当前管道关联的引流液，再通过引流液查询相对应的数据
	 * @return
	 */
	@ApiOperation(value = "通过管道查询相应的引流液的数据",notes = "通过管道查询相应的引流液的数据")
	@SysLog("通过管道查询相应的引流液的数据")
	@PostMapping("/selectDrainageRecords")
	public R selectDrainageRecords (@RequestBody UseRecord useRecord){

		Piping piping = new Piping();
		piping.setPipingId(useRecord.getPipingId());
		PipingVo pipingId = pipingService.getPipingId(piping);/* 通过管道id查询出相对应的引流液id */


		if (pipingId ==null){
			return R.failed(null,"没有当前管道信息");
		}
		// 通过当前的引流液数据查询数相对应的引流液属性
		DrainageAttribute drainageAttribute = new DrainageAttribute();
		drainageAttribute.setDrainageId(pipingId.getDrainageId());
		/* 查询出引流液对应的属性 */
		List<DrainageAttribute> list = drainageAttributeService.distinctName(drainageAttribute);
		if (list.size()==0){
		return R.failed(null,"当前管道没有添加对应的引流液数据");
		}

		Map<String,Object>test = new HashMap<>();
		for (int i =0;i<list.size();i++){
			DrainageAttribute attribute = new DrainageAttribute();
			attribute.setDrainageId(pipingId.getDrainageId());
			attribute.setAttributeName(list.get(i).getAttributeName());
			List<DrainageAttribute> Attribute = drainageAttributeService.selectNameType(attribute);
			test.put(list.get(i).getAttributeName(),Attribute);
		}
		Map<String,Object> data = new HashMap<>();
		data.put("piping",pipingId);
		data.put("attributeType",test);
		data.put("attributeName",list);
		return R.ok(data);
	}




    /**
     * 新增引流液记录表id
     * @param drainageRecord 引流液记录表id
     * @return R
     */
    @ApiOperation(value = "新增引流液记录表id", notes = "新增引流液记录表id")
    @SysLog("新增引流液记录表id" )
	@Transactional
    @PostMapping("/add")
    public R save(@RequestBody DrainageRecord drainageRecord) {
		String id =SnowFlake.getId()+"";
		drainageRecord.setDrainageRecordsId(id);//雪花
    	drainageRecord.setCreateTime(LocalDateTime.now()); // 开始时间
		drainageRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");//创建人
		drainageRecord.setNurseId(SecurityUtils.getUser().getId()+"");//护士
		drainageRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室

		boolean save = drainageRecordService.save(drainageRecord);
		if (save==false){
			return R.failed(null,"引流液添加失败！！");
		}
		/* 同时添加出入量数据 */
		IntakeOutputRecord intakeOutputRecord = new IntakeOutputRecord();
		intakeOutputRecord.setIntakeOutputId(SnowFlake.getId()+"");// //雪花
		intakeOutputRecord.setPatientId(drainageRecord.getPatientId());//患者id
		intakeOutputRecord.setIntakeOutputType(0);//出量标识
		intakeOutputRecord.setIntakeOutputValue(drainageRecord.getNormal()); //出入量值
		intakeOutputRecord.setDelFlag(0);//删除标识
		intakeOutputRecord.setCreateTime(LocalDateTime.now());//创建时间
		intakeOutputRecord.setCreateUserId(SecurityUtils.getUser().getId()+"");//创建人
		intakeOutputRecord.setDeptId(SecurityUtils.getUser().getDeptId()+""); ///科室
		intakeOutputRecord.setSourceId(id);//数据关联id
		/*通过引流液id 查询当前 引流液的名称  */
		Drainage drainage = new Drainage();
		drainage.setDrainageId(drainageRecord.getDrainageId());
		Drainage drainageId = drainageService.getDrainageId(drainage);
		if (drainageId ==null){
			return R.failed(null,"没有引流液的数据源");
		}

		intakeOutputRecord.setValueDescription(drainageId.getDrainageName());//出入量值描述
		intakeOutputRecord.setIntakeOutputValue(RegularMatchUtils.getInteger(drainageRecord.getValue())); //出入值
		/* 查询引流液中项目中的编号
		* 判断当前引流液是否为尿液
		* */
		if (drainageId.getDrainageName().equals("尿液")){
			Project project = projectService.selectByName("尿液");
			if (project !=null){
				intakeOutputRecord.setProjectId(project.getProjectId());
			}
		}else {
			Project project = projectService.selectByName("引流液");
			if (project !=null){
				intakeOutputRecord.setProjectId(project.getProjectId());
			}
		}
		return R.ok(intakeOutputRecordService.add(intakeOutputRecord));
    }

    /**
     * 修改引流液记录表id
     * @param drainageRecord 引流液记录表id
     * @return R
     */
    @ApiOperation(value = "修改引流液记录表id", notes = "修改引流液记录表id")
    @SysLog("修改引流液记录表id" )
    @PostMapping("/update")
    public R updateById(@RequestBody DrainageRecord drainageRecord) {
        return R.ok(drainageRecordService.updateById(drainageRecord));
    }

    /**
     * 通过id删除引流液记录表id
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除引流液记录表id", notes = "通过id删除引流液记录表id")
    @SysLog("通过id删除引流液记录表id" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
		DrainageRecord byId = drainageRecordService.getById(id);
		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(SecurityUtils.getUser().getId()+"");

		return R.ok(drainageRecordService.updateById(byId));
    }


	/**
	 * 分页查询数据
	 * @param drainageRecord
	 * @return
	 */
	@ApiOperation(value = "分页查询数据",notes = "分页查询数据")
	@SysLog("分页查询数据源")
	@GetMapping("/selectPaging")
    public R selectPaging(Page page, DrainageRecord drainageRecord){

		if ("ROLE_ADMIN".equals(SecurityUtils.getRoleCodes().get(0))){
			return R.ok(drainageRecordService.selectPaging(page, drainageRecord));
		}
		drainageRecord.setDeptId(SecurityUtils.getUser().getDeptId()+"");//科室
    	return R.ok(drainageRecordService.selectPaging(page, drainageRecord));
	}




}
