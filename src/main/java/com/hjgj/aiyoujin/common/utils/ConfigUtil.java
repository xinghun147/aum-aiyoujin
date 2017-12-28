package com.hjgj.aiyoujin.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {
	
	private @Value("${cas.server-url-prefix}")String casServerUrl;
	private @Value("${cas.client-host-url}")String casServiceUrl;
	private @Value("${host.base_path}")String hostUrl;
	
	public String getCasServerUrl() {
		return casServerUrl;
	}
	
	public String getCasServiceUrl() {
		return casServiceUrl;
	}

	public String getHostUrl() {
		return hostUrl;
	}
}
