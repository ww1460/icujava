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
import com.pig4cloud.pigx.ccxxicu.api.Bo.knowledgeBase.NurseCollectBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.NurseCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 护士个人总结收藏关联表
 *
 * @author pigx code generator
 * @date 2019-11-01 20:38:14
 */
public interface NurseCollectMapper extends BaseMapper<NurseCollect> {

    List<NurseCollect> queryCollect(NurseCollect nurseCollect);

    NurseCollect selectColl(@Param("nurseCollectBo")NurseCollectBo nurseCollectBo);
}
