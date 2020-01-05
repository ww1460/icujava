package com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HisDoctorsAdviceExtBo {
	/**
	 * 正式 【zs】测试【cs】
	 */
	private String Db;
	/**
	 * 患者数据
	 */
	private String hisPatientId;

	private String hisFZYExecInfoID;

	private String hisDoctorsAdviceId;

	private String hisDoctorsAdviceProjectId;

	private String executeNurse;

	private LocalDateTime executeTime;



}
