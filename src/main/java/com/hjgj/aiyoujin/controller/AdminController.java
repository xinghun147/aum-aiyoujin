package com.hjgj.aiyoujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hjgj.aiyoujin.common.utils.ConfigUtil;
import com.hjgj.aiyoujin.common.utils.SessionUtil;

@Controller
@RequestMapping("/user")
public class AdminController extends BaseController {
	
	
	@Autowired
	private ConfigUtil configUtil;

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request ,String service) throws Exception
	{
		SessionUtil.clearSession(request);
		if(StringUtils.isBlank(service)){
			service = configUtil.getCasServiceUrl();
		}
		//被拦截器拦截处理
		return "redirect:" + configUtil.getCasServerUrl()+"/logout?service=" + service;
	}
	
	
	@RequestMapping("/allUsers.html")
	public String allUsers(){
		return "user/allUsers";
	}
	
	@RequestMapping("/addUser.html")
	public String addUser(){
		return "user/addUser";
	}
	
	@RequestMapping("/changePwd.html")
	public String changePwd(){
		return "user/changePwd";
	}
	
	@RequestMapping("/userInfo.html")
	public String userInfo(){
		return "user/userInfo";
	}
	
	
}
