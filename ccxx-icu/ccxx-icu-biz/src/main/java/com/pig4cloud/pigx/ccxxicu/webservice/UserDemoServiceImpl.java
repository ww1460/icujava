package com.pig4cloud.pigx.ccxxicu.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author bolei
 * @Date 2019/10/30 15:24
 * @Version 1.0
 * @Des 描述
 */


@WebService(serviceName="userService",//对外发布的服务名
		targetNamespace="http://service.demo.example.com",//指定你想要的名称空间，通常使用使用包名反转
		endpointInterface="com.pig4cloud.pigx.ccxxicu.webservice.UserDemoService")
@Component
public class UserDemoServiceImpl implements UserDemoService{

	private Map<String, UserDemo> userMap = new HashMap<String, UserDemo>();
	public UserDemoServiceImpl() {
		System.out.println("向实体类插入数据");
		UserDemo user = new UserDemo();
		user.setId(111);
		user.setUserName("test1");

		userMap.put(user.getId()+"", user);

		user = new UserDemo();
		user.setId(112);
		user.setUserName("test2");
		userMap.put(user.getId()+"", user);

		user = new UserDemo();
		user.setId(113);
		user.setUserName("test3");
		userMap.put(user.getId()+"", user);
	}
	@Override
	public String getUserName(String userId) {
		return "userId为：" +userMap.get( userId).getUserName();
	}
	@Override
	public UserDemo getUser(String userId) {
		System.out.println("userMap是:"+userMap);
		return userMap.get(userId);
	}

}
