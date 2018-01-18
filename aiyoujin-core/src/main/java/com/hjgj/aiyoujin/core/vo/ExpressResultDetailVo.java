package com.hjgj.aiyoujin.core.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: ExpressResultDetailVo
* @Description: 快递内容详情
* @author Gaoguanjun gaoguanjun@32gold.com
* @date 2017年12月6日 下午4:56:53
*
 */
public class ExpressResultDetailVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String time;//时间
	private String status;//状态
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}