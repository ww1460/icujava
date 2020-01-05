package com.pig4cloud.pigx.ccxxicu.api.vo.pressuresore;

import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.InevitablePressureSore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InevitablePressureSoreVo extends InevitablePressureSore {

	/**
	 * 床位名称
	 */
	private String bedName;
}
