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

package com.pig4cloud.pigx.ccxxicu.mapper.knowledgeBase;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.PersonalSummary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 个人总结
 *
 * @author pigx code generator
 * @date 2019-11-01 19:55:59
 */
public interface PersonalSummaryMapper extends BaseMapper<PersonalSummary> {

    @Select("select * from know_personal_summary where personal_summary_id = #{personalSummaryId}")
    PersonalSummary selectId(@Param("personalSummaryId") String personalSummaryId);

    IPage<PersonalSummary> selectAll(Page page, @Param("personalSummary") PersonalSummary personalSummary);

    IPage<PersonalSummary> selectPersonal(Page page, @Param("personalSummary") PersonalSummary personalSummary);

    IPage<PersonalSummary> selectCollect(Page page,@Param("personalSummary") PersonalSummary personalSummary);
}
