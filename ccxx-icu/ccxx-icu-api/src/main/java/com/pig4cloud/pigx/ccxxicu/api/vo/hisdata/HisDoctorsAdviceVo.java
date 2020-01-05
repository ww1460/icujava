package com.pig4cloud.pigx.ccxxicu.api.vo.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HisDoctorsAdviceVo extends HisDoctorsAdvice {


	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 医嘱内容
	 */
	private List<HisDoctorsAdviceProject> projectList;

	/**
	 * 间隔值 是 多长 时间
	 */
	private String dataValue;

	/**
	 * 当前登录护士id
	 */
	private String loginNurse;
	/**
	 * 科室id
	 */
	private String deptId;

}
