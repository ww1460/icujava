package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "知情文书记录展示")
public class KnowWritLogsVo {

	/**
	 * 知情文书记录  id
	 */
	@ApiModelProperty(value="知情文书记录  id")
	private Integer id;
	/**
	 * 生成id
	 */
	@ApiModelProperty(value="生成id")
	private String knowWritLogsId;
	/**
	 * 知情文书生成id
	 */
	@ApiModelProperty(value="知情文书生成id")
	private String knowWritId;
	/**
	 * 科室id
	 */
	@ApiModelProperty(value="科室id")
	private String deptId;
	/**
	 * 患者id
	 */
	@ApiModelProperty(value="患者id")
	private String patientId;
	/**
	 * 患者姓名
	 */
	@ApiModelProperty(value="患者姓名")
	private String patientName;

	/**
	 * 文书内容
	 */
	@ApiModelProperty(value="文书内容")
	private String knowWritContent;
	/**
	 * 患者或患者家属签字
	 */
	@ApiModelProperty(value="患者或患者家属签字")
	private String patientRelationSignature;
	/**
	 * 患者或关系人的联系方式
	 */
	@ApiModelProperty(value="患者或关系人的联系方式")
	private String patientRelativePhone;
	/**
	 * 与患者的关系
	 */
	@ApiModelProperty(value="与患者的关系")
	private String patientRelation;
	/**
	 * 签字日期
	 */
	@ApiModelProperty(value="签字日期")
	private String signatureTime;
	/**
	 * 创建人id
	 */
	@ApiModelProperty(value="创建人id")
	private String createUserId;

	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value="创建人姓名")
	private String nurseName;

	/**
	 * 文书标题
	 */
	@ApiModelProperty(value="文书标题")
	private String writTitle;


}
