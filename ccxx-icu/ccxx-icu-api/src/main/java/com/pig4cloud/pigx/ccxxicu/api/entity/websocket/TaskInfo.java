package com.pig4cloud.pigx.ccxxicu.api.entity.websocket;

import lombok.Data;

@Data
public class TaskInfo {

	//项目雪花id
	private String taskId;
	//病床号
	private String bedNo;
	//患者名
	private String patientName;
	//任务name
	private String taskName;
	//状态
	private TaskStateEnum status;
	//进度条 %
	private Integer progressBar;
}
