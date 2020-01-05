package com.pig4cloud.pigx.ccxxicu.api.entity.websocket;

import lombok.Data;

import java.util.Date;

@Data
public class TaskWarnInfo {

    //病床号
    private String bedNo;

    //病者姓名
    private String patientName;

    //任务名称
    private String taskName;

    //执行时间
    private Date executeTime;
}
