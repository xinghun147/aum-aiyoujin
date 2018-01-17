package com.hjgj.aiyoujin.core.model;

import java.util.Date;

public class OrderNotify {
    private String id;

    private String orderId;

    private Integer orderType;

    private String notifyMsg;

    private Integer notifys;

    private Date notifyTime;

    private Date notifyCallTime;

    private Integer notifyState;

    private String notifyBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getNotifyMsg() {
        return notifyMsg;
    }

    public void setNotifyMsg(String notifyMsg) {
        this.notifyMsg = notifyMsg == null ? null : notifyMsg.trim();
    }

    public Integer getNotifys() {
        return notifys;
    }

    public void setNotifys(Integer notifys) {
        this.notifys = notifys;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Date getNotifyCallTime() {
        return notifyCallTime;
    }

    public void setNotifyCallTime(Date notifyCallTime) {
        this.notifyCallTime = notifyCallTime;
    }

    public Integer getNotifyState() {
        return notifyState;
    }

    public void setNotifyState(Integer notifyState) {
        this.notifyState = notifyState;
    }

    public String getNotifyBy() {
        return notifyBy;
    }

    public void setNotifyBy(String notifyBy) {
        this.notifyBy = notifyBy == null ? null : notifyBy.trim();
    }
}