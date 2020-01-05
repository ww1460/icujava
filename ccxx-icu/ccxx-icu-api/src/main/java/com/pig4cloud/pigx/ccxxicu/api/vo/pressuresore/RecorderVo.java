package com.pig4cloud.pigx.ccxxicu.api.vo.pressuresore;

import com.pig4cloud.pigx.ccxxicu.api.entity.pressuresore.Record;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecorderVo extends Record {

	/**
	 * 护士姓名 
	 */
	private String nurseName;

}
