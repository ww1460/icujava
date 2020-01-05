package com.pig4cloud.pigx.ccxxicu.webservice;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: pigx
 * @description: Demo
 * @author: yyj
 * @create: 2019-11-01 13:54
 **/
@Data
public class PatientDemo implements Serializable {

	private static final long serialVersionUID = 7972035138330883590L;

	private String patientName;

	private Integer patientId;

	private String hospitalNum;




}
