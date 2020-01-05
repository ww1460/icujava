package com.pig4cloud.pigx.ccxxicu.api.entity.websocket;

import lombok.Data;

@Data
public class ItemWarnInfo {

    //病床号
    private String bedNo;

    //病者姓名
    private String patientName;

    //预警项目名
    private String itemName;

    //预警项目值
    private String itemValue;
}
