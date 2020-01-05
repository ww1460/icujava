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

package com.pig4cloud.pigx.ccxxicu.service.knowledgeBase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.PersonalSummary;

import java.util.List;


/**
 * 个人总结
 *
 * @author pigx code generator
 * @date 2019-11-01 19:55:59
 */
public interface PersonalSummaryService extends IService<PersonalSummary> {

    Boolean delete(Integer id);

    IPage<PersonalSummary> selectCollect(Page page, PersonalSummary personalSummary);

    PersonalSummary selectId(String personalSummaryId);

    IPage<PersonalSummary> selectAll(Page page, PersonalSummary personalSummary);

    IPage<PersonalSummary> selectPersonal(Page page, PersonalSummary personalSummary);
}
