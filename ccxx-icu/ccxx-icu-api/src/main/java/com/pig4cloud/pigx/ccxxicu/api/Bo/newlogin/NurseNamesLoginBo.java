package com.pig4cloud.pigx.ccxxicu.api.Bo.newlogin;

import lombok.Data;

import java.util.List;

@Data
public class NurseNamesLoginBo {

	/**
	 * username和password为空，mac地址不为空，免登录流程，查Mac表，返回一个json字符串
	 *{
	 *     "ismac":true,
	 *     "data":["小李","小张","小王"]
	 * }
	 * @return
	 */
	private boolean ismac;

	public List<String> data;
}
