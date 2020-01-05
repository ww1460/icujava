package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.bed.BedRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "床位使用时间记录展示")
public class BedRecordVo extends BedRecord {

	/**
	 * 患者姓名
	 */
	@ApiModelProperty(value="患者姓名")
	private String patientName;
	/**
	 * 床位名
	 */
	@ApiModelProperty(value="床位名")
	private String bedName;
	/**
	 * 床位编号
	 */
	@ApiModelProperty(value="床位编号")
	private String bedCode;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value="创建人")
	private String nurseName;






}
