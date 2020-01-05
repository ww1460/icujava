package com.pig4cloud.pigx.ccxxicu.api.vo.assess;

import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessCondition;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProjectRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: pigx
 * @description: 评估的条件极其记录
 * @author: yyj
 * @create: 2019-09-29 20:03
 **/
@Data
@ApiModel(value = "评估树")
public class ConditionRecordVo {

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

	private List<String> records;

	/**
	 * 选中条件的id
	 */
	@ApiModelProperty(value = "选中条件的对象")
	private List<AssessProjectRecord> pitchOnId;

}
