package com.hjgj.aiyoujin.core.model.vo;

import com.hjgj.aiyoujin.core.model.UserAddress;

public class UserAddressVo extends UserAddress {

    private String userNickName;

    private String openId;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("UserAddressVo{");
        sb.append("userNickName='").append(userNickName).append('\'');
        sb.append(", openId='").append(openId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
