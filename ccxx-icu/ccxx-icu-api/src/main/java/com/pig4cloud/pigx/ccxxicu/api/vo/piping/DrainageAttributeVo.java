package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import com.pig4cloud.pigx.ccxxicu.api.entity.piping.DrainageAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DrainageAttributeVo extends DrainageAttribute {

	/**
	 * 管道名称
	 */
	private String drainageName;

}
