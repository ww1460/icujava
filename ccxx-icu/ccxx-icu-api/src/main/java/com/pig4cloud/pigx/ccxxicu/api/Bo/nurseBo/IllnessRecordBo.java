package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: pigx
 * @description: 病情护理记录
 * @author: yyj
 * @create: 2019-09-04 19:30
 **/
@Data
@ApiModel(value = "病情护理记录")
public class IllnessRecordBo {


	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;

	/**
	 * 项目状态
	 */
	@ApiModelProperty(value="项目状态")
	private String projectState;

	/**
	 * 实际措施
	 */
	@ApiModelProperty(value="实际措施")
	private String nursingMeasure;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;

	/**
	 * 患者病情
	 */
	@ApiModelProperty(value="患者病情")
	private String illnessRecord;

	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;


	/**
	 *模板项目及数据
	 */
	@ApiModelProperty(value="是否进入护理记录  0是  1否")
	private Integer inNursingFlag = 1;



}
