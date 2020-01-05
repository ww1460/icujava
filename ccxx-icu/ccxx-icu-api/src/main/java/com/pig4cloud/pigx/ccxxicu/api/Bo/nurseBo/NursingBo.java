package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: pigx
 * @description: 用于报表表单的查询
 * @author: yyj
 * @create: 2019-09-11 15:42
 **/
@Data
@ApiModel(value = "护理记录分页查询条件")
public class NursingBo {

	/**
	 * 患者id
	 */
	@ApiModelProperty(value = "患者id")
	private String patientId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private LocalDateTime endTime;

	/**
	 * 报表标识
	 */
	@ApiModelProperty(value = "报表标识")
	private Integer nursingReportFlag;
	/**
	 * 项目id
	 */
	@ApiModelProperty(value = "项目id")
	private String projectId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value = "科室id")
	private String deptId;


}
