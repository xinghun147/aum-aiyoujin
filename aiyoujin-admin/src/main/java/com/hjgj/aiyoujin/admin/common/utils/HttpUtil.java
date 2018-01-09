package com.hjgj.aiyoujin.admin.common.utils;


import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final Function<Map.Entry<String, String>, NameValuePair> nameValueTransformFunction = new Function<Map.Entry<String, String>, NameValuePair>() {
        public NameValuePair apply(final Map.Entry<String, String> input) {
            return new BasicNameValuePair(input.getKey(), input.getValue());
        }
    };
    
    public static final int CONNECT_TIMEOUT = 20000;
    public static final int SOCKET_TIMEOUT = 20000;

    /**
     * handle response's entity to utf8 text
     */
    public static final ResponseHandler<String> UTF8_CONTENT_HANDLER = new ResponseHandler<String>() {
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            final StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
            return StringUtils.EMPTY;
        }
    };

    public static List<NameValuePair> toNameValuePair(Map<String, String> paramMap) {
    	 List<NameValuePair> nameValuePairs = Lists.newArrayList();
         if (paramMap != null) {
             Iterables.addAll(nameValuePairs, Iterables.transform(paramMap.entrySet(), nameValueTransformFunction));
         }
         return nameValuePairs;
    }

    public static String getRequest(String url) {
        try {
            String content = Request.Get(url)
                    .connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT)
                    .execute().handleResponse(UTF8_CONTENT_HANDLER);
            logger.debug("{},result:{}",url,content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        	logger.error("getRequest:{},error:{}",url,e);
        	return null;
        }
    }
    
    public static String getRequest(String uri, Map<String, String> paramMap) {
        try {
        	URI _uri=new URIBuilder(uri).addParameters(toNameValuePair(paramMap)).build();
            String content = Request.Get(_uri)
                    .connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT)
                    .execute().handleResponse(UTF8_CONTENT_HANDLER);
            logger.debug("{},result:{}",_uri,content);
            return content;
        } catch (Exception e) {
        	logger.error("getRequest:{},error:{}",uri,e);
        	return null;
        }
    }
    
    public static String postRequest(String url, Map<String, String> paramMap) {
        try {
            String content = Request.Post(url)
            		.bodyForm(toNameValuePair(paramMap))
                    .connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT)
                    .execute().handleResponse(UTF8_CONTENT_HANDLER);
            logger.debug("{},result:{}",url,content);
            return content;
        } catch (Exception e) {
        	logger.error("postRequest:{},error:{}",url,e);
        	return null;
        }
    }
    
    public static String postToJsonBody(String url,Object obj) {
    	return postBodyRequest(url,JSON.toJSONString(obj));
    }
    
    public static String postBodyRequest(String url, String body) {
        try {
        	Request request=Request.Post(url)
                    .connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT);
        	if(StringUtils.isNotBlank(body)){
        		request.bodyString(body, ContentType.create("text/html", Consts.UTF_8));
        	}
            String content = request.execute().handleResponse(UTF8_CONTENT_HANDLER);
            logger.debug("{},result:{}",url,content);
            return content;
        } catch (Exception e) {
        	logger.error("postBodyRequest:{},error:{}",url,e);
        	return null;
        }
    }
    
    
    public static String getRequest(String uri, Map<String, String> paramMap,Map<String,String> headers) {
        try {
        	URI _uri=new URIBuilder(uri).addParameters(toNameValuePair(paramMap)).build();
        	Request request = Request.Get(_uri)
                    .connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT);
            for (Map.Entry<String, String> e : headers.entrySet()) {
            	request.addHeader(e.getKey(), e.getValue());
            }
            String content = request
                    .execute().handleResponse(UTF8_CONTENT_HANDLER);
            logger.debug("{},result:{}",_uri,content);
            return content;
        } catch (Exception e) {
        	logger.error("getRequest:{},error:{}",uri,e);
        	return null;
        }
    }

    @Test
    public void tests(){
        String cardNo = "62177307090499340";
        String cardNo1 = "6222030200000585178";
        String merchantId = "M200000663";
        String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0zCFvnHnxLb4SgBLsP61z1h6wRUqngSYRcTO4xGcrHcDqhwxIeUeZ4aeitxO/jW0frU89c/EHjz4scrgZSnsZRBACTMvocZhQwz4D1N+1gaapV6MDB6Pjat8TYTu624lGfwtdCHT00G7QFDJtJaGvpBrNW+jFZlq3ckMwwBMJ4wIDAQAB";

        String payUrl ="http://sandbox.firstpay.com/security/gateway.do";
//        Map<String, String> queryMap = UcfPayUtils.getBinQueryMap(cardNo1, merchantId, privateKey);
//        String postRequest = HttpUtil.postRequest(payUrl, queryMap);
//        System.out.println(postRequest);


    }

    @Test
    public void abc(){
        String cardNo = "62177307090499340";
        String cardNo1 = "6222030200000585178";
        System.out.println(FormatCheckUtils.matchLuhn(cardNo));
    }
}