package com.hjgj.aiyoujin.core.common.exception;


/**
 * 
 * @ClassName: BusinessException
 * @Description: 业务异常类
 * @author Gaoguanjun gaoguanjun@32gold.com
 * @date 2017年10月18日 下午5:01:33
 *
 */
public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2322478954247153563L;
	
	protected ErrorCode errorCode;
	
	private int code;
	private String msg;

	public BusinessException() {
		super();
	}
	
	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public BusinessException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
