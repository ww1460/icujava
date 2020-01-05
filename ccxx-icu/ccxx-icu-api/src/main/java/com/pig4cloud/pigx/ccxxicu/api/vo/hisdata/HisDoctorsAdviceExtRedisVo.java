package com.pig4cloud.pigx.ccxxicu.api.vo.hisdata;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 医嘱执行记录redis储存   implements Serializable
 */
@Data
public class HisDoctorsAdviceExtRedisVo implements Serializable {

	@ApiModelProperty(value = "his医嘱id")
	public String hisDoctorsAdviceId;

	@ApiModelProperty(value = "医嘱执行id")
	public String hisFZYExecInfoId;

	@ApiModelProperty(value = "创建医嘱时间")
	public LocalDateTime createTime;


}
