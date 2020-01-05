package com.pig4cloud.pigx.ccxxicu.api.vo.device;

import com.pig4cloud.pigx.ccxxicu.api.entity.device.UseRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UseRecordVo extends UseRecord {


	@ApiModelProperty(value="患者姓名")
	private String patientName;

	@ApiModelProperty(value = "护士姓名")
	private String nurseName;

	@ApiModelProperty(value = "仪器名称")
	private String deviceName;


}
