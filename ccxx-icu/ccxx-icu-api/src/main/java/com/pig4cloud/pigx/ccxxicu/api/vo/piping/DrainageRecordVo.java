package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import com.pig4cloud.pigx.ccxxicu.api.entity.piping.DrainageRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DrainageRecordVo extends DrainageRecord {

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 护士姓名
	 */
	private String nurseName;

	/**
	 * 管道名称
	 */
	private String pipingName;

	/**
	 * 引流液名称
	 */
	private String drainageName;

}
