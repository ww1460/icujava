package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@ApiModel(value = "出入量记录分页查询条件")
public class IntakeOutputShow {


	/**
	 * 出入量 自增id
	 */
	@ApiModelProperty(value="出入量 自增id")
	private Integer id;
	/**
	 * 生成id
	 */
	@ApiModelProperty(value="生成id")
	private String intakeOutputId;
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
	 * 产生出入量的原因备注
	 */
	@ApiModelProperty(value="产生出入量的原因备注")
	private String causeRemark;

	/**
	 * 出入量标识   0是出量 1是入量
	 */
	@ApiModelProperty(value="出入量标识   0是出量 1是入量")
	private Integer intakeOutputType;
	/**
	 * 出入量的值
	 */
	@ApiModelProperty(value="出入量的值")
	private Integer intakeOutputValue;
	/**
	 * 当前的出入量的平衡量
	 */
	@ApiModelProperty(value="当前的出入量的平衡量")
	private Integer equilibriumAmount;


	/**
	 * 当前小时的总计
	 */
	@ApiModelProperty(value="当前小时的总计")
	private Integer countNum;

	/**
	 * 出入量描述
	 */
	@ApiModelProperty(value="出入量描述")
	private String valueDescription;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;
	/**
	 * 项目简称
	 */
	@ApiModelProperty(value="项目简称")
	private String projectCode;

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

	private Integer hour;

	private String signature;


	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remarks;



}
