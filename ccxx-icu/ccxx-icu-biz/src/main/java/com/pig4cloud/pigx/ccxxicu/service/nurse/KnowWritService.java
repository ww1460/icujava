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

package com.pig4cloud.pigx.ccxxicu.service.nurse;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.KnowWrit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知情文书表 
 *
 * @author pigx code generator
 * @date 2019-08-17 14:55:05
 */
public interface KnowWritService extends IService<KnowWrit> {

	/**
	 * 根据项目简称查询
	 * @param writCode
	 * @return
	 */
	List<KnowWrit> selectByCode(@Param("writCode") String writCode);

	/**
	 * 新增知情文书
	 * @param knowWrit
	 * @return
	 */
	boolean add(KnowWrit knowWrit);


}
