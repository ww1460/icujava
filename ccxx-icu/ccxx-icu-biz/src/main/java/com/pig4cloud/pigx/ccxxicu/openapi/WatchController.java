package com.pig4cloud.pigx.ccxxicu.openapi;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.Message;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.MessageTypeEnum;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.TaskWarnInfo;
import com.pig4cloud.pigx.ccxxicu.service.scenes.WatchOpenService;
import com.pig4cloud.pigx.ccxxicu.websocket.WebSocketServer;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: pigx
 * @description: 腕表接口
 * @author: yyj
 * @create: 2019-10-25 10:36
 **/
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/watch")
@Api(value = "watch", tags = "腕表与icu后端的交互")
public class WatchController {

	@Autowired
	private WatchOpenService watchOpenService;


	@ApiOperation(value = "项目预警列表", notes = "项目预警列表")
	@GetMapping("/projectWarm")
	@Inner(value = false)
	public R selectProjectWarm(@RequestParam String nurseId) {

		return R.ok(watchOpenService.projectWarm(nurseId));
	}




	@ApiOperation(value = "任务预警列表", notes = "任务预警列表")
	@GetMapping("/taskWarm")
	@Inner(value = false)
	public R taskWarm(@RequestParam String nurseId) {

		return R.ok(watchOpenService.taskWarm(nurseId));
	}

	@ApiOperation(value = "任务列表", notes = "任务列表")
	@GetMapping("/taskList")
	@Inner(value = false)
	public R taskList(@RequestParam String nurseId) {

		return R.ok(watchOpenService.taskList(nurseId));
	}


	@ApiOperation(value = "任务列表", notes = "任务列表")
	@GetMapping("/nurse")
	@Inner(value = false)
	public R nurseList(@RequestParam String deptId) {

		return R.ok(watchOpenService.getNurseList(deptId));
	}


	@GetMapping("/sendMessage")
	public Message testSendMessage(@RequestParam String username){
		Message message = new Message();
		message.setReceiver(username);
		message.setType(MessageTypeEnum.TASK_WARN_PROMPT);
		List<Object> infoList = new ArrayList<>();
		TaskWarnInfo itemWarnInfo = new TaskWarnInfo();
		itemWarnInfo.setBedNo("B1231214141434");
		itemWarnInfo.setTaskName("打针");
		itemWarnInfo.setExecuteTime(new Date());
		itemWarnInfo.setPatientName("李子树");
		infoList.add(itemWarnInfo);
		message.setMessageBody(infoList);
		try {
			WebSocketServer.sendMessageTo(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("返回的数据为：{}", JSON.toJSON(message));
		return message;
	}
}
