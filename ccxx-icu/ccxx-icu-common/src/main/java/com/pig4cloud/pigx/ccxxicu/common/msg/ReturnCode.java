package com.pig4cloud.pigx.ccxxicu.common.msg;


/**
 * Title: 对于一般返回码应该具有方法功能的接口定义
 * Description: 对于一般返回码应该具有方法功能的接口定义，目前的用途是用于第三方扩展api返回的枚举类型ResponseCode使用
 * Copyright: Copyright(c) 2019
 * Company:
 *
 * @author bolei
 */
public interface ReturnCode {

	int getReturnCode();

	String getReturnMessage();

}
