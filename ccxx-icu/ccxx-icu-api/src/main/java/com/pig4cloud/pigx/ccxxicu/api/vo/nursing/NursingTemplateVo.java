package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "护理模板分页展示字段")
public class NursingTemplateVo {

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
	 * 模板描述
	 */
	@ApiModelProperty(value="模板描述")
	private String templateDescription;
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
	 * 模板使用范围 0代表个人  1代表科室
	 */
	@ApiModelProperty(value="模板使用范围 0代表个人  1代表科室")
	private Integer templateUseScope;
	/**
	 * 创建人id
	 */
	@ApiModelProperty(value="创建人id")
	private String createUserId;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value="创建人")
	private String creator;
	/**
	 * 科室
	 */
	@ApiModelProperty(value="科室")
	private String deptId;


}
