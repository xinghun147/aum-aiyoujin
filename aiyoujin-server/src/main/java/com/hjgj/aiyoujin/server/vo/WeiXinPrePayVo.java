package com.hjgj.aiyoujin.server.vo;

import java.math.BigDecimal;

public class WeiXinPrePayVo {

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机串
     */
    private String nonceStr;

    /**
     * 用户的openid
     */
    private String openId;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 用户id
     */
    private String userId;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WeiXinPrePayVo{" +
                "timeStamp='" + timeStamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", openId='" + openId + '\'' +
                ", productId='" + productId + '\'' +
                ", payMoney=" + payMoney +
                ", userId='" + userId + '\'' +
                '}';
    }
}