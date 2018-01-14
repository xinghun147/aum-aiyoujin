package com.hjgj.aiyoujin.server.controller;

import com.alibaba.fastjson.JSON;
import com.hjgj.aiyoujin.core.common.utils.CommonUtils;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderLog;
import com.hjgj.aiyoujin.core.model.Product;
import com.hjgj.aiyoujin.core.service.OrderLogService;
import com.hjgj.aiyoujin.core.service.ProductService;
import com.hjgj.aiyoujin.core.service.UserOrderService;
import com.hjgj.aiyoujin.core.vo.UnifiedOrderRespose;
import com.hjgj.aiyoujin.server.util.MD5Util;
import com.hjgj.aiyoujin.server.vo.WeiXinPayResultVo;
import com.hjgj.aiyoujin.server.vo.WeiXinPrePayVo;
import com.hjgj.aiyoujin.server.webBiz.WeixinOrder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
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
    private UserOrderService userOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WeixinOrder weixinOrder;


    @ResponseBody
    @RequestMapping(value = "/preparePay", method = RequestMethod.POST)
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
            System.out.println("二次签名是" + sb.toString());
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


    @ApiOperation(value = "微信预下单")
    @ResponseBody
    @RequestMapping(value = "/preparePay", method = RequestMethod.GET)
    public String prePayOrder2(WeiXinPrePayVo weixin) {
        String responseMsg = null;
        String orderNo = CommonUtils.generateOrderNo("CZ");

        Product product = productService.findProduct(weixin.getProductId());
        String name = product.getName();


        Date nowDate = new Date();
        Order wxOrder = new Order();
        wxOrder.setBuyAmount(weixin.getPayMoney());
        wxOrder.setId(UUIDGenerator.generate());
        wxOrder.setDeleted(0);
        wxOrder.setCreateTime(nowDate);
        wxOrder.setProductId(weixin.getProductId());
        wxOrder.setStatus(0);
        wxOrder.setCode(orderNo); // 订单号
        // 订单状态：0待支付、1支付成功、2支付失败、3送出待收、4已退回、5送出成功、
        //	  6领取成功、7变现待处理、8变现成功、9变现失败、10提货待处理、11物流中、12已收货


        HashMap<String, Object> map = new HashMap<>();
        String xmlRequest = weixinOrder.getXmlRequest(weixin.getNonceStr(), weixin.getOpenId(), weixin.getPayMoney(), orderNo, weixin.getProductId());
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
            treeMap.put("nonceStr", weixin.getNonceStr());
            treeMap.put("package", "prepay_id=" + prepayId);
            treeMap.put("signType", "MD5");
            treeMap.put("timeStamp", weixin.getTimeStamp());

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
            System.out.println("二次签名是" + sb.toString());
            String paySign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//            String paySign = sb.toString();
            map.put("code", "0");
            map.put("msg", "恭喜,微信预支付成功!");
            map.put("timeStamp", weixin.getTimeStamp());
            map.put("nonceStr", weixin.getNonceStr());
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


    /**
     * TODO 微信支付通知回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/wxPayCallback")
    public void paySuccessCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");

        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
        //将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
        xStream.alias("xml", UnifiedOrderRespose.class);
        WeiXinPayResultVo payResultVo = (WeiXinPayResultVo) xStream.fromXML(postData);

        if (payResultVo != null && payResultVo.getReturn_code().equals("SUCCESS")) {
            SortedMap<String, Object> treeMap = new TreeMap<>();
            treeMap.put("appid", weixinOrder.getWeiXinProperty().getAppid());
            treeMap.put("bank_type", payResultVo.getBank_type());
            treeMap.put("cash_fee", payResultVo.getCash_fee());
            treeMap.put("fee_type", payResultVo.getFee_type());
            treeMap.put("is_subscribe", payResultVo.getIs_subscribe());
            treeMap.put("mch_id", weixinOrder.getWeiXinProperty().getMerchantId());
            treeMap.put("nonce_str", payResultVo.getNonce_str());
            treeMap.put("openid", payResultVo.getOpenid());
            treeMap.put("out_trade_no", payResultVo.getOut_trade_no());
            treeMap.put("result_code", payResultVo.getResult_code());
            treeMap.put("return_code", payResultVo.getReturn_code());
            treeMap.put("time_end", payResultVo.getTime_end());
            treeMap.put("total_fee", payResultVo.getTotal_fee());
            treeMap.put("trade_type", payResultVo.getTrade_type());
            treeMap.put("transaction_id", payResultVo.getTransaction_id());


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
            System.out.println("二次签名是" + sb.toString());
            String paySign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
            System.out.println(paySign);
            if (paySign.equals(payResultVo.getSign())) {
                Order selfOrder = userOrderService.getUserOrderBySelfOrderno(payResultVo.getOut_trade_no());
                if (selfOrder != null) {
                    Integer status = selfOrder.getStatus();
                    if(status.equals(Integer.valueOf(2))){
                        StringBuilder xml = new StringBuilder();
                        xml.append("<xml>");
                        xml.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                        xml.append("<return_msg><![CDATA[OK]]></return_msg>");
                        xml.append("</xml>");
                        PrintWriter out = response.getWriter();
                        out.print(xml.toString());
                        out.close();
                        Map orderProduct = userOrderService.getOrderProduct(selfOrder.getId());
                        Object updateTime = (Date)orderProduct.get("updateTime");
                    }else if(status.equals(Integer.valueOf(0))){  // 待支付状态
                        String result_code = payResultVo.getResult_code();
                        if("SUCCESS".equals(result_code.trim())){
                            userOrderService.updateOrderByCodeState(payResultVo.getOut_trade_no(),Integer.valueOf(2));
                            StringBuilder builder = new StringBuilder();
                            builder.append("<xml>");
                            builder.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            builder.append("<return_msg><![CDATA[OK]]></return_msg>");
                            builder.append("</xml>");
                            PrintWriter out = response.getWriter();
                            out.print(builder.toString());
                            out.close();
                        } else if("FAIL".equals(result_code.trim())){
                            userOrderService.updateOrderByCodeState(payResultVo.getOut_trade_no(),Integer.valueOf(1));
                        }
                    }else if(status.equals(Integer.valueOf(2))){ // 支付失败
                        String result_code = payResultVo.getResult_code();
                        if("SUCCESS".equals(result_code.trim())){
                            userOrderService.updateOrderByCodeState(payResultVo.getOut_trade_no(),Integer.valueOf(2));
                            StringBuilder builder = new StringBuilder();
                            builder.append("<xml>");
                            builder.append("<return_msg><![CDATA[OK]]></return_msg>");
                            builder.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            builder.append("</xml>");
                            PrintWriter out = response.getWriter();
                            out.print(builder.toString());
                            out.close();
                        } else if("FAIL".equals(result_code.trim())){
                            userOrderService.updateOrderByCodeState(payResultVo.getOut_trade_no(),Integer.valueOf(1));
                        }
                    }
                } else { // 订单不存在
                    logger.info("该笔订单不存在,订单号为:"+payResultVo.getOut_trade_no());
                }
            } else { // 微信校验失败
                logger.info("微信校验签名失败,请执行后续操作");
            }
        }
    }
}
