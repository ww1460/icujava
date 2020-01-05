package com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes;

public class ProcessMessageConstant {

	public static final String MMOVE_FLAG = "MMOVE_SEND";//用药结束已发送标志位

	public static final String MEDICATION_FLAG = "MEDICATION_SEND";//用药已发送标志位

	public static final String RFID_PROCESS_OK = "0000";//处理成功

	public static final String RFID_ACCEPT_EXCEPTION = "5001";//数据为空

	public static final String RFID_RESPONSE_EXCEPTION = "5002";

	public static final String RFID_REQUEST_EXCEPTION = "5003";

	public static final String RFID_ERROR_EXCEPTION = "5004";

	public static final String RFID_CLASS_CASE_EXCEPTION = "5005";

	public static final String RFID_RESOLVING_EXCEPTION = "5006";//解析异常

	public static final String RFID_NODEFIN_EXCEPTION = "5007";//未定义类型

	public static final String RFID_IP_MISSING_EXCEPTION = "5008";//ip、action 缺失

	public static final String RFID_ACTION_ERROR_EXCEPTION = "5009";//action类型错误，不在定义的枚举值中

	public static final String RFID_DATA_MISSING_EXCEPTION = "5010";//rfid数据 缺失

	public static final String RFID_EQUIPMENT_UPDATE_EXCEPTION = "5011";//设备位置信息更新失败

	public static final String MEDICATION_OPERATION_MISSING_OBJECT = "5020";//护士、病人、药不全在或者病人不为一个，无法用药

	public static final String RFID_SAVE_EXCEPTION = "5030";//RFID数据保存失败

	public static final String RFID_PATINET_LEAVE_EXCEPTION = "5040";//病人离开病房

	public static final String MEDICATION_DATA_EXCEPTION = "5100";//数据异常，联系维护人员

	public static final String COLLECTION_MISSING_RFID = "5101";//缺少病人和设备的rfid

	public static final String NO_DATA = "5200";//无数据操作


	public static final String RFID_SUCCESS = "success";//FRID交互成功

	public static final String RFID_SUCCESS_MESSAGE = "show";//FRID交互成功 需显示提示信息

	public static final String RFID_FAIL = "fail";//FRID交互失败

}
