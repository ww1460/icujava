package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Nursing;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NursingVo extends Nursing {


	/**
	 * 管道名称
	 */
	private String pipingName;

	/**
	 * 插管护士id
	 */
	private String cannulaNurseName;

	/**
	 * 创建护士
	 */
	private String nurseName;

	/**
	 * 患者id
	 */
	private String patientName;

}
