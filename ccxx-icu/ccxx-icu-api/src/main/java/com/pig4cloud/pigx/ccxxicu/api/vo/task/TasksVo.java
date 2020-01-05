package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Tasks;
import lombok.Data;

import java.util.List;

/**
 * 用于在医嘱中生成任务用
 */
@Data
public class TasksVo {

	/**
	 * 任务内容
	 */
	private Tasks task;

	/**
	 * 子任务数据
	 */
	private List<TaskSubTask> subTask;

}
