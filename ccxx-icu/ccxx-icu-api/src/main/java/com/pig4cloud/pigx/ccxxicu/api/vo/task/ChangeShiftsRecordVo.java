package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeShiftsRecordVo extends ChangeShiftsRecord {

	/**
	 * 患者姓名
	 */
	private String patientName;
	/**
	 * 患者年龄
	 */
	private String patientAge;
	/**
	 * 患者性别
	 */
	private String patientGenter;
	/**
	 * 患者入科病情
	 */
	private String patientDiagnosis;
	/**
	 * 床位
	 */
	private String bedName;

	/**
	 * 患者入科时间
	 */
	private LocalDateTime entranceTime;






}
