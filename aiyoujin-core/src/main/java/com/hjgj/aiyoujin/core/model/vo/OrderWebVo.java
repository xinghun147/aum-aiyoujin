package com.hjgj.aiyoujin.core.model.vo;

import com.hjgj.aiyoujin.core.model.ProductPicture;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderWebVo {

    /**
     * 订单主键
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String orderStatus;
    
    private Integer statusCode;

    /**
     * 用户主键
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 来源 昵称
     */
    private String fromNickName;
    
    /**
     * 赠送人头像
     */
    private String fromAvatar;

    /**
     * 到达 昵称
     */
    private String toNickName;

    /**
     * 产品主键
     */
    private String productId;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 买入价格
     */
    private BigDecimal buyAmount;

    /**
     * 购买原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 出手价格
     */
    private BigDecimal sellAmount;

    /**
     * 产品图片
     */
    private String productImgUrl;
    
    private List<ProductPicture> middlePictures;//订单详情图片(带文字、和不带文字)
    
    /**
     * 留言
     */
    private String message;
    /**
     * 留言图片
     */
    private String imageUrl;
    /**
     * 留言视频
     */
    private String videoUrl;
    
    /**
     * 是否留言  1是0否
     */
    private Integer isMsg;
    
    /**
     * 物流公司
     */
    private String expressCompany;
    
    /**
     * 物流编号
     */
    private String expressNo;

    /**
     * 接收时间
     */
    private Date receiveTime;

    /**
     * 转送时间
     */
    private Date sentTime;

    /**
     * 变现时间
     */
    private Date sellTime;

    /**
     * 提货时间
     */
    private Date pickupTime;

    /**
     * 回滚时间
     */
    private Date backTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    private Date updateTime;


	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getFromAvatar() {
		return fromAvatar;
	}

	public void setFromAvatar(String fromAvatar) {
		this.fromAvatar = fromAvatar;
	}

	public Integer getIsMsg() {
		return isMsg;
	}

	public void setIsMsg(Integer isMsg) {
		this.isMsg = isMsg;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public List<ProductPicture> getMiddlePictures() {
		return middlePictures;
	}

	public void setMiddlePictures(List<ProductPicture> middlePictures) {
		this.middlePictures = middlePictures;
	}

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
