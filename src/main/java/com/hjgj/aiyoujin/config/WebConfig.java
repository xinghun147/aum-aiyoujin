
package com.hjgj.aiyoujin.config;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Charsets;
import com.hjgj.aiyoujin.common.interceptor.PermissionsInterceptor;
import com.hjgj.aiyoujin.common.utils.ConfigUtil;
import com.hjgj.permissions.api.IPermissionsApi;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private IPermissionsApi permissionsApi;
	@Autowired
	private ConfigUtil configUtil;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/static/**").addResourceLocations("classpath:/static/")
        	.setCachePeriod(5).setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES).cachePublic());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	PermissionsInterceptor permissionsInterceptor =  new PermissionsInterceptor();
    	permissionsInterceptor.setConfigUtil(configUtil);
    	permissionsInterceptor.setPermissionsApi(permissionsApi);
        registry.addInterceptor(permissionsInterceptor).addPathPatterns("/admin/**");
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
