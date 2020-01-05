package com.pig4cloud.pigx.ccxxicu.common.utils;


import com.alibaba.fastjson.JSON;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.ItemWarnInfo;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class WatchMessageConstructor {

	public static void DeserializationData(String messageString) throws Exception {
		log.info("收到服务器推送消息为：{}", messageString);
		Message message = JSON.parseObject(messageString, Message.class);
		if(ObjectUtils.isEmpty(message) || ObjectUtils.isEmpty(message.getType())){
			log.error("服务器推送消息异常,缺少必须字段，异常信息为：{}", messageString);
			throw new Exception("服务器推送消息异常,缺少必须字段");
		}

		switch (message.getType()){
			case ITEM_WARN_LIST:
			case ITEM_WARN_PROMPT:
				List<ItemWarnInfo> itemWarnInfos = new ArrayList<>();
				for (Object item : message.getMessageBody()){
					//ItemWarnInfo itemWarnInfo = (ItemWarnInfo) item;
					//itemWarnInfos.add(itemWarnInfo);
					Field[] fields = item.getClass().getDeclaredFields();
					for (Field field:fields){
						field.setAccessible(true);
						log.info("获取的字段名：{}, value:{}", field.getName(),field.get(item));

					}
					Field field = item.getClass().getDeclaredField("map");
					field.setAccessible(true);
					Map map = JSON.parseObject(JSON.toJSONString(field.get(item)), Map.class);
					log.info("转换成的map为：{}", map);
				}
				//调用展示提示信息的activity
				break;
				//...其他几种消息类型类似
		}

	}
}
