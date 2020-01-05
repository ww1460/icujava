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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.dto.UserDTO;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.feign.RemoteUserService;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.UserBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseWatch;
import com.pig4cloud.pigx.ccxxicu.common.utils.RegularMatchUtils;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NurseMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 护士表
 *
 * @author pigx code generator
 * @date 2019-08-02 10:02:36
 */
@Service
public class NurseServiceImpl extends ServiceImpl<NurseMapper, Nurse> implements NurseService {


	@Value("${nurse.roleId}")
	private Integer nurseRoleId;//护士权限id

	@Value("${matron.roleId}")
	private Integer matronRoleId;//护士长权限id

	@Resource
	private NurseMapper nurseMapper;

	@Resource
	private RemoteUserService remoteUserService;

	/**
	 * 护士长添加护士
	 * @param userBo
	 * @return
	 */
	@Override
	public int add(UserBo userBo) {

		PigxUser user = SecurityUtils.getUser();

		List<Integer> role = new ArrayList<>();

		Integer roleFlag = userBo.getRoleFlag();
		if (StringUtils.isEmpty(userBo.getDeptId())) {

			userBo.setDeptId(user.getDeptId()+"");

		}
		//判断新增护士
		if (roleFlag.equals(0)) {

			role.add(nurseRoleId);

		}
		//判断新增的为护士长
		if (roleFlag.equals(1)) {

			role.add(matronRoleId);
		}


		userBo.setRole(role);

		UserDTO userDTO = new UserDTO();
		//创建一个userDto类  去添加用户和角色关联
		userDTO.setRole(userBo.getRole());
		userDTO.setLockFlag(userBo.getLockFlag());
		userDTO.setDelFlag(0 + "");
		userDTO.setUsername(userBo.getUsername());
		userDTO.setPhone(userBo.getPhone());
		userDTO.setPassword(userBo.getPassword());
		userDTO.setNewpassword1(userBo.getNewpassword1());
		userDTO.setDeptId(RegularMatchUtils.getInteger(userBo.getDeptId()));
		userDTO.setAvatar(userBo.getAvatar());
		userDTO.setSalt(userBo.getSalt());
		userDTO.setLockFlag(0 + "");
		userDTO.setCreateTime(LocalDateTime.now());
		//返回用户主键
		SysUser data = remoteUserService.save(userDTO).getData();

		Integer userId = data.getUserId();

		Nurse nurse = new Nurse();
		//添加到护士表
		nurse.setGender(userBo.getGender());
		nurse.setCreateTime(LocalDateTime.now());
		nurse.setNurseRfid(userBo.getNurseRfid());
		nurse.setRealName(userBo.getRealName());
		nurse.setDeptId(userBo.getDeptId());
		nurse.setUserId(userId + "");
		nurse.setNurseGrade(userBo.getNurseGrade());
		nurse.setDelFlag(0);
		nurse.setNurseId(userId + "");
		nurse.setCreateUserId(user.getId()+"");
		nurse.setRoleFlag(userBo.getRoleFlag());
		nurse.setSignature(userBo.getSignature());
		nurse.setMotto(userBo.getMotto());

		return baseMapper.insert(nurse);
	}

	/**
	 * 根据用户id 查询护士
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public NurseInfo getNurseInfo(Integer userId) {
		//获取该护士在用户表的信息
		R<UserVO> userVOR = remoteUserService.selectUser(userId);

		NurseInfo nurseInfo = new NurseInfo();

		UserVO use = userVOR.getData();

		BeanUtil.copyProperties(use, nurseInfo);
		//获取该护士在护士表的信息
		Nurse nurse = baseMapper.selectByUserId(userId+"");

		BeanUtil.copyProperties(nurse, nurseInfo);

		return nurseInfo;
	}

	@Override
	public Nurse getNurseInfoByRfidId(String rfidId) {
		return nurseMapper.selectByUserRfidId(rfidId);
	}

	/**
	 * 修改护士信息
	 * @param nurseInfo
	 * @return
	 */
	@Override
	public int update(NurseInfo nurseInfo) {

		UserDTO userDTO = new UserDTO();
		//封装对象  修改用户表的内容
		BeanUtil.copyProperties(nurseInfo,userDTO);

		Integer roleFlag = nurseInfo.getRoleFlag();

		List<Integer> role = new ArrayList<>();

		//判断新增护士
		if (roleFlag.equals(0)) {

			role.add(nurseRoleId);

		}
		//判断新增的为护士长
		if (roleFlag.equals(1)) {

			role.add(matronRoleId);
		}

		userDTO.setRole(role);

		remoteUserService.update(userDTO);

		Nurse nurse = new Nurse();
		//修改护士表内容
		BeanUtil.copyProperties(nurseInfo,nurse);

	//	nurse.setId(nurseInfo.getId());

		return baseMapper.updateById(nurse);
	}

	/**
	 * 删除护士及用户表 角色关联
	 * @param nurseId
	 * @return
	 */
	@Override
	public int deleteByNurseId(String nurseId) {


		//删除用户表 和 角色关联
		remoteUserService.userDel(Integer.parseInt(nurseId));

		//修改护士表的删除标识

		Nurse nurse = baseMapper.selectByUserId(nurseId);

		PigxUser user = SecurityUtils.getUser();

		nurse.setDelFlag(1);
		nurse.setDelUserId(user.getId()+"");
		nurse.setDelTime(LocalDateTime.now());

		return baseMapper.updateById(nurse);
	}

	/**
	 * 根据科室查询非护士长 护士
	 * @param deptId
	 * @return
	 */
	@Override
	public List<Nurse> selectByDept(String deptId) {
		return baseMapper.selectByDept(deptId);
	}
	/**
	 * 根据科室查询所有护士
	 * @param deptId
	 * @return
	 */
	@Override
	public List<Nurse> selectAllNurse(String deptId) {
		return baseMapper.selectAllNurse(deptId);
	}
	/**
	 * 根据用户id 查询护士信息
	 * @param userId
	 * @return
	 */
	@Override
	public Nurse selectByUserId(String userId) {
		return baseMapper.selectByUserId(userId);
	}

	/**
	 * 根据护士等级查询护士
	 *
	 * @param nurseGrade
	 * @return
	 */
	@Override
	public List<Nurse> selectByGrade(Integer nurseGrade) {
		return baseMapper.selectByGrade(nurseGrade);
	}

	/**
	 * 查询患者的非责任护士的信息
	 * @param patientId
	 * @return
	 */
	@Override
	public List<Nurse> getPatientNurse(String patientId) {
		return baseMapper.getPatientNurse(patientId);
	}

	/**
	 * 查询护士的科室【去重后】
	 * @return
	 */
	@Override
	public List<Integer> nurseDept() {
		return nurseMapper.nurseDept();
	}

	/**
	 * 查询科室护士的详细信息
	 * @param deptId
	 * @return
	 */
	@Override
	public List<NurseWatch> AllNurse(String deptId) {

		List<Nurse> nurses = this.selectAllNurse(deptId);

		List<NurseWatch> result = new ArrayList<>();
		Vector<Thread> threadVector = new Vector<>();
		nurses.forEach(ar->{

			Thread thread = new Thread(){

				@Override
				public void run() {

					//获取该护士在用户表的信息
					R<UserVO> userVOR = remoteUserService.selectUser(Integer.valueOf(ar.getUserId()));

					NurseWatch nurseWatch = new NurseWatch();

					nurseWatch.setNurseId(ar.getNurseId());
					nurseWatch.setNurseRfid(ar.getNurseRfid());
					nurseWatch.setRealName(ar.getRealName());
					nurseWatch.setUserName(userVOR.getData().getUsername());
					result.add(nurseWatch);
					super.run();
				}
			};
			thread.start();
			threadVector.add(thread);

		});

		threadVector.forEach(ar->{

			try {
				ar.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});


		return result;
	}
}
