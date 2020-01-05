package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessAdvice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 分页查询数据
 * @author: yyj
 * @create: 2019-09-09 19:17
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页查询数据护理建议")
public class IllnessAdviceVo extends IllnessAdvice {


	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String  projectName;
	/**
	 * 项目简称
	 */
	@ApiModelProperty(value="项目简称")
	private String projectCode;

	@ApiModelProperty(value="已选择标识 0是已选择  1是未选择")
	private Integer pitchOnId;


}
