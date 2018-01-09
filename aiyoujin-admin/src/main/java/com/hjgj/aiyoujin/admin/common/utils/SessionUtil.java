package com.hjgj.aiyoujin.admin.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hjgj.permissions.model.Resource;
import com.hjgj.permissions.model.User;

import java.util.List;


public class SessionUtil {
	
	/**
	 * 系统登录用户名
	 */
	public static final String SessionSystemLoginUserName = "SessionSystemLoginUserName";
	
	/**
	 * 系统菜单
	 */
	public static final String SessionSysteMenus = "SessionSysteMenus";
	
	/**
	 * 清空session
	 */
	public static final void clearSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		
		session.removeAttribute(SessionUtil.SessionSystemLoginUserName);
		session.removeAttribute(SessionUtil.SessionSysteMenus);
		
		session.invalidate();//非必须，单点登出接收到服务器消息时，会自动销毁session
	}

	/**
	 * 返回session中的用户对象
	 * @param request
	 * @return
	 */
	public static final User getSessionUser(HttpServletRequest request)
	{
		return (User) request.getSession().getAttribute(SessionUtil.SessionSystemLoginUserName);
	}
	
	/**
	 * 返回session中的用户对象
	 * @param request
	 * @return
	 */
	public static final String getSessionMenus(HttpServletRequest request)
	{
		return  (String) request.getSession().getAttribute(SessionUtil.SessionSysteMenus);
	}
}
