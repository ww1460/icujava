package com.pig4cloud.pigx.ccxxicu.api.Bo.datav;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 患者床号，患者名字，护士名字，入院时间（time_of_admission），预警时间，预警原因
 */
@Data
@ApiModel(value = "综合数据大屏滚动图数据对象")
public class DataVEarlyWarning {

	private String bedNumber;

	private String patientName;

	private String nurseName;

	private LocalDateTime admissionTime;

	private LocalDateTime warningTime;

	private String warningReason;
}
