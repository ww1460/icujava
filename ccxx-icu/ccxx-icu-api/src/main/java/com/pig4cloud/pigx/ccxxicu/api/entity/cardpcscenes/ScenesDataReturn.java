package com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes;

import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "卡片电脑和icu后端交互，后端返回数据结构")
public class ScenesDataReturn {

	@ApiModelProperty(value="请求响应结果")
	private String result;

	@ApiModelProperty(value="响应状态码")
	private String code;

	@ApiModelProperty(value="请求错误信息")
	private String msg;

	public ScenesDataReturn(String result, String code, String msg){
		this.result = result;
		this.code = code;
		this.msg = msg;
	}
}
