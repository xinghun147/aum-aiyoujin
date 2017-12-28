package com.hjgj.aiyoujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjgj.aiyoujin.common.utils.ConfigUtil;
import com.hjgj.aiyoujin.common.utils.SessionUtil;
import com.hjgj.aiyoujin.common.utils.StringUtil;
import com.hjgj.permissions.model.User;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {

	@Autowired
	private ConfigUtil configUtil;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/index.html",method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/main.html",method = RequestMethod.GET)
    public String main() {
        return "main";
    }
    
    @ResponseBody
    @RequestMapping(value = "/navs.html",method = RequestMethod.GET)
    public String navs(HttpServletRequest request) {
    	String navs =  (String) request.getSession().getAttribute(SessionUtil.SessionSysteMenus);
    	return navs;
    }
    
    @ResponseBody
    @RequestMapping(value = "/checkPassword",method = RequestMethod.POST)
    public Boolean checkPassword(HttpServletRequest request,String password) {
    	User user = SessionUtil.getSessionUser(request);
    	if(StringUtil.makeMD5(password).equals(user.getPassword())){
    		return true;
    	}
    	return false;
    }
	
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
    

}
