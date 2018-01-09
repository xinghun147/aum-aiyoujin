package com.hjgj.aiyoujin.admin.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.hjgj.aiyoujin.admin.common.utils.ConfigUtil;
import com.hjgj.aiyoujin.admin.common.utils.SessionUtil;
import com.hjgj.aiyoujin.core.model.vo.NavsModel;
import com.hjgj.permissions.api.IPermissionsApi;
import com.hjgj.permissions.model.Resource;
import com.hjgj.permissions.model.User;


/**
 * 拦截指定path，进行权限验证，及用户的本地session过期后，重新进行赋值
 */
public class PermissionsInterceptor extends HandlerInterceptorAdapter {
	
	private  ConfigUtil configUtil;
	private IPermissionsApi permissionsApi;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		Assertion assertion=AssertionHolder.getAssertion();
		//实际cas-client-core中org.jasig.cas.client.authentication.AuthenticationFilter已经进行了单点登录认证，这里主要是为了获得用户信息
		if(SessionUtil.getSessionUser(request) == null && assertion == null){
			//没有登录，跳转到没有登录页面
			response.sendRedirect(configUtil.getCasServerUrl());
			return false;
		}
		User user = SessionUtil.getSessionUser(request);
		if(user == null){
			//存储Session:用户登录名			
			user = permissionsApi.getUserByUsername(assertion.getPrincipal().getName());
			request.getSession().setAttribute(SessionUtil.SessionSystemLoginUserName,user);
		}
		
		//初始化菜单
		if(StringUtils.isBlank(SessionUtil.getSessionMenus(request))){
			List<Resource> resourceList = permissionsApi.queryListResource(user.getId(), "ayj");
		    request.getSession().setAttribute(SessionUtil.SessionSysteMenus, treeMenuList(resourceList));
		}
		//判断权限，没有权限，进入没有权限页面
		
		return true;	
		
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}

	public IPermissionsApi getPermissionsApi() {
		return permissionsApi;
	}

	public void setPermissionsApi(IPermissionsApi permissionsApi) {
		this.permissionsApi = permissionsApi;
	}
	
	//菜单树形结构  
    public  String treeMenuList(List<Resource> resourceList){ 
    	 List<NavsModel> navsList = new ArrayList<NavsModel>();
    	 for (Resource res : resourceList) {  
             if (res.getParentId()==0) {//父级菜单开始添加  
            	 NavsModel parent = new NavsModel();
            	 parent.setHref(res.getUrl());
            	 parent.setIcon("icon-computer");
            	 parent.setTitle(res.getName());
	             if (ifChilds(resourceList, res.getId())) {//存在子集  
	                 List<Resource> childs = new ArrayList<Resource>();  
	                 childs = getChildList(resourceList,res.getId(),childs);  
	                 List<NavsModel> childsList = new ArrayList<NavsModel>();
	                 for (Resource chil : childs) {
	                		NavsModel child = new NavsModel();
	                		child.setHref(chil.getUrl());
	                		child.setIcon("icon-computer");
	                		child.setTitle(chil.getName());
	            	 		childsList.add(child);  
					}
	                 parent.setChildren(childsList);
	             }  
	             navsList.add(parent);
	         }  
         }  
       return JSON.toJSONString(navsList);
	}
    
    
    //获取父id下的子集合  
    private static List<Resource> getChildList(List<Resource> list,int pId,List<Resource> reList) {  
        for (Resource testEntity : list) {  
            if (testEntity.getParentId()==pId) {//查询下级菜单  
                reList.add(testEntity);  
                if (ifChilds(list, testEntity.getId())) {  
                    getChildList(list, testEntity.getId(), reList);  
                }  
            }  
        }  
        return reList;  
    }  
      
    //判断是否存在子集  
    private static boolean ifChilds(List<Resource> list,int pId) {  
        boolean flag = false;  
        for (Resource testEntity : list) {  
            if (testEntity.getParentId()==pId) {  
                flag=true;  
                break;  
            }  
        }  
        return flag;  
    }
    
}
