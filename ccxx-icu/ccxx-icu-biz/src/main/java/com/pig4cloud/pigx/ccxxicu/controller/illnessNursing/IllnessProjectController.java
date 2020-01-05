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

package com.pig4cloud.pigx.ccxxicu.controller.illnessNursing;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectVo;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.illnessNursing.IllnessProjectService;
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
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 病情护理项目
 *
 * @author pigx code generator
 * @date 2019-10-16 16:36:40
 */
@RestController
@AllArgsConstructor
@RequestMapping("/illnessProject" )
@Api(value = "illnessProject", tags = "病情护理项目管理")
public class IllnessProjectController {

    private final IllnessProjectService illnessProjectService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param illnessProject 病情护理项目
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getIllnessProjectPage(Page page, IllnessProject illnessProject) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		illnessProject.setDeptId(user.getDeptId()+"");

        return R.ok(illnessProjectService.page(page, Wrappers.query(illnessProject)));
    }


    /**
     * 通过id查询病情护理项目
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(illnessProjectService.getById(id));
    }

    /**
     * 新增病情护理项目
     * @param illnessProject 病情护理项目
     * @return R
     */
    @ApiOperation(value = "新增病情护理项目", notes = "新增病情护理项目")
    @SysLog("新增病情护理项目" )
    @PostMapping("/add")
    public R save(@RequestBody List<IllnessProject> illnessProject) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (CollectionUtils.isEmpty(illnessProject)) {
			return R.failed(false);
		}

		//查询原数据
		List<IllnessProject> list = illnessProjectService.list(Wrappers.<IllnessProject>query().lambda()
				.eq(IllnessProject::getDelFlag, 0)
				.eq(IllnessProject::getIllnessNursingTypeFlag, illnessProject.get(0).getIllnessNursingTypeFlag())
				.eq(IllnessProject::getDeptId,user.getDeptId()+"")
		);

		//当原始数据为空时 直接新增
		if (CollectionUtils.isEmpty(list)) {

			illnessProject.forEach(ar->{
				ar.setDelFlag(0);
				ar.setDeptId(user.getDeptId()+"");
				ar.setCreateTime(LocalDateTime.now());
				ar.setCreateUserId(user.getId()+"");
				ar.setIllnessProjectId(SnowFlake.getId()+"");
				illnessProjectService.save(ar);
			});

			return R.ok(true);
		}

		Map<String, List<IllnessProject>> collect = list.stream().collect(Collectors.groupingBy(IllnessProject::getProjectId));
		Map<String, List<IllnessProject>> collect1 = illnessProject.stream().collect(Collectors.groupingBy(IllnessProject::getProjectId));
		//循环原始数据
		list.forEach(ar->{
			//判断新数据中是否存在
			List<IllnessProject> illnessProjects = collect1.get(ar.getProjectId());

			if (CollectionUtils.isEmpty(illnessProjects)) {
				//新数据中不存在  将原数据删除
				ar.setDelFlag(1);
				ar.setDelTime(LocalDateTime.now());
				ar.setDelUserId(user.getId()+"");
				//这里需要将对应的状态和建议删除
				illnessProjectService.delByUpdate(ar);

			}


		});
		//循环新数据
		illnessProject.forEach(ar->{
			List<IllnessProject> illnessProjects = collect.get(ar.getProjectId());
			if (CollectionUtils.isEmpty(illnessProjects)) {
				//原数据中不存在  进行新增
				ar.setDelFlag(0);
				ar.setCreateTime(LocalDateTime.now());
				ar.setCreateUserId(user.getId()+"");
				ar.setDeptId(user.getDeptId()+"");
				ar.setIllnessProjectId(SnowFlake.getId()+"");
				illnessProjectService.save(ar);
			}
		});

		return R.ok(true);
    }

    /**
     * 通过id删除病情护理项目
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除病情护理项目", notes = "通过id删除病情护理项目")
    @SysLog("通过id删除病情护理项目" )
    @GetMapping("/del/{id}" )
    public R removeById(@PathVariable Integer id) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		IllnessProject byId = illnessProjectService.getById(id);

		byId.setDelFlag(1);
		byId.setDelTime(LocalDateTime.now());
		byId.setDelUserId(user.getId()+"");

		return R.ok(illnessProjectService.delByUpdate(byId));
    }

	/**
	 * 根据类型查询对应的项目
	 * @param illnessNursingTypeFlag illnessNursingTypeFlag
	 * @return R
	 */
	@ApiOperation(value = "根据类型查询对应的项目", notes = "根据类型查询对应的项目")
	@SysLog("根据类型查询对应的项目" )
	@GetMapping("/type/{illnessNursingTypeFlag}" )
	public R getByType(@PathVariable("illnessNursingTypeFlag") Integer illnessNursingTypeFlag) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		return R.ok(illnessProjectService.getByType(illnessNursingTypeFlag));
	}

	/**
	 * 获取各项目的项目名
	 *
	 * @return
	 */
	@ApiOperation(value = "获取各类的项目", notes = "获取各类的项目")
	@GetMapping("/project/{illnessNursingTypeFlag}")
	public R getTypeProject(@PathVariable("illnessNursingTypeFlag") Integer illnessNursingTypeFlag) {

		PigxUser user = SecurityUtils.getUser();


		if (user == null) {

			R.failed(1, "操作有误！");

		}

		List<ProjectVo> project = illnessProjectService.getProject(illnessNursingTypeFlag);

		if (CollectionUtils.isEmpty(project)) {

			return R.failed("项目为空，请先去维护项目！");

		}

		Map<Integer, List<ProjectVo>> collect = project.stream().collect(Collectors.groupingBy(Project::getProjectType));

		return R.ok(collect);

		/*List<Project> list = projectService.list(Wrappers.<Project>query().lambda()
				.eq(Project::getDelFlag, 0)
				.eq(Project::getDeptId, user.getDeptId() + "")
		);
		if (CollectionUtils.isEmpty(list)) {

			return R.failed("尚未维护项目，请先维护项目！");

		}
		List<ProjectVo> projectVos = new ArrayList<>();


		Vector<Thread> threadVector = new Vector<>(list.size());
		list.forEach(ar-> {
			Thread thread = new Thread() {
				@Override
				public void run() {
					IllnessProject illnessProject = new IllnessProject();
					illnessProject.setDeptId(user.getDeptId()+"");
					illnessProject.setDelFlag(0);
					illnessProject.setIllnessNursingTypeFlag(illnessNursingTypeFlag);
					ProjectVo projectVo = new ProjectVo();
					BeanUtil.copyProperties(ar,projectVo);
					illnessProject.setProjectId(ar.getProjectId());
					IllnessProject one = illnessProjectService.getOne(new QueryWrapper<>(illnessProject));
					if (one == null) {
						projectVo.setPitchOn(1);
					} else {
						projectVo.setPitchOn(0);
					}
					projectVos.add(projectVo);

					super.run();
				}
			};

			thread.start();
			threadVector.add(thread);


		});

		threadVector.forEach(ar->{

			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

			Map<Integer, List<ProjectVo>> collect = projectVos.stream().collect(Collectors.groupingBy(Project::getProjectType));
			*/



	}




}
