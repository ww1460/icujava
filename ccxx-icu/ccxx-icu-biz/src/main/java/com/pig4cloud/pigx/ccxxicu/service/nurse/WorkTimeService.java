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
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.WorkTimeBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.WorkTime;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.WorkTimeVo;

import java.util.List;

/**
 * 护士在线时间
 *
 * @author pigx code generator
 * @date 2019-11-06 17:02:01
 */
public interface WorkTimeService extends IService<WorkTime> {

	List<WorkTimeVo> groupByBed(WorkTimeBo workTimeBo);


	boolean add(WorkTime workTime);

	boolean addEndTime(WorkTime workTime);

	WorkTime getNewRecord(WorkTime workTime);


}
