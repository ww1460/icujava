package com.pig4cloud.pigx.ccxxicu.api.Bo.datav;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @program: pigx
 * @description: 综合数据大屏-环形图数据，使用map进行展示
 * @author: 崔洪振
 * @create: 2019-10-11 15:13
 **/
@Data
@ApiModel(value = "综合数据大屏-环形图数据")
public class DataVRingChartData {

	/**
	 * 环形数据名称
	 */
	private String ringName;//demo：护士数据，患者数据，病床数据，任务数据，出入科数据

	/**
	 * 环形数据总数名称
	 */
	private String totalName;//demo：总护士数

	/**
	 * 环形数据总数
	 */
	private String totalNumber;//demo：1000人

	/**
	 * 子模块数据名称
	 */
	private String subName;//demo：在线护士数

	/**
	 * 子模块数据数量
	 */
	private String subNumber;//demo：500人

	/**
	 * 占比数
	 */
	private String ratio;//demo：50%

	/**
	 * 单位
	 */
	private String unit;
}
