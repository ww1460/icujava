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
package com.pig4cloud.pigx.ccxxicu.service.impl.newlogin;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pig4cloud.pigx.admin.api.feign.RemoteUserService;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.ccxxicu.api.Bo.newlogin.NurseNamesLoginBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.newlogin.MacRfidUserRelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.mapper.newlogin.MacRfidUserRelationMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NurseMapper;
import com.pig4cloud.pigx.ccxxicu.service.newlogin.MacRfidUserRelationService;
import com.pig4cloud.pigx.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * mac地址与护士RFID的关系
 *
 * @author 崔洪振
 * @date 2019-09-06 15:03:28
 */
@Slf4j
@Service
public class MacRfidUserRelationServiceImpl extends ServiceImpl<MacRfidUserRelationMapper, MacRfidUserRelation> implements MacRfidUserRelationService {

	@Resource
	private MacRfidUserRelationMapper macRfidUserRelationMapper;

	@Resource
	private NurseMapper nurseMapper;

	@Resource
	private RemoteUserService remoteUserService;


	/**
	 * username和password不为空，mac地址为空，需要判断用户名和密码的校验逻辑，原登录逻辑（在用户登录表中找信息）
	 * {
	 * "ismac":false,
	 * "data":"登录后的地址"
	 * }
	 *
	 * @return
	 */
	@Override
	public String macAddressIsNull(String username, String password1) {
		//走老逻辑-http://localhost:8080/auth/oauth/token
		return null;
	}

	/**
	 * username和password为空，mac地址不为空，免登录流程，查Mac表，返回一个json字符串
	 * {
	 * "ismac":true,
	 * "data":["小李","小张","小王"]
	 * }
	 *
	 * @return
	 */
	@Override
	public NurseNamesLoginBo macAddressIsNotNull(String ipAddress) {
		String string = "";
		NurseNamesLoginBo nurseNamesLoginBo = new NurseNamesLoginBo();
		if (StringUtils.isEmpty(ipAddress)) {
			return nurseNamesLoginBo;//还是显示登录页面，提示登录失败
		} else {//不为空
			List<String> userNameList = checkUserContainsInUserTable(getUserIDByRfid(getRfidInfoByMacAddress(ipAddress)));

			if (!CollectionUtils.isEmpty(userNameList)) {
				nurseNamesLoginBo.setIsmac(true);
				nurseNamesLoginBo.setData(userNameList);
			} else {//查到用户为空，不能免登录
				nurseNamesLoginBo.setIsmac(false);
				nurseNamesLoginBo.setData(null);//此时前端跳回登录页面
			}
		}
		System.out.println("mac info and login:{}" + JSON.toJSONString(nurseNamesLoginBo));
		log.info("mac info and login:{}", JSON.toJSONString(nurseNamesLoginBo));
		return nurseNamesLoginBo;
	}

	/**
	 * 讲记录保存到数据表中
	 *
	 * @param macRfidUserRelation
	 * @return
	 */
	@Override
	public int saveMacRfidUserRelation(MacRfidUserRelation macRfidUserRelation) {
		return macRfidUserRelationMapper.insert(macRfidUserRelation);
	}

	/**
	 * 根据Mac地址查找对应的RFID
	 *
	 * @param ipAddress
	 * @return
	 */
	public List<String> getRfidInfoByMacAddress(String ipAddress) {
		List<String> result = Lists.newArrayList();
		try {
			List<MacRfidUserRelation> ipInfoList = macRfidUserRelationMapper.selectMacRfidUserRelationList();
			for (MacRfidUserRelation item : ipInfoList) {
				result.add(item.getRfidId());
			}
		} catch (Exception e) {
			log.error("get rfid information by ipaddress exception:{}", e);
		}
		System.out.println("get rfid information by ipaddress:" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 根据RFID查找用户的userID
	 *
	 * @param rfidList
	 */
	public List<String> getUserIDByRfid(List<String> rfidList) {
		List<String> result = Lists.newArrayList();

		if (CollectionUtils.isEmpty(rfidList)) {
			return result;
		}

		try {
			List<Nurse> nurseList = nurseMapper.selectNurseUserIdByRfid(rfidList);
			log.info("护士列表信息:"+JSON.toJSONString(nurseList));
			for (Nurse nurse : nurseList) {
				result.add(nurse.getUserId());
			}
		} catch (Exception e) {
			log.error("get userid by rfid exception:{}", e);
		}
		log.info("根据RFID查找用户的userID:"+JSON.toJSONString(result));
		return result;
	}

	/**
	 * 校验用户userId是否在用户登录表SysUser
	 *
	 * @param userids
	 * @return
	 */
	public List<String> checkUserContainsInUserTable(List<String> userids) {
		List<String> userNameList = Lists.newArrayList();
		try {
			for (String userid : userids) {
				//获取该护士在用户表的信息
				R<UserVO> userVOR = remoteUserService.selectUser(Integer.valueOf(userid));
				log.info("获取的用户对象信息为:"+JSON.toJSONString(userVOR));
				userNameList.add(userVOR.getData().getUsername());
			}
		} catch (Exception e) {
			log.error("check userid contains in user table:{}", e);
		}
		return userNameList;
	}

	public static void main(String[] args) {
		List<String> nurseNameList = Lists.newArrayList();
		nurseNameList.add("xiaoli");
		nurseNameList.add("xiaozhang");
		String ss = JSON.toJSONString(nurseNameList);
		System.out.println(ss);
	}
}
