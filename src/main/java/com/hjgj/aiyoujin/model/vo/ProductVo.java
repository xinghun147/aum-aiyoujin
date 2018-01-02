package com.hjgj.aiyoujin.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String name;

    private String thumbPictures;

    private BigDecimal buyPrice;
    
    private String [] middlePictures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

	public String getThumbPictures() {
		return thumbPictures;
	}

	public void setThumbPictures(String thumbPictures) {
		this.thumbPictures = thumbPictures;
	}

	public String[] getMiddlePictures() {
		return middlePictures;
	}

	public void setMiddlePictures(String[] middlePictures) {
		this.middlePictures = middlePictures;
	}
}