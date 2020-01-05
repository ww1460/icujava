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
package com.pig4cloud.pigx.ccxxicu.service.impl.hisdata;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DeptCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisPatient;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisPatientMapper;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.DeptCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 患者his传输表
 *
 * @author pigx code generator
 * @date 2019-08-27 15:58:29
 */
@Service
public class HisPatientServiceImpl extends ServiceImpl<HisPatientMapper, HisPatient> implements HisPatientService {

	@Autowired
	private HisPatientMapper hisPatientMapper;
	@Autowired
	private DeptCorrelationService deptCorrelationService;

	/***
	 * 接收his的数据
	 * @param
	 * @return
	 */
	@Override
	public Boolean hisPatientAdd(HisPatient hisPatient) {


	/*  通过his中的唯一患者id 查询一下 当前患者是否已经有*/
		HisPatient patient = this.selectHisPatientId(hisPatient.getHisPatientId());

		if (hisPatient.getHisPatientId() ==null){
			return false;
		}else{
			/*将传来的患者数据科室修改为icu的科室  admissionDept   */
			DeptCorrelation deptCorrelation = deptCorrelationService.hisDeptId(hisPatient.getAdmissionDept());
			if (deptCorrelation==null){
				throw new ApiException(ResponseCode.DEPT_FALL.getCode(),ResponseCode.DEPT_FALL.getMessage());
			}else{
				if (StringUtils.isNotEmpty(hisPatient.getGender())){//当患者的性别不为空时
					/**
					 * 将患者的性别编号修改成我们的样式
					 * 【his  1男 2女】
					 * 【ICU  1男 0女】
					 * 判断his传来性别为2时修改成0
					 */
					if (hisPatient.getGender().equals("2")){
						hisPatient.setGender("0");
					}
				}
				hisPatient.setAdmissionDept(deptCorrelation.getDeptId());
				if (patient==null){ //当查询不到当前患者时新增
					try {
						boolean save = this.save(hisPatient);
						System.out.println("新增一次------------------------");
						if (!save){
							return false;
						}else{
							System.out.println("成功------------");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					try {
						HisPatient patients = hisPatientMapper.hisPatientIdSelect(hisPatient.getHisPatientId());
						hisPatient.setId(patients.getId());
						boolean b = this.updateById(hisPatient);
						System.out.println("修改一次------------------------");
						if (!b){
							return false;
						}else {
							System.out.println("成功------------");
							return true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		return true;
		}

	}

	/**
	 * 通过his中的患者唯一id查询当前患者是否有
	 * @param id
	 * @return
	 */
	@Override
	public HisPatient selectHisPatientId(String id) {
		return hisPatientMapper.selectHisPatientId(id);
	}

	@Override
	public List<HisPatient> selectAll() {
		return hisPatientMapper.selectAll();
	}

	/**
	 * 利用his中的患者id修改患者出科信息
	 * @param hisPatient
	 * @return
	 */
	@Override
	public Boolean hisPatientId(HisPatient hisPatient) {
		/**
		 *  出院、自动出院
		 * 	 *
		 * 	 * 死亡
		 * 	 *
		 * 	 * 处次之外都是转科
		 */





		Integer integer = hisPatientMapper.hisPatientId(hisPatient);
		if (integer==1){
			return true;
		}else {
		return false;
		}
	}

	/**
	 *  his患者id查询数据
	 * @param hisPatientId
	 * @return
	 */
	@Override
	public HisPatient hisPatientIdSelect(String hisPatientId) {
		return hisPatientMapper.hisPatientIdSelect(hisPatientId);
	}

	/**
	 * 分页查询数据
	 * @param hisPatient
	 * @return
	 */
	@Override
	public IPage selectPaging(Page page,HisPatient hisPatient) {
		return hisPatientMapper.selectPaging(page,hisPatient);
	}
}
