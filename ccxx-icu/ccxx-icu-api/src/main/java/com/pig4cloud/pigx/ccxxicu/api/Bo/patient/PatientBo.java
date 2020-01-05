package com.pig4cloud.pigx.ccxxicu.api.Bo.patient;

import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "患者查询数据")
public class PatientBo extends Patient {

	/**
	 * 入科开始时间  	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	 */

	@ApiModelProperty(value = "入科开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime entryStartTime;
	/**
	 * 入科结束时间
	 */

	@ApiModelProperty(value = "入科结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime entryEndTime;
	/**
	 * 出科开始时间
	 */

	@ApiModelProperty(value = "入科开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dischargeStartTime;
	/**
	 * 出科结束时间
	 */

	@ApiModelProperty(value = "入科结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dischargeEndTime;


	@ApiModelProperty(value = "当前登录的护士id")
	private String registerNurseId;


}
