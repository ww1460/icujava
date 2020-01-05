package com.pig4cloud.pigx.ccxxicu.api.entity.websocket;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    //消息类型
    private MessageTypeEnum type;
    //消息接收方--护士姓名
    private String receiver;
    //消息体--如果为预警提示，
    private List<Object> messageBody;

}
