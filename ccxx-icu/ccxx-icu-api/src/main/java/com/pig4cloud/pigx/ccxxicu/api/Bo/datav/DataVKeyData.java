package com.pig4cloud.pigx.ccxxicu.api.Bo.datav;

import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @program: pigx
 * @description: 综合数据大屏关键数据
 * @author: 崔洪振
 * @create: 2019-10-11 14:20
 **/
@Data
@ApiModel(value = "综合数据大屏关键数据")
public class DataVKeyData {

	/**
	 * 在线护士数据
	 */
	private Integer onlineNurse;

	/**
	 * 在线病床使用数
	 */
	private Integer onlineBed;

	/**
	 * 在线病人人数
	 */
	private Integer onlinePatient;

	/**
	 * 今日任务数据
	 */
	private Integer todayTaskNumber;

	/**
	 * 今日护理记录数据
	 */
	private Integer todayNursingNumber;

	/**
	 * 今日使用设备数
	 */
	private Integer todayDeviceNumber;

	/**
	 * 今日管路数
	 */
	private Integer todayPipingNumber;

	/**
	 * 今日出科人数
	 */
	private Integer todayDischargeNumber;

	/**
	 * 今日医嘱数据
	 */
	private Integer todayDoctorsAdvice;


}
