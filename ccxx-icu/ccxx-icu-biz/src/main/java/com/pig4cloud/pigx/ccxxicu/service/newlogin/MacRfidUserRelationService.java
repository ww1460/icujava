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

package com.pig4cloud.pigx.ccxxicu.service.newlogin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.newlogin.NurseNamesLoginBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.newlogin.MacRfidUserRelation;

/**
 * mac地址与护士RFID的关系
 *
 * @author 崔洪振
 * @date 2019-09-06 15:03:28
 */
public interface MacRfidUserRelationService extends IService<MacRfidUserRelation> {

	String macAddressIsNull(String username, String password);


	NurseNamesLoginBo macAddressIsNotNull(String ipAddress);

	int saveMacRfidUserRelation(MacRfidUserRelation macRfidUserRelation);

}
