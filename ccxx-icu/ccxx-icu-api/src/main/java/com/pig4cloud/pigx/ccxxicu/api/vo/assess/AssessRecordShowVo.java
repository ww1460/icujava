package com.pig4cloud.pigx.ccxxicu.api.vo.assess;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "评估记录结果展示")
public class AssessRecordShowVo {
	/**
	 * 记录id
	 */
	@ApiModelProperty(value="记录id")
	private String id;
	/**
	 * 记录生成id
	 */
	@ApiModelProperty(value="记录生成id")
	private String assessRecordId;
	/**
	 * 评估项目id
	 */
	@ApiModelProperty(value="评估项目id")
	private String projectId;
	/**
	 * 评估结果
	 */
	@ApiModelProperty(value="评估结果")
	private String assessResult;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private String createTime;
	/**
	 * 创建人id
	 */
	@ApiModelProperty(value="创建人id")
	private String createUserId;
	/**
	 * 评估名
	 */
	@ApiModelProperty(value="评估名")
	private String projectName;
	/**
	 * 评估名
	 */
	@ApiModelProperty(value="评估名")
	private String projectCode;
	/**
	 * 患者名
	 */
	@ApiModelProperty(value="患者名")
	private String  patientName;
	/**
	 * 评估人
	 */
	@ApiModelProperty(value="评估人")
	private String  nurseName;
	/**
	 * 患者床位号
	 */
	@ApiModelProperty(value="患者床位号")
	private String bedCode;

	
	
}
