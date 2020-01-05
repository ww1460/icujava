package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 护理记录单关联项目展示
 * @author: yyj
 * @create: 2019-09-18 10:19
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护理记录单关联项目展示")
public class ProjectCorrelationVo extends Project {

	/**
	 * 自增id
	 */
	@TableId
	@ApiModelProperty(value="自增id")
	private Integer correlationId;



	/**
	 * 展示形式 0 固定值编号  1 是否完成  2  具体数据
	 */
	@ApiModelProperty(value="展示形式 0 固定值编号  1 是否完成  2  具体数据")
	private Integer showWayFlag;

	/**
	 * 展示顺序
	 */
	@ApiModelProperty(value="展示顺序")
	private Integer index;



}
