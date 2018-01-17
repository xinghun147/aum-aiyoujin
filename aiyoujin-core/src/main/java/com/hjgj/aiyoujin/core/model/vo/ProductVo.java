package com.hjgj.aiyoujin.core.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String name;

    private String thumbPictures;//产品列表图片

    private BigDecimal buyPrice;
    
    private String [] middlePictures;//订单详情图片(带文字、和不带文字)
    
    private String [] largePictures; //产品详情图片
    
    private Integer quantity;//库存数量

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

	public void setMiddlePictures(String middlePictures) {
		this.middlePictures = middlePictures.split(",");
	}

	public String[] getLargePictures() {
		return largePictures;
	}

	public void setLargePictures(String largePictures) {
		this.largePictures = largePictures.split(",");
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}