package com.pig4cloud.pigx.ccxxicu.api.vo.assess;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "评估树")
public class AssessProjectVo {

	/**
	 * 评估类型id
	 */
	@ApiModelProperty(value = "评估类型id")
	private Integer id;

	/**
	 * 评估id
	 */
	@ApiModelProperty(value = "评估id")
	private String projectId;

	/**
	 * 评估类型
	 */
	@ApiModelProperty(value = "评估类型")
	private String assessType;

	/**
	 * 类型id
	 */
	@ApiModelProperty(value = "类型id")
	private String assessTypeId;

	/**
	 * 分值最小值
	 */
	@ApiModelProperty(value = "分值最小值")
	private String scoreMin;
	/**
	 * 分值范围
	 */
	@ApiModelProperty(value = "分值最大值")
	private String scoreMax;

	/**
	 * 索引
	 */
	@ApiModelProperty(value = "索引")
	private final int index = 0;

	/**
	 * 类型的项目
	 */
	@ApiModelProperty(value = "类型的项目")
	private List<ProjectConditionVo> assessProjects;


}
