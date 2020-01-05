package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import com.pig4cloud.pigx.ccxxicu.api.entity.piping.UseRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UseRecordVo extends UseRecord {

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 设备名称
	 */
	private String pipingName;

	/**
	 * 护士姓名
	 */
	private String nurseName;
	/**
	 * 引流液名称
	 */
	private String drainageName;

	/**
	 * 开始时间用于后台查询一定时间内的断开的管道信息
	 */
	private LocalDateTime sTime;

	/**
	 * 结束时间用于后台查询一定时间内的断开的管道信息
	 */
	private LocalDateTime eTime;

	/**
	 * 管道评估分数
	 */
	private String assessmentScore;

}
