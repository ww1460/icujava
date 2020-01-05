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
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.PersonalNotes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 护士个人笔记
 *
 * @author pigx code generator
 * @date 2019-08-05 09:36:19
 */
public interface PersonalNotesService extends IService<PersonalNotes> {
	/**
	 * 查询该护士某月的笔记数据
	 * @param dateTime
	 * @return
	 */
	List<PersonalNotes> selectByDate(LocalDateTime dateTime);

	/**
	 * 查询护士某天的数据
	 * @param personalNotes
	 * @return
	 */
	List<PersonalNotes> getByDay(PersonalNotes personalNotes);

}
