package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessAdvice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: pigx
 * @description: 护理建议
 * @author: yyj
 * @create: 2019-09-09 13:35
 **/
@Data
@ApiModel(value = "护理建议")
public class IllnessNursingStateVo  {

	/**
	 * 病情护理项目状态表 id
	 */
	@TableId
	@ApiModelProperty(value="病情护理项目状态表 id")
	private Integer id;
	/**
	 * 生成id
	 */
	@ApiModelProperty(value="生成id")
	private String illnessNursingStateId;

	/**
	 * 项目的状态标识 0正常 1异常
	 */
	@ApiModelProperty(value="项目的状态标识 0正常 1异常")
	private Integer projectStateFlag;
	/**
	 * 项目的状态详情
	 */
	@ApiModelProperty(value="项目的状态详情")
	private String projectState;

	/**
	 * 删除标识 0正常 1删除
	 */
	@ApiModelProperty(value="删除标识 0正常 1删除")
	private Integer delFlag;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value="创建人")
	private String createUserId;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value="修改时间")
	private LocalDateTime updateTime;
	/**
	 * 修改人
	 */
	@ApiModelProperty(value="修改人")
	private String updateUserId;
	/**
	 * 删除时间
	 */
	@ApiModelProperty(value="删除时间")
	private LocalDateTime delTime;
	/**
	 * 删除人
	 */
	@ApiModelProperty(value="删除人")
	private String delUserId;
	/**
	 * 建议措施
	 */
	@ApiModelProperty(value="建议措施")
	private List<IllnessAdvice> illnessAdvices;


}
