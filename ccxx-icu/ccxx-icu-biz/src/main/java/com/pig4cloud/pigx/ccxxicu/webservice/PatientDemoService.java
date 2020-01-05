package com.pig4cloud.pigx.ccxxicu.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface PatientDemoService {

	@WebMethod
	@WebResult(name = "String")
	public String getPatientName(@WebParam(name = "patientId") Integer patientId);


	@WebMethod
	public PatientDemo getPatient(@WebParam(name = "patientId")Integer patientId);


}
