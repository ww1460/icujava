package com.pig4cloud.pigx.ccxxicu.service.pharmacy;

import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.DoctorsAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.DoctorsAdviceDictionaries;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceProject;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;

import java.util.List;
import java.util.Map;

/**
 * 配药室交互接口
 */
public interface PharmacyService {

	/**
	 * 配药室的卡片电脑:将IP地址，Mac地址，RFID类型，药品空白标签RFID保存到数据库表：mac_rfid_user_relation中
	 * @param ipAddress
	 * @param macAddress
	 * @param rfidType
	 * @param rfidValue
	 * @return
	 */
	boolean saveAppParams(String ipAddress, String macAddress, String rfidType, String rfidValue);

	/**
	 * 第一步：chrome浏览器根据IP地址获取护士标签RFID
	 * 第二步：根据标签RFID来获取护士的ID
	 * @param ipAddress
	 * @return mRfid
	 */
	String getNurseIdByRfidByIp(String ipAddress);

	/**
	 * 第一步：根据护士ID获取患者列表
	 * @param nurseId
	 * @return
	 */
	List<Patient> getPatientListByNurseId(String nurseId);

	/**
	 * 第2.1步：根据患者ID获取任务列表，表：tak_task
	 * @param patientId
	 * @return
	 */
	Map<Task,HisDoctorsAdvice> getTaskListByPatientId(String patientId);

	/**
	 * 第2.2步：根据护理任务获取医嘱ID，表：nur_dispensing_drug
	 * @param taskId
	 * @return
	 */
	List<DispensingDrug> getDoctorAdviceListByTaskId(String taskId);

	/**
	 * 第2.3步：根据医嘱ID获取医嘱批次号，表：his_doctors_advice
	 * @param adviceId
	 * @return
	 */
	List<HisDoctorsAdvice> getBatchNumberByDoctorAdviceId(String adviceId);

	/**
	 * 第二步：根据患者ID获取医生的医嘱id（详细信息）列表-表：his_doctors_advice
	 * @param patientid
	 * @return
	 */
	List<HisDoctorsAdvice> getDoctorAdviceListByPatientId(String patientid);

	/**
	 * 第三步：批次号和医嘱ID是一一对应的关系(根据医嘱ID获取批次号)
	 * @param batchNumber
	 * @return
	 */
	List<HisDoctorsAdviceProject> getAdviceDetailByBatchNumber(String batchNumber);

	/**
	 * 将药品空白标签RFID和医嘱ID绑定并保存在表：nur_dispensing_drug
	 * @param dispensingDrug
	 */
	boolean saveDispensingDrugInfo(DispensingDrug dispensingDrug);


	/**
	 * 通用接口：根据IP地址获取各类的RFID
	 * @param ipAddress
	 * @return mRfid
	 */
	String getRfidByipAddress(String ipAddress, String rfidType);

	/**
	 *
	 * @return
	 */
	List<DoctorsAdviceDictionaries> getLabelNameValueEnums();
}
