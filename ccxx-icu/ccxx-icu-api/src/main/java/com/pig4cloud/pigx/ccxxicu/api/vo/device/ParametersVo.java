package com.pig4cloud.pigx.ccxxicu.api.vo.device;

import com.pig4cloud.pigx.ccxxicu.api.entity.device.Parameters;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParametersVo extends Parameters {

	/**
	 * 设备名称
	 */
	private String deviceName;


}
