package com.pig4cloud.pigx.ccxxicu.api.vo.hisdata;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 给his传输的数据
 */
@Data
public class HisDoctorsAdviceExtVo {

	/**
	 * 正式 【zs】测试【cs】
	 */
	private String Db;
	/**
	 * His执行情况id
	 */
	@ApiModelProperty(value="His执行情况id")
	private String hisFZYExecInfoId;
	/**
	 * his医嘱id
	 */
	@ApiModelProperty(value="")
	private String hisDoctorsAdviceId;
	/**
	 * 关联id
	 */
	@ApiModelProperty(value="")
	private String hisDoctorsAdviceProjectId;

	/**
	 * 执行状态[4完成]
	 */
	@ApiModelProperty(value="执行状态[4完成]")
	private Integer executeType;
	/**
	 * 执行人
	 */
	@ApiModelProperty(value="执行人")
	private String executeNurse;
	/**
	 * 执行时间
	 */
	@ApiModelProperty(value="执行时间")
	private LocalDateTime executeTime;

	/**
	 * 患者id
	 */
	private String hisPatientId;


}
