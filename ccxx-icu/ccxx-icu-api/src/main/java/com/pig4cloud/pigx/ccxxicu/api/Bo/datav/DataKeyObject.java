package com.pig4cloud.pigx.ccxxicu.api.Bo.datav;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "综合数据大屏关键数据返回对象")
public class DataKeyObject {

	private Integer sortValue;

	private String title;

	private Integer number;

	private String unit;
}
