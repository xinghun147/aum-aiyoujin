package com.hjgj.aiyoujin.core.common.exception;


public enum EnumError implements ErrorCode {
	
	INSUFFICIENT_STOCK_ERROR(10001,"商品库存不足");

	private final int number;
	private final String errorMsg;

	private EnumError(int number,String errorMsg) {
		this.number = number;
		this.errorMsg = errorMsg;
	}
	
	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public String getMsg() {
		return errorMsg;
	}
	
	
	

}
