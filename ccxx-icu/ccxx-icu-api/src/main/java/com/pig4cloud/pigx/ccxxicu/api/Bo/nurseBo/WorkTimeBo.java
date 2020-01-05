package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: pigx
 * @description: 工作时间查询条件
 * @author: yyj
 * @create: 2019-11-06 17:22
 **/
@Data
public class WorkTimeBo {


	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private LocalDateTime firstTime;

	/**
	 * 科室id
	 */
	@ApiModelProperty(value = "科室id")
	private String deptId;

	/**
	 * 护士id
	 */
	@ApiModelProperty(value = "护士id")
	private String nurseId;

	/**
	 * 时间标识   1本日  2本月 3本周
	 */
	@ApiModelProperty(value = "时间标识")
	private Integer timeFlag;


}
