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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.pig4cloud.pigx.ccxxicu.api.Bo.knowledgeBase.NurseCollectBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.NurseCollect;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.knowledgeBase.NurseCollectMapper;
import com.pig4cloud.pigx.ccxxicu.service.knowledgeBase.NurseCollectService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * 护士个人总结收藏关联表
 *
 * @author pigx code generator
 * @date 2019-11-01 20:38:14
 */
@Service
public class NurseCollectServiceImpl extends ServiceImpl<NurseCollectMapper, NurseCollect> implements NurseCollectService {

    @Autowired
    private NurseCollectMapper nurseCollectMapper;
    @Override
    public boolean add(NurseCollect nurseCollect) {
        PigxUser user = SecurityUtils.getUser();
        if (user == null) {
            return false;
        }
        nurseCollect.setNurseId(user.getId()+"");
        nurseCollect.setPersonalSummaryId(SnowFlake.getId()+"");
        boolean b = this.save(nurseCollect);
        if (b==true){
            return false;
        }
        return true;
    }

    @Override
    public List<NurseCollect> queryCollect(NurseCollect nurseCollect) {
        return nurseCollectMapper.queryCollect(nurseCollect);
    }

    @Override
    public NurseCollect selectColl(NurseCollectBo nurseCollectBo) {
        return nurseCollectMapper.selectColl(nurseCollectBo);
    }
}
