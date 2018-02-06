package com.hjgj.aiyoujin.core.model.vo;

import com.hjgj.aiyoujin.core.common.excel.ExcelResources;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderPickExportVo {

    private String orderId;

    private String userNickName;

    private String openId;

    private String orderNo;

    private String productCode;

    private String productName;

    private BigDecimal buyAmount;

    private Integer orderStatus;

    private String expressNo;

    private String expressCompany;

    private String contact;

    private String address;

    private String phone;

    private BigDecimal expressFee;

    private Date createTime;

    private Date updateTime;

    private Date finishTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @ExcelResources(title = "用户昵称", order = 3)
    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    @ExcelResources(title = "用户OpenId", order = 2)
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @ExcelResources(title = "订单编号", order = 1)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @ExcelResources(title = "产品编号", order = 4)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @ExcelResources(title = "产品名称", order = 5)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ExcelResources(title = "产品金额", order = 6)
    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    @ExcelResources(title = "提货状态", order = 7)
    public String getOrderStatus() {
        if (orderStatus != null) {
            if (10 == orderStatus) {
                return "待发货";
            } else if (11 == orderStatus) {
                return "物流中";
            } else if (12 == orderStatus) {
                return "已收货";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    @ExcelResources(title = "快递单号", order = 12)
    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    @ExcelResources(title = "快递公司", order = 11)
    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    @ExcelResources(title = "联系人", order = 8)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @ExcelResources(title = "收货地址", order = 10)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ExcelResources(title = "联系电话", order = 9)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ExcelResources(title = "快递费用", order = 13)
    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ExcelResources(title = "提货时间", order = 14)
    public String getUpdateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (updateTime != null) {
            String pickTime = format.format(updateTime);
            return pickTime;
        } else {
            return null;
        }
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @ExcelResources(title = "完成时间", order = 15)
    public String getFinishTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (finishTime != null) {
            String pickTime = format.format(finishTime);
            return pickTime;
        } else {
            return null;
        }
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
