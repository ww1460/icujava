package com.pig4cloud.pigx.ccxxicu.service.impl.datav;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Maps;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataKeyObject;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataVCatalogData;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataVEarlyWarning;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataVRingChartData;
import com.pig4cloud.pigx.ccxxicu.api.entity.device.Device;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.NursePatientCorrelation;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.IcuRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.EarlyWarning;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.Task;
import com.pig4cloud.pigx.ccxxicu.api.vo.patient.PatientVo;
import com.pig4cloud.pigx.ccxxicu.mapper.bed.HospitalBedMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.device.DeviceMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.hisdata.HisDoctorsAdviceMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NurseMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nurse.NursePatientCorrelationMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.nursing.NursingRecordMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.patient.IcuRecordMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.patient.PatientMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.piping.PipingMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.task.EarlyWarningMapper;
import com.pig4cloud.pigx.ccxxicu.mapper.task.TaskMapper;
import com.pig4cloud.pigx.ccxxicu.service.datav.DataVService;
import com.pig4cloud.pigx.common.core.constant.DataVConstants;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DataVServiceImpl implements DataVService {

	@Autowired
	private NursePatientCorrelationMapper nursePatientCorrelationMapper;

	@Autowired
	private PatientMapper patientMapper;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private NursingRecordMapper nursingRecordMapper;

	@Autowired
	private DeviceMapper deviceMapper;

	@Autowired
	private PipingMapper pipingMapper;

	@Autowired
	private HisDoctorsAdviceMapper hisDoctorsAdviceMapper;

	@Autowired
	private EarlyWarningMapper earlyWarningMapper;

	@Autowired
	private NurseMapper nurseMapper;

	@Autowired
	private HospitalBedMapper hospitalBedMapper;

	@Autowired
	private IcuRecordMapper icuRecordMapper;


	/**
	 * 关键字数据
	 * 1、在线护士数
	 * //2、在线床位数
	 * 3、在线患者数（等价于在线床位数）
	 * 4、任务数据
	 * 5、护理数据
	 * 6、设备数据
	 * 7、管路数据
	 * 8、出科数据
	 * 9、医嘱数据
	 *
	 * @return
	 */
	@Override
	public List<DataKeyObject> keyData() {
//		Map<String, String> keyMap = getKeyData();
//		keyMap = getKeyData();
		List<DataKeyObject> list= getKeyData();
		if (CollectionUtils.isNotEmpty(list)){
			return list;
		}
		return null;
	}

	/**
	 * 滚动图数据获取预警信息-患者床号，患者名字，护士名字，入院时间（time_of_admission），预警时间，预警原因
	 *
	 * @return
	 */
	@Override
	public List<DataVEarlyWarning> scrollingData() {
		List<DataVEarlyWarning> dataVEarlyWarningList = Lists.newArrayList();
		List<EarlyWarning> list = Lists.newArrayList();
		try {
			list = earlyWarningMapper.scrollingData();
			for (EarlyWarning e : list){
				DataVEarlyWarning early = getEarlyWarning(e);
				dataVEarlyWarningList.add(early);
			}
		} catch (Exception e) {
			log.error("滚动图获取预警信息异常:", e);
		}
		return dataVEarlyWarningList;
	}

	private DataVEarlyWarning getEarlyWarning(EarlyWarning e){
		log.info("预警查询数据信息:"+ JSON.toJSONString(e));
		DataVEarlyWarning early = new DataVEarlyWarning();
		PatientVo patientVo = patientMapper.patientIdSelect(e.getPatientId());
		early.setBedNumber(patientVo.getAdmissionBeds());
		early.setPatientName(patientVo.getName());//病人表
		early.setAdmissionTime(patientVo.getTimeOfAdmission());
		early.setWarningTime(e.getTriggerTime());
		early.setWarningReason(e.getReason());
		if (StringUtils.isNotEmpty(e.getNurseId())){
			Nurse nurse = nurseMapper.selectByUserId(e.getNurseId());
			early.setNurseName(nurse.getRealName());//护士表
		}
		return early;
	}

	/**
	 * 环形图数据信息
	 * 护士数据，患者数据，病床数据，任务数据，出入科数据
	 * @return
	 */
	@Override
	public List<DataVRingChartData> ringData() {
		List<DataVRingChartData> list = Lists.newArrayList();
		try {
			//获取护士数据
			getRingDataForNurse(list);
			//患者数据
			getRingDataForAllPatient(list);
			//病床数据
			getRingDataForBed(list);
			//任务数据
			getRingDataForTask(list);
			//出入科数据
			getRingDataForPatient(list);
		}catch (Exception e){
			log.error("环形数据大屏获取数据异常:", e);
		}
		return list;
	}

	/**
	 * 目录栏数据
	 * 患者今日入科人数
	 * 患者今日出科人数
	 * 今日出院人数
	 * 今日转科人数
	 * 今日死亡人数
	 * 留置导尿管患者人数
	 * 动静脉插管患者人数
	 * 使用呼吸机患者人数
	 * @return
	 */
	@Override
	public List<DataVCatalogData> catalogData() {
		List<DataVCatalogData> list = Lists.newArrayList();
		try {
			//今日入科人数
			getCatalogBuildNumber(list);
			//今日出科人数(出院人数，转科人数，死亡人数）
			getCatalogDischargeNumber(list);
			//留置导尿管患者人数,动静脉插管患者人数,使用呼吸机患者人数
			getCatalogCatheter(list);
		}catch (Exception e){
			log.error("目录栏数据获取异常:", e);
		}
		return list;
	}

	/**
	 * 目录栏数据-今日入科人数
	 * @param list
	 * @return
	 */
	private List<DataVCatalogData> getCatalogBuildNumber(List<DataVCatalogData> list){
		//今日入科人数
		Integer builNumber = patientMapper.getTodayBuildPatientNumber();
		DataVCatalogData dataVCatalogData = new DataVCatalogData();
		dataVCatalogData.setCatalogName(DataVConstants.CATALOG_TODAY_BUILD);
		if (builNumber != 0){
			dataVCatalogData.setCatalogData(builNumber+"");
		}else {
			dataVCatalogData.setCatalogData("0");
		}
		list.add(dataVCatalogData);
		return list;
	}

	/**
	 * 目录栏数据-今日出科人数(出院人数，转科人数，死亡人数）
	 * @param list
	 * @return
	 */
	private List<DataVCatalogData> getCatalogDischargeNumber(List<DataVCatalogData> list){
		List<PatientVo>  dischargeList = patientMapper.getDischargeNumber();

		Integer dischargerNumber = dischargeList.size();//总转科人数
		Integer transferNumber = 0;
		Integer leaveNumber = 0;
		Integer deathNumber = 0;
		for(PatientVo patientVo : dischargeList) {
			String dischargeType = patientVo.getDischargeType();
			if ("1".equals(dischargeType)){//转科
				transferNumber++;
			}else if ("2".equals(dischargeType)){//出院
				leaveNumber++;
			}else if ("3".equals(dischargeType)){//死亡
				deathNumber++;
			}
		}
		DataVCatalogData data = new DataVCatalogData();
		data.setCatalogName(DataVConstants.CATALOG_TODAY_DISCHARGE);
		data.setCatalogData(dischargerNumber+"");
		list.add(data);

		DataVCatalogData data1 = new DataVCatalogData();
		data1.setCatalogName(DataVConstants.CATALOG_TODAY_TRANSFER);
		data1.setCatalogData(transferNumber+"");
		list.add(data1);

		DataVCatalogData data2 = new DataVCatalogData();
		data2.setCatalogName(DataVConstants.CATALOG_TODAY_LEAVE);
		data2.setCatalogData(leaveNumber+"");
		list.add(data2);

		DataVCatalogData data3 = new DataVCatalogData();
		data3.setCatalogName(DataVConstants.CATALOG_TODAY_DEATH);
		data3.setCatalogData(deathNumber+"");
		list.add(data3);
		return list;
	}

	/**
	 * 目录栏数据-留置导尿管患者人数,动静脉插管患者人数,使用呼吸机患者人数
	 * @param list
	 * @return
	 */
	private List<DataVCatalogData> getCatalogCatheter(List<DataVCatalogData> list){
		List<IcuRecord> icuRecordList = icuRecordMapper.getIcuRecordForCatalog();

		Integer catheter = 0;//留置导尿管数量
		Integer cannula = 0;//动静脉插管数量
		Integer res = 0;//呼吸机使用数量
		for (IcuRecord icuRecord : icuRecordList){
			//indwelling_catheter_patient-留置导尿管
			catheter =+ icuRecord.getIndwellingCatheterPatient();
			//arteriovenou_patient-动静脉插管
			cannula =+ icuRecord.getArteriovenouPatient();
			//respirator_use_patient-呼吸机患者
			res =+ icuRecord.getRespiratorUsePatient();
		}
		DataVCatalogData data = new DataVCatalogData();
		data.setCatalogName(DataVConstants.CATALOG_TODAY_CATHETER);
		data.setCatalogData(catheter+"");
		list.add(data);

		DataVCatalogData data1 = new DataVCatalogData();
		data1.setCatalogName(DataVConstants.CATALOG_TODAY_CANNULA);
		data1.setCatalogData(cannula+"");
		list.add(data1);

		DataVCatalogData data2 = new DataVCatalogData();
		data2.setCatalogName(DataVConstants.CATALOG_TODAY_RES);
		data2.setCatalogData(res+"");
		list.add(data2);
		return list;
	}




	/**
	 * 环形图获取护士的数据信息
	 *
	 * @param list
	 * @return
	 */
	private List<DataVRingChartData> getRingDataForNurse(List<DataVRingChartData> list) {
		List<Nurse> totalLists = nurseMapper.selectAllNurseNumber();
		List<NursePatientCorrelation> subLists = nursePatientCorrelationMapper.selectOnlineNurse();
		builRingDataObject(list, DataVConstants.RINGDATA_NURSE, DataVConstants.RINGDATA_NURSE_ALL, totalLists.size(),
				DataVConstants.RINGDATA_NURSE_ONLINE, subLists.size(), DataVConstants.RINGDATA_UNIT_VISITING);

		return list;
	}

	/**
	 * 环形图获取总入科数（该数据指的是：所有入科人数），在科数
	 *
	 * @param list
	 * @return
	 */
	private List<DataVRingChartData> getRingDataForAllPatient(List<DataVRingChartData> list) {
		Integer totalListNumber = patientMapper.getAllPatient();
		List<PatientVo> subLists = patientMapper.getOnlinePatient();
		builRingDataObject(list, DataVConstants.RINGDATA_DISCHARGE, DataVConstants.RINGDATA_DISCHARGE_ALL, totalListNumber,
				DataVConstants.RINGDATA_DISCHARGE_ONLINE, subLists.size(),DataVConstants.RINGDATA_UNIT_VISITING);
		return list;
	}

	/**
	 * 环形图获取所有病床数，及在线病床数
	 *
	 * @param list
	 * @return
	 */
	private List<DataVRingChartData> getRingDataForBed(List<DataVRingChartData> list) {
		Integer totalListNumber = hospitalBedMapper.selectAllBedNumber();
		List<PatientVo> subLists = patientMapper.getOnlinePatient();//经过讨论确认病人数和病床数是一致的（重症系统）
		builRingDataObject(list, DataVConstants.RINGDATA_BED, DataVConstants.RINGDATA_BED_ALL, totalListNumber,
				DataVConstants.RINGDATA_BED_ONLINE, subLists.size(), DataVConstants.RINGDATA_UNIT_BED);
		return list;
	}

	/**
	 * 环形图获取任务数（当日创建任务数，当日完成创建任务数）
	 *
	 * @param list
	 * @return
	 */
	private List<DataVRingChartData> getRingDataForTask(List<DataVRingChartData> list) {
		Integer totalListNumber = taskMapper.getTodayCreateTask();
		Integer subListNumber = taskMapper.getTodayCreateOverTask();
		builRingDataObject(list, DataVConstants.RINGDATA_TASK, DataVConstants.RINGDATA_TASK_ALL, totalListNumber,
				DataVConstants.RINGDATA_TASK_OVER, subListNumber, DataVConstants.RINGDATA_UNIT_STRIP);
		return list;
	}

	/**
	 * 环形图获取今日出入科人数（当日入科人数，当日出科人数）
	 *
	 * @param list
	 * @return
	 */
	private List<DataVRingChartData> getRingDataForPatient(List<DataVRingChartData> list) {
		Integer totalListNumber = patientMapper.getTodayBuildPatientNumber();
		Integer subListNumber = patientMapper.getTodayBuildPatientAndDischargeNumber();
		builRingDataObject(list, DataVConstants.RINGDATA_PATIENT, DataVConstants.RINGDATA_PATIENT_BUILD, totalListNumber,
				DataVConstants.RINGDATA_PATIENT_DISCHARGE, subListNumber, DataVConstants.RINGDATA_UNIT_VISITING);
		return list;
	}

	/**
	 * 构建环形数据前相关参数的统计计算
	 * @param list
	 * @param ringName
	 * @param totalName
	 * @param totalNumber
	 * @param subName
	 * @param subNumber
	 * @return
	 */
	private List<DataVRingChartData> builRingDataObject(List<DataVRingChartData> list, String ringName, String totalName, Integer totalNumber, String subName, Integer subNumber, String unit) {
		String percent;
		Double value = 0.0;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(0);//控制保留小数点后几位，2：表示保留2位小数点
		percent = nf.format(value);
		if (!"0".equals(totalNumber+"")) {
			value = subNumber * 1.0 / totalNumber;
			percent = nf.format(value);
			buildDataVRingChartData(list, ringName, totalName, totalNumber + "", subName, subNumber + "", percent, unit);
		} else {
			buildDataVRingChartData(list, ringName, totalName, totalNumber + "", subName,"0", percent, unit);
		}
		return list;
	}

	/**
	 * 构建环形数据返回对象
	 *
	 * @param list
	 * @param ringName
	 * @param totalName
	 * @param totalNumber
	 * @param subName
	 * @param subNumber
	 * @param ratio
	 * @return
	 */
	private static List<DataVRingChartData> buildDataVRingChartData(List<DataVRingChartData> list, String ringName, String totalName,
															 String totalNumber, String subName, String subNumber, String ratio, String unit) {
		DataVRingChartData dataVRingChartData = new DataVRingChartData();
		dataVRingChartData.setRingName(ringName);
		dataVRingChartData.setTotalName(totalName);
		dataVRingChartData.setTotalNumber(totalNumber);
		dataVRingChartData.setSubName(subName);
		dataVRingChartData.setSubNumber(subNumber);
		dataVRingChartData.setRatio(ratio);
		dataVRingChartData.setUnit(unit);
		list.add(dataVRingChartData);
		return list;
	}
	/**
	 * 获取关键数据方法
	 *
	 * @return
	 */
	private List<DataKeyObject> getKeyData() {
		//Map<String, String> mapResult = Maps.newHashMap();
		List<DataKeyObject> dataKeyObjectList = Lists.newArrayList();
		getOnlineNurse(dataKeyObjectList);
		getOnlinePatient(dataKeyObjectList);
		getOnlineTask(dataKeyObjectList);
		getOnlineNursing(dataKeyObjectList);
		getOnlineDevice(dataKeyObjectList);
		getOnlinePiping(dataKeyObjectList);
		getDischargeNumber(dataKeyObjectList);
		getDoctorsAdviceNumber(dataKeyObjectList);
		return dataKeyObjectList;
	}

	/**
	 * 获取在线护士数据
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getOnlineNurse(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线护士数");
		dataKeyObject.setUnit("名");
		dataKeyObject.setSortValue(1);
		try {
			List<NursePatientCorrelation> nursePatientCorrelationList = nursePatientCorrelationMapper.selectOnlineNurse();
			if (CollectionUtils.isNotEmpty(nursePatientCorrelationList)) {
				dataKeyObject.setNumber(nursePatientCorrelationList.size());
				//map.put("在线护士数", nursePatientCorrelationList.size() + "");
			} else {
				dataKeyObject.setNumber(0);
				//map.put("在线护士数", "0");
			}
		} catch (Exception e) {
			log.error("在线护士数查询异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 获取在线患者数据
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getOnlinePatient(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线患者数");
		dataKeyObject.setUnit("名");
		dataKeyObject.setSortValue(2);
		try {
			List<PatientVo> patientVoList = patientMapper.getOnlinePatient();
			if (CollectionUtils.isNotEmpty(patientVoList)) {
				dataKeyObject.setNumber(patientVoList.size());
				//map.put("在线患者数", patientVoList.size() + "");
			} else {
				dataKeyObject.setNumber(0);
				//map.put("在线患者数", "0");
			}
		} catch (Exception e) {
			log.error("在线患者数统计查询异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 获取在线任务数
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getOnlineTask(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线任务数");
		dataKeyObject.setUnit("个");
		dataKeyObject.setSortValue(3);
		try {
			List<Task> taskList = taskMapper.getOnlineTask();
			if (CollectionUtils.isNotEmpty(taskList)) {
				dataKeyObject.setNumber(taskList.size());
				//map.put("在线任务数", taskList.size() + "");
			} else {
				dataKeyObject.setNumber(0);
				//map.put("在线任务数", "0");
			}
		} catch (Exception e) {
			log.error("获取在线任务数异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 获取在线护理记录数
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getOnlineNursing(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线护理记录数");
		dataKeyObject.setUnit("条");
		dataKeyObject.setSortValue(4);
		try {
			List<NursingRecord> list = nursingRecordMapper.getOnlineNursingRecordNumber();
			if (CollectionUtils.isNotEmpty(list)) {
				dataKeyObject.setNumber(list.size());
				//map.put("在线护理记录数", list.size() + "");
			} else {
				dataKeyObject.setNumber(0);
				//map.put("在线护理记录数", "0");
			}
		} catch (Exception e) {
			log.error("获取在线护理记录数异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 获取在线设备数据
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getOnlineDevice(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线设备数据");
		dataKeyObject.setUnit("名");
		dataKeyObject.setSortValue(5);
		try {
			List<Device> list = deviceMapper.getOnlineDevice();
			if (CollectionUtils.isNotEmpty(list)) {
				dataKeyObject.setNumber(list.size());
				//map.put("在线设备数据", list.size() + "");
			} else {
				dataKeyObject.setNumber(0);
				//map.put("在线设备数据", "0");
			}
		} catch (Exception e) {
			log.error("获取获取在线设备数据异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 管路数据
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getOnlinePiping(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线管路数据");
		dataKeyObject.setUnit("名");
		dataKeyObject.setSortValue(6);
		try {
			List<Piping> list = pipingMapper.getOnlinePipingNumber();
			if (CollectionUtils.isNotEmpty(list)) {
				dataKeyObject.setNumber(list.size());
				//map.put("在线管路数据", list.size() + "");
			} else {
				dataKeyObject.setNumber(0);
				//map.put("在线管路数据", "0");
			}
		} catch (Exception e) {
			log.error("获取管路数据异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 出科数据
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getDischargeNumber(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("出科数据");
		dataKeyObject.setUnit("个");
		dataKeyObject.setSortValue(7);
		try {
			List<PatientVo> list = patientMapper.getDischargeNumber();
			if (CollectionUtils.isNotEmpty(list)) {
				dataKeyObject.setNumber(list.size());
			} else {
				dataKeyObject.setNumber(0);
			}
		} catch (Exception e) {
			log.error("获取出科数据异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}

	/**
	 * 医嘱数据
	 *
	 * @param dataKeyObjectList
	 * @return
	 */
	private List<DataKeyObject> getDoctorsAdviceNumber(List<DataKeyObject> dataKeyObjectList) {
		DataKeyObject dataKeyObject = new DataKeyObject();
		dataKeyObject.setTitle("在线医嘱数据");
		dataKeyObject.setUnit("条");
		dataKeyObject.setSortValue(8);
		try {
			List<HisDoctorsAdvice> list = hisDoctorsAdviceMapper.getDoctorsAdviceForToday();
			if (CollectionUtils.isNotEmpty(list)) {
				dataKeyObject.setNumber(list.size());
			} else {
				dataKeyObject.setNumber(0);
			}
		} catch (Exception e) {
			log.error("获取医嘱数据异常:", e);
		}
		dataKeyObjectList.add(dataKeyObject);
		return dataKeyObjectList;
	}


	public static void main(String[] args) {
		String percent;
		Double p3 = 0.0;
		p3 = 0 * 1.0 / 300;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
		percent = nf.format(p3);
		System.out.println(percent.toString()+"llllllllllllll");
		List<DataVRingChartData> lists = Lists.newArrayList();

//		List<DataVRingChartData>  lists = builRingDataObject(list, "ringName", "totalName", 369, "subName", 123);
		for(DataVRingChartData data : lists){
			System.out.println(data.getRingName()+","+data.getRatio());
			System.out.println(data.getTotalName()+","+data.getTotalNumber());
			System.out.println(data.getSubName()+","+data.getSubNumber());
		}
	}
}
