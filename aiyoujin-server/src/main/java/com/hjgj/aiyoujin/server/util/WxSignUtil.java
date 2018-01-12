package com.hjgj.aiyoujin.server.util;

import com.hjgj.aiyoujin.core.vo.UnifiedOrderRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class WxSignUtil {


    /**
     * 生成微信签名
     *
     * @param unifiedOrderRequest
     * @return
     */
    public static String createSign(UnifiedOrderRequest unifiedOrderRequest, String apiKey) {
        //根据规则创建可排序的map集合
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", unifiedOrderRequest.getAppid());
        packageParams.put("body", unifiedOrderRequest.getBody());
        packageParams.put("mch_id", unifiedOrderRequest.getMch_id());
        packageParams.put("nonce_str", unifiedOrderRequest.getNonce_str());
        packageParams.put("notify_url", unifiedOrderRequest.getNotify_url());
        packageParams.put("out_trade_no", unifiedOrderRequest.getOut_trade_no());
        packageParams.put("spbill_create_ip", unifiedOrderRequest.getSpbill_create_ip());
        packageParams.put("trade_type", unifiedOrderRequest.getTrade_type());
        packageParams.put("total_fee", unifiedOrderRequest.getTotal_fee());
        packageParams.put("openid",unifiedOrderRequest.getOpenid());

        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();//字典序
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            //为空不参与签名、参数名区分大小写
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();//MD5加密
        return sign;
    }


}
