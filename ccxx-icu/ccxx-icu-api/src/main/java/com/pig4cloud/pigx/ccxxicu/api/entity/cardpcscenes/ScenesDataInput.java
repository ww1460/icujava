package com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes;

import com.pig4cloud.pigx.common.core.constant.enums.ScenesActionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "卡片电脑与后端交互数据结构")
public class ScenesDataInput {

	@ApiModelProperty(value="场景名")
	private ScenesActionEnum action;

	@ApiModelProperty(value="主机ipv4地址")
	private String ip;

	@ApiModelProperty(value="主机mac地址")
	private String mac;

	@ApiModelProperty(value="描述信息")
	private String des;

	@ApiModelProperty(value="数据列表")
	private List<RfidData> data;

	public ScenesDataInput(){
		this.data = new ArrayList<>();
	}
}
