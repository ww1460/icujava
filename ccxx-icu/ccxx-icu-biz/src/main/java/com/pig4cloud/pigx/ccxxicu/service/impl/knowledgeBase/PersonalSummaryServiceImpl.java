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
package com.pig4cloud.pigx.ccxxicu.service.impl.knowledgeBase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.NurseCollect;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.PersonalSummary;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.mapper.knowledgeBase.NurseCollectMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.knowledgeBase.PersonalSummaryMapper;
import com.pig4cloud.pigx.ccxxicu.service.knowledgeBase.NurseCollectService;
import com.pig4cloud.pigx.ccxxicu.service.knowledgeBase.PersonalSummaryService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 个人总结
 *
 * @author pigx code generator
 * @date 2019-11-01 19:55:59
 */
@Service
public class PersonalSummaryServiceImpl extends ServiceImpl<PersonalSummaryMapper, PersonalSummary> implements PersonalSummaryService {


    @Autowired
    private NurseCollectService nurseCollectService;

    @Autowired
    private PersonalSummaryMapper personalSummaryMapper;




    @Override
    public Boolean delete(Integer id) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return false;
        }
        /**
         * 通过id得到PersonalSummary对象，设置del_flag字段值为1
         */
        PersonalSummary byId = this.getById(id);
        byId.setDelFlag(1);
        byId.setDelUserId(user.getId()+"");
        byId.setDelTime(LocalDateTime.now());
        boolean b = this.updateById(byId);
        if (!b){
          return false;
        }
        List<NurseCollect> list = nurseCollectService.list(Wrappers.<NurseCollect>query().lambda()
                .eq(NurseCollect::getPersonalSummaryId, byId.getPersonalSummaryId())
        );
        if (CollectionUtils.isEmpty(list)){
            return true;
        }

        list.forEach(listPer->{nurseCollectService.removeById(listPer.getId());});

        return true;
    }

    @Override
    public IPage<PersonalSummary> selectCollect(Page page, PersonalSummary personalSummary) {
        return personalSummaryMapper.selectCollect(page,personalSummary);
    }


    @Override
    public PersonalSummary selectId(String personalSummaryId) {
        return personalSummaryMapper.selectId(personalSummaryId);
    }

    @Override
    public IPage<PersonalSummary> selectAll(Page page, PersonalSummary personalSummary) {
        return personalSummaryMapper.selectAll(page,personalSummary);
    }

    @Override
    public IPage<PersonalSummary> selectPersonal(Page page, PersonalSummary personalSummary) {
        return personalSummaryMapper.selectPersonal(page,personalSummary);
    }
}
