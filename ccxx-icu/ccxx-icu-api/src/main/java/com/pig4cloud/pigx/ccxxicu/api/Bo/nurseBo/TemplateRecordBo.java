package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "使用护理模板添加护理记录")
public class TemplateRecordBo {


	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 护理记录内容
	 */
	@ApiModelProperty(value="护理记录内容")
	private String recordContent;


	private Integer isProjectFlag;

	/**
	 *模板项目及数据
	 */
	@ApiModelProperty(value="模板项目及数据")
	private List<ProjectRecord> projectRecords;

	/**
	 * 展示的位置  1 护理记录单三
	 */
	@ApiModelProperty(value="展示的位置  1 护理记录单三")
	private Integer showFlag;

	/**
	 * 模板编号
	 */
	@ApiModelProperty(value="模板编号")
	private String templateCode;


}
