package com.pig4cloud.pigx.ccxxicu.api.entity.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 腕表WebSocket
 * 客户端和服务端交互的消息类型
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    TASK_LIST(0, "任务列表"),
    ITEM_WARN_LIST(1, "项目预警列表"),
    TASK_WARN_LIST(2, "任务预警列表"),
    ADD_TASK_PROMPT(3, "新增任务提示"),
    ITEM_WARN_PROMPT(4, "项目预警提示"),
    TASK_WARN_PROMPT(5, "任务预警提示"),
    TASK_LIST_QUERY(6, "任务列表查询"),
    ITEM_WARN_LIST_QUERY(7, "项目预警列表查询"),
    TASK_WARN_LIST_QUERY(8, "任务预警列表查询"),
    ;

    private int code;

    private String des;

    /**
     * 由String 转换为枚举值
     *
     * @param des
     * @return
     */
    public static MessageTypeEnum getFrom(String des) {
        for (MessageTypeEnum tmp : MessageTypeEnum.values()) {
            if (des.equals(tmp.getDes())) {
                return tmp;
            }
        }
        return null;
    }
}
