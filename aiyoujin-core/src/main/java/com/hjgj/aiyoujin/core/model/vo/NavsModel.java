package com.hjgj.aiyoujin.core.model.vo;


import java.io.Serializable;
import java.util.List;

public class NavsModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String icon;
	private String href;
	private boolean spread;
	private List<NavsModel> children;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean getSpread() {
		return spread;
	}
	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	public List<NavsModel> getChildren() {
		return children;
	}
	public void setChildren(List<NavsModel> children) {
		this.children = children;
	}
}
