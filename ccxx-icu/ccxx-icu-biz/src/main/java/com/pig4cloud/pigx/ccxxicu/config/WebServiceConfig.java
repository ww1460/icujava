package com.pig4cloud.pigx.ccxxicu.config;

import com.pig4cloud.pigx.ccxxicu.webservice.UserDemoService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @Author bolei
 * @Date 2019/10/30 15:33
 * @Version 1.0
 * @Des 描述
 */


@Configuration
public class WebServiceConfig {

	@Autowired
	private Bus bus;

	@Autowired
	UserDemoService userService;

	/**
	 * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
	 * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/user?wsdl
	 * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/soap/user?wsdl
	 * @return
	 */
	@SuppressWarnings("all")
	@Bean
	public ServletRegistrationBean disServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
	}

	/** JAX-WS
	 * 站点服务
	 * **/
//	@Bean
//	public Endpoint endpoint() {
//		EndpointImpl endpoint = new EndpointImpl(bus, userService);
//		endpoint.publish("/user");
//		return endpoint;
//	}

}
