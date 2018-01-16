package com.hjgj.aiyoujin.server.vo;

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
    //private BigDecimal payMoney;

    /**
     * 用户id
     */
    //private String userId;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 图片或视频url
     */
    private String mediaUrl;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
