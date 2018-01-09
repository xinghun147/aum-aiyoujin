package com.hjgj.aiyoujin.admin.common;


public enum ResultStatus {
	
    SUCCESS(100, "成功"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录"),
    USER_CAPTCHA_ERROR(-1004, "验证码错误"),
    USER_NOT_PASSWORD(-1005, "用户未设置登录密码"),
    USER_PHONE_ERROR(-1006, "手机号码格式错误"),
    UPLOAD_NOT_IMAGE(-1007, "非法图片格式"),
    USER_BANKCARD_NULL(-1015, "银行卡不不在"),
    USER_BANKCARD_UNAVAILABLE(-1016, "平台不支持此银行卡"),
    USER_PARAMS_ISNULL(-1017, "绑定信用卡必填参数为空"),

    
    //产品
    PRODUCT_PARAMS_ISNULL(-2001, "参数为空"),
    PRODUCT_NO_EXIST(-2002, "产品不存在"),
    
    //订单类
    ORDER_PARAMS_ISNULL(-3001, "参数为空"),
    ORDER_PARAMS_AMOUNT_LTE_ZERO(-3002, "金额不能小于等于0"),
    ORDER_PARAMS_WEIGHT_LTE_ZERO(-3003, "克重不能小于等于0"),
    ORDER_PARAMS_BOTH_ISNULL(-3004, "购买克重和金额不能全部为空"),
    
    
    
    
    UNAUTHORISED(401,"未登录授权"),
	SYSTEM_ERROR(1000,"系统错误"),
	ERROR_NOT_USER_ID(2000,"用户ID未输入"),
	ERROR_EMPTY_VAL_RETURNED(2002,"返回值为空"),
	ERROR_NOT_FIND_DATA(2003,"没有查询到数据"),
	ERROR_OPERATION_FREQUENT(2004,"操作过于频繁");
	
	private int code;
    private String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
    public static ResultStatus getResultStatus(Integer  code) {   
        for  (ResultStatus status : ResultStatus.values()) {   
            if  (status.getCode() == code) {
                return  status;   
            }
        }   
        return null ;   
    }
    
}
