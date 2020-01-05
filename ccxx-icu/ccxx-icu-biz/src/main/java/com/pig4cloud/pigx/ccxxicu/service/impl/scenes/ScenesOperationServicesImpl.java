package com.pig4cloud.pigx.ccxxicu.service.impl.scenes;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ProcessMessageConstant;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.RfidData;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataInput;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataReturn;
import com.pig4cloud.pigx.ccxxicu.api.entity.collection.CollectionRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.DeviceDataRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Parameters;
import com.pig4cloud.pigx.ccxxicu.api.entity.newlogin.MacRfidUserRelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.PatientBedCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceDataRecordService;
import com.pig4cloud.pigx.ccxxicu.service.device.DeviceService;
import com.pig4cloud.pigx.ccxxicu.service.device.ParametersService;
import com.pig4cloud.pigx.ccxxicu.service.newlogin.MacRfidUserRelationService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.DispensingDrugService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientBedCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.ccxxicu.service.scenes.ScenesOperationService;
import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class ScenesOperationServicesImpl implements ScenesOperationService {

	@Resource
	private PatientService patientService;
	@Resource
	private DispensingDrugService drugService;
	@Resource
	private MacRfidUserRelationService macRfidUserRelationService;
	@Resource
	private NurseService nurseService;
	@Resource
	private PatientBedCorrelationService patientBedCorrelationService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private DeviceDataRecordService dataRecordService;
	@Resource
	private ParametersService parametersService;
	@Resource
	private ProjectRecordService projectRecordService;

	/**
	 * 通用场景：
	 * 地点：配药室、病房；
	 * 对象：护士、病人(唯一)、药品(唯一)rfid、设备、病床；
	 * 场景描述：任何的状态发生变化，出发此场景；
	 * 方法功能：将所有的变化的状态记录到数据库的mac_rfid_user_relation表中
	 *
	 * @param scenesDataInput
	 * @return
	 */
	@Override
	public ScenesDataReturn common(ScenesDataInput scenesDataInput) {
		//使用rfidSet进行去重
		Set<String> rfidSet = new HashSet<>();
		try {
			MacRfidUserRelation macRfidUserRelation = new MacRfidUserRelation();
			macRfidUserRelation.setMacAddress(scenesDataInput.getMac());
			macRfidUserRelation.setTerminalIntranetIp(scenesDataInput.getIp());
			for (RfidData rfidData : scenesDataInput.getData()) {
				if (rfidSet.contains(rfidData.getRfid())) {
					continue;
				}
				rfidSet.add(rfidData.getRfid());
				macRfidUserRelation.setRfidTime(LocalDateTime.now());
				macRfidUserRelation.setRfidId(rfidData.getRfid());
				macRfidUserRelation.setStatus(rfidData.getStatus());
				macRfidUserRelation.setNurseName(null);
				macRfidUserRelation.setNurseId(null);
				//设置rfid的类型
				switch (rfidData.getType()) {
					case N:
						macRfidUserRelation.setRfidType(RfidTypeEnum.N.getType());
						//通过rfidId查询到护士的信息，并赋值
						Nurse nurse = nurseService.getNurseInfoByRfidId(rfidData.getRfid());
						//没有查到护士信息，告警
						if (!ObjectUtils.isEmpty(nurse)) {
							//log.error("该护士为非法护士，请去系统录入信息");
							macRfidUserRelation.setNurseName(nurse.getRealName());
							macRfidUserRelation.setNurseId(nurse.getNurseId());
							//throw new Exception("该护士为非法护士，请去系统录入信息");
						}
						break;
					case P:
						macRfidUserRelation.setRfidType(RfidTypeEnum.P.getType());
						break;
					case M:
						macRfidUserRelation.setRfidType(RfidTypeEnum.M.getType());
						break;
					case B:
						macRfidUserRelation.setRfidType(RfidTypeEnum.B.getType());
						break;
					case C:
						macRfidUserRelation.setRfidType(RfidTypeEnum.C.getType());
						break;
					case E:
						macRfidUserRelation.setRfidType(RfidTypeEnum.E.getType());
						break;
					default:
						log.info("无用数据【{}】，忽略！", rfidData);
				}
				macRfidUserRelationService.saveMacRfidUserRelation(macRfidUserRelation);
			}
		} catch (NullPointerException | IndexOutOfBoundsException ne) {
			log.error("数据处理失败，异常信息为：【{}】", ne.getMessage(), ne);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_SAVE_EXCEPTION, "保存失败");
		} catch (Exception e) {
			log.error("数据处理失败，异常信息为：【{}】", e.getMessage(), e);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_SAVE_EXCEPTION, e.getMessage());
		}

		return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.RFID_PROCESS_OK, "保存成功");
	}

	/**
	 * 场景二：
	 * 地点：用药室（重症监护室）；
	 * 对象：护士、病人(唯一)、药品(唯一)rfid；
	 * 场景描述：护士带着药来到重症监护室，三者都在且持续一段时间，出发用药场景；
	 * 方法功能：校验护士是否是该病人的责任护士，药品是否属于该病人；且校验成功后，讲医嘱的状态改为已使用
	 *
	 * @param scenesDataInput
	 * @return
	 */
	@Override
	public ScenesDataReturn medication(ScenesDataInput scenesDataInput) {
		log.info("medication process!");
		//存放nRfid,pRfid,mRfid并去重
		Set<String> nurseSet = new HashSet<>();
		Set<String> patientSet = new HashSet<>();
		Set<String> medicalSet = new HashSet<>();
		AtomicReference<Integer> isRightFlag = new AtomicReference<>(0);
		for (RfidData rfidData : scenesDataInput.getData()) {
			switch (rfidData.getType()) {
				case N:
					nurseSet.add(rfidData.getRfid());
					isRightFlag.updateAndGet(v -> v + rfidData.getStatus());
					break;
				case P:
					patientSet.add(rfidData.getRfid());
					isRightFlag.updateAndGet(v -> v + rfidData.getStatus());
					break;
				case M:
					medicalSet.add(rfidData.getRfid());
					isRightFlag.updateAndGet(v -> v + rfidData.getStatus());
					break;
				default:
					log.info("无用数据【{}】，忽略！", rfidData);
			}
		}
		//护士、病人、药的状态必须为零&&药和病人唯一
		if (isRightFlag.get() > 0 || nurseSet.isEmpty() || patientSet.size() != 1 || medicalSet.size() != 1) {
			log.error("护士、病人、药不全在或者病人不为一个，无法用药，数据为：【{}】", scenesDataInput);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.MEDICATION_OPERATION_MISSING_OBJECT, "护士、病人、药不全在或者病人不为一个，无法用药");
		}
		try {
			//通过病人的rfid查询到病人信息，得到病人的id
			Patient patient = patientService.getByPatientRfid(patientSet.iterator().next());
			log.info("patient message:{}", patient);
			//通过病人的id获取到责任护士的rfid
			//没有必要校验责任护士
			/*String nRfid = patientService.getNurseRfidByPatientId(patient.getPatientId());
			log.info("查询到的责任护士rfid为:【{}】", nRfid);
			if (!nurseSet.contains(nRfid)) {
				log.error("此护士不是责任护士");
				throw new Exception("此护士不是责任护士");
			}*/
			//通过药品rfid查询用药信息 -->只可能查询到一条记录（暂不修改，保证可行性）
			List<DispensingDrug> dispensingDrugList = drugService.getDispensingDrugByRfidId(medicalSet.iterator().next());
			log.info("查询到的用药信息为：【{}】", dispensingDrugList);
			Integer dispensingDrugId = 0;
			if (!ObjectUtils.isEmpty(dispensingDrugList)) {
				boolean isLegalMedicine = false;
				for (DispensingDrug dispensingDrug : dispensingDrugList) {
					if (patient.getPatientId().equals(dispensingDrug.getPatientId())) {
						dispensingDrugId = dispensingDrug.getId();
						isLegalMedicine = true;
						break;
					}
				}
				if (!isLegalMedicine) {
					log.error("此药物不属于该病人");
					throw new Exception("此药物不属于该病人");
				}
			} else {
				log.error("此病人无待用医嘱");
				throw new Exception("此病人无待用医嘱");
			}
			log.info("用药信息为：护士：【{}】, 病人：【{}】, 药品：【{}】", nurseSet, patientSet, medicalSet);
			//为确认用药时才更新状态
			//更新医嘱状态，改为已删除（药品已使用）
			if (scenesDataInput.getDes() != null && "confirm".equals(scenesDataInput.getDes())) {
				drugService.updateDispensingDrugState(dispensingDrugId);
			} else {
				return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS_MESSAGE, ProcessMessageConstant.RFID_PROCESS_OK, "药品校验成功");
			}
		} catch (NullPointerException | IndexOutOfBoundsException ne) {
			//处理因数据问题导致的异常
			log.error("处理错误，发生异常，异常信息为：【{}】", ne.getMessage(), ne);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.MEDICATION_DATA_EXCEPTION, "数据异常，联系维护人员");
		} catch (Exception e) {
			//处理校验不通过的情况，此时的异常时上面主动抛出的
			log.info("处理错误，发生异常，异常信息为：【{}】", e.getMessage());
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.MEDICATION_DATA_EXCEPTION, e.getMessage());
		}

		return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS_MESSAGE, ProcessMessageConstant.RFID_PROCESS_OK, "用药成功");
	}

	/**
	 * 场景一：
	 * 地点：配药室；
	 * 对象：药品MRFID，护士NRFID；
	 * 场景描述：护士到配药室配药，读到了护士NRFID和药品的MRFID；
	 * 方法功能：在该场景下将护士和药品所在的内网IP，Mac，已经对应的RFID保存到表：mac_rfid_user_relation
	 *
	 * @param scenesDataInput
	 * @return
	 */
	@Override
	public ScenesDataReturn pharmacy(ScenesDataInput scenesDataInput) {
		String requestInfo = JSON.toJSONString(scenesDataInput);
		log.info("pharmacy scene request:{}", requestInfo);
		try {
			String ip = scenesDataInput.getIp();
			String macaddress = scenesDataInput.getMac();
			List<RfidData> rfidDataList = scenesDataInput.getData();
			AtomicReference<Integer> isRightFlag = new AtomicReference<>(0);
			for (RfidData rfidData : rfidDataList) {
				MacRfidUserRelation macRfidUserRelation = new MacRfidUserRelation();
				macRfidUserRelation.setRfidTime(LocalDateTime.now());
				macRfidUserRelation.setRfidId(rfidData.getRfid());
				macRfidUserRelation.setRfidType(rfidData.getType().getType());
				macRfidUserRelation.setMacAddress(macaddress);
				macRfidUserRelation.setTerminalIntranetIp(ip);
				//macRfidUserRelation.setRfidTypeEnum(rfidData.getType());
				macRfidUserRelationService.save(macRfidUserRelation);
			}
			return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.RFID_PROCESS_OK, "RFID数据保存成功");
		} catch (Exception e) {
			log.error("pharmacy save exception:", e);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_SAVE_EXCEPTION, "RFID数据保存异常:" + e.getMessage() + "请重刷!");
		}
	}

	@Override
	public ScenesDataReturn medicalWaste(ScenesDataInput scenesDataInput) {
		log.info("medicalWaste recycle move process");
		try {
			//保存上传数据
			return common(scenesDataInput);
		} catch (Exception e) {
			log.info("medicalWaste recycle process exception");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_SAVE_EXCEPTION, "保存失败");
		}
	}

	@Override
	public ScenesDataReturn pMove(ScenesDataInput scenesDataInput) {
		try {
			common(scenesDataInput);
			Set<RfidData> patientSet = new HashSet<>();
			Set<String> bedSet = new HashSet<>();
			for (RfidData rfidData : scenesDataInput.getData()) {
				switch (rfidData.getType()) {
					case P:
						patientSet.add(rfidData);
						break;
					case B:
						bedSet.add(rfidData.getRfid());
						break;
					default:
						log.info("无用数据【{}】，忽略！", rfidData);
				}
			}
			if (patientSet.isEmpty()) {
				return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.MEDICATION_DATA_EXCEPTION, "数据异常，不包含病人信息");
			}
			for (RfidData rfidData : patientSet) {
				//设备到到病床前，更新设备的位置信息
				if (rfidData.getStatus() == 0) {
					//根据设备的rfid去更新位置
					log.info("病人：{}回到病房,病床为：{}", rfidData.getRfid(), bedSet);
				} else {
					//根据设备的rfid去更新位置
					log.info("病人：{}离开病房,病床为：{}", rfidData.getRfid(), bedSet);
					return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_PATINET_LEAVE_EXCEPTION, "病人离开病房");
				}
			}
		} catch (Exception e) {
			log.info("medicine move process exception");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_SAVE_EXCEPTION, "保存失败");
		}
		return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.RFID_PROCESS_OK, "保存成功");
	}

	/**
	 * 场景五
	 * 地点：重症监护室
	 * 对象：药品rfid、护士rfid、病人rfid
	 * 场景描述：用药结束，护士拿着药瓶离开
	 * 方法功能：记录用药记录
	 *
	 * @param scenesDataInput
	 * @return
	 */
	@Override
	public ScenesDataReturn mMove(ScenesDataInput scenesDataInput) {
		log.info("medicine move process");
		try {
			//保存上传数据
			//计算滴速
			return common(scenesDataInput);

		} catch (Exception e) {
			log.info("medicine move process exception");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_SAVE_EXCEPTION, "保存失败");
		}
	}

	/**
	 * 场景十
	 * 地点：重症监护室；
	 * 对象：病床BRFID，设备ERFID；
	 * 场景描述：医疗设备位置变化；
	 * 方法功能：记录设备变化
	 *
	 * @param scenesDataInput
	 * @return
	 */
	@Override
	public ScenesDataReturn equipment(ScenesDataInput scenesDataInput) {
		log.info("equipment change process");
		try {
			//保存记录信息
			common(scenesDataInput);
			//更新设备所在位置信息
			Set<RfidData> BedSet = new HashSet<>();
			Set<RfidData> equipmentSet = new HashSet<>();
			for (RfidData rfidData : scenesDataInput.getData()) {
				switch (rfidData.getType()) {
					case B:
						BedSet.add(rfidData);
						break;
					case E:
						equipmentSet.add(rfidData);
						break;
					default:
						log.info("无用数据【{}】，忽略！", rfidData);
				}
			}
			//!BedSet.isEmpty() 病床必须存在
			if (BedSet.size() != 1) {
				throw new Exception("数据异常，病床只能有一个");
			}
			//根据病床的rfid查询病床id
			PatientBedCorrelation patientBedCorrelation = patientBedCorrelationService.selectByBedRfid(BedSet.iterator().next().getRfid());
			log.info("查询到的病床信息：【{}】", patientBedCorrelation);

			String position = "";
			for (RfidData rfidData : equipmentSet) {
				//设备到到病床前，更新设备的位置信息
				if (rfidData.getStatus() == 0) {
					//根据设备的rfid去更新位置
					position = patientBedCorrelation.getBedId();
				} else {
					//根据设备的rfid去更新位置
					position = null;
				}
				deviceService.updateByERfid(rfidData.getRfid(), position);
			}
		} catch (Exception e) {
			log.error("处理异常，异常信息为：【{}】", e.getMessage(), e);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_EQUIPMENT_UPDATE_EXCEPTION, "设备位置更新失败");
		}
		return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.RFID_PROCESS_OK, "设备位置更新成功");
	}

	@Override
	public ScenesDataReturn collectData(CollectionRecord record) {
		log.info("Collection Record process started");
		try {
			Set<DeviceDataRecord> deviceDataRecordSet = new HashSet<>();
			Set<ProjectRecord> projectRecordSet = new HashSet<>();
			//循环将数据装入set中
			for (String key : record.getData().keySet()) {
				//如果传来的值为- （没有值）,则不存储该字段的数据
				if("-".equals(record.getData().get(key))){
					continue;
				}
				DeviceDataRecord dataRecord = new DeviceDataRecord();
				dataRecord.setDeviceIp(record.getIp());
				dataRecord.setCreateTime(LocalDateTime.now());
				dataRecord.setDeviceRfid(record.getERfid());
				dataRecord.setPatientRfid(record.getPRfid());
				dataRecord.setTimestamp(record.getTimestamp().getTime());
				dataRecord.setParameterCode(key);
				dataRecord.setParameterValue(record.getData().get(key));
				deviceDataRecordSet.add(dataRecord);
			}
			//通过设备rfid获取设备所属的科室
			Device device = deviceService.selectByRfid(record.getERfid());
			log.info("查询到的设备信息为：【{}】", JSON.toJSON(device));
			//获取对应参数的projectId
			List<Parameters> parametersList = parametersService.selectByCode(record.getData().keySet(), device.getDeptId());
			log.info("获取的参数列表为：【{}】", parametersList);
			//通过病人的rfid获取病人id
			Patient patient = patientService.getByPatientRfid(record.getPRfid());
			if (ObjectUtils.isEmpty(patient)) {
				throw new Exception("病人Rfid错误，找不到对应的病人");
			}
			for (Parameters parameters : parametersList) {
				log.info("获取的数据为：【{},{},{}】", parameters.getParameterCode(), parameters.getProjectId(), parameters.getDeptId());
				if("-".equals(record.getData().get(parameters.getParameterCode()))){
					continue;
				}
				//组装nur_project_record数据
				ProjectRecord projectRecord = new ProjectRecord();
				projectRecord.setProjectRecordId(SnowFlake.getId() + "");
				projectRecord.setProjectId(parameters.getProjectId());
				projectRecord.setProjectValue(record.getData().get(parameters.getParameterCode()));
				projectRecord.setSource(DataSourceEnum.DEVICE.getCode());
				projectRecord.setPatientId(patient.getPatientId());
				projectRecord.setDeptId(parameters.getDeptId());
				projectRecord.setCreateTime(LocalDateTime.now());
				projectRecordSet.add(projectRecord);
			}
			log.info("将采集到的的数据插入数据库成功【{}】，共【{}】条", dataRecordService.saveBatch(deviceDataRecordSet), deviceDataRecordSet.size());
			log.info("将采集到的的数据插入数据库成功【{}】，共【{}】条", projectRecordService.saveBatch(projectRecordSet), projectRecordSet.size());
		} catch (NullPointerException | IndexOutOfBoundsException ne) {
			//处理因数据问题导致的异常
			log.error("处理错误，发生异常，异常信息为：【{}】", ne.getMessage(), ne);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.MEDICATION_DATA_EXCEPTION, "数据异常，联系维护人员");
		} catch (Exception e) {
			//处理校验不通过的情况，此时的异常时上面主动抛出的
			log.info("处理错误，发生异常，异常信息为：【{}】", e.getMessage(), e);
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.MEDICATION_DATA_EXCEPTION, e.getMessage());
		}

		return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.RFID_PROCESS_OK, "设备数据保存成功");
	}
}
