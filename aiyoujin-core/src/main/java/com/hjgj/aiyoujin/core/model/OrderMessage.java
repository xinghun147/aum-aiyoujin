package com.hjgj.aiyoujin.core.model;

import java.util.Date;

public class OrderMessage {
    private String id;

    private String orderId;

    private String content;

    private String imageUrl;

    private String title;

    private Integer deleted;

    private Date createTime;

    private Date updateTime;

    private String videoUrl;

    private String userId;

    private String orderNo;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderMessage{");
        sb.append("id='").append(id).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", videoUrl='").append(videoUrl).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}