package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.WXProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.Transfers;
import weixin.popular.bean.paymch.TransfersResult;
import weixin.popular.client.LocalHttpClient;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信 商户 平台 服务
 *
 * @author sunii
 * @version 1.0
 */
@Service
public class WxMPService {

    protected final Logger logger = LoggerFactory.getLogger(WxMPService.class);

    /**
     * TODO 付款金额单位是分,必须由元转换为分(且为整数)
     *
     * @param openId
     * @param nonceStr
     * @param orderNo
     * @param amount
     * @param desc
     * @return
     */
    public Map promotionTransfers(String openId, String nonceStr, String orderNo, String amount, String desc) {
        //String responseStr = "";
        HashMap<String, Object> hashMap = new HashMap<>();
        Transfers transfers = new Transfers();
        transfers.setAmount(amount);
        // NO_CHECK：不校验真实姓名
        // FORCE_CHECK：强校验真实姓名
        transfers.setCheck_name("NO_CHECK");
        transfers.setDesc(desc);
        transfers.setMch_appid(WXProperties.WeiXinAppid);
        transfers.setMchid(WXProperties.WeiXinMchId);
        transfers.setNonce_str(nonceStr);
        transfers.setOpenid(openId);
        transfers.setPartner_trade_no(orderNo);
        //transfers.setSign_type("MD5");
        transfers.setSpbill_create_ip(WXProperties.WeiXinSpbillCreateIp);
        InputStream resource = getClass().getClassLoader().getResourceAsStream("config/apiclient_cert.p12");
        LocalHttpClient.initMchKeyStore(WXProperties.WeiXinMchId, resource);
        TransfersResult transfersResult = PayMchAPI.mmpaymkttransfersPromotionTransfers(transfers, WXProperties.weixinApiKey);

        if (null != transfersResult && "SUCCESS".equals(transfersResult.getReturn_code()) && "SUCCESS".equals(transfersResult.getResult_code())) {
            hashMap.put("code", "0");
            hashMap.put("msg", transfersResult.getReturn_msg());
            hashMap.put("wxpayOrderNo", transfersResult.getPayment_no());
            hashMap.put("wxpayTime", transfersResult.getPayment_time());

            logger.info("打款成功");
        } else {
            hashMap.put("code", "1");
            hashMap.put("msg", transfersResult.getReturn_msg());
            logger.error("打款失败");
        }
        //responseStr = JSON.toJSONString(hashMap);
        return hashMap;
    }

}
