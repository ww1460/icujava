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

package com.pig4cloud.pigx.ccxxicu.mapper.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.WarmJudge;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.WarmJudgeVo;
import org.apache.ibatis.annotations.Param;

/**
 * 预警项目判断表
 *
 * @author pigx code generator
 * @date 2019-08-09 19:58:20
 */
public interface WarmJudgeMapper extends BaseMapper<WarmJudge> {

	/**
	 * 分页查询所有项目的预警判断值
	 * @param page
	 * @param warmJudgeVo
	 * @return
	 */
	IPage<WarmJudgeVo> selectByPage(Page page,@Param("query") WarmJudgeVo warmJudgeVo);

	/**
	 * 根据项目id   查询该项目的预警判断
	 * @param projectId
	 * @return
	 */
	WarmJudge selectByProjectId(@Param("projectId") String projectId);

}
