package com.hjgj.aiyoujin.core.vo;



import java.io.Serializable;

public class AddressVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String contact;
	private String telephone;
	private String areaId;
	private String address;
	private String postCode;
	private String userId;
	private Integer isDefault;
	private Integer deleted;
	

	public AddressVo() {
		super();
	}

	public AddressVo(String id, Integer isDefault) {
		super();
		this.id = id;
		this.isDefault = isDefault;
	}
	

	public AddressVo(String id, String contact, String telephone, String areaId, String address, String postCode,
			String userId, Integer isDefault, Integer deleted) {
		super();
		this.id = id;
		this.contact = contact;
		this.telephone = telephone;
		this.areaId = areaId;
		this.address = address;
		this.postCode = postCode;
		this.userId = userId;
		this.isDefault = isDefault;
		this.deleted = deleted;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}