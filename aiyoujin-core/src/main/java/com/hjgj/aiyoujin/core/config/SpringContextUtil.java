package com.hjgj.aiyoujin.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringContextUtil {

	private static ApplicationContext applicationContext;

	protected static void setApplicationContext(ApplicationContext applicationContextx) {
		if(applicationContext==null){
			SpringContextUtil.applicationContext = applicationContextx;
		}
	}

	public static <T> T getBean(Class<T> beanName) throws BeansException {
		return applicationContext.getBean(beanName);
	}

}
