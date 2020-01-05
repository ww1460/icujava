package com.pig4cloud.pigx.ccxxicu.common.msg;

public enum ResponseCode implements ReturnCode {


	/***********************************通用错误码*****************************************/
	/***********************************护士模块*****************************************/
	NURSE_FAIL(1000, "请求成功"),

	/***********************************护士长模块*****************************************/
	MATRON_FAIL(2000, "请求成功"),

	/***********************************患者模块*****************************************/
	PATIENT_FALL(3000,"患者请求成功"),
	PATIENT_HIS_ADD(3001,"从his添加患者失败，请联系管理员"),

	/***********************************任务模块*****************************************/
	TASK_FAIL(5000,"请求成功"),
	TASK_ADD_FAIL(5001,"主任务新增失败"),
	TASK_PATIENT(5002,"当前任务中没有患者"),
	TASK_NOT_TYPE_ID(5003,"当前任务没有任务，任务类型id ，请联系管理员"),
	TASK_ADD_RESULT(5004,"当前任务结束处理失败"),
	//子任务
	TASK_ADD_SUB_FAIL(5100,"子任务新增失败"),


	/***********************************任务模板模块*****************************************/
	TEMPLATE_ADD_FAIL(6001,"主任务模板新增失败"),
	TEMPLATE__ADD_SUB_FAIL(6002,"子任务模板新增失败"),
	TEMPLATE__PATIENT(6003,"当前任务模板中没有患者"),
	TEMPLATE__NOT_SUB(6004,"当前主模板中没有子模板"),
	//长期任务模板
	TIMING_ADD_FAIL(6100,"长期任务新增失败！！"),




	/***********************************   医嘱表 *****************************************/
	DOCTORS_ADVICE_IS_NULL(8000,"当前患者没有医嘱数据"),
	DOCTORS_ADVICE_HIS_ADD(8001,"接收his医嘱数据失败，请尽快联系管理员"),
	DOCTORS_ADVICE_HIS_NOT_SUB(8002,"接收his医嘱数据中没有医嘱内容数据，请尽快联系管理员"),
	DOCTORS_ADVICE_ADD(8003,"医嘱新增失败，请尽快联系管理员"),
	DOCTORS_ADVICE_UPDATE(8004,"医嘱修改失败，请尽快联系管理员"),
	//医嘱内容记录
	DOCTORS_ADVICE_PROJECT(8100,"没有医嘱内容数据，请联系管理员"),
	DOCTORS_ADVICE_PROJECT_ADD(8100,"医嘱内容新增失败，请联系管理员"),
	DOCTORS_ADVICE_PROJECT_UPDATE(8100,"医嘱内容修改失败，请联系管理员"),
	//医嘱执行记录
	DOCTORS_ADVICE_EXT_IS_NULL(8200,"没有医嘱执行记录数据，请联系管理员"),


	/***********************************   科室表 *****************************************/
	DEPT_FALL(9000,"没有查询到相对应的HIs科室数据！！1"),



	/***********************************   HIS 数据*****************************************/
	HIS_PATIENT_FAIL(13000,"HIS 患者数据接收失败，请尽快联系管理员！！"),
	HIS_DOCTORS_ADVICE_FAIL(18000,"HIS 医嘱接收失败，请尽快联系管理员！！"),
	HIS_DOCTORS_ADVICE_ADD_FALL(18001,"HIS 医嘱新增失败，请尽快联系管理员！！"),
	HIS_DOCTORS_ADVICE_PROJECT_FALL(18100,"HIS 医嘱项目无数据，请尽快联系管理员！！"),
	HIS_DOCTORS_ADVICE_EXT_FALL(18200,"HIS 医嘱执行记录接收 失败，请尽快联系管理员！！"),




	OK(0, "请求成功");




	private int code;
	private String message;


	private ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public int getReturnCode() {
		return code;
	}

	@Override
	public String getReturnMessage() {
		return message;
	}

}
