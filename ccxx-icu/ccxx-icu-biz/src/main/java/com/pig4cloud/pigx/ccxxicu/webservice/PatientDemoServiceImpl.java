package com.pig4cloud.pigx.ccxxicu.webservice;

import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * @program: pigx
 * @description: 实现
 * @author: yyj
 * @create: 2019-11-01 14:12
 **/
@WebService( serviceName="patientDemoService",//对外发布的服务名
		targetNamespace="http://service.demo.example.com",//指定你想要的名称空间，通常使用使用包名反转
		endpointInterface="com.pig4cloud.pigx.ccxxicu.webservice.PatientDemoService")
@Component
public class PatientDemoServiceImpl implements PatientDemoService {


	@Autowired
	private PatientService patientService;

	@Override
	public String getPatientName(Integer patientId) {
		System.out.println("患者id："+patientId);

		String name = patientService.getById(patientId).getName();


		return name;
	}

	@Override
	public PatientDemo getPatient(Integer patientId) {

		System.out.println("患者id："+patientId);

		Patient byId = patientService.getById(patientId);

		PatientDemo patientDemo = new PatientDemo();

		patientDemo.setHospitalNum(byId.getIdNumber());
		patientDemo.setPatientId(patientId);
		patientDemo.setPatientName(byId.getName());

		return null;
	}
}
