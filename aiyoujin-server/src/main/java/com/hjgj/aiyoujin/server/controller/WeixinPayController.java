package com.hjgj.aiyoujin.server.controller;

import com.alibaba.fastjson.JSON;
import com.hjgj.aiyoujin.core.common.utils.CommonUtils;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.OrderLog;
import com.hjgj.aiyoujin.core.service.OrderLogService;
import com.hjgj.aiyoujin.core.vo.UnifiedOrderRespose;
import com.hjgj.aiyoujin.server.util.MD5Util;
import com.hjgj.aiyoujin.server.webBiz.WeixinOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "/weixin")
public class WeixinPayController {

    protected final Logger logger = LoggerFactory.getLogger(WeixinPayController.class);

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private WeixinOrder weixinOrder;


    @ResponseBody
    @RequestMapping(value = "/preparePay",method = RequestMethod.POST)
    public String prePayOrder(String timeStamp, String nonceStr, String openId, BigDecimal payMoney) {
        String responseMsg = null;
        String orderNo = CommonUtils.generateOrderNo("CZ");
        HashMap<String, Object> map = new HashMap<>();
        String xmlRequest = weixinOrder.generateXmlRequest(nonceStr, openId, payMoney, orderNo);
        Map<String, Object> prePayXML = weixinOrder.wxPrePayXML(xmlRequest);
        String xmlStr = (String) prePayXML.get("xmlStr");
        UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) prePayXML.get("unifiedOrderRespose");
        Date createTime = (Date) prePayXML.get("createTime");
        Date updateTime = (Date) prePayXML.get("updateTime");

        OrderLog orderLog = new OrderLog();
        orderLog.setId(UUIDGenerator.generate());
        orderLog.setOrderId(orderNo);
        orderLog.setPayReq(xmlRequest);
        orderLog.setPayResp(xmlStr);
        orderLog.setStatus(Integer.valueOf(0)); // 支付状态: 0-处理中 1-成功 2-失败
        orderLog.setPayType(Integer.valueOf(1)); //支付类型（0-付款 1-收款）
        orderLog.setCreateTime(new Date());
        orderLog.setCreateBy("System");
        orderLog.setReqTime(createTime);
        orderLog.setRespTime(updateTime);
        if (unifiedOrderRespose != null) {

            String prepayId = unifiedOrderRespose.getPrepay_id();
            String appid = unifiedOrderRespose.getAppid();
            SortedMap<String, Object> treeMap = new TreeMap<>();
            treeMap.put("appId", appid);
            treeMap.put("nonceStr", nonceStr);
            treeMap.put("package", "prepay_id=" + prepayId);
            treeMap.put("signType", "MD5");
            treeMap.put("timeStamp", timeStamp);

            StringBuffer sb = new StringBuffer();
            Set es = treeMap.entrySet();//字典序
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
            sb.append("key=" + weixinOrder.getWeiXinProperty().getApiKey());
            System.out.println("二次签名是"+sb.toString());
            String paySign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//            String paySign = sb.toString();
            map.put("code", "0");
            map.put("msg", "恭喜,微信预支付成功!");
            map.put("timeStamp", timeStamp);
            map.put("nonceStr", nonceStr);
            map.put("package", "prepay_id=" + prepayId);
            map.put("signType", "MD5");
            map.put("paySign", paySign);

            orderLog.setPayResultCode(unifiedOrderRespose.getResult_code());
            orderLog.setPayResultMsg(unifiedOrderRespose.getReturn_msg());
            orderLog.setUpdateTime(orderLog.getCreateTime());
            orderLog.setPrepayId(unifiedOrderRespose.getPrepay_id());

            int insertOrderLog = orderLogService.insertOrderLog(orderLog);
        } else {
            map.put("code", "1");
            map.put("msg", "您的参数不合法,请校验后重试!");
        }
        responseMsg = JSON.toJSONString(map);
        return responseMsg;
    }



}
