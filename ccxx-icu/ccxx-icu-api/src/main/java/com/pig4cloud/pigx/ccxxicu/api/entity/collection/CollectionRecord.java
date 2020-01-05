package com.pig4cloud.pigx.ccxxicu.api.entity.collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@ApiModel(value = "卡片电脑与后端交互数据结构")
public class CollectionRecord {

	@ApiModelProperty(value="主机ipv4地址")
	private String ip;

	@ApiModelProperty(value="主机mac地址")
	private String mac;

	@ApiModelProperty(value="数据发送时间戳")
	private Date timestamp;

	@ApiModelProperty(value="病人rfid")
	private String pRfid;

	@ApiModelProperty(value="设备rfid")
	private String eRfid;

	@ApiModelProperty(value="数据列表")
	private Map<String,String> data;

}
