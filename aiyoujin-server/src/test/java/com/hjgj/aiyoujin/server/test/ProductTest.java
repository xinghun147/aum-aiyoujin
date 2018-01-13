package com.hjgj.aiyoujin.server.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hjgj.aiyoujin.core.common.exception.BusinessException;
import com.hjgj.aiyoujin.core.service.ProductService;


public class ProductTest extends BaseTest {

	
	@Autowired
	public ProductService productService;
	

	@Test
	public void query(){
		try {
			productService.updateQuantity("8ad8d8fb60b5d62b0160b5d7ae430001", 1);
		} catch (BusinessException e) {
			System.out.println(e.getErrorCode().getMsg());
		}
	}
	
}
