package com.pig4cloud.pigx.ccxxicu.api.Bo.datav;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @program: pigx
 * @description: 综合数据大屏关键目录栏数据
 * @author: 崔洪振
 * @create: 2019-10-12 9:29
 **/
@Data
@ApiModel(value = "综合数据大屏目录栏数据")
public class DataVCatalogData {

	/**
	 * 目录栏名称
	 */
	private String catalogName;

	/**
	 * 目录栏数据
	 */
	private String catalogData;

}
