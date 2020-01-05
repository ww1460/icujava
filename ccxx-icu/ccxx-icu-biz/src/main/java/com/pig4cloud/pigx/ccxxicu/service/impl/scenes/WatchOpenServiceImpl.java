package com.pig4cloud.pigx.ccxxicu.service.impl.scenes;

import com.pig4cloud.pigx.ccxxicu.api.Bo.patient.PatientInfoBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.ArrangeResult;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.TaskSubTask;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.*;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NurseWatch;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientInfo;
import com.pig4cloud.pigx.ccxxicu.api.vo.project.ProjectWarmVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.EarlyWarningVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.task.TaskSubTaskVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.ShiftEnum;
import com.pig4cloud.pigx.ccxxicu.service.nurse.ArrangeResultService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectWarmService;
import com.pig4cloud.pigx.ccxxicu.service.scenes.WatchOpenService;
import com.pig4cloud.pigx.ccxxicu.service.task.EarlyWarningService;
import com.pig4cloud.pigx.ccxxicu.service.task.TaskSubTaskService;
import com.pig4cloud.pigx.ccxxicu.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: pigx
 * @description: 腕表实现类
 * @author: yyj
 * @create: 2019-10-26 11:09
 **/
@Service
public class WatchOpenServiceImpl implements WatchOpenService {


	@Autowired
	private ProjectWarmService projectWarmService;

	@Autowired
	private EarlyWarningService earlyWarningService;

	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	@Autowired
	private TaskSubTaskService taskSubTaskService;

	@Autowired
	private NurseService nurseService;

	@Autowired
	private ArrangeResultService arrangeResultService;

	//白班
	@Value("${dayShift.startTime}")
	private Integer dayStartHour;//开始的小时
	@Value("${dayShift.endTime}")
	private Integer dayEndHour;//结束的小时
	//早班
	@Value("${morningShift.startTime}")
	private Integer morningStartHour;//开始的小时
	@Value("${morningShift.endTime}")
	private Integer morningEndHour;//结束的小时
	//小夜班
	@Value("${swingShift.startTime}")
	private Integer swingStartHour;//开始的小时
	@Value("${swingShift.endTime}")
	private Integer swingEndHour;//结束的小时
	//大夜班
	@Value("${nightShift.startTime}")
	private Integer nightStartHour;//开始的小时
	@Value("${nightShift.endTime}")
	private Integer nightEndHour;//结束的小时
	//主
	@Value("${hostShift.startTime}")
	private Integer hostStartHour;//开始的小时
	@Value("${hostShift.endTime}")
	private Integer hostEndHour;//结束的小时
	//辅助
	@Value("${auxiliaryShift.startTime}")
	private Integer auxiliaryStartHour;//开始的小时
	@Value("${auxiliaryShift.endTime}")
	private Integer auxiliaryEndHour;//结束的小时
	//总管
	@Value("${managerShift.startTime}")
	private Integer managerStartHour;//开始的小时
	@Value("${managerShift.endTime}")
	private Integer managerEndHour;//结束的小时

	private List<Integer> shiftTime(Integer now) {

		List<Integer> time = new ArrayList<>();

		time.add(dayStartHour);
		time.add(dayEndHour);
		time.add(morningStartHour);
		time.add(morningEndHour);
		time.add(swingStartHour);
		time.add(swingEndHour);
		time.add(nightStartHour);
		time.add(nightEndHour);
		time.add(hostStartHour);
		time.add(hostEndHour);
		time.add(auxiliaryStartHour);
		time.add(auxiliaryEndHour);
		time.add(managerStartHour);
		time.add(managerEndHour);

		List<Integer> hour = new ArrayList<>(48);

		for (int i = 0; i < 48; i++) {

			hour.add(i % 24);

		}
		List<Integer> res = new ArrayList<>();


		for (int i = 0; i < time.size(); i += 2) {

			int j = hour.indexOf(time.get(i));
			List<Integer> integers = hour.subList(j, hour.size());

			int k = integers.indexOf(time.get(i + 1));

			List<Integer> integers1 = integers.subList(0, k-1);

			if (integers1.indexOf(now) > -1) {
				res.add(i / 2);
			}
		}
		List<Integer> shift = new ArrayList<>();
		res.forEach(ar -> {
			if (ar == 0) {
				shift.add(ShiftEnum.DAY_SHIFT.getCode());
			} else if (ar ==1) {
				shift.add(ShiftEnum.MORNING_SHIFT.getCode());
			} else if (ar ==2) {
				shift.add(ShiftEnum.SWING_SHIFT.getCode());
			} else if (ar ==3) {
				shift.add(ShiftEnum.NIGHT_SHIFT.getCode());
			} else if (ar ==4) {
				shift.add(ShiftEnum.HOST_SHIFT.getCode());
			} else if (ar ==5) {
				shift.add(ShiftEnum.AUXILIARY_SHIFT.getCode());
			} else if (ar ==6) {
				shift.add(ShiftEnum.MANAGER_SHIFT.getCode());
			}
		});

		return shift;
	}

	/**
	 * 项目预警列表
	 *
	 * @return
	 */
	@Override
	public Message projectWarm(String nurseId) {

		if (StringUtils.isEmpty(nurseId)) {
			return null;
		}

		List<ProjectWarmVo> projectWarmVos = projectWarmService.toWatch(nurseId);

		Message message = new Message();
		message.setReceiver(nurseId);
		message.setType(MessageTypeEnum.TASK_WARN_LIST);
		if (CollectionUtils.isEmpty(projectWarmVos)) {
			return message;
		}
		List<Object> infoList = new ArrayList<>();
		projectWarmVos.forEach(ar -> {
			ItemWarnInfo itemWarnInfo = new ItemWarnInfo();
			itemWarnInfo.setItemName(ar.getProjectName());
			itemWarnInfo.setBedNo(ar.getBedCode());
			itemWarnInfo.setPatientName(ar.getPatientName());
			itemWarnInfo.setItemValue(ar.getWarmValue());
			infoList.add(itemWarnInfo);
		});

		message.setMessageBody(infoList);
		return message;
	}

	/**
	 * 任务预警列表
	 *
	 * @return
	 */
	@Override
	public Message taskWarm(String nurseId) {

		List<EarlyWarningVo> task = earlyWarningService.getTask(nurseId);

		Message message = new Message();
		message.setReceiver(nurseId);
		message.setType(MessageTypeEnum.TASK_WARN_LIST);
		if (CollectionUtils.isEmpty(task)) {
			return message;
		}

		List<Object> infoList = new ArrayList<>();
		task.forEach(ar -> {
			TaskWarnInfo taskWarnInfo = new TaskWarnInfo();
			taskWarnInfo.setTaskName(ar.getTaskContent());
			taskWarnInfo.setBedNo(ar.getBedName());
			taskWarnInfo.setPatientName(ar.getPatientName());
			ZoneId zoneId = ZoneId.systemDefault();
			ZonedDateTime zdt = ar.getTriggerTime().atZone(zoneId);
			Date date = Date.from(zdt.toInstant());
			taskWarnInfo.setExecuteTime(date);
			infoList.add(taskWarnInfo);
		});

		message.setMessageBody(infoList);

		return message;
	}

	/**
	 * 任务列表
	 *
	 * @return
	 */
	@Override
	public Message taskList(String nurseId) {

		Nurse nurse = nurseService.selectByUserId(nurseId);

		TaskSubTaskVo taskSubTaskVo = new TaskSubTaskVo();

		PatientInfoBo patientInfo = new PatientInfoBo();


		patientInfo.setDeptId(nurse.getDeptId());

		if (nurse.getRoleFlag().equals("0")) {

			taskSubTaskVo.setAssociatedNurse(nurseId);
			patientInfo.setNurseId(nurseId);
		}

		taskSubTaskVo.setDeptId(nurse.getDeptId());

		List<TaskSubTaskVo> taskSubTaskVos = taskSubTaskService.taskList(taskSubTaskVo);

		Message message = new Message();
		message.setReceiver(nurseId);
		message.setType(MessageTypeEnum.TASK_LIST);

		if (CollectionUtils.isEmpty(taskSubTaskVos)) {

			return message;
		}

		List<PatientInfo> patientId = nursePatientCorrelationService.getPatientId(patientInfo);

		if (CollectionUtils.isEmpty(patientId)) {

			return message;

		}
		Map<String, List<PatientInfo>> collect = patientId.stream().collect(Collectors.groupingBy(PatientInfo::getPatientIndex));
		List<Object> infoList = new ArrayList<>();
		taskSubTaskVos.forEach(ar -> {
			TaskInfo taskInfo = new TaskInfo();
			List<PatientInfo> patientInfos = collect.get(ar.getPatientId());
			if (CollectionUtils.isEmpty(patientInfos)) {
				return;
			}
			taskInfo.setTaskName(ar.getTaskName());
			taskInfo.setPatientName(ar.getPatientName());
			taskInfo.setBedNo(patientInfos.get(0).getHospitalBed().getBedCode());
			taskInfo.setProgressBar(ar.getProgressBar());
			taskInfo.setStatus(TaskStateEnum.getFrom(ar.getState() + ""));
			taskInfo.setTaskId(ar.getTaskSubTaskId());
			infoList.add(taskInfo);
		});

		message.setMessageBody(infoList);

		return message;
	}

	/**
	 * 新增任务时 推送
	 *
	 * @param taskSubTask
	 * @return
	 */
	@Override
	public boolean addTaskToWatch(TaskSubTask taskSubTask) {

		if (taskSubTask == null) {
			return false;
		}
		PatientInfo patientInfo = nursePatientCorrelationService.selectPatientId(taskSubTask.getPatientId());
		WebSocketServer ws = new WebSocketServer();
		Message message = new Message();
		message.setReceiver(patientInfo.getNurse().getNurseId());
		message.setType(MessageTypeEnum.ADD_TASK_PROMPT);
		List<Object> infoList = new ArrayList<>();
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setTaskId(taskSubTask.getTaskSubTaskId());
		taskInfo.setStatus(TaskStateEnum.WAIT);
		taskInfo.setProgressBar(taskSubTask.getProgressBar());
		infoList.add(taskInfo);
		message.setMessageBody(infoList);
		try {
			ws.sendMessageTo(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 获取到科室
	 *
	 * @return
	 */
	@Override
	public List<NurseWatch> getNurseList(String deptId) {

		LocalDateTime now = LocalDateTime.now();

		int hour = now.getHour();
		List<Integer> integers = shiftTime(hour);
		ArrangeResult arrangeResult = new ArrangeResult();
		arrangeResult.setDateTime(now);
		arrangeResult.setDeptId(deptId);
		List<NurseWatch> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(integers)) {

			List<NurseWatch> nurseWatches = nurseService.AllNurse(deptId);

			/*List<Nurse> nurses = nurseService.selectByDept(deptId);
			nurses.forEach(ar->{
				NurseWatch nurseWatch = new NurseWatch();
				BeanUtils.copyProperties(ar,nurseWatch);
				result.add(nurseWatch);
			});*/
			return nurseWatches;
		}

		integers.forEach(ar->{
			arrangeResult.setWorkShift(ar);
			List<ArrangeResult> byDate = arrangeResultService.getByDate(arrangeResult);
			if (CollectionUtils.isEmpty(byDate)) {
				return;
			}
			byDate.forEach(msg->{
				NurseInfo nurseInfo = nurseService.getNurseInfo(Integer.getInteger(msg.getNurseId()));
				NurseWatch nurseWatch = new NurseWatch();
				BeanUtils.copyProperties(nurseInfo,nurseWatch);
				result.add(nurseWatch);
			});
		});
		if (CollectionUtils.isEmpty(result)) {
			/*List<Nurse> nurses = nurseService.selectByDept(deptId);
			nurses.forEach(ar->{
				NurseWatch nurseWatch = new NurseWatch();
				BeanUtils.copyProperties(ar,nurseWatch);
				result.add(nurseWatch);
			});*/
			List<NurseWatch> nurseWatches = nurseService.AllNurse(deptId);
			return nurseWatches;
		}
		return result;
	}

}
