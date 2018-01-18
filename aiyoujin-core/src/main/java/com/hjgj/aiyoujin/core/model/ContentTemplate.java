package com.hjgj.aiyoujin.core.model;

import java.util.Date;

public class ContentTemplate {
    private String id;

    private String wxtemplateId;

    private Integer wxtemplateType;

    private String templateContent;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWxtemplateId() {
        return wxtemplateId;
    }

    public void setWxtemplateId(String wxtemplateId) {
        this.wxtemplateId = wxtemplateId == null ? null : wxtemplateId.trim();
    }

    public Integer getWxtemplateType() {
        return wxtemplateType;
    }

    public void setWxtemplateType(Integer wxtemplateType) {
        this.wxtemplateType = wxtemplateType;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentTemplate{");
        sb.append("id='").append(id).append('\'');
        sb.append(", wxtemplateId='").append(wxtemplateId).append('\'');
        sb.append(", wxtemplateType=").append(wxtemplateType);
        sb.append(", templateContent='").append(templateContent).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", updateBy='").append(updateBy).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}