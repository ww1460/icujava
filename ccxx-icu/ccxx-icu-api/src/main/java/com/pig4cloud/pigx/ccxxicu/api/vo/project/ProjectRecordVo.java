package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "项目记录分页查询条件")
public class ProjectRecordVo {


	/**
	 * 项目数据记录  自增id
	 */
	@ApiModelProperty(value="项目数据记录  自增id")
	private Integer id;
	/**
	 * 生成id
	 */
	@ApiModelProperty(value="生成id")
	private String projectRecordId;
	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;
	/**
	 * 项目的详细数据
	 */
	@ApiModelProperty(value="项目的详细数据")
	private String projectSpecificRecord;
	/**
	 * 项目的数值数据
	 */
	@ApiModelProperty(value="项目的数值数据")
	private String projectValue;
	/**
	 * 该记录的来源
	 */
	@ApiModelProperty(value="该记录的来源")
	private String source;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remarks;
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
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectCode;
	/**
	 * 患者名
	 */
	@ApiModelProperty(value="患者名")
	private String patientName;
	/**
	 * 患者床位
	 */
	@ApiModelProperty(value="患者床位")
	private String bedCode;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private LocalDateTime createTime;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value="创建人")
	private String createName;


}
