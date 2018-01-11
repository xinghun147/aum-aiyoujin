
package com.hjgj.aiyoujin.admin.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Charsets;
import com.hjgj.aiyoujin.admin.common.interceptor.PermissionsInterceptor;
import com.hjgj.aiyoujin.admin.common.utils.ConfigUtil;
import com.hjgj.permissions.api.IPermissionsApi;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private IPermissionsApi permissionsApi;
	@Autowired
	private ConfigUtil configUtil;
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	PermissionsInterceptor permissionsInterceptor =  new PermissionsInterceptor();
    	permissionsInterceptor.setConfigUtil(configUtil);
    	permissionsInterceptor.setPermissionsApi(permissionsApi);
        registry.addInterceptor(permissionsInterceptor).addPathPatterns("/**");
    }

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter(Charsets.UTF_8);
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		converters.add(stringHttpMessageConverter);
		converters.add(new FastJsonHttpMessageConverter());
		super.configureMessageConverters(converters);
	}
    
    
}
