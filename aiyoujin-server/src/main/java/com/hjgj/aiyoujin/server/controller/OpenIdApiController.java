package com.hjgj.aiyoujin.server.controller;

import com.hjgj.aiyoujin.server.common.OpenIdResult;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.client.LocalHttpClient;

@Controller
//@RequestMapping(value = "/api/openid")
public class OpenIdApiController {

    protected final Logger logger = LoggerFactory.getLogger(OpenIdApiController.class);

    protected static final String BASE_URI = "https://api.weixin.qq.com";

    protected static final String MEDIA_URI = "http://file.api.weixin.qq.com";
    protected static final String MP_URI = "https://mp.weixin.qq.com";
    protected static final String MCH_URI = "https://api.mch.weixin.qq.com";
    protected static final String OPEN_URI = "https://open.weixin.qq.com";

    protected static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    protected static Header xmlHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_XML.toString());

    protected static final String PARAM_ACCESS_TOKEN = "access_token";

    @ResponseBody
    @RequestMapping(value = "getOpenId",method = RequestMethod.GET)
    public OpenIdResult getOpenId(String jsCode) {
    	OpenIdResult openIdResult =null;
    	try {
    		HttpUriRequest httpUriRequest = RequestBuilder.post()
    				.setHeader(jsonHeader)
    				.setUri(BASE_URI+"/sns/jscode2session")
    				.addParameter("appid", "wxbad011f34af54919")
    				.addParameter("secret", "8c998dd4511ad0c2b23e40a0cfebe1a1")
    				.addParameter("js_code",jsCode)
    				.addParameter("grant_type","authorization_code")
    				.build();
    		
    		 openIdResult = LocalHttpClient.executeJsonResult(httpUriRequest, OpenIdResult.class);
		} catch (Exception e) {
			logger.error("获取OpenId异常，e:{},",e);
		}
    	return openIdResult;
    }


}
