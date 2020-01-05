package com.pig4cloud.pigx.ccxxicu.service.datav;

import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataKeyObject;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataVCatalogData;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataVEarlyWarning;
import com.pig4cloud.pigx.ccxxicu.api.Bo.datav.DataVRingChartData;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.EarlyWarning;

import java.util.List;
import java.util.Map;

/**
 * 综合数据大屏
 */
public interface DataVService {

	/**
	 * 关键数据
	 * @return List<DataKeyObject>
	 */
	//Map<String, String> keyData();
	List<DataKeyObject> keyData();

	/**
	 * 滚动图数据
	 * @return
	 */
	List<DataVEarlyWarning> scrollingData();

	/**
	 * 环形图数据
	 * @return
	 */
	List<DataVRingChartData> ringData();

	/**
	 * 目录栏数据
	 * @return
	 */
	List<DataVCatalogData> catalogData();

}
