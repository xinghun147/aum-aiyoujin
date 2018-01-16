package com.hjgj.aiyoujin.core.common;

/**
 * 
* @ClassName: BizTypeEnum
* @Description: 业务类型
* @author Gaoguanjun gaoguanjun@32gold.com
* @date 2017年10月17日 下午2:06:32
*
 */
public enum OrderStatusEnum {
	
	ORDER_STATUS_PAY_UNPAID(0,"待支付"),
	ORDER_STATUS_PAY_PAID(1,"支付成功"),
	ORDER_STATUS_PAY_ERROR(2,"支付失败"),
	ORDER_STATUS_UNRECEIVE(3,"送出待收"),
	ORDER_STATUS_RETURN(4,"已退回"),
	ORDER_STATUS_SEND_SUCCESS (5,"送出成功"),
	ORDER_STATUS_RECEIVED(6,"已领取"),
	ORDER_STATUS_CASH_PROCESSING(7,"变现待处理"),
	ORDER_STATUS_CASH_SUCCESS(8,"变现成功"),
	ORDER_STATUS_CASH_FAIL(9,"变现失败"),
	ORDER_STATUS_PICKPROCESSING(10,"提货待处理"),
	ORDER_STATUS_DELIVERING(11,"物流中"),
	ORDER_STATUS_CONFIRM_RECEIPT(12,"已收货");
	
	
	private int code;
	private String name;
	private OrderStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "["+this.code+"]"+this.name();
	}
	
	/**
	 * 状态描述
	 * @param code
	 * @return
	 */
	public static String switchOrderStateName(int code) {
		switch (code) {
		case 0:
			return ORDER_STATUS_PAY_UNPAID.getName();
		case 1:
			return ORDER_STATUS_PAY_PAID.getName();
		case 2:
			return ORDER_STATUS_PAY_ERROR.getName();
		case 3:
			return ORDER_STATUS_UNRECEIVE.getName();
		case 4:
			return ORDER_STATUS_RETURN.getName();
		case 5:
			return ORDER_STATUS_SEND_SUCCESS.getName();
		case 6:
			return ORDER_STATUS_RECEIVED.getName();
		case 7:
			return ORDER_STATUS_CASH_PROCESSING.getName();
		case 8:
			return ORDER_STATUS_CASH_SUCCESS.getName();
		case 9:
			return ORDER_STATUS_CASH_FAIL.getName();
		case 10:
			return ORDER_STATUS_PICKPROCESSING.getName();
		case 11:
			return ORDER_STATUS_DELIVERING.getName();
		case 12:
			return ORDER_STATUS_CONFIRM_RECEIPT.getName();
		default:
			return "未知状态";
		}
	}

}
