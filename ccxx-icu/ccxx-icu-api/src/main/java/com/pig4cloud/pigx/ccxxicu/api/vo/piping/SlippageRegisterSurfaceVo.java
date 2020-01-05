package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import com.pig4cloud.pigx.ccxxicu.api.entity.piping.SlippageRegisterSurface;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SlippageRegisterSurfaceVo extends SlippageRegisterSurface {

	/**
	 * 护士姓名
	 */
	private String nurseName;

	/**
	 * 管道名称
	 */
	private String pipingName;


}
