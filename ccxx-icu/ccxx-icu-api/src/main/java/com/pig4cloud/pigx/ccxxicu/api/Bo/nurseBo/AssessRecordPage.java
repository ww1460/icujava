package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "评估记录展示条件")
public class AssessRecordPage {

	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;


	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;

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
	/**
	 * 分数区间的最低分
	 */
	@ApiModelProperty(value="分数区间的最低分")
	private Integer minScore;
	/**
	 * 分数区间的最高分
	 */
	@ApiModelProperty(value="分数区间的最高分")
	private Integer maxScore;

}
