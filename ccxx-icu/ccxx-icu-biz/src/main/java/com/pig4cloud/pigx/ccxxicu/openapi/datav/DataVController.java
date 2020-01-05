package com.pig4cloud.pigx.ccxxicu.openapi.datav;


import com.pig4cloud.pigx.ccxxicu.service.datav.DataVService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/datav" )
@Api(value = "datav", tags = "综合数据大屏")
public class DataVController {

	@Autowired
	private DataVService dataVService;

	/**
	 * 通用接口：综合数据大屏关键数据
	 * @param request request
	 * @return R
	 * 1、在线护士数
	 * 2、在线床位数
	 * 3、在线患者数
	 * 4、任务数据
	 * 5、护理数据
	 * 6、设备数据
	 * 7、管路数据
	 * 8、出科数据
	 * 9、医嘱数据
	 */
	@ApiOperation(value = "关键数据", notes = "关键数据")
	@GetMapping("/keydata")
	@Inner(value = false)
	public R keyData(HttpServletRequest request) {
		return R.ok(dataVService.keyData());
	}

	/**
	 * 滚动图的内容主要是预警信息
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "滚动图数据", notes = "滚动图数据")
	@GetMapping("/scrollingdata")
	@Inner(value = false)
	public R scrollingData(HttpServletRequest request) {
		return R.ok(dataVService.scrollingData());
	}

	@ApiOperation(value = "玫瑰图数据", notes = "玫瑰图数据")
	@GetMapping("/rosedata")
	@Inner(value = false)
	public R roseData(HttpServletRequest request) {
		return R.ok();
	}

	/**
	 * 综合数据大屏-环形图数据
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "环形图数据", notes = "环形图数据")
	@GetMapping("/ringdata")
	@Inner(value = false)
	public R ringData(HttpServletRequest request) {
		return R.ok(dataVService.ringData());
	}

	@ApiOperation(value = "目录栏图数据", notes = "目录栏图数据")
	@GetMapping("/catalogdata")
	@Inner(value = false)
	public R catalogData(HttpServletRequest request) {
		return R.ok(dataVService.catalogData());
	}
}
