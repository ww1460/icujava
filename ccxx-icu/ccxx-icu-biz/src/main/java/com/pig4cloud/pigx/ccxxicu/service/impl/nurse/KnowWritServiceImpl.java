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


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.KnowWrit;
import com.pig4cloud.pigx.ccxxicu.common.utils.ChineseToPinYin;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.KnowWritMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.KnowWritService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知情文书表
 *
 * @author pigx code generator
 * @date 2019-08-17 14:55:05
 */
@Service
public class KnowWritServiceImpl extends ServiceImpl<KnowWritMapper, KnowWrit> implements KnowWritService {
	/**
	 * 新增知情文书
	 * @param knowWrit
	 * @return
	 */
	@Override
	public boolean add(KnowWrit knowWrit) {

		String projectCode = knowWrit.getWritCode();

		boolean msg = true;
		//当code为空时 将name值的拼音转换为code
		if (StringUtils.isEmpty(projectCode)) {

			String s = ChineseToPinYin.HanZiToPinYin(knowWrit.getWritTitle(), false);

			projectCode = s;

		}

		//对code值验重 不重复直接结束   重复在后面追加一个随机的大写字母
		while (msg) {

			Integer integer = this.selectByCode(projectCode).size();
			if (integer.equals(0)) {

				break;

			} else {

				projectCode = projectCode + "_" + (char) (int) (Math.random() * 26 + 65);

			}
		}

		knowWrit.setWritCode(projectCode);

		return this.save(knowWrit);
	}

	/**
	 * 通过文书的code值 查询文书内容
	 * @param writCode
	 * @return
	 */
	@Override
	public List<KnowWrit> selectByCode(String writCode) {
		return baseMapper.selectList(Wrappers.<KnowWrit>query().lambda()
				.eq(KnowWrit::getDelFlag, 0)
				.eq(KnowWrit::getWritCode,writCode)
		);

	}
}
