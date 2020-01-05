package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "调用护理模板")
public class NursingTemplateUseVo {


	/**
	 * 模板标题
	 */
	@ApiModelProperty(value="模板标题")
	private String templateTitle;
	/**
	 * 模板编号
	 */
	@ApiModelProperty(value="模板编号")
	private String templateCode;
	/**
	 * 是否为项目类的模板  0是 1否
	 */
	@ApiModelProperty(value="是否为项目类的模板  0是 1否")
	private Integer isProjectFlag;

	/**
	 * 模板内容
	 */
	@ApiModelProperty(value="模板内容")
	private String templateContent;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
	/**
	 * 模板描述
	 */
	@ApiModelProperty(value="模板描述")
	private String templateDescription;
	/**
	 * 模板使用范围 0代表个人  1代表科室
	 */
	@ApiModelProperty(value="模板使用范围 0代表个人  1代表科室")
	private Integer templateUseScope;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;
	/**
	 * 同一模板的项目索引
	 */
	@ApiModelProperty(value="同一模板的项目索引")
	private Integer templateIndex;
	/**
	 * 项目描述
	 */
	@ApiModelProperty(value="项目描述")
	private String projectDescribe;

	/**
	 * 是否存在记录固定值  0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在记录固定值  0存在  1不存在")
	private Integer projectRecordValueFlag;
	/**
	 * 是否存在预警 0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在预警")
	private Integer projectWarmFlag;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;

	/**
	 * 项目数据
	 */
	@ApiModelProperty(value="项目数据")
	private String projectSpecificRecord;



}
