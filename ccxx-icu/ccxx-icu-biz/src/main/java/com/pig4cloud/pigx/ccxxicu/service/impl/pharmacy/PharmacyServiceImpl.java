package com.pig4cloud.pigx.ccxxicu.service.impl.pharmacy;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceDictionaries;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.newlogin.MacRfidUserRelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo.NursePatientCorrelationVo;
import com.pig4cloud.pigx.ccxxicu.common.exception.BusinessException;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.DoctorsAdviceDictionariesMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisDoctorsAdviceMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisDoctorsAdviceProjectMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.newlogin.MacRfidUserRelationMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.DispensingDrugMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NurseMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TaskMapper;
import com.pig4cloud.pigx.ccxxicu.service.nurse.DispensingDrugService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NursePatientCorrelationService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.pharmacy.PharmacyService;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PharmacyServiceImpl implements PharmacyService {

	//ip,mac,rfid的对应关系表
	@Resource
	private MacRfidUserRelationMapper macRfidUserRelationMapper;

	//根据护士RFID获取护士的ID
	@Autowired
	private NurseMapper nurseMapper;

	//根据护士ID获取患者列表
	@Autowired
	private NursePatientCorrelationService nursePatientCorrelationService;

	//根据患者ID获取医嘱列表
	@Autowired
	private HisDoctorsAdviceMapper hisDoctorsAdviceMapper;

	//根据批次ID获取医嘱详细信息
	@Autowired
	private HisDoctorsAdviceProjectMapper hisDoctorsAdviceProjectMapper;

	//根据病人的ID获取任务列表
	@Autowired
	private TaskMapper taskMapper;

	//根据任务获取医嘱列表
	@Autowired
	private DispensingDrugMapper dispensingDrugMapper;

	@Autowired
	private DoctorsAdviceDictionariesMapper doctorsAdviceDictionariesMapper;

	@Autowired
	private DispensingDrugService dispensingDrugService;

	/**
	 * 患者
	 */
	@Autowired
	private PatientService patientService;

	public static String enum_type = "2";//获取枚举值的类型参数为2

	public static int PARAM_EXCEPTION = 5007;//


	/**
	 * 配药室的卡片电脑:将IP地址，Mac地址，RFID类型，药品空白标签RFID保存到数据库表：mac_rfid_user_relation中
	 *
	 * @param ipAddress
	 * @param macAddress
	 * @param rfidType
	 * @param rfidValue
	 * @return
	 */
	@Override
	public boolean saveAppParams(String ipAddress, String macAddress, String rfidType, String rfidValue) {
		if (StringUtils.isNotEmpty(ipAddress) && StringUtils.isNotEmpty(macAddress) &&
				StringUtils.isNotEmpty(rfidType) && StringUtils.isNotEmpty(rfidValue)) {
			//数据落库，返回结果状态值
			try {
				MacRfidUserRelation macRfidUserRelation = new MacRfidUserRelation();
				macRfidUserRelation.setTerminalIntranetIp(ipAddress);
				macRfidUserRelation.setMacAddress(macAddress);
				macRfidUserRelation.setRfidType(rfidType);
				macRfidUserRelation.setRfidId(rfidValue);
				macRfidUserRelation.setRfidTime(LocalDateTime.now());
				macRfidUserRelationMapper.insert(macRfidUserRelation);
				return true;
			} catch (Exception e) {
				log.error("app 保存IP地址，Mac地址及标签信息失败，失败原因:", e);
			}
		}
		return false;
	}

	/**
	 * chrome浏览器根据IP地址获取护士标签RFID
	 * 第二步：根据标签RFID来获取护士的ID
	 *
	 * @param ipAddress
	 * @return mRfid
	 */
	@Override
	public String getNurseIdByRfidByIp(String ipAddress) {
		String nurseId = "";
		try {
			//第一步：浏览器根据IP地址获取护士的标签RFID
			String nurseRfid = getNurseRfidByIp(ipAddress);
			if (StringUtils.isEmpty(nurseRfid)) {
				return nurseId;
			}
			//第二步：根据护士的标签RFID来获取护士的ID
			nurseId = getNurseIdByNurserfid(nurseRfid);
		} catch (Exception e) {
			log.error("浏览器根据IP地址获取护士的标签ID及护士的ID出现异常.", e);
		}
		log.info("根据IP地址获取护士RFID，再获取护士的nurseId:" + nurseId);
		return nurseId;
	}

	/**
	 * 第一步：根据护士ID获取患者列表
	 *
	 * @param nurseId
	 * @return
	 */
	@Override
	public List<Patient> getPatientListByNurseId(String nurseId) {
		List<Patient> patients = Lists.newArrayList();
		if (StringUtils.isEmpty(nurseId)) {
			return patients;
		}

		NursePatientCorrelation nursePatientCorrelation = new NursePatientCorrelation();
		nursePatientCorrelation.setNurseId(nurseId);
		try {
			//查询表：nur_nurse_patient_correlation 获取病人列表信息
			List<NursePatientCorrelationVo> list = nursePatientCorrelationService.selectAll(nursePatientCorrelation);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NursePatientCorrelationVo item : list) {
					patients.add(item.getPatient());
				}
			}
		} catch (Exception e) {
			log.error("根据护士ID获取患者列表出现异常：", e);
		}
		log.info("根据护士ID获取患者列表" + JSON.toJSONString(patients));
		return patients;
	}

	/**
	 * 第2.1步：根据患者ID获取任务列表，表：tak_task
	 *
	 * @param patientId
	 * @return
	 */
	@Override
	public Map<Task, HisDoctorsAdvice> getTaskListByPatientId(String patientId) {
		Map<Task, HisDoctorsAdvice> map = new HashMap<>();
		List<Task> taskList = Lists.newArrayList();
		if (StringUtils.isEmpty(patientId)) {
			log.info("根据病人获取任务列表的请求参数为空");
			return map;
		}
		try {
			taskList = taskMapper.getAdviceTaskByPatientId(patientId);
			log.info("根据患者ID{}获取任务列表:{}", patientId, JSON.toJSONString(taskList));

			if (CollectionUtils.isNotEmpty(taskList)) {
				for (Task task : taskList) {
					//task.getSourceItems()==1表示任务类型为医嘱，则task.getSourceItemsId()表示批次号(确认人：刘伟)
					String batchNumber = task.getSourceItemsId();
					if (StringUtils.isNotEmpty(batchNumber)) {
						HisDoctorsAdvice hisDoctorsAdvice = hisDoctorsAdviceMapper.getBatchNumber(batchNumber);
						if (hisDoctorsAdvice != null) {
							map.put(task, hisDoctorsAdvice);
						}
					}
					//根据任务获取获取医嘱ID（一个任务对应一个医嘱ID）
//					List<DispensingDrug> dispensingDrugList  = dispensingDrugMapper.getDispensingDrugByTaskId(task.getTaskId());
//					log.info("根据任务{}获取医嘱信息:{}",task.getTaskId(), JSON.toJSONString(dispensingDrugList));
//					if (CollectionUtils.isNotEmpty(dispensingDrugList)){
//						DispensingDrug dispensingDrug = dispensingDrugList.get(0);
//						HisDoctorsAdvice hisDoctorsAdvice = hisDoctorsAdviceMapper.getBatchNumberByAdviceId(dispensingDrug.getDoctorsAdviceId());
//						map.put(task,hisDoctorsAdvice);
//					}
				}
			}
		} catch (Exception e) {
			log.error("根据病人ID获取任务列表异常:", e);
		}
		log.info("根据患者ID获取对应的任务和医嘱信息" + JSON.toJSONString(map));
		return map;
	}

	/**
	 * 第2.2步：根据护理任务获取医嘱ID，表：nur_dispensing_drug
	 *
	 * @param taskId
	 * @return
	 */
	@Override
	public List<DispensingDrug> getDoctorAdviceListByTaskId(String taskId) {
		List<DispensingDrug> dispensingDrugList = Lists.newArrayList();
		if (StringUtils.isEmpty(taskId)) {
			log.info("根据任务获取医嘱ID列表的任务ID为空");
			return dispensingDrugList;
		}
		try {
			dispensingDrugList = dispensingDrugMapper.getDispensingDrugByTaskId(taskId);
		} catch (Exception e) {
			log.error("根据任务ID获取医嘱列表信息异常", e);
		}
		log.info("根据护理任务获取医嘱ID" + JSON.toJSONString(dispensingDrugList));
		return dispensingDrugList;
	}

	/**
	 * 第2.3步：根据医嘱ID获取医嘱批次号，表：his_doctors_advice
	 *
	 * @param doctorAdviceId
	 * @return
	 */
	@Override
	public List<HisDoctorsAdvice> getBatchNumberByDoctorAdviceId(String doctorAdviceId) {
		List<HisDoctorsAdvice> hisDoctorsAdviceList = Lists.newArrayList();
		if (StringUtils.isEmpty(doctorAdviceId)) {
			log.info("根据医嘱ID获取医嘱批次号的请求参数为空");
			return hisDoctorsAdviceList;
		}
		try {
			HisDoctorsAdvice hisDoctorsAdvice = hisDoctorsAdviceMapper.getBatchNumberByAdviceId(doctorAdviceId);
			hisDoctorsAdviceList.add(hisDoctorsAdvice);
		} catch (Exception e) {
			log.error("根据医嘱ID获取医嘱批次号请求异常", e);
		}
		log.info("根据医嘱ID获取医嘱批次号" + JSON.toJSONString(hisDoctorsAdviceList));
		return hisDoctorsAdviceList;
	}

	/**
	 * 第二步：根据患者ID获取医生的医嘱id列表-表：his_doctors_advice
	 *
	 * @param patientid
	 * @return
	 */
	@Override
	public List<HisDoctorsAdvice> getDoctorAdviceListByPatientId(String patientid) {
		List<HisDoctorsAdvice> hisDoctorsAdviceList = Lists.newArrayList();
		if (StringUtils.isEmpty(patientid)) {
			return hisDoctorsAdviceList;
		}
		try {
			hisDoctorsAdviceList = hisDoctorsAdviceMapper.getHisDoctorAdviceListByPatientId(patientid);
		} catch (Exception e) {
			log.error("根据患者ID获取医生的医嘱id列表异常:", e);
		}
		return hisDoctorsAdviceList;
	}

	/**
	 * 第三步：根据批次号（医嘱ID）获取医嘱详情-表：his_doctors_advice_project
	 *
	 * @param batchNumber
	 * @return
	 */
	@Override
	public List<HisDoctorsAdviceProject> getAdviceDetailByBatchNumber(String batchNumber) {
		List<HisDoctorsAdviceProject> adviceProjectList = Lists.newArrayList();
		if (StringUtils.isEmpty(batchNumber)) {
			return adviceProjectList;
		}
		try {
			adviceProjectList = hisDoctorsAdviceProjectMapper.selectBatchNumber(batchNumber);
		} catch (Exception e) {
			log.error("根据批次号获取医嘱详细异常:", e);
		}
		log.info("根据批次号（医嘱ID）获取医嘱详情:" + JSON.toJSONString(adviceProjectList));
		return adviceProjectList;
	}

	/**
	 * 浏览器根据IP地址获取护士的标签RFID
	 *
	 * @param ipAddress
	 * @return
	 */
	private String getNurseRfidByIp(String ipAddress) {
		MacRfidUserRelation macRfidUserRelation = macRfidUserRelationMapper.selectRfidByIp(ipAddress);
		log.info("根据IP地址获取护士的标签RFID的结果:" + JSON.toJSONString(macRfidUserRelation));
		if (macRfidUserRelation == null) {
			return "";
		}
		return macRfidUserRelation.getRfidId();
	}

	/**
	 * 根据护士RFID获取护士的ID
	 *
	 * @param nurseRfid
	 * @return
	 */
	private String getNurseIdByNurserfid(String nurseRfid) {
		String nurseId = "";
		List<String> rfidList = Lists.newArrayList();
		rfidList.add(nurseRfid);
		List<Nurse> nurseList = nurseMapper.selectNurseUserIdByRfid(rfidList);
		if (CollectionUtils.isNotEmpty(nurseList)) {
			nurseId = nurseList.get(0).getUserId();
		}
		return nurseId;
	}

	/**
	 * 护士核对信息后需要将信息保存到表：nur_dispensing_drug
	 *
	 * @param dispensingDrug
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveDispensingDrugInfo(DispensingDrug dispensingDrug) {
		try{
			log.info("药物核对保存信息为:{}", JSON.toJSONString(dispensingDrug));
			checkParam(dispensingDrug);
			return saveDispensingDrugInfos(dispensingDrug);
		}catch (Exception e){
			log.error("核对药物页面出现异常:", e);
		}
		return false;
	}

	/**
	 * 核对药物页面关键参数校验
	 * @param dispensingDrug
	 */
	private void checkParam(DispensingDrug dispensingDrug){
		if (dispensingDrug == null){
			throw new BusinessException(PARAM_EXCEPTION,"核对药物对象为空");
		}
		if (dispensingDrug.getDoctorsAdviceId() == null){
			throw new BusinessException(PARAM_EXCEPTION,"医嘱ID为空");
		}
		if (dispensingDrug.getNurseId() == null){
			throw new BusinessException(PARAM_EXCEPTION,"护士ID为空");
		}
		if (dispensingDrug.getPatientId() == null){
			throw new BusinessException(PARAM_EXCEPTION,"病人ID为空");

		}
		if (dispensingDrug.getTaskId() == null){
			throw new BusinessException(PARAM_EXCEPTION,"任务ID为空");
		}
		if (dispensingDrug.getDeptId() == null){
			throw new BusinessException(PARAM_EXCEPTION,"科室ID为空");
		}
	}

	/**
	 * 核对药物页面的保存逻辑
	 * @param dispensingDrug
	 * @return
	 */
	private boolean saveDispensingDrugInfos(DispensingDrug dispensingDrug){
		dispensingDrug.setDispensingDrugId(SnowFlake.getId() + ""); //雪花
		log.info("配药页面药物核对后保存的信息:{}", JSON.toJSONString(dispensingDrug));
		String allergichistory = dispensingDrug.getAllergichistory();//过敏史
		if (!allergichistory.equals("") || allergichistory != null || !allergichistory.equals("无")) {
			Patient byPatientId = patientService.getByPatientId(dispensingDrug.getPatientId());
			log.info("药物核对中对应的病人信息为:{}", JSON.toJSONString(byPatientId));
			byPatientId.setAllergichistory(allergichistory);
			boolean res = patientService.updateById(byPatientId);
			log.info("药物核对中对应病人信息更新过敏史(true成功，false失败):{}", res);
		}
		//将预执行实现保存到任务表（tak_task）的字段pre_start_time中；
		Task task = taskMapper.getTaskByTaskId(dispensingDrug.getTaskId());
		log.info("药物核对对应的任务信息为:{}", JSON.toJSONString(task));
		task.setPreStartTime(dispensingDrug.getCreateTime());
		taskMapper.updateById(task);
		dispensingDrug.setCreateTime(LocalDateTime.now());
		//将过敏史更新到病人列表中
		log.info("药物核对对应的保存的核对信息为:{}", JSON.toJSONString(dispensingDrug));
		boolean resSave = dispensingDrugService.save(dispensingDrug);
		log.info("药物核对对应的保存的核对信息结果为:{},(true成功，false失败)", resSave);
		return resSave;
	}

	@Override
	public String getRfidByipAddress(String ipAddress, String rfidType) {
		log.info("获取IP地址为:{},且请求的RFID值为:{}", ipAddress, rfidType);
		String rfid = "";
		try {
			if (StringUtils.isNotEmpty(ipAddress) && StringUtils.isNotEmpty(rfidType)) {
				MacRfidUserRelation macRfidUserRelation = macRfidUserRelationMapper.selectRfidByIpAndType(ipAddress, rfidType);
				log.info("根据IP地址和NRFIDTYPE获取护士的标签RFID的结果:" + JSON.toJSONString(macRfidUserRelation));
				if (macRfidUserRelation != null) {
					return rfid = macRfidUserRelation.getRfidId();
				}
			}
		} catch (Exception e) {
			log.error("根据IP地址和RFID的类型获取RFID的值异常", e);
		}
		return rfid;
	}

	@Override
	public List<DoctorsAdviceDictionaries> getLabelNameValueEnums() {
		List<DoctorsAdviceDictionaries> docAdvDictionariesList = Lists.newArrayList();
		try {
			docAdvDictionariesList = doctorsAdviceDictionariesMapper.getLabelNameValueEnumByTypeValue(enum_type);
		} catch (Exception e) {
			log.error("获取用药途径枚举值异常", e);
		}
		log.info("获取用药途径枚举值的结果为:{}", JSON.toJSONString(docAdvDictionariesList));
		return docAdvDictionariesList;
	}

}
