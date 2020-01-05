package com.pig4cloud.pigx.ccxxicu.api.vo.arrange;


import com.pig4cloud.pigx.ccxxicu.api.entity.arrange.ArrangePeriod;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 周期班次展示
 * @author: yyj
 * @create: 2019-10-30 16:06
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class WeekShiftVo extends ArrangePeriod {
	/**
	 * 护士姓名
	 */
	private String realName;


}
