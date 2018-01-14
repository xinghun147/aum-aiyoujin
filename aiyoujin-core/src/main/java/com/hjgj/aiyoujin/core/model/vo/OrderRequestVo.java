package com.hjgj.aiyoujin.core.model.vo;

import java.util.Date;

public class OrderRequestVo {

    private String orderNo;

    private String userName;

    private String productName;

    private Date buyTimeStart;

    private Date buyTimeEnd;

    private Integer orderState;

    private Date startTime;

    private Date endTime;

    private String toUserName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getBuyTimeStart() {
        return buyTimeStart;
    }

    public void setBuyTimeStart(Date buyTimeStart) {
        this.buyTimeStart = buyTimeStart;
    }

    public Date getBuyTimeEnd() {
        return buyTimeEnd;
    }

    public void setBuyTimeEnd(Date buyTimeEnd) {
        this.buyTimeEnd = buyTimeEnd;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
