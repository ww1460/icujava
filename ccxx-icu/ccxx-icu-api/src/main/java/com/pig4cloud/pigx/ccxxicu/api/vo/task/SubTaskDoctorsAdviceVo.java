package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubTaskDoctorsAdviceVo {


	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(value="id")
	private Integer id;;
	/**
	 * 任务名称
	 */
	@ApiModelProperty(value="任务名称")
	private String taskName;
	/**
	 * 任务描述
	 */
	@ApiModelProperty(value="任务描述")
	private String taskDescribe;
	/**
	 * "任务完成状态   1待执行      3完成  4停止（因为转科等等）    5患者出科
	 */
	@ApiModelProperty(value="任务完成状态   1待执行     3完成      4 停止（因为转科等等）  5患者出科")
	private Integer state;
	/**
	 * 完成护士id
	 */
	@ApiModelProperty(value="完成护士id")
	private String completedNurseId;

	/**
	 * 护士姓名
	 */
	@ApiModelProperty(value="护士姓名")
	private String completedNurse;

	/**
	 * 医嘱id
	 */
	@ApiModelProperty(value = "医嘱id")
	private String  hisDoctorsAdviceId;
}
