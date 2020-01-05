package com.pig4cloud.pigx.common.core.constant;

/**
 * @author 崔洪振
 * @date 2019/10/18
 */
public interface DataVConstants {

	/** ------环形数据------ */
	/**
	 * 环形图数据名称-护士数据
	 */
	String RINGDATA_NURSE = "护士数据";

	/**
	 * 环形图数据名称-护士数据-总护士数据
	 */
	String RINGDATA_NURSE_ALL = "总护士数据";

	/**
	 * 环形图数据名称-护士数据-在线护士数据
	 */
	String RINGDATA_NURSE_ONLINE = "在线护士数据";

	/**
	 * 环形图数据名称-患者数据-当日出入科数据
	 */
	String RINGDATA_PATIENT = "当日出入科数据";

	/**
	 * 当日入科数据
	 */
	String RINGDATA_PATIENT_BUILD = "入科数据";

	/**
	 * 当日入科人数&当日出科人数
	 */
	String RINGDATA_PATIENT_DISCHARGE = "出科数据";

	/**
	 * 环形图数据名称-病床数据
	 */
	String RINGDATA_BED = "病床数据";

	String RINGDATA_BED_ALL = "病床总数据";

	String RINGDATA_BED_ONLINE = "在用病床数据";

	/**
	 * 环形图数据名称-任务数据
	 */
	String RINGDATA_TASK = "今天任务数据";

	/**
	 * 环形图数据名称-任务数据-当日创建总任务数
	 */
	String RINGDATA_TASK_ALL = "任务总数据";

	/**
	 * 环形图数据名称-任务数据-当日创建任务完成数
	 */
	String RINGDATA_TASK_OVER = "任务完成数";

	/**
	 * 环形图数据名称-出入科数据
	 */
	String RINGDATA_DISCHARGE = "出入科数据";

	/**
	 * 环形图数据名称-出入科数据-总数据
	 */
	String RINGDATA_DISCHARGE_ALL = "出入科总数据";

	/**
	 * 环形图数据名称-出入科数据-在科数据
	 */
	String RINGDATA_DISCHARGE_ONLINE = "在科数据";


	/** ------目录栏数据------ */
	String CATALOG_TODAY_BUILD = "患者今日入科人数";

	String CATALOG_TODAY_DISCHARGE = "患者今日出科人数";

	String CATALOG_TODAY_LEAVE = "今日出院人数";

	String CATALOG_TODAY_TRANSFER = "今日转科人数";

	String CATALOG_TODAY_DEATH = "今日死亡人数";

	String CATALOG_TODAY_CATHETER = "留置导尿管患者人数";

	String CATALOG_TODAY_CANNULA = "动静脉插管患者人数";

	String CATALOG_TODAY_RES = "使用呼吸机患者人数";

	String RINGDATA_UNIT_VISITING = "名";

	String RINGDATA_UNIT_BED = "张";

	String RINGDATA_UNIT_INDIVIDAL = "个";

	String RINGDATA_UNIT_STRIP = "条";
}
