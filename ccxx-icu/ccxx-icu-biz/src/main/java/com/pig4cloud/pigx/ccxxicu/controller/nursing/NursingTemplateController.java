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

package com.pig4cloud.pigx.ccxxicu.controller.nursing;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UseNursingTemplateBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingTemplate;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingShowVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.NursingTemplateUseVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nursing.NursingTemplateService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 护理模板
 *
 * @author pigx code generator
 * @date 2019-08-19 17:00:03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/nursingTemplate")
@Api(value = "nursingTemplate", tags = "护理模板管理")
public class NursingTemplateController {

	private final NursingTemplateService nursingTemplateService;

	private final ProjectRecordService projectRecordService;

	/**
	 * 分页查询
	 *
	 * @param page              分页对象
	 * @param nursingTemplateBo 护理模板
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getNursingTemplatePage(Page page, NursingTemplateBo nursingTemplateBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(nursingTemplateService.selectByPage(page, nursingTemplateBo));

		}

		nursingTemplateBo.setDeptId(user.getDeptId() + "");

		if (roleCodes.get(0).equals("ROLE_MATRON")) {
			//护士长

			return R.ok(nursingTemplateService.selectByPage(page, nursingTemplateBo));
		}
		nursingTemplateBo.setNurseId(user.getId() +"");
		nursingTemplateBo.setOrderBy("case when nt.template_use_scope = 1 then 0 else 1 end,case when nt.create_user_id =  "+user.getId() +"then 0 else 1 end");
		return R.ok(nursingTemplateService.selectByPage(page, nursingTemplateBo));

	}


	/**
	 * 通过id查询护理模板
	 *
	 * @param templateCode 唯一标识
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getCode/{templateCode}")
	public R getById(@PathVariable("templateCode") String templateCode) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//查询出该code对应的数据
		if (StringUtils.isEmpty(templateCode)) {
			return R.failed(1, "操作失败！");
		}


		return R.ok(nursingTemplateService.selectByCode(templateCode));
	}


	/**
	 * 新增护理模板
	 * 带项目版
	 * @param nursingTemplates 护理模板
	 * @return R
	 */
	@ApiOperation(value = "新增护理模板", notes = "新增护理模板")
	@SysLog("新增护理模板")
	@PostMapping("/add")
	public R save(@RequestBody List<NursingTemplate> nursingTemplates) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//用于作为唯一的code
		long id = SnowFlake.getId();
		//遍历添加数据
		nursingTemplates.forEach(nursingTemplate -> {
			nursingTemplate.setNursingTemplateId(SnowFlake.getId() + "");
			nursingTemplate.setDelFlag(0);
			nursingTemplate.setTemplateCode(id + "");
			nursingTemplate.setCreateUserId(user.getId() + "");
			nursingTemplate.setCreateTime(LocalDateTime.now());
			nursingTemplate.setDeptId(user.getDeptId() + "");
		});
		return R.ok(nursingTemplateService.saveBatch(nursingTemplates));
	}

	/**
	 * 新增护理模板
	 * 纯文字版
	 * @param nursingTemplate 护理模板
	 * @return R
	 */
	@ApiOperation(value = "新增护理模板", notes = "新增护理模板")
	@SysLog("新增护理模板")
	@PostMapping("/save")
	public R add(@RequestBody NursingTemplate nursingTemplate) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//用于作为唯一的code
		long id = SnowFlake.getId();

		nursingTemplate.setNursingTemplateId(SnowFlake.getId() + "");
		nursingTemplate.setDelFlag(0);
		nursingTemplate.setTemplateCode(id + "");
		nursingTemplate.setCreateUserId(user.getId() + "");
		nursingTemplate.setCreateTime(LocalDateTime.now());
		nursingTemplate.setDeptId(user.getDeptId() + "");
		return R.ok(nursingTemplateService.save(nursingTemplate));
	}


	/**
	 * 通过id删除护理模板
	 *
	 * @param templateCode 唯一标识
	 * @return R
	 */
	@ApiOperation(value = "通过id删除护理模板", notes = "通过id删除护理模板")
	@SysLog("通过id删除护理模板")
	@GetMapping("/del/{templateCode}")
	public R removeById(@PathVariable String templateCode) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		//查询出该code对应的数据
		List<NursingTemplate> nursingTemplates = nursingTemplateService.list(Wrappers
				.<NursingTemplate>query().lambda()
				.eq(NursingTemplate::getTemplateCode, templateCode)
				.eq(NursingTemplate::getDelFlag, 0)
		);

		if (nursingTemplates != null && nursingTemplates.size() > 0) {
			//当登录者为护士 且要删除的数据为科室公共数据时或者删除非个人的数据
			if (roleCodes.get(0).equals("ROLE_NURSE") && "1".equals(nursingTemplates.get(0).getTemplateUseScope())
					|| user.getId().equals(nursingTemplates.get(0).getCreateUserId())) {
				return R.failed(1, "操作有误！");
			}

			nursingTemplates.forEach(nursingTemplate -> {
				nursingTemplate.setDelUserId(user.getId() + "");
				nursingTemplate.setDelFlag(1);
				nursingTemplate.setDelTime(LocalDateTime.now());
			});

			return R.ok(nursingTemplateService.updateBatchById(nursingTemplates));

		}

		return R.failed(1, "操作有误！");
	}


	@ApiOperation(value = "使用护理模板", notes = "使用护理模板")
	@SysLog("使用护理模板")
	@GetMapping("/useTemplate")
	public R useTemplate(UseNursingTemplateBo useNursingTemplateBo) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (useNursingTemplateBo.getIsProjectFlag().equals(0)) {

			return R.ok(nursingTemplateService.selectByCode(useNursingTemplateBo.getTemplateCode()));

		}


		//查询该模板
		List<NursingShowVo> nursingShowVos = nursingTemplateService.selectByCode(useNursingTemplateBo.getTemplateCode());


		//将数据按项目分类
		HashMap<String, String> collect = new HashMap<>();

		//查询对应的项目数据
		nursingShowVos.forEach(ar -> {

			ProjectRecord oneRecord = projectRecordService.getOneRecord(ar.getProjectId(), useNursingTemplateBo.getPatientId());

			collect.put(ar.getProjectId(), oneRecord == null ? "" : oneRecord.getProjectSpecificRecord());

		});

		List<NursingTemplateUseVo> records = new ArrayList<>();

		//将数据重新封装
		nursingShowVos.forEach(ar -> {

			NursingTemplateUseVo msg = new NursingTemplateUseVo();
			BeanUtil.copyProperties(ar, msg);
			//封装项目数据
			msg.setProjectSpecificRecord(collect.get(ar.getProjectId()));
			records.add(msg);

		});
		return R.ok(records);

	}


	@ApiOperation(value = "使用护理模板", notes = "使用护理模板")
	@SysLog("使用护理模板")
	@GetMapping("/useTemplates")
	public R useTemplates(UseNursingTemplateBo useNursingTemplateBo) {
		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}
		List<NursingTemplateUseVo> nursingTemplateUseVos = nursingTemplateService.useTemplate(useNursingTemplateBo);
		return R.ok(nursingTemplateUseVos);

	}
}
