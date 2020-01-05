package com.pig4cloud.pigx.ccxxicu.common.exception;

import com.pig4cloud.pigx.ccxxicu.common.msg.ReturnCode;
import lombok.Data;

/**
 * @author: bolei
 * @date：2017年4月30日 上午11:18:04
 * @description：业务异常
 */

@Data
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	protected int code ;
	protected String message ;

	public BusinessException(ReturnCode enums) {
		super();
		this.message = enums.getReturnMessage();
	}

	public BusinessException(int code) {
		this.code = code;
	}

	public BusinessException(int code,String message){
		this.code = code;
		this.message = message;
	}

	public BusinessException(int code, Object... arguments) {
		this.code = code;
	}

	public BusinessException(int code, Throwable cause) {
		this.code = code;
		this.message = cause.getMessage();
	}

	public BusinessException(Throwable e){
		super(e);
		this.message = e.getMessage();
	}

	public static void main(String[] args) {
		String[] sexs = {"男性","女性","中性"};
		for(int i = 0; i < sexs.length; i++){
			if("中性".equals(sexs[i])){
				//throw new BusinessException("你全家都是中性！");
				throw new BusinessException(22,"你全家都是中性！");
			}else{
				System.out.println(sexs[i]);
			}
		}
	}

}