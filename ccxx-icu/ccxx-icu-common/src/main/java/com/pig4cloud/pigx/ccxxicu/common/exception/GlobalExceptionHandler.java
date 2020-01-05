package com.pig4cloud.pigx.ccxxicu.common.exception;

import com.pig4cloud.pigx.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLDataException;

/**
 * @Author bolei
 * @Date 2019/8/24 16:09
 * @Version 1.0
 * @Des 描述
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理API异常
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(ApiException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleGlobalException(ApiException e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);
		return R.failed(e.getCode(),e.getLocalizedMessage());
	}

	/**
	 * 处理所有业务异常
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleGlobalException(BusinessException e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);
		return R.failed(e.getCode(),e.getLocalizedMessage());
	}

	/**
	 * 处理所有业务异常
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(SQLDataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleGlobalException(SQLDataException e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);
		return R.failed("数据库异常");
	}

}
