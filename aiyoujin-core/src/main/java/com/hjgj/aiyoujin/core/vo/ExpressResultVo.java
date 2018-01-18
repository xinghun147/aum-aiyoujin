package com.hjgj.aiyoujin.core.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: ExpressResultVo
* @Description: 快递详情
* @author Gaoguanjun gaoguanjun@32gold.com
* @date 2017年12月6日 下午4:57:58
*
 */
public class ExpressResultVo implements Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String issign;//是否本人签收 1-是
	private String deliverystatus;//配送状态
	private String number;//快递单号
	private String type;//快递公司
	private List<ExpressResultDetailVo> list;//快递明细

	
	public String getIssign() {
		return issign;
	}
	public void setIssign(String issign) {
		this.issign = issign;
	}
	public String getDeliverystatus() {
		return deliverystatus;
	}
	public void setDeliverystatus(String deliverystatus) {
		this.deliverystatus = deliverystatus;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ExpressResultDetailVo> getList() {
		return list;
	}
	public void setList(List<ExpressResultDetailVo> list) {
		this.list = list;
	}
	
	
}