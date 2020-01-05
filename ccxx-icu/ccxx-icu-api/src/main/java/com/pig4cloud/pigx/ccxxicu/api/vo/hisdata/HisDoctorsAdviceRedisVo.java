package com.pig4cloud.pigx.ccxxicu.api.vo.hisdata;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class HisDoctorsAdviceRedisVo implements Serializable {

	@ApiModelProperty(value = "his医嘱id")
	public String hisDoctorsAdviceId;

	@ApiModelProperty(value = "新增医嘱时间")
	public LocalDateTime intsetTime;

	@ApiModelProperty(value = "修改数据时间")
	public LocalDateTime updateTime;
	//用来判断当前医嘱是否修改了
	@ApiModelProperty(value = "医嘱状态")
	public String state;

}
