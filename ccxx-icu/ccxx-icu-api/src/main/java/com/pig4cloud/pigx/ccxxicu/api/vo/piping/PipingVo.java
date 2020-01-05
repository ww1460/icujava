package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import com.pig4cloud.pigx.ccxxicu.api.entity.piping.Piping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PipingVo extends Piping {

	/**
	 * 引流液名称
	 */
	private String drainageName;



}
