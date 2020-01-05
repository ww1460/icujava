package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecordDuplicate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: pigx
 * @description: 报表展示
 * @author: yyj
 * @create: 2019-09-12 11:28
 **/
@Data
@ApiModel(value = "报表数据展示")
public class NursingReportVo  {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer id;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private String projectId;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectName;

	/**
	 * 项目类型
	 */
	@ApiModelProperty(value="项目类型")
	private String projectType;

	/**
	 * 项目名
	 */
	@ApiModelProperty(value="项目名")
	private String projectCode;
	/**
	 * 是否存在记录固定值  0存在  1不存在
	 */
	@ApiModelProperty(value="是否存在记录固定值  0存在  1不存在")
	private Integer projectRecordValueFlag;


	/**
	 * 展示形式 0 固定值编号  1 是否完成  2  具体数据
	 */
	@ApiModelProperty(value="展示形式 0 固定值编号  1 是否完成  2  具体数据")
	private Integer showWayFlag;

	/**
	 * 项目数据
	 */
	@ApiModelProperty(value="项目数据")
	private List<ProjectRecordDuplicate> projectRecordDuplicates;


	/**
	 * 展示顺序
	 */
	@ApiModelProperty(value="展示顺序")
	private Integer showIndex;


}
