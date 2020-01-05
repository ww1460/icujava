package com.pig4cloud.pigx.ccxxicu.service.scenes;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.Message;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseWatch;

import java.util.List;

public interface WatchOpenService {

	/**
	 * 项目预警
	 * @return
	 */
	Message projectWarm(String nurseId);

	/**
	 * 任务预警
	 * @param nurseId
	 * @return
	 */
	Message taskWarm(String nurseId);

	/**
	 * 任务列表
	 * @param nurseId
	 * @return
	 */
	Message taskList(String nurseId);

	/**
	 * 新增任务时  推送
	 * @param taskSubTask
	 * @return
	 */
	boolean addTaskToWatch(TaskSubTask taskSubTask);


	List<NurseWatch> getNurseList(String deptId);



}
