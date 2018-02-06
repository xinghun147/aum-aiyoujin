package com.hjgj.aiyoujin.core.model.vo;

import com.hjgj.aiyoujin.core.common.excel.ExcelResources;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderSellExportVo {

    private String orderNo;

    private String openId;

    private String userNickName;

    private String productCode;

    private String productName;

    private BigDecimal buyAmount;

    private Integer orderStatus;

    private BigDecimal sellAmount;

    private Date createTime;

    private Date updateTime;

    private Date sellTime;

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

    @ExcelResources(title = "用户昵称", order = 3)
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

    @ExcelResources(title = "产品金额", order = 6)
    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    @ExcelResources(title = "状态", order = 8)
    public String getOrderStatus() {
        if (orderStatus != null) {
            if (7 == orderStatus) {
                return "待处理";
            } else if (8 == orderStatus) {
                return "变现成功";
            } else if (9 == orderStatus) {
                return "变现失败";
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

    @ExcelResources(title = "变现金额", order = 7)
    public BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ExcelResources(title = "变现时间", order = 9)
    public String getUpdateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (7 == orderStatus) {
            return format.format(updateTime);
        } else if (8 == orderStatus) {
            return format.format(sellTime);
        } else if (9 == orderStatus) {
            return format.format(updateTime);
        } else {
            return null;
        }
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }
}
