package com.pig4cloud.pigx.ccxxicu.api.Bo.datav;


import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @program: pigx
 * @description: 综合数据大屏-玫瑰图数据，建议：使用map进行展示
 * @author: 崔洪振
 * @create: 2019-10-11 15:13
 **/
@Data
@ApiModel(value = "综合数据大屏-玫瑰图数据")
public class DataVRoseMapData {

	/**
	 * 儿童占比
	 */
	private String childrenProportion;

	/**
	 * 少年占比
	 */
	private String juvenileProportion;

	/**
	 * 青年占比
	 */
	private String youthProportion;

	/**
	 * 中年占比
	 */
	private String middleAgeProportion;

	/**
	 * 老年占比
	 */
	private String oldAgeProportion;

	/**
	 * 男性占比
	 */
	private String maleProportion;

	/**
	 * 女性占比
	 */
	private String femaleProportion;

}
