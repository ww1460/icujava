package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@ApiModel(value = "护理记录展示数据")
public class NursingRecordVo {

	/**
	 * 护理记录id
	 */
	@ApiModelProperty(value = "护理记录id")
	private Integer id;
	/**
	 * 护理记录生成id
	 */
	@ApiModelProperty(value = "护理记录生成id")
	private String nursingRecordId;
	/**
	 * 护理记录内容
	 */
	@ApiModelProperty(value = "护理记录内容")
	private String recordContent;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value = "患者id")
	private String patientId;
	/**
	 * 创建人id
	 */
	@ApiModelProperty(value = "创建人id")
	private String createUserId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value = "科室id")
	private String deptId;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 记录来源
	 */
	@ApiModelProperty(value = "记录来源")
	private String source;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remarks;
	/**
	 * 患者姓名
	 */
	@ApiModelProperty(value = "患者姓名")
	private String patientName;
	/**
	 * 床位
	 */
	@ApiModelProperty(value = "床位")
	private String bedCode;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String realName;

	/**
	 * 展示的位置  1 护理记录单三
	 */
	@ApiModelProperty(value="展示的位置  1 护理记录单三")
	private Integer showFlag;


}
