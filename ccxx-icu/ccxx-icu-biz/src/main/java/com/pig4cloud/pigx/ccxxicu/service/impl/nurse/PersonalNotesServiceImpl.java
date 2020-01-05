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
package com.pig4cloud.pigx.ccxxicu.service.impl.nurse;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.PersonalNotes;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.PersonalNotesMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.PersonalNotesService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 护士个人笔记
 *
 * @author pigx code generator
 * @date 2019-08-05 09:36:19
 */
@Service
public class PersonalNotesServiceImpl extends ServiceImpl<PersonalNotesMapper, PersonalNotes> implements PersonalNotesService {

	/**
	 * 查询该护士某月的笔记数据
	 * @param dateTime
	 * @return
	 */
	@Override
	public List<PersonalNotes> selectByDate(LocalDateTime dateTime) {


		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return null;

		}

		Integer id = user.getId();

		List<PersonalNotes> personalNotes = baseMapper.selectByDate(dateTime.toString(),id+"");

		return personalNotes;
	}

	/**
	 * 查询护士某天的数据
	 * @param personalNotes
	 * @return
	 */
	@Override
	public List<PersonalNotes> getByDay(PersonalNotes personalNotes) {

		PigxUser user = SecurityUtils.getUser();

		if (user == null) {

			return null;

		}

		Integer id = user.getId();

		String s = personalNotes.getCreateTime().toString();

		String substring = s.substring(0, 10);


		return baseMapper.getByDay(substring,id+"");
	}
}
