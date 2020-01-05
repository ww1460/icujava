package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 护理记录单三
 * @author: yyj
 * @create: 2019-09-12 16:18
 **/

@Data
@ApiModel(value = "护理单三")
@EqualsAndHashCode(callSuper = true)
public class NursingReportThreeVo extends NursingRecord {

	/**
	 * 时间月日
	 */
	@ApiModelProperty(value = "时间月日")
	private String timeOfYear;
	/**
	 * 时间时分
	 */
	@ApiModelProperty(value = "时间时分")
	private String timeOfMin;
	/**
	 * 护理人
	 */
	@ApiModelProperty(value = "护理人")
	private String nurseName;

	/**
	 * 护理人
	 */
	@ApiModelProperty(value = "护理人")
	private String signature;


}
