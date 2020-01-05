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

package com.pig4cloud.pigx.ccxxicu.mapper.piping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipingVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管路表
 *
 * @author pigx code generator
 * @date 2019-08-08 15:11:10
 */
public interface PipingMapper extends BaseMapper<Piping> {

	/**
	 * 通过雪花患者id查询数据
	 * @param piping
	 * @return
	 */
	PipingVo getPipingId(@Param("piping") Piping piping);


	/**
	 * 全查管路信息
	 * @param piping
	 * @return
	 */
 	List<PipingVo> selectAll(@Param("piping") Piping piping);


	/**
	 * 分页查询应引流液
	 * @param page
	 * @param piping
	 * @return
	 */
 	IPage<List<PipingVo>> selectPaging(Page page,@Param("piping")Piping piping);

	/**
	 * 获取在线管路数据
	 * @return
	 */
	List<Piping> getOnlinePipingNumber();
}
