package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: pigx
 * @description: 病情护理数据树
 * @author: yyj
 * @create: 2019-09-04 15:38
 **/
@Data
@ApiModel(value = "病情护理数据树")
public class IllnessTree {

	/**
	 * 异常数据及其措施
	 */
	@ApiModelProperty(value="异常数据及其措施")
	List<IllnessNursingStateVo> illness;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;

	/**
	 * 项目简称
	 */
	@ApiModelProperty(value="项目简称")
	private String projectCode;
	/**
	 * 实际措施
	 */
	@ApiModelProperty(value="实际措施")
	private String nursingMeasure="";

	/**
	 * 项目记录
	 */
	@ApiModelProperty(value="项目记录")
	private String projectRecord;

	/**
	 * 是否存在记录固定值  0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在记录固定值  0存在  1不存在")
	private Integer projectRecordValueFlag;

	/**
	 * 项目的数值数据
	 */
	@ApiModelProperty(value="项目的数值数据")
	private String projectValue;

	/**
	 * 病情护理项目类型
	 */
	@ApiModelProperty(value="病情护理项目类型")
	private Integer illnessNursingTypeFlag;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;


}
