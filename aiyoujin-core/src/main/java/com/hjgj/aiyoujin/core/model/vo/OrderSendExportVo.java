package com.hjgj.aiyoujin.core.model.vo;

import com.hjgj.aiyoujin.core.common.excel.ExcelResources;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderSendExportVo {

    private String orderId;

    private String openId;

    private String orderNo;

    private Integer orderStatus;

    private BigDecimal buyAmount;

    private Date createTime;

    private Date updateTime;

    private Date sentTime;

    private Date backTime;

    private Date receiveTime;

    private String productCode;

    private String productName;

    private String toUserNickName;

    private String fromUserNickName;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    @ExcelResources(title = "状态", order = 8)
    public String getOrderStatus() {
        if (orderStatus != null) {
            if (3 == orderStatus) {
                return "送出待接收";
            } else if (4 == orderStatus) {
                return "已退回";
            } else if (5 == orderStatus) {
                return "送出成功";
            } else if (6 == orderStatus) {
                return "领取成功";
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

    @ExcelResources(title = "产品金额", order = 6)
    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    //@ExcelResources(title = "接收时间", order = 10)
    public String getCreateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (6 == orderStatus) {
            String receiveTimess = format.format(createTime);
            return receiveTimess;
        } else if (5 == orderStatus) {
            String receiveTimess = format.format(receiveTime);
            return receiveTimess;
        } else {
            return null;
        }
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

    @ExcelResources(title = "赠送时间", order = 9)
    public String getSentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (6 == orderStatus) {
            return null;
        } else {
            String sentTimess = format.format(sentTime);
            return sentTimess;
        }
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @ExcelResources(title = "退回时间", order = 11)
    public String getBackTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (4 == orderStatus) {
            String sentTimess = format.format(backTime);
            return sentTimess;
        } else {
            return null;
        }
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    @ExcelResources(title = "接收时间", order = 10)
    public String getReceiveTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (6 == orderStatus) {
            String receiveTimess = format.format(createTime);
            return receiveTimess;
        } else if (5 == orderStatus) {
            String receiveTimess = format.format(receiveTime);
            return receiveTimess;
        } else {
            return null;
        }
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    @ExcelResources(title = "转送至用户名", order = 7)
    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

    @ExcelResources(title = "用户名", order = 3)
    public String getFromUserNickName() {
        return fromUserNickName;
    }

    public void setFromUserNickName(String fromUserNickName) {
        this.fromUserNickName = fromUserNickName;
    }
}
