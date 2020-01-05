package com.pig4cloud.pigx.ccxxicu.common.exception;

import com.pig4cloud.pigx.ccxxicu.common.msg.ReturnCode;
import lombok.Data;

/**
 * @author: bolei
 * @date：2018年5月18日 下午5:06:19
 * @description：https://juejin.im/post/5a93bb835188257a7b5ab9a4
 */

@Data
public class ApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	protected int code ;
	protected String message ;

	public ApiException(ReturnCode enums) {
		super();
		this.code = enums.getReturnCode();
		this.message = enums.getReturnMessage();
	}

	public ApiException(int code) {
		this.code = code;
	}

	public ApiException(int code,String message){
		this.code = code;
		this.message = message;
	}

	public ApiException(int code, Object... arguments) {
		this.code = code;
	}

	public ApiException(int code, Throwable cause) {
		this.code = code;
		this.message = cause.getMessage();
	}

	public ApiException(Throwable e){
		super(e);
		this.message = e.getMessage();
	}

	public static void main(String[] args) {
		String[] sexs = {"男性","女性","中性"};
		for(int i = 0; i < sexs.length; i++){
			if("中性".equals(sexs[i])){
				//throw new ApiException("你全家都是中性！");
				throw new ApiException(22,"你全家都是中性！");
			}else{
				System.out.println(sexs[i]);
			}
		}
	}


}
