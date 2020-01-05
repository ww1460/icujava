package com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用于his传输
 */
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DoctorsAdviceBo {

	/**
	 * 医嘱
	 */
	private HisDoctorsAdvice hisDoctorsAdvice;
	/**
	 * 医嘱项目
	 */
	private List<HisDoctorsAdviceProject> projectsList;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;

	/**
	 * 患者id
	 */
	private String patientId;



}
