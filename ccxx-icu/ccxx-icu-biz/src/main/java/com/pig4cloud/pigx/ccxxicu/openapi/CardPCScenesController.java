package com.pig4cloud.pigx.ccxxicu.openapi;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ProcessMessageConstant;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.RfidData;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataInput;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataReturn;
import com.pig4cloud.pigx.ccxxicu.api.entity.collection.CollectionRecord;
import com.pig4cloud.pigx.ccxxicu.service.scenes.ScenesOperationService;
import com.pig4cloud.pigx.common.core.constant.enums.PositionEnum;
import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import com.pig4cloud.pigx.common.core.constant.enums.ScenesActionEnum;
import com.pig4cloud.pigx.common.core.constant.enums.StatusChangeEnum;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cardPC")
@Api(value = "cardPC", tags = "卡片电脑采集到的数据与icu后端的交互")
public class CardPCScenesController {

	@Autowired
	private ScenesOperationService scenesOperationService;

	/**
	 * android app 暂用全局变量
	 */
	private static final String ipAddress = "192.168.4.177";
	private static final String macAddress = "ac:bc:32:94:d2:ed";
	private static final String index = "NPMEB";
	//pharmacy：制药室；icu：重症监护室；gc：药品回收处
	private static final PositionEnum position = PositionEnum.ICU;
	private static final Integer cacheCount = 60;
	//场景组装数据使用
	private static final Set<String> commonSet = new HashSet<>();
	private static final Set<String> pharmacySet = new HashSet<>();
	private static final Set<String> equipmentSet = new HashSet<>();
	private static final Set<String> medicationSet = new HashSet<>();
	private static final Set<String> pmoveSet = new HashSet<>();
	private static final Set<String> medicalWasteSet = new HashSet<>();
	private static List<String> historyStatus = new ArrayList<>();
	private static List<String> historyRfid = new ArrayList<>();

	static {
		historyStatus.add("11111");
		//初始时，没有rfid数据，置为NULL
		historyRfid.add("NULL");
		pharmacySet.add("N");
		pharmacySet.add("M");
		medicationSet.addAll(pharmacySet);
		medicationSet.add("P");
		equipmentSet.add("E");
		equipmentSet.add("B");
		commonSet.addAll(medicationSet);
		commonSet.addAll(equipmentSet);
		pmoveSet.add("P");
		pmoveSet.add("B");
		medicalWasteSet.addAll(pharmacySet);
	}

	private static final Pattern pharmacy = Pattern.compile("^[2|4]\\d2\\d\\d");
	private static final Pattern medication = Pattern.compile("^[2|4]{3}");
	private static final Pattern equipment = Pattern.compile("^\\d{3}[2|3][2|4]");
	private static final Pattern pMove = Pattern.compile("^\\d[2|3]\\d{3}");
	private static final Pattern mMove = Pattern.compile("^\\d{2}[1|3]\\d{2}");
	private static final Pattern medicalWaste = Pattern.compile("\\d{2}[2|4]\\d{2}");

	/**
	 * 根据数据中的action分配处理方法
	 * 将APP和C#客户端传来的数据进行处理
	 *
	 * @param data
	 * @return
	 */
	@Inner(value = false)
	@ApiOperation(value = "transport", notes = "统一交互接口")
	@PostMapping("/transport")
	public ScenesDataReturn scenesOperation(@RequestBody String data) {
		ScenesDataReturn scenesDataReturn = null;
		if (StringUtils.isEmpty(data)) {
			log.error("接收信息为空");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_ACCEPT_EXCEPTION, "接收信息为空");
		}
		log.info("接收的数据为：【{}】", data);
		ScenesDataInput scenesDataInput = JSON.parseObject(data, ScenesDataInput.class);
		log.info("反序列化收到的数据：【{}】", scenesDataInput);
		if (StringUtils.isEmpty(scenesDataInput.getIp()) || StringUtils.isEmpty(scenesDataInput.getAction())) {
			log.error("发送的数据IP地址不存在");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_IP_MISSING_EXCEPTION, "IP和action不可为空");
		}
		if (scenesDataInput.getData().isEmpty()) {
			log.error("发送的数据中不包含任何的rfid数据");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.NO_DATA, "发送的数据中不包含任何的rfid数据");
		}
		switch (scenesDataInput.getAction()) {
			case COMMON:
				log.info("common!");
				scenesDataReturn = scenesOperationService.common(scenesDataInput);
				break;
			case MEDICATION:
				log.info("medication!");
				scenesDataReturn = scenesOperationService.medication(scenesDataInput);
				break;
			case PHARMACY:
				log.info("pharmacy");
				scenesDataReturn = scenesOperationService.pharmacy(scenesDataInput);
				break;
			case PMOVE:
				log.info("p move");
				scenesDataReturn = scenesOperationService.pMove(scenesDataInput);
				break;
			case MEDICALWASTE:
				log.info("medical waste");
				scenesDataReturn = scenesOperationService.medicalWaste(scenesDataInput);
				break;
			case EQUIPMENT:
				log.info("equipment change");
				scenesDataReturn = scenesOperationService.equipment(scenesDataInput);
				break;
			case MMOVE:
				//用药结束
				log.info("medicine move");
				scenesDataReturn = scenesOperationService.mMove(scenesDataInput);
				break;
			default:
				log.error("场景不存在，数据处理失败");
				return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_ACTION_ERROR_EXCEPTION, "上传的场景错误");
		}
		return scenesDataReturn;
	}

	/**
	 * 根据数据中的action分配处理方法
	 * 将APP和C#客户端传来的数据进行处理
	 *
	 * @param data
	 * @return
	 */
	@Inner(value = false)
	@ApiOperation(value = "collect", notes = "设备采集记录")
	@PostMapping("/collect")
	public ScenesDataReturn equipmentCollectionRecord(@RequestBody String data) {
		if (StringUtils.isEmpty(data)) {
			log.error("接收信息为空");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.RFID_ACCEPT_EXCEPTION, "接收信息为空");
		}
		log.info("接收的数据为：【{}】", data);
		CollectionRecord record = JSON.parseObject(data, CollectionRecord.class);
		log.info("反序列化收到的数据：【{}】", record);
		if (StringUtils.isEmpty(record.getERfid()) || StringUtils.isEmpty(record.getPRfid())) {
			log.error("发送的数据IP地址不存在");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_FAIL, ProcessMessageConstant.COLLECTION_MISSING_RFID, "缺少病人和设备的rfid");
		}
		if (record.getData().isEmpty()) {
			log.error("接收的数据中不包含任何采集到的数据");
			return new ScenesDataReturn(ProcessMessageConstant.RFID_SUCCESS, ProcessMessageConstant.NO_DATA, "接收的数据中不包含任何采集到的数据");
		}
		ScenesDataReturn dataReturn = scenesOperationService.collectData(record);
		log.info("设备【{}】采集到的病人【{}】数据记录成功！", record.getERfid(), record.getPRfid());
		return dataReturn;
	}

	@Inner(value = false)
	@ApiOperation(value = "dataChangeTrigger", notes = "数据变化触发器")
	@PostMapping("/dataChangeTrigger")
	public ScenesDataInput processDataChange(@RequestBody String data) throws Exception {
		//process data
		//data:N-100002,B-20001,...
		log.info("接收的数据为：【{}】", data);
		if (StringUtils.isEmpty(data)) {
			log.error("接收信息为空");
			return null;
		}
		if (!"NULL".equals(data)) {
			data = hexToStr(data);
		}
		ScenesDataInput dataReturn = new ScenesDataInput();
		dataReturn.setIp(ipAddress);
		dataReturn.setMac(macAddress);
		String[] rfids = data.split(",");
		List<String> rfidList = new ArrayList<>(Arrays.asList(rfids));
		//当前状态变化
		String nowStatus = castData(rfidList);
		//当前rfid   --主要为了获取状态0->1时，上一个状态的rfid
		String nowRfids = getRfifData(rfidList);
		log.info("转换后的数据为：【{}】【{}】", nowStatus, nowRfids);
		String statusChange = castAndOperation(nowStatus);
		log.info("状态变化为：【{}】", statusChange);
		switch (position) {
			case PHARMACY:
				log.info("制药室");
				//todo 包含空标签->向标签中写数据（此判断应在前面判断）
				if (pharmacy.matcher(statusChange).find()) {
					//只有护士和药品被采集到,且药品的状态由1至0
					log.info("pharmacy");
					dataReturn.setAction(ScenesActionEnum.PHARMACY);
					setRfidData(dataReturn, rfidList, statusChange);
				} else {
					//状态为common,将有状态变化的rifd组装
					log.info("common");
					dataReturn.setAction(ScenesActionEnum.COMMON);
					setRfidData(dataReturn, rfidList, statusChange);
				}
				break;
			case ICU:
				log.info("重症监护室");
				if (medication.matcher(statusChange).find()) {
					//用药场景（需要向前追溯,追溯20次，如果有五次满足条件则符合用药场景）
					//逻辑需要讨论
					//满足压入一个发送标志
					dataReturn.setAction(ScenesActionEnum.MEDICATION);
					setRfidData(dataReturn, rfidList, statusChange);
				} else if (equipment.matcher(statusChange).find()) {
					//设备变化
					dataReturn.setAction(ScenesActionEnum.EQUIPMENT);
					setRfidData(dataReturn, rfidList, statusChange);
				} else if (pMove.matcher(statusChange).find()) {
					//病人状态变化
					dataReturn.setAction(ScenesActionEnum.PMOVE);
					setRfidData(dataReturn, rfidList, statusChange);
					//保证不会发生空指针异常
				} else if (mMove.matcher(statusChange).find() && historyStatus.size() > 30) {
					//药品离开->用药结束
					dataReturn.setAction(ScenesActionEnum.MMOVE);
					setRfidData(dataReturn, rfidList, statusChange);
				} else {
					//状态为common,将有状态变化的rifd组装
					dataReturn.setAction(ScenesActionEnum.COMMON);
					setRfidData(dataReturn, rfidList, statusChange);
				}
				break;
			case DRUG_RECOVERY:
				log.info("drug recovery");
				if (medicalWaste.matcher(statusChange).find()) {
					dataReturn.setAction(ScenesActionEnum.MEDICALWASTE);
				} else {
					dataReturn.setAction(ScenesActionEnum.COMMON);
				}
				setRfidData(dataReturn, rfidList, statusChange);
				break;
			default:
				log.error("该位置不存在，数据处理失败");
		}
		historyStatus.add(nowStatus);
		historyRfid.add(nowRfids);
		//清除数据-->内存中保存过多数据会导致APP闪退
		clearCacheRfidData();
		return dataReturn;
	}

	/**
	 * 将接收的16进制数据转换为所需的数据
	 * 返回N-N10001,P-P20001
	 *
	 * @param receive
	 * @return
	 */
	private String hexToStr(String receive) throws Exception {
		String[] rfids = receive.split(",");
		StringBuilder builder = new StringBuilder("");
		for (String rfid : rfids) {
			String type = rfid.substring(0, 2);
			String temType = "" + (char) Integer.parseInt(type, 16);
			System.out.println("转换为的字母为：" + temType);
			RfidTypeEnum rfidTypeEnum = RfidTypeEnum.getFrom(temType);
			if (rfidTypeEnum == null) {
				throw new Exception("非法的Rfid类型");
			}
			builder.append(rfidTypeEnum.getType()).append("-").append(rfidTypeEnum.getType());
			for (int i = 2; i < rfid.length(); i++) {
				char tmp = rfid.charAt(i);
				if (tmp >= '0' && tmp <= '9') {
					builder.append(tmp);
				} else {
					throw new Exception("参数中存在非法字符");
				}
			}
			builder.append(",");
		}
		String result = builder.toString().trim().toUpperCase();
		return result.substring(0, result.length() - 1);
	}

	/**
	 * 根据标签列表产生采集到的标签
	 * 根据顺序N P M E B
	 * 0表示在，1表示不在
	 * 因为状态变化出发操作与采集到的数据中有多少个无关，所以此时不关心数量
	 * 如果rfidList中只有一个NULL,状态全部为11111
	 *
	 * @param rfidList
	 * @return
	 */
	private String castData(List<String> rfidList) {
		StringBuilder result = new StringBuilder("11111");
		//兼容采集不到数据的问题 字符串为"NULL"
		if (rfidList.size() == 1 && "NULL".equals(rfidList.get(0))) {
			return result.toString();
		}
		for (String rfid : rfidList) {
			String type = rfid.split("-")[0];
			result.replace(index.indexOf(type), index.indexOf(type) + 1, "0");
		}
		return result.toString();
	}

	/**
	 * 根据标签列表得到rfid列表
	 * rfid列表按照N P M E B排列
	 * 多个rfid按照xxx,xxx组合  英文,为分隔符
	 * 如果某个不存在，使用NULL标记
	 * 不同的类型的用-分割
	 * 数据示例
	 *
	 * @param rfidList
	 * @return
	 */
	private String getRfifData(List<String> rfidList) {
		//去除重复的rfid
		Set<String> rfidSet = new HashSet<>();
		StringBuilder[] rfids = {new StringBuilder(), new StringBuilder("-"),
				new StringBuilder("-"), new StringBuilder("-"), new StringBuilder("-")};
		for (String rfid : rfidList) {
			String[] nums = rfid.split("-");
			//防止空指针异常
			if (index.contains(nums[0]) && !rfidSet.contains(nums[1])) {
				//将rfid拼接到对应的位置
				rfids[index.indexOf(nums[0])].append(nums[1]).append(",");
				//将rfid加入到集合中，用作下次判重
				rfidSet.add(nums[1]);
			}
		}
		StringBuilder result = new StringBuilder();
		for (StringBuilder tem : rfids) {
			if (tem.length() <= 1) {
				//如果此次数据不包含此类标签，使用NULL标记
				tem.append("NULL");
			} else {
				//替换掉多余的 ,
				tem.replace(tem.lastIndexOf(","), tem.lastIndexOf(",") + 1, "");
			}
			result.append(tem);
		}
		return result.toString();
	}

	/**
	 * 将当前状态和上一状态做'与操作'
	 *
	 * @param nowStatus
	 * @return
	 */
	private String castAndOperation(String nowStatus) {
		log.info("history data: 【{}】", historyStatus);
		String beforeStatus = historyStatus.get(historyStatus.size() - 1);
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < beforeStatus.length(); i++) {
			String change = "" + beforeStatus.charAt(i) + nowStatus.charAt(i);
			result.append(StatusChangeEnum.getFrom(change).getValue());
		}
		return result.toString();
	}

	/**
	 * 根据action来组装数据
	 *
	 * @param scenesDataInput
	 * @param rfidList
	 * @param statusChange
	 */
	private void setRfidData(ScenesDataInput scenesDataInput, List<String> rfidList, String statusChange) {
		switch (scenesDataInput.getAction()) {
			case COMMON:
				log.info("common assembly data");
				scenesDataInput.getData().addAll(getStatusChangeRfidData(rfidList, statusChange, commonSet));
				break;
			case MEDICATION:
				log.info("medication assembly data");
				Pattern traceBackMedication = Pattern.compile("^000\\d{2}");
				Integer count = 0;
				boolean sendMedication = true;
				for (int i = 1; historyStatus.size() > 20 && i <= 20; i++) {
					String beforeStatus = historyStatus.get(historyStatus.size() - i);
					if (ProcessMessageConstant.MEDICATION_FLAG.equals(beforeStatus)) {
						sendMedication = false;
						//再次插入已发送标志，防止重复发送
						if (i > 15) {
							historyStatus.add(historyStatus.size() - 1, ProcessMessageConstant.MEDICATION_FLAG);
							historyRfid.add(historyRfid.size() - 1, historyRfid.get(historyRfid.size() - 1));
						}
						break;
					}
					if (traceBackMedication.matcher(beforeStatus).find()) {
						count++;
					}
				}
				//没发送过&&护士、病人、药都在且持续15次，触发用药操作
				if (sendMedication && count > 15) {
					scenesDataInput.getData().addAll(getStatusUnchangeRfidData(rfidList, medicationSet));
					//插入已发送标志位
					historyStatus.add(historyStatus.size() - 1, ProcessMessageConstant.MEDICATION_FLAG);
					historyRfid.add(historyRfid.size() - 1, historyRfid.get(historyRfid.size() - 1));
				} else {
					scenesDataInput.setAction(ScenesActionEnum.COMMON);
					scenesDataInput.getData().addAll(getStatusChangeRfidData(rfidList, statusChange, commonSet));
				}
				break;
			case PHARMACY:
				log.info("pharmacy assembly data");
				scenesDataInput.getData().addAll(getStatusUnchangeRfidData(rfidList, pharmacySet));
				break;
			case PMOVE:
				log.info("p move assembly data");
				//获取状态不变的所需数据
				scenesDataInput.getData().addAll(getStatusUnchangeRfidData(rfidList, pmoveSet));
				//如果设备状态由0->1，当前rfid列表中是没有设备的rfid的，需要单独获取
				if ("3".equals("" + statusChange.charAt(1))) {
					List<RfidData> rfidDataList = getStatusChangeRfidData(rfidList, statusChange, pmoveSet);
					log.info("通过getStatusChangeRfidData方法获取的状态变化的数据为：【{}】", rfidDataList);
					//不会产生重复数据  因为上面是获取的状态不变的数据，此时获取的是状态变化的数据
					scenesDataInput.getData().addAll(rfidDataList);
				}
				break;
			case MEDICALWASTE:
				log.info("medical waste assembly data");
				scenesDataInput.getData().addAll(getStatusUnchangeRfidData(rfidList, medicalWasteSet));
				break;
			case EQUIPMENT:
				log.info("equipment change assembly data");
				//获取状态不变的所需数据
				scenesDataInput.getData().addAll(getStatusUnchangeRfidData(rfidList, equipmentSet));
				//如果设备状态由0->1，当前rfid列表中是没有设备的rfid的，需要单独获取
				if ("3".equals("" + statusChange.charAt(3))) {
					List<RfidData> rfidDataList = getStatusChangeRfidData(rfidList, statusChange, equipmentSet);
					//log.info("通过getStatusChangeRfidData方法获取的状态变化的数据为：【{}】", rfidDataList);
					//不会产生重复数据  因为上面是获取的状态不变的数据，此时获取的是状态变化的数据
					scenesDataInput.getData().addAll(rfidDataList);
				}
				break;
			case MMOVE:
				//用药结束
				//追溯四次  防止因为距离过远导致的概率性采集不到
				log.info("medicine move assembly data");
				int i;
				Pattern traceBack0 = Pattern.compile("^\\d{2}0\\d{2}");
				for (i = 1; i < 5; i++) {
					String beforeStatus = historyStatus.get(historyStatus.size() - i);
					//如果已发送，或者序列中药品存在，break
					if (ProcessMessageConstant.MMOVE_FLAG.equals(beforeStatus)
							|| traceBack0.matcher(beforeStatus).find()) {
						break;
					}
				}
				if (i >= 5) {
					//再次向前追溯，必须同时包含1
					for (; i < 10; i++) {
						String beforeStatus = historyStatus.get(historyStatus.size() - i);
						if (traceBack0.matcher(beforeStatus).find()) {
							break;
						}
					}
					if (i < 10) {
						Pattern traceBackMmove = Pattern.compile("^\\d00\\d{2}");
						for (i = 1; i <= 10; i++) {
							String beforeStatus = historyStatus.get(historyStatus.size() - i);
							if (traceBackMmove.matcher(beforeStatus).find()) {
								String rfidString = historyRfid.get(historyRfid.size() - i);
								log.info("获取到的rfid为：【{}】", rfidString);
								List<RfidData> rfidDataList = getRfidDataFromString(rfidString, medicationSet, 1);
								updateStatusByNow(rfidDataList, rfidList, medicationSet);
								scenesDataInput.getData().addAll(rfidDataList);
								break;
							}
						}
						historyStatus.add(historyStatus.size() - 1, ProcessMessageConstant.MMOVE_FLAG);
						historyRfid.add(historyRfid.size() - 1, historyRfid.get(historyRfid.size() - 1));

						log.info("push 后的数据：【{}】", historyStatus);
					} else {
						//不满足用药结束场景，改为common
						scenesDataInput.setAction(ScenesActionEnum.COMMON);
						scenesDataInput.getData().addAll(getStatusChangeRfidData(rfidList, statusChange, commonSet));
					}
				} else {
					//不满足用药结束场景，改为common
					scenesDataInput.setAction(ScenesActionEnum.COMMON);
					scenesDataInput.getData().addAll(getStatusChangeRfidData(rfidList, statusChange, commonSet));
				}
				break;
			default:
				log.error("场景不存在，数据处理失败");
		}
	}

	/**
	 * 根据当前的状态更新list中rfid数据的状态
	 *
	 * @param rfidDataList
	 * @param rfidList
	 */
	private void updateStatusByNow(List<RfidData> rfidDataList, List<String> rfidList, Set<String> typeSet) {
		for (String rfid : rfidList) {
			String[] nums = rfid.split("-");
			if (typeSet.contains(nums[0])) {
				for (RfidData rfidData : rfidDataList) {
					if (nums[1].equals(rfidData.getRfid())) {
						rfidData.setStatus(0);
					}
				}
			} else {
				log.info("无用数据：【{}】，过滤", nums);
			}
		}
	}

	/**
	 * 将上面需要获取状态变化的数据抽取出来
	 * 可以根据所需数据的集合获取String中有的rfid
	 * 数据形如：1001,1002-NULL-3001,3002-NULL-NULL
	 *
	 * @param rfidString
	 * @param typeSet
	 * @return
	 */
	private List<RfidData> getRfidDataFromString(String rfidString, Set<String> typeSet, Integer status) {
		List<RfidData> rfidDataList = new ArrayList<>();
		List<String> rfidList = Arrays.asList(rfidString.split("-"));
		for (String type : typeSet) {
			int typeIndex = index.indexOf(type);
			List<String> rfids = Arrays.asList(rfidList.get(typeIndex).split(","));
			for (String rfid : rfids) {
				RfidData rfidData = new RfidData();
				rfidData.setType(RfidTypeEnum.getFrom(type));
				rfidData.setStatus(status);
				rfidData.setRfid(rfid);
				rfidDataList.add(rfidData);
			}

		}
		return rfidDataList;
	}

	/**
	 * 将上面需要获取状态变化的数据抽取出来
	 * 可以根据所需数据的集合获取状态未变化的rfid数据列表
	 *
	 * @param rfidList
	 * @param typeSet
	 * @return
	 */
	private List<RfidData> getStatusUnchangeRfidData(List<String> rfidList, Set<String> typeSet) {
		List<RfidData> rfidDataList = new ArrayList<>();
		for (String rfid : rfidList) {
			RfidData rfidData = new RfidData();
			String[] nums = rfid.split("-");
			if (typeSet.contains(nums[0])) {
				rfidData.setType(RfidTypeEnum.getFrom(nums[0]));
				rfidData.setStatus(0);
				rfidData.setRfid(nums[1]);
				rfidDataList.add(rfidData);
			} else {
				log.info("无用数据：【{}】，过滤", nums);
			}
		}
		return rfidDataList;
	}

	/**
	 * 将上面需要获取状态变化的数据抽取出来
	 * 可以根据当前序列和上一序列，获取状态变化的rfid数据列表
	 *
	 * @param rfidList
	 * @param statusChange
	 * @param typeSet
	 * @return
	 */
	private List<RfidData> getStatusChangeRfidData(List<String> rfidList, String statusChange, Set<String> typeSet) {
		List<RfidData> rfidDataList = new ArrayList<>();
		//获取当前的和上一时刻的rfid列表
		String beforeRfid = historyRfid.get(historyRfid.size() - 1);
		String nowRfid = getRfifData(rfidList);
		List<String> beforeRfids = Arrays.asList(beforeRfid.split("-"));
		List<String> nowRfids = Arrays.asList(nowRfid.split("-"));
		for (int i = 0; i < statusChange.length(); i++) {
			int status = Integer.parseInt("" + statusChange.charAt(i));
			//设置数据  只有状态发生变化才发送0->1||1->0
			String[] rfidArray;
			if (status == 2 || status == 3) {
				//1->0 可从当前获取  0->1 从上一时刻获取
				rfidArray = status == 2 ? nowRfids.get(i).split(",") : beforeRfids.get(i).split(",");
				Integer dataStatus = status == 2 ? 0 : 1;
				//数据异常，跳过这条数据
				if ("NULL".equals(rfidArray[0])) {
					continue;
				}
				for (String rfid : rfidArray) {
					if (typeSet.contains("" + index.charAt(i))) {
						RfidData rfidData = new RfidData();
						rfidData.setType(RfidTypeEnum.getFrom("" + index.charAt(i)));
						rfidData.setStatus(dataStatus);
						rfidData.setRfid(rfid);
						rfidDataList.add(rfidData);
					}
				}
			}
		}
		return rfidDataList;
	}

	/**
	 * 清除存储的缓存数据，保留设定的记录数量
	 */
	private void clearCacheRfidData() {
		if (historyStatus.size() > 200 && historyStatus.size() == historyRfid.size()) {
			while (historyStatus.size() > cacheCount) {
				historyStatus.remove(0);
				historyRfid.remove(0);
			}
			log.info("清除后的状态数据：【{}，{}】", historyStatus.size(), historyStatus);
			log.info("清除后的rfid数据：【{}，{}】", historyRfid.size(), historyRfid);
		}
		//两个数据不一致  需要调整
		if (historyStatus.size() > 200 && historyStatus.size() != historyRfid.size()) {
			int remainCount = cacheCount;
			for (int i = 1; i <= cacheCount; i++) {
				String status = historyStatus.get(historyStatus.size() - i);
				String rfid = historyRfid.get(historyRfid.size() - i);
				if (!judgeStatusAndRfid(status, rfid)) {
					//此时数据异常，清空前序的所有数据
					remainCount = i - 1;
					break;
				}
			}
			//只保留正确的数据或者最新的60条无误的数据
			while (historyStatus.size() > remainCount) {
				historyStatus.remove(0);
			}
			while (historyRfid.size() > remainCount) {
				historyRfid.remove(0);
			}
		}
	}

	private boolean judgeStatusAndRfid(String status, String rfid) {
		List<String> rfidList = Arrays.asList(rfid.split("-"));
		for (int j = 0; j < 5; j++) {
			//状态为0--存在，需要判断对应的rfid是否存在
			//状态为1--不存在，需要判断对应的rfid是否为NULL
			if ((status.charAt(j) - '0' == 0 && "NULL".equals(rfidList.get(j)))
					|| (status.charAt(j) - '0' == 1 && !"NULL".equals(rfidList.get(j)))) {
				return false;
			}
		}
		return true;
	}

}
