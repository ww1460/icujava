package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "知情文书记录 查询条件")
public class KnowWritLogsBo {

	/**
	 * 生成id
	 */
	@ApiModelProperty(value="生成id")
	private String knowWritLogsId;
	/**
	 * 知情文书生成id
	 */
	@ApiModelProperty(value="知情文书生成id")
	private String knowWritId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 护士id
	 */
	@ApiModelProperty(value="护士id")
	private String nurseId;
	/**
	 * 患者姓名
	 */
	@ApiModelProperty(value="患者姓名")
	private String patientName;

	/**
	 * 文书标题
	 */
	@ApiModelProperty(value="文书标题")
	private String writTitle;

	/**
	 * 文书类型
	 */
	@ApiModelProperty(value="文书类型")
	private Integer writType;
}
