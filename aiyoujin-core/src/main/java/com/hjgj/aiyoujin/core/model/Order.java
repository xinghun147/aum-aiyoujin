package com.hjgj.aiyoujin.core.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private String id;

    private String code;

    private String userId;

    private Date createTime;

    private Date updateTime;

    private Date sentTime;

    private Date sellTime;

    private Date pickupTime;

    private Date backTime;

    private Date finishTime;

    private BigDecimal buyAmount;

    private BigDecimal sellAmount;

    private Long expressFee;

    private String expressCompany;

    private String expressNo;

    private String sourceOrderId;

    private String fromOrderId;

    private String toOrderId;

    private String address;

    private String reason;

    private String productId;

    private Integer status;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Long getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Long expressFee) {
        this.expressFee = expressFee;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public String getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(String sourceOrderId) {
        this.sourceOrderId = sourceOrderId == null ? null : sourceOrderId.trim();
    }

    public String getFromOrderId() {
        return fromOrderId;
    }

    public void setFromOrderId(String fromOrderId) {
        this.fromOrderId = fromOrderId == null ? null : fromOrderId.trim();
    }

    public String getToOrderId() {
        return toOrderId;
    }

    public void setToOrderId(String toOrderId) {
        this.toOrderId = toOrderId == null ? null : toOrderId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id='").append(id).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", sentTime=").append(sentTime);
        sb.append(", sellTime=").append(sellTime);
        sb.append(", pickupTime=").append(pickupTime);
        sb.append(", backTime=").append(backTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", buyAmount=").append(buyAmount);
        sb.append(", sellAmount=").append(sellAmount);
        sb.append(", expressFee=").append(expressFee);
        sb.append(", expressCompany='").append(expressCompany).append('\'');
        sb.append(", expressNo='").append(expressNo).append('\'');
        sb.append(", sourceOrderId='").append(sourceOrderId).append('\'');
        sb.append(", fromOrderId='").append(fromOrderId).append('\'');
        sb.append(", toOrderId='").append(toOrderId).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", status=").append(status);
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}