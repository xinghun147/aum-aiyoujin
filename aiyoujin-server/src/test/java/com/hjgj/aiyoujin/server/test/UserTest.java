package com.hjgj.aiyoujin.server.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hjgj.aiyoujin.core.common.exception.BusinessException;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.service.ProductService;
import com.hjgj.aiyoujin.core.service.UserService;


public class UserTest extends BaseTest {

	
	@Autowired
	public UserService userService;
	

	@Test
	public void insertUser(){
		try {
			User user = new User();
			user.setAvatar("sdfsdfsdfsfd");
			user.setOpenId("sdfsdfsd");
			user.setNickname("test");
			user = userService.insertUser(user);
			System.out.println(JSON.toJSON(user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
