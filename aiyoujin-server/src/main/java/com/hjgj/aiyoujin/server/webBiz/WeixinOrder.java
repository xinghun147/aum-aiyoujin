package com.hjgj.aiyoujin.server.webBiz;

import com.hjgj.aiyoujin.core.vo.UnifiedOrderRequest;
import com.hjgj.aiyoujin.core.vo.UnifiedOrderRespose;
import com.hjgj.aiyoujin.server.config.WeiXinProperty;
import com.hjgj.aiyoujin.server.util.WxSignUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weixin.popular.client.LocalHttpClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeixinOrder {

    protected static final String MCH_URI = "https://api.mch.weixin.qq.com";

    protected static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    protected static Header xmlHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.toString());

    @Autowired
    private WeiXinProperty weiXinProperty;

    public WeiXinProperty getWeiXinProperty() {
        return weiXinProperty;
    }

    public String generateXmlRequest(String nonce, String openId, BigDecimal payMoney, String orderNo) {
        String appid = weiXinProperty.getAppid();
        String merchantId = weiXinProperty.getMerchantId();
        String invokeUrl = weiXinProperty.getInvokeUrl();
        String clientIP = weiXinProperty.getClientIP();
        String apiKey = weiXinProperty.getApiKey();

        UnifiedOrderRequest orderRequest = new UnifiedOrderRequest();

        DecimalFormat df = new DecimalFormat("#");
        String money = df.format(payMoney.multiply(new BigDecimal(100)));
        orderRequest.setAppid(appid);
        orderRequest.setBody("爱有金,微信支付:" + payMoney + "元");
        orderRequest.setMch_id(merchantId);
        orderRequest.setNonce_str(nonce);
        orderRequest.setNotify_url(invokeUrl);
        orderRequest.setOut_trade_no(orderNo);
        orderRequest.setSpbill_create_ip(clientIP);
        orderRequest.setTrade_type("JSAPI"); //JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
        orderRequest.setTotal_fee(money); // 单位是:分
        orderRequest.setOpenid(openId);
        orderRequest.setSign(WxSignUtil.createSign(orderRequest, apiKey));//签名

        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
        String xmlRequest = xStream.toXML(orderRequest);
        return xmlRequest;
    }


    public UnifiedOrderRespose wxPrePay(String xmlRequestStr) {

        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(MCH_URI + "/pay/unifiedorder")
                .setCharset(Charset.forName("utf-8"))
                .setEntity(new StringEntity(xmlRequestStr, Charset.forName("utf-8")))
                .build();
        CloseableHttpResponse response = LocalHttpClient.execute(httpUriRequest);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();


        try {
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                Header contentType = response.getEntity().getContentType();

                if (contentType != null && !contentType.toString().matches(".*[uU][tT][fF]-8$")) {
                    str = new String(str.getBytes("iso-8859-1"), "utf-8");
                }

                System.out.println("xml原文是:" + str);
                XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
                //将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
                xStream.alias("xml", UnifiedOrderRespose.class);
                UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(str);

                if (null != unifiedOrderRespose && "SUCCESS".equals(unifiedOrderRespose.getReturn_code()) && "SUCCESS".equals(unifiedOrderRespose.getResult_code())) {
                    return unifiedOrderRespose;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map wxPrePayXML(String xmlRequestStr) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("xmlStr", null);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(MCH_URI + "/pay/unifiedorder")
                .setCharset(Charset.forName("utf-8"))
                .setEntity(new StringEntity(xmlRequestStr, Charset.forName("utf-8")))
                .build();
        CloseableHttpResponse response = LocalHttpClient.execute(httpUriRequest);
        hashMap.put("createTime", new Date());
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        try {
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                Header contentType = response.getEntity().getContentType();

                if (contentType != null && !contentType.toString().matches(".*[uU][tT][fF]-8$")) {
                    str = new String(str.getBytes("iso-8859-1"), "utf-8");
                }

                System.out.println("xml原文是:" + str);
                hashMap.put("xmlStr", str);
                XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
                //将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
                xStream.alias("xml", UnifiedOrderRespose.class);
                UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(str);

                if (null != unifiedOrderRespose && "SUCCESS".equals(unifiedOrderRespose.getReturn_code()) && "SUCCESS".equals(unifiedOrderRespose.getResult_code())) {
                    hashMap.put("unifiedOrderRespose", unifiedOrderRespose);
                } else {
                    hashMap.put("unifiedOrderRespose", null);
                }
            } else {
                hashMap.put("unifiedOrderRespose", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        hashMap.put("updateTime", new Date());
        return hashMap;
    }

}
