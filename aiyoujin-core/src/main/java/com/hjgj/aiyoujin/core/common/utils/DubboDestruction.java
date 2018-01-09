package com.hjgj.aiyoujin.core.common.utils;


import com.alibaba.dubbo.config.ProtocolConfig;

public class DubboDestruction {

	public void destroy() throws Exception {
		ProtocolConfig.destroyAll();
	}
}
