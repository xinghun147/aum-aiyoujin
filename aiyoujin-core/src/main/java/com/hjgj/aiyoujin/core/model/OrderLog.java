package com.hjgj.aiyoujin.core.model;

import java.util.Date;

public class OrderLog {
    private String id;

    private String orderId;

    private Integer status;

    private String prepayId;

    private String payId;

    private String payResultCode;

    private String payResultMsg;

    private String payReq;

    private String payResp;

    private Integer payType;

    private Date respTime;

    private Date reqTime;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId == null ? null : prepayId.trim();
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getPayResultCode() {
        return payResultCode;
    }

    public void setPayResultCode(String payResultCode) {
        this.payResultCode = payResultCode == null ? null : payResultCode.trim();
    }

    public String getPayResultMsg() {
        return payResultMsg;
    }

    public void setPayResultMsg(String payResultMsg) {
        this.payResultMsg = payResultMsg == null ? null : payResultMsg.trim();
    }

    public String getPayReq() {
        return payReq;
    }

    public void setPayReq(String payReq) {
        this.payReq = payReq == null ? null : payReq.trim();
    }

    public String getPayResp() {
        return payResp;
    }

    public void setPayResp(String payResp) {
        this.payResp = payResp == null ? null : payResp.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}