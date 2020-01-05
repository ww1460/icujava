package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "护理模板分页查询条件")
public class NursingTemplateBo {

	@ApiModelProperty(value="自增id")
	private Integer id;
	/**
	 * 护理模板id
	 */
	@ApiModelProperty(value="护理模板id")
	private String nursingTemplateId;
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
	 * 模板编号
	 */
	@ApiModelProperty(value="模板编号")
	private Integer templateUseScope;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value="创建人")
	private String nurseId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
	/**
	 * 是否为个人 0是  1否
	 */
	@ApiModelProperty(value="是否为个人 0是  1否")
	private Integer personal;

	/**
	 * 模板类型  1直接添加 2护理记录单 3 两者都有
	 */
	@ApiModelProperty(value="模板类型  1直接添加 2护理记录单 3 两者都有")
	private Integer templateType;


	private String orderBy;
}
