package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import lombok.Data;

/**
 * @program: pigx
 * @description: 工作时间展示
 * @author: yyj
 * @create: 2019-11-06 17:25
 **/
@Data
public class WorkTimeVo {


	/**
	 * 床位名
	 */
	private String bedCode;
	/**
	 * 时间合计
	 */
	private Integer count;


	private Double proportion;


}
