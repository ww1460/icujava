package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "出入量记录分页查询条件")
public class IntakeOutputVo {

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
	 * 出入量描述
	 */
	@ApiModelProperty(value="出入量描述")
	private String valueDescription;
	/**
	 * 产生出入量的原因备注
	 */
	@ApiModelProperty(value="产生出入量的原因备注")
	private String causeRemark;
	/**
	 * 数据产生关联的id
	 */
	@ApiModelProperty(value="数据产生关联的id")
	private String sourceId;
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
