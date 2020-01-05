package com.pig4cloud.pigx.ccxxicu.api.vo.task;

import com.pig4cloud.pigx.ccxxicu.api.entity.task.ChangeShiftsTaskRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2019/11/6 0006.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeShiftsTaskRecordVo extends ChangeShiftsTaskRecord {
    /**
     * 护士id
     */
    private String shiftNurseId;

    /**
     * 护士姓名
     */
    private String realName;
}
