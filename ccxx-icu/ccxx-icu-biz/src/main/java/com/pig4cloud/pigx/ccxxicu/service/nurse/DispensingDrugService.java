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
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 配药
 *
 * @author pigx code generator
 * @date 2019-09-05 15:53:09
 */
public interface DispensingDrugService extends IService<DispensingDrug> {


	/**
	 * 医嘱配药页面的展示
	 *
	 * @return
	 */
	Map<String, Object> dispensingDrugPage(String doctorsAdviceId);

	/**
	 * 通过药品rfid获取用药信息
	 *
	 * @return
	 */
	List<DispensingDrug> getDispensingDrugByRfidId(String rfidId);

	/**
	 * 通过病人id获取用药信息
	 *
	 * @return
	 */
	int updateDispensingDrugState(Integer id);

}
