package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@ApiModel(value = "护理记录修改展示")
public class NursingProjectVo {

	/**
	 * 护理记录id
	 */
	@ApiModelProperty(value = "护理记录id")
	private String  id;
	/**
	 * 护理记录生成id
	 */
	@ApiModelProperty(value = "护理记录生成id")
	private String  projectRecordId;
	/**
	 * 项目id
	 */
	@ApiModelProperty(value = "项目id")
	private String  projectId;
	/**
	 * 项目详细记录
	 */
	@ApiModelProperty(value = "项目详细记录")
	private String  projectSpecificRecord;
	/**
	 *项目数值记录
	 */
	@ApiModelProperty(value = "项目数值记录")
	private String  projectValue;
	/**
	 *结果来源
	 */
	@ApiModelProperty(value = "结果来源")
	private String  source;
	/**
	 *数据关联的id
	 */
	@ApiModelProperty(value = "数据关联的id")
	private String  sourceId;
	/**
	 *患者id
	 */
	@ApiModelProperty(value = "患者id")
	private String  patientId;
	/**
	 *备注
	 */
	@ApiModelProperty(value = "备注")
	private String  remarks;
	/**
	 *科室id
	 */
	@ApiModelProperty(value = "科室id")
	private String  deptId;
	/**
	 *删除标识
	 */
	@ApiModelProperty(value = "删除标识")
	private String  delFlag;
	/**
	 *删除时间
	 */
	@ApiModelProperty(value = "删除时间")
	private LocalDateTime  delTime;
	/**
	 *删除人
	 */
	@ApiModelProperty(value = "删除人")
	private String  delUserId;
	/**
	 *创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String  createUserId;
	/**
	 *创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 *修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime  updateTime;
	/**
	 *修改人
	 */
	@ApiModelProperty(value = "修改人")
	private String  updateUserId;
	/**
	 *项目名
	 */
	@ApiModelProperty(value = "项目名")
	private String  projectName;
	/**
	 *项目类型
	 */
	@ApiModelProperty(value = "项目类型")
	private Integer  projectType;
	/**
	 *项目是否预警
	 */
	@ApiModelProperty(value = "项目是否预警")
	private String  projectWarmFlag;




}
