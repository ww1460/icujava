package com.pig4cloud.pigx.ccxxicu.websocket;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pigx.ccxxicu.api.entity.websocket.Message;
import com.pig4cloud.pigx.ccxxicu.service.impl.scenes.WatchOpenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ServerEndpoint("/ws/{username}")
@Slf4j
@Component
public class WebSocketServer {
	@Resource
	private WatchOpenServiceImpl watchOpenService;

	private static int onlineCount = 0;
	private static Map<String, Session> clients = new ConcurrentHashMap<>();
	private static Map<String, String> idNameMap = new ConcurrentHashMap<>();
	private static Map<String, List<Message>> unSentMessage = new ConcurrentHashMap<>();
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

	//创建连接时初始化信息
	@OnOpen
	public void onOpen(@PathParam("username") String username, Session session) {
		addOnlineCount();
		idNameMap.put(session.getId(), username);
		clients.put(username, session);
		System.out.println("已连接,username = " + username);
		if (unSentMessage.containsKey(username)) {
			fixedThreadPool.execute(() -> {
				try {
					processUnsentMessage(username);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		log.info("当前在线护士人数：{}", getOnlineCount());
	}

	//连接关闭时回收资源
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("连接关闭, sessionId = " + session.getId() + "，关闭原因为：" + closeReason.getCloseCode());
		String username = idNameMap.get(session.getId());
		idNameMap.remove(session.getId());
		clients.remove(username);
		subOnlineCount();
	}

	//收到客户端发送的消息，根据type调用接口
	@OnMessage
	public void onMessage(@PathParam("username") String username, String messageString, Session session) {
		System.out.println("收到的消息为：" + messageString + ",username:" + username);
		Message message = JSON.parseObject(messageString, Message.class);
		System.out.println("转换后的消息为：" + message);
		//设置发送者的姓名
		//message.setSender(idNameMap.get(session.getId()));
		//接收者就是自己
		message.setReceiver(idNameMap.get(session.getId()));
		switch (message.getType()) {
			case TASK_LIST_QUERY:
				message = watchOpenService.taskList(username);
				break;
			case TASK_WARN_LIST_QUERY:
				message = watchOpenService.taskWarm(username);
				break;
			case ITEM_WARN_LIST_QUERY:
				message = watchOpenService.projectWarm(username);
				break;
			default:
				break;
		}
		try {
			if (!"ALL".equals(message.getReceiver())) {
				sendMessageTo(message);
			} else {
				sendMessageAll(message);
			}
		} catch (Exception e) {
			log.error("消息发送失败，异常信息为:{}, 消息：{}", e.getMessage(), message);
		}

	}

	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	/**
	 * 发送消息给指定用户
	 * 返回true发送成功
	 * 返回false发送失败--当前护士未建立连接或连接意外断开
	 *
	 * @param message
	 * @return
	 */
	public static boolean sendMessageTo(Message message) throws Exception {
		if (ObjectUtils.isEmpty(message) || StringUtils.isEmpty(message.getReceiver())) {
			throw new Exception("消息和接收人都不可为空");
		}
		String messageBody = JSON.toJSONString(message);
		String receiver = message.getReceiver();
		if (clients.containsKey(receiver)) {
			Session session = clients.get(receiver);
			Future<Void> result = session.getAsyncRemote().sendText(messageBody);
			log.info("send message to {},result：{}", receiver, JSON.toJSONString(result));
		} else {
			log.info("{}护士连接已断开，消息发送失败", message.getReceiver());
			//如果内存中已经有之前未发的消息,直接添加进去就可
			if (unSentMessage.containsKey(receiver)) {
				unSentMessage.get(receiver).add(message);
			} else {
				//初始化消息队列，并放入缓存中
				List<Message> unSentMessageList = new ArrayList<>();
				unSentMessageList.add(message);
				unSentMessage.put(receiver, unSentMessageList);
			}
			log.info("未发送的数据集合：{}", unSentMessage);
			return false;
		}
		return true;
	}

	/**
	 * 给当前所有在线的用户发消息
	 * 如果想做广播，应当获取所有应当发送人员的集合，然后分别发送
	 *
	 * @param message
	 */
	private void sendMessageAll(Message message) throws Exception {
		for (Session session : clients.values()) {
			if (ObjectUtils.isEmpty(message) || StringUtils.isEmpty(message.getReceiver())) {
				throw new Exception("消息和接收人都不可为空");
			}
			session.getAsyncRemote().sendText(JSON.toJSONString(message.getMessageBody()));
		}
	}

	/**
	 * 维护在线的人数
	 *
	 * @return
	 */
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	private static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}

	private static synchronized void subOnlineCount() {
		WebSocketServer.onlineCount--;
	}

	/**
	 * 处理未发送成功的消息
	 *
	 * @param receiver
	 * @throws Exception
	 */
	private void processUnsentMessage(String receiver) throws Exception {
		List<Message> waitSendMessage = unSentMessage.get(receiver);
		for (Message message : waitSendMessage) {
			sendMessageTo(message);
			//如果有多条消息，中间停顿5s在发送，保证护士可以看到消息
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		unSentMessage.remove(receiver);
	}
}
