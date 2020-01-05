package com.pig4cloud.pigx.ccxxicu.api.vo.patient;

import com.pig4cloud.pigx.ccxxicu.api.entity.patient.AdverseDrugReactionsRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdverseDrugReactionsRecordVo extends AdverseDrugReactionsRecord {

	/**
	 * 护士姓名
	 */
	private String nurseName;


}
