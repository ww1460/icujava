package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "出入量记录分页查询条件")
public class IntakeOutPutBo {


	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 护士id
	 */
	@ApiModelProperty(value="护士id")
	private String nurseId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;
	/**
	 * 患者姓名
	 */
	@ApiModelProperty(value="患者姓名")
	private String patientName;

	/**
	 * 数据范围
	 */
	@ApiModelProperty(value="数据范围 0关联患者的数据  1 为创建人的数据")
	private Integer range;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="开始时间")
	private LocalDateTime startTime;
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="结束时间")
	private LocalDateTime endTime;


}
