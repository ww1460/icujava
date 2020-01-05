package com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes;

import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "卡片电脑与后端交互数据结构")
public class RfidData {

	@ApiModelProperty(value="Rfid类型")
	private RfidTypeEnum type;

	@ApiModelProperty(value="状态 0：在；1：离开")
	private Integer status;

	@ApiModelProperty(value="Rfid")
	private String rfid;
}
