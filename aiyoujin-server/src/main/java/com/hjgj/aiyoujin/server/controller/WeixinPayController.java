package com.hjgj.aiyoujin.server.controller;

import com.alibaba.fastjson.JSON;
import com.hjgj.aiyoujin.core.common.exception.BusinessException;
import com.hjgj.aiyoujin.core.common.utils.CommonUtils;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderLog;
import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.Product;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.service.OrderNotifyService;
import com.hjgj.aiyoujin.core.service.ProductService;
import com.hjgj.aiyoujin.core.service.UserOrderService;
import com.hjgj.aiyoujin.core.service.UserService;
import com.hjgj.aiyoujin.core.vo.UnifiedOrderRespose;
import com.hjgj.aiyoujin.server.common.ResultStatus;
import com.hjgj.aiyoujin.server.common.redis.RedisLockUtil;
import com.hjgj.aiyoujin.server.util.MD5Util;
import com.hjgj.aiyoujin.server.vo.WeiXinPayResultVo;
import com.hjgj.aiyoujin.server.vo.WeiXinPrePayVo;
import com.hjgj.aiyoujin.server.webBiz.WeixinOrder;
import com.hjgj.aiyoujin.server.webBiz.WeixinPush;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    private UserOrderService userOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WeixinOrder weixinOrder;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderNotifyService orderNotifyService;
    
    @Autowired
	public RedisLockUtil redisLockUtil;

    @ApiOperation(value = "微信预下单")
    @ResponseBody
    @RequestMapping(value = "/preparePay", method = RequestMethod.POST)
    public String prePayOrder2(@ApiParam(value = "时间戳", required = true) @RequestParam String timeStamp,
                               @ApiParam(value = "随机串", required = true) @RequestParam String nonceStr,
                               @ApiParam(value = "openId", required = true) @RequestParam String openId,
                               @ApiParam(value = "产品Id", required = true) @RequestParam String productId,
                               @ApiParam(value = "留言内容") @RequestParam(required = false) String content,
                               @ApiParam(value = "图片地址") @RequestParam(required = false) String imageUrl,
                               @ApiParam(value = "视频地址") @RequestParam(required = false) String videoUrl) {
        Assert.notNull(timeStamp, "时间戳can not be empty");
        Assert.notNull(nonceStr, "随机串 can not be empty");
        Assert.notNull(openId, "openId can not be empty");
        Assert.notNull(productId, "产品Id can not be empty");
        HashMap<String, Object> map = new HashMap<>();
        if(!redisLockUtil.acquireLock(productId+"_"+openId+"prePayOrder2", 3 * 1000)){
   			logger.error("频繁调用微信预下单接口,"+productId+"_"+openId+"_sendGiftCard");
   			map.put("code", "1");
   			map.put("msg", ResultStatus.ERROR_OPERATION_FREQUENT.getMsg());
   			return JSON.toJSONString(map);
       	}
        //校验库存
        Product product = productService.findProduct(productId);
        if(product == null){
            map.put("code", "1");
            map.put("msg", ResultStatus.PRODUCT_NO_EXIST.getMsg());
            return JSON.toJSONString(map);
        }
        if(product.getQuantity() < 1){
    	   map.put("code", "1");
           map.put("msg", ResultStatus.PRODUCT_NO_STOCK.getMsg());
           return JSON.toJSONString(map);
        }
        Integer productStatus = product.getStatus();
        if (productStatus != 2) {
            map.put("code", "1");
            map.put("msg", ResultStatus.PRODUCT_NO_SELLING.getMsg());
            return JSON.toJSONString(map);
        }
        WeiXinPrePayVo weixin = new WeiXinPrePayVo();
        weixin.setContent(content);
        weixin.setNonceStr(nonceStr);
        weixin.setOpenId(openId);
        weixin.setProductId(productId);
        weixin.setTimeStamp(timeStamp);

        String responseMsg = null;
        String orderNo = CommonUtils.generateOrderNo("CZ");

        User userBy = userService.getUserByOpenId(weixin.getOpenId());

        Order wxOrder = new Order();
        wxOrder.setBuyAmount(product.getBuyPrice());
        wxOrder.setId(UUIDGenerator.generate());
        wxOrder.setDeleted(0);
        wxOrder.setCreateTime(new Date());
        wxOrder.setProductId(weixin.getProductId());
        wxOrder.setStatus(0);
        wxOrder.setCode(orderNo); // 订单号
        wxOrder.setUserId(userBy.getId());
        wxOrder.setBuyAmount(product.getBuyPrice());
        wxOrder.setSellAmount(product.getSellPrice());
        logger.info("微信预支付订单为:" + wxOrder);
        // 订单状态：0待支付、1支付成功、2支付失败、3送出待收、4已退回、5送出成功、
        //	  6领取成功、7变现待处理、8变现成功、9变现失败、10提货待处理、11物流中、12已收货

        String xmlRequest = weixinOrder.getXmlRequest(weixin.getNonceStr(), weixin.getOpenId(), product.getBuyPrice(), orderNo, product.getName());
        Map<String, Object> prePayXML = weixinOrder.wxPrePayXML(xmlRequest);
        String xmlStr = (String) prePayXML.get("xmlStr");
        UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) prePayXML.get("unifiedOrderRespose");
        Date createTime = (Date) prePayXML.get("createTime");
        Date updateTime = (Date) prePayXML.get("updateTime");

        OrderLog orderLog = new OrderLog();
        orderLog.setId(UUIDGenerator.generate());
        orderLog.setOrderId(wxOrder.getId());
        orderLog.setPayReq(xmlRequest);
        orderLog.setPayResp(xmlStr);
        orderLog.setStatus(Integer.valueOf(0)); // 支付状态: 0-处理中 1-成功 2-失败
        orderLog.setPayType(Integer.valueOf(1)); //支付类型（0-付款 1-收款）
        orderLog.setCreateTime(new Date());
        orderLog.setCreateBy("System");
        orderLog.setReqTime(createTime);
        orderLog.setRespTime(updateTime);
        logger.info("微信预支付订单log为:" + orderLog);

        
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(UUIDGenerator.generate());
        orderMessage.setContent(weixin.getContent());
        orderMessage.setCreateTime(new Date());
        orderMessage.setDeleted(0);
        orderMessage.setImageUrl(imageUrl);
        orderMessage.setVideoUrl(videoUrl);
        orderMessage.setUserId(userBy.getId());
        orderMessage.setOrderId(wxOrder.getId());
        orderMessage.setOrderNo(orderNo);
        logger.info("微信预支付订单log为:" + orderMessage);

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
            logger.info("二次签名是" + sb.toString());
            String paySign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//            String paySign = sb.toString();

            orderLog.setPayResultCode(unifiedOrderRespose.getResult_code());
            orderLog.setPayResultMsg(unifiedOrderRespose.getReturn_msg());
            orderLog.setUpdateTime(orderLog.getCreateTime());
            orderLog.setPrepayId(unifiedOrderRespose.getPrepay_id());
            int insertThree;
			try {
				insertThree = userOrderService.createOrder(wxOrder, orderLog, orderMessage);
				   if (insertThree > 0) {
		                map.put("code", "0");
		                map.put("msg", "恭喜,微信预支付成功!");
		                map.put("timeStamp", weixin.getTimeStamp());
		                map.put("nonceStr", weixin.getNonceStr());
		                map.put("package", "prepay_id=" + prepayId);
		                map.put("signType", "MD5");
		                map.put("paySign", paySign);
		                map.put("orderId", wxOrder.getId());
		            } else {
		                map.put("code", "1");
		            }
			} catch (BusinessException e) {
				logger.error("创建订单接口异常,e:{}", e);
			    map.put("code", "1");
	            map.put("msg", "创建订单失败!");
			}
        } else {
            map.put("code", "1");
            map.put("msg", "您的参数不合法,请校验后重试!");
        }
        responseMsg = JSON.toJSONString(map);
        return responseMsg;
    }


    /**
     * TODO 微信支付通知回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "微信支付通知回调")
    @RequestMapping(value = "/wxPayCallback/{orderNo}", method = {RequestMethod.POST, RequestMethod.GET})
    public void paySuccessCallback(@PathVariable(value = "orderNo") String orderCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("微信支付,请求回调,订单号为:" + orderCode);
        System.out.println("----微信支付,请求回调,订单号为:" + orderCode);
        if (orderCode == null) {
            response.addHeader("Content-Length", "100");
            PrintWriter writer = response.getWriter();
            writer.print("100");
            writer.close();
            return;
        }
        Order userOrderBySelfOrderno = userOrderService.getUserOrderBySelfOrderno(orderCode);
        if (userOrderBySelfOrderno == null) {
            response.addHeader("Content-Length", "100");
            PrintWriter writer = response.getWriter();
            writer.print("请求非法");
            writer.close();
            return;
        }

        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        logger.info("微信支付,请求回调,回调数据为:" + postData);
        if (postData == null) {
            return;
        }
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
        //将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
        xStream.alias("xml", WeiXinPayResultVo.class);
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
                String v = (String) entry.getValue();
                String k = (String) entry.getKey();
                //为空不参与签名、参数名区分大小写
                if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                    sb.append(k + "=" + v + "&");
                }
            }
            sb.append("key=" + weixinOrder.getWeiXinProperty().getApiKey());
            logger.info("二次签名是" + sb.toString());
            String paySign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
            logger.info("paySign:{}", paySign);
            if (paySign.equals(payResultVo.getSign())) {
                Order selfOrder = userOrderService.getUserOrderBySelfOrderno(payResultVo.getOut_trade_no());
                if (selfOrder != null) {
                    Integer status = selfOrder.getStatus();
                    if (status.equals(Integer.valueOf(2))) {
                        String result_code = payResultVo.getResult_code();
                        if ("SUCCESS".equals(result_code.trim())) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("<xml>");
                            builder.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            builder.append("<return_msg><![CDATA[OK]]></return_msg>");
                            builder.append("</xml>");
                            PrintWriter out = response.getWriter();
                            out.print(builder.toString());
                            out.close();
                            //callbackResolved(selfOrder.getId(), payResultVo.getOpenid(), payResultVo.getTransaction_id(), 1);
                        } else if ("FAIL".equals(result_code.trim())) {
                            callbackResolved(selfOrder.getId(), payResultVo.getOpenid(), payResultVo.getTransaction_id(), 2);
                        }
                    } else if (status.equals(Integer.valueOf(0))) {  // 待支付状态
                        String result_code = payResultVo.getResult_code();
                        if ("SUCCESS".equals(result_code.trim())) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("<xml>");
                            builder.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            builder.append("<return_msg><![CDATA[OK]]></return_msg>");
                            builder.append("</xml>");
                            PrintWriter out = response.getWriter();
                            out.print(builder.toString());
                            out.close();
                            callbackResolved(selfOrder.getId(), payResultVo.getOpenid(), payResultVo.getTransaction_id(), 1);
                        } else if ("FAIL".equals(result_code.trim())) {
                            callbackResolved(selfOrder.getId(), payResultVo.getOpenid(), payResultVo.getTransaction_id(), 2);
                        }
                    } else if (status.equals(Integer.valueOf(2))) { // 支付失败
                        String result_code = payResultVo.getResult_code();
                        if ("SUCCESS".equals(result_code.trim())) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("<xml>");
                            builder.append("<return_msg><![CDATA[OK]]></return_msg>");
                            builder.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                            builder.append("</xml>");
                            PrintWriter out = response.getWriter();
                            out.print(builder.toString());
                            out.close();
                            callbackResolved(selfOrder.getId(), payResultVo.getOpenid(), payResultVo.getTransaction_id(), 1);
                        } else if ("FAIL".equals(result_code.trim())) {
                            callbackResolved(selfOrder.getId(), payResultVo.getOpenid(), payResultVo.getTransaction_id(), 2);
                        }
                    }
                } else { // 订单不存在
                    logger.info("该笔订单不存在,订单号为:" + payResultVo.getOut_trade_no());
                }
            } else { // 微信校验失败
                logger.info("微信校验签名失败,请执行后续操作");
                logger.info("微信校验签名失败,回调数据为:" + postData);
            }
        } else {
            logger.info("系统校验签名失败,回调数据为:" + postData);
        }
    }


    private void callbackResolved(String orderIdSrc, String openId, String wxOrderId, int state) {
        Map orderProduct = userOrderService.getOrderProduct(orderIdSrc);
        String orderId = (String) orderProduct.get("orderId");
        String orderNo = (String) orderProduct.get("orderNo");
        Date updateTime = (Date) orderProduct.get("updateTime");
        String prepayId = (String) orderProduct.get("prepayId");
        String productName = (String) orderProduct.get("productName");
        BigDecimal buyMoney = (BigDecimal) orderProduct.get("buyMoney");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderNo", orderNo);
        hashMap.put("orderId", orderId);
        hashMap.put("updateTime", updateTime);
        hashMap.put("updateBy", "wexinCallBack");
        //hashMap.put("prepayId",prepayId);
        hashMap.put("logStatus", state); // 订单记录表 支付状态: 0-处理中 1-成功 2-失败
        hashMap.put("orderStatus", state);  // 订单状态：0待支付、1支付成功、2支付失败
        hashMap.put("payId", wxOrderId);
        userOrderService.updateOrderAndOrderLogByMap(hashMap);

        DecimalFormat df = new DecimalFormat("#");
        if (state == 1) {
//            Map map = weixinPush.payResultNotify(productName, df.format(buyMoney), updateTime, prepayId, openId);
            Map map = weixinPush.payResultNotify(productName, buyMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString(), updateTime, prepayId, openId,orderIdSrc);
        }
        if (state == 2) {
//            Map failMap = weixinPush.payResultNotifyFail(productName, df.format(buyMoney), "订单异常", updateTime, prepayId, openId);
            Map failMap = weixinPush.payResultNotifyFail(productName, buyMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString(), "订单异常", updateTime, prepayId, openId);
        }

    }
}
