package com.pig4cloud.pigx.ccxxicu.api.vo.assess;

import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "评估树")
public class ProjectConditionVo {

	/**
	 * 类型的项目
	 */
	@ApiModelProperty(value = "类型的项目")
	private final int index = 1;

	/**
	 * 评估项目id
	 */
	@ApiModelProperty(value = "评估项目id")
	private Integer id;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value = "项目id")
	private String assessProjectId;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value = "项目名")
	private String projectName;

	/**
	 * 项目简称
	 */
	@ApiModelProperty(value = "项目简称")
	private String projectCode;

	/**
	 * 项目的条件
	 */
	@ApiModelProperty(value = "项目的条件")
	private List<AssessCondition> projectConditions;
	/**
	 * 评估结果备注
	 */
	@ApiModelProperty(value="评估结果备注")
	private String remarks;

	/**
	 * 选中条件的id
	 */
	@ApiModelProperty(value = "选中条件的id")
	private List<String> pitchOnId;


	/**
	 * 选中条件的id
	 */
	@ApiModelProperty(value = "选中条件的id")
	private List<LocalDateTime> recordTime;

}
