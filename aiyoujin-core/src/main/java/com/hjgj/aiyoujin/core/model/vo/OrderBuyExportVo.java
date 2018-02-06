package com.hjgj.aiyoujin.core.model.vo;

import com.hjgj.aiyoujin.core.common.excel.ExcelResources;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderBuyExportVo {

    private String orderNo;

    private String openId;

    private String userNickName;

    private String productCode;

    private String productName;

    private BigDecimal buyAmount;

    private Integer orderStatus;

    private Date createTime;

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @ExcelResources(title = "订单编号", order = 1)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @ExcelResources(title = "用户OpenId", order = 2)
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @ExcelResources(title = "用户名称", order = 3)
    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
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

    @ExcelResources(title = "订单金额", order = 6)
    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    @ExcelResources(title = "支付状态", order = 7)
    public String getOrderStatus() {
        if (orderStatus != null) {
            if (0 == orderStatus) {
                return "待支付";
            } else if (1 == orderStatus) {
                return "支付成功";
            } else if (2 == orderStatus) {
                return "支付失败";
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

    @ExcelResources(title = "买入时间", order = 8)
    public String getCreateTime() {
        if (createTime != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String butTime = format.format(createTime);
            return butTime;
        } else {
            return null;
        }
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
