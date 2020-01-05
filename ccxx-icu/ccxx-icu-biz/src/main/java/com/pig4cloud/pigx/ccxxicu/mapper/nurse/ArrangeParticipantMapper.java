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

package com.pig4cloud.pigx.ccxxicu.mapper.nurse;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeParticipant;

import java.util.List;

/**
 * 参与排班人员表
 *
 * @author pigx code generator
 * @date 2019-08-12 20:48:58
 */
public interface ArrangeParticipantMapper extends BaseMapper<ArrangeParticipant> {
	/**
	 * 获取该科室参与排班的护士
	 * @param arrangeParticipant
	 * @return
	 */
	List<ArrangeParticipant> getNurseByShift(ArrangeParticipant arrangeParticipant);

}
