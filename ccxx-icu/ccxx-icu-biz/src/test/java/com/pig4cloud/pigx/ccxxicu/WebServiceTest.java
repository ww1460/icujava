package com.pig4cloud.pigx.ccxxicu;

import com.pig4cloud.pigx.ccxxicu.webservice.PatientDemoService;
import com.pig4cloud.pigx.ccxxicu.webservice.UserDemoService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * @Author bolei
 * @Date 2019/10/30 15:39
 * @Version 1.0
 * @Des 描述
 */


public class WebServiceTest {

	public static void main(String[] args) {
		//WebServiceTest.main1();
		WebServiceTest.main2();
		//main3();
	}


	public static  void main3() {

		try {
			JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

			factoryBean.setAddress("http://127.0.0.1:4011/soap/patient?wsdl");

			factoryBean.setServiceClass(PatientDemoService.class);

			PatientDemoService patientDemoService = (PatientDemoService) factoryBean.create();

			String patientName = patientDemoService.getPatientName(5);

			System.out.println("患者名：" + patientName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}










	/**
	 * 1.代理类工厂的方式,需要拿到对方的接口地址
	 */
	public static void main1() {
		try {
			// 接口地址
			String address = "http://127.0.0.1:4011/soap/user?wsdl";
			// 代理工厂
			JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
			// 设置代理地址
			jaxWsProxyFactoryBean.setAddress(address);
			// 设置接口类型
			jaxWsProxyFactoryBean.setServiceClass(UserDemoService.class);
			// 创建一个代理接口实现
			UserDemoService us = (UserDemoService) jaxWsProxyFactoryBean.create();
			// 数据准备
			String userId = "111";
			// 调用代理接口的方法调用并返回结果
			String result = us.getUserName(userId);
			System.out.println("返回结果:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2：httpClient调用
	 */
	public static void main2() {
		try {

			final String SERVER_URL = "http://127.0.0.1:4011/soap/user"; // 定义需要获取的内容来源地址

			HttpPost request = new HttpPost(SERVER_URL);
			String soapRequestData = getRequestXml();
			HttpEntity re = new StringEntity(soapRequestData, HTTP.UTF_8);
			request.setHeader("Content-Type","application/soap+xml; charset=utf-8");

			request.setEntity(re);

			HttpResponse httpResponse = new DefaultHttpClient().execute(request);


			if (httpResponse.getStatusLine().getStatusCode() ==200) {
				String xmlString = EntityUtils.toString(httpResponse.getEntity());
				String jsonString = parseXMLSTRING(xmlString);


				System.out.println("---"+jsonString);

			}


		} catch (Exception e) {


			e.printStackTrace();

		}

	}

	public static String parseXMLSTRING(String xmlString) {
		String returnJson = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(String.valueOf(new InputSource(new StringReader(xmlString))));
			Element root = doc.getDocumentElement();//根节点
			Node node = root.getFirstChild();
			while (!node.getNodeName().equals("String")) {
				node = node.getFirstChild();
			}
			if (node.getFirstChild() != null) returnJson = node.getFirstChild().getNodeValue();
			System.out.println("获取的返回参数为：" + returnJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnJson;
	}

	private static String getRequestXml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
		sb.append(" xmlns:sam=\"http://service.springboot.huaxun.com/\" ");  //前缀,这一串由服务端提供
		sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		sb.append(" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		sb.append("<soap:Header/>");
		sb.append("<soap:Body>");
		sb.append("<sam:getUserName>");  //“getUserName”调用方法名
		sb.append("<userId>111</userId>"); //传参,“userId”是配置在服务端的参数名称，“111”是要传入的参数值
		sb.append("</sam:getUserName>");
		sb.append("</soap:Body>");
		sb.append("</soap:Envelope>");
		return sb.toString();
	}

}
