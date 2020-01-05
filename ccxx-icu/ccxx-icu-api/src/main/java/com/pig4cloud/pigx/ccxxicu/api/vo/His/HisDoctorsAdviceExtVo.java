package com.pig4cloud.pigx.ccxxicu.api.vo.His;

import lombok.Data;

import java.util.List;

/**
 * 医嘱执行记录
 */
@Data
public class HisDoctorsAdviceExtVo {


	/**
	 * 正式 【zs】测试【cs】
	 */
	private String Db;
	/**
	 * 患者数据
	 */
	private String hisPatientId;
	/**
	 * 关联医嘱id【主医嘱id】
	 */
	private List<String> hisDoctorsAdviceIdList;





}
