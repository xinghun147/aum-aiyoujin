package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.WXProperties;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信 商户 平台 服务
 * @author sunii
 * @version 1.0
 */
@Service
public class WxMPService {


    // 企业给用户打钱 地址
    protected static final String REFUND_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";


    // NO_CHECK：不校验真实姓名
    // FORCE_CHECK：强校验真实姓名
    public void refundToUser(String openId) {
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mch_appid", WXProperties.WeiXinAppid);
        packageParams.put("mchid",WXProperties.WeiXinMchId);
        packageParams.put("nonce_str",WXProperties.WeiXinMchId);
        packageParams.put("partner_trade_no",WXProperties.WeiXinMchId);
        packageParams.put("openid",WXProperties.WeiXinMchId);
        packageParams.put("",WXProperties.WeiXinMchId);
        packageParams.put("check_name","NO_CHECK");
//        packageParams.put("re_user_name","NO_CHECK");
        packageParams.put("amount",WXProperties.WeiXinMchId); // 企业付款金额，单位为分
        packageParams.put("desc",WXProperties.WeiXinMchId); // 企业付款描述信息 企业付款操作说明信息
        packageParams.put("spbill_create_ip",WXProperties.WeiXinMchId); // 调用接口的机器Ip地址
    }

}
