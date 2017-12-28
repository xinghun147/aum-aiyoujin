package com.hjgj.aiyoujin.common.utils;

import com.alibaba.dubbo.config.ProtocolConfig;

public class DubboDestruction {

	public void destroy() throws Exception {
		ProtocolConfig.destroyAll();
	}
}
