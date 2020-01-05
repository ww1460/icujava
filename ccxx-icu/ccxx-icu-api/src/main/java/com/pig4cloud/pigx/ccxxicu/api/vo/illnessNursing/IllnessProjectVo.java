package com.pig4cloud.pigx.ccxxicu.api.vo.illnessNursing;

import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 病情护理项目
 * @author: yyj
 * @create: 2019-10-16 19:30
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "病情护理项目")
public class IllnessProjectVo extends IllnessProject {


	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;
	/**
	 * 项目简称
	 */
	@ApiModelProperty(value="项目简称")
	private String projectCode;

	/**
	 * 是否存在预警  0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在预警  0存在  1不存在")
	private Integer projectWarmFlag;

	/**
	 * 是否存在记录固定值  0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在记录固定值  0存在  1不存在")
	private Integer projectRecordValueFlag;


}
