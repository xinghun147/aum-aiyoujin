package com.hjgj.aiyoujin.core.model.vo;

import java.util.Date;

public class UserVO {
	    private String id;
	
	    private String openId;
	
	    private String nickname;
	
	    private Integer age;
	
	    private String mobile;
	
	    private String avatar;
	
	    private String gender;
	
	    private String email;
	
	    private String province;
	
	    private String city;
	
	    private Integer deleted;
	
	    private Date createTime;
	
	    private Date updateTime;
	
	    public String getId() {
	        return id;
	    }
	
	    public void setId(String id) {
	        this.id = id == null ? null : id.trim();
	    }
	
	    public String getOpenId() {
	        return openId;
	    }
	
	    public void setOpenId(String openId) {
	        this.openId = openId == null ? null : openId.trim();
	    }
	
	    public String getNickname() {
	        return nickname;
	    }
	
	    public void setNickname(String nickname) {
	        this.nickname = nickname == null ? null : nickname.trim();
	    }
	
	    public Integer getAge() {
	        return age;
	    }
	
	    public void setAge(Integer age) {
	        this.age = age;
	    }
	
	    public String getMobile() {
	        return mobile;
	    }
	
	    public void setMobile(String mobile) {
	        this.mobile = mobile == null ? null : mobile.trim();
	    }
	
	    public String getAvatar() {
	        return avatar;
	    }
	
	    public void setAvatar(String avatar) {
	        this.avatar = avatar == null ? null : avatar.trim();
	    }
	
	    public String getGender() {
	        return gender;
	    }
	
	    public void setGender(String gender) {
	        this.gender = gender == null ? null : gender.trim();
	    }
	
	    public String getEmail() {
	        return email;
	    }
	
	    public void setEmail(String email) {
	        this.email = email == null ? null : email.trim();
	    }
	
	    public String getProvince() {
	        return province;
	    }
	
	    public void setProvince(String province) {
	        this.province = province == null ? null : province.trim();
	    }
	
	    public String getCity() {
	        return city;
	    }
	
	    public void setCity(String city) {
	        this.city = city == null ? null : city.trim();
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
}
