package com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HisDoctorsAdviceBo extends HisDoctorsAdvice {

	/**
	 * 医嘱内容
	 */
	private List<HisDoctorsAdviceProject> projectList;


}
