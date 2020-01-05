package com.pig4cloud.pigx.ccxxicu.api.Bo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import lombok.Data;

import java.util.List;

@Data
public class ChangeShiftsBo {
	/**
	 * 交接班记录
	 */
	private ChangeShiftsRecord changeShiftsRecord;
	/**
	 * 交接班内容
	 */
	private List<ChangeShiftsTaskRecord> list;


}
